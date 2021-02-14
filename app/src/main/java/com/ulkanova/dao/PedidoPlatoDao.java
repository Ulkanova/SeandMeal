package com.ulkanova.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.ulkanova.model.Plato;

import java.util.List;

@Dao
public interface PedidoPlatoDao{
//    @Insert
//    void insertar(PedidoPlato pedidoPlato);
//
//    @Transaction
//    @Query("SELECT * FROM plato "+
//    "INNER JOIN pedido_plato ON plato.platoId = pedido_plato.platoId "+
//    "WHERE pedido_plato.pedidoId = :pedido")
//    List<Plato> getPlatos(final int pedido);
}
