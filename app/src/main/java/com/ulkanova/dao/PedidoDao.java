package com.ulkanova.dao;

import android.os.AsyncTask;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.ulkanova.model.Pedido;
import com.ulkanova.model.Plato;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface PedidoDao {
//    @Insert
//    void insertar(Pedido pedido);
//
//    @Delete
//    void borrar(Pedido pedido);
//
//    @Update
//    void actualizar(Pedido pedido);
//
//    @Query("SELECT * FROM pedido WHERE pedidoId = :id LIMIT 1")
//    Pedido buscarPedido(String id);
//
//    @Query("SELECT * FROM pedido")
//    List<Pedido> buscarTodos();
//
//
////    @Transaction
////    @Query("SELECT * FROM pedido_plato WHERE pedidoId=:pedidoId")
////    List<Plato> getPlatos(String pedidoId);
//
////    @Transaction
////    @Query("SELECT * FROM pedido_plato WHERE pedidoId =:pedidoId")
////    List<Plato> getPlatos(String pedidoId);
//}
//
//class BuscarPedido extends AsyncTask<String, Void, Pedido> {
//
//    private PedidoConPlatosDao dao;
//    private OnPedidoResultCallback callback;
//
//    public BuscarPedido(PedidoConPlatosDao dao, OnPedidoResultCallback context) {
//        this.dao = dao;
//        this.callback = context;
//    }
//
//    @Override
//    protected Pedido doInBackground(String... strings) {
//        Pedido pedido = dao.buscarPedido(strings[0]);
//        return pedido;
//    }
//
//    @Override
//    protected void onPostExecute(Pedido pedido) {
//        super.onPostExecute(pedido);
//        callback.onResult(pedido);
//    }
//}
//interface OnPedidoResultCallback {
//    void onResult(Pedido pedido);
//}
}