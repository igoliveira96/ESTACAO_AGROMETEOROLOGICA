package mubble.estaoagrometeorolgica.model.tempo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Criado por Igor em 02/06/2018.
 */
public class Tempo {

    private static Tempo instancia;

    public static Tempo getInstancia(){
        if(instancia == null){
            instancia = new Tempo();
        }
        return instancia;
    }

    private Tempo(){

    }

    public long getMilisegundos(String data){
        String[] dataValores = data.split("/");
        Calendar calendar = Calendar.getInstance();
        calendar.set(
                Integer.parseInt(dataValores[2]),
                Integer.parseInt(dataValores[1]) - 1,
                Integer.parseInt(dataValores[0]));
        return calendar.getTimeInMillis();
    }

    public long getMilisegundos(String hora, String minuto){
        long horas = TimeUnit.HOURS.toMillis(Integer.parseInt(hora));
        long minutos = TimeUnit.MINUTES.toMillis(Integer.parseInt(minuto));

        return horas+minutos;
    }

    public String milissegundosParaData(long milissegundos){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milissegundos);
        return formatter.format(calendar.getTime());
    }

    public String getModeloDataServidor(String data){
        long milissegundos = getMilisegundos(data);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milissegundos);
        return formatter.format(calendar.getTime());
    }

}
