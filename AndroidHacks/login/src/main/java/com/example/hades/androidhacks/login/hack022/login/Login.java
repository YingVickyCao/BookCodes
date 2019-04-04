/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.example.hades.androidhacks.login.hack022.login;

public class Login {

    public boolean login(String user, String passwd) {
        if (user.equals("admin") && passwd.equals("admin")) {
            return true;
        } else {
            return false;
        }
    }
}
