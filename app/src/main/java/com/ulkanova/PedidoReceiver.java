package com.ulkanova;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class PedidoReceiver extends BroadcastReceiver {

    public static final String PEDIDO_CONFIRMADO  = "com.ulkanova.PEDIDO_CONFIRMADO";

    @Override
    public void onReceive(Context context, Intent intent) {
        notificar(context,intent);
    }

    private void notificar(Context context, Intent intent){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "1")
                .setSmallIcon(android.R.drawable.star_on)
                .setAutoCancel(true)
                .setContentTitle("Pedido Confirmado")
                .setContentText("Su pedido de Send Meal ha sido confirmado")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Notification notificacion =  mBuilder.build();

        // obtengo el notification manager
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        // envio la notificacion
        notificationManager.notify(1,notificacion);
    }
}