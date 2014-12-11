package com.example.gonzalo.intercambiodatos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ActivityBorrar extends Activity {

    Button bDel;
    TextView etTel;
    TextView tvNom;
    Contacto c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borra_contacto);

        bDel = (Button) findViewById(R.id.bDel);
        etTel = (TextView) findViewById(R.id.etTelEdit);
        tvNom = (TextView) findViewById(R.id.nomContato);

        c = (Contacto) getIntent().getSerializableExtra("contacto");


        tvNom.setText(c.getNombre());
        etTel.setText(c.getTelefono());

        bDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                borrar();
            }
        });


    }

    private void borrar() {

        Intent intent = new Intent(ActivityBorrar.this, Lista.class);
        intent.putExtra("contacto",  c);
        setResult(RESULT_OK, intent);
        finish();

    }


}
