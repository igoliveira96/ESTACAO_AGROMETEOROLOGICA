package mubble.estaoagrometeorolgica.model.webservice;

import java.util.List;

import mubble.estaoagrometeorolgica.model.webservice.response.ResponseEvaporacao;
import mubble.estaoagrometeorolgica.model.webservice.response.ResponseInsolacao;
import mubble.estaoagrometeorolgica.model.webservice.response.ResponsePrecipitacao;
import mubble.estaoagrometeorolgica.model.webservice.response.ResponseTemperatura;
import mubble.estaoagrometeorolgica.model.webservice.response.ResponseTermometroSeco;
import mubble.estaoagrometeorolgica.model.webservice.response.ResponseUmidadeRelativaAr;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Criado por Igor em 11/03/2018.
 */
public interface IDadosService {

    @GET("api/temperatura/period/{dataInicial}/{dataFinal}")
    Call<List<ResponseTemperatura>> getTemperaturas(
            @Path("dataInicial") String dataInicial,
            @Path("dataFinal") String dataFinal
    );

    @GET("api/insolacao/period/{dataInicial}/{dataFinal}")
    Call<List<ResponseInsolacao>> getInsolacoes(
            @Path("dataInicial") String dataInicial,
            @Path("dataFinal") String dataFinal
    );

    @GET("api/termometro-seco/period/{dataInicial}/{dataFinal}")
    Call<List<ResponseTermometroSeco>> getTermometroSeco(
            @Path("dataInicial") String dataInicial,
            @Path("dataFinal") String dataFinal
    );

    @GET("api/umidade-relativa-ar/period/{dataInicial}/{dataFinal}")
    Call<List<ResponseUmidadeRelativaAr>> getUmidadeRelativaAr(
            @Path("dataInicial") String dataInicial,
            @Path("dataFinal") String dataFinal
    );

    @GET("api/evaporacao/period/{dataInicial}/{dataFinal}")
    Call<List<ResponseEvaporacao>> getEvaporacao(
            @Path("dataInicial") String dataInicial,
            @Path("dataFinal") String dataFinal
    );

    @GET("api/precipitacao/period/{dataInicial}/{dataFinal}")
    Call<List<ResponsePrecipitacao>> getPrecipitacao(
            @Path("dataInicial") String dataInicial,
            @Path("dataFinal") String dataFinal
    );

}
