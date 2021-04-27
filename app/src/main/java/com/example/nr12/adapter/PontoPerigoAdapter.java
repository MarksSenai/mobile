package com.example.nr12.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nr12.R;
import com.example.nr12.dominio.entidades.Cliente;
import com.example.nr12.dominio.entidades.PontoPerigo;

import java.util.List;

/**
 * Created by Fabiano on 25/09/2017.
 */

public class PontoPerigoAdapter extends ArrayAdapter<PontoPerigo>{

    private int resource = 0;
    private LayoutInflater inflater;
    private Context context;

    public PontoPerigoAdapter(Context context, int resource, List<PontoPerigo> list) {
        super(context, resource, list);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
        this.context = context;
    }

    static class ViewHolder{
        TextView txtPontoPerigo;
        TextView txtFace;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        PontoPerigoAdapter.ViewHolder viewHolder = null;

        if (convertView == null) {

            viewHolder = new PontoPerigoAdapter.ViewHolder();

            view = inflater.inflate(resource, parent, false);

            viewHolder.txtPontoPerigo = (TextView)view.findViewById(R.id.txtPontoPerigo);
            viewHolder.txtFace = (TextView)view.findViewById(R.id.txtFace);

            view.setTag(viewHolder);

            convertView = view;

        } else {
            viewHolder = (PontoPerigoAdapter.ViewHolder)convertView.getTag();
            view = convertView;
        }

        PontoPerigo pontoPerigo = getItem(position);

        viewHolder.txtPontoPerigo.setText(pontoPerigo.getPontoPerigo());
        viewHolder.txtFace.setText(pontoPerigo.getFace());

        return view;
    }
}
