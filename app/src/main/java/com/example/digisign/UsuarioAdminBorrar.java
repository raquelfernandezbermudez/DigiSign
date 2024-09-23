package com.example.digisign;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.digisign.clases.Usuario;
import com.example.digisign.dao.UsuarioDao;

import java.util.List;

public class UsuarioAdminBorrar extends AppCompatActivity {

    private Spinner spinnerUsuarios;
    private Button borrarUsuarioButton;

    private AppDatabase appDatabase;
    private UsuarioDao usuarioDao;
    private ArrayAdapter<Usuario> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuarioadminborrar);

        spinnerUsuarios = findViewById(R.id.spinnerUsuarios);
        borrarUsuarioButton = findViewById(R.id.borrarUsuarioButton);

        appDatabase = AppDatabase.getInstance(this);
        usuarioDao = appDatabase.usuarioDao();

        // Obtener la lista de usuarios
        List<Usuario> usuarios = usuarioDao.getAllUsuarios();

        // Configurar el adaptador
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, usuarios);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUsuarios.setAdapter(adapter);

        borrarUsuarioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarUsuario();
            }
        });
    }

    private void borrarUsuario() {
        Usuario usuarioSeleccionado = (Usuario) spinnerUsuarios.getSelectedItem();
        if (usuarioSeleccionado != null) {
            // Borrar el usuario de la base de datos
            usuarioDao.deleteUsuario(usuarioSeleccionado);

            // Actualizar la lista
            adapter.remove(usuarioSeleccionado);
            adapter.notifyDataSetChanged();

            Toast.makeText(UsuarioAdminBorrar.this, "Usuario eliminado con éxito", Toast.LENGTH_SHORT).show();
        }
    }
}
