package com.example.nr12.dominio.entidades;

import java.io.Serializable;

/**
 * Created by Gui on 28/09/2017.
 */

public class TipoMaquina implements Serializable {

    public static String TABELA = "TIPOMAQUINA";
    public static String ID = "ID";
    public static String NOME = "NOME";

    private int id;
    private String nome;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String toString(){
        return nome;
    }
}
