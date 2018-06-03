package mubble.estaoagrometeorolgica.presenter.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.ArrayList;
import java.util.List;

import mubble.estaoagrometeorolgica.R;
import mubble.estaoagrometeorolgica.model.Imagem;
import mubble.estaoagrometeorolgica.model.aplicacao.Alertas;
import mubble.estaoagrometeorolgica.model.webservice.response.ResponseAlbum;
import mubble.estaoagrometeorolgica.presenter.holder.AlbumHolder;
import mubble.estaoagrometeorolgica.presenter.personalizacao_layout.timeline.ItemClickListener;
import mubble.estaoagrometeorolgica.view.VisualizarAlbum;

/**
 * Criado por Igor Goulart de Oliveira em 02/06/2018.
 */

public class AlbunsAdapter extends RecyclerView.Adapter<AlbumHolder> {

    private Activity activity;
    List<ResponseAlbum> albuns;

    public AlbunsAdapter(Activity activity, List<ResponseAlbum> albuns){
        this.activity = activity;
        this.albuns = albuns;
    }

    @NonNull
    @Override
    public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AlbumHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumHolder holder, int position) {
        final ResponseAlbum album = albuns.get(position);
        String[] imagens = new String[0];

        if(album.imagemCapa != null){
            Picasso.get().load("http://200.201.11.14/estacao/"+album.imagemCapa.caminho)
                    .placeholder(R.drawable.padrao)
                    .error(R.drawable.nao_encontrada)
                    .into(holder.capaAlbumIV);
        }

        if(album.imagens != null && album.imagens.length > 0){
            imagens = new String[album.imagens.length];
            for(int pos = 0; pos < album.imagens.length; pos++){
                imagens[pos] = "http://200.201.11.14/estacao/"+album.imagens[pos].caminho;
            }
        }

        holder.nomeAlbumTV.setText(album.nome);
        holder.dataCriacaoAlbumTV.setText(album.dataCriacao);

        final String[] finalImagens = imagens;
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(finalImagens.length > 0){
                    Intent intent = new Intent(activity, VisualizarAlbum.class);
                    intent.putExtra("nomeAlbum", album.nome);
                    intent.putExtra("imagens", finalImagens);
                    activity.startActivity(intent);
                } else {
                    Alertas.tapadoo(activity, "Sem Imagens", "Este álbum ainda não possui imagens", R.color.laranja_medio_grafico);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return albuns != null ? albuns.size() : 0;
    }

}
