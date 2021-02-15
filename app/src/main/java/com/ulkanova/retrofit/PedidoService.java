package com.ulkanova.retrofit;

import com.ulkanova.model.Pedido;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PedidoService {
    @GET("pedidos/{id}")
    Call<Pedido> getPedido(@Path("id") String id);

    @GET("pedidos")
    Call<List<Pedido>> getPedidosList();

    @POST("pedidos")
    Call<Pedido> createPedido(@Body Pedido pedido);

}