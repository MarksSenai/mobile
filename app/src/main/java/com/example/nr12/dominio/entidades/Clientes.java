package com.example.nr12.dominio.entidades;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Gui on 24/09/2017.
 */

public class Clientes implements Serializable {

    private List<Cliente> data;

    public List<Cliente> getClientesList() {
        return data;
    }

    public void setClientesList(List<Cliente> clientesList) {
        this.data = clientesList;
    }
}
