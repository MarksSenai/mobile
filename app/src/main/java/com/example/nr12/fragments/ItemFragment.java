package com.example.nr12.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.nr12.CadRelatorioActivity;
import com.example.nr12.R;
import com.example.nr12.adapter.PontoPerigoAdapter;
import com.example.nr12.dominio.entidades.Laudo;
import com.example.nr12.dominio.entidades.PontoPerigo;

import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemFragment extends Fragment {

    private Laudo laudo;

    private OnFragmentInteractionListener mListener;

    public ItemFragment() {
        // Required empty public constructor
    }

    /**
     * 1º metodo executado
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof GeneralFragment.OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    /**
     * 2º método executado
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        laudo = mListener.getLaudo();
    }

    /**
     * 3º Método executado
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        ListView lstItens = (ListView)view.findViewById(R.id.lstItem);

        try {
            if (laudo.getPontoPerigos() != null) {
                //ArrayAdapter adapter = ArrayAdapter.createFromResource(view.getContext(), laudo.getPontoPerigos(), android.R.layout.simple_list_item_1);
                PontoPerigoAdapter listAdapter = new PontoPerigoAdapter(getContext(), R.layout.item_ponto_perigo, laudo.getPontoPerigos());
                lstItens.setAdapter(listAdapter);
            }
        }catch (Exception ex){
            Log.i("INFO", ex.getMessage());
        }
        //ArrayAdapter adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.itens, android.R.layout.simple_list_item_1);
        //lstItens.setAdapter(adapter);

        return view;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
        Laudo getLaudo();
        List<PontoPerigo> getPontoPerigoList();
        void addPontoPerigo(PontoPerigo pontoPerigo);

    }


    /**
     * Último método executado
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}



