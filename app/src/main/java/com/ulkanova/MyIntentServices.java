package com.ulkanova;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

public class MyIntentServices extends IntentService {

    public MyIntentServices() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Intent i = new Intent();
        i.setAction(PedidoReceiver.PEDIDO_CONFIRMADO);
        sendBroadcast(i);
        this.sendBroadcast(i);
    }
}
