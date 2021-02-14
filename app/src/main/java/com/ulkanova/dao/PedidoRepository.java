package com.ulkanova.dao;
import android.app.Application;
import android.util.Log;

import com.ulkanova.model.Pedido;
import com.ulkanova.model.Plato;

import java.util.ArrayList;
import java.util.List;

public class PedidoRepository {//implements OnPedidoResultCallback {
//    private PedidoConPlatosDao pedidoConPlatosDao;
//    private PedidoDao pedidoDao;
//    private OnResultCallback callback;
//
//    public PedidoRepository(Application application, OnResultCallback context){
//        AppDatabase db = AppDatabase.getInstance(application);
//        pedidoDao = db.pedidoDao();
//        callback = context;
//    }
//
//    public void insertar(final Pedido pedido){
//        Log.d("PEDIDO", "PEDIDO EN INSERCION");
//        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                pedidoDao.insertar(pedido);
//            }
//        });
//    }
//
//    public void borrar(final Pedido pedido){
//        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                pedidoDao.borrar(pedido);
//            }
//        });
//    }
//
//    public void actualizar(final Pedido pedido){
//        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                pedidoDao.actualizar(pedido);
//            }
//        });
//    }
//
//    public void buscar(String id) {
//        new BuscarPedido(pedidoConPlatosDao, this).execute(id);
//    }
//
//    public void buscarTodos() {
//        new BuscarPedido(pedidoConPlatosDao, this).execute();
//    }
//
//    @Override
//    public void onResult(Pedido pedido) {
//        Log.d("DEBUG", "Pedido found");
//        List<Pedido> lista = new ArrayList<>();
//        lista.add(pedido);
//        callback.onResult(lista);
//    }
//
//    public interface OnResultCallback<T> {
//        void onResult(List<T> result);
//    }
}