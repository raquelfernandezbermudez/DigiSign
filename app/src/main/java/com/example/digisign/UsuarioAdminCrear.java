package com.example.digisign;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.digisign.clases.Departamento;
import com.example.digisign.clases.Rol;
import com.example.digisign.clases.Usuario;
import com.example.digisign.dao.DepartamentoDao;
import com.example.digisign.dao.RolDao;
import com.example.digisign.dao.UsuarioDao;

import java.util.List;

public class UsuarioAdminCrear extends AppCompatActivity {

    private EditText nombreEditText, apellidosEditText, correoEditText, contrasenaEditText;
    private Spinner rolSpinner, departamentoSpinner;
    private Button crearUsuarioButton;

    private AppDatabase appDatabase;
    private UsuarioDao usuarioDao;
    private RolDao rolDao;
    private DepartamentoDao departamentoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuarioadmincrear);

        nombreEditText = findViewById(R.id.nombreEditText);
        apellidosEditText = findViewById(R.id.apellidosEditText);
        correoEditText = findViewById(R.id.correoEditText);
        contrasenaEditText = findViewById(R.id.contrasenaEditText);
        rolSpinner = findViewById(R.id.rolSpinner);
        departamentoSpinner = findViewById(R.id.departamentoSpinner);
        crearUsuarioButton = findViewById(R.id.crearUsuarioButton);

        appDatabase = AppDatabase.getInstance(this);
        usuarioDao = appDatabase.usuarioDao();
        rolDao = appDatabase.rolDao();
        departamentoDao = appDatabase.departamentoDao();

        cargarDatosSpinner();


        crearUsuarioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearUsuario();
            }
        });
    }

    private void cargarDatosSpinner() {
        // Cargar roles desde la base de datos
        List<Rol> roles = rolDao.getAllRoles();
        ArrayAdapter<Rol> rolAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
        rolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rolSpinner.setAdapter(rolAdapter);

        // Cargar departamentos desde la base de datos
        List<Departamento> departamentos = departamentoDao.getAllDepartamentos();
        ArrayAdapter<Departamento> departamentoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departamentos);
        departamentoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        departamentoSpinner.setAdapter(departamentoAdapter);
    }

    private void crearUsuario() {
        // Comprobar que todos los campos tengan algún valor
        if (camposVacios()) {
            Toast.makeText(this, "Todos los campos deben ser completados.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Comprobar que el correo contenga '@'
        String correo = correoEditText.getText().toString();
        if (!correo.contains("@")) {
            Toast.makeText(this, "La dirección de correo electrónico debe contener '@'.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Comprobar que el correo no existe
        if (usuarioDao.getUsuarioByCorreo(correo) != null) {
            Toast.makeText(this, "Ya existe un usuario para este correo.", Toast.LENGTH_SHORT).show();
            return;
        }

        String nombre = nombreEditText.getText().toString();
        String apellidos = apellidosEditText.getText().toString();
        String contrasena = contrasenaEditText.getText().toString();

        // Obtener el rol
        Rol rolSeleccionado = (Rol) rolSpinner.getSelectedItem();
        long rolId = rolSeleccionado.getId();

        // Obtener el departamento
        Departamento departamentoSeleccionado = (Departamento) departamentoSpinner.getSelectedItem();
        long departamentoId = departamentoSeleccionado.getId();

        // Crea un nuevo usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setApellidos(apellidos);
        nuevoUsuario.setCorreo(correo);
        nuevoUsuario.setContrasena(contrasena);
        nuevoUsuario.setRolId(rolId);
        nuevoUsuario.setDepartamentoId(departamentoId);

        long nuevoUsuarioId = usuarioDao.insertUsuario(nuevoUsuario);

        Toast.makeText(this, "Nuevo usuario creado", Toast.LENGTH_SHORT).show();

        limpiarCampos();
    }

    private boolean camposVacios() {
        return nombreEditText.getText().toString().isEmpty() ||
                apellidosEditText.getText().toString().isEmpty() ||
                correoEditText.getText().toString().isEmpty() ||
                contrasenaEditText.getText().toString().isEmpty();
    }

    private void limpiarCampos() {
        nombreEditText.setText("");
        apellidosEditText.setText("");
        correoEditText.setText("");
        contrasenaEditText.setText("");
        rolSpinner.setSelection(0);
        departamentoSpinner.setSelection(0);
    }
}
