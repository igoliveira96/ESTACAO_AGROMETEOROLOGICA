package mubble.estaoagrometeorolgica.presenter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import mubble.estaoagrometeorolgica.R;
import mubble.estaoagrometeorolgica.presenter.personalizacao_layout.timeline.ItemClickListener;

/**
 * Criado por Igor Goulart de Oliveira em 02/06/2018.
 */

public class ImagemAlbumHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.imagemAlbumIV)
    public ImageView imagemAlbumIV;

    private ItemClickListener itemClickListener;

    public ImagemAlbumHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition());
    }

}