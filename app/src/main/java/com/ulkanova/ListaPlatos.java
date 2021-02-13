package com.ulkanova;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ulkanova.dao.PlatoDao;
import com.ulkanova.model.Plato;
import com.ulkanova.model.PlatoAdapter;

public class ListaPlatos extends AppCompatActivity implements PlatoAdapter.OnPlatoListener {
    public static final int CODIGO_PEDIDO = 777;
    PlatoDao daoPlatos = PlatoDao.instancia;
    Toolbar toolbar;
    private RecyclerView recyclerView;
    private PlatoAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    boolean pedido=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_platos);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Lista de Platos");
        recyclerView = findViewById(R.id.recyclerPlato);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
//        Si pedido es verdadero, es porque se viene desde la actividad Pedido, entonces agrega botón "PEDIR"
        pedido= getIntent().getBooleanExtra("pedido",false);
        mAdapter = new PlatoAdapter(daoPlatos.list(),pedido,this);
        recyclerView.setAdapter(mAdapter);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itmregistrarme:
                Intent intentRegistro = new Intent(this,MainActivity.class);
                startActivity(intentRegistro);
                return true;
            case R.id.itmCrearItem:
                Intent intentCrearItem = new Intent(this,NuevoPlato.class);
                startActivity(intentCrearItem);
                return true;
            case R.id.itmListarItems:
                Intent intentListarItem = new Intent(this, ListaPlatos.class);
                startActivity(intentListarItem);
                return true;
            case R.id.itmNuevoPedido:
                Intent intentNuevoPedido = new Intent(this, PedidoActivity.class);
                startActivity(intentNuevoPedido);
                return true;
            case android.R.id.home: onBackPressed(); return true;
            default:
                Toast.makeText(this, "Menú no válido", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPlatoClick(int posicion) {
        Intent iplatoElegido = new Intent();
        iplatoElegido.putExtra("plato",daoPlatos.list().get(posicion));
        setResult(Activity.RESULT_OK,iplatoElegido);
        finish();
//        Toast.makeText(getApplicationContext(), "PLATO: "+daoPlatos.list().get(posicion).getTitulo(),Toast.LENGTH_SHORT).show();
    }
}