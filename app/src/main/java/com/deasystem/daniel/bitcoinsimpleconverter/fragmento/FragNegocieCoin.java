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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class FragNegocieCoin extends Fragment {

    private AdView mAdView;
    private EditText txtValor1, txtValor2, txtResultado;
    private TextView txtViewTaxa;

    private RadioGroup radioGroup;
    private RadioButton radioButtonRealBtc;
    private RadioButton radioButtonBtcReal;

    private AlertDialog alerta;

    public FragNegocieCoin() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_negocie_coin, container, false);

        MobileAds.initialize(this.getActivity().getApplicationContext(), "ca-app-pub-1974086740128373~6640975025");
        AdView adView = new AdView(this.getActivity().getApplicationContext());
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        txtViewTaxa = view.findViewById(R.id.txtViewTaxa);

        txtValor1 = view.findViewById(R.id.txtValorInvestidoConvertido);
        txtValor2 = view.findViewById(R.id.txtValorCotacaoAtual);
        txtResultado = view.findViewById(R.id.txtResultado);
        radioButtonRealBtc = view.findViewById(R.id.realBitCoin);
        radioButtonBtcReal = view.findViewById(R.id.bitcoinReal);
        radioGroup = view.findViewById(R.id.radioGroup);


        txtValor1.addTextChangedListener(new TextWatcher() {
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
                        String a = txtValor1.getText().toString();
                        String b = txtValor2.getText().toString();

                        int idRadioButton = radioGroup.getCheckedRadioButtonId();
                        if (idRadioButton <= 0) {
                            Toast.makeText(getActivity(), "Deve ser escolhido uma opção!", Toast.LENGTH_SHORT).show();
                        } else {

                            if (radioButtonRealBtc.isChecked() == true) {
                                if (a.equals("")) {
                                    txtValor1.setError("Esse campo é obrigatório.");
                                } else if (b.equals("")) {
                                    //txtValor2.setError("");
                                    Toast.makeText(getActivity(), "Verifique a conexão, o campo cotação deve ser preenchido automaticamente.", Toast.LENGTH_LONG).show();
                                } else {
                                    double val1 = Double.parseDouble(txtValor1.getText().toString().replace(",", "."));
                                    double val2 = Double.parseDouble(txtValor2.getText().toString().replace("R$", "").replace(".", "").replace(",", "."));
                                    double taxa = Double.parseDouble("0,00183430".toString().replace(",", "."));
                                    txtResultado.setText("BTC " + String.valueOf(Util.valorEmBtcFormatado(Util.converterRealBtc(val1, val2) - taxa)).replace(".", ","));
                                    txtViewTaxa.setText("Valor sem Taxa: " + Util.valorEmBtcFormatado(Util.converterRealBtc(val1, val2)));
                                }
                            }

                            if (radioButtonBtcReal.isChecked() == true) {
                                if (a.equals("")) {
                                    txtValor1.setError("Esse campo é obrigatório.");
                                } else if (b.equals("")) {
                                    //txtValor2.setError("");
                                    Toast.makeText(getActivity(), "Verifique a conexão, o campo cotação deve ser preenchido automaticamente.", Toast.LENGTH_LONG).show();
                                } else {
                                    double val1 = Double.parseDouble(txtValor1.getText().toString().replace(",", "."));
                                    double val2 = Double.parseDouble(txtValor2.getText().toString().replace("R$", "").replace(".", "").replace(",", "."));
                                    double taxa = 1.39;
                                    double valorComTaxa = Util.converterBtcReal(val1, val2) * taxa / 100;
                                    txtResultado.setText(Util.numeroformatadoEmReal.format(Util.converterBtcReal(val1, val2) - valorComTaxa));
                                    txtViewTaxa.setText("BRL sem taxa " + Util.numeroformatadoEmReal.format(Util.converterBtcReal(val1, val2)));
                                }
                            }

                        }
                    } else {
                        Toast.makeText(getActivity(), "Nenhuma conexão foi detectada", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    //Toast.makeText(getActivity(), "Padrão de Valor informado incorreto", Toast.LENGTH_SHORT).show();
                    Alerta();
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int idRadioButton = group.getCheckedRadioButtonId();

                if (idRadioButton == radioButtonRealBtc.getId()) {
                    txtValor1.setHint("BRL -> BTC");
                    limparCampos();
                    //btnCalcular.setText("BRL -> BTC");
                    volleyStringRequestCompra(Util.STRING_NEGOCIECOINS);
                    //Toast.makeText(MainActivity.this, "RadioButton: " + radioButtonRealBtc.getText().toString(), Toast.LENGTH_SHORT).show();
                }

                if (checkedId == radioButtonBtcReal.getId()) {
                    txtValor1.setHint("BTC -> BRL");
                    limparCampos();
                    //btnCalcular.setText("BTC -> BRL");
                    volleyStringRequestVenda(Util.STRING_NEGOCIECOINS);
                    //Toast.makeText(MainActivity.this, "RadioButton: " + radioButtonBtcReal.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    public void volleyStringRequestCompra(String url){
        String  REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";

        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String teste = response;

                try {

                    JSONObject jo = new JSONObject(teste);
                    //String a = j.get("bid").toString() + " - " + j.get("ask").toString();
                    double valor1 = Double.parseDouble(jo.get("buy").toString());

                    NumberFormat formato = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));

                    txtValor2.setText(formato.format(valor1));


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Toast.makeText(getApplicationContext(), "MSG:" , Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        // Adding String request to request queue
        AppSingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(strReq, REQUEST_TAG);
    }

    public void volleyStringRequestVenda(String url){
        String  REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";

        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String teste = response;

                try {

                    JSONObject jo = new JSONObject(teste);

                    double valor1 = Double.parseDouble(jo.get("sell").toString());

                    NumberFormat formato = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));

                    txtValor2.setText(formato.format(valor1));


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Toast.makeText(getApplicationContext(), "MSG:" , Toast.LENGTH_LONG).show();
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
        txtValor1.setEnabled(true);
        txtValor2.setEnabled(false);
        txtValor1.setText("");
        txtValor2.setText("");
        txtResultado.setText("");
        txtViewTaxa.setText("");
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
                txtResultado.setText("");
            }
        });

        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

}
