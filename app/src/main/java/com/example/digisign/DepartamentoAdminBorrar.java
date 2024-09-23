package com.example.digisign;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.digisign.clases.Departamento;
import com.example.digisign.dao.DepartamentoDao;

import java.util.List;

public class DepartamentoAdminBorrar extends AppCompatActivity {

    private Spinner spinnerDepartamentos;
    private Button borrarDepartamentoButton;

    private AppDatabase appDatabase;
    private DepartamentoDao departamentoDao;
    private ArrayAdapter<Departamento> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.departamentoadminborrar);

        spinnerDepartamentos = findViewById(R.id.spinnerDepartamentos);
        borrarDepartamentoButton = findViewById(R.id.borrarDepartamentoButton);

        appDatabase = AppDatabase.getInstance(this);
        departamentoDao = appDatabase.departamentoDao();

        // Obtener la lista de departamentos
        List<Departamento> departamentos = departamentoDao.getAllDepartamentos();

        // Configurar el adaptador para el spinner
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departamentos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartamentos.setAdapter(adapter);

        // Establecer un listener para el botón de borrar
        borrarDepartamentoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarDepartamento();
            }
        });
    }

    private void borrarDepartamento() {
        Departamento departamentoSeleccionado = (Departamento) spinnerDepartamentos.getSelectedItem();
        if (departamentoSeleccionado != null) {
            // Borrar el departamento de la base de datos
            departamentoDao.deleteDepartamento(departamentoSeleccionado);

            // Actualizar la lista
            adapter.remove(departamentoSeleccionado);
            adapter.notifyDataSetChanged();

            Toast.makeText(DepartamentoAdminBorrar.this, "Departamento eliminado con éxito", Toast.LENGTH_SHORT).show();
        }
    }
}
