package com.example.nr12.dominio.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gui on 28/09/2017.
 */

public class Risco implements Serializable {

    public static String TABELA = "RISCO";
    public static String ID = "ID";
    public static String NOME = "NOME";

    private int id;
    private String nome;
    private ArrayList<Risco> Riscos;

    public ArrayList<Risco> getRiscos() {
        return Riscos;
    }

    public void setRiscos(ArrayList<Risco> riscos) {
        Riscos = riscos;
    }

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
