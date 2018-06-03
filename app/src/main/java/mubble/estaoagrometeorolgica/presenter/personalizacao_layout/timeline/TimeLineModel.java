package mubble.estaoagrometeorolgica.presenter.personalizacao_layout.timeline;

/**
 * Criado por Igor em 27/10/2017.
 */

public class TimeLineModel {

    private String texto;
    private String titulo;
    private OrderStatus orderStatus;

    public TimeLineModel(){}

    public TimeLineModel(String titulo, String texto, OrderStatus orderStatus){
        this.titulo = titulo;
        this.texto = texto;
        this.orderStatus = orderStatus;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
