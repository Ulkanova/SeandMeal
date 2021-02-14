package com.ulkanova;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ulkanova.model.CuentaBancaria;
import com.ulkanova.model.Tarjeta;
import com.ulkanova.model.Usuario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Switch swCarga;
    SeekBar sbCarga;
    TextView lblMonto;
    CheckBox chktyc;
    EditText txtNombre, txtPass, txtRepeatPass, txtEmail, txtTarjeta, txtCCV, txtMes, txtYear, txtCBU, txtAlias;
    //Salto para redondear la carga inicial. Tambien es el valor minimo
    Integer step=50;
    Button btnRegistrar;
    String patronEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+" ;
    RadioButton rdbCredito;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Registro");

        swCarga = findViewById(R.id.swCargaInicial);
        sbCarga = findViewById(R.id.sbCargaInicial);
        lblMonto = findViewById(R.id.lblMontoCarga);
        chktyc = findViewById(R.id.chkTyC);
        txtNombre = findViewById(R.id.txtNombre);
        txtPass = findViewById(R.id.txtPass);
        txtRepeatPass = findViewById(R.id.txtRptPass);
        txtEmail =findViewById(R.id.txtEmail);
        txtTarjeta =findViewById(R.id.txtTarjeta);
        txtCCV = findViewById(R.id.txtCcv);
        txtMes = findViewById(R.id.txtMes);
        txtYear = findViewById(R.id.txtAnio);
        txtCBU = findViewById(R.id.txtCbu);
        txtAlias =findViewById(R.id.txtAlias);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        rdbCredito = findViewById(R.id.rdbtnCredito);


//      CHECK LISTENER
        CompoundButton.OnCheckedChangeListener listenerSWCHK = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch (buttonView.getId())
                {
                    case R.id.swCargaInicial:
                         if(isChecked) {
                             sbCarga.setVisibility(View.VISIBLE);
                             lblMonto.setVisibility(View.VISIBLE);
                             sbCarga.setProgress(step); //Setea el valor minimo de carga
                         }
                         else {
                             sbCarga.setProgress(0);
                             sbCarga.setVisibility(View.GONE);
                             lblMonto.setVisibility(View.GONE);
                         }
                         break;
                    case R.id.chkTyC:
                         if(isChecked) {
                             btnRegistrar.setEnabled(true);
                         }
                         else btnRegistrar.setEnabled(false);
                        break;
                 }
                }

        };

//      SEEK BAR
        SeekBar.OnSeekBarChangeListener listenerSeek = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                lblMonto.setText("$ " + Integer.toString(progress));
                if(progress==0 && swCarga.isChecked()) sbCarga.setProgress(step);
                else sbCarga.setProgress(Math.round(progress/step)*step);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        };
        View.OnFocusChangeListener focusListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if((v.getId()==txtRepeatPass.getId() || v.getId()==txtPass.getId()) && !vacio(txtRepeatPass)){
                    if(!txtPass.getText().toString().trim().matches(txtRepeatPass.getText().toString().trim())) {
                        txtRepeatPass.setError(getString(R.string.mensajePass));
                    }
                    else {
                        txtRepeatPass.setError(null);
                    }
                }
                else if (v.getId()==txtEmail.getId() && !txtEmail.getText().toString().matches(patronEmail) && !hasFocus){
                    ((EditText) v).setError("Email inválido");
                }

                else if (v.getId()==txtMes.getId()  && !hasFocus){
                    try{
                        int mes=Integer.parseInt(((EditText) v).getText().toString());
                        if (mes<=0 || mes>12){
                            ((EditText) v).setError("Mes inválido");
                        }
                        else {
                            ((EditText) v).setError(null);
                        }
                    }
                    catch (NumberFormatException e){
                        ((EditText) v).setError("Mes inválido");
                    }
                }
                else if (v.getId()==txtYear.getId()  && !hasFocus){
                    try{
                        int anio=Integer.parseInt(((EditText) v).getText().toString());
                        if (anio<=1900 || anio>2500) {
                            ((EditText) v).setError("Año inválido");
                        }
                        else {
                            if (!fechasValidas(txtMes,txtYear)) {
                                Toast.makeText(MainActivity.this, "La fecha de vencimiento debe ser mayor a tres meses de la fecha actual",Toast.LENGTH_LONG).show();
                                txtMes.setError("Vencimiento próximo");
                                txtYear.setError("Vencimiento próximo");
                            }
                            else {
                                txtMes.setError(null);
                                txtYear.setError(null);
                            }
                        }
                    }
                    catch (NumberFormatException e){
                        ((EditText) v).setError("Año inválido");
                    }

                }
                else if (v instanceof EditText && !hasFocus) {
                    if (vacio((EditText)v)) {
                        ((EditText) v).setError("Campo requerido");
                    }
                    else ((EditText) v).setError(null);
                }
          /*     if(v.getPedidoId()==txtTarjeta.getPedidoId()){
                  if(!hasFocus){
                        Toast.makeText(MainActivity.this,"tama&ntilde;o: "+txtTarjeta.getTextSize(),Toast.LENGTH_SHORT);
                        if (txtTarjeta.getText().length()>0) txtCCV.setEnabled(true);
                        else{
                            txtCCV.setText("");
                            txtCCV.setEnabled(false);
                        }
                    }
                }*/
            }
        };
        txtTarjeta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (txtTarjeta.getText().length()>0)  txtCCV.setEnabled(true);
                else{
                    txtCCV.setText("");
                    txtCCV.setEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txtTarjeta.getText().length()>0)  txtCCV.setEnabled(true);
                else{
                    txtCCV.setText("");
                    txtCCV.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (txtTarjeta.getText().length()>0)  txtCCV.setEnabled(true);
                else{
                    txtCCV.setText("");
                    txtCCV.setEnabled(false);
                }
            }
        });


        View.OnClickListener listenerClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==btnRegistrar.getId() && validarCampos()) {
                    String nombre = txtNombre.getText().toString();
                    String clave = txtPass.getText().toString();
                    String email =txtEmail.getText().toString();
                    Double credito = (double) sbCarga.getProgress();
                    String numero = txtTarjeta.getText().toString();
                    String ccv = txtCCV.getText().toString();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
                    Date vencimiento = new Date();
                    Boolean esCredito;
                    String cbu = txtCBU.getText().toString();
                    String alias = txtAlias.getText().toString();
                    if (rdbCredito.isChecked()) esCredito = true;
                    else esCredito = false;
                    try {
                        vencimiento = dateFormat.parse(txtYear.getText().toString()+"-"+txtMes.getText().toString());
                        Tarjeta tarjeta = new Tarjeta(numero,ccv, vencimiento, esCredito);
                        CuentaBancaria cuenta = new CuentaBancaria(cbu, alias);
                        Usuario user = new Usuario((int) Math.random(),nombre,clave,email,credito,tarjeta,cuenta);
                    } catch (ParseException e) {
                        //Se supone que nunca llegaría a dispararse esta excepción porque se captura antes en validarCampos()
                    }
                }
            }
        };


        txtAlias.setOnFocusChangeListener(focusListener);
        txtNombre.setOnFocusChangeListener(focusListener);
        txtEmail.setOnFocusChangeListener(focusListener);
        txtTarjeta.setOnFocusChangeListener(focusListener);
        txtCCV.setOnFocusChangeListener(focusListener);
        txtMes.setOnFocusChangeListener(focusListener);
        txtYear.setOnFocusChangeListener(focusListener);
        txtCBU.setOnFocusChangeListener(focusListener);
        txtPass.setOnFocusChangeListener(focusListener);
        txtRepeatPass.setOnFocusChangeListener(focusListener);
        swCarga.setOnCheckedChangeListener(listenerSWCHK);
        chktyc.setOnCheckedChangeListener(listenerSWCHK);
        sbCarga.setOnSeekBarChangeListener(listenerSeek);
        btnRegistrar.setOnClickListener(listenerClick);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
            case android.R.id.home: onBackPressed(); return true;
            default:
                Toast.makeText(this, "How do you??", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    private boolean vacio (EditText campo){
        if(campo.getText().length()==0) return true;
        else return false;
    }

    private boolean validarCampos(){
        if(
//                VACIOS
               !vacio(txtNombre) && !vacio(txtPass) && !vacio(txtRepeatPass) && !vacio(txtEmail) &&
               !vacio(txtTarjeta) && !vacio(txtCCV) && !vacio(txtMes) && !vacio(txtYear) && !vacio(txtCBU) && !vacio(txtAlias)
//               EMAIL VALIDO
              && txtEmail.getText().toString().matches(patronEmail)
//               VENCIMIENTOS MAYOR A 3 DIAS
               && fechasValidas(txtMes, txtYear)
//               CONTRASEÑAS COINCIDENTES
               && txtPass.getText().toString().trim().matches(txtRepeatPass.getText().toString().trim())
//               ACEPTADO TYC
               && chktyc.isChecked()
//               MES Y AÑO VALIDOS
               && mesValido(txtMes.getText().toString()) && anioValido(txtYear.getText().toString())
        ){
            Toast.makeText(MainActivity.this, "Ingreso correcto",Toast.LENGTH_LONG).show();
            return true;
        }
        else {
            Toast.makeText(MainActivity.this, "Hay datos incorrectos",Toast.LENGTH_LONG).show();
            return false;
        }
    }
    private boolean fechasValidas(EditText txtmes, EditText txtyear){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        Date actual90 = new Date();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH,3);
        actual90 = c.getTime();
        Date vencimiento = new Date();
        try {
            vencimiento = dateFormat.parse(txtyear.getText().toString()+"-"+txtmes.getText().toString());
        } catch (ParseException e) {
            return false;
        }
        if(actual90.before(vencimiento)) return true;
        else return false;
    }
    private boolean mesValido(String mes){
        try{
            int dato=Integer.parseInt(mes);
            if (dato<=0 || dato>12){
                return false;
            }
        }
        catch (NumberFormatException e){
            return false;
        }
        return true;
    }
    private boolean anioValido(String year){
        try{
            int anio=Integer.parseInt(year);
            if (anio<=1900 || anio>2500){
                return false;
            }
        }
        catch (NumberFormatException e){
            return false;
        }
        return true;
    }
}