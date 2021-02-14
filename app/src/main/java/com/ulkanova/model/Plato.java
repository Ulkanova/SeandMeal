package com.ulkanova.model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Plato implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long platoId;
    private String titulo;
    private String descripcion;
    private Double precio;
    private Integer calorias;

    public Plato(String titulo, String descripcion, Double precio, Integer calorias) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.calorias = calorias;
    }

    @Ignore
    public Plato(String titulo, Double precio) {
        this.titulo = titulo;
        this.precio = precio;
    }

    @Ignore
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

    public Long getPlatoId() { return platoId; }

    public void setPlatoId(Long id) {  this.platoId = id;  }

    protected Plato(Parcel in) {
        platoId = in.readByte() == 0x00 ? null : in.readLong();
        titulo = in.readString();
        descripcion = in.readString();
        precio = in.readByte() == 0x00 ? null : in.readDouble();
        calorias = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (platoId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(platoId);
        }
        dest.writeString(titulo);
        dest.writeString(descripcion);
        if (precio == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(precio);
        }
        if (calorias == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(calorias);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Plato> CREATOR = new Parcelable.Creator<Plato>() {
        @Override
        public Plato createFromParcel(Parcel in) {
            return new Plato(in);
        }

        @Override
        public Plato[] newArray(int size) {
            return new Plato[size];
        }
    };
}