package com.coltek.cims.ui.dashboard;

/**
 * Created by BraDev ${LOCALE} on 1/14/2018.
 */

public interface FragCommunicable {
    void changeEmail(String email);
    void makeText(String msg);

    void showEditProfileFragment(String email);
}
