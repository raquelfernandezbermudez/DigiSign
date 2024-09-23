package com.example.digisign;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.digisign.clases.Fichaje;
import com.example.digisign.clases.Usuario;
import com.example.digisign.dao.DepartamentoDao;
import com.example.digisign.dao.UsuarioDao;

import java.util.List;

public class FichajesDepartamento extends AppCompatActivity {

    private Spinner spinnerUsuarios;

    private ListView listViewFichajes;

    private AppDatabase appDatabase;
    private UsuarioDao usuarioDao;
    private DepartamentoDao departamentoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fichajesdepartamento);
        // inicializar objetos
        spinnerUsuarios = findViewById(R.id.spinnerUsuarios);

        // inicializar daos
        appDatabase = AppDatabase.getInstance(this);
        usuarioDao = appDatabase.usuarioDao();
        departamentoDao = appDatabase.departamentoDao();

        // Cargar la lista de usuarios en el spinner
        cargarDatosSpinner();

        spinnerUsuarios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                // Obtener el usuario seleccionado del spinner y cargar sus datos en los campos
                Usuario usuarioSeleccionado = (Usuario) adapterView.getItemAtPosition(position);
                cargarFichajes(usuarioSeleccionado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Manejar la situación donde no se ha seleccionado nada en el spinner
            }
        });
    }

    private void cargarFichajes(Usuario usuario) {

        // Obtener la lista de fichajes
        List<Fichaje> fichajes = appDatabase.fichajeDao().getMisFichajes(usuario.getId());

        // Configurar el adaptador para la lista de fichajes
        ArrayAdapter<Fichaje> adapter = new ArrayAdapter<Fichaje>(this, R.layout.item_fichaje, fichajes) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_fichaje, parent, false);
                }

                Fichaje fichaje = getItem(position);

               // TextView textViewId = convertView.findViewById(R.id.textViewId);
                TextView textViewFechaEntrada = convertView.findViewById(R.id.textViewFechaEntrada);
                TextView textViewFechaSalida = convertView.findViewById(R.id.textViewFechaSalida);

                if (fichaje != null) {
                   // textViewId.setText(String.valueOf(fichaje.getId()));
                    textViewFechaEntrada.setText("Entrada: " + String.valueOf(fichaje.getFechaEntrada()));
                    textViewFechaSalida.setText("Salida: " + String.valueOf(fichaje.getFechaSalida()));
                }

                return convertView;
            }
        };

        // Enlazar el adaptador a la ListView
        listViewFichajes = findViewById(R.id.listViewFichajes);
        listViewFichajes.setAdapter(adapter);
    }

    private void cargarDatosSpinner() {
        // Seleccionar mi departamento
        PrefManager prefManager = new PrefManager(this);
        long usuarioId = prefManager.getUserId();
        long departamentoId = usuarioDao.getUsuarioById(usuarioId).getDepartamentoId();
        // Coger usuarios departamento
        List<Usuario> usuarios = usuarioDao.getUsuariosByDepartamento(departamentoId);
        // Precargar spinner con esos usuarios
        // Obtener la lista de usuarios y configurar el adapter para el spinner
        ArrayAdapter<Usuario> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, usuarios);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUsuarios.setAdapter(adapter);
    }

}