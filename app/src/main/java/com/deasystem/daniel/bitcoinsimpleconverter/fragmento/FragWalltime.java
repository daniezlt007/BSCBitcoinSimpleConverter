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
import com.deasystem.daniel.bitcoinsimpleconverter.modelo.RetWalltime;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragWalltime extends Fragment {

    private AdView mAdView;
    private EditText txtValor1, txtValor2, txtResultado;
    private TextView txtViewTaxa;

    private RadioGroup radioGroup;
    private RadioButton radioButtonRealBtc;
    private RadioButton radioButtonBtcReal;

    private AlertDialog alerta;

    //private String STRING_REQUEST_URL = "http://walltime-api.herokuapp.com/price/ticker";


    public FragWalltime() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_walltime, container, false);

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
                                    txtResultado.setText("BTC " + String.valueOf(numeroFormatado(converterRealBtc(val1, val2) - taxa)).replace(".", ","));
                                    txtViewTaxa.setText("Valor sem Taxa: " + numeroFormatado(converterRealBtc(val1, val2)));
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
                                    txtResultado.setText(formato.format(converterBtcReal(val1, val2)));
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
                    volleyStringRequst(Util.STRING_WALLTIME);
                    //Toast.makeText(MainActivity.this, "RadioButton: " + radioButtonRealBtc.getText().toString(), Toast.LENGTH_SHORT).show();
                }

                if (checkedId == radioButtonBtcReal.getId()) {
                    txtValor1.setHint("BTC -> BRL");
                    limparCampos();
                    //btnCalcular.setText("BTC -> BRL");
                    volleyStringRequest(Util.STRING_WALLTIME);
                    //Toast.makeText(MainActivity.this, "RadioButton: " + radioButtonBtcReal.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }


    public double converterRealBtc(double v1, double v2) {
        return v1 / v2;
    }

    public double converterBtcReal(double v1, double v2) {
        return v1 * v2;
    }

    public void limparCampos() {
        txtValor1.setEnabled(true);
        txtValor2.setEnabled(false);
        txtValor1.setText("");
        txtValor2.setText("");
        txtResultado.setText("");
        txtViewTaxa.setText("");
    }

    public static String numeroFormatado(double valor) {
        DecimalFormat dc = new DecimalFormat("#,##0.0000000");
        return dc.format(valor);
    }

    public static String numeroFormatadoReal(double valor) {
        DecimalFormat dc = new DecimalFormat("#,##0.00");
        return dc.format(valor);
    }

    public void volleyStringRequst(String url) {
        String REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";

        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String retorno = response;
                NumberFormat formato = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                RetWalltime cotacao = JSONGsonObjeto(retorno);
                String res = cotacao.getRetWalltime2().getXbt_brl();
                if (res.contains("/")) {
                    String[] explode = res.split("/");
                    String v1 = explode[0];
                    String v2 = explode[1];
                    double val1 = Double.parseDouble(v1);
                    double val2 = Double.parseDouble(v2);
                    if (explode[1] == null || explode[1].isEmpty()) {
                        double total = val2 / 1;
                        txtValor2.setText(formato.format(total));
                    } else {
                        double total = val2 / val1;
                        txtValor2.setText(formato.format(total));

                    }
                } else {
                    double total = Double.parseDouble(res);

                    txtValor2.setText(formato.format(total));
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


    public void volleyStringRequest(String url) {
        String REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";

        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String teste = response;
                NumberFormat formato = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                RetWalltime cotacao = JSONGsonObjeto(teste);
                String res = cotacao.getRetWalltime2().getBrl_xbt();

                if (res.contains("/")) {
                    String[] explode = res.split("/");
                    String v1 = explode[0];
                    String v2 = explode[1];
                    double val1 = Double.parseDouble(v1);
                    double val2 = Double.parseDouble(v2);
                    if (explode[0] == null || explode[0].isEmpty()) {
                        double total = val1 / 1;

                        txtValor2.setText(formato.format(total));
                    } else {
                        double total = val1 / val2;
                        txtValor2.setText(formato.format(total));

                    }
                } else {
                    double total = Double.parseDouble(res);

                    txtValor2.setText(formato.format(total));
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

    public void volleyCacheRequest(String url) {
        Cache cache = AppSingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);
        if (entry != null) {
            try {
                String data = new String(entry.data, "UTF-8");
                // handle data, like converting it to xml, json, bitmap etc.,
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {

        }
    }

    public void volleyInvalidateCache(String url) {
        AppSingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue().getCache().invalidate(url, true);
    }

    public void volleyDeleteCache(String url) {
        AppSingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue().getCache().remove(url);
    }

    public void volleyClearCache() {
        AppSingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue().getCache().clear();
    }


    public RetWalltime JSONGsonObjeto(String jsonString) {
        Gson gson = new Gson();
        RetWalltime cotacao = gson.fromJson(jsonString, RetWalltime.class);
        return cotacao;
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
