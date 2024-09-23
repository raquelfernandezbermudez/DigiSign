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
import com.example.digisign.clases.Rol;
import com.example.digisign.clases.Usuario;
import com.example.digisign.dao.DepartamentoDao;
import com.example.digisign.dao.RolDao;
import com.example.digisign.dao.UsuarioDao;

import java.util.List;

public class UsuarioAdminEditar extends AppCompatActivity {

    private Spinner spinnerUsuarios, rolSpinner, departamentoSpinner ;
    private EditText nombreEditText, apellidosEditText, correoEditText, contrasenaEditText;
    private Button editarUsuarioButton;

    private AppDatabase appDatabase;
    private UsuarioDao usuarioDao;
    private RolDao rolDao;
    private DepartamentoDao departamentoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuarioadmineditar);

        spinnerUsuarios = findViewById(R.id.spinnerUsuarios);
        nombreEditText = findViewById(R.id.nombreEditText);
        apellidosEditText = findViewById(R.id.apellidosEditText);
        correoEditText = findViewById(R.id.correoEditText);
        contrasenaEditText = findViewById(R.id.contrasenaEditText);
        rolSpinner = findViewById(R.id.rolSpinner);
        departamentoSpinner = findViewById(R.id.departamentoSpinner);
        editarUsuarioButton = findViewById(R.id.editarUsuarioButton);

        appDatabase = AppDatabase.getInstance(this);
        usuarioDao = appDatabase.usuarioDao();
        rolDao = appDatabase.rolDao();
        departamentoDao = appDatabase.departamentoDao();

        // Cargar la lista de usuarios
        cargarDatosSpinner();
        //Cargar la lista de roles y departamentos
        cargarDatosSpinnerEditar();

        // Botón de editar
        editarUsuarioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editarUsuario();
            }
        });

        spinnerUsuarios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                // Obtener el usuario seleccionado del spinner y cargar sus datos en los campos
                Usuario usuarioSeleccionado = (Usuario) adapterView.getItemAtPosition(position);
                cargarDatosUsuario(usuarioSeleccionado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Cuando no se ha seleccionado nada
            }
        });
    }

    private void cargarDatosSpinner() {
        // Lista de usuarios
        List<Usuario> usuarios = usuarioDao.getAllUsuarios();
        ArrayAdapter<Usuario> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, usuarios);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUsuarios.setAdapter(adapter);
    }

    private void cargarDatosUsuario(Usuario usuario) {
        // Cargar los datos del usuario
        if (usuario != null) {
            nombreEditText.setText(usuario.getNombre());
            apellidosEditText.setText(usuario.getApellidos());
            correoEditText.setText(usuario.getCorreo());

            // Precargar rol seleccionado
            long idRolSeleccionado = usuario.getRolId();
            long idDepartamentoSeleccionado = usuario.getDepartamentoId();

            // Iterar sobre los elementos del spinner para buscar el rol con el ID deseado
            for (int i = 0; i < rolSpinner.getCount(); i++) {
                Rol rol = (Rol) rolSpinner.getItemAtPosition(i);
                if (rol.getId() == idRolSeleccionado) {
                    // Seleccionar el rol
                    rolSpinner.setSelection(i);
                    break;
                }
            }
            // Departamento
            for (int i = 0; i < departamentoSpinner.getCount(); i++) {
                Departamento departamento = (Departamento) departamentoSpinner.getItemAtPosition(i);
                if (departamento.getId() == idDepartamentoSeleccionado) {
                    departamentoSpinner.setSelection(i);
                    break; //
                }
            }

        }
    }

    private void cargarDatosSpinnerEditar() {
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

    private void editarUsuario() {
        // Obtener el usuario seleccionado del spinner
        Usuario usuarioSeleccionado = (Usuario) spinnerUsuarios.getSelectedItem();

        // Comprobar que se ha seleccionado
        if (usuarioSeleccionado == null) {
            Toast.makeText(this, "Por favor, selecciona un usuario", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtener los nuevos valores
        String nuevoNombre = nombreEditText.getText().toString();
        String nuevosApellidos = apellidosEditText.getText().toString();
        String nuevoCorreo = correoEditText.getText().toString();

        // Actualizar los valores
        usuarioSeleccionado.setNombre(nuevoNombre);
        usuarioSeleccionado.setApellidos(nuevosApellidos);
        usuarioSeleccionado.setCorreo(nuevoCorreo);

        // Actualizar el usuario en la base de datos
        usuarioDao.updateUsuario(usuarioSeleccionado);

        Toast.makeText(this, "Usuario editado con éxito", Toast.LENGTH_SHORT).show();
    }
}