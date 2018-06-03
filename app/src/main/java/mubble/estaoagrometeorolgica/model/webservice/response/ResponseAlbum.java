package mubble.estaoagrometeorolgica.model.webservice.response;

import mubble.estaoagrometeorolgica.model.Imagem;

/**
 * Criado por Igor Goulart de Oliveira em 02/06/2018.
 */
public class ResponseAlbum {

    public int id;
    public String nome;
    public String dataCriacao;
    public Imagem imagemCapa;
    public Imagem[] imagens;

}
