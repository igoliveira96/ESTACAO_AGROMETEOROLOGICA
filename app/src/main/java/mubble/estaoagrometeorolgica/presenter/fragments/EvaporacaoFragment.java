package mubble.estaoagrometeorolgica.presenter.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;

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
public class EvaporacaoFragment extends Fragment {

    private Activity activity;
    private String dataInicial;
    private String dataFinal;
    private LineChart chart;
    private LinearLayout layoutProgressBuscandoLL;
    private LinearLayout layoutErroLL;
    private FButton tentarNovamenteFB;

    public EvaporacaoFragment(){}

    public static EvaporacaoFragment newInstance(String dataInicial, String dataFinal) {
        EvaporacaoFragment fragment = new EvaporacaoFragment();
        Bundle args = new Bundle();
        args.putString("dataInicial", dataInicial);
        args.putString("dataFinal", dataFinal);
        fragment.setArguments(args);

        return fragment;
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
        final View view = inflater.inflate(R.layout.line_chart, container, false);

        chart = view.findViewById(R.id.lineChart);
        layoutProgressBuscandoLL = view.findViewById(R.id.layoutProgressBuscandoLL);
        layoutErroLL = view.findViewById(R.id.layoutErroLL);
        tentarNovamenteFB = view.findViewById(R.id.tenteNovamenteFB);

        tentarNovamenteFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutProgressBuscandoLL.setVisibility(View.VISIBLE);
                chart.setVisibility(View.GONE);
                layoutErroLL.setVisibility(View.GONE);

                carregarGrafico();
            }
        });

        carregarGrafico();

        return view;
    }

    private void carregarGrafico(){
        ConsultaDadosPresenter.getInstancia(activity).getGraficoEvaporacao(
                Tempo.getInstancia().getModeloDataServidor(dataInicial),
                Tempo.getInstancia().getModeloDataServidor(dataFinal),
                chart);
    }

    @Subscribe
    public void onMessageEvent(final MessageBus event){
        ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.SUCESSO;
        if(consultaDadosEnum.toString() == event.getMensagem() && getActivity() != null){
            this.layoutProgressBuscandoLL.setVisibility(View.GONE);
            this.chart.setVisibility(View.VISIBLE);
            this.layoutErroLL.setVisibility(View.GONE);
        }
        else {
            this.layoutProgressBuscandoLL.setVisibility(View.GONE);
            this.chart.setVisibility(View.GONE);
            this.layoutErroLL.setVisibility(View.VISIBLE);
        }
    }

}
