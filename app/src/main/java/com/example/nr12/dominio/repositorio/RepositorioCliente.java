package com.example.nr12.dominio.repositorio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.nr12.adapter.ClienteAdapter;
import com.example.nr12.R;
import com.example.nr12.dominio.entidades.Cliente;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Fabiano on 25/09/2017.
 */

public class RepositorioCliente {

    private SQLiteDatabase conn;

    public RepositorioCliente(SQLiteDatabase conn){ this.conn = conn; }

    private ContentValues preencheContentValues(Cliente cliente){
        ContentValues values = new ContentValues();
        values.put(Cliente.ID, cliente.getId());
        values.put(Cliente.NOME, cliente.getNome());
        values.put(Cliente.CNPJ, cliente.getCnpj());
        values.put(Cliente.ENDERECO, cliente.getEndereco());
        values.put(Cliente.NUMERO, cliente.getNumero());
        values.put(Cliente.BAIRRO, cliente.getBairro());
        values.put(Cliente.CIDADE, cliente.getCidade());
        values.put(Cliente.ESTADO, cliente.getEstado());
        values.put(Cliente.CEP, cliente.getCep());
        values.put(Cliente.TELEFONE, cliente.getTelefone());
        values.put(Cliente.EMAIL, cliente.getEmail());

        return values;
    }

    public void excluir(long id){
        try{
            conn.delete(Cliente.TABELA, " "+Cliente.ID+" = ? ", new String[]{ String.valueOf(id) });
        }catch(Exception ex){
            Log.i("INFO", "Erro ao excluir do banco: "+ex.getMessage());
        }
    }


    public void alterar(Cliente cliente){
        try{
            ContentValues values = preencheContentValues(cliente);
            conn.update(Cliente.TABELA, values, " "+Cliente.ID+" = ? ", new String[]{ String.valueOf(cliente.getId()) });
        }catch(Exception ex){
            Log.i("INFO", "Erro ao alterar registro no banco: "+ex.getMessage());
        }
    }

    public void inserir(Cliente cliente){
        try{
            ContentValues values = preencheContentValues(cliente);
            conn.insertOrThrow(Cliente.TABELA, null, values);
        }catch(Exception ex){
            Log.i("INFO", "Erro ao cadastrar registro no banco: "+ex.getMessage());
        }
    }

    public List<Cliente> buscarTodos(){
        List<Cliente> clientes= new ArrayList<Cliente>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ID, NOME, CNPJ, ENDERECO, NUMERO, BAIRRO, CIDADE, ESTADO, CEP, TELEFONE, EMAIL ");
        sql.append("    FROM CLIENTE ");
        Cursor resultado = conn.rawQuery(sql.toString(), null);
        if(resultado.getCount() > 0){
            resultado.moveToFirst();
            do{
                Cliente cliente = new Cliente();
                cliente.setId( resultado.getInt( resultado.getColumnIndexOrThrow(Cliente.ID) ) );
                cliente.setNome( resultado.getString( resultado.getColumnIndexOrThrow(Cliente.NOME) ) );
                cliente.setCnpj( resultado.getString( resultado.getColumnIndexOrThrow(Cliente.CNPJ) ) );
                cliente.setEndereco( resultado.getString( resultado.getColumnIndexOrThrow(Cliente.ENDERECO) ) );
                cliente.setNumero( resultado.getString( resultado.getColumnIndexOrThrow(Cliente.NUMERO) ) );
                cliente.setBairro( resultado.getString( resultado.getColumnIndexOrThrow(Cliente.BAIRRO) ) );
                cliente.setCidade( resultado.getString( resultado.getColumnIndexOrThrow(Cliente.CIDADE) ) );
                cliente.setEstado( resultado.getString( resultado.getColumnIndexOrThrow(Cliente.ESTADO) ) );
                cliente.setCep( resultado.getString( resultado.getColumnIndexOrThrow(Cliente.CEP) ) );
                cliente.setTelefone( resultado.getString( resultado.getColumnIndexOrThrow(Cliente.TELEFONE) ) );
                cliente.setEmail( resultado.getString( resultado.getColumnIndexOrThrow(Cliente.EMAIL) ) );

                clientes.add(cliente);

            }while (resultado.moveToNext());
        }
        return clientes;
    }

    public Cliente buscarCliente(long id){
        Cliente cliente = new Cliente();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ID, NOME, CNPJ, ENDERECO, NUMERO, BAIRRO, CIDADE, ESTADO, CEP, TELEFONE, EMAIL ");
        sql.append(" FROM CLIENTE ");
        sql.append(" WHERE ID = ? ");
        Cursor resultado = conn.rawQuery(sql.toString(), new String[]{ String.valueOf(id) });
        if(resultado.getCount() > 0){
            resultado.moveToFirst();
            cliente.setId( resultado.getInt( resultado.getColumnIndexOrThrow(Cliente.ID) ) );
            cliente.setNome( resultado.getString( resultado.getColumnIndexOrThrow(Cliente.NOME) ) );
            cliente.setCnpj( resultado.getString( resultado.getColumnIndexOrThrow(Cliente.CNPJ) ) );
            cliente.setEndereco( resultado.getString( resultado.getColumnIndexOrThrow(Cliente.ENDERECO) ) );
            cliente.setNumero( resultado.getString( resultado.getColumnIndexOrThrow(Cliente.NUMERO) ) );
            cliente.setBairro( resultado.getString( resultado.getColumnIndexOrThrow(Cliente.BAIRRO) ) );
            cliente.setCidade( resultado.getString( resultado.getColumnIndexOrThrow(Cliente.CIDADE) ) );
            cliente.setEstado( resultado.getString( resultado.getColumnIndexOrThrow(Cliente.ESTADO) ) );
            cliente.setCep( resultado.getString( resultado.getColumnIndexOrThrow(Cliente.CEP) ) );
            cliente.setTelefone( resultado.getString( resultado.getColumnIndexOrThrow(Cliente.TELEFONE) ) );
            cliente.setEmail( resultado.getString( resultado.getColumnIndexOrThrow(Cliente.EMAIL) ) );
            return cliente;
        }
        return null;
    }

    public ClienteAdapter buscarTodosClientesAdapter(Context context){
        ClienteAdapter adpClientes = new ClienteAdapter(context, R.layout.item_cliente);
        Cursor cursor = conn.query(Cliente.TABELA, null, null, null, null, null, null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                Cliente cliente = new Cliente();
                cliente.setId(cursor.getInt(cursor.getColumnIndex(Cliente.ID)));
                cliente.setNome(cursor.getString(cursor.getColumnIndex(Cliente.NOME)));
                cliente.setCnpj(cursor.getString(cursor.getColumnIndex(Cliente.CNPJ)));
                cliente.setEndereco(cursor.getString(cursor.getColumnIndex(Cliente.ENDERECO)));
                cliente.setNumero(cursor.getString(cursor.getColumnIndex(Cliente.NUMERO)));
                cliente.setBairro(cursor.getString(cursor.getColumnIndex(Cliente.BAIRRO)));
                cliente.setCidade(cursor.getString(cursor.getColumnIndex(Cliente.CIDADE)));
                cliente.setEstado(cursor.getString(cursor.getColumnIndex(Cliente.ESTADO)));
                cliente.setCep(cursor.getString(cursor.getColumnIndex(Cliente.CEP)));
                cliente.setTelefone(cursor.getString(cursor.getColumnIndex(Cliente.TELEFONE)));
                cliente.setEmail(cursor.getString(cursor.getColumnIndex(Cliente.EMAIL)));
                adpClientes.add(cliente);
            }while(cursor.moveToNext());
        }
        return adpClientes;
    }

    public void inserirOuAlterar(Cliente cliente){
        try{
            ContentValues values = preencheContentValues(cliente);
            int u = conn.update(Cliente.TABELA, values, " "+Cliente.ID+" = ? ", new String[]{ String.valueOf(cliente.getId()) });
            if (u == 0){
                conn.insertWithOnConflict(Cliente.TABELA, null, values, SQLiteDatabase.CONFLICT_IGNORE);
            }
        }catch(Exception ex){
            Log.i("INFO", "Erro ao alterar registro no banco: "+ex.getMessage());
        }
    }

    public ArrayAdapter buscaClientesOld(Context context){
        ArrayAdapter<Cliente> adpClientes = new ArrayAdapter<Cliente>(context, android.R.layout.simple_list_item_1);
        Cursor cursor = conn.query(Cliente.TABELA, null, null, null, null, null, null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                Cliente cliente = new Cliente();
                cliente.setId(cursor.getInt(cursor.getColumnIndex(Cliente.ID)));
                cliente.setNome(cursor.getString(cursor.getColumnIndex(Cliente.NOME)));
                cliente.setTelefone(cursor.getString(cursor.getColumnIndex(Cliente.TELEFONE)));
                cliente.setEmail(cursor.getString(cursor.getColumnIndex(Cliente.EMAIL)));
                cliente.setEndereco(cursor.getString(cursor.getColumnIndex(Cliente.ENDERECO)));
                adpClientes.add(cliente);
            }while(cursor.moveToNext());
        }
        return adpClientes;
    }
}
