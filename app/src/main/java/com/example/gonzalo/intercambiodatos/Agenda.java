package com.example.gonzalo.intercambiodatos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Agenda extends Activity {

    public static List<Contacto> agenda = new ArrayList<Contacto>();

    Button bAdd, bEdit;
    EditText etAddNom, etAddTel, etSearchNom;
    ListView listView;


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
                accionPulsoEditar(etSearchNom.getText().toString());
            }
        });

        listView = (ListView) findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                Contacto c = (Contacto) listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  ListItem : " + c.toString(), Toast.LENGTH_SHORT)
                        .show();

                accionPulsoEditar(c.getNombre());
            }

        });
        actualizarLista();

    }

    private void actualizarLista() {
//        ArrayList<String> nombres = new ArrayList<String>();
//        for (int i = 0; i < agenda.size(); i++) {
//            nombres.add(agenda.get(i).getNombre());
//        }
//        Map<String,String> telefonos = new HashMap<String, String>();
//        for (int i = 0; i < agenda.size(); i++) {
//            telefonos.put(agenda.get(i).getNombre(),agenda.get(i).getTelefono());
//            //telefonos.add(agenda.get(i).getTelefono());
//        }

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
        //Log.d("ONRESULT", "On cativity Result ejecutado");
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
            if (resultCode == RESULT_CANCELED) {
//                Contacto c = (Contacto) data.getSerializableExtra("contacto");
//                int pos = -1;
//                for (int i = 0; i < agenda.size(); i++) {
//                    if (agenda.get(i).getNombre().equalsIgnoreCase(c.getNombre())) {
//                        pos = i;
//                    }
//                }
//                Toast.makeText(getApplicationContext(), "Contacto: " + agenda.get(pos) + " editado", Toast.LENGTH_SHORT).show();
//                etSearchNom.setText("");
                Toast.makeText(getApplicationContext(), "Contacto eliminado", Toast.LENGTH_SHORT).show();
                actualizarLista();
            }
        }
    }


    private void accionPulsoEditar(String nombre) {

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
            actualizarLista();
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
