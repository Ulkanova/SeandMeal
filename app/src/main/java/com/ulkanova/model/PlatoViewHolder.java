package com.ulkanova.model;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.ulkanova.R;

public class PlatoViewHolder extends ViewHolder {

    ImageView imagenPlato;
    TextView lblPrecio, lblPlato;

    public PlatoViewHolder(@NonNull View v) {
        super(v);
        imagenPlato = v.findViewById(R.id.imagenPlato);
        lblPlato = v.findViewById(R.id.lblPlato);
        lblPrecio = v.findViewById(R.id.lblPrecio);
    }
}
