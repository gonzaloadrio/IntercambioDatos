package com.example.gonzalo.intercambiodatos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Agenda extends Activity {

    public List<Contacto> agenda = new ArrayList<Contacto>();

    Button bAdd, bVer;
    EditText etAddNom, etAddTel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        bAdd = (Button) findViewById(R.id.bAdd);
        bVer = (Button) findViewById(R.id.bVer);
        etAddNom = (EditText) findViewById(R.id.etNomAdd);
        etAddTel = (EditText) findViewById(R.id.etTelAdd);
        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accionPulsoAñadir();
            }
        });
        bVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Lista.class);
                intent.putExtra("agenda", (java.io.Serializable) agenda);
                startActivityForResult(intent, 1);
            }
        });


    }

    private void accionPulsoAñadir() {
        String nombre, telefono;
        nombre = etAddNom.getText().toString();
        telefono = etAddTel.getText().toString();

        if (!exitste(nombre) && !nombre.isEmpty()) {
            agenda.add(new Contacto(nombre, telefono));
            Toast.makeText(getApplicationContext(), "Contacto: " + (new Contacto(nombre, telefono)).toString() + " creado", Toast.LENGTH_SHORT).show();
            etAddNom.setText("");
            etAddTel.setText("");
            etAddNom.requestFocus();

        } else {
            Toast.makeText(getApplicationContext(), "Este contacto ya existe o esta vacio", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean exitste(String nombre) {

        for (int i = 0; i < agenda.size(); i++) {
            if (agenda.get(i).getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1 && data != null) {
            if (resultCode == RESULT_OK) {

                agenda = (List<Contacto>) data.getSerializableExtra("agenda");

                Toast.makeText(getApplicationContext(), "Contacto editado", Toast.LENGTH_SHORT).show();

            }

        }

    }

}
