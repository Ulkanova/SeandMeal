package com.ulkanova;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ulkanova.dao.AppRepository;
import com.ulkanova.dao.PedidoRepository;
import com.ulkanova.memoria.PlatoDaoMem;
import com.ulkanova.model.Plato;
import com.ulkanova.model.PlatoAdapter;
import com.ulkanova.retrofit.PlatoRepositoryApi;

import java.lang.ref.WeakReference;
import java.util.List;


public class NuevoPlato extends AppCompatActivity implements AppRepository.OnResultCallback{
    Toolbar toolbar;
    Button guardar;
    EditText txtTitulo, txtPrecio, txtDescripcion, txtCalorias;
    PlatoDaoMem platosMem = PlatoDaoMem.instancia;
    private final NuevoPlato.MyPlatoHandler mHandler = new NuevoPlato.MyPlatoHandler(this);
//    AppRepository repository;
    PlatoRepositoryApi respositorioApi;
    boolean retorno;


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

//        repository = new AppRepository(this.getApplication(), this);
        respositorioApi = new PlatoRepositoryApi();

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
                        respositorioApi.insertar(plato, mHandler);

//                        Toast.makeText(getApplicationContext(),plato.getTitulo()+" se ha guardado correctamente",Toast.LENGTH_SHORT).show();
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

    private static class MyPlatoHandler extends Handler {
        private final WeakReference<NuevoPlato> mActivity;

        public MyPlatoHandler(NuevoPlato activity) {
            mActivity = new WeakReference<NuevoPlato>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.d("PLATO", "MENSAJE RECIBIDO ");
            NuevoPlato activity = mActivity.get();
            if (activity != null) {
                Bundle data = msg.getData();
//                ArrayList<Plato> losPlatos = data.getParcelableArrayList("plato");
                activity.retorno = data.getBoolean("insertado");
                if (!activity.retorno) {
                    Toast.makeText(activity, "No se ha podido crear el plato. Intente nuevamente", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(activity, "Plato creado correctamente", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }


    private boolean vacio (EditText campo){
        if(campo.getText().length()==0) return true;
        else return false;
    }

    @Override
    public void onResult(List result) {

        Toast.makeText(this, "Exito! ", Toast.LENGTH_SHORT).show();
    }
}