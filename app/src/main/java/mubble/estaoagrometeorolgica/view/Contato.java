package mubble.estaoagrometeorolgica.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import mubble.estaoagrometeorolgica.R;
import mubble.estaoagrometeorolgica.model.aplicacao.Alertas;
import mubble.estaoagrometeorolgica.model.webservice.request.RequestContato;
import mubble.estaoagrometeorolgica.presenter.presenter.ContatoPresenter;

public class Contato extends AppCompatActivity {

    @BindView(R.id.nomeUsuarioET)
    public MaterialEditText nome;
    @BindView(R.id.emailUsuarioET)
    public MaterialEditText email;
    @BindView(R.id.mensagemUsuarioET)
    public MaterialEditText mensagem;

    private ContatoPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        ButterKnife.bind(this, this);

        getSupportActionBar().setTitle("Entrar em contato");

        presenter = new ContatoPresenter(this);
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

    public void enviarMensagem(View view){
        boolean enviar = true;

        String nomeUsuario = nome.getText().toString().replace(" ", "");
        if(nomeUsuario.length() == 0){
            enviar = false;
            Alertas.exibirTooltip(nome, "Informe seu nome");
        }

        String emailUsuario = email.getText().toString().replace(" ", "");
        if(emailUsuario.length() == 0){
            enviar = false;
            Alertas.exibirTooltip(email, "Informe seu e-mail");
        }

        String mensagemUsuario = mensagem.getText().toString().replace(" ", "");
        if(mensagemUsuario.length() == 0){
            enviar = false;
            Alertas.exibirTooltip(mensagem, "Escreva sua mensagem");
        }

        if(enviar){
            RequestContato request = new RequestContato();
            request.nome = nome.getText().toString();
            request.email = email.getText().toString();
            request.mensagem = mensagem.getText().toString();

            presenter.enviarMensagem(request, nome, email, mensagem);
        }
    }

}
