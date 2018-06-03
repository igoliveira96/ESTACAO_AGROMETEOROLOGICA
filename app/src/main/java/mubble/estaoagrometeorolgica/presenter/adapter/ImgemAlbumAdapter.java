package mubble.estaoagrometeorolgica.presenter.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.squareup.picasso.Picasso;
import com.stfalcon.frescoimageviewer.ImageViewer;

import mubble.estaoagrometeorolgica.R;
import mubble.estaoagrometeorolgica.presenter.holder.ImagemAlbumHolder;
import mubble.estaoagrometeorolgica.presenter.personalizacao_layout.timeline.ItemClickListener;

/**
 * Criado por Igor Goulart de Oliveira em 02/06/2018.
 */

public class ImgemAlbumAdapter extends RecyclerView.Adapter<ImagemAlbumHolder> {

    private Activity activity;
    String[] imagens;

    public ImgemAlbumAdapter(Activity activity, String[] imagens){
        this.activity = activity;
        this.imagens = imagens;
    }

    @NonNull
    @Override
    public ImagemAlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImagemAlbumHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_album_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImagemAlbumHolder holder, int position) {
        final String imagem = imagens[position];

        Picasso.get().load(imagem)
                .placeholder(R.drawable.padrao)
                .error(R.drawable.nao_encontrada)
                .into(holder.imagemAlbumIV);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(imagens.length > 0){
                    GenericDraweeHierarchyBuilder hierarchyBuilder = GenericDraweeHierarchyBuilder.newInstance(activity.getResources())
                            .setFailureImage(R.drawable.nao_encontrada)
                            .setProgressBarImage(R.drawable.padrao)
                            .setPlaceholderImage(R.drawable.padrao);

                    new ImageViewer.Builder(activity, imagens)
                            .setStartPosition(position)
                            .setCustomDraweeHierarchyBuilder(hierarchyBuilder)
                            .show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return imagens != null ? imagens.length : 0;
    }

}

