package com.ulkanova;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Inicializar Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Iniciar Session como usuario anónimo
        signInAnonymously();
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
            default:
                Toast.makeText(this, "Opción inválida", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void signInAnonymously() {
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Exito
                            Log.d("FIREBASE", "signInAnonymously:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // Error
                            Log.w("FIREBASE", "signInAnonymously:failure", task.getException());
                            Toast.makeText(HomeActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
