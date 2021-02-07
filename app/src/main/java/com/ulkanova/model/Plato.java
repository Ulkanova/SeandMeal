package com.ulkanova.model;

public class Plato {
    private String titulo, descripcion;
    private Double precio;
    private Integer calorias;

    public Plato(String titulo, String descripcion, Double precio, Integer calorias) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.calorias = calorias;
    }

    public Plato(String titulo, Double precio) {
        this.titulo = titulo;
        this.precio = precio;
    }

    public Plato(String titulo, Double precio, Integer calorias) {
        this.titulo = titulo;
        this.precio = precio;
        this.calorias = calorias;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getCalorias() {
        return calorias;
    }

    public void setCalorias(Integer calorias) {
        this.calorias = calorias;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
