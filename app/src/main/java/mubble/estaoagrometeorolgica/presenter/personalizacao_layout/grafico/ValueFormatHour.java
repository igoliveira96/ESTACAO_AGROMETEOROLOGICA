package mubble.estaoagrometeorolgica.presenter.personalizacao_layout.grafico;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * Criado por Igor em 02/06/2018.
 */
public class ValueFormatHour implements IValueFormatter {

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        int hora = (int) value / 3600000;
        int minuto = (int) (value / 60000 ) % 60;
        return String.format( "%02d:%02d", hora, minuto);
    }

}
