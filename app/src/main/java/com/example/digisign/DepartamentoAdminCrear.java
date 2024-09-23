package com.example.digisign;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.digisign.clases.Departamento;
import com.example.digisign.dao.DepartamentoDao;

public class DepartamentoAdminCrear extends AppCompatActivity {

    private EditText nombreEditText;
    private Button crearDepartamentoButton;

    private AppDatabase appDatabase;
    private DepartamentoDao departamentoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.departamentoadmincrear);

        nombreEditText = findViewById(R.id.nombreEditText);
        crearDepartamentoButton = findViewById(R.id.crearDepartamentoButton);


        appDatabase = AppDatabase.getInstance(this);
        departamentoDao = appDatabase.departamentoDao();

        crearDepartamentoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearDepartamento();
            }
        });
    }

    private void crearDepartamento() {
        // Verificar que el campo tenga algún valor
        if (campoVacio()) {
            Toast.makeText(this, "El campo debe ser completado.", Toast.LENGTH_SHORT).show();
            return;
        }

        String nombre = nombreEditText.getText().toString();

        // Crear un nuevo departamento
        Departamento nuevoDepartamento = new Departamento();
        nuevoDepartamento.setNombre(nombre);

        // Insertar el nuevo departamento en la base de datos
        long nuevoDepartamentoId = departamentoDao.insertDepartamento(nuevoDepartamento);

        Toast.makeText(this, "Nuevo departamento creado", Toast.LENGTH_SHORT).show();


        // Limpiar el campo después de crear el departamento
        limpiarCampo();
    }

    private boolean campoVacio() {
        return nombreEditText.getText().toString().isEmpty();
    }

    private void limpiarCampo() {
        nombreEditText.setText("");
    }
}
