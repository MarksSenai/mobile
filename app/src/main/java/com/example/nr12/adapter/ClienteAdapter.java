package com.example.nr12.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nr12.R;
import com.example.nr12.dominio.entidades.Cliente;

/**
 * Created by Fabiano on 25/09/2017.
 */

public class ClienteAdapter extends ArrayAdapter<Cliente>{

    private int resource = 0;
    private LayoutInflater inflater;
    private Context context;

    public ClienteAdapter(Context context, int resource) {
        super(context, resource);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
        this.context = context;
    }

    static class ViewHolder{
        TextView txtNome;
        TextView txtCnpj;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        ClienteAdapter.ViewHolder viewHolder = null;

        if (convertView == null) {

            viewHolder = new ClienteAdapter.ViewHolder();

            view = inflater.inflate(resource, parent, false);

            viewHolder.txtNome = (TextView)view.findViewById(R.id.txtNome);
            viewHolder.txtCnpj = (TextView)view.findViewById(R.id.txtCnpj);

            view.setTag(viewHolder);

            convertView = view;

        } else {
            viewHolder = (ClienteAdapter.ViewHolder)convertView.getTag();
            view = convertView;
        }

        Cliente cliente = getItem(position);

        viewHolder.txtNome.setText(cliente.getNome());
        viewHolder.txtCnpj.setText(cliente.getCnpj());

        return view;
    }
}
