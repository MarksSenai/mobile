package com.example.nr12.database;

import android.util.Log;

import com.example.nr12.dominio.entidades.Maquina;

/**
 * Created by Fabiano on 25/09/2017.
 */

public class ScriptSQL {

    public static String getCreateConfig(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS CONFIG ( ");
        sqlBuilder.append("ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ");
        sqlBuilder.append("URL VARCHAR (45), ");
        sqlBuilder.append("EMAIL VARCHAR (45) NOT NULL, ");
        sqlBuilder.append("SENHA VARCHAR (45) NOT NULL ");
        sqlBuilder.append(");");
        Log.i("INFO", sqlBuilder.toString());
        return sqlBuilder.toString();
    }

    public static String getCreateRisco(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS RISCO ( ");
        sqlBuilder.append("ID INTEGER PRIMARY KEY NOT NULL, ");
        sqlBuilder.append("NOME VARCHAR (45) NOT NULL ");
        sqlBuilder.append(");");
        Log.i("INFO", sqlBuilder.toString());
        return sqlBuilder.toString();
    }

    public static String getCreatePerigo(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS PERIGO ( ");
        sqlBuilder.append("ID INTEGER PRIMARY KEY NOT NULL, ");
        sqlBuilder.append("NOME VARCHAR (45) NOT NULL ");
        sqlBuilder.append(");");
        Log.i("INFO", sqlBuilder.toString());
        return sqlBuilder.toString();
    }

    public static String getCreatePerigoRisco() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS PERIGORISCO ( ");
        sqlBuilder.append("PERIGO INTEGER NOT NULL REFERENCES PERIGO (ID) ON DELETE NO ACTION ON UPDATE NO ACTION, ");
        sqlBuilder.append("RISCO INTEGER NOT NULL REFERENCES RISCO (ID) ON DELETE NO ACTION ON UPDATE NO ACTION, ");
        sqlBuilder.append("PRIMARY KEY (PERIGO,RISCO) ");
        sqlBuilder.append(");");
        Log.i("INFO", sqlBuilder.toString());
        return  sqlBuilder.toString();
    }

    public static String getCreateTipoMaquina(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS TIPOMAQUINA ( ");
        sqlBuilder.append("ID INTEGER PRIMARY KEY NOT NULL, ");
        sqlBuilder.append("NOME VARCHAR (200) NOT NULL ");
        sqlBuilder.append(");");
        Log.i("INFO", sqlBuilder.toString());
        return sqlBuilder.toString();
    }

    public static String getCreateSistemaSeguranca(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS SISTEMASEGURANCA ( ");
        sqlBuilder.append("ID INTEGER PRIMARY KEY NOT NULL, ");
        sqlBuilder.append("NOME VARCHAR (100) NOT NULL ");
        sqlBuilder.append(");");
        Log.i("INFO", sqlBuilder.toString());
        return sqlBuilder.toString();
    }

    public static String getCreateCliente(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS CLIENTE (");
        sqlBuilder.append("ID INTEGER NOT NULL PRIMARY KEY, ");
        sqlBuilder.append("NOME VARCHAR (45), ");
        sqlBuilder.append("CNPJ VARCHAR (14) UNIQUE NOT NULL, ");
        sqlBuilder.append("ENDERECO VARCHAR (60), ");
        sqlBuilder.append("NUMERO INTEGER, ");
        sqlBuilder.append("BAIRRO VARCHAR (45), ");
        sqlBuilder.append("CIDADE VARCHAR (45), ");
        sqlBuilder.append("ESTADO VARCHAR (2), ");
        sqlBuilder.append("CEP VARCHAR (8), ");
        sqlBuilder.append("TELEFONE VARCHAR (11), ");
        sqlBuilder.append("EMAIL VARCHAR (60) ");
        sqlBuilder.append(");");
        Log.i("INFO", sqlBuilder.toString());
        return sqlBuilder.toString();
    }

    public static String getCreateMaquina(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS " + Maquina.TABELA + " (");
        sqlBuilder.append(Maquina.IDLOCAL           + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ");
        sqlBuilder.append(Maquina.IDEXT             + " INTEGER, ");
        sqlBuilder.append(Maquina.CLIENTEID         + " INTEGER NOT NULL REFERENCES CLIENTE (ID) ON DELETE NO ACTION ON UPDATE NO ACTION, ");
        sqlBuilder.append(Maquina.TIPOMAQUINAID     + " INTEGER NOT NULL REFERENCES TIPOMAQUINA (ID) ON DELETE NO ACTION ON UPDATE NO ACTION, ");
        sqlBuilder.append(Maquina.NOME              + " VARCHAR (45) NOT NULL, ");
        sqlBuilder.append(Maquina.MODELO            + " VARCHAR (45), ");
        sqlBuilder.append(Maquina.NUMEROSERIE       + " VARCHAR (45), ");
        sqlBuilder.append(Maquina.NUMEROPATRIMONIO  + " VARCHAR (45), ");
        sqlBuilder.append(Maquina.CAPACIDADE        + " VARCHAR (15), ");
        sqlBuilder.append(Maquina.OPERADORES        + " INTEGER, ");
        sqlBuilder.append(Maquina.ANO               + " INTEGER, ");
        sqlBuilder.append(Maquina.FABRICANTE        + " VARCHAR (45), ");
        sqlBuilder.append(Maquina.SETOR             + " VARCHAR (45) ");
        sqlBuilder.append(");");
        Log.i("INFO", sqlBuilder.toString());
        return sqlBuilder.toString();
    }

    public static String getCreateLaudo(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS LAUDO (");
        sqlBuilder.append("ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ");
        sqlBuilder.append("MAQUINAID INTEGER NOT NULL REFERENCES MAQUINA (ID) ON DELETE NO ACTION ON UPDATE NO ACTION, ");
        sqlBuilder.append("STATUS VARCHAR (45) NOT NULL, ");
        sqlBuilder.append("DATAINICIAL DATE, ");
        sqlBuilder.append("DATAFINAL DATE ");
        sqlBuilder.append(");");
        Log.i("INFO", sqlBuilder.toString());
        return sqlBuilder.toString();
    }

    public static String getCreatePontoPerigo(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS PONTOPERIGO ( ");
        sqlBuilder.append("ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, ");
        sqlBuilder.append("LAUDOID INTEGER NOT NULL REFERENCES LAUDO (ID) ON DELETE CASCADE ON UPDATE CASCADE, ");
        sqlBuilder.append("PONTOPERIGO VARCHAR(100) NOT NULL, ");
        sqlBuilder.append("FACE VARCHAR (45) NOT NULL, ");
        sqlBuilder.append("SISTEMASEGURANCAID INTEGER REFERENCES SISTEMASEGURANCA (ID) ON DELETE NO ACTION ON UPDATE NO ACTION, ");
        sqlBuilder.append("ANEXO1 CHAR (1) NOT NULL ");
        sqlBuilder.append(");");
        Log.i("INFO", sqlBuilder.toString());
        return sqlBuilder.toString();
    }

    public static String getCreatePerigoPontoPerigo(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS PERIGOPONTOPERIGO ( ");
        sqlBuilder.append("PERIGOID INTEGER REFERENCES PERIGO (ID) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL, ");
        sqlBuilder.append("PONTOPERIGOID INTEGER REFERENCES PONTOPERIGO (ID) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL, ");
        sqlBuilder.append("PRIMARY KEY (PERIGOID,PONTOPERIGOID) ");
        sqlBuilder.append(");");
        Log.i("INFO", sqlBuilder.toString());
        return sqlBuilder.toString();
    }

    public static String getCreateRiscoPontoPerigo(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS RISCOPONTOPERIGO ( ");
        sqlBuilder.append("RISCOID INTEGER REFERENCES RISCO (ID) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL, ");
        sqlBuilder.append("PONTOPERIGOID INTEGER REFERENCES PONTOPERIGO (ID) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL, ");
        sqlBuilder.append("PRIMARY KEY (RISCOID,PONTOPERIGOID) ");
        sqlBuilder.append(");");
        Log.i("INFO", sqlBuilder.toString());
        return sqlBuilder.toString();
    }

}
