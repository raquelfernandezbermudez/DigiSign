package com.example.digisign;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class UsuarioAdminMenu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuarioadminmenu);

        // Botón VER
        Button btnVerUsuarios = findViewById(R.id.btnVerUsuarios);
        btnVerUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentVerUsuarios = new Intent(UsuarioAdminMenu.this, UsuarioAdminVer.class);
                startActivity(intentVerUsuarios);
            }
        });

        // Botón CREAR
        Button btnCrearUsuarios = findViewById(R.id.btnCrearUsuarios);
        btnCrearUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCrearUsuarios = new Intent(UsuarioAdminMenu.this, UsuarioAdminCrear.class);
                startActivity(intentCrearUsuarios);
            }
        });

        // Botón EDITAR
        Button btnModificarUsuarios = findViewById(R.id.btnModificarUsuarios);
        btnModificarUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentModificarUsuarios = new Intent(UsuarioAdminMenu.this, UsuarioAdminEditar.class);
                startActivity(intentModificarUsuarios);
            }
        });

        // Botón BORRAR
        Button btnEliminarUsuarios = findViewById(R.id.btnEliminarUsuarios);
        btnEliminarUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentEliminarUsuarios = new Intent(UsuarioAdminMenu.this, UsuarioAdminBorrar.class);
                startActivity(intentEliminarUsuarios);
            }
        });
    }
}