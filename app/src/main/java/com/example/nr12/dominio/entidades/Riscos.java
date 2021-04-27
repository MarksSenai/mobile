package com.example.nr12.dominio.entidades;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Gui on 24/09/2017.
 */

public class Riscos implements Serializable {

    private List<Risco> data;

    public List<Risco> getRiscosList() {
        return data;
    }

    public void setRiscosList(List<Risco> riscosList) {
        this.data = riscosList;
    }
}
