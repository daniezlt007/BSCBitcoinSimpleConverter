package com.deasystem.daniel.bitcoinsimpleconverter.fragmento;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.deasystem.daniel.bitcoinsimpleconverter.R;
import com.deasystem.daniel.bitcoinsimpleconverter.common.Util;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragCalculadora extends Fragment {

    private AdView mAdView;
    private EditText txtValor1, txtValor2, txtResultado;
    private AlertDialog alerta;
    private RadioGroup radioGroup;
    private RadioButton radioButtonRealBtc;
    private RadioButton radioButtonBtcReal;


    public FragCalculadora() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_calculadora, container, false);

        MobileAds.initialize(this.getActivity().getApplicationContext(), Util.idSdk);
        AdView adView = new AdView(this.getActivity().getApplicationContext());
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(Util.idBanner);

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        txtValor1 = view.findViewById(R.id.txtValor1Calc);
        txtValor2 = view.findViewById(R.id.txtValor2Calc);
        txtResultado = view.findViewById(R.id.txtResultadoCalc);
        radioButtonRealBtc = view.findViewById(R.id.realBitCoinCalc);
        radioButtonBtcReal = view.findViewById(R.id.bitcoinRealCalc);
        radioGroup = view.findViewById(R.id.radioGroupCalc);

        txtValor2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try{
                    String a = txtValor1.getText().toString();
                    String b = txtValor2.getText().toString();

                    int idRadioButton = radioGroup.getCheckedRadioButtonId();
                    if (idRadioButton <= 0) {
                        Toast.makeText(getActivity(), "Deve ser escolhido uma opção!", Toast.LENGTH_SHORT).show();
                    } else {

                        if (radioButtonRealBtc.isChecked() == true) {
                            if (a.equals("")) {
                                txtValor1.setError("Esse campo é obrigatório.");
                                txtValor2.setError("Esse campo é obrigatório.");
                            } else {
                                double val1 = Double.parseDouble(txtValor1.getText().toString().replace(",", "."));
                                double val2 = Double.parseDouble(txtValor2.getText().toString().replace("R$", "").replace(".", "").replace(",", "."));
                                txtResultado.setText("BTC " + String.valueOf(Util.valorEmBtcFormatado(Util.converterRealBtc(val1, val2))).replace(".", ","));
                            }
                        }

                        if (radioButtonBtcReal.isChecked() == true) {
                            if (a.equals("")) {
                                txtValor1.setError("Esse campo é obrigatório.");
                                txtValor2.setError("Esse campo é obrigatório.");
                            } else {
                                NumberFormat formato = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                                double val1 = Double.parseDouble(txtValor1.getText().toString().replace(",", "."));
                                double val2 = Double.parseDouble(txtValor2.getText().toString().replace("R$", "").replace(".", "").replace(",", "."));
                                txtResultado.setText(Util.numeroformatadoEmReal.format(Util.converterBtcReal(val1, val2)));
                            }
                        }

                    }
                }catch (NumberFormatException e){
                    Alerta();
                }
            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int idRadioButton = group.getCheckedRadioButtonId();

                if(idRadioButton == radioButtonRealBtc.getId()){
                    txtValor1.setHint("BRL -> BTC");
                    limparCampos();
                }

                if(checkedId == radioButtonBtcReal.getId()){
                    txtValor1.setHint("BTC -> BRL");
                    limparCampos();
                }
            }
        });

        return view;
    }

    public void limparCampos(){
        txtValor1.setEnabled(true);
        txtValor2.setEnabled(true);
        txtValor1.setText("");
        txtValor2.setText("");
        txtResultado.setText("");
    }

    public void Alerta(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        //define o titulo
        builder.setTitle("Verifique o valor digitado.");
        //define a mensagem
        builder.setMessage("Ele deve ser desse padrão: \nEx:1,75 ou 2.78");
        //define um botão como positivo
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                txtValor1.setText("");
                txtValor2.setText("");
                txtResultado.setText("");
            }
        });

        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

}
