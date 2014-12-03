package com.example.gonzalo.intercambiodatos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class EditaContacto extends Activity {

    Button bSave;
    Button bDel;
    EditText etTel;
    TextView tvNom;
    List<Contacto> agenda = new ArrayList<Contacto>();
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita_contacto);

        bDel = (Button) findViewById(R.id.bDel);
        bSave = (Button) findViewById(R.id.bSave);
        etTel = (EditText) findViewById(R.id.etTelEdit);
        tvNom = (TextView) findViewById(R.id.nomContato);
        agenda = (List<Contacto>) getIntent().getSerializableExtra("agenda");

        pos = getIntent().getIntExtra("pos", -1);

        tvNom.setText(agenda.get(pos).getNombre());
        etTel.setText(agenda.get(pos).getTelefono());

        bDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                borrar();
            }
        });
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarMod();
            }
        });
    }

    private void borrar() {
        if (pos > -1) {
            agenda.remove(pos);
            Intent intent = new Intent(EditaContacto.this, Lista.class);
            intent.putExtra("agenda", (java.io.Serializable) agenda);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private void guardarMod() {

        if (pos > -1) {
            agenda.get(pos).setTelefono(etTel.getText().toString());
            Intent intent = new Intent(EditaContacto.this, Lista.class);
            intent.putExtra("agenda", (java.io.Serializable) agenda);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
