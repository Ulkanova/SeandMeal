package com.ulkanova.retrofit;

import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ulkanova.dao.AppDatabase;
import com.ulkanova.dao.AppRepository;
import com.ulkanova.dao.PlatoDao;
import com.ulkanova.model.Plato;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlatoRepositoryApi{
    private PlatoService platoService;

    private ApiBuilder apiBuilder;

    public PlatoRepositoryApi(){
        platoService = apiBuilder.getInstance().getPlatoService();
    }

    public void insertar(final Plato plato, final Handler h){
            final Message msg = h.obtainMessage();
            final Bundle retorno = new Bundle();
            Call<Plato> callPlatos = platoService.createPlato(plato);
            Log.d("PLATO", "Insertando plato id: "+plato.getPlatoId()+ " titulo: "+plato.getTitulo());
            callPlatos.enqueue(
                    new Callback<Plato>() {
                        @Override
                        public void onResponse(Call<Plato> call, Response<Plato> response) {
                            if (!response.isSuccessful()) {
                                Log.d("PLATO", "Retorno Fallido");
                                Log.d("PLATO", "RESPUESTA: "+response.code());
                                retorno.putBoolean("insertado",false);
                                msg.setData(retorno);
                                h.sendMessage(msg);
                                return;
                            }
                            retorno.putBoolean("insertado",true);
                            msg.setData(retorno);
                            h.sendMessage(msg);
                            Log.d("PLATO", "RESPUESTA: "+response.code());
                        }
                        @Override
                        public void onFailure(Call<Plato> call, Throwable t) {
                            Log.d("PLATO", "Retorno Fallido");
                            retorno.putBoolean("insertado",false);
                            msg.setData(retorno);
                            h.sendMessage(msg);

                        }

                    }
            );

    }

    public void borrar(final Plato plato){

    }

    public void actualizar(final Plato plato){

    }

    public void buscar(String id) {
    }

    public void buscarTodos(final Handler h) {
        final Message msg = h.obtainMessage();
        final Bundle datos = new Bundle();
        Call<List<Plato>> callPlatos = platoService.getPlatoList();
        Log.d("PLATO", "Buscando todos");

        callPlatos.enqueue(
                new Callback<List<Plato>>() {
                    @Override
                    public void onResponse(Call<List<Plato>> call, Response<List<Plato>> response) {
                        Log.d("PLATO", "Call: "+call.request().url());
                        Log.d("PLATO", "Retorno: "+response.code());
                        if (!response.isSuccessful()) {
                            Log.d("PLATO", "Retorno Fallido");
                            return;
                        }
                        datos.putParcelableArrayList("plato",(ArrayList<Plato>) response.body());
                        msg.setData(datos);
                        h.sendMessage(msg);
                    }
                    @Override
                    public void onFailure(Call<List<Plato>> call, Throwable t) {
                        Log.d("PLATO", "Retorno Fallido");
                    }
                }
        );
    }

//    @Override
//    public void onResult(List<Plato> platos) {
//
//        callback.onResult(platos);
//    }

    public interface OnResultCallback<T> {
        void onResult(List<T> result);
    }
}
