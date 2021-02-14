package com.ulkanova.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Pedido {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int pedidoId;
    private String email;
    private String direccion;
    private boolean delivery;
    @Ignore
    private List<Plato> platos;

    @Ignore
    public Pedido(String email, String direccion, boolean delivery, List<Plato> platos) {
        this.email = email;
        this.direccion = direccion;
        this.delivery = delivery;
        this.platos = platos;
    }

    public Pedido(String email, String direccion, boolean delivery) {
        this.email = email;
        this.direccion = direccion;
        this.delivery = delivery;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public int getPedidoId() { return pedidoId; }

    public void setPedidoId(int pedidoId) {  this.pedidoId = pedidoId;  }

    public boolean isDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    public List<Plato> getPlatos() {
        return platos;
    }

    public void setPlatos(List<Plato> platos) {
        this.platos = platos;
    }

//    public long getId() {
//        return pedidoId;
//    }
//
//    public void setId(int id) {
//        this.pedidoId = id;
//    }
}
