package com.example.nr12.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.nr12.CadRelatorioActivity;
import com.example.nr12.R;
import com.example.nr12.SelecionaClienteActivity;
import com.example.nr12.SelecionaMaquinaActivity;
import com.example.nr12.dominio.entidades.Cliente;
import com.example.nr12.dominio.entidades.Laudo;
import com.example.nr12.dominio.entidades.Maquina;
import com.example.nr12.util.DateUtils;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class GeneralFragment extends Fragment {

    private Cliente cliente;
    private Maquina maquina;
    private Laudo laudo;

    private OnFragmentInteractionListener mListener;

    private TextView txtViewCLiente;
    private TextView txtViewMaquina;
    private EditText editTextData;
    private EditText editTextTime;

    public GeneralFragment() {
        // Required empty public constructor
    }

    /**
     * 1º metodo executado
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
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
        //View view = inflater.inflate(R.layout.fragment_general, container, false);
        return inflater.inflate(R.layout.fragment_general, container, false);

        //return view;
    }

    /**
     * 4º Método executado
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //EditText getData = (EditText) view.findViewById(R.id.editTextData);

        txtViewCLiente  = (TextView) getActivity().findViewById(R.id.txtViewCLiente);
        txtViewMaquina  = (TextView) getActivity().findViewById(R.id.txtViewMaquina);
        editTextData    = (EditText) getActivity().findViewById(R.id.editTextData);
        editTextTime    = (EditText) getActivity().findViewById(R.id.editTextTime);


        ExibeDataListener listenerDate = new ExibeDataListener();
        editTextData.setOnClickListener(listenerDate);
        editTextData.setOnFocusChangeListener(listenerDate);
        editTextData.setKeyListener(null);

        ExibeTimeListener listenerTime = new ExibeTimeListener();
        editTextTime.setOnClickListener(listenerTime);
        editTextTime.setOnFocusChangeListener(listenerTime);
        editTextTime.setKeyListener(null);



        if(laudo.getMaquina() != null) {
            if (laudo.getMaquina().getCliente() != null) {
                txtViewCLiente.setText(laudo.getMaquina().getCliente().getNome());
            }
            if (laudo.getMaquina() != null) {
                txtViewMaquina.setText(laudo.getMaquina().getNome());
            }
        }
        txtViewCLiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), SelecionaClienteActivity.class);
                getActivity().startActivityForResult(intent, CadRelatorioActivity.PAR_CONSULTA_CLIENTE);
            }
        });

        txtViewMaquina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), SelecionaMaquinaActivity.class);
                intent.putExtra(CadRelatorioActivity.PAR_CLIENTE, laudo.getMaquina().getCliente());
                getActivity().startActivityForResult(intent, CadRelatorioActivity.PAR_CONSULTA_MAQUINA);
            }
        });


        if(laudo.getData_inicial() != null) {
            DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
            String dt = format.format(laudo.getData_inicial());
            editTextData.setText(dt);
        }




    }

    // onStart()
    // onResume()

    // onPause()
    // onStop()
    // onDestroyView()
    // onDestroy()

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
        Laudo getLaudo();
        void setDataInicial(Date dataInicial);

    }

    /**
     * Último método executado
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void exibeData(){
        Calendar calendar = Calendar.getInstance();
        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dlg = new DatePickerDialog(getContext(), new SelecionaDataListener(), ano, mes, dia);
        dlg.show();
    }

    private class ExibeDataListener implements View.OnClickListener, View.OnFocusChangeListener{

        @Override
        public void onClick(View v) {
            exibeData();
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus){
                exibeData();
            }
        }
    }

    private class SelecionaDataListener implements DatePickerDialog.OnDateSetListener{

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            String dt = DateUtils.dateToString(year, month, dayOfMonth);
            Date data = DateUtils.getDate(year, month, dayOfMonth);

            editTextData.setText(dt);

            //laudo.setDataInicial(data);
        }
    }

    private class ExibeTimeListener implements View.OnClickListener, View.OnFocusChangeListener {

        @Override
        public void onClick(View v) {
            exibeTime();
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus){
                exibeTime();
            }
        }

    }

    private void exibeTime(){
        /*
        Calendar calendar = Calendar.getInstance();
        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dlg = new DatePickerDialog(getContext(), new SelecionaDataListener(), ano, mes, dia);
        dlg.show();
        */

        Calendar calendar = Calendar.getInstance();
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int minuto = calendar.get(Calendar.MINUTE);

        TimePickerDialog dlgTime = new TimePickerDialog(getContext(), new SelecionaTimeListener(), hora, minuto, true);
        dlgTime.show();
    }

    private class SelecionaTimeListener implements TimePickerDialog.OnTimeSetListener{

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            /*
            String dt = DateUtils.dateToString(year, month, dayOfMonth);
            Date data = DateUtils.getDate(year, month, dayOfMonth);
            editTextData.setText(dt);
            //laudo.setDataInicial(data);
            */

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);

            editTextTime.setText(hourOfDay+":"+minute);

        }


    }


}
