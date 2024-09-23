package com.example.digisign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digisign.clases.Fichaje;
import com.example.digisign.dao.FichajeDao;

import java.sql.Timestamp;
import java.util.Date;

public class InicioJefe extends AppCompatActivity {

    // Elementos del xml
    private Button jornadaButton;

    // Base de datos
    private AppDatabase appDatabase;
    private FichajeDao fichajeDao;
    private long usuarioId;
    private TextView fechaInicioTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iniciojefe);

        fechaInicioTextView = findViewById(R.id.fechaInicioTextView);

        appDatabase = AppDatabase.getInstance(this);
        fichajeDao = appDatabase.fichajeDao();

        // Obtener el ID del usuario
        PrefManager prefManager = new PrefManager(this);
        usuarioId = prefManager.getUserId();

        // Configurar el botón
        jornadaButton = findViewById(R.id.jornadaButton);
        actualizarBotonJornada(jornadaButton);

        jornadaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manejarJornada();
            }
        });

        Button btnVerFichajes = findViewById(R.id.btnVerFichajes);
        btnVerFichajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVerFichajesMios = new Intent(InicioJefe.this, FichajesMios.class);
                startActivity(intentVerFichajesMios);
            }
        });

        Button btnVerFichajesDepartamento = findViewById(R.id.btnVerFichajesDepartamento);
        btnVerFichajesDepartamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVerFichajesDepartamento = new Intent(InicioJefe.this, FichajesDepartamento.class);
                startActivity(intentVerFichajesDepartamento);
            }
        });

        Button btnSalir = findViewById(R.id.btnSalir);
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioJefe.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void manejarJornada() {
        // Obtener la fecha y hora actual
        Timestamp timestamp = new Timestamp(new Date().getTime());

        // Verificar si ya existe un registro para la fecha actual y el usuario actual
        Fichaje ultimoFichaje = fichajeDao.getUltimoFichajePorUsuario(usuarioId);

        if (ultimoFichaje != null && ultimoFichaje.getFechaSalida() == null) {
            // Registraremos la salida
            ultimoFichaje.setFechaSalida(timestamp);
            fichajeDao.updateFichaje(ultimoFichaje);
            Toast.makeText(this, "Salida registrada", Toast.LENGTH_SHORT).show();
        } else {
            // No existe un registro, entrada
            Fichaje nuevoFichaje = new Fichaje();
            nuevoFichaje.setUsuarioId(usuarioId);
            nuevoFichaje.setFechaEntrada(timestamp);
            long id = fichajeDao.insertFichaje(nuevoFichaje);
            Toast.makeText(this, "Entrada registrada", Toast.LENGTH_SHORT).show();
        }

        actualizarBotonJornada(jornadaButton);
    }

    private void actualizarBotonJornada(Button jornadaButton) {
        // Verificar si hay una jornada abierta
        Fichaje ultimoFichaje = fichajeDao.getUltimoFichajePorUsuario(usuarioId);

        if (ultimoFichaje != null && ultimoFichaje.getFechaSalida() == null) {
            // Jornada abierta
            jornadaButton.setBackgroundColor(ContextCompat.getColor(this, R.color.rojo));
            jornadaButton.setText("FINALIZAR JORNADA");
            // Mostrar el TextView de fecha de inicio y establecer la fecha
            fechaInicioTextView.setVisibility(View.VISIBLE);
            if (ultimoFichaje != null) {
                fechaInicioTextView.setText("Fecha inicio jornada: " + (ultimoFichaje.getFechaEntrada()));
            }
        } else {
            // Jornada cerrada
            jornadaButton.setBackgroundColor(ContextCompat.getColor(this, R.color.verde));
            jornadaButton.setText("REGISTRAR JORNADA");
            fechaInicioTextView.setVisibility(View.GONE);

        }
    }
}
