package com.ulkanova;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ulkanova.model.Plato;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PedidoActivity extends AppCompatActivity {
    public static final int CODIGO_PEDIDO = 777;
    Toolbar toolbar;
    EditText txtEmail, txtDireccion;
    TextView lblTotal;
    RadioButton btnDelivery, btnTakeAway;
    FloatingActionButton addPedido;
    Plato platoSeleccionado;
    ListView listViewPedidos;
    List<Plato> pedido;
    ArrayList<String> platosSeleccionados;
    ArrayList<Double> preciosPlatos;
    ArrayAdapter adapterLista;
    Button btnConfirmar;
    TextView lblPedido;
    String strplatos=" plato";
    Double total=0.0;
    ConfirmarPedidoTask tarea;
    BroadcastReceiver br;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Realizar Pedido");

        crearCanal(this);

        platosSeleccionados = new ArrayList<String>(0);
        preciosPlatos = new ArrayList<>();
        pedido = new ArrayList<>();

        tarea = new ConfirmarPedidoTask();

        // registro el brodcast receiver
        br = new PedidoReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(PedidoReceiver.PEDIDO_CONFIRMADO);
        this.registerReceiver(br, filter);

        adapterLista = new ArrayAdapter(this, android.R.layout.simple_list_item_1, platosSeleccionados);


        txtEmail = findViewById(R.id.txtEmailPedido);
        txtDireccion = findViewById(R.id.txtDireccionEnvio);
        btnDelivery = findViewById(R.id.rdbtnDelivery);
        btnTakeAway = findViewById(R.id.rdbtnTakeAway);
        addPedido = findViewById(R.id.fltAgregarPlato);
        listViewPedidos = findViewById(R.id.listViewPedidos);
        btnConfirmar = findViewById(R.id.btnConfirmarPedido);
        lblPedido = findViewById(R.id.txtMiPedido);
        lblTotal = findViewById(R.id.lblTotal);

        listViewPedidos.setAdapter(adapterLista);

        listViewPedidos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                total = total - preciosPlatos.get(position);
                preciosPlatos.remove(position);
                pedido.remove(position);

                adapterLista.remove(platosSeleccionados.get(position));
                lblTotal.setText("Total: $"+total);
                if(platosSeleccionados.size()>1) strplatos=" platos";
                if(platosSeleccionados.size()==1) strplatos=" plato";
                if(platosSeleccionados.size()==0) {
                    btnConfirmar.setEnabled(false);
                    lblPedido.setText("Mi Pedido");
                }
                else {
                    lblPedido.setText("Mi Pedido de "+platosSeleccionados.size()+strplatos);
                }
                return false;
            }
        });

        View.OnClickListener listenerClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==addPedido.getId()){
                    Intent addPedidoIntent = new Intent(PedidoActivity.this, ListaPlatos.class);
                    addPedidoIntent.putExtra("pedido",true);
                    startActivityForResult(addPedidoIntent,CODIGO_PEDIDO);
                }
                else if (v.getId()==btnConfirmar.getId()){
                    Plato[] pedidos = pedido.toArray(new Plato[0]);
                    tarea.execute(pedido.toArray(new Plato[0]));
                }
            }
        };

        btnConfirmar.setOnClickListener(listenerClick);
        addPedido.setOnClickListener(listenerClick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CODIGO_PEDIDO) {
                platoSeleccionado = data.getExtras().getParcelable("plato");
                pedido.add(platoSeleccionado);
                adapterLista.add(platoSeleccionado.getTitulo()+"\t $ "+platoSeleccionado.getPrecio());
                preciosPlatos.add(platoSeleccionado.getPrecio());
                total = total + platoSeleccionado.getPrecio();
                if(platosSeleccionados.size()>=1) btnConfirmar.setEnabled(true);
                if(platosSeleccionados.size()>1) strplatos=" platos";
                lblPedido.setText("Mi Pedido de "+platosSeleccionados.size()+strplatos);
                lblTotal.setText("Total: $"+total);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(br);
    }

    public void crearCanal(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("1", "NOTIFICACIONES", importance);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    class ConfirmarPedidoTask extends AsyncTask<Plato,Integer, Integer>{

        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(),"Su pedido está siendo procesado...",Toast.LENGTH_SHORT).show();
            btnConfirmar.setEnabled(false); //Se inhabilita para evitar java.lang.IllegalStateException al disparar el mismo hilo más de una vez
            pedido.clear();
            platosSeleccionados.clear();
            preciosPlatos.clear();
            total=0.0;
            lblPedido.setText("Mi Pedido");
            lblTotal.setText("Total: $"+total);
            adapterLista.clear();
        }

        @Override
        protected Integer doInBackground(Plato... platos) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer resultado) {

            btnConfirmar.setText("PEDIDO CONFIRMADO");
            Intent i = new Intent(getApplicationContext(),MyIntentServices.class);
            startService(i);
        }
    }
}