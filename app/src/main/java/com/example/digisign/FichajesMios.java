package com.example.digisign;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.digisign.clases.Fichaje;
import com.example.digisign.dao.FichajeDao;

import java.util.List;

public class FichajesMios extends AppCompatActivity {

    private AppDatabase appDatabase;
    private FichajeDao fichajeDao;

    private ListView listViewFichajes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fichajesmios);

        appDatabase = AppDatabase.getInstance(this);
        fichajeDao = appDatabase.fichajeDao();

        // Coger mi usuario
        PrefManager prefManager = new PrefManager(this);
        long usuarioId = prefManager.getUserId();
        // Obtener la lista de fichajes
        List<Fichaje> fichajes = appDatabase.fichajeDao().getMisFichajes(usuarioId);

        // Configurar el adaptador para la lista de fichajes
        ArrayAdapter<Fichaje> adapter = new ArrayAdapter<Fichaje>(this, R.layout.item_fichaje, fichajes) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_fichaje, parent, false);
                }

                Fichaje fichaje = getItem(position);

                // Configurar las vistas del diseño personalizado con la información del fichaje
                //TextView textViewId = convertView.findViewById(R.id.textViewId);
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
}
