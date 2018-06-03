package mubble.estaoagrometeorolgica.presenter.presenter;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

import info.hoang8f.widget.FButton;
import mubble.estaoagrometeorolgica.R;
import mubble.estaoagrometeorolgica.model.Imagem;
import mubble.estaoagrometeorolgica.model.webservice.RetrofitConfig;
import mubble.estaoagrometeorolgica.model.webservice.response.ResponseAlbum;
import mubble.estaoagrometeorolgica.presenter.adapter.AlbunsAdapter;
import mubble.estaoagrometeorolgica.presenter.personalizacao_layout.ItemOffsetDecoration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Criado por Igor Goulart de Oliveira em 02/06/2018.
 */

public class AlbunsPresenter {

    private Activity activity;
    private RecyclerView recyclerView;

    public AlbunsPresenter(Activity activity, RecyclerView recyclerView){
        this.activity = activity;
        this.recyclerView = recyclerView;
    }

    public void carregarAlbuns(final LinearLayout layoutProgressBuscandoLL, final LinearLayout layoutMensagemErroConsultaLL, final FButton tenteNovamenteLayoutErroFB){
        Call<List<ResponseAlbum>> call = new RetrofitConfig().getAlbumService().getAlbuns();

        call.enqueue(new Callback<List<ResponseAlbum>>() {
            @Override
            public void onResponse(Call<List<ResponseAlbum>> call, Response<List<ResponseAlbum>> response) {
                try {
                    List<ResponseAlbum> albuns = response.body();
                    carregarListaAlbuns(albuns);

                    layoutProgressBuscandoLL.setVisibility(View.GONE);
                    layoutMensagemErroConsultaLL.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }catch (Exception e){
                    exibirLayoutErro(layoutProgressBuscandoLL, layoutMensagemErroConsultaLL, tenteNovamenteLayoutErroFB);
                }
            }

            @Override
            public void onFailure(Call<List<ResponseAlbum>> call, Throwable t) {
                exibirLayoutErro(layoutProgressBuscandoLL, layoutMensagemErroConsultaLL, tenteNovamenteLayoutErroFB);
            }
        });
    }

    private void exibirLayoutErro(final LinearLayout layoutProgressBuscandoLL, final LinearLayout layoutMensagemErroConsultaLL, final FButton tenteNovamenteLayoutErroFB){
        layoutProgressBuscandoLL.setVisibility(View.GONE);
        layoutMensagemErroConsultaLL.setVisibility(View.VISIBLE);
        tenteNovamenteLayoutErroFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutProgressBuscandoLL.setVisibility(View.VISIBLE);
                layoutMensagemErroConsultaLL.setVisibility(View.GONE);
                carregarAlbuns(layoutProgressBuscandoLL, layoutMensagemErroConsultaLL, tenteNovamenteLayoutErroFB);
            }
        });
    }

    private void carregarListaAlbuns(List<ResponseAlbum> albums){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 2);
        AlbunsAdapter albunsAdapter = new AlbunsAdapter(activity, albums);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(albunsAdapter);
    }

}
