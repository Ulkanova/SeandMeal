package com.ulkanova.dao;

import com.ulkanova.model.Plato;

import java.util.ArrayList;
import java.util.List;

public enum PlatoDao {
   instancia;
   List<Plato> platos = new ArrayList<>();

//    private PlatoDao(){}

    public List<Plato> list(){
        return this.platos;
    }

    public void add(String titulo, String descripcion, Double precio, Integer calorias){
        platos.add(new Plato(titulo, descripcion, precio, calorias));
    }

    public void add(Plato plato){
        platos.add(plato);
    }
}
