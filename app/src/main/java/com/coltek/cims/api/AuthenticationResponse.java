package com.coltek.cims.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.coltek.cims.entity.Student;

public class AuthenticationResponse {
    private String message;
    private boolean error;
    private Student student;

    public AuthenticationResponse(@NonNull boolean error, @NonNull String message,@Nullable Student student) {
        this.error = error;
        this.student = student;
        this.message = message;
    }

    @Override
    public String toString() {
         super.toString();
         return this.message+" "+this.error;
    }

    public Student getStudent() {
        return student;
    }

    public String getMessage() {
        return message;
    }

    public boolean isError() {
        return error;
    }
}
