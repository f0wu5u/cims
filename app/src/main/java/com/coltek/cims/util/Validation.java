package com.coltek.cims.util;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by BraDev on 11/2/2017.
 */

public final class Validation {
    protected static final Pattern emailPattern = Pattern.compile("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$",Pattern.CASE_INSENSITIVE);

    public static boolean isValidIndex(String student_id) {
        return TextUtils.isDigitsOnly(student_id) && student_id.length() == 10;
    }
    public static boolean isValidNumber(String number) {

        return (number.startsWith("020") ||
                number.startsWith("023") ||
                number.startsWith("024") ||
                number.startsWith("026") ||
                number.startsWith("027") ||
                number.startsWith("05")) && isValidIndex(number);
    }
    public static boolean isValidPassword(String password) {
        return !isEmpty(password);
    }


    public static boolean isEmpty(String string) {
        return TextUtils.isEmpty(string);
    }

    public static boolean isEmail(String email) {
        return emailPattern.matcher(email).matches();
    }

}
