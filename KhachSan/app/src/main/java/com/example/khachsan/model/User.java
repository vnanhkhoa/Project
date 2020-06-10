package com.example.khachsan.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String fullName;
    private String userName;
    private String passWord;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    private String error;

    public User(String id, String fullName, String userName, String passWord, String error) {
        this.id = id;
        this.fullName = fullName;
        this.userName = userName;
        this.passWord = passWord;
        this.error = error;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @NonNull
    @Override
    public String toString() {
        if (this.error == null){
            return this.id+"\n"+this.fullName+"\n"+this.userName+"\n"+this.passWord;
        }
        else{
            return this.error;
        }
    }
}
