package mubble.estaoagrometeorolgica.presenter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.mikhaellopez.circularimageview.CircularImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import mubble.estaoagrometeorolgica.R;
import mubble.estaoagrometeorolgica.presenter.personalizacao_layout.timeline.ItemClickListener;

/**
 * Criado por Igor Goulart de Oliveira em 02/06/2018.
 */

public class AlbumHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.nomeAlbumTV)
    public TextView nomeAlbumTV;
    @BindView(R.id.dataCriacaoAlbumTV)
    public TextView dataCriacaoAlbumTV;
    @BindView(R.id.capaAlbumIV)
    public ImageView capaAlbumIV;

    private ItemClickListener itemClickListener;

    public AlbumHolder(View itemView) {
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
