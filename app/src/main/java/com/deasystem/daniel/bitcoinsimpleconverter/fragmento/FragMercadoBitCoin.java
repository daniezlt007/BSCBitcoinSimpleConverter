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

import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.deasystem.daniel.bitcoinsimpleconverter.R;
import com.deasystem.daniel.bitcoinsimpleconverter.common.Util;
import com.deasystem.daniel.bitcoinsimpleconverter.modelo.AppSingleton;
import com.deasystem.daniel.bitcoinsimpleconverter.modelo.MercadoBitCoin;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragMercadoBitCoin extends Fragment {

    private AlertDialog alerta;
    private AdView mAdView;
    private EditText txtValor1, txtValor2, txtResultado;
    private TextView txtViewTaxaMB;

    private RadioGroup radioGroup;
    private RadioButton radioButtonRealBtc;
    private RadioButton radioButtonBtcReal;


    public FragMercadoBitCoin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_mercadobitcoin, container, false);

        MobileAds.initialize(this.getActivity().getApplicationContext(), "ca-app-pub-1974086740128373~6640975025");
        AdView adView = new AdView(this.getActivity().getApplicationContext());
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        txtViewTaxaMB = view.findViewById(R.id.textViewTaxaMB);
        txtValor1 = view.findViewById(R.id.txtValorInvestidoConvertido);
        txtValor2 = view.findViewById(R.id.txtValorCotacaoAtual);
        txtResultado = view.findViewById(R.id.txtResultado);
        radioButtonRealBtc = view.findViewById(R.id.realBitCoin);
        radioButtonBtcReal = view.findViewById(R.id.bitcoinReal);
        radioGroup = view.findViewById(R.id.radioGroup);

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
                                    double taxa = 0.70;
                                    double valorComTaxa = converterRealBtc(val1, val2) * taxa / 100;
                                    txtResultado.setText("BTC " + String.valueOf(numeroFormatado(converterRealBtc(val1, val2) - valorComTaxa).replace(".", ",")));
                                    txtViewTaxaMB.setText("BTC sem Taxa: " + numeroFormatado(converterRealBtc(val1, val2)).replace(".", ","));
                                }
                            }

                            if (radioButtonBtcReal.isChecked() == true) {
                                if (a.equals("")) {
                                    txtValor1.setError("Esse campo é obrigatório.");
                                } else if (b.equals("")) {
                                    //txtValor2.setError("");
                                    Toast.makeText(getActivity(), "Verifique a conexão, o campo cotação deve ser preenchido automaticamente.", Toast.LENGTH_LONG).show();
                                } else {
                                    NumberFormat formato = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                                    double val1 = Double.parseDouble(txtValor1.getText().toString().replace(",", "."));
                                    double val2 = Double.parseDouble(txtValor2.getText().toString().replace("R$", "").replace(".", "").replace(",", "."));
                                    double taxa = 0.70;
                                    double valorComTaxa = converterBtcReal(val1, val2) * taxa / 100;
                                    txtResultado.setText(formato.format(converterBtcReal(val1, val2) - valorComTaxa));
                                    txtViewTaxaMB.setText("BRL sem taxa " + formato.format(converterBtcReal(val1, val2)));
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

                if(idRadioButton == radioButtonRealBtc.getId()){
                    txtValor1.setHint("BRL -> BTC");
                    limparCampos();
                    volleyStringRequst(Util.STRING_MERCADOBITCOIN);
                    //Toast.makeText(MainActivity.this, "RadioButton: " + radioButtonRealBtc.getText().toString(), Toast.LENGTH_SHORT).show();
                }

                if(checkedId == radioButtonBtcReal.getId()){
                    txtValor1.setHint("BTC -> BRL");
                    limparCampos();
                    volleyStringRequest(Util.STRING_MERCADOBITCOIN);
                    //Toast.makeText(MainActivity.this, "RadioButton: " + radioButtonBtcReal.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }


    public double converterRealBtc(double v1, double v2){
        return v1 / v2;
    }

    public double converterBtcReal(double v1, double v2){
        return v1 * v2;
    }

    public void limparCampos(){
        txtValor1.setEnabled(true);
        txtValor2.setEnabled(false);
        txtValor1.setText("");
        txtValor2.setText("");
        txtViewTaxaMB.setText("");
        txtResultado.setText("");
    }

    public static String numeroFormatado(double valor) {
        DecimalFormat dc = new DecimalFormat("#,##0.0000000");
        return dc.format(valor);
    }

    public static String numeroFormatadoReal(double valor) {
        DecimalFormat dc = new DecimalFormat("#,##0.00");
        return dc.format(valor);
    }

    public void volleyStringRequst(String url){
        String  REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";

        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String teste = response;
                double valor = retornaObjeto(teste, 1);
                NumberFormat formato = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));
                txtValor2.setText(formato.format(valor));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        // Adding String request to request queue
        AppSingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(strReq, REQUEST_TAG);
    }


    public void volleyStringRequest(String url){
        String  REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";

        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String teste = response;
                double valor = retornaObjeto(teste, 2);
                NumberFormat formato = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));
                txtValor2.setText(formato.format(valor));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        // Adding String request to request queue
        AppSingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(strReq, REQUEST_TAG);
    }

    public void volleyCacheRequest(String url){
        Cache cache = AppSingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);
        if(entry != null){
            try {
                String data = new String(entry.data, "UTF-8");
                // handle data, like converting it to xml, json, bitmap etc.,
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        else{

        }
    }

    public void volleyInvalidateCache(String url){
        AppSingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue().getCache().invalidate(url, true);
    }

    public void volleyDeleteCache(String url){
        AppSingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue().getCache().remove(url);
    }

    public void volleyClearCache(){
        AppSingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue().getCache().clear();
    }


    public MercadoBitCoin JSONGsonObjeto(String jsonString) {
        Gson gson = new Gson();
        MercadoBitCoin cotacao = gson.fromJson(jsonString, MercadoBitCoin.class);
        return cotacao;
    }

    public double retornaObjeto(String js, int op){
        MercadoBitCoin m = new MercadoBitCoin();
        double valor = 0;
        try {
            JSONObject json = new JSONObject(js);
            if(op == 1){
                JSONObject d = json.getJSONObject("ticker");
                m.setBuy(Double.parseDouble(d.getString("buy")));
                valor = m.getBuy();
            }
            if(op == 2){
                JSONObject d = json.getJSONObject("ticker");
                m.setSell(Double.parseDouble(d.getString("sell")));
                valor = m.getSell();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return valor;
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
