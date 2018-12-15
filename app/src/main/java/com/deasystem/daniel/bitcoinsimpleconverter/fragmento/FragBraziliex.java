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
import com.deasystem.daniel.bitcoinsimpleconverter.modelo.Brasiliex;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragBraziliex extends Fragment {

    private AdView mAdView;
    private EditText txtValorInvestidoConvertido, txtValorCotacaoAtual, txtResultado;
    private TextView textViewBraziliexTaxa;

    private RadioGroup radioGroup;
    private RadioButton radioButtonRealBtc;
    private RadioButton radioButtonBtcReal;

    private AlertDialog alerta;

    public FragBraziliex() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_braziliex, container, false);

        MobileAds.initialize(this.getActivity().getApplicationContext(), "ca-app-pub-1974086740128373~6640975025");
        AdView adView = new AdView(this.getActivity().getApplicationContext());
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        textViewBraziliexTaxa = view.findViewById(R.id.textViewFoxTaxa);

        txtValorInvestidoConvertido = view.findViewById(R.id.txtValorInvestidoConvertido);
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
                                    double taxa = Double.parseDouble("0,00015625".replace(",", "."));
                                    txtResultado.setText("BTC " + String.valueOf(Util.valorEmBtcFormatado(Util.converterRealBtc(val1, val2) - taxa)).replace(".", ","));
                                    textViewBraziliexTaxa.setText("BTC sem Taxa: " + Util.valorEmBtcFormatado(Util.converterRealBtc(val1, val2)).replace(".", ","));
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
                                    double taxa = 0.25;
                                    double valorComTaxa = 9.0 + (Util.converterBtcReal(val1, val2) * taxa / 100);
                                    txtResultado.setText(Util.numeroformatadoEmReal.format(Util.converterBtcReal(val1, val2) - valorComTaxa));
                                    textViewBraziliexTaxa.setText("BRL sem taxa " + Util.numeroformatadoEmReal.format(Util.converterBtcReal(val1, val2)));
                                }
                            }

                        }
                    } else {
                        Toast.makeText(getActivity(), "Nenhuma conexão foi detectada", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    //Toast.makeText(getActivity(), "Padrão de Valor informado incorreto", Toast.LENGTH_SHORT).show();
                    alerta();
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int idRadioButton = group.getCheckedRadioButtonId();
                int flag = -1;
                if (idRadioButton == radioButtonRealBtc.getId()) {
                    flag = 1;
                    txtValorInvestidoConvertido.setHint("BRL -> BTC");
                    limparCampos();
                    volleyStringRequestBrasiliex(flag, Util.STRING_BRAZILIEX);
                }

                if (checkedId == radioButtonBtcReal.getId()) {
                    flag = 2;
                    txtValorInvestidoConvertido.setHint("BTC -> BRL");
                    limparCampos();
                    volleyStringRequestBrasiliex(flag, Util.STRING_BRAZILIEX);
                }
            }
        });
        return view;
    }

    public void volleyStringRequestBrasiliex(final int codigo, String url) {
        String REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";

        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String json = response;
                Brasiliex brasiliex = Util.retornaObjetoBraziliex(json);
                double valorCompra = brasiliex.getLowestAsk();
                double valorVenda  = brasiliex.getHighestBid();

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

    public void limparCampos() {
        txtValorInvestidoConvertido.setEnabled(true);
        txtValorCotacaoAtual.setEnabled(false);
        txtValorInvestidoConvertido.setText("");
        txtValorCotacaoAtual.setText("");
        txtResultado.setText("");
        textViewBraziliexTaxa.setText("");
    }

    public void alerta() {

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
