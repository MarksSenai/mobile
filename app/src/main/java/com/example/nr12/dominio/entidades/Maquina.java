package com.example.nr12.dominio.entidades;

import java.io.Serializable;

/**
 * Created by Fabiano on 24/09/2017.
 */

public class Maquina implements Serializable {

    public static String TABELA             = "MAQUINA";
    public static String IDLOCAL            = "IDLOCAL";
    public static String IDEXT              = "IDEXT";
    public static String CLIENTEID          = "CLIENTEID";
    public static String TIPOMAQUINAID      = "TIPOMAQUINAID";
    public static String NOME               = "NOME";
    public static String MODELO             = "MODELO";
    public static String NUMEROSERIE        = "NUMEROSERIE";
    public static String NUMEROPATRIMONIO   = "NUMEROPATRIMONIO";
    public static String CAPACIDADE         = "CAPACIDADE";
    public static String OPERADORES         = "OPERADORES";
    public static String ANO                = "ANO";
    public static String FABRICANTE         = "FABRICANTE";
    public static String SETOR              = "SETOR";

    private int id;
    private long idLocal;
    private Cliente cliente;
    private int clienteId;
    private int tipoMaquinaId;
    private TipoMaquina tipoMaquina;
    private String nome;
    private String modelo;
    private String numeroSerie;
    private String numeroPatrimonio;
    private String capacidade;
    private int operadores;
    private int ano;
    private String fabricante;
    private String setor;

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getTipoMaquinaId() {
        return tipoMaquinaId;
    }

    public void setTipoMaquinaId(int tipoMaquinaId) {
        this.tipoMaquinaId = tipoMaquinaId;
    }

    public long getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(long idLocal) {
        this.idLocal = idLocal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public TipoMaquina getTipoMaquina() {
        return tipoMaquina;
    }

    public void setTipoMaquina(TipoMaquina tipoMaquina) {
        this.tipoMaquina = tipoMaquina;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getNumeroPatrimonio() {
        return numeroPatrimonio;
    }

    public void setNumeroPatrimonio(String numeroPatrimonio) {
        this.numeroPatrimonio = numeroPatrimonio;
    }

    public String getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(String capacidade) {
        this.capacidade = capacidade;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public int getOperadores() {
        return operadores;
    }

    public void setOperadores(int operadores) {
        this.operadores = operadores;
    }

    public String toString(){
        return nome;
    }
}
