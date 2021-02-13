package com.ulkanova.model;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.ulkanova.PedidoActivity;
import com.ulkanova.R;
import com.ulkanova.dao.PlatoDao;

public class PlatoViewHolder2   extends ViewHolder {

    ImageView imagenPlato;
    TextView lblPrecio, lblPlato;
    Button btnPedir;
    private Plato platoSeleccionado;

    public PlatoViewHolder2(@NonNull View v) {
        super(v);
        imagenPlato = v.findViewById(R.id.imagenPlato);
        lblPlato = v.findViewById(R.id.lblPlato);
        lblPrecio = v.findViewById(R.id.lblPrecio);
        btnPedir = v.findViewById(R.id.btnPedir);
//        btnPedir.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v1) {
//                Log.d("PEDIR", "onBindViewHolder: Hace el click");
//                Integer posicion = (Integer) v1.getTag();
//                Log.d("PEDIR", "Posicion: "+posicion);
//                Log.d("PEDIR", "PlatoDao.instancia.list().get(posicion):"+ PlatoDao.instancia.list().get(posicion));
////                platoSeleccionado = PlatoDao.instancia.list().get(posicion);
////
////                Intent agregarPedido = new Intent(v1.getContext(), PedidoActivity.class);
////                agregarPedido.putExtra("plato",platoSeleccionado);
//            }
//        });
    }
}
