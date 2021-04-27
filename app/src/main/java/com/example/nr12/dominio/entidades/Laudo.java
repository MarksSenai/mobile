package com.example.nr12.dominio.entidades;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * Created by Fabiano on 24/09/2017.
 */

public class Laudo implements Serializable {

    public static String TABELA = "LAUDO";
    public static String ID = "ID";
    public static String MAQUINAID = "MAQUINAID";
    public static String STATUS = "STATUS";
    public static String DATAINICIAL = "DATAINICIAL";
    //public static String DATAFINAL = "DATAFINAL";

    public Long id;
    public Maquina maquina;
    public int maquinaId;
    public Date data_inicial;
    public Time hora;
    public String status;
    public List<PontoPerigo> pontoPerigos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }

    public int getMaquinaId() {
        return maquinaId;
    }

    public void setMaquinaId(int maquinaId) {
        this.maquinaId = maquinaId;
    }

    public Date getData_inicial() {
        return data_inicial;
    }

    public void setData_inicial(Date data_inicial) {
        this.data_inicial = data_inicial;
    }

    public List<PontoPerigo> getPontoPerigos() {
        return pontoPerigos;
    }

    public void setPontoPerigos(List<PontoPerigo> pontoPerigos) {
        this.pontoPerigos = pontoPerigos;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

}
