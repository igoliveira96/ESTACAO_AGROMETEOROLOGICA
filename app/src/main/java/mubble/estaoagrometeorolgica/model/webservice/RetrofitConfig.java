package mubble.estaoagrometeorolgica.model.webservice;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Criado por Igor em 11/03/2018.
 */

public class RetrofitConfig {

    private final Retrofit retrofit;

    public RetrofitConfig(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://200.201.11.14/estacao/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public IDadosService getDadosService(){
        return retrofit.create(IDadosService.class);
    }

    public IAlbumService getAlbumService() { return retrofit.create(IAlbumService.class); }

    public IContatoService getContatoService() { return retrofit.create(IContatoService.class); }

}
