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
    @Insert
    long insertar(Pedido pedido);

    @Delete
    void borrar(Pedido pedido);

    @Update
    void actualizar(Pedido pedido);
//
    @Query("SELECT * FROM pedido WHERE pedidoId = :id LIMIT 1")
    Pedido buscarPedido(String id);

//    @Transaction
//    @Query("SELECT * FROM pedido_plato WHERE pedidoId=:pedidoId")
//    List<Plato> getPlatos(String pedidoId);

    @Transaction
    @Query("SELECT * FROM pedido_plato WHERE pedidoId =:pedidoId")
    List<Plato> getPlatos(String pedidoId);
}

//class BuscarPedido extends AsyncTask<String, Void, Pedido> {
//
//    private PedidoPlatoDao dao;
//    private OnPedidoResultCallback callback;
//
//    public BuscarPedido(PedidoPlatoDao dao, OnPedidoResultCallback context) {
//        this.dao = dao;
//        this.callback = context;
//    }
//
//    @Override
//    protected Pedido doInBackground(Integer id) {
//        Pedido pedido = new Pedido( dao.getPlatos(id));
//        return pedido;
//    }
//
//    @Override
//    protected void onPostExecute(Pedido pedido) {
//        super.onPostExecute(pedido);
//        callback.onResult(pedido);
//    }
//}
interface OnPedidoResultCallback {
    void onResult(List<Pedido> pedido);
}
//}