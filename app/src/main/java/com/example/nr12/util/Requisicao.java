package com.example.nr12.util;

/**
 * Created by Fabiano on 22/09/2017.
 */

public class Requisicao {

    public static String token;
    public static String status;
    public static String message;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Requisicao.token = token;
    }

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status) {
        Requisicao.status = status;
    }

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        Requisicao.message = message;
    }
}
