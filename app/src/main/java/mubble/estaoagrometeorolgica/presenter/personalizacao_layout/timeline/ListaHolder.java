package mubble.estaoagrometeorolgica.presenter.personalizacao_layout.timeline;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Criado por Igor em 13/10/2017.
 */

public class ListaHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView numero_passo;
    TextView nome_passo;
    private ItemClickListener itemClickListener;

    public ListaHolder(View itemView) {
        super(itemView);

        //numero_passo = (TextView) itemView.findViewById(R.id.numero_passo);
        //nome_passo = (TextView) itemView.findViewById(R.id.texto_passo);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener onClick){
        this.itemClickListener = onClick;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition());
    }
}
