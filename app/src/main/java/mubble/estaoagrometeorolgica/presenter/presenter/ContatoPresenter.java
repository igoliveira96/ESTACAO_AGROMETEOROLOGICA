package mubble.estaoagrometeorolgica.presenter.presenter;

import android.app.Activity;
import android.support.v7.app.AlertDialog;

import com.rengwuxian.materialedittext.MaterialEditText;

import mubble.estaoagrometeorolgica.R;
import mubble.estaoagrometeorolgica.model.aplicacao.Alertas;
import mubble.estaoagrometeorolgica.model.webservice.RetrofitConfig;
import mubble.estaoagrometeorolgica.model.webservice.request.RequestContato;
import mubble.estaoagrometeorolgica.model.webservice.response.ResponseContato;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Criado por Igor Goulart de Oliveira em 03/06/2018.
 */

public class ContatoPresenter {

    private Activity activity;
    private AlertDialog progress;

    public ContatoPresenter(Activity activity){
        this.activity = activity;
    }

    public void enviarMensagem(final RequestContato requestContato, final MaterialEditText nome, final MaterialEditText email, final MaterialEditText mensagem){
        exibirProgressEnvio();
        Call<ResponseContato> call = new RetrofitConfig().getContatoService().enviarMensagem(requestContato);

        call.enqueue(new Callback<ResponseContato>() {
            @Override
            public void onResponse(Call<ResponseContato> call, Response<ResponseContato> response) {
                try {
                    ResponseContato responseContato = response.body();
                    cancelarProgressEnvio();

                    if(responseContato.success){
                        nome.setText("");
                        email.setText("");
                        mensagem.setText("");
                        Alertas.tapadoo(activity, "Sucesso", "E-mail enviado com sucesso", R.color.corVerde);
                    } else {
                        Alertas.tapadoo(activity, "Erro", "Erro ao enviar o e-mail", R.color.vermelho);
                    }
                }catch (Exception e){
                    cancelarProgressEnvio();
                    Alertas.tapadoo(activity, "Erro", "Erro ao enviar o e-mail", R.color.vermelho);
                }
            }

            @Override
            public void onFailure(Call<ResponseContato> call, Throwable t) {
                System.out.println(t.getMessage());
                cancelarProgressEnvio();
                Alertas.tapadoo(activity, "Erro", "Erro ao enviar o e-mail", R.color.vermelho);
            }
        });
    }

    private void exibirProgressEnvio(){
        progress = Alertas.progress(activity, "Enviando...");
    }

    private void cancelarProgressEnvio(){
        if(progress != null){
            progress.dismiss();
        }
    }

}
