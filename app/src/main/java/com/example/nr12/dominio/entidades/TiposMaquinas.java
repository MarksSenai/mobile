package com.example.nr12.dominio.entidades;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Gui on 24/09/2017.
 */

public class TiposMaquinas implements Serializable {

    private List<TipoMaquina> data;

    public List<TipoMaquina> getListTipoMaquina() {
        return data;
    }

    public void setListTipoMaquina(List<TipoMaquina> data) {
        this.data = data;
    }
}
