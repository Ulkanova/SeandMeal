package com.ulkanova;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ulkanova.dao.AppRepository;
import com.ulkanova.dao.PlatoDaoMem;
import com.ulkanova.model.Plato;
import com.ulkanova.model.PlatoAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListaPlatos extends AppCompatActivity implements PlatoAdapter.OnPlatoListener, AppRepository.OnResultCallback{
    public static final int CODIGO_PEDIDO = 777;
//    PlatoDaoMem daoPlatos = PlatoDaoMem.instancia;
    Toolbar toolbar;
    private RecyclerView recyclerView;
    private PlatoAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    boolean pedido=false;
    AppRepository repository;
    List<Plato> platos = new ArrayList<>();

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

//        Si pedido es verdadero, es porque se viene desde la actividad Pedido
        pedido= getIntent().getBooleanExtra("pedido",false);

        repository = new AppRepository(this.getApplication(), this);
        repository.buscarTodos();
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
        iplatoElegido.putExtra("plato",platos.get(posicion));
        setResult(Activity.RESULT_OK,iplatoElegido);
        finish();
        Toast.makeText(getApplicationContext(), "PLATO: "+platos.get(posicion).getTitulo(),Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onResult(List result) {
        mAdapter = new PlatoAdapter(result,pedido,this);
        platos = result;
        recyclerView.setAdapter(mAdapter);
    }
}