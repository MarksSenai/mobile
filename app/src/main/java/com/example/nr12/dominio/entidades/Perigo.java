package com.example.nr12.dominio.entidades;


import java.io.Serializable;
import java.util.List;


/**private List<Risco> Riscos;
 * Created by Gui on 28/09/2017.
 */

public class Perigo implements Serializable {

    public static String TABELA = "PERIGO";
    public static String ID = "ID";
    public static String NOME = "NOME";

    private int id;
    private String nome;
    private List<Risco> Riscos;

    public List<Risco> getRiscos() {
        return Riscos;
    }

    public void setRiscos(List<Risco> riscos) {
        this.Riscos = riscos;
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
