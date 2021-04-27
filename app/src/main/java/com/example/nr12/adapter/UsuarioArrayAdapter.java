package com.example.nr12.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nr12.R;
import com.example.nr12.dominio.entidades.Config;


/**
 * Created by Fabiano on 03/10/2017.
 */

public class UsuarioArrayAdapter extends ArrayAdapter<Config>{

    private int resource = 0;
    private LayoutInflater inflater;
    private Context context;

    public UsuarioArrayAdapter(Context context, int resource) {
        super(context, resource);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
        this.context = context;
    }

    static class ViewHolder{
        TextView txtEmail;
        TextView txtSenha;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        ViewHolder viewHolder = null;

        if (convertView == null) {

            viewHolder = new ViewHolder();

            view = inflater.inflate(resource, parent, false);

            viewHolder.txtEmail = (TextView)view.findViewById(R.id.txtEmail);
            viewHolder.txtSenha = (TextView)view.findViewById(R.id.txtSenha);

            view.setTag(viewHolder);

            convertView = view;

        } else {
            viewHolder = (ViewHolder)convertView.getTag();
            view = convertView;
        }

        Config config = getItem(position);

        viewHolder.txtEmail.setText(config.getEmail());
        viewHolder.txtSenha.setText(config.getSenha());

        return view;
    }
}
