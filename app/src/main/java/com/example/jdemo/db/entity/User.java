package com.example.jdemo.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "user_account")
    private String account;

    @ColumnInfo(name = "user_pwd")
    private String pwd;

    @ColumnInfo(name = "user_name")
    private String name;

    @ColumnInfo(name = "user_url")
    private String headImage;

    public User(String account, String pwd, String name, String headImage) {
        this.account = account;
        this.pwd = pwd;
        this.name = name;
        this.headImage = headImage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }
}
