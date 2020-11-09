package com.ulkanova;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Switch swCarga;
    SeekBar sbCarga;
    TextView lblMonto;
    CheckBox chktyc;
    EditText txtNombre, txtPass, txtRepeatPass, txtEmail, txtTarjeta, txtCCV, txtMes, txtYear, txtCBU, txtAlias;
    //Salto para redondear la carga inicial
    Integer step=50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        CompoundButton.OnCheckedChangeListener listenerSWCHK = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch (buttonView.getId())
                {
                    case R.id.swCargaInicial:
                     if(isChecked) {
                         sbCarga.setVisibility(View.VISIBLE);
                         lblMonto.setVisibility(View.VISIBLE);
                     }
                     else {
                         sbCarga.setVisibility(View.GONE);
                         lblMonto.setVisibility(View.GONE);
                     }
                     break;
                    case R.id.chkTyC:
                     if(!isChecked) Toast.makeText(MainActivity.this, "Acepte los términos y condiciones para continuar", Toast.LENGTH_LONG).show();
                    break;
                 }
                }

        };

        SeekBar.OnSeekBarChangeListener listenerSeek = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                lblMonto.setText("$ " + Integer.toString(progress));
                sbCarga.setProgress(Math.round(progress/step)*step);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
/*        View.OnFocusChangeListener focusListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
               if(v.getId()==txtTarjeta.getId()){
                  if(!hasFocus){
                        Toast.makeText(MainActivity.this,"tamaño: "+txtTarjeta.getTextSize(),Toast.LENGTH_SHORT);
                        if (txtTarjeta.getText().length()>0) txtCCV.setEnabled(true);
                        else{
                            txtCCV.setText("");
                            txtCCV.setEnabled(false);
                        }
                    }

                }
            }
        };*/
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
       // txtTarjeta.setOnFocusChangeListener(focusListener);

        swCarga.setOnCheckedChangeListener(listenerSWCHK);
        chktyc.setOnCheckedChangeListener(listenerSWCHK);
        sbCarga.setOnSeekBarChangeListener(listenerSeek);
    }



}