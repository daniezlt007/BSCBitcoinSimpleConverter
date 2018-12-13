package com.deasystem.daniel.bitcoinsimpleconverter.fragmento;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.deasystem.daniel.bitcoinsimpleconverter.R;
import com.deasystem.daniel.bitcoinsimpleconverter.modelo.AppSingleton;
import com.deasystem.daniel.bitcoinsimpleconverter.modelo.BitcoinToYou;
import com.deasystem.daniel.bitcoinsimpleconverter.modelo.MercadoBitCoin;
import com.deasystem.daniel.bitcoinsimpleconverter.modelo.RetWalltime;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragLucro extends Fragment {

    private static AlertDialog alerta;
    private AdView mAdView;
    private MercadoBitCoin m;
    private BitcoinToYou b;
    private Spinner spinner;
    private EditText txtValorInvestido, txtCotacaoDeCompra,
            txtValorInvestidoConvertido, txtValorCotacaoAtual,
            txtValorEmReaisAtual;
    private TextView txtResultadoLucro;

    private String STRING_REQUEST_URL = "https://s3.amazonaws.com/data-production-walltime-info/production/dynamic/meta.json";
    private String STRING_REQUEST_URL_MERCADO = "https://www.mercadobitcoin.net/api/btc/ticker/";
    private String STRING_REQUEST_URL_BITCOINTOYOU = "https://www.bitcointoyou.com/api/ticker.aspx";
    private String STRING_REQUEST_URL_FOXBIT = "https://api.bitvalor.com/v1/order_book_stats.json";
    private String STRING_REQUEST_URL_BITCOINTRADE = "https://api.bitcointrade.com.br/v1/public/BTC/ticker";

    public FragLucro() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_lucro, container, false);

        MobileAds.initialize(this.getActivity().getApplicationContext(), "ca-app-pub-1974086740128373~6640975025");
        AdView adView = new AdView(this.getActivity().getApplicationContext());
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        txtValorInvestido = view.findViewById(R.id.txtValorInvestido);
        txtCotacaoDeCompra = view.findViewById(R.id.txtCotacaoDeCompra);
        txtValorInvestidoConvertido = view.findViewById(R.id.txtValorInvestidoConvertido);
        txtValorCotacaoAtual = view.findViewById(R.id.txtValorCotacaoAtual);
        txtValorEmReaisAtual = view.findViewById(R.id.txtValorEmReaisAtual);
        txtResultadoLucro = view.findViewById(R.id.txtResultadoLucro);

        String[] bitCoins = {"Selecione uma Exchange","Walltime",
                "M. Bitcoin" , "Foxbit", "BitcoinToYou", "BitcoinTrade"};
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, bitCoins);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = view.findViewById(R.id.id_spinner);
        spinner.setAdapter(adapt);

        txtValorInvestido.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String a = txtValorInvestido.getText().toString();
                String b = txtCotacaoDeCompra.getText().toString();
                if (a.equals("")) {
                    txtValorInvestido.setError("Esse campo é obrigatório.");
                } else if (b.equals("")) {
                    txtCotacaoDeCompra.setError("Esse campo é obrigatório.");
                }else {
                    try {
                        double val1 = Double.parseDouble(a.replace(",", "."));
                        double val2 = Double.parseDouble(b.replace(",", "."));
                        txtValorInvestidoConvertido.setText(String.valueOf(numeroFormatado(converterRealBtc(val1, val2))).replace(".", ","));
                    } catch (Exception e) {
                        alerta("Verifique o valor digitado.", "Ele deve ser desse padrão: \nEx:1,75 ou 2.78");
                    }
                }
            }
        });

        txtCotacaoDeCompra.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String a = txtValorInvestido.getText().toString();
                    String b = txtCotacaoDeCompra.getText().toString();
                    if (a.equals("")) {
                        txtValorInvestido.setError("Esse campo é obrigatório.");
                    } else if (b.equals("")) {
                        txtCotacaoDeCompra.setError("Esse campo é obrigatório.");
                    }else {
                        try {
                            double val1 = Double.parseDouble(a.replace(",", "."));
                            double val2 = Double.parseDouble(b.replace(",", "."));
                            txtValorInvestidoConvertido.setText(String.valueOf(numeroFormatado(converterRealBtc(val1, val2))).replace(".", ","));
                            spinner.setSelection(0);
                            txtValorCotacaoAtual.setText("");
                            txtValorEmReaisAtual.setText("");
                        } catch (Exception e) {
                            alerta("Verifique o valor digitado.", "Ele deve ser desse padrão: \nEx:1,75 ou 2.78");
                        }
                    }
                }
            });

            txtValorCotacaoAtual.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String a = txtValorInvestidoConvertido.getText().toString();
                    String b = txtValorCotacaoAtual.getText().toString();
                        try {
                            NumberFormat formato = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                            double val1 = Double.parseDouble(a.replace(",", "."));
                            double val2 = Double.parseDouble(b.replace("R$", "").replace(".", "").replace(",", "."));
                            txtValorEmReaisAtual.setText(formato.format(converterBtcReal(val1, val2)));

                            double val3 = Double.parseDouble(txtValorInvestido.getText().toString().replace("R$", "").replace(".", "").replace(",", "."));
                            double val4 = Double.parseDouble(txtValorEmReaisAtual.getText().toString().replace("R$", "").replace(".", "").replace(",", "."));
                            double total = calcularTotal(val4, val3);
                            if (val3 > val4) {
                                Log.d("DEBUG", "prejuizo: " + formato.format(total));
                                txtResultadoLucro.setText("Seu Prejuízo é de:" + formato.format(total));
                            } else {
                                Log.d("DEBUG", "lucro: " + formato.format(total));
                                txtResultadoLucro.setText("Seu Lucro é de: " + formato.format(total));
                            }
                        } catch (Exception e) {

                        }
                    }
            });



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int posicaoClicada = position;
                switch (posicaoClicada){
                    case 1:
                        volleyStringRequestWalltime(STRING_REQUEST_URL);
                        break;
                    case 2:
                        volleyStringRequestMercadoBitcoin(STRING_REQUEST_URL_MERCADO);
                        break;
                    case 3:
                        volleyStringRequestFoxbit(STRING_REQUEST_URL_FOXBIT);
                        break;
                    case 4:
                        volleyStringRequstBitCoinToYou(STRING_REQUEST_URL_BITCOINTOYOU);
                        break;
                    case 5:
                        volleyStringRequestBitcoinTradeVenda(STRING_REQUEST_URL_BITCOINTRADE);
                        break;
                    default:
                        txtValorCotacaoAtual.setText("");
                        txtValorEmReaisAtual.setText("");
                        txtResultadoLucro.setText("");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        return view;
    }

    public void volleyStringRequestWalltime(String url) {
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
                        txtValorCotacaoAtual.setText(formato.format(total));
                    } else {
                        double total = val1 / val2;
                        txtValorCotacaoAtual.setText(formato.format(total));

                    }
                } else {
                    double total = Double.parseDouble(res);

                    txtValorCotacaoAtual.setText(formato.format(total));
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

    public void volleyStringRequestBitcoinTradeVenda(String url) {
        String REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";

        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String teste = response;

                try {

                    JSONObject jo = new JSONObject(teste);
                    JSONObject j = jo.getJSONObject("data");

                    double valor1 = Double.parseDouble(j.get("sell").toString());

                    NumberFormat formato = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

                    txtValorCotacaoAtual.setText(formato.format(valor1));


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

    public void volleyStringRequestMercadoBitcoin(String url) {
        String REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";

        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String teste = response;
                m = retornaObjeto(teste);
                double valor = m.getSell();
                NumberFormat formato = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                txtValorCotacaoAtual.setText(formato.format(valor));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        // Adding String request to request queue
        AppSingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(strReq, REQUEST_TAG);
    }

    public void volleyStringRequstBitCoinToYou(String url) {

        String REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";
        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String teste = response;
                b = retornaObjetoBitCoinToYou(teste);
                NumberFormat formato = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                txtValorCotacaoAtual.setText(formato.format(b.getSell()));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        // Adding String request to request queue
        AppSingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(strReq, REQUEST_TAG);
    }

    public void volleyStringRequestFoxbit(String url) {
        String REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";

        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String teste = response;

                try {

                    JSONObject jo = new JSONObject(teste);
                    JSONObject j = jo.getJSONObject("FOX");
                    //String a = j.get("bid").toString() + " - " + j.get("ask").toString();
                    double valor1 = Double.parseDouble(j.get("bid").toString());
                    double valor2 = Double.parseDouble(j.get("ask").toString());
                    double valor3 = Double.parseDouble(j.get("bid_vol").toString());

                    NumberFormat formato = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

                    txtValorCotacaoAtual.setText(formato.format(valor2));


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

    public double converterRealBtc(double v1, double v2) {
        return v1 / v2;
    }

    public double converterBtcReal(double v1, double v2) {
        return v1 * v2;
    }

    public double calcularTotal(double v1, double v2){
        return v1 - v2;
    }

    public static String numeroFormatado(double valor) {
        DecimalFormat dc = new DecimalFormat("#,##0.0000000");
        return dc.format(valor);
    }

    public static String numeroFormatadoReal(double valor) {
        DecimalFormat dc = new DecimalFormat("#,##0.00");
        return dc.format(valor);
    }

    public RetWalltime JSONGsonObjeto(String jsonString) {
        Gson gson = new Gson();
        RetWalltime cotacao = gson.fromJson(jsonString, RetWalltime.class);
        return cotacao;
    }

    public MercadoBitCoin retornaObjeto(String js) {
        m = new MercadoBitCoin();
        try {
            JSONObject json = new JSONObject(js);

            JSONObject d = json.getJSONObject("ticker");
            m.setBuy(Double.parseDouble(d.getString("buy")));
            m.setSell(Double.parseDouble(d.getString("sell")));
            m.setVol(Double.parseDouble(d.getString("vol")));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return m;
    }


    public BitcoinToYou retornaObjetoBitCoinToYou(String js) {
        b = new BitcoinToYou();
        double valor = 0;
        try {
            JSONObject json = new JSONObject(js);
            JSONObject d = json.getJSONObject("ticker");
            b.setBuy(Double.parseDouble(d.getString("buy")));
            b.setSell(Double.parseDouble(d.getString("sell")));
            b.setVol(Double.parseDouble(d.getString("vol")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return b;
    }

    public void alerta(String titulo, String msg){

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        //define o titulo
        builder.setTitle(titulo);
        //define a mensagem
        builder.setMessage(msg);
        //define um botão como positivo
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            txtValorInvestido.setText("");
            txtValorCotacaoAtual.setText("");
            txtCotacaoDeCompra.setText("");
            txtValorInvestidoConvertido.setText("");
            txtValorCotacaoAtual.setText("");
            txtValorEmReaisAtual.setText("");
            txtResultadoLucro.setText("");
            }
        });

        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

}
