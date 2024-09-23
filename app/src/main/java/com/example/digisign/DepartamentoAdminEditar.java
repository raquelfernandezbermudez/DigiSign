package com.example.digisign;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.digisign.clases.Departamento;
import com.example.digisign.dao.DepartamentoDao;

import java.util.List;

public class DepartamentoAdminEditar extends AppCompatActivity {

    private Spinner spinnerDepartamentos;
    private EditText nombreEditText;
    private Button editarDepartamentoButton;

    private AppDatabase appDatabase;
    private DepartamentoDao departamentoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.departamentoadmineditar);

        spinnerDepartamentos = findViewById(R.id.spinnerDepartamentos);
        nombreEditText = findViewById(R.id.nombreEditText);
        editarDepartamentoButton = findViewById(R.id.editarDepartamentoButton);

        appDatabase = AppDatabase.getInstance(this);
        departamentoDao = appDatabase.departamentoDao();

        // Cargar la lista de departamentos en el spinner
        cargarDatosSpinner();

        // Configurar el botón de editar
        editarDepartamentoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editarDepartamento();
            }
        });

        // Listener para manejar la selección del departamento en el spinner
        spinnerDepartamentos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                // Obtener el departamento seleccionado del spinner y cargar sus datos en los campos
                Departamento departamentoSeleccionado = (Departamento) adapterView.getItemAtPosition(position);
                cargarDatosDepartamento(departamentoSeleccionado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Manejar la situación donde no se ha seleccionado nada en el spinner
            }
        });
    }

    private void cargarDatosSpinner() {
        // Obtener la lista de departamentos y configurar el adapter para el spinner
        List<Departamento> departamentos = departamentoDao.getAllDepartamentos();
        ArrayAdapter<Departamento> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departamentos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartamentos.setAdapter(adapter);
    }

    private void cargarDatosDepartamento(Departamento departamento) {
        // Cargar los datos del departamento en los campos correspondientes
        if (departamento != null) {
            nombreEditText.setText(departamento.getNombre());
        }
    }

    private void editarDepartamento() {
        // Obtener el departamento seleccionado del spinner
        Departamento departamentoSeleccionado = (Departamento) spinnerDepartamentos.getSelectedItem();

        // Verificar que se ha seleccionado un departamento
        if (departamentoSeleccionado == null) {
            Toast.makeText(this, "Por favor, selecciona un departamento", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtener el nuevo valor del nombre del departamento
        String nuevoNombre = nombreEditText.getText().toString();

        // Actualizar el valor del nombre del departamento seleccionado
        departamentoSeleccionado.setNombre(nuevoNombre);

        // Actualizar el departamento en la base de datos
        departamentoDao.updateDepartamento(departamentoSeleccionado);

        // Mostrar mensaje de éxito
        Toast.makeText(this, "Departamento editado con éxito", Toast.LENGTH_SHORT).show();
    }
}
