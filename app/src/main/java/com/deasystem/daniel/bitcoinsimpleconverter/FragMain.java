package com.deasystem.daniel.bitcoinsimpleconverter;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.deasystem.daniel.bitcoinsimpleconverter.common.Util;
import com.deasystem.daniel.bitcoinsimpleconverter.fragmento.FragBitcoinToYou;
import com.deasystem.daniel.bitcoinsimpleconverter.fragmento.FragBitcoinTrade;
import com.deasystem.daniel.bitcoinsimpleconverter.fragmento.FragFoxbit;
import com.deasystem.daniel.bitcoinsimpleconverter.fragmento.FragMercadoBitCoin;
import com.deasystem.daniel.bitcoinsimpleconverter.fragmento.FragNegocieCoin;
import com.deasystem.daniel.bitcoinsimpleconverter.fragmento.FragWalltime;
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
public class FragMain extends Fragment {

    private AdView mAdView;
    private MercadoBitCoin m;
    private BitcoinToYou b;
    private Button btn_consultar, btn_walltime,
            btn_mercado, btn_foxbit,
            btn_bitcointoyou, btn_bitcointrade, btn_negociecoin;
    private TextView txt_vl_compra_walltime, txt_vl_venda_walltime, txt_vl_compra_mercado_bitcoin,
            txt_vl_venda_mercado_bitcoin, txt_vl_compra_bitcointoyou, txt_vl_venda_bitcointoyou,
            txt_vl_compra_foxbit, txt_vl_venda_foxbit, txt_vl_compra_bitcointrade, txt_vl_venda_bitcointrade,
            txt_vl_compra_negociecoins, txt_vl_venda_negociecoins;

    public FragMain() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        //Anúncio
        MobileAds.initialize(this.getActivity().getApplicationContext(), "ca-app-pub-1974086740128373~6640975025");
        AdView adView = new AdView(this.getActivity().getApplicationContext());
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        volleyStringRequstWalltime(Util.STRING_WALLTIME);
        volleyStringRequestWalltime(Util.STRING_WALLTIME);
        volleyStringRequestMercadoBitcoin(Util.STRING_MERCADOBITCOIN);
        volleyStringRequstMercadoBitcoin(Util.STRING_MERCADOBITCOIN);
        volleyStringRequestBitCoinToYou(Util.STRING_BITCOINTOYOU);
        volleyStringRequstBitCoinToYou(Util.STRING_BITCOINTOYOU);
        volleyStringRequestFoxbit(Util.STRING_FOXBIT);
        volleyStringRequestBitcoinTradeCompra(Util.STRING_BITCOINTRADE);
        volleyStringRequestBitcoinTradeVenda(Util.STRING_BITCOINTRADE);
        volleyStringRequestCompra(Util.STRING_NEGOCIECOINS);
        volleyStringRequestVenda(Util.STRING_NEGOCIECOINS);



        txt_vl_compra_walltime = view.findViewById(R.id.txt_vl_compra_walltime);
        txt_vl_venda_walltime = view.findViewById(R.id.txt_vl_venda_walltime);
        txt_vl_compra_mercado_bitcoin = view.findViewById(R.id.txt_vl_compra_mercadobitcoin);
        txt_vl_venda_mercado_bitcoin = view.findViewById(R.id.txt_vl_venda_mercadobitcoin);
        txt_vl_compra_bitcointoyou = view.findViewById(R.id.txt_vl_compra_bitcointoyou);
        txt_vl_venda_bitcointoyou = view.findViewById(R.id.txt_vl_venda_bitcointoyou);
        txt_vl_compra_foxbit = view.findViewById(R.id.txt_vl_compra_foxbit);
        txt_vl_venda_foxbit = view.findViewById(R.id.txt_vl_venda_foxbit);
        txt_vl_compra_bitcointrade = view.findViewById(R.id.txt_vl_compra_bitcointrade);
        txt_vl_venda_bitcointrade = view.findViewById(R.id.txt_vl_venda_bitcointrade);
        txt_vl_compra_negociecoins = view.findViewById(R.id.txt_vl_compra_negociecoins);
        txt_vl_venda_negociecoins = view.findViewById(R.id.txt_vl_venda_negociecoins);

        btn_consultar = view.findViewById(R.id.btn_consultar);
        btn_consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volleyStringRequstWalltime(Util.STRING_WALLTIME);
                volleyStringRequestWalltime(Util.STRING_WALLTIME);
                volleyStringRequestMercadoBitcoin(Util.STRING_MERCADOBITCOIN);
                volleyStringRequstMercadoBitcoin(Util.STRING_MERCADOBITCOIN);
                volleyStringRequestBitCoinToYou(Util.STRING_BITCOINTOYOU);
                volleyStringRequstBitCoinToYou(Util.STRING_BITCOINTOYOU);
                volleyStringRequestFoxbit(Util.STRING_FOXBIT);
                volleyStringRequestBitcoinTradeCompra(Util.STRING_BITCOINTRADE);
                volleyStringRequestBitcoinTradeVenda(Util.STRING_BITCOINTRADE);
                volleyStringRequestCompra(Util.STRING_NEGOCIECOINS);
                volleyStringRequestVenda(Util.STRING_NEGOCIECOINS);
            }
        });

        btn_walltime = view.findViewById(R.id.btn_walltime);
        btn_walltime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, new FragWalltime()).commit();
            }
        });

        btn_mercado = view.findViewById(R.id.btn_mercadobitcoin);
        btn_mercado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, new FragMercadoBitCoin()).commit();
            }
        });

        btn_foxbit = view.findViewById(R.id.btn_foxbit);
        btn_foxbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, new FragFoxbit()).commit();
            }
        });

        btn_bitcointoyou = view.findViewById(R.id.btn_bitcointoyou);
        btn_bitcointoyou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, new FragBitcoinToYou()).commit();
            }
        });

        btn_bitcointrade = view.findViewById(R.id.btn_bitcointrade);
        btn_bitcointrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, new FragBitcoinTrade()).commit();
            }
        });

        btn_negociecoin = view.findViewById(R.id.btn_negociecoin);
        btn_negociecoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, new FragNegocieCoin()).commit();
            }
        });

        return view;
    }

    public void volleyStringRequstWalltime(String url) {
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
                        txt_vl_compra_walltime.setText(formato.format(total) + "\nvol: sem info");
                    } else {
                        double total = val2 / val1;
                        txt_vl_compra_walltime.setText(formato.format(total) + "\nvol: sem info");

                    }
                } else {
                    double total = Double.parseDouble(res);

                    txt_vl_compra_walltime.setText(formato.format(total));
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
                        txt_vl_venda_walltime.setText(formato.format(total));
                    } else {
                        double total = val1 / val2;
                        txt_vl_venda_walltime.setText(formato.format(total));

                    }
                } else {
                    double total = Double.parseDouble(res);

                    txt_vl_venda_walltime.setText(formato.format(total));
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

    public void volleyStringRequstMercadoBitcoin(String url) {
        String REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";

        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String teste = response;
                m = retornaObjeto(teste);

                double valor = m.getBuy();
                NumberFormat formato = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                txt_vl_compra_mercado_bitcoin.setText(formato.format(valor) + "\nvol:" + numeroFormatado(m.getVol()));
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
                txt_vl_venda_mercado_bitcoin.setText(formato.format(valor));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        // Adding String request to request queue
        AppSingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(strReq, REQUEST_TAG);
    }

    public void volleyStringRequestBitCoinToYou(String url) {

        String REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";
        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String teste = response;
                b = retornaObjetoBitCoinToYou(teste);
                NumberFormat formato = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                txt_vl_compra_bitcointoyou.setText(formato.format(b.getBuy()) + "\nvol:" + numeroFormatado(b.getVol()));
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
                txt_vl_venda_bitcointoyou.setText(formato.format(b.getSell()));
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
                System.out.println("teste: " + teste);
                try {

                    JSONObject jo = new JSONObject(teste);
                    JSONObject j = jo.getJSONObject("FOX");
                    //String a = j.get("bid").toString() + " - " + j.get("ask").toString();
                    double valor1 = Double.parseDouble(j.get("bid").toString());
                    double valor2 = Double.parseDouble(j.get("ask").toString());
                    double valor3 = Double.parseDouble(j.get("bid_vol").toString());

                    NumberFormat formato = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

                    txt_vl_compra_foxbit.setText(formato.format(valor1) + "\nvol:" + numeroFormatado(valor3));
                    txt_vl_venda_foxbit.setText(formato.format(valor2));


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


    public void volleyStringRequestBitcoinTradeCompra(String url) {
        String REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";

        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String teste = response;

                try {

                    JSONObject jo = new JSONObject(teste);
                    JSONObject j = jo.getJSONObject("data");
                    //String a = j.get("bid").toString() + " - " + j.get("ask").toString();
                    double valor1 = Double.parseDouble(j.get("buy").toString());
                    double valor2 = Double.parseDouble(j.get("volume").toString());
                    NumberFormat formato = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

                    txt_vl_compra_bitcointrade.setText(formato.format(valor1) + "\nvol:" + numeroFormatado(valor2));


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

                    txt_vl_venda_bitcointrade.setText(formato.format(valor1));


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

    public static String numeroFormatado(double valor) {
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
                    double vol = Double.parseDouble(jo.get("vol").toString());
                    txt_vl_compra_negociecoins.setText(formato.format(valor1) + "\nvol:" + numeroFormatado(vol));


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

                    txt_vl_venda_negociecoins.setText(formato.format(valor1));


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

}
