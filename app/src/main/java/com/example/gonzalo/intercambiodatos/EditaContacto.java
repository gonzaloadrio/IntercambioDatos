package com.example.gonzalo.intercambiodatos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class EditaContacto extends Activity {

    Button bSave;
    Button bDel;
    EditText etTel;
    TextView tvNom;
    String nombre;
    Contacto c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita_contacto);

        bDel = (Button) findViewById(R.id.bDel);
        bSave = (Button) findViewById(R.id.bSave);
        etTel = (EditText) findViewById(R.id.etTelEdit);
        tvNom = (TextView) findViewById(R.id.nomContato);

        Contacto c = (Contacto) getIntent().getSerializableExtra("contacto");


        nombre = c.getNombre();
        tvNom.setText(nombre);
        etTel.setText(c.getTelefono());


        bDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                borrar(nombre);
            }
        });

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarMod(nombre);
            }
        });


    }

    private void borrar(String nombre) {
        for (int i = 0; i < Agenda.agenda.size(); i++) {
            if (Agenda.agenda.get(i).getNombre().equalsIgnoreCase(nombre)) {
                Agenda.agenda.remove(i);
                Intent intent = new Intent(EditaContacto.this, Agenda.class);
                setResult(RESULT_CANCELED,getIntent());
                finish();
            }
        }
    }

    private void guardarMod(String nombre) {
        for (int i = 0; i < Agenda.agenda.size(); i++) {
            if (Agenda.agenda.get(i).getNombre().equalsIgnoreCase(nombre)) {
                c = new Contacto(nombre, etTel.getText().toString());
                Agenda.agenda.set(i, c);

                Intent intent = new Intent(EditaContacto.this, Agenda.class);
                intent.putExtra("contacto", c);
                setResult(RESULT_OK, getIntent());
                finish();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edita_contacto, menu);
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
