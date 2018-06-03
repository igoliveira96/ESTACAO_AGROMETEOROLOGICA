package mubble.estaoagrometeorolgica.presenter.personalizacao_layout.timeline;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.vipulasri.timelineview.TimelineView;

import java.util.List;

import mubble.estaoagrometeorolgica.R;
import mubble.estaoagrometeorolgica.presenter.personalizacao_layout.timeline.utils.VectorDrawableUtils;

/**
 * Criado por Igor em 27/10/2017.
 */

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineViewHolder> {

    private List<TimeLineModel> linhaTempo;
    private Context context;
    private LayoutInflater layoutInflater;

    public TimeLineAdapter(List<TimeLineModel> linhaTempo){
        this.linhaTempo = linhaTempo;
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        layoutInflater = LayoutInflater.from(context);
        View view;

        /*if(orientation == Orientation.HORIZONTAL) {
            view = layoutInflater.inflate(mWithLinePadding ? R.layout.item_timeline_horizontal_line_padding : R.layout.item_timeline_horizontal, parent, false);
        } else {
            view = layoutInflater.inflate(mWithLinePadding ? R.layout.item_timeline_line_padding : R.layout.item_timeline, parent, false);
        }*/

        view = layoutInflater.inflate(R.layout.item_timeline, parent, false);

        return new TimeLineViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {
        TimeLineModel timeLineModel = linhaTempo.get(position);

        if(timeLineModel.getOrderStatus() == OrderStatus.INACTIVE) {
            holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(context, R.drawable.ic_marker_inactive, android.R.color.darker_gray));
        } else if(timeLineModel.getOrderStatus() == OrderStatus.ACTIVE) {
            holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(context, R.drawable.ic_marker_active, R.color.colorAccent));
        } else {
            holder.mTimelineView.setMarker(ContextCompat.getDrawable(context, R.drawable.ic_marker), ContextCompat.getColor(context, R.color.colorAccent));
        }

        holder.tituloLinha.setText(timeLineModel.getTitulo());
        holder.texto.setText(timeLineModel.getTexto());

        holder.tituloLinha.setTextColor(context.getResources().getColor(R.color.colorPrimaryText));
        holder.tituloLinha.setAllCaps(true);
        if(timeLineModel.getOrderStatus() != OrderStatus.ACTIVE){
            holder.tituloLinha.setTypeface(holder.tituloLinha.getTypeface(), Typeface.NORMAL);
            holder.texto.setVisibility(View.GONE);
            holder.itemCard.setCardBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            holder.tituloLinha.setTextColor(context.getResources().getColor(android.R.color.white));
        }
        else{
            holder.tituloLinha.setTypeface(holder.tituloLinha.getTypeface(), Typeface.ITALIC);
            holder.texto.setVisibility(View.VISIBLE);
            holder.itemCard.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }

    @Override
    public int getItemCount() {
        return linhaTempo != null ? linhaTempo.size() : 0;
    }
}
