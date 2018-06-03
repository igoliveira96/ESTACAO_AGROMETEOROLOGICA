package mubble.estaoagrometeorolgica.presenter.personalizacao_layout.timeline;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.vipulasri.timelineview.TimelineView;

import butterknife.BindView;
import butterknife.ButterKnife;
import mubble.estaoagrometeorolgica.R;

/**
 * Criado por Igor em 27/10/2017.
 */

public class TimeLineViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.texto_timeline)
    TextView texto;
    @BindView(R.id.subtitulo)
    TextView tituloLinha;
    @BindView(R.id.cardItemLinha)
    CardView itemCard;

    public TimelineView mTimelineView;

    public TimeLineViewHolder(View itemView, int viewType) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        mTimelineView = itemView.findViewById(R.id.time_marker);
        mTimelineView.initLine(viewType);
    }
}
