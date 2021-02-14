package com.ulkanova.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.ulkanova.model.Pedido;
import com.ulkanova.model.Plato;

import java.util.List;

@Dao
public interface PedidoConPlatosDao {
//    @Insert
//    void insertar(PedidoconPlatos pedidoconPlatos);
//
//    @Delete
//    void borrar(PedidoconPlatos pedidoconPlatos);
//
//    @Update
//    void actualizar(PedidoconPlatos pedidoconPlatos);
//
//    @Transaction
//    @Query("SELECT * FROM pedido, pedido_plato WHERE pedido.pedidoId = pedido_plato.pedidoId AND pedido_plato.pedidoId = :id LIMIT 1")
//    Pedido buscarPedido(String id);
//
//    @Transaction
//    @Query("SELECT * FROM pedido_plato")
//    public List<PedidoconPlatos> getPedidosConPlatos();
}
