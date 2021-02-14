package com.ulkanova.dao;

import com.ulkanova.model.Plato;

import java.util.ArrayList;
import java.util.List;

public enum PlatoDaoMem {
   instancia;
   List<Plato> platos = new ArrayList<>();



    public List<Plato> list(){
        if(platos.isEmpty()){        iniciar();}
        return this.platos;
    }

    public void add(String titulo, String descripcion, Double precio, Integer calorias){
        platos.add(new Plato(titulo, descripcion, precio, calorias));
    }

    public void add(Plato plato){
        platos.add(plato);
    }

    public void iniciar(){
        platos.add(new Plato("Empanadas","Una docena", 450.00, 1899 ));
        platos.add(new Plato("Pizza","La clásica de muzza", 400.00, 900 ));
        platos.add(new Plato("Milanesa al plato","Con papas fritas", 350.00, 700 ));
        platos.add(new Plato("Pollo al horno","Con papas fritas o ensalada", 850.00, 2600 ));
        platos.add(new Plato("Helado","Medio kilo", 250.70, 855 ));
    }
}
