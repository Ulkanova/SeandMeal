package com.ulkanova.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
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
        Log.d("PLATOHOLDER", "plato.getImagen: "+platos.getImagen());
        if (!(platos.getImagen()==null)) {
            cargarImagen(holder);
        }

    }

    private void cargarImagen(PlatoViewHolder holder) {
            // Creamos una referencia al storage con la Uri de la img
        Bitmap bitmap;
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference gsReference = storage.getReferenceFromUrl("gs://send-meal-305000.appspot.com/images/plato_id.jpg");

            final long THREE_MEGABYTE = 3 * 1024 * 1024;
            gsReference.getBytes(THREE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    // Exito
                    Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    holder.imagenPlato.setImageBitmap(bm);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Error - Carga una imagen por defecto
                }
            });
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
