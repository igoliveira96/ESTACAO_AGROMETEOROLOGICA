package mubble.estaoagrometeorolgica.model.eventbus;

import org.greenrobot.eventbus.EventBus;

/**
 * Criado por Igor Goulart de Oliveira em 01/03/2018.
 */

public class EventBusNotificao {

    private static EventBusNotificao instancia;

    public static EventBusNotificao getInstancia(){
        if(instancia == null){
            instancia = new EventBusNotificao();
        }
        return instancia;
    }

    private EventBusNotificao(){}

    public void notificarEventBus(String mensagem){
        EventBus.getDefault().post(new MessageBus(mensagem));
    }

    public synchronized void notificarEventBus(String mensagem, long id){
        EventBus.getDefault().post(new MessageBus(mensagem, id));
    }

    public synchronized void notificarEventBus(String mensagem, Object object){
        EventBus.getDefault().post(new MessageBus(mensagem, object));
    }

}
