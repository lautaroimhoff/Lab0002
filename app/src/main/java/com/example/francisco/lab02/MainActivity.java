package com.example.francisco.lab02;

import android.content.DialogInterface;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

import static android.support.constraint.R.id.parent;
import static android.support.v7.appcompat.R.styleable.View;


public class MainActivity extends AppCompatActivity {

    Utils.ElementoMenu elementoSeleccionado;
    ListView lista;
    Double precio;
    TextView pedidoActual;
    ArrayList<Utils.ElementoMenu> listapedidos = new ArrayList<>();
    boolean confirmado = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button agregar = (Button) findViewById(R.id.button);
        Button confirmarpedido = (Button) findViewById(R.id.button2);
        Button reiniciar = (Button) findViewById(R.id.button3);

        RadioButton plato = (RadioButton) findViewById(R.id.radioButton4);
        RadioButton postre = (RadioButton) findViewById(R.id.radioButton5);
        RadioButton bebida = (RadioButton) findViewById(R.id.radioButton6);
        RadioGroup grupo = (RadioGroup) findViewById(R.id.radioGroup);
        pedidoActual = (TextView) findViewById(R.id.pedidoActual);
        pedidoActual.setMovementMethod(new ScrollingMovementMethod());

        lista = (ListView) findViewById(R.id.listview);

        Switch avisar = (Switch) findViewById(R.id.switch1);

        Spinner desplegable = (Spinner) findViewById(R.id.spinner);
        String[] datos = {"20:00", "20:30", "21:00", "21:30", "22:00", "22:30"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);
        desplegable.setAdapter(adaptador);
        final String[] seleccionados = new String[0]; 
        ToggleButton elegir = (ToggleButton) findViewById(R.id.toggleButton);
        Utils utilidades = new Utils();
        utilidades.iniciarListas();


        final ArrayAdapter<Utils.ElementoMenu> adaptador1  = new ArrayAdapter<Utils.ElementoMenu>(this,android.R.layout.simple_list_item_1);
        final ArrayAdapter<Utils.ElementoMenu> adaptador2 = new ArrayAdapter<Utils.ElementoMenu>(this,android.R.layout.simple_list_item_single_choice,utilidades.getListaPlatos());
        final ArrayAdapter<Utils.ElementoMenu> adaptador3  = new ArrayAdapter<Utils.ElementoMenu>(this,android.R.layout.simple_list_item_single_choice,utilidades.getListaPostre());
        final ArrayAdapter<Utils.ElementoMenu> adaptador4 = new ArrayAdapter<Utils.ElementoMenu>(this,android.R.layout.simple_list_item_single_choice,utilidades.getListaBebidas());


        grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                elementoSeleccionado = null;
                if (checkedId==R.id.radioButton4){
                    lista.setAdapter(null);
                    lista.setAdapter(adaptador2);}

                if (checkedId==R.id.radioButton5){
                    lista.setAdapter(null);
                    lista.setAdapter(adaptador3);}

                if (checkedId==R.id.radioButton6){
                    lista.setAdapter(null);
                    lista.setAdapter(adaptador4);}

            }
        });


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                elementoSeleccionado = (Utils.ElementoMenu)adapterView.getItemAtPosition(i);
                
            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(elementoSeleccionado != null && !confirmado){
                    listapedidos.add(elementoSeleccionado);
                    pedidoActual.setText(pedidoActual.getText() + elementoSeleccionado.toString() + "\n");

                }
                else{
                    Toast.makeText(MainActivity.this,"Debe seleccionar algo del menu",Toast.LENGTH_LONG).show();
                }
            }
        });

        reiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listapedidos = new ArrayList<Utils.ElementoMenu>();
                pedidoActual.setText("");

            }
        });



        precio=0.0;
        confirmarpedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                elementoSeleccionado = null;
                for(Utils.ElementoMenu elemento : listapedidos){
                    precio = precio +elemento.getPrecio();
                }
                pedidoActual.setText(pedidoActual.getText() + "TOTAL: $" + precio.toString().substring(0,5));
                confirmado = true;
            }
        });
    }
}
