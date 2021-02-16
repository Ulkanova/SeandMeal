package com.ulkanova;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ulkanova.dao.AppRepository;
import com.ulkanova.memoria.PlatoDaoMem;
import com.ulkanova.model.Plato;
import com.ulkanova.retrofit.PlatoRepositoryApi;

import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;
import java.util.List;


public class NuevoPlato extends AppCompatActivity implements AppRepository.OnResultCallback{
    FirebaseStorage storage = FirebaseStorage.getInstance();
    Toolbar toolbar;
    Button guardar;
    ImageButton btnCamara;
    ImageView imagenPlato;
    String imagenURI=null;
    EditText txtTitulo, txtPrecio, txtDescripcion, txtCalorias;
    PlatoDaoMem platosMem = PlatoDaoMem.instancia;
    private final NuevoPlato.MyPlatoHandler mHandler = new NuevoPlato.MyPlatoHandler(this);
//    AppRepository repository;
    PlatoRepositoryApi respositorioApi;
    boolean retorno;

    static final int CAMARA_REQUEST = 1;
    static final int GALERIA_REQUEST = 2;


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
        btnCamara=findViewById(R.id.btnCamara);
        imagenPlato = findViewById(R.id.imagenPlatoFoto);
        imagenPlato.setImageResource(android.R.drawable.ic_menu_gallery);

//        repository = new AppRepository(this.getApplication(), this);
        respositorioApi = new PlatoRepositoryApi();

        View.OnClickListener listenerClick  = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == guardar.getId() && !vacio(txtPrecio) && !vacio(txtCalorias) && !vacio(txtTitulo) && !vacio(txtDescripcion)){
                    try {
                        Double Precio = Double.parseDouble(txtPrecio.getText().toString());
                        Integer Calorias = Integer.parseInt(txtCalorias.getText().toString());
                        Plato plato = new Plato(txtTitulo.getText().toString(),txtDescripcion.getText().toString(),Precio,Calorias,imagenURI);
//                        platos.add(plato);
                        txtDescripcion.getText().clear();
                        txtCalorias.getText().clear();
                        txtPrecio.getText().clear();
                        txtTitulo.getText().clear();
                        respositorioApi.insertar(plato, mHandler);
                        imagenPlato.setImageResource(android.R.drawable.ic_menu_gallery);
//                        Toast.makeText(getApplicationContext(),plato.getTitulo()+" se ha guardado correctamente",Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        };
        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarCamara();
            }
        });
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
            NuevoPlato activity = mActivity.get();
            if (activity != null) {
                Bundle data = msg.getData();
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

    private void lanzarCamara() {
        Intent camaraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camaraIntent, CAMARA_REQUEST);
    }

   
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == CAMARA_REQUEST || requestCode == GALERIA_REQUEST) && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] dato = baos.toByteArray(); // Imagen en arreglo de bytes
            imagenPlato.setImageBitmap(imageBitmap);
            guardarImagen(dato);
        }
    }
    private boolean vacio (EditText campo){
        if(campo.getText().length()==0) return true;
        else return false;
    }

    private void guardarImagen(byte[] data) {
        // Creamos una referencia a nuestro Storage
        StorageReference storageRef = storage.getReference();

        // Creamos una referencia a 'images/plato_id.jpg'
        StorageReference platosImagesRef = storageRef.child("images/plato_id.jpg");

        UploadTask uploadTask = platosImagesRef.putBytes(data);

        // Registramos un listener para saber el resultado de la operación
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                // Continuamos con la tarea para obtener la URL
                return platosImagesRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    // URL de descarga del archivo
                    Uri downloadUri = task.getResult();
                    imagenURI = downloadUri.toString();
                } else {
                    // Fallo
                }
            }
        });
    }

    @Override
    public void onResult(List result) {

        Toast.makeText(this, "Exito! ", Toast.LENGTH_SHORT).show();
    }
}