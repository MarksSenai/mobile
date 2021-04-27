package com.example.nr12.dominio.entidades;

import java.io.Serializable;

/**
 * Created by Fabiano on 24/09/2017.
 */

public class Cliente implements Serializable {

    public static String TABELA = "CLIENTE";
    public static String ID = "ID";
    public static String NOME = "NOME";
    public static String CNPJ = "CNPJ";
    public static String ENDERECO = "ENDERECO";
    public static String NUMERO = "NUMERO";
    public static String BAIRRO = "BAIRRO";
    public static String CIDADE = "CIDADE";
    public static String ESTADO = "ESTADO";
    public static String CEP = "CEP";
    public static String TELEFONE = "TELEFONE";
    public static String EMAIL = "EMAIL";

    private long id;
    private String nome;
    private String cnpj;
    private String endereco;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String telefone;
    private String email;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
