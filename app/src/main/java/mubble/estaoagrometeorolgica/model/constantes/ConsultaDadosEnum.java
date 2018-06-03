package mubble.estaoagrometeorolgica.model.constantes;

/**
 * Criado por Igor em 08/03/2018.
 */

public enum ConsultaDadosEnum {

    SUCESSO("SUCESSO", 1),
    ERRO("ERRO");

    private String stringValue;
    private int intValue;

    private ConsultaDadosEnum(String strValue, int value) {
        stringValue = strValue;
        intValue = value;
    }

    private ConsultaDadosEnum(String strValue){
        stringValue = strValue;
    }

    @Override
    public String toString() {
        return stringValue;
    }

    public int getIntValue(){
        return intValue;
    }

}
