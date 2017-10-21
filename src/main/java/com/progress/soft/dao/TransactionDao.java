package com.progress.soft.dao;

import com.progress.soft.model.TransactionModel;

import java.util.List;

/**
 * Created by inshaf on 10/19/17.
 */
public interface TransactionDao {

    void upload(TransactionModel transaction);

    void upload(List<TransactionModel> transactionModelList);

    void uploadNativeQuery(List<TransactionModel> transactionModelList);

    void uploadNativeQuery(StringBuilder stringBuilder);

    List<TransactionModel> findByFileName(String fileName);

    void uploadInvalidNativeQuery(StringBuilder stringBuilder);

    long getRecordCountForFileName(String fileName);

    List<String> listOfDistinctFile();
}
