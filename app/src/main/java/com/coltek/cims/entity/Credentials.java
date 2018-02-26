package com.coltek.cims.entity;


/**
 * Created by BraDev on 11/9/2017.
 */

public final class Credentials {
    String username,
            password;

    public Credentials(String studentID, String mPassword) {
        username=studentID;
        password=mPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setCredentials(String username,String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
//        return super.toString();
        return username+" "+password;
    }
}
