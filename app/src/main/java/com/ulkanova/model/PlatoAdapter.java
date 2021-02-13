package com.ulkanova.model;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ulkanova.PedidoActivity;
import com.ulkanova.R;

import java.util.List;


public class PlatoAdapter extends RecyclerView.Adapter<PlatoAdapter.PlatoViewHolder>{
    @NonNull
    private List<Plato> mDataset;
    private Plato platoSeleccionado;
    private boolean pedir=false;
    private OnPlatoListener mOnPlatoListener;

    public PlatoAdapter(List<Plato> myDataset, Boolean pedido, OnPlatoListener onPlatoListener){
        this.pedir=pedido;
        this.mDataset=myDataset;
        this.mOnPlatoListener=onPlatoListener;
    }

    @Override
    public PlatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fila_plato, parent, false);
        PlatoViewHolder  vh = new PlatoViewHolder(v, mOnPlatoListener);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull PlatoViewHolder holder, int position) {
        holder.lblPrecio.setTag(position);
        holder.lblPlato.setTag(position);
        Plato platos = mDataset.get(position);
        holder.lblPlato.setText(platos.getTitulo().toUpperCase());
        String precioVisual = "$ "+platos.getPrecio().toString();
        holder.lblPrecio.setText(precioVisual);
        if (pedir) holder.btnPedir.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public class PlatoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imagenPlato;
        TextView lblPrecio, lblPlato;
        Button btnPedir;
        OnPlatoListener onPlatoListener;

        public PlatoViewHolder(@NonNull View v, OnPlatoListener onPlatoListener) {
            super(v);
            imagenPlato = v.findViewById(R.id.imagenPlato);
            lblPlato = v.findViewById(R.id.lblPlato);
            lblPrecio = v.findViewById(R.id.lblPrecio);
            btnPedir = v.findViewById(R.id.btnPedir);
            this.onPlatoListener=onPlatoListener;
            btnPedir.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onPlatoListener.onPlatoClick(getAdapterPosition());
        }
    }

    public interface OnPlatoListener{
        void onPlatoClick (int posicion);
    }
}
