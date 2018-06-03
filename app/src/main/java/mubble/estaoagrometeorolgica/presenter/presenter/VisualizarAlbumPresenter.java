package mubble.estaoagrometeorolgica.presenter.presenter;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import mubble.estaoagrometeorolgica.presenter.adapter.ImgemAlbumAdapter;

/**
 * Criado por Igor Goulart de Oliveira em 02/06/2018.
 */

public class VisualizarAlbumPresenter {

    private Activity activity;
    private RecyclerView recyclerView;

    public VisualizarAlbumPresenter(Activity activity, RecyclerView recyclerView){
        this.activity = activity;
        this.recyclerView = recyclerView;
    }

    public void carregarImagens(String[] imagens){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 2);
        ImgemAlbumAdapter imgemAlbumAdapter = new ImgemAlbumAdapter(activity, imagens);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(imgemAlbumAdapter);
    }

}
