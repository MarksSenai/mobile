package com.example.nr12.dominio.entidades;

/**
 * Created by Gui on 28/09/2017.
 */

public class Config {

    public static String TABELA = "CONFIG";
    public static String ID = "ID";
    public static String URL = "URL";
    public static String EMAIL = "EMAIL";
    public static String SENHA = "SENHA";

    private long id;
    private String url;
    private String email;
    private String senha;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
