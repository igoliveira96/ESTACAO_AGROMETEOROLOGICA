package mubble.estaoagrometeorolgica.presenter.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import info.hoang8f.widget.FButton;
import mubble.estaoagrometeorolgica.R;
import mubble.estaoagrometeorolgica.model.constantes.ConsultaDadosEnum;
import mubble.estaoagrometeorolgica.model.eventbus.MessageBus;
import mubble.estaoagrometeorolgica.model.tempo.Tempo;
import mubble.estaoagrometeorolgica.presenter.presenter.ConsultaDadosPresenter;

/**
 * Criado por Igor em 02/06/2018.
 */

public class TemperaturaTimeLineFragment extends Fragment {

    private Activity activity;
    private String dataInicial;
    private String dataFinal;
    private RecyclerView recyclerView;
    private LinearLayout layoutProgressBuscandoLL;
    private LinearLayout layoutErroLL;
    private FButton tentarNovamenteFB;

    public TemperaturaTimeLineFragment(){}

    public static TemperaturaTimeLineFragment newInstance(String dataInicial, String dataFinal) {
        TemperaturaTimeLineFragment timeLineFragment = new TemperaturaTimeLineFragment();
        Bundle args = new Bundle();
        args.putString("dataInicial", dataInicial);
        args.putString("dataFinal", dataFinal);
        timeLineFragment.setArguments(args);

        return timeLineFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataInicial = getArguments().getString("dataInicial");
        dataFinal = getArguments().getString("dataFinal");

        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity){
            activity =(Activity) context;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dados_timeline, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewDados);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        layoutProgressBuscandoLL = view.findViewById(R.id.layoutProgressBuscandoTimeLineLL);
        layoutErroLL = view.findViewById(R.id.layoutErroTimeLineLL);
        tentarNovamenteFB = view.findViewById(R.id.tenteNovamenteTimeLineFB);

        tentarNovamenteFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutProgressBuscandoLL.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                layoutErroLL.setVisibility(View.GONE);

                carregarDados();
            }
        });

        carregarDados();

        return view;
    }

    private void carregarDados(){
        ConsultaDadosPresenter.getInstancia(activity).getTimeLineTemperatura(
                Tempo.getInstancia().getModeloDataServidor(dataInicial),
                Tempo.getInstancia().getModeloDataServidor(dataFinal),
                recyclerView);
    }

    @Subscribe
    public void onMessageEvent(final MessageBus event){
        ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.SUCESSO;
        if(consultaDadosEnum.toString() == event.getMensagem() && getActivity() != null){
            this.layoutProgressBuscandoLL.setVisibility(View.GONE);
            this.recyclerView.setVisibility(View.VISIBLE);
            this.layoutErroLL.setVisibility(View.GONE);
        }
        else {
            this.layoutProgressBuscandoLL.setVisibility(View.GONE);
            this.recyclerView.setVisibility(View.GONE);
            this.layoutErroLL.setVisibility(View.VISIBLE);
        }
    }

}