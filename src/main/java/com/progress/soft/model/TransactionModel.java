package com.progress.soft.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by inshaf on 10/19/17.
 */

@Entity
@Table(name = "TRANSACTION_TABLE")
public class TransactionModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", unique = true)
    private long id;

    @Column(name = "FROM_CURRENCY")
    private String fromCurrency;

    @Column(name = "TO_CURRENCY")
    private String toCurrency;

    @Column(name = "TIMESTAMP")
    private String timestamp;

    @Column(name = "AMOUNT")
    private double amount;

    @Column(name = "FILE_NAME")
    private String fileName;

    public TransactionModel(long id, String fromCurrency, String toCurrency, String timestamp, double amount, String fileName) {
        this.id = id;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.timestamp = timestamp;
        this.amount = amount;
        this.fileName = fileName;
    }

    public TransactionModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
