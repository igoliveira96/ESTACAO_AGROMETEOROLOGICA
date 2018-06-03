package mubble.estaoagrometeorolgica.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import mubble.estaoagrometeorolgica.R;
import mubble.estaoagrometeorolgica.presenter.presenter.VisualizarAlbumPresenter;

public class VisualizarAlbum extends AppCompatActivity {

    private String nomeAlbum;
    private String[] imagens;
    private VisualizarAlbumPresenter presenter;

    @BindView(R.id.recyclerViewImagensAlbum)
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_album);

        ButterKnife.bind(this, this);

        Intent intent = getIntent();
        nomeAlbum = intent.getStringExtra("nomeAlbum");
        imagens = intent.getStringArrayExtra("imagens");

        getSupportActionBar().setTitle(nomeAlbum);

        presenter = new VisualizarAlbumPresenter(this, recyclerView);

        presenter.carregarImagens(imagens);
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
