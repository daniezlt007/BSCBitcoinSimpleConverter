package com.deasystem.daniel.bitcoinsimpleconverter.fragmento;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deasystem.daniel.bitcoinsimpleconverter.R;
import com.deasystem.daniel.bitcoinsimpleconverter.common.Util;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class FragBitcoin extends Fragment {

    private TextView txtBitcoin;
    private AdView mAdView;

    public FragBitcoin() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_bitcoin, container, false);
        txtBitcoin = view.findViewById(R.id.txtBitcoin);
        txtBitcoin.setText("Bitcoin (símbolo: Ƀ; abrev: BTC ou XBT , peer-to-peer electronic cash system) é uma moeda digital do tipo criptomoeda descentralizada, e também um sistema econômico alternativo, apresentada em 2008 na lista de discussão The Cryptography Mailing por um programador, ou um grupo de programadores, de pseudônimo Satoshi Nakamoto. É considerada a primeira moeda digital mundial descentralizada, e tida como responsável pelo ressurgimento do sistema bancário livre. O bitcoin permite a transação financeira sem intermediários, mas verificadas por todos os nós da rede Bitcoin peer-to-peer, que são gravadas em um banco de dados distribuídos, chamado de blockchain.");

        MobileAds.initialize(this.getActivity().getApplicationContext(), Util.idSdk);
        AdView adView = new AdView(this.getActivity().getApplicationContext());
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(Util.idBanner);

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        return view;
    }

}
