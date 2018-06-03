package mubble.estaoagrometeorolgica.model.aplicacao;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.florent37.viewtooltip.ViewTooltip;
import com.tapadoo.alerter.Alerter;

import mubble.estaoagrometeorolgica.R;

import static com.github.florent37.viewtooltip.ViewTooltip.ALIGN.CENTER;
import static com.github.florent37.viewtooltip.ViewTooltip.Position.TOP;

/**
 * Criado por Igor em 10/02/2018.
 */

public class Alertas {

    public static AlertDialog progress(Activity activity, String mensagem){
        LayoutInflater inflater = activity.getLayoutInflater();

        View alertLayout = inflater.inflate(R.layout.progress_layout, null);
        TextView mensagemTV = alertLayout.findViewById(R.id.textoProgressBarAlert);
        mensagemTV.setText(mensagem);

        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setView(alertLayout);
        alert.setCancelable(false);

        AlertDialog dialog = alert.create();
        dialog.show();

        return dialog;
    }

    public static void exibirTooltip(View view, String mensagem){
        ViewTooltip
                .on(view)
                .autoHide(true, 3000)
                .clickToHide(true)
                .align(CENTER)
                .position(TOP)
                .text(mensagem)
                .textColor(Color.WHITE)
                .corner(30)
                .color(Color.parseColor("#607D8B"))
                .show();
    }

    public static void tapadoo(Activity activity, String titulo, String mensagem, int background){
        Alerter.create(activity)
                .setTitle(titulo)
                .setIcon(R.drawable.alerter_ic_notifications)
                .setBackgroundColorRes(background)
                .setText(mensagem)
                .setDuration(3000)
                .enableSwipeToDismiss()
                .show();
    }

    public static void exibirAlertaBasico(Activity activity, String titulo, String mensagem){
        android.app.AlertDialog alerta;
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity, R.style.Alerta);
        alerta = builder.create();
        alerta.setTitle(titulo);
        alerta.setMessage(mensagem);
        alerta.setButton(Dialog.BUTTON_POSITIVE, "Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        alerta.show();
    }

}
