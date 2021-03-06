package com.example.gonzalo.intercambiodatos;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Lista extends ListActivity {

    ListView listView;
    public List<Contacto> agenda = new ArrayList<Contacto>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        agenda = (List<Contacto>) getIntent().getSerializableExtra("agenda");


        listView = (ListView) findViewById(android.R.id.list);

        registerForContextMenu(listView);


//     EVENTO PARA LANZAR LA ACTIVITY EDITACONTACTO CON UN SOLO CLICK
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                final Contacto c = (Contacto) listView.getItemAtPosition(position);
//
//                accionPulsoEditar(c.getNombre());
//            }
//
//        });
//
        actualizarLista();

    }

    private void accionPulsoBorrar(int posicion) {

        Intent intent = new Intent(this.getApplicationContext(), ActivityBorrar.class);

        intent.putExtra("contacto", agenda.get(posicion));
        startActivityForResult(intent, 2);

//        agenda.remove(posicion);
//        actualizarLista();

    }

    private void accionPulsoEditar(String nombre) {

        int pos = -1;
        for (int i = 0; i < agenda.size(); i++) {
            if (agenda.get(i).getNombre().equalsIgnoreCase(nombre)) {
                pos = i;
            }
        }

        if (exitste(nombre)) {
            Intent intent = new Intent(this.getApplicationContext(), EditaContacto.class);

            intent.putExtra("agenda", (java.io.Serializable) agenda);
            intent.putExtra("pos", pos);
            startActivityForResult(intent, 1);

        } else {
            Toast.makeText(getApplicationContext(), "Este contacto no existe", Toast.LENGTH_SHORT).show();
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

    private void actualizarLista() {

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.simple_list_item_3, R.id.text1, agenda) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(R.id.text1);
                TextView text2 = (TextView) view.findViewById(R.id.text2);
                TextView text3 = (TextView) view.findViewById(R.id.text3);
                text1.setTextColor(Color.DKGRAY);
                text2.setTextColor(Color.DKGRAY);
                text3.setTextColor(Color.DKGRAY);
                text1.setText(agenda.get(position).getNombre());
                text2.setText(agenda.get(position).getTelefono());
                text3.setText("tercer elemento");
                return view;
            }
        };
        listView.setAdapter(adapter);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && data != null) {
            if (resultCode == RESULT_OK) {
                agenda = (List<Contacto>) data.getSerializableExtra("agenda");
                Toast.makeText(getApplicationContext(), "Contacto editado", Toast.LENGTH_SHORT).show();

            }
            if (resultCode == RESULT_CANCELED) {
                agenda = (List<Contacto>) data.getSerializableExtra("agenda");
                Toast.makeText(getApplicationContext(), "Contacto eliminado", Toast.LENGTH_SHORT).show();

            }
        } else if (requestCode == 2 && data != null) {
            if (resultCode == RESULT_OK) {
                Contacto c = (Contacto) data.getSerializableExtra("contacto");
                for (int i = 0; i < agenda.size(); i++) {
                    if (agenda.get(i).getNombre().equalsIgnoreCase(c.getNombre())) {
                        agenda.remove(i);
                    }
                }
                Toast.makeText(getApplicationContext(), "Contacto eliminado", Toast.LENGTH_SHORT).show();

            }
        }
        actualizarLista();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent(Lista.this, Agenda.class);
            intent.putExtra("agenda", (java.io.Serializable) agenda);
            setResult(RESULT_OK, intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.edit:
                accionPulsoEditar(agenda.get(info.position).getNombre());
                Log.d("MENU", "EDITA " + info.position);
                return true;
            case R.id.delete:
                accionPulsoBorrar(info.position);
                Log.d("MENU", "ELIMINA");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
