package com.example.digisign;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
public class InicioAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicioadmin);

        // Botón Usuarios
        Button btnUsuarios = findViewById(R.id.btnUsuarios);
        btnUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentUsuarios = new Intent(InicioAdmin.this, UsuarioAdminMenu.class);
                startActivity(intentUsuarios);
            }
        });

      // Botón Departamentos
      Button btnDepartamentos = findViewById(R.id.btnDepartamentos);
        btnDepartamentos.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View view) {
                Intent intentDepartamentos = new Intent(InicioAdmin.this, DepartamentoAdminMenu.class);
               startActivity(intentDepartamentos);
            }
        });

        Button btnSalir = findViewById(R.id.btnSalir);
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implementar el logout y navegar a la pantalla de inicio
                Intent intent = new Intent(InicioAdmin.this, MainActivity.class);
                startActivity(intent);
                finish(); // Cerrar la actividad actual para evitar volver atrás con el botón de retroceso
            }
        });

    }

}