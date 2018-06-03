package mubble.estaoagrometeorolgica.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.hoang8f.widget.FButton;
import mubble.estaoagrometeorolgica.R;
import mubble.estaoagrometeorolgica.presenter.presenter.AlbunsPresenter;

public class Albuns extends AppCompatActivity {

    private AlbunsPresenter presenter;

    @BindView(R.id.recyclerViewAlbuns)
    public RecyclerView recyclerView;

    @BindView(R.id.layoutProgressBuscandoLL)
    public LinearLayout layoutProgressBuscandoLL;
    @BindView(R.id.layoutMensagemErroConsultaLL)
    public LinearLayout layoutMensagemErroConsultaLL;
    @BindView(R.id.tenteNovamenteLayoutErroFB)
    public FButton tenteNovamenteLayoutErroFB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albuns);

        ButterKnife.bind(this, this);

        getSupportActionBar().setTitle("Galeria");

        presenter = new AlbunsPresenter(this, recyclerView);

        presenter.carregarAlbuns(layoutProgressBuscandoLL, layoutMensagemErroConsultaLL, tenteNovamenteLayoutErroFB);
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

}
