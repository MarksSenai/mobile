package com.example.nr12.dominio.entidades;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Fabiano on 24/09/2017.
 */

public class PontoPerigo  implements Serializable {

    public static String TABELA = "PONTOPERIGO";
    public static String ID = "ID";
    public static String LAUDOID = "LAUDOID";
    public static String PONTOPERIGO = "PONTOPERIGO";
    public static String FACE = "FACE";
    public static String SISTEMASEGURANCAID = "SISTEMASEGURANCAID";
    public static String ANEXO1 = "ANEXO1";

    private long id;
    private long laudoId;
    private String pontoperigo;
    private String face;
    private int sistemaSegurancaId;
    private SistemaSeguranca sistemaseguranca;
    private List<Perigo> Perigos;
    private List<Risco> Riscos;
    private String anexo1;

    public String getPontoPerigo() {
        return pontoperigo;
    }

    public void setPontoPerigo(String pontoPerigo) {
        this.pontoperigo = pontoPerigo;
    }

    public int getSistemaSegurancaId(){
        return sistemaSegurancaId;
    }

    public void setSistemaSegurancaId(int sistemaSegurancaId){
        this.sistemaSegurancaId = sistemaSegurancaId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLaudoId() {
        return laudoId;
    }

    public void setLaudoId(long laudoId) {
        this.laudoId = laudoId;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public SistemaSeguranca getSistemaSeguranca() {
        return sistemaseguranca;
    }

    public void setSistemaSeguranca(SistemaSeguranca sistemaSeguranca) {
        this.sistemaseguranca = sistemaSeguranca;
    }

    public List<Perigo> getPerigos() {
        return Perigos;
    }

    public void setPerigos(List<Perigo> perigos) {
        Perigos = perigos;
    }

    public List<Risco> getRiscos() {
        return Riscos;
    }

    public void setRiscos(List<Risco> riscos) {
        Riscos = riscos;
    }

    public String getAnexo1() {
        return anexo1;
    }

    public void setAnexo1(String anexo1) {
        this.anexo1 = anexo1;
    }
}
