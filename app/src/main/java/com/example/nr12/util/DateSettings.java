package com.example.nr12.util;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.Toast;


/**
 * Created by Fabiano on 22/09/2017.
 */

public class DateSettings implements DatePickerDialog.OnDateSetListener {

    Context context;
    public DateSettings(Context context){
        this.context = context;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Toast.makeText(context, "Data selecionada"+dayOfMonth+"/"+month+"/"+year, Toast.LENGTH_LONG).show();
    }
}
