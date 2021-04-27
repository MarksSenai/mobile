package com.example.nr12.dominio.entidades;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Gui on 24/09/2017.
 */

public class Maquinas implements Serializable {

    private List<Maquina> data;

    public List<Maquina> getListMaquina() {
        return data;
    }

    public void setListMaquina(List<Maquina> data) {
        this.data = data;
    }
}
