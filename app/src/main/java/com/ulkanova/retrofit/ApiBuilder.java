package com.ulkanova.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiBuilder {
    private PlatoService platoService;
    private static ApiBuilder _INSTANCIA;

    private void iniciarRetrofit(){
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3001/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
//        categoriaRest =retrofit.create(CategoriaDAORest.class);
        platoService =retrofit.create(PlatoService.class);
    }

    public PlatoService getPlatoService() {
        if(platoService==null) this.iniciarRetrofit();
        return platoService;
    }

    public static ApiBuilder getInstance(){
        if(_INSTANCIA == null) {
            _INSTANCIA = new ApiBuilder();
        }
        return _INSTANCIA;
    }
}
