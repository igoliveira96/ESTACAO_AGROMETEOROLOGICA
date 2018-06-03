package mubble.estaoagrometeorolgica.model.eventbus;

/**
 * Criado por Igor Goulart de Oliveira em 10/02/2018.
 */

public class MessageBus {

    private String mensagem;
    private long id;
    private Object object;

    public MessageBus(String mensagem){
        this.mensagem = mensagem;
    }

    public MessageBus(String mensagem, long id){
        this.mensagem = mensagem;
        this.id = id;
    }

    public MessageBus(String mensagem, Object object){
        this.mensagem = mensagem;
        this.object = object;
    }

    public String getMensagem(){
        return mensagem;
    }

    public long getId(){
        return id;
    }

    public Object getObject(){
        return object;
    }

}
