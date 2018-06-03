package mubble.estaoagrometeorolgica.presenter.personalizacao_layout.grafico;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Criado por Igor em 02/06/2018.
 */

public class AxisHour implements IAxisValueFormatter {

    private BarLineChartBase<?> chart;

    public AxisHour(BarLineChartBase<?> chart) {
        this.chart = chart;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int hora = (int) value / 3600000;
        int minuto = (int) (value / 60000 ) % 60;
        return String.format( "%02d:%02d", hora, minuto);
    }
}
