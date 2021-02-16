package com.ulkanova.retrofit;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ulkanova.model.Pedido;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PedidoRepositoryApi {
    private PedidoService pedidoService;

    private ApiBuilder apiBuilder;

    public PedidoRepositoryApi(){
        pedidoService = apiBuilder.getInstance().getPedidoService();
    }

    public void insertar(final Pedido pedido, final Handler h){
        final Message msg = h.obtainMessage();
        final Bundle retorno = new Bundle();
        Call<Pedido> callPedido = pedidoService.createPedido(pedido);
        callPedido.enqueue(
                new Callback<Pedido>() {
                    @Override
                    public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                        if (!response.isSuccessful()) {
                            retorno.putBoolean("insertado",false);
                            msg.setData(retorno);
                            h.sendMessage(msg);
                            return;
                        }
                        retorno.putBoolean("insertado",true);
                        msg.setData(retorno);
                        h.sendMessage(msg);
                    }
                    @Override
                    public void onFailure(Call<Pedido> call, Throwable t) {
                        retorno.putBoolean("insertado",false);
                        msg.setData(retorno);
                        h.sendMessage(msg);

                    }

                }
        );

    }

    public void borrar(final Pedido pedido){

    }

    public void actualizar(final Pedido pedido){

    }

    public void buscar(String id) {
    }

    public void buscarTodos(final Handler h) {
        final Message msg = h.obtainMessage();
        final Bundle datos = new Bundle();
        Call<List<Pedido>> callPedido = pedidoService.getPedidosList();
        Log.d("PEDIDO", "Buscando todos");

        callPedido.enqueue(
                new Callback<List<Pedido>>() {
                    @Override
                    public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                        Log.d("PLATO", "Call: "+call.request().url());
                        Log.d("PLATO", "Retorno: "+response.code());
                        if (!response.isSuccessful()) {
                            Log.d("PEDIDO", "Retorno Fallido");
                            return;
                        }
                    //    datos.putParcelableArrayList("pedido",(ArrayList<Pedido>) response.body());
                    //    msg.setData(datos);
                        h.sendMessage(msg);
                    }
                    @Override
                    public void onFailure(Call<List<Pedido>> call, Throwable t) {
                        Log.d("Pedido", "Retorno Fallido");
                    }
                }
        );
    }


    public interface OnResultCallback<T> {
        void onResult(List<T> result);
    }

}
