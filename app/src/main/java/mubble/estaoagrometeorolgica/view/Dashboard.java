package mubble.estaoagrometeorolgica.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import mubble.estaoagrometeorolgica.R;
import mubble.estaoagrometeorolgica.model.Imagem;
import mubble.estaoagrometeorolgica.model.constantes.ConsultaDadosEnum;
import mubble.estaoagrometeorolgica.model.eventbus.EventBusNotificao;
import mubble.estaoagrometeorolgica.model.tempo.Tempo;
import mubble.estaoagrometeorolgica.model.webservice.RetrofitConfig;
import mubble.estaoagrometeorolgica.model.webservice.response.ResponseAlbum;
import mubble.estaoagrometeorolgica.model.webservice.response.ResponseTemperatura;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.sobreAppIM) {
            Intent intent = new Intent(this, SobreApp.class);
            startActivity(intent);
        }
        return false;
    }

    public void abrirConsultaDados(View view){
        Intent intent = new Intent(this, ConsultaDados.class);
        startActivity(intent);
    }

    public void abrirAlbuns(View view){
        Intent intent = new Intent(this, Albuns.class);
        startActivity(intent);
    }

    public void abrirEntrarContato(View view){
        Intent intent = new Intent(this, Contato.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }

}
