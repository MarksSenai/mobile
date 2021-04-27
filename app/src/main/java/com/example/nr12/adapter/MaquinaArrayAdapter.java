package com.example.nr12.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nr12.R;
import com.example.nr12.dominio.entidades.Maquina;


/**
 * Created by Fabiano on 05/10/2017.
 */

public class MaquinaArrayAdapter extends ArrayAdapter<Maquina>{

    private int resource = 0;
    private LayoutInflater inflater;
    private Context context;

    public MaquinaArrayAdapter(Context context, int resource) {
        super(context, resource);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
        this.context = context;
    }

    static class ViewHolder{
        TextView txtMaquina;
        TextView txtTipoMaquina;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        ViewHolder viewHolder = null;

        if (convertView == null) {

            viewHolder = new ViewHolder();

            view = inflater.inflate(resource, parent, false);

            viewHolder.txtMaquina = (TextView)view.findViewById(R.id.txtMaquina);
            viewHolder.txtTipoMaquina = (TextView)view.findViewById(R.id.txtTipoMaquina);

            view.setTag(viewHolder);

            convertView = view;

        } else {
            viewHolder = (ViewHolder)convertView.getTag();
            view = convertView;
        }

        Maquina maquina = getItem(position);

        viewHolder.txtMaquina.setText(maquina.getNome());
        viewHolder.txtTipoMaquina.setText(maquina.getTipoMaquina().getNome());

        return view;
    }
}
