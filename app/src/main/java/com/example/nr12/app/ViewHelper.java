package com.example.nr12.app;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


/**
 * Created by Fabiano on 22/09/2017.
 */

public class ViewHelper {

    public static ArrayAdapter<String> createArrayAdapter(Context ctx, Spinner spiner){

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spiner.setAdapter(arrayAdapter);

        return arrayAdapter;
    }

    public static ArrayAdapter<String> createArrayAdapter(Context ctx, Spinner spiner, int textArrayResId){

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_dropdown_item, ctx.getResources().getStringArray(textArrayResId));
        arrayAdapter.createFromResource(ctx, textArrayResId, android.R.layout.simple_spinner_dropdown_item);

        spiner.setAdapter(arrayAdapter);

        return arrayAdapter;
    }
}
