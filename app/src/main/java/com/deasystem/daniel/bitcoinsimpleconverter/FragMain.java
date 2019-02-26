package com.deasystem.daniel.bitcoinsimpleconverter;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
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
import com.deasystem.daniel.bitcoinsimpleconverter.fragmento.FragBraziliex;
import com.deasystem.daniel.bitcoinsimpleconverter.fragmento.FragBroker;
import com.deasystem.daniel.bitcoinsimpleconverter.fragmento.FragMercadoBitCoin;
import com.deasystem.daniel.bitcoinsimpleconverter.fragmento.FragNegocieCoin;
import com.deasystem.daniel.bitcoinsimpleconverter.fragmento.FragWalltime;
import com.deasystem.daniel.bitcoinsimpleconverter.modelo.AppSingleton;
import com.deasystem.daniel.bitcoinsimpleconverter.modelo.BitcoinToYou;
import com.deasystem.daniel.bitcoinsimpleconverter.modelo.Brasiliex;
import com.deasystem.daniel.bitcoinsimpleconverter.modelo.MercadoBitCoin;
import com.deasystem.daniel.bitcoinsimpleconverter.modelo.RetWalltime;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragMain extends Fragment {

    private SwipeRefreshLayout swipe;
    private AdView mAdView;
    private MercadoBitCoin mercadoBitcoin;
    private BitcoinToYou bitcoinToYOu;
    private Button btn_walltime,
            btn_mercado, btn_braziliex,
            btn_bitcointoyou, btn_bitcointrade, btn_negociecoin, btn_broker;
    private TextView txt_vl_compra_walltime, txt_vl_venda_walltime, txt_vl_compra_mercado_bitcoin,
            txt_vl_venda_mercado_bitcoin, txt_vl_compra_bitcointoyou, txt_vl_venda_bitcointoyou,
            txt_vl_compra_braziliex, txt_vl_venda_braziliex, txt_vl_compra_bitcointrade, txt_vl_venda_bitcointrade,
            txt_vl_compra_negociecoins, txt_vl_venda_negociecoins,txt_vl_compra_broker, txt_vl_venda_broker;

    public FragMain() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        //Anúncio
        MobileAds.initialize(this.getActivity().getApplicationContext(), Util.idSdk);
        AdView adView = new AdView(this.getActivity().getApplicationContext());
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(Util.idBanner);

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        swipe = view.findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pegaValores();
                swipe.setRefreshing(false);
            }
        });

        pegaValores();


        txt_vl_compra_walltime = view.findViewById(R.id.txt_vl_compra_walltime);
        txt_vl_venda_walltime = view.findViewById(R.id.txt_vl_venda_walltime);
        txt_vl_compra_mercado_bitcoin = view.findViewById(R.id.txt_vl_compra_mercadobitcoin);
        txt_vl_venda_mercado_bitcoin = view.findViewById(R.id.txt_vl_venda_mercadobitcoin);
        txt_vl_compra_bitcointoyou = view.findViewById(R.id.txt_vl_compra_bitcointoyou);
        txt_vl_venda_bitcointoyou = view.findViewById(R.id.txt_vl_venda_bitcointoyou);
        txt_vl_compra_braziliex = view.findViewById(R.id.txt_vl_compra_foxbit);
        txt_vl_venda_braziliex = view.findViewById(R.id.txt_vl_venda_foxbit);
        txt_vl_compra_bitcointrade = view.findViewById(R.id.txt_vl_compra_bitcointrade);
        txt_vl_venda_bitcointrade = view.findViewById(R.id.txt_vl_venda_bitcointrade);
        txt_vl_compra_negociecoins = view.findViewById(R.id.txt_vl_compra_negociecoins);
        txt_vl_venda_negociecoins = view.findViewById(R.id.txt_vl_venda_negociecoins);

        txt_vl_compra_broker = view.findViewById(R.id.txt_vl_compra_broker);
        txt_vl_venda_broker = view.findViewById(R.id.txt_vl_venda_broker);

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

        btn_braziliex = view.findViewById(R.id.btn_braziliex);
        btn_braziliex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, new FragBraziliex()).commit();
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

        btn_broker = view.findViewById(R.id.btn_broker);
        btn_broker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, new FragBroker()).commit();
            }
        });

        return view;
    }

    public void pegaValores(){
        RequestWalltime(Util.STRING_WALLTIME);
        RequestWalltime2(Util.STRING_WALLTIME);
        RequestMercadoBitcoin(Util.STRING_MERCADOBITCOIN);
        RequestBitCoinToYou(Util.STRING_BITCOINTOYOU);
        RequestBrasiliex(Util.STRING_BRAZILIEX);
        RequestBitcoinTrade(Util.STRING_BITCOINTRADE);
        RequestNegocieCoins(Util.STRING_NEGOCIECOINS);
        Request3xBit(Util.STRING_3XBIT);
        RequestBroker(Util.STRING_BROKER);
    }

    public void RequestBrasiliex(String url) {
        String REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";

        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String json = response;
                Brasiliex brasiliex = Util.retornaObjetoBraziliex(json);
                double valorCompra = brasiliex.getLowestAsk();
                double valorVenda  = brasiliex.getHighestBid();
                double volume = brasiliex.getBaseVolume();

                txt_vl_venda_braziliex.setText(Util.numeroformatadoEmReal.format(valorVenda));
                txt_vl_compra_braziliex.setText(Util.numeroformatadoEmReal.format(valorCompra) + "\nvol:" + volume);

                // Toast.makeText(getApplicationContext(), "MSG:" , Toast.LENGTH_LONG).show();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipe.setRefreshing(false);
            }
        });

        // Adding String request to request queue
        AppSingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(strReq, REQUEST_TAG);
    }

    public void RequestWalltime(String url) {
        String REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";

        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String retorno = response;

                RetWalltime cotacao = Util.retornaObjetoWalltime(retorno);
                String res = cotacao.getRetWalltime2().getXbt_brl();
                if (res.contains("/")) {
                    String[] explode = res.split("/");
                    String v1 = explode[0];
                    String v2 = explode[1];
                    double val1 = Double.parseDouble(v1);
                    double val2 = Double.parseDouble(v2);
                    if (explode[1] == null || explode[1].isEmpty()) {
                        double total = val2 / 1;
                        txt_vl_compra_walltime.setText(Util.numeroformatadoEmReal.format(total) + "\nvol: sem info");
                    } else {
                        double total = val2 / val1;
                        txt_vl_compra_walltime.setText(Util.numeroformatadoEmReal.format(total) + "\nvol: sem info");

                    }
                } else {
                    double total = Double.parseDouble(res);

                    txt_vl_compra_walltime.setText(Util.numeroformatadoEmReal.format(total));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipe.setRefreshing(false);
            }
        });
        // Adding String request to request queue
        AppSingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(strReq, REQUEST_TAG);
    }

    public void RequestWalltime2(String url) {
        String REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";

        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String retorno = response;

                RetWalltime cotacao = Util.retornaObjetoWalltime(retorno);
                String res = cotacao.getRetWalltime2().getBrl_xbt();

                if (res.contains("/")) {
                    String[] explode = res.split("/");
                    String v1 = explode[0];
                    String v2 = explode[1];
                    double val1 = Double.parseDouble(v1);
                    double val2 = Double.parseDouble(v2);
                    if (explode[0] == null || explode[0].isEmpty()) {
                        double total = val1 / 1;
                        txt_vl_venda_walltime.setText(Util.numeroformatadoEmReal.format(total));
                    } else {
                        double total = val1 / val2;
                        txt_vl_venda_walltime.setText(Util.numeroformatadoEmReal.format(total));

                    }
                } else {
                    double total = Double.parseDouble(res);

                    txt_vl_venda_walltime.setText(Util.numeroformatadoEmReal.format(total));

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipe.setRefreshing(false);
            }
        });
        // Adding String request to request queue
        AppSingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(strReq, REQUEST_TAG);
    }

    public void RequestMercadoBitcoin(String url) {
        String REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";

        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String json = response;
                mercadoBitcoin = Util.retornaObjetoMercadoBitcoin(json);

                double valorCompra = mercadoBitcoin.getBuy();
                double valorVenda = mercadoBitcoin.getSell();

                txt_vl_compra_mercado_bitcoin.setText(Util.numeroformatadoEmReal.format(valorCompra) + "\nvol:" + Util.numeroFormatadoEmValorReal(mercadoBitcoin.getVol()));
                txt_vl_venda_mercado_bitcoin.setText(Util.numeroformatadoEmReal.format(valorVenda));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipe.setRefreshing(false);
            }
        });
        // Adding String request to request queue
        AppSingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(strReq, REQUEST_TAG);
    }

    public void RequestBitCoinToYou(String url) {

        String REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";
        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String json = response;
                bitcoinToYOu = Util.retornaObjetoBitCoinToYou(json);

                txt_vl_compra_bitcointoyou.setText(Util.numeroformatadoEmReal.format(bitcoinToYOu.getBuy()) + "\nvol:" + Util.numeroFormatadoEmValorReal(bitcoinToYOu.getVol()));
                txt_vl_venda_bitcointoyou.setText(Util.numeroformatadoEmReal.format(bitcoinToYOu.getSell()));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipe.setRefreshing(false);
            }
        });

        // Adding String request to request queue
        AppSingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(strReq, REQUEST_TAG);
    }

    public void RequestBitcoinTrade(String url) {
        String REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";

        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String json = response;

                try {

                    JSONObject jo = new JSONObject(json);
                    JSONObject j = jo.getJSONObject("data");

                    double valorCompra = Double.parseDouble(j.get("buy").toString());
                    double valorVenda = Double.parseDouble(j.get("sell").toString());
                    double volume = Double.parseDouble(j.get("volume").toString());

                    txt_vl_compra_bitcointrade.setText(Util.numeroformatadoEmReal.format(valorCompra) + "\nvol:" + Util.numeroFormatadoEmValorReal(volume));
                    txt_vl_venda_bitcointrade.setText(Util.numeroformatadoEmReal.format(valorVenda));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Toast.makeText(getApplicationContext(), "MSG:" , Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipe.setRefreshing(false);
            }
        });
        // Adding String request to request queue
        AppSingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(strReq, REQUEST_TAG);
    }

    public void RequestNegocieCoins(String url){
        String  REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";

        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String teste = response;

                try {

                    JSONObject jo = new JSONObject(teste);
                    double valorCompra = Double.parseDouble(jo.get("buy").toString());
                    double valorVenda = Double.parseDouble(jo.get("sell").toString());

                    double vol = Double.parseDouble(jo.get("vol").toString());
                    txt_vl_compra_negociecoins.setText(Util.numeroformatadoEmReal.format(valorCompra) + "\nvol:" + Util.numeroFormatadoEmValorReal(vol));
                    txt_vl_venda_negociecoins.setText(Util.numeroformatadoEmReal.format(valorVenda));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Toast.makeText(getApplicationContext(), "MSG:" , Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipe.setRefreshing(false);
            }
        });
        // Adding String request to request queue
        AppSingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(strReq, REQUEST_TAG);
    }

    public void RequestBroker(String url){
        String  REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";

        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String teste = response;

                try {

                    JSONObject jo = new JSONObject(teste);
                    double valorCompra = Double.parseDouble(jo.get("buy").toString());
                    double valorVenda = Double.parseDouble(jo.get("sell").toString());

                    double vol = Double.parseDouble(jo.get("vol").toString());
                    txt_vl_compra_broker.setText(Util.numeroformatadoEmReal.format(valorCompra) + "\nvol:" + Util.numeroFormatadoEmValorReal(vol));
                    txt_vl_venda_broker.setText(Util.numeroformatadoEmReal.format(valorVenda));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Toast.makeText(getApplicationContext(), "MSG:" , Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipe.setRefreshing(false);
            }
        });
        // Adding String request to request queue
        AppSingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(strReq, REQUEST_TAG);
    }


    public void Request3xBit(String url) {
        String REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";

        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String json = response;

                try {

                    JSONObject jo = new JSONObject(json);
                    JSONObject j = jo.getJSONObject("CREDIT_BTC");

                    double valorCompra = Double.parseDouble(j.get("bid").toString());
                    double valorVenda = Double.parseDouble(j.get("ask").toString());
                    double volume = Double.parseDouble(j.get("volume").toString());

                    String retorno = Util.numeroformatadoEmReal.format(valorCompra) + "\nvol:" + Util.numeroFormatadoEmValorReal(volume) + "\n";
                    retorno += Util.numeroformatadoEmReal.format(valorVenda);

                    Log.d("3XBIT", "Retorno: " + retorno);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Toast.makeText(getApplicationContext(), "MSG:" , Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipe.setRefreshing(false);
            }
        });
        // Adding String request to request queue
        AppSingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(strReq, REQUEST_TAG);
    }



}
