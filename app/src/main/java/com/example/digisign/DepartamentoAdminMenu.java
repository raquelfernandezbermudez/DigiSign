package com.example.digisign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DepartamentoAdminMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.departamentoadminmenu);

        // Botón VER
        Button btnVerDepartamentos = findViewById(R.id.btnVerDepartamentos);
        btnVerDepartamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abrir la actividad de VER Departamentos
                Intent intentVerDepartamentos = new Intent(DepartamentoAdminMenu.this, DepartamentoAdminVer.class);
                startActivity(intentVerDepartamentos);
            }
        });

        // Botón CREAR
        Button btnCrearDepartamentos = findViewById(R.id.btnCrearDepartamentos);
        btnCrearDepartamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abrir la actividad de CREAR Departamentoss
                Intent intentCrearDepartamentos = new Intent(DepartamentoAdminMenu.this, DepartamentoAdminCrear.class);
                startActivity(intentCrearDepartamentos);
            }
        });

        // Botón EDITAR
        Button btnModificarDepartamentos = findViewById(R.id.btnModificarDepartamentos);
        btnModificarDepartamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentModificarDepartamentos = new Intent(DepartamentoAdminMenu.this, DepartamentoAdminEditar.class);
                startActivity(intentModificarDepartamentos);
            }
        });

        // Botón BORRAR
        Button btnEliminarDepartamentos = findViewById(R.id.btnEliminarDepartamentos);
        btnEliminarDepartamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentEliminarDepartamentos = new Intent(DepartamentoAdminMenu.this, DepartamentoAdminBorrar.class);
                startActivity(intentEliminarDepartamentos);
            }
        });


    }
}