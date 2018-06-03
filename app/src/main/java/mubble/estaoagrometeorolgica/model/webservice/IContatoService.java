package mubble.estaoagrometeorolgica.model.webservice;

import mubble.estaoagrometeorolgica.model.webservice.request.RequestContato;
import mubble.estaoagrometeorolgica.model.webservice.response.ResponseContato;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Criado por Igor Goulart de Oliveira em 03/06/2018.
 */

public interface IContatoService {

    @POST("api/site-contato/contatar")
    Call<ResponseContato> enviarMensagem(@Body RequestContato requestContato);

}
