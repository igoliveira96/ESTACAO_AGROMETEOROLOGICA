package mubble.estaoagrometeorolgica.model.webservice.request;

/**
 * Criado por Igor em 30/05/2018.
 */
public class RequestDadosAgrometeorologicos {

    public String dataInicial;
    public String dataFinal;

    public String getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(String dataInicial) {
        this.dataInicial = dataInicial;
    }

    public String getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(String dataFinal) {
        this.dataFinal = dataFinal;
    }
}
