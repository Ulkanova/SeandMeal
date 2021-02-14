package com.ulkanova.dao;
import android.app.Application;
import android.util.Log;

import com.ulkanova.model.Pedido;
import com.ulkanova.model.Plato;

import java.util.ArrayList;
import java.util.List;

public class PedidoRepository implements OnPedidoResultCallback {
    private PedidoPlatoDao pedidoPlatoDao;
    private PedidoDao pedidoDao;
    private List<Plato> platos;
    private long idPedido;
    private OnResultCallback callback;

    public PedidoRepository(Application application, OnResultCallback context){
        AppDatabase db = AppDatabase.getInstance(application);
        pedidoDao = db.pedidoDao();
        pedidoPlatoDao = db.pedidoPlatosDao();
        callback = context;
    }

    public void insertar(final Pedido pedido, final List<Long> idPlatos){
        Log.d("PEDIDO", "PEDIDO EN INSERCION");
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("PEDIDOPLATO", "ID PLATOS: "+ idPlatos);

               idPedido = pedidoDao.insertar(pedido);
                Log.d("PEDIDOPLATO", "PEDIDO: "+ idPedido);
               for (int i=0; i<idPlatos.size();i++){

                   PedidoPlato pedidoPlato = new PedidoPlato(idPedido,idPlatos.get(i));
                   Log.d("PEDIDOPLATO", "PEDIDOPLATO: Insertado: "+idPlatos.get(i));

                   pedidoPlatoDao.insertar(pedidoPlato);
               }
            }
        });
        callback.onResult(new ArrayList());
    }

    public void borrar(final Pedido pedido){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                pedidoDao.borrar(pedido);
            }
        });
    }

    public void actualizar(final Pedido pedido){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                pedidoDao.actualizar(pedido);
            }
        });
    }

//    public void buscar(String id) {
//        new BuscarPedido(pedidoPlatoDao, this).execute(id);
//    }
//
//    public void buscarTodos() {
//        new BuscarPedido(pedidoPlatoDao, this).execute();
//    }

    @Override
    public void onResult(List<Pedido> pedido) {
        Log.d("DEBUG", "Pedido found");

        callback.onResult(pedido);
    }

    public interface OnResultCallback<T> {
        void onResult(List<T> result);
    }
}