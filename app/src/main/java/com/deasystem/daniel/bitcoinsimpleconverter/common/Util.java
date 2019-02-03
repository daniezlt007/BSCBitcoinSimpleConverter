package com.deasystem.daniel.bitcoinsimpleconverter.common;

import com.deasystem.daniel.bitcoinsimpleconverter.modelo.BitcoinToYou;
import com.deasystem.daniel.bitcoinsimpleconverter.modelo.Brasiliex;
import com.deasystem.daniel.bitcoinsimpleconverter.modelo.MercadoBitCoin;
import com.deasystem.daniel.bitcoinsimpleconverter.modelo.RetWalltime;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Util {

    public static final String STRING_BITCOINTOYOU = "https://www.bitcointoyou.com/api/ticker.aspx";
    public static final String STRING_MERCADOBITCOIN = "https://www.mercadobitcoin.net/api/btc/ticker/";
    public static final String STRING_WALLTIME = "https://s3.amazonaws.com/data-production-walltime-info/production/dynamic/meta.json";
    public static final String STRING_BITCOINTRADE = "https://api.bitcointrade.com.br/v1/public/BTC/ticker";
    public static final String STRING_NEGOCIECOINS  = "https://broker.negociecoins.com.br/api/v3/btcbrl/ticker";

    public static final String STRING_BRAZILIEX = "https://braziliex.com/api/v1/public/ticker/btc_brl";
    public static final String STRING_3XBIT = "https://api.exchange.3xbit.com.br/ticker/brl";

    public static final String idBanner = "ca-app-pub-1974086740128373/9663553135";
    public static final String idSdk = "ca-app-pub-1974086740128373~6640975025";

    public static final NumberFormat numeroformatadoEmReal = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    //Métodos auxiliares

    public static String numeroFormatadoEmValorReal(double valor) {
        DecimalFormat dc = new DecimalFormat("#,##0.00");
        return dc.format(valor);
    }

    public static String valorEmBtcFormatado(double valor) {
        DecimalFormat dc = new DecimalFormat("#,##0.0000000");
        return dc.format(valor);
    }

    public static double converterRealBtc(double v1, double v2) {
        return v1 / v2;
    }

    public static double converterBtcReal(double v1, double v2) {
        return v1 * v2;
    }

    public static double calcularTotal(double v1, double v2){
        return v1 - v2;
    }
    //retorna objetos

    public static Brasiliex retornaObjetoBraziliex(String json){
        Gson gson = new Gson();
        Brasiliex brasiliex = gson.fromJson(json, Brasiliex.class);
        return  brasiliex;
    }

    public static RetWalltime retornaObjetoWalltime(String json) {
        Gson gson = new Gson();
        RetWalltime cotacao = gson.fromJson(json, RetWalltime.class);
        return cotacao;
    }

    public static MercadoBitCoin retornaObjetoMercadoBitcoin(String js) {
        MercadoBitCoin mercadoBitcoin = new MercadoBitCoin();
        try {
            JSONObject json = new JSONObject(js);

            JSONObject d = json.getJSONObject("ticker");
            mercadoBitcoin.setBuy(Double.parseDouble(d.getString("buy")));
            mercadoBitcoin.setSell(Double.parseDouble(d.getString("sell")));
            mercadoBitcoin.setVol(Double.parseDouble(d.getString("vol")));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mercadoBitcoin;
    }

    public static BitcoinToYou retornaObjetoBitCoinToYou(String js) {
        BitcoinToYou bitcoinToYOu = new BitcoinToYou();
        double valor = 0;
        try {
            JSONObject json = new JSONObject(js);
            JSONObject d = json.getJSONObject("ticker");
            bitcoinToYOu.setBuy(Double.parseDouble(d.getString("buy")));
            bitcoinToYOu.setSell(Double.parseDouble(d.getString("sell")));
            bitcoinToYOu.setVol(Double.parseDouble(d.getString("vol")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bitcoinToYOu;
    }


}
