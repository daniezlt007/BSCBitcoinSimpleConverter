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

public class FragWallet extends Fragment {

    private AdView mAdView;
    private TextView txtWallet;

    public FragWallet() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_wallet, container, false);

        MobileAds.initialize(this.getActivity().getApplicationContext(), Util.idSdk);
        AdView adView = new AdView(this.getActivity().getApplicationContext());
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(Util.idBanner);

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        txtWallet = view.findViewById(R.id.txtWallet);
        txtWallet.setText("Uma carteira digital (digital wallet) é um software que armazena chaves públicas e privadas e interage com uma cadeias de blocos (blockchain) para permitir que o usuário envie e receba uma moeda digital, e monitorar seu saldo. Se você quiser usar Bitcoin ou qualquer outra criptomoeda, você precisará ter uma carteira digital.");
        return view;
    }

}
