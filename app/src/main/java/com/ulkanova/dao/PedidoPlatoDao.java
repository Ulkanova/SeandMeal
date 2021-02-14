package com.ulkanova.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.ulkanova.model.Plato;

import java.util.List;

@Dao
public interface PedidoPlatoDao{
//    void insertar(int pedidoId, Long aLong);
    @Insert
    void insertar(PedidoPlato pedidoPlato);

//    @Query("INSERT INTO pedido_plato VALUES (:pedidoId,:platoId)")
//    void insertarPorId(long pedidoId, long platoId);

    @Transaction
    @Query("SELECT * FROM plato "+
    "INNER JOIN pedido_plato ON plato.platoId = pedido_plato.platoId "+
    "WHERE pedido_plato.pedidoId = :pedido")
    List<Plato> getPlatos(final long pedido);
}
