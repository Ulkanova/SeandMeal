package com.ulkanova.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.http.Field;

@Entity
public class Pedido {
   @PrimaryKey//(autoGenerate = true)
    @NonNull
    @Expose
    @SerializedName(value="id")
    private String pedidoId;
    @Expose
    private String email;
    @Expose
    private String direccion;
    @Expose
    private boolean delivery;
    @Ignore
    @Expose
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
    public String getPedidoId() { return pedidoId; }

    public void setPedidoId(String pedidoId) {  this.pedidoId = pedidoId;  }

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
