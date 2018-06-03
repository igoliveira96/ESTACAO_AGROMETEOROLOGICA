package mubble.estaoagrometeorolgica.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import mubble.estaoagrometeorolgica.R;
import mubble.estaoagrometeorolgica.model.aplicacao.Alertas;
import mubble.estaoagrometeorolgica.presenter.personalizacao_layout.Mask;

public class ConsultaDados extends AppCompatActivity {

    @BindView(R.id.dataInicialET)
    public MaterialEditText dataInicialET;
    @BindView(R.id.dataFinalET)
    public MaterialEditText dataFinalET;
    @BindView(R.id.informacaoDesejadaSPN)
    public Spinner informacaoDesejadaSPN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_dados);

        getSupportActionBar().setTitle("Consultar dados");
        ButterKnife.bind(this, this);

        dataInicialET.addTextChangedListener(Mask.insert("##/##/####", dataInicialET));
        dataFinalET.addTextChangedListener(Mask.insert("##/##/####", dataFinalET));

        setListaInformacoes();
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

    private void setListaInformacoes(){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.tipo_informacao));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        informacaoDesejadaSPN.setAdapter(dataAdapter);
    }

    private String getInformacaoDesejada(){
        return informacaoDesejadaSPN.getSelectedItem().toString();
    }

    public void buscarDados(View view){
        boolean consultar = true;
        if(dataInicialET.getText().length() != 10){
            Alertas.exibirTooltip(dataInicialET, "Informe uma data válida");
            consultar = false;
        }

        if(dataFinalET.getText().length() != 10){
            Alertas.exibirTooltip(dataFinalET, "Informe uma data válida");
            consultar = false;
        }

        if(consultar){
            String dataInicial = dataInicialET.getText().toString();
            String dataFinal = dataFinalET.getText().toString();

            Intent intent = new Intent(this, VisualizarDados.class);
            intent.putExtra("dataInicial", dataInicial);
            intent.putExtra("dataFinal", dataFinal);
            intent.putExtra("tipoInformacao", getInformacaoDesejada());
            startActivity(intent);
        }

    }

}
