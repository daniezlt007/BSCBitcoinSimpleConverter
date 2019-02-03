package com.deasystem.daniel.bitcoinsimpleconverter;

import com.deasystem.daniel.bitcoinsimpleconverter.common.Util;
import com.deasystem.daniel.bitcoinsimpleconverter.modelo.BitcoinToYou;
import com.deasystem.daniel.bitcoinsimpleconverter.modelo.Brasiliex;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InterfaceConsulta {

    @GET("ticker/{moeda}")
    Call<Brasiliex> getTicker(@Path("moeda") String moeda);

    public final static Retrofit retrofit = new Retrofit.Builder().
            baseUrl(Util.STRING_BRAZILIEX).
            addConverterFactory(GsonConverterFactory.create()).
            build();

}
