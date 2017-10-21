package com.progress.soft.dao;

import com.progress.soft.dao.TransactionDao;
import com.progress.soft.model.TransactionModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by inshaf on 10/19/17.
 */

@Repository("TransactionDaoImpl")
public class TransactionDaoImpl implements TransactionDao {

    private Log logger = LogFactory.getLog(TransactionDaoImpl.class);

    @PersistenceContext
    EntityManager em;

    @Override
    public void upload(TransactionModel transaction) {
        em.persist(transaction);
    }

    /*
    This method uses batch processing and is based on data binding
     */
    @Override
    public void upload(List<TransactionModel> transactionModelList) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LOCAL_PERSISTENCE");
        em = emf.createEntityManager();

        final int batchSize = 20000;

        try {
            em.getTransaction().begin();

            for (int i = 0; i < transactionModelList.size(); i++) {
                em.persist(transactionModelList.get(i));

                if (i % batchSize == 0 && i > 0) {
                    em.flush();
                    em.clear();

                }
            }

            em.getTransaction().commit();

        } catch (Exception e) {
            logger.error("Exception while processing transaction ", e);
            throw e;
        } finally {
            em.close();
            emf.close();
        }


    }

    /*
    This method uses native query and data binding, Latency only happens when creating model for each row
     */
    @Override
    public void uploadNativeQuery(List<TransactionModel> transactionModelList) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LOCAL_PERSISTENCE");
        em = emf.createEntityManager();

        String query = "insert into TRANSACTION_TABLE (ID, FROM_CURRENCY, TO_CURRENCY, TIMESTAMP, AMOUNT, FILE_NAME) values ";

        try {
            em.getTransaction().begin();

            String separator = "";

            String queryVal = "";

            for (TransactionModel temp : transactionModelList) {
                queryVal += separator + "( '" + temp.getFromCurrency() + "' , '" + temp.getToCurrency() + "' , '" + temp.getTimestamp()
                        + "' , '" + temp.getAmount() + "' , '" + temp.getFileName() + "')";

                separator = ",";

            }

            query += queryVal;

            em.createNativeQuery(query).executeUpdate();

            em.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Exception while processing transaction ", e);
            throw e;
        } finally {
            em.close();
            emf.close();
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Transaction committed");
        }

    }

    /*
    This method based on native query, No data binding involved, only takes 3 seconds to upload entire 100k records
     */
    @Override
    public void uploadNativeQuery(StringBuilder stringBuilder) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LOCAL_PERSISTENCE");
        em = emf.createEntityManager();

        stringBuilder.insert(0, "insert into TRANSACTION_TABLE (ID, FROM_CURRENCY, TO_CURRENCY, TIMESTAMP, AMOUNT, FILE_NAME) values ");

        try {

            em.getTransaction().begin();
            em.createNativeQuery(stringBuilder.toString()).executeUpdate();
            em.getTransaction().commit();

        } catch (Exception e) {
            logger.error("Exception while processing transaction ", e);
            throw e;
        } finally {
            em.close();
            emf.close();
        }


        if (logger.isDebugEnabled()) {
            logger.debug("Transaction committed");
        }


    }

    /*
    get the list of records fro given file
     */
    @Override
    public List<TransactionModel> findByFileName(String fileName) {
        try {
            CriteriaQuery<TransactionModel> criteriaQuery = em.getCriteriaBuilder().createQuery(TransactionModel.class);
            Root<TransactionModel> root = criteriaQuery.from(TransactionModel.class);
            return em.createQuery(criteriaQuery).getResultList();
        }
        catch (RuntimeException e) {
            logger.error("Exception while creating JPA query", e);
            throw e;
        }
    }

    /*
    This method updates the invalid entries table
     */
    @Override
    public void uploadInvalidNativeQuery(StringBuilder stringBuilder) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LOCAL_PERSISTENCE");
        em = emf.createEntityManager();

        stringBuilder.insert(0, "insert into TRANSACTION_TABLE_INVALID (ID, DATA, FILE_NAME) values ");

        try {
            em.getTransaction().begin();
            em.createNativeQuery(stringBuilder.toString()).executeUpdate();
            em.getTransaction().commit();

        } catch (Exception e) {
            logger.error("Exception while processing transaction ", e);
            throw e;
        } finally {
            em.close();
            emf.close();
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Transaction committed");
        }


    }

    /*
    Returns no of records for given file name
     */
    @Override
    public long getRecordCountForFileName(String fileName) {

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);
            Root<TransactionModel> r = query.from(TransactionModel.class);
            query.select(cb.count(r)).where(cb.equal(r.get("fileName"), fileName));
            long rs = em.createQuery(query).getSingleResult();

            return rs;
        } catch (RuntimeException e) {
            logger.error("Exception while creating JPA query", e);
            throw e;
        }


    }

    /*
    Returns distinct file count
     */
    @Override
    public List<String> listOfDistinctFile() {

        List<String> result = new ArrayList<String>();
        try {

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<String> query = cb.createQuery(String.class);
            Root<TransactionModel> r = query.from(TransactionModel.class);
            query.multiselect(r.get("fileName")).distinct(true);
            TypedQuery<String> q = em.createQuery(query);

            result = q.getResultList();

        }
        catch (RuntimeException e) {
            logger.error("Exception while creating JPA query", e);
            throw e;
        }

        return result;
    }
}
