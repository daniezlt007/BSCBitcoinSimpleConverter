package com.deasystem.daniel.bitcoinsimpleconverter.fragmento;


import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.deasystem.daniel.bitcoinsimpleconverter.R;
import com.deasystem.daniel.bitcoinsimpleconverter.common.Util;
import com.deasystem.daniel.bitcoinsimpleconverter.modelo.AppSingleton;
import com.deasystem.daniel.bitcoinsimpleconverter.modelo.BitcoinToYou;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragBitcoinToYou extends Fragment {

    private AlertDialog alerta;
    private AdView mAdView;
    private EditText txtValorInvestidoConvertido, txtValorCotacaoAtual, txtResultado;
    private TextView textViewBitCoinToYouTaxa;

    private RadioGroup radioGroup;
    private RadioButton radioButtonRealBtc;
    private RadioButton radioButtonBtcReal;

    public FragBitcoinToYou() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_bitcointoyou, container, false);

        MobileAds.initialize(this.getActivity().getApplicationContext(), Util.idSdk);
        AdView adView = new AdView(this.getActivity().getApplicationContext());
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(Util.idBanner);

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        txtValorInvestidoConvertido = view.findViewById(R.id.txtValorInvestidoConvertido);
        textViewBitCoinToYouTaxa = view.findViewById(R.id.textViewBitCoinToYouTaxa);
        txtValorCotacaoAtual = view.findViewById(R.id.txtValorCotacaoAtual);
        txtResultado = view.findViewById(R.id.txtResultado);
        radioButtonRealBtc = view.findViewById(R.id.realBitCoin);
        radioButtonBtcReal = view.findViewById(R.id.bitcoinReal);
        radioGroup = view.findViewById(R.id.radioGroup);

        txtValorInvestidoConvertido.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    ConnectivityManager connMgr = (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        String a = txtValorInvestidoConvertido.getText().toString();
                        String b = txtValorCotacaoAtual.getText().toString();

                        int idRadioButton = radioGroup.getCheckedRadioButtonId();
                        if (idRadioButton <= 0) {
                            Toast.makeText(getActivity(), "Deve ser escolhido uma opção!", Toast.LENGTH_SHORT).show();
                        } else {

                            if (radioButtonRealBtc.isChecked() == true) {
                                if (a.equals("")) {
                                    txtValorInvestidoConvertido.setError("Esse campo é obrigatório.");
                                } else if (b.equals("")) {
                                    //txtValorCotacaoAtual.setError("");
                                    Toast.makeText(getActivity(), "Verifique a conexão, o campo cotação deve ser preenchido automaticamente.", Toast.LENGTH_LONG).show();
                                } else {
                                    double val1 = Double.parseDouble(txtValorInvestidoConvertido.getText().toString().replace(",", "."));
                                    double val2 = Double.parseDouble(txtValorCotacaoAtual.getText().toString().replace("R$", "").replace(".", "").replace(",", "."));
                                    double taxa = Double.parseDouble("0,00010000".toString().replace(",", "."));
                                    txtResultado.setText("BTC " + String.valueOf(Util.valorEmBtcFormatado(Util.converterRealBtc(val1, val2) - taxa)).replace(".", ","));
                                    textViewBitCoinToYouTaxa.setText("BTC sem Taxa: " + Util.valorEmBtcFormatado(Util.converterRealBtc(val1, val2)).replace(".", ","));
                                }
                            }

                            if (radioButtonBtcReal.isChecked() == true) {
                                if (a.equals("")) {
                                    txtValorInvestidoConvertido.setError("Esse campo é obrigatório.");
                                } else if (b.equals("")) {
                                    //txtValorCotacaoAtual.setError("");
                                    Toast.makeText(getActivity(), "Verifique a conexão, o campo cotação deve ser preenchido automaticamente.", Toast.LENGTH_LONG).show();
                                } else {
                                    double val1 = Double.parseDouble(txtValorInvestidoConvertido.getText().toString().replace(",", "."));
                                    double val2 = Double.parseDouble(txtValorCotacaoAtual.getText().toString().replace("R$", "").replace(".", "").replace(",", "."));
                                    double taxa = 1.39;
                                    double valorComTaxa = Util.converterBtcReal(val1, val2) * taxa / 100;
                                    txtResultado.setText(Util.numeroformatadoEmReal.format(Util.converterBtcReal(val1, val2) - valorComTaxa));
                                    textViewBitCoinToYouTaxa.setText("BRL sem taxa " + Util.numeroformatadoEmReal.format(Util.converterBtcReal(val1, val2)));
                                }
                            }
                        }
                    } else {
                        Toast.makeText(getActivity(), "Nenhuma conexão foi detectada", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    //Toast.makeText(getActivity(), "Padrão de Valor informado incorreto: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                    Log.e("ERRO" ,"Erro:" + e.getMessage() + "class:" + e.getClass() + " cause:" + e.getCause() + "" + e.getLocalizedMessage());
                    alerta();
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int idRadioButton = group.getCheckedRadioButtonId();
                int flag = 0;
                if(idRadioButton == radioButtonRealBtc.getId()){
                    flag = 1;
                    txtValorInvestidoConvertido.setHint("BRL -> BTC");
                    limparCampos();
                    volleyStringRequest(flag, Util.STRING_BITCOINTOYOU);
                }

                if(checkedId == radioButtonBtcReal.getId()){
                    flag = 2;
                    txtValorInvestidoConvertido.setHint("BTC -> BRL");
                    limparCampos();
                    volleyStringRequest(flag, Util.STRING_BITCOINTOYOU);
                }
            }
        });


        return view;
    }

    public void limparCampos(){
        txtValorInvestidoConvertido.setEnabled(true);
        txtValorCotacaoAtual.setEnabled(false);
        txtValorInvestidoConvertido.setText("");
        txtValorCotacaoAtual.setText("");
        txtResultado.setText("");
    }

    public void volleyStringRequest(final int codigo, String url){
        String  REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";

        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String json = response;
                BitcoinToYou bitcoinToYou = Util.retornaObjetoBitCoinToYou(json);

                double valorCompra = bitcoinToYou.getBuy();
                double valorVenda  = bitcoinToYou.getSell();

                if(codigo == 1){
                    txtValorCotacaoAtual.setText(Util.numeroformatadoEmReal.format(valorCompra));
                }else{
                    txtValorCotacaoAtual.setText(Util.numeroformatadoEmReal.format(valorVenda));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        // Adding String request to request queue
        AppSingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(strReq, REQUEST_TAG);
    }

    public void alerta(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        //define o titulo
        builder.setTitle("Verifique o valor digitado.");
        //define a mensagem
        builder.setMessage("Ele deve ser desse padrão: \nEx:1,75 ou 2.78");
        //define um botão como positivo
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                txtValorInvestidoConvertido.setText("");
                txtResultado.setText("");
            }
        });

        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }
}
