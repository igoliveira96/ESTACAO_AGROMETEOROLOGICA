package mubble.estaoagrometeorolgica.model.webservice;

import java.util.List;

import mubble.estaoagrometeorolgica.model.webservice.response.ResponseAlbum;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Criado por Igor Goulart de Oliveira em 02/06/2018.
 */

public interface IAlbumService {

    @GET("api/album/get-albuns")
    Call<List<ResponseAlbum>> getAlbuns();

}
