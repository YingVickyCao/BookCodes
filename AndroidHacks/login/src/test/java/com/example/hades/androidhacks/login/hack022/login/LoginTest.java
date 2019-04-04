package com.example.hades.androidhacks.login.hack022.login;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by hades on 23/03/2018.
 */
public class LoginTest {

    @Test
    public void LoginShouldReturnTrueWhenCredentialsAreOk() {
        Login login = new Login();
        Assert.assertEquals(true, login.login("admin", "admin"));
    }

    @Test
    public void LoginShouldReturnFalseWhenCredentialsAreNotOk() {
        Login login = new Login();
        Assert.assertEquals(false, login.login("user", "wrongPass"));
    }
}