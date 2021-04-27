package com.example.nr12.dominio.entidades;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Gui on 24/09/2017.
 */

public class Perigos implements Serializable {

    private List<Perigo> data;

    public List<Perigo> getPerigosList() {
        return data;
    }

    public void setPerigosList(List<Perigo> perigosList) {
        this.data = perigosList;
    }
}
