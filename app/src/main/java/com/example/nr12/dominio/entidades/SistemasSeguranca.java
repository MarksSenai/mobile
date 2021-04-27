package com.example.nr12.dominio.entidades;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Gui on 24/09/2017.
 */

public class SistemasSeguranca implements Serializable {

    private List<SistemaSeguranca> data;

    public List<SistemaSeguranca> getSistemasList() {
        return data;
    }

    public void setSistemasList(List<SistemaSeguranca> sistemasList) {
        this.data = sistemasList;
    }
}
