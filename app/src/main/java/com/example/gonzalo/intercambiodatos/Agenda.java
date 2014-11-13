package com.example.gonzalo.intercambiodatos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;


public class Agenda extends Activity {

    public static List<Contacto> agenda = new ArrayList<Contacto>();

    Button bAdd, bEdit;
    EditText etAddNom, etAddTel, etSearchNom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        bAdd = (Button) findViewById(R.id.bAdd);
        bEdit = (Button) findViewById(R.id.bEd);
        etAddNom = (EditText) findViewById(R.id.etNomAdd);
        etAddTel = (EditText) findViewById(R.id.etTelAdd);
        etSearchNom = (EditText) findViewById(R.id.etNomEd);

        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accionPulsoAñadir();
            }
        });
        bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accionPulsoEditar();
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("ONRESULT", "On cativity Result ejecutado");
        if (requestCode == 1 && data != null) {
            if (resultCode == RESULT_OK) {
                Contacto c = (Contacto) data.getSerializableExtra("contacto");
                int pos = -1;
                for (int i = 0; i < agenda.size(); i++) {
                    if (agenda.get(i).getNombre().equalsIgnoreCase(c.getNombre())) {
                        pos = i;
                    }
                }
                Toast.makeText(getApplicationContext(), "Contacto: " + agenda.get(pos) + " editado", Toast.LENGTH_SHORT).show();
                etSearchNom.setText("");
            }
        }
    }

    private void accionPulsoEditar() {
        String nombre;
        nombre = etSearchNom.getText().toString();
        int pos = -1;
        for (int i = 0; i < agenda.size(); i++) {
            if (agenda.get(i).getNombre().equalsIgnoreCase(nombre)) {
                pos = i;
            }
        }

        if (exitste(nombre)) {
            Intent intent = new Intent(getApplicationContext(), EditaContacto.class);

            Bundle extras = new Bundle();

            extras.putSerializable("contacto", agenda.get(pos));
            intent.putExtras(extras);
            startActivityForResult(intent, 1);

        } else {
            Toast.makeText(getApplicationContext(), "Este contacto no existe", Toast.LENGTH_SHORT).show();
        }
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
