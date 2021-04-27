package com.example.nr12.dominio.entidades;

import java.io.Serializable;

/**
 * Created by root on 10/23/17.
 */

public class PerigoRisco implements Serializable {

    public static String TABELA = "PERIGORISCO";
    public static String PERIGO = "PERIGO";
    public static String RISCO = "RISCO";


    private int perigo;
    private int risco;

    public static String getTABELA() {
        return TABELA;
    }

    public static void setTABELA(String TABELA) {
        PerigoRisco.TABELA = TABELA;
    }

    public static String getPERIGO() {
        return PERIGO;
    }

    public static void setPERIGO(String PERIGO) {
        PerigoRisco.PERIGO = PERIGO;
    }

    public static String getRISCO() {
        return RISCO;
    }

    public static void setRISCO(String RISCO) {
        PerigoRisco.RISCO = RISCO;
    }

    public int getPerigo() {
        return perigo;
    }

    public void setPerigo(int perigo) {
        this.perigo = perigo;
    }

    public int getRisco() {
        return risco;
    }

    public void setRisco(int risco) {
        this.risco = risco;
    }

}
