package com.ulkanova;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ulkanova.dao.AppRepository;
import com.ulkanova.dao.PlatoDao;
import com.ulkanova.dao.PlatoDaoMem;
import com.ulkanova.model.Plato;

import java.util.List;


public class NuevoPlato extends AppCompatActivity implements AppRepository.OnResultCallback{
    Toolbar toolbar;
    Button guardar;
    EditText txtTitulo, txtPrecio, txtDescripcion, txtCalorias;
    PlatoDaoMem platosMem = PlatoDaoMem.instancia;
    AppRepository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_plato);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Nuevo Plato");
        guardar = findViewById(R.id.btnGuardarPlato);
        txtCalorias=findViewById(R.id.txtCalorias);
        txtTitulo=findViewById(R.id.txtTitulo);
        txtDescripcion=findViewById(R.id.txtDescripcion);
        txtPrecio=findViewById(R.id.txtPrecio);

        repository = new AppRepository(this.getApplication(), this);

        View.OnClickListener listenerClick  = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == guardar.getId() && !vacio(txtPrecio) && !vacio(txtCalorias) && !vacio(txtTitulo) && !vacio(txtDescripcion)){
                    try {
                        Double Precio = Double.parseDouble(txtPrecio.getText().toString());
                        Integer Calorias = Integer.parseInt(txtCalorias.getText().toString());
                        Plato plato = new Plato(txtTitulo.getText().toString(),txtDescripcion.getText().toString(),Precio,Calorias);
//                        platos.add(plato);
                        txtDescripcion.getText().clear();
                        txtCalorias.getText().clear();
                        txtPrecio.getText().clear();
                        txtTitulo.getText().clear();
                        repository.insertar(plato);

                        Toast.makeText(getApplicationContext(),plato.getTitulo()+" se ha guardado correctamente",Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        };

        guardar.setOnClickListener(listenerClick);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                Toast.makeText(this, "WHAT?? HOW??", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }



    private boolean vacio (EditText campo){
        if(campo.getText().length()==0) return true;
        else return false;
    }

    @Override
    public void onResult(List result) {
        Toast.makeText(this, "Exito! "+result.get(0).toString(), Toast.LENGTH_SHORT).show();
    }
}