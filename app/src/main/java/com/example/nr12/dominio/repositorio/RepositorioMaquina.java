package com.example.nr12.dominio.repositorio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.nr12.R;
import com.example.nr12.adapter.MaquinaArrayAdapter;
import com.example.nr12.dominio.entidades.Cliente;
import com.example.nr12.dominio.entidades.Maquina;
import com.example.nr12.dominio.entidades.TipoMaquina;


/**
 * Created by Fabiano on 05/10/2017.
 */

public class RepositorioMaquina {

    private SQLiteDatabase conn;

    public RepositorioMaquina(SQLiteDatabase conn){ this.conn = conn; }

    private ContentValues preencheContentValues(Maquina maquina){

        ContentValues values = new ContentValues();
        values.put(Maquina.IDEXT, maquina.getId());
        values.put(Maquina.NOME, maquina.getNome());
        values.put(Maquina.CLIENTEID, maquina.getClienteId());
        values.put(Maquina.TIPOMAQUINAID, maquina.getTipoMaquinaId());
        values.put(Maquina.MODELO, maquina.getModelo());
        values.put(Maquina.NUMEROSERIE, maquina.getNumeroSerie());
        values.put(Maquina.NUMEROPATRIMONIO, maquina.getNumeroPatrimonio());
        values.put(Maquina.CAPACIDADE, maquina.getCapacidade());
        values.put(Maquina.OPERADORES, maquina.getOperadores());
        values.put(Maquina.ANO, maquina.getAno());
        values.put(Maquina.FABRICANTE, maquina.getFabricante());
        values.put(Maquina.SETOR, maquina.getSetor());

        return values;
    }

    private ContentValues preencheValues(Maquina maquina){

        ContentValues values = new ContentValues();
        values.put(Maquina.NOME, maquina.getNome());
        values.put(Maquina.CLIENTEID, maquina.getCliente().getId());
        values.put(Maquina.TIPOMAQUINAID, maquina.getTipoMaquina().getId());
        values.put(Maquina.MODELO, maquina.getModelo());
        values.put(Maquina.NUMEROSERIE, maquina.getNumeroSerie());
        values.put(Maquina.NUMEROPATRIMONIO, maquina.getNumeroPatrimonio());
        values.put(Maquina.CAPACIDADE, maquina.getCapacidade());
        values.put(Maquina.OPERADORES, maquina.getOperadores());
        values.put(Maquina.ANO, maquina.getAno());
        values.put(Maquina.FABRICANTE, maquina.getFabricante());
        values.put(Maquina.SETOR, maquina.getSetor());

        return values;
    }

    public MaquinaArrayAdapter buscaMaquinas(Context context, Cliente cliente) {
        MaquinaArrayAdapter adpMaquinas = new MaquinaArrayAdapter(context, R.layout.item_maquina);
        try {
            Cursor cursor = conn.query(Maquina.TABELA, null, " CLIENTEID = ? ", new String[]{String.valueOf(cliente.getId())}, null, null, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    Maquina maquina = new Maquina();

                    maquina.setCliente(cliente);

                    maquina.setIdLocal(cursor.getLong(cursor.getColumnIndexOrThrow(Maquina.IDLOCAL)));
                    maquina.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Maquina.IDEXT)));
                    maquina.setNome(cursor.getString(cursor.getColumnIndexOrThrow(Maquina.NOME)));
                    maquina.setModelo(cursor.getString(cursor.getColumnIndexOrThrow(Maquina.MODELO)));
                    maquina.setNumeroSerie(cursor.getString(cursor.getColumnIndexOrThrow(Maquina.NUMEROSERIE)));
                    maquina.setNumeroPatrimonio(cursor.getString(cursor.getColumnIndexOrThrow(Maquina.NUMEROPATRIMONIO)));
                    maquina.setCapacidade(cursor.getString(cursor.getColumnIndexOrThrow(Maquina.CAPACIDADE)));
                    maquina.setAno(cursor.getInt(cursor.getColumnIndexOrThrow(Maquina.ANO)));
                    maquina.setFabricante(cursor.getString(cursor.getColumnIndexOrThrow(Maquina.FABRICANTE)));
                    maquina.setSetor(cursor.getString(cursor.getColumnIndexOrThrow(Maquina.SETOR)));

                    TipoMaquina tipoMaquina = new TipoMaquina();
                    tipoMaquina.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Maquina.TIPOMAQUINAID)));
                    maquina.setTipoMaquina(tipoMaquina);

                    adpMaquinas.add(maquina);
                } while (cursor.moveToNext());
            }
        }catch (Exception ex){
            Log.i("INFO", "Erro ao consultar registro no banco: "+ex.getMessage());
        }
        return adpMaquinas;
    }

    public Maquina buscaMaquinaId(long id) {
        Maquina maquina = new Maquina();

        try {
            Cursor cursor = conn.query(Maquina.TABELA, null, " IDLOCAL = ? ", new String[]{String.valueOf(id)}, null, null, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                maquina.setIdLocal(cursor.getLong(cursor.getColumnIndexOrThrow(Maquina.IDLOCAL)));
                maquina.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Maquina.IDEXT)));
                maquina.setNome(cursor.getString(cursor.getColumnIndexOrThrow(Maquina.NOME)));
                maquina.setModelo(cursor.getString(cursor.getColumnIndexOrThrow(Maquina.MODELO)));
                maquina.setNumeroSerie(cursor.getString(cursor.getColumnIndexOrThrow(Maquina.NUMEROSERIE)));
                maquina.setNumeroPatrimonio(cursor.getString(cursor.getColumnIndexOrThrow(Maquina.NUMEROPATRIMONIO)));
                maquina.setCapacidade(cursor.getString(cursor.getColumnIndexOrThrow(Maquina.CAPACIDADE)));
                maquina.setAno(cursor.getInt(cursor.getColumnIndexOrThrow(Maquina.ANO)));
                maquina.setFabricante(cursor.getString(cursor.getColumnIndexOrThrow(Maquina.FABRICANTE)));
                maquina.setSetor(cursor.getString(cursor.getColumnIndexOrThrow(Maquina.SETOR)));

                TipoMaquina tipoMaquina = new TipoMaquina();
                tipoMaquina.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Maquina.TIPOMAQUINAID)));
                maquina.setTipoMaquina(tipoMaquina);

                Cliente cliente = new Cliente();
                RepositorioCliente repositorioCliente = new RepositorioCliente(this.conn);
                cliente = repositorioCliente.buscarCliente(cursor.getLong(cursor.getColumnIndexOrThrow(Maquina.CLIENTEID)));

                maquina.setCliente(cliente);

                return maquina;
            }
        }catch (Exception ex){
            Log.i("INFO", "Erro ao consultar registro no banco: "+ex.getMessage());
        }

        return null;
    }

    public void inserir(Maquina maquina){
        try{
            ContentValues values = preencheValues(maquina);
            conn.insertOrThrow(Maquina.TABELA, null, values);
            //conn.insertWithOnConflict(Maquina.TABELA, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        }catch(SQLException ex){
            Log.i("INFO", "Erro ao cadastrar registro no banco: "+ex.getMessage());
        }
    }

/*
    public void alterar(Maquina maquina){
        try{
            ContentValues values = preencheContentValues(maquina);
            conn.update(Maquina.TABELA, values, " IDLOCAL = ? ", new String[]{ String.valueOf(maquina.getIdLocal()) });
        }catch(Exception ex){
            Log.i("INFO", "Erro ao alterar registro no banco: "+ex.getMessage());
        }
    }
*/

    public void inserirOuAlterar(Maquina maquina){
        try{
            ContentValues values = preencheContentValues(maquina);
            int u = conn.update(Maquina.TABELA, values, " "+Maquina.IDEXT +" = ? ", new String[]{ String.valueOf(maquina.getId()) });
            if (u == 0){
                conn.insertWithOnConflict(Maquina.TABELA, null, values, SQLiteDatabase.CONFLICT_IGNORE);
            }
        }catch(Exception ex){
            Log.i("INFO", "Erro ao alterar registro no banco: "+ex.getMessage());
        }
    }


}
