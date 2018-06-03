package mubble.estaoagrometeorolgica.presenter.presenter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;
import mubble.estaoagrometeorolgica.R;
import mubble.estaoagrometeorolgica.model.constantes.ConsultaDadosEnum;
import mubble.estaoagrometeorolgica.model.eventbus.EventBusNotificao;
import mubble.estaoagrometeorolgica.model.tempo.Tempo;
import mubble.estaoagrometeorolgica.model.webservice.RetrofitConfig;
import mubble.estaoagrometeorolgica.model.webservice.response.ResponseEvaporacao;
import mubble.estaoagrometeorolgica.model.webservice.response.ResponseInsolacao;
import mubble.estaoagrometeorolgica.model.webservice.response.ResponsePrecipitacao;
import mubble.estaoagrometeorolgica.model.webservice.response.ResponseTemperatura;
import mubble.estaoagrometeorolgica.model.webservice.response.ResponseTermometroSeco;
import mubble.estaoagrometeorolgica.model.webservice.response.ResponseUmidadeRelativaAr;
import mubble.estaoagrometeorolgica.presenter.personalizacao_layout.grafico.AxisHour;
import mubble.estaoagrometeorolgica.presenter.personalizacao_layout.grafico.ValueFormatHour;
import mubble.estaoagrometeorolgica.presenter.personalizacao_layout.timeline.OrderStatus;
import mubble.estaoagrometeorolgica.presenter.personalizacao_layout.timeline.TimeLineAdapter;
import mubble.estaoagrometeorolgica.presenter.personalizacao_layout.timeline.TimeLineModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Criado por Igor Goulart de Oliveira em 01/06/2018.
 */
public class ConsultaDadosPresenter {

    private Activity activity;
    private static ConsultaDadosPresenter instancia;

    public static ConsultaDadosPresenter getInstancia(Activity activity){
        if(instancia == null){
            instancia = new ConsultaDadosPresenter(activity);
        }
        return instancia;
    }

    private ConsultaDadosPresenter(Activity activity){
        this.activity = activity;
    }

    public void getGraficoTemperatura(String dataInicial, String dataFinal, final LineChart lineChart){
        Call<List<ResponseTemperatura>> call = new RetrofitConfig().getDadosService().getTemperaturas(dataInicial, dataFinal);

        call.enqueue(new Callback<List<ResponseTemperatura>>() {
            @Override
            public void onResponse(Call<List<ResponseTemperatura>> call, Response<List<ResponseTemperatura>> response) {
                try {
                    List<ResponseTemperatura> temperaturas = response.body();

                    if(temperaturas.size() > 0){
                        List<Entry> temperaturasMinima = new ArrayList<Entry>();
                        List<Entry> temperaturasMedia = new ArrayList<Entry>();
                        List<Entry> temperaturasMaxima = new ArrayList<Entry>();
                        List<Entry> temperaturasRelva = new ArrayList<Entry>();

                        for (ResponseTemperatura responseT : temperaturas) {
                            temperaturasMinima.add(new Entry(Tempo.getInstancia().getMilisegundos(responseT.dataDados), responseT.minima));
                            temperaturasMedia.add(new Entry(Tempo.getInstancia().getMilisegundos(responseT.dataDados), responseT.media));
                            temperaturasMaxima.add(new Entry(Tempo.getInstancia().getMilisegundos(responseT.dataDados), responseT.maxima));
                            temperaturasRelva.add(new Entry(Tempo.getInstancia().getMilisegundos(responseT.dataDados), responseT.relva));
                        }

                        List<ILineDataSet> dataSets = new ArrayList<>();

                        LineDataSet dataSet = new LineDataSet(temperaturasMinima, "Mínima °C");
                        dataSet.setColor(activity.getResources().getColor(R.color.azul_grafico));
                        dataSet.setCircleColor(activity.getResources().getColor(R.color.azul_grafico));
                        dataSet.setValueTextColor(activity.getResources().getColor(R.color.colorPrimaryText));
                        dataSets.add(dataSet);

                        dataSet = new LineDataSet(temperaturasMedia, "Média °C");
                        dataSet.setColor(activity.getResources().getColor(R.color.laranja_medio_grafico));
                        dataSet.setCircleColor(activity.getResources().getColor(R.color.laranja_medio_grafico));
                        dataSet.setValueTextColor(activity.getResources().getColor(R.color.colorPrimaryText));
                        dataSets.add(dataSet);

                        dataSet = new LineDataSet(temperaturasMaxima, "Máxima °C");
                        dataSet.setColor(activity.getResources().getColor(R.color.azul_escuro_grafico));
                        dataSet.setCircleColor(activity.getResources().getColor(R.color.azul_escuro_grafico));
                        dataSet.setValueTextColor(activity.getResources().getColor(R.color.colorPrimaryText));
                        dataSets.add(dataSet);

                        dataSet = new LineDataSet(temperaturasRelva, "Relva °C");
                        dataSet.setCircleColor(activity.getResources().getColor(R.color.cinza_grafico));
                        dataSet.setColor(activity.getResources().getColor(R.color.cinza_grafico));
                        dataSet.setValueTextColor(activity.getResources().getColor(R.color.colorPrimaryText));
                        dataSets.add(dataSet);

                        LineData data = new LineData(dataSets);
                        lineChart.setData(data);

                        XAxis xAxis = lineChart.getXAxis();
                        xAxis.setDrawGridLines(false);
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setDrawGridLines(false);
                        xAxis.setCenterAxisLabels(true);
                        xAxis.setValueFormatter(new IAxisValueFormatter() {
                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {
                                return Tempo.getInstancia().milissegundosParaData((long) value);
                            }
                        });

                        lineChart.getAxisRight().setEnabled(false);
                        lineChart.invalidate();

                        ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.SUCESSO;
                        EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                    } else {
                        ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                        EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                    }
                }catch (Exception e){
                    ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                    EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                }
            }

            @Override
            public void onFailure(Call<List<ResponseTemperatura>> call, Throwable t) {
                ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
            }
        });
    }

    public void getGraficoInsolacao(String dataInicial, String dataFinal, final LineChart lineChart){
        Call<List<ResponseInsolacao>> call = new RetrofitConfig().getDadosService().getInsolacoes(dataInicial, dataFinal);

        call.enqueue(new Callback<List<ResponseInsolacao>>() {
            @Override
            public void onResponse(Call<List<ResponseInsolacao>> call, Response<List<ResponseInsolacao>> response) {
                try {
                    List<ResponseInsolacao> insolacoes = response.body();
                    if(insolacoes.size() > 0){
                        IAxisValueFormatter xAxisFormatter = new AxisHour(lineChart);

                        List<Entry> dadosInsolacao = new ArrayList<Entry>();

                        for (ResponseInsolacao responseI : insolacoes) {
                            if (responseI.horario != null) {
                                String[] horario = responseI.horario.split(":");
                                dadosInsolacao.add(new Entry(
                                        Tempo.getInstancia().getMilisegundos(responseI.dataDado),
                                        Tempo.getInstancia().getMilisegundos(horario[0], horario[1])));
                            }
                        }

                        List<ILineDataSet> dataSets = new ArrayList<>();

                        LineDataSet dataSet = new LineDataSet(dadosInsolacao, "Horário");
                        dataSet.setValueFormatter(new ValueFormatHour());
                        dataSet.setColor(activity.getResources().getColor(R.color.azul_grafico));
                        dataSet.setCircleColor(activity.getResources().getColor(R.color.azul_grafico));
                        dataSet.setValueTextColor(activity.getResources().getColor(R.color.colorPrimaryText));
                        dataSets.add(dataSet);

                        LineData data = new LineData(dataSets);
                        lineChart.setData(data);

                        XAxis xAxis = lineChart.getXAxis();
                        xAxis.setDrawGridLines(false);
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setDrawGridLines(false);
                        xAxis.setCenterAxisLabels(true);
                        xAxis.setValueFormatter(xAxisFormatter);
                        xAxis.setValueFormatter(new IAxisValueFormatter() {
                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {
                                return Tempo.getInstancia().milissegundosParaData((long) value);
                            }
                        });

                        YAxis left = lineChart.getAxisLeft();
                        left.setValueFormatter(xAxisFormatter);

                        lineChart.getAxisRight().setEnabled(false);
                        lineChart.invalidate();

                        ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.SUCESSO;
                        EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                    } else {
                        ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                        EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                    }
                } catch (Exception e){
                    ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                    EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                }
            }

            @Override
            public void onFailure(Call<List<ResponseInsolacao>> call, Throwable t) {
                ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
            }
        });
    }

    public void getGraficoTermometroSeco(String dataInicial, String dataFinal, final LineChart lineChart){
        Call<List<ResponseTermometroSeco>> call = new RetrofitConfig().getDadosService().getTermometroSeco(dataInicial, dataFinal);

        call.enqueue(new Callback<List<ResponseTermometroSeco>>() {
            @Override
            public void onResponse(Call<List<ResponseTermometroSeco>> call, Response<List<ResponseTermometroSeco>> response) {
                try {
                    List<ResponseTermometroSeco> temperaturas = response.body();

                    if(temperaturas.size() > 0){
                        List<Entry> noveHoras = new ArrayList<Entry>();
                        List<Entry> quinzeHoras = new ArrayList<Entry>();
                        List<Entry> vinteUmaHoras = new ArrayList<Entry>();
                        List<Entry> temperaturasRelva = new ArrayList<Entry>();

                        for (ResponseTermometroSeco responseT : temperaturas) {
                            noveHoras.add(new Entry(Tempo.getInstancia().getMilisegundos(responseT.dataDados), responseT.noveHoras));
                            quinzeHoras.add(new Entry(Tempo.getInstancia().getMilisegundos(responseT.dataDados), responseT.quinzeHoras));
                            vinteUmaHoras.add(new Entry(Tempo.getInstancia().getMilisegundos(responseT.dataDados), responseT.vinteUmaHoras));
                        }

                        List<ILineDataSet> dataSets = new ArrayList<>();

                        LineDataSet dataSet = new LineDataSet(noveHoras, "09h °C");
                        dataSet.setColor(activity.getResources().getColor(R.color.azul_grafico));
                        dataSet.setCircleColor(activity.getResources().getColor(R.color.azul_grafico));
                        dataSet.setValueTextColor(activity.getResources().getColor(R.color.colorPrimaryText));
                        dataSets.add(dataSet);

                        dataSet = new LineDataSet(quinzeHoras, "15h °C");
                        dataSet.setColor(activity.getResources().getColor(R.color.laranja_medio_grafico));
                        dataSet.setCircleColor(activity.getResources().getColor(R.color.laranja_medio_grafico));
                        dataSet.setValueTextColor(activity.getResources().getColor(R.color.colorPrimaryText));
                        dataSets.add(dataSet);

                        dataSet = new LineDataSet(vinteUmaHoras, "21h °C");
                        dataSet.setColor(activity.getResources().getColor(R.color.azul_escuro_grafico));
                        dataSet.setCircleColor(activity.getResources().getColor(R.color.azul_escuro_grafico));
                        dataSet.setValueTextColor(activity.getResources().getColor(R.color.colorPrimaryText));
                        dataSets.add(dataSet);

                        LineData data = new LineData(dataSets);
                        lineChart.setData(data);

                        XAxis xAxis = lineChart.getXAxis();
                        xAxis.setDrawGridLines(false);
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setDrawGridLines(false);
                        xAxis.setCenterAxisLabels(true);
                        xAxis.setValueFormatter(new IAxisValueFormatter() {
                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {
                                return Tempo.getInstancia().milissegundosParaData((long) value);
                            }
                        });

                        lineChart.getAxisRight().setEnabled(false);
                        lineChart.invalidate();

                        ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.SUCESSO;
                        EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                    } else {
                        ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                        EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                    }
                } catch (Exception e){
                    ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                    EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                }
            }

            @Override
            public void onFailure(Call<List<ResponseTermometroSeco>> call, Throwable t) {
                ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
            }
        });
    }

    public void getGraficoUmidadeRelativaAr(String dataInicial, String dataFinal, final LineChart lineChart){
        Call<List<ResponseUmidadeRelativaAr>> call = new RetrofitConfig().getDadosService().getUmidadeRelativaAr(dataInicial, dataFinal);

        call.enqueue(new Callback<List<ResponseUmidadeRelativaAr>>() {
            @Override
            public void onResponse(Call<List<ResponseUmidadeRelativaAr>> call, Response<List<ResponseUmidadeRelativaAr>> response) {
                try {
                    List<ResponseUmidadeRelativaAr> responses = response.body();

                    if(responses.size() > 0){
                        List<Entry> noveHoras = new ArrayList<Entry>();
                        List<Entry> quinzeHoras = new ArrayList<Entry>();
                        List<Entry> vinteUmaHoras = new ArrayList<Entry>();
                        List<Entry> media = new ArrayList<Entry>();

                        for (ResponseUmidadeRelativaAr responseT : responses) {
                            noveHoras.add(new Entry(Tempo.getInstancia().getMilisegundos(responseT.dataDados), responseT.noveHoras));
                            quinzeHoras.add(new Entry(Tempo.getInstancia().getMilisegundos(responseT.dataDados), responseT.quinzeHoras));
                            vinteUmaHoras.add(new Entry(Tempo.getInstancia().getMilisegundos(responseT.dataDados), responseT.vinteUmaHoras));
                            media.add(new Entry(Tempo.getInstancia().getMilisegundos(responseT.dataDados), responseT.media));
                        }

                        List<ILineDataSet> dataSets = new ArrayList<>();

                        LineDataSet dataSet = new LineDataSet(noveHoras, "09h %");
                        dataSet.setColor(activity.getResources().getColor(R.color.azul_grafico));
                        dataSet.setCircleColor(activity.getResources().getColor(R.color.azul_grafico));
                        dataSet.setValueTextColor(activity.getResources().getColor(R.color.colorPrimaryText));
                        dataSets.add(dataSet);

                        dataSet = new LineDataSet(quinzeHoras, "15h %");
                        dataSet.setColor(activity.getResources().getColor(R.color.laranja_medio_grafico));
                        dataSet.setCircleColor(activity.getResources().getColor(R.color.laranja_medio_grafico));
                        dataSet.setValueTextColor(activity.getResources().getColor(R.color.colorPrimaryText));
                        dataSets.add(dataSet);

                        dataSet = new LineDataSet(vinteUmaHoras, "21h %");
                        dataSet.setColor(activity.getResources().getColor(R.color.azul_escuro_grafico));
                        dataSet.setCircleColor(activity.getResources().getColor(R.color.azul_escuro_grafico));
                        dataSet.setValueTextColor(activity.getResources().getColor(R.color.colorPrimaryText));
                        dataSets.add(dataSet);

                        dataSet = new LineDataSet(media, "Média %");
                        dataSet.setCircleColor(activity.getResources().getColor(R.color.cinza_grafico));
                        dataSet.setColor(activity.getResources().getColor(R.color.cinza_grafico));
                        dataSet.setValueTextColor(activity.getResources().getColor(R.color.colorPrimaryText));
                        dataSets.add(dataSet);

                        LineData data = new LineData(dataSets);
                        lineChart.setData(data);

                        XAxis xAxis = lineChart.getXAxis();
                        xAxis.setDrawGridLines(false);
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setDrawGridLines(false);
                        xAxis.setCenterAxisLabels(true);
                        xAxis.setValueFormatter(new IAxisValueFormatter() {
                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {
                                return Tempo.getInstancia().milissegundosParaData((long) value);
                            }
                        });

                        lineChart.getAxisRight().setEnabled(false);
                        lineChart.invalidate();

                        ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.SUCESSO;
                        EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                    } else {
                        ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                        EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                    }
                } catch (Exception e){
                    ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                    EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                }
            }

            @Override
            public void onFailure(Call<List<ResponseUmidadeRelativaAr>> call, Throwable t) {
                ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
            }
        });
    }

    public void getGraficoEvaporacao(String dataInicial, String dataFinal, final LineChart lineChart){
        Call<List<ResponseEvaporacao>> call = new RetrofitConfig().getDadosService().getEvaporacao(dataInicial, dataFinal);

        call.enqueue(new Callback<List<ResponseEvaporacao>>() {
            @Override
            public void onResponse(Call<List<ResponseEvaporacao>> call, Response<List<ResponseEvaporacao>> response) {
                try{
                    List<ResponseEvaporacao> responses = response.body();

                    if(responses.size() > 0){
                        List<Entry> milimetros = new ArrayList<Entry>();

                        for (ResponseEvaporacao responseT : responses) {
                            milimetros.add(new Entry(Tempo.getInstancia().getMilisegundos(responseT.dataDado), responseT.milimetros));
                        }

                        List<ILineDataSet> dataSets = new ArrayList<>();

                        LineDataSet dataSet = new LineDataSet(milimetros, "Milímetros");
                        dataSet.setColor(activity.getResources().getColor(R.color.azul_grafico));
                        dataSet.setCircleColor(activity.getResources().getColor(R.color.azul_grafico));
                        dataSet.setValueTextColor(activity.getResources().getColor(R.color.colorPrimaryText));
                        dataSets.add(dataSet);

                        LineData data = new LineData(dataSets);
                        lineChart.setData(data);

                        XAxis xAxis = lineChart.getXAxis();
                        xAxis.setDrawGridLines(false);
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setDrawGridLines(false);
                        xAxis.setCenterAxisLabels(true);
                        xAxis.setValueFormatter(new IAxisValueFormatter() {
                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {
                                return Tempo.getInstancia().milissegundosParaData((long)value);
                            }
                        });

                        lineChart.getAxisRight().setEnabled(false);
                        lineChart.invalidate();

                        ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.SUCESSO;
                        EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                    } else {
                        ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                        EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                    }
                } catch (Exception e){
                    ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                    EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                }
            }

            @Override
            public void onFailure(Call<List<ResponseEvaporacao>> call, Throwable t) {
                ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
            }
        });
    }

    public void getGraficoPrecipitacao(String dataInicial, String dataFinal, final LineChart lineChart){
        Call<List<ResponsePrecipitacao>> call = new RetrofitConfig().getDadosService().getPrecipitacao(dataInicial, dataFinal);

        call.enqueue(new Callback<List<ResponsePrecipitacao>>() {
            @Override
            public void onResponse(Call<List<ResponsePrecipitacao>> call, Response<List<ResponsePrecipitacao>> response) {
                try {
                    List<ResponsePrecipitacao> responses = response.body();

                    if(responses.size() > 0){
                        List<Entry> milimetros = new ArrayList<Entry>();

                        for (ResponsePrecipitacao responseT : responses) {
                            milimetros.add(new Entry(Tempo.getInstancia().getMilisegundos(responseT.dataDado), responseT.milimetros));
                        }

                        List<ILineDataSet> dataSets = new ArrayList<>();

                        LineDataSet dataSet = new LineDataSet(milimetros, "Milímetros");
                        dataSet.setColor(activity.getResources().getColor(R.color.azul_grafico));
                        dataSet.setCircleColor(activity.getResources().getColor(R.color.azul_grafico));
                        dataSet.setValueTextColor(activity.getResources().getColor(R.color.colorPrimaryText));
                        dataSets.add(dataSet);

                        LineData data = new LineData(dataSets);
                        lineChart.setData(data);

                        XAxis xAxis = lineChart.getXAxis();
                        xAxis.setDrawGridLines(false);
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setDrawGridLines(false);
                        xAxis.setCenterAxisLabels(true);
                        xAxis.setValueFormatter(new IAxisValueFormatter() {
                            @Override
                            public String getFormattedValue(float value, AxisBase axis) {
                                return Tempo.getInstancia().milissegundosParaData((long) value);
                            }
                        });

                        lineChart.getAxisRight().setEnabled(false);
                        lineChart.invalidate();
                        ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.SUCESSO;
                        EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                    } else {
                        ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                        EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                    }
                } catch (Exception e){
                    ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                    EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                }
            }

            @Override
            public void onFailure(Call<List<ResponsePrecipitacao>> call, Throwable t) {
                ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
            }
        });
    }

    public void getTimeLineTemperatura(String dataInicial, String dataFinal, final RecyclerView recyclerView){
        Call<List<ResponseTemperatura>> call = new RetrofitConfig().getDadosService().getTemperaturas(dataInicial, dataFinal);

        call.enqueue(new Callback<List<ResponseTemperatura>>() {
            @Override
            public void onResponse(Call<List<ResponseTemperatura>> call, Response<List<ResponseTemperatura>> response) {
                try {
                    List<ResponseTemperatura> temperaturas = response.body();

                    if(temperaturas.size() > 0){
                        List<TimeLineModel> dadosTimeLine = new ArrayList<>();

                        for (ResponseTemperatura responseT : temperaturas) {
                            dadosTimeLine.add(new TimeLineModel(responseT.dataDados, "", OrderStatus.COMPLETED));
                            dadosTimeLine.add(new TimeLineModel(responseT.dataDados + " - Mínima", Float.toString(responseT.minima) + " °C", OrderStatus.ACTIVE));
                            dadosTimeLine.add(new TimeLineModel(responseT.dataDados + " - Média", Float.toString(responseT.media) + " °C", OrderStatus.ACTIVE));
                            dadosTimeLine.add(new TimeLineModel(responseT.dataDados + " - Máxima", Float.toString(responseT.maxima) + " °C", OrderStatus.ACTIVE));
                            dadosTimeLine.add(new TimeLineModel(responseT.dataDados + " - Relva", Float.toString(responseT.relva) + " °C", OrderStatus.ACTIVE));
                        }

                        TimeLineAdapter timeLineAdapter = new TimeLineAdapter(dadosTimeLine);
                        recyclerView.setAdapter(timeLineAdapter);

                        ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.SUCESSO;
                        EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                    } else {
                        ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                        EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                    }
                }catch (Exception e){
                    ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                    EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                }
            }

            @Override
            public void onFailure(Call<List<ResponseTemperatura>> call, Throwable t) {
                ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
            }
        });
    }

    public void getTimeLineInsolacao(String dataInicial, String dataFinal, final RecyclerView recyclerView){
        Call<List<ResponseInsolacao>> call = new RetrofitConfig().getDadosService().getInsolacoes(dataInicial, dataFinal);

        call.enqueue(new Callback<List<ResponseInsolacao>>() {
            @Override
            public void onResponse(Call<List<ResponseInsolacao>> call, Response<List<ResponseInsolacao>> response) {
                try {
                    List<ResponseInsolacao> responseInsolacaos = response.body();

                    if(responseInsolacaos.size() > 0){
                        List<TimeLineModel> dadosTimeLine = new ArrayList<>();

                        for (ResponseInsolacao responseT : responseInsolacaos) {
                            dadosTimeLine.add(new TimeLineModel(responseT.dataDado, "", OrderStatus.COMPLETED));
                            dadosTimeLine.add(new TimeLineModel(responseT.dataDado + " - Horário", responseT.horario, OrderStatus.ACTIVE));
                        }

                        TimeLineAdapter timeLineAdapter = new TimeLineAdapter(dadosTimeLine);
                        recyclerView.setAdapter(timeLineAdapter);

                        ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.SUCESSO;
                        EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                    } else {
                        ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                        EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                    }
                } catch (Exception e){
                    ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                    EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                }
            }

            @Override
            public void onFailure(Call<List<ResponseInsolacao>> call, Throwable t) {
                ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
            }
        });
    }

    public void getTimeLineTermometroSeco(String dataInicial, String dataFinal, final RecyclerView recyclerView){
        Call<List<ResponseTermometroSeco>> call = new RetrofitConfig().getDadosService().getTermometroSeco(dataInicial, dataFinal);

        call.enqueue(new Callback<List<ResponseTermometroSeco>>() {
            @Override
            public void onResponse(Call<List<ResponseTermometroSeco>> call, Response<List<ResponseTermometroSeco>> response) {
                try {
                    List<ResponseTermometroSeco> responseTermometroSecos = response.body();

                    if(responseTermometroSecos.size() > 0){
                        List<TimeLineModel> dadosTimeLine = new ArrayList<>();

                        for (ResponseTermometroSeco responseT : responseTermometroSecos) {
                            dadosTimeLine.add(new TimeLineModel(responseT.dataDados, "", OrderStatus.COMPLETED));
                            dadosTimeLine.add(new TimeLineModel(responseT.dataDados + " - 09h", Float.toString(responseT.noveHoras) + " °C", OrderStatus.ACTIVE));
                            dadosTimeLine.add(new TimeLineModel(responseT.dataDados + " - 15h", Float.toString(responseT.quinzeHoras) + " °C", OrderStatus.ACTIVE));
                            dadosTimeLine.add(new TimeLineModel(responseT.dataDados + " - 21h", Float.toString(responseT.vinteUmaHoras) + " °C", OrderStatus.ACTIVE));
                        }

                        TimeLineAdapter timeLineAdapter = new TimeLineAdapter(dadosTimeLine);
                        recyclerView.setAdapter(timeLineAdapter);

                        ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.SUCESSO;
                        EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                    } else {
                        ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                        EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                    }
                } catch (Exception e){
                    ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                    EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                }
            }

            @Override
            public void onFailure(Call<List<ResponseTermometroSeco>> call, Throwable t) {
                ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
            }
        });
    }

    public void getTimeLineUmidadeRelativaAr(String dataInicial, String dataFinal, final RecyclerView recyclerView){
        Call<List<ResponseUmidadeRelativaAr>> call = new RetrofitConfig().getDadosService().getUmidadeRelativaAr(dataInicial, dataFinal);

        call.enqueue(new Callback<List<ResponseUmidadeRelativaAr>>() {
            @Override
            public void onResponse(Call<List<ResponseUmidadeRelativaAr>> call, Response<List<ResponseUmidadeRelativaAr>> response) {
                try {
                    List<ResponseUmidadeRelativaAr> responses = response.body();
                    if(responses.size() > 0){
                        List<TimeLineModel> dadosTimeLine = new ArrayList<>();

                        for (ResponseUmidadeRelativaAr responseT : responses) {
                            dadosTimeLine.add(new TimeLineModel(responseT.dataDados, "", OrderStatus.COMPLETED));
                            dadosTimeLine.add(new TimeLineModel(responseT.dataDados + " - 09h", Float.toString(responseT.noveHoras), OrderStatus.ACTIVE));
                            dadosTimeLine.add(new TimeLineModel(responseT.dataDados + " - 15h", Float.toString(responseT.quinzeHoras), OrderStatus.ACTIVE));
                            dadosTimeLine.add(new TimeLineModel(responseT.dataDados + " - 21h", Float.toString(responseT.vinteUmaHoras), OrderStatus.ACTIVE));
                            dadosTimeLine.add(new TimeLineModel(responseT.dataDados + " - Média", Float.toString(responseT.media), OrderStatus.ACTIVE));
                        }

                        TimeLineAdapter timeLineAdapter = new TimeLineAdapter(dadosTimeLine);
                        recyclerView.setAdapter(timeLineAdapter);

                        ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.SUCESSO;
                        EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                    } else {
                        ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                        EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                    }
                } catch (Exception e){
                    ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                    EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                }
            }

            @Override
            public void onFailure(Call<List<ResponseUmidadeRelativaAr>> call, Throwable t) {
                ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
            }
        });
    }

    public void getTimeLineEvaporacao(String dataInicial, String dataFinal, final RecyclerView recyclerView){
        Call<List<ResponseEvaporacao>> call = new RetrofitConfig().getDadosService().getEvaporacao(dataInicial, dataFinal);

        call.enqueue(new Callback<List<ResponseEvaporacao>>() {
            @Override
            public void onResponse(Call<List<ResponseEvaporacao>> call, Response<List<ResponseEvaporacao>> response) {
                try{
                    List<ResponseEvaporacao> responses = response.body();

                    if(responses.size() > 0){
                        List<TimeLineModel> dadosTimeLine = new ArrayList<>();

                        for (ResponseEvaporacao responseT : responses) {
                            dadosTimeLine.add(new TimeLineModel(responseT.dataDado, "", OrderStatus.COMPLETED));
                            dadosTimeLine.add(new TimeLineModel(responseT.dataDado, Float.toString(responseT.milimetros)+"mm", OrderStatus.ACTIVE));
                        }

                        TimeLineAdapter timeLineAdapter = new TimeLineAdapter(dadosTimeLine);
                        recyclerView.setAdapter(timeLineAdapter);

                        ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.SUCESSO;
                        EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                    }
                } catch (Exception e){
                    ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                    EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                }
            }

            @Override
            public void onFailure(Call<List<ResponseEvaporacao>> call, Throwable t) {
                ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
            }
        });
    }

    public void getTimeLinePrecipitacao(String dataInicial, String dataFinal, final RecyclerView recyclerView){
        Call<List<ResponsePrecipitacao>> call = new RetrofitConfig().getDadosService().getPrecipitacao(dataInicial, dataFinal);

        call.enqueue(new Callback<List<ResponsePrecipitacao>>() {
            @Override
            public void onResponse(Call<List<ResponsePrecipitacao>> call, Response<List<ResponsePrecipitacao>> response) {
                try {
                    List<ResponsePrecipitacao> responses = response.body();

                    if(responses.size() > 0){
                        List<TimeLineModel> dadosTimeLine = new ArrayList<>();

                        for (ResponsePrecipitacao responseT : responses) {
                            dadosTimeLine.add(new TimeLineModel(responseT.dataDado, "", OrderStatus.COMPLETED));
                            dadosTimeLine.add(new TimeLineModel(responseT.dataDado, Float.toString(responseT.milimetros) + "mm", OrderStatus.ACTIVE));
                        }

                        TimeLineAdapter timeLineAdapter = new TimeLineAdapter(dadosTimeLine);
                        recyclerView.setAdapter(timeLineAdapter);

                        ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.SUCESSO;
                        EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                    } else {
                        ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                        EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                    }
                } catch (Exception e){
                    ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                    EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
                }
            }

            @Override
            public void onFailure(Call<List<ResponsePrecipitacao>> call, Throwable t) {
                ConsultaDadosEnum consultaDadosEnum = ConsultaDadosEnum.ERRO;
                EventBusNotificao.getInstancia().notificarEventBus(consultaDadosEnum.toString());
            }
        });
    }

}
