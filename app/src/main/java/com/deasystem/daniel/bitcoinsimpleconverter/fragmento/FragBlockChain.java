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

public class FragBlockChain extends Fragment {

    private AdView mAdView;
    private TextView txtBlockchain;

    public FragBlockChain() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_blockchain, container, false);
        MobileAds.initialize(this.getActivity().getApplicationContext(), Util.idSdk);
        AdView adView = new AdView(this.getActivity().getApplicationContext());
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(Util.idBanner);

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        txtBlockchain = view.findViewById(R.id.txtBlockchain);
        txtBlockchain.setText("O blockchain (também conhecido como “o protocolo da confiança”) é uma tecnologia que visa a descentralização como medida de segurança. São bases de registros e dados distribuídos e compartilhados que possuem a função de criar um índice global para todas as transações que ocorrem em um determinado mercado. Funciona como um livro-razão, só que de forma pública, compartilhada e universal, que cria consenso e confiança na comunicação direta entre duas partes, ou seja, sem o intermédio de terceiros. Está constantemente crescendo à medida que novos blocos completos são adicionados a ela por um novo conjunto de registros. Os blocos são adicionados à blockchain de modo linear e cronológico. Cada nó - qualquer computador que conectado à essa rede tem a tarefa de validar e repassar transações - obtém uma cópia da blockchain após o ingresso na rede. A blockchain possui informação completa sobre endereços e saldos diretamente do bloco gênese até o bloco mais recentemente concluído.\n" +
                "\n" +
                "A blockchain é vista como a principal inovação tecnológica do bitcoin visto que é a prova de todas as transações na rede. Seu projeto original tem servido de inspiração para o surgimento de novas criptomoedas e de bancos de dados distribuídos.");
        return view;
    }
}
