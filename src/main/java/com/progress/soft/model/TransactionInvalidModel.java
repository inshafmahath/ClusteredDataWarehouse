package com.progress.soft.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by inshaf on 10/19/17.
 */

@Entity
@Table(name = "TRANSACTION_TABLE_INVALID")
public class TransactionInvalidModel implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", unique = true)
    private long id;

    @Column(name = "DATA")
    private String data;

    @Column(name = "FILE_NAME")
    private String fileName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public TransactionInvalidModel(long id, String data, String fileName) {
        this.id = id;
        this.data = data;
        this.fileName = fileName;
    }

    public TransactionInvalidModel() {}

}
