package mubble.estaoagrometeorolgica.view;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import mubble.estaoagrometeorolgica.R;
import mubble.estaoagrometeorolgica.presenter.adapter.AbasAdapter;
import mubble.estaoagrometeorolgica.presenter.fragments.EvaporacaoFragment;
import mubble.estaoagrometeorolgica.presenter.fragments.EvaporacaoTimeLineFragment;
import mubble.estaoagrometeorolgica.presenter.fragments.InsolacaoFragment;
import mubble.estaoagrometeorolgica.presenter.fragments.InsolacaoTimeLineFragment;
import mubble.estaoagrometeorolgica.presenter.fragments.PrecipitacaoFragment;
import mubble.estaoagrometeorolgica.presenter.fragments.PrecipitacaoTimeLineFragment;
import mubble.estaoagrometeorolgica.presenter.fragments.TemperaturaFragment;
import mubble.estaoagrometeorolgica.presenter.fragments.TemperaturaTimeLineFragment;
import mubble.estaoagrometeorolgica.presenter.fragments.TermometroSecoFragment;
import mubble.estaoagrometeorolgica.presenter.fragments.TermometroSecoTimeLineFragment;
import mubble.estaoagrometeorolgica.presenter.fragments.UmidadeRelativaArFragment;
import mubble.estaoagrometeorolgica.presenter.fragments.UmidadeRelativaArTimeLineFragment;
import mubble.estaoagrometeorolgica.presenter.personalizacao_layout.SlidingTabLayout;
import mubble.estaoagrometeorolgica.presenter.personalizacao_layout.timeline.TimeLineAdapter;
import mubble.estaoagrometeorolgica.presenter.personalizacao_layout.timeline.TimeLineModel;

public class VisualizarDados extends AppCompatActivity {

    private String dataInicial;
    private String dataFinal;
    private String informacaoDesejada;
    private ViewPager viewPager;

    private TimeLineAdapter timeLineAdapter;
    private List<TimeLineModel> beneficios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_visualizar_dados);

        Bundle extras = getIntent().getExtras();
        dataInicial = extras.getString("dataInicial");
        dataFinal = extras.getString("dataFinal");
        informacaoDesejada = extras.getString("tipoInformacao");

        // ADICIONANDO UM TOOLBAR PERSONALIZADO:
        Toolbar toolbar = findViewById(R.id.toolbarVisualizarDados);
        toolbar.setTitle(informacaoDesejada);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        carregarPaginas(getSupportFragmentManager());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void carregarPaginas(FragmentManager fragmentManager){
        viewPager = findViewById(R.id.paginas);
        viewPager.setAdapter(new AbasAdapter(fragmentManager, getListaFragments(), getAbas()));

        SlidingTabLayout slidingTabLayout = findViewById(R.id.abasBarra);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        slidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.branco));
        slidingTabLayout.setViewPager(viewPager);
    }

    private List<Fragment> getListaFragments(){
        List<Fragment> fragmentList = new ArrayList<>();
        switch(informacaoDesejada){
            case "Temperatura":
                TemperaturaTimeLineFragment temperaturaTimeLineFragment = TemperaturaTimeLineFragment.newInstance(dataInicial, dataFinal);
                TemperaturaFragment temperaturaFragment = TemperaturaFragment.newInstance(dataInicial, dataFinal);

                fragmentList.add(temperaturaTimeLineFragment);
                fragmentList.add(temperaturaFragment);
                break;
            case "Insolação":
                InsolacaoTimeLineFragment insolacaoTimeLineFragment = InsolacaoTimeLineFragment.newInstance(dataInicial, dataFinal);
                InsolacaoFragment insolacaoFragment = InsolacaoFragment.newInstance(dataInicial, dataFinal);

                fragmentList.add(insolacaoTimeLineFragment);
                fragmentList.add(insolacaoFragment);
                break;
            case "Termômetro Seco":
                TermometroSecoTimeLineFragment termometroSecoTimeLineFragment = TermometroSecoTimeLineFragment.newInstance(dataInicial, dataFinal);
                TermometroSecoFragment termometroSecoFragment = TermometroSecoFragment.newInstance(dataInicial, dataFinal);

                fragmentList.add(termometroSecoTimeLineFragment);
                fragmentList.add(termometroSecoFragment);
                break;
            case "Umidade Relativa do Ar":
                UmidadeRelativaArTimeLineFragment umidadeRelativaArTimeLineFragment = UmidadeRelativaArTimeLineFragment.newInstance(dataInicial, dataFinal);
                UmidadeRelativaArFragment umidadeRelativaArFragment = UmidadeRelativaArFragment.newInstance(dataInicial, dataFinal);

                fragmentList.add(umidadeRelativaArTimeLineFragment);
                fragmentList.add(umidadeRelativaArFragment);
                break;
            case "Evaporação":
                EvaporacaoTimeLineFragment evaporacaoTimeLineFragment = EvaporacaoTimeLineFragment.newInstance(dataInicial, dataFinal);
                EvaporacaoFragment evaporacaoFragment = EvaporacaoFragment.newInstance(dataInicial, dataFinal);

                fragmentList.add(evaporacaoTimeLineFragment);
                fragmentList.add(evaporacaoFragment);
                break;
            case "Precipitação":
                PrecipitacaoTimeLineFragment precipitacaoTimeLineFragment = PrecipitacaoTimeLineFragment.newInstance(dataInicial, dataFinal);
                PrecipitacaoFragment precipitacaoFragment = PrecipitacaoFragment.newInstance(dataInicial, dataFinal);

                fragmentList.add(precipitacaoTimeLineFragment);
                fragmentList.add(precipitacaoFragment);
                break;
        }

        return fragmentList;
    }

    private String[] getAbas(){
        String[] abas = new String[2];
        abas[0] = "DADOS";
        abas[1] = "GRÁFICO";
        return abas;
    }

}
