// AuthContext.java
package com.library.libraryclient.util;

public class AuthContext {
    private static String username;
    private static String password;

    public static void setCredentials(String user, String pass) {
        username = user;
        password = pass;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }
}

