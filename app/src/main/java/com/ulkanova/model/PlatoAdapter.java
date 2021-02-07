package com.ulkanova.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ulkanova.R;

import java.util.List;


public class PlatoAdapter extends RecyclerView.Adapter<PlatoViewHolder> {
    @NonNull
    private List<Plato> mDataset;

    public PlatoAdapter(List<Plato> myDataset){
        mDataset=myDataset;
    }

    @Override
    public PlatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fila_plato, parent, false);

        PlatoViewHolder  vh = new PlatoViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PlatoViewHolder holder, int position) {
//Implementar---
//        holder.imagenPlato.setTag(position);
        holder.lblPrecio.setTag(position);
        holder.lblPlato.setTag(position);
        Plato platos = mDataset.get(position);
        holder.lblPlato.setText(platos.getTitulo().toUpperCase());
        String precioVisual = "$ "+platos.getPrecio().toString();
        holder.lblPrecio.setText(precioVisual);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
