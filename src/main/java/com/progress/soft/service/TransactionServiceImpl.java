package com.progress.soft.service;

import com.progress.soft.service.TransactionOperationService;
import com.progress.soft.dao.TransactionDao;
import com.progress.soft.model.TransactionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by inshaf on 10/19/17.
 */

@Service("TransactionServiceImpl")
public class TransactionServiceImpl implements TransactionOperationService {

    @Autowired
    private TransactionDao transactionDao;


    @Transactional
    @Override
    public void upload(TransactionModel transaction) {
        transactionDao.upload(transaction);
    }

    @Override
    public void upload(List<TransactionModel> transactionModelList) {
        transactionDao.upload(transactionModelList);
    }

    @Override
    public void uploadNativeQuery(List<TransactionModel> transactionModelList) {
        transactionDao.uploadNativeQuery(transactionModelList);
    }

    @Override
    public void uploadNativeQuery(StringBuilder stringBuilder) {
        transactionDao.uploadNativeQuery(stringBuilder);
    }

    @Transactional
    @Override
    public List<TransactionModel> findByFileName(String fileName) {
        return transactionDao.findByFileName(fileName);
    }

    @Override
    public void uploadInvalidNativeQuery(StringBuilder stringBuilder) {
        transactionDao.uploadInvalidNativeQuery(stringBuilder);
    }

    @Override
    public long getRecordCountForFileName(String fileName) {
        return transactionDao.getRecordCountForFileName(fileName);
    }

    @Override
    public List<String> listOfDistinctFile() {
        return transactionDao.listOfDistinctFile();
    }


}
