package com.ulkanova.dao;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Junction;
import androidx.room.Relation;

import com.ulkanova.model.Pedido;
import com.ulkanova.model.Plato;

import java.util.List;
//
//@Entity(tableName = "pedido_plato",
//        primaryKeys = {"pedidoId", "platoId"}
//,
//        foreignKeys = {
//            @ForeignKey(entity = Pedido.class, parentColumns = "pedidoId", childColumns = "pedidoId"),
//            @ForeignKey(entity = Plato.class, parentColumns = "platoId", childColumns = "platoId")
//        })
//public class PedidoPlato {
//    public long pedidoId;
//    public long platoId;
//}

