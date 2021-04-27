package com.example.nr12.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Fabiano on 25/09/2017.
 */

public class DataBase extends SQLiteOpenHelper{

    public DataBase(Context context){
        super(context, "NR12", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ScriptSQL.getCreateConfig());
        db.execSQL(ScriptSQL.getCreateRisco());
        db.execSQL(ScriptSQL.getCreatePerigo());
        db.execSQL(ScriptSQL.getCreatePerigoRisco());
        db.execSQL(ScriptSQL.getCreateTipoMaquina());
        db.execSQL(ScriptSQL.getCreateSistemaSeguranca());
        db.execSQL(ScriptSQL.getCreateCliente());
        db.execSQL(ScriptSQL.getCreateMaquina());
        db.execSQL(ScriptSQL.getCreateLaudo());
        db.execSQL(ScriptSQL.getCreatePontoPerigo());
        db.execSQL(ScriptSQL.getCreatePerigoPontoPerigo());
        db.execSQL(ScriptSQL.getCreateRiscoPontoPerigo());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
