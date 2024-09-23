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

public class InicioEmpleado extends AppCompatActivity {

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
        setContentView(R.layout.inicioempleado);

        fechaInicioTextView = findViewById(R.id.fechaInicioTextView);

        appDatabase = AppDatabase.getInstance(this);
        fichajeDao = appDatabase.fichajeDao();

        // Obtener el ID del usuario desde SharedPreferences
        PrefManager prefManager = new PrefManager(this);
        usuarioId = prefManager.getUserId();

        // Configurar el botón para manejar las jornadas
        jornadaButton = findViewById(R.id.jornadaButton);
        actualizarBotonJornada(jornadaButton); // Actualizar la apariencia inicial

        jornadaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manejarJornada();
            }
        });

        // Botón Mis fichajes
        Button btnVerFichajes = findViewById(R.id.btnVerFichajes);
        btnVerFichajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abrir la actividad de VER FichajesMios
                Intent intentVerFichajesMios = new Intent(InicioEmpleado.this, FichajesMios.class);
                startActivity(intentVerFichajesMios);
            }
        });

        // Botón Salir
        Button btnSalir = findViewById(R.id.btnSalir);
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implementar el logout y navegar a la pantalla de inicio
                Intent intent = new Intent(InicioEmpleado.this, MainActivity.class);
                startActivity(intent);
                finish(); // Cerrar la actividad actual para evitar volver atrás con el botón de retroceso
            }
        });
    }

    private void manejarJornada() {
        // Obtener la fecha y hora actual
        Timestamp timestamp = new Timestamp(new Date().getTime());

        // Verificar si ya existe un registro para la fecha actual y el usuario actual
        Fichaje ultimoFichaje = fichajeDao.getUltimoFichajePorUsuario(usuarioId);

        if (ultimoFichaje != null && ultimoFichaje.getFechaSalida() == null) {
            // Ya existe un registro abierto, registraremos la salida
            ultimoFichaje.setFechaSalida(timestamp);
            fichajeDao.updateFichaje(ultimoFichaje);
            Toast.makeText(this, "Salida registrada", Toast.LENGTH_SHORT).show();
        } else {
            // No existe un registro, registraremos la entrada
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
            fechaInicioTextView.setVisibility(View.VISIBLE);
            if (ultimoFichaje != null) {
                fechaInicioTextView.setText("Fecha inicio jornada: " + (ultimoFichaje.getFechaEntrada()));
            }
        } else {
            // Jornada cerrada
            jornadaButton.setBackgroundColor(ContextCompat.getColor(this, R.color.verde));
            jornadaButton.setText("REGISTRAR JORNADA");
            fechaInicioTextView.setVisibility(View.GONE); // Ocultar el TextView de fecha de inicio

        }
    }
    }