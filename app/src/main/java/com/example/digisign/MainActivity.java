package com.example.digisign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.digisign.clases.Usuario;
import com.example.digisign.dao.RolDao;
import com.example.digisign.dao.UsuarioDao;

public class MainActivity extends AppCompatActivity {

    private EditText userEditText;
    private EditText passwordEditText;
    private Button loginButton;

    private AppDatabase appDatabase;
    private UsuarioDao usuarioDao;
    private RolDao rolDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userEditText = findViewById(R.id.userEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        appDatabase = AppDatabase.getInstance(this);
        usuarioDao = appDatabase.usuarioDao();
        rolDao = appDatabase.rolDao();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String username = userEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Verificar si los campos están vacíos
        if (username.isEmpty() || password.isEmpty()) {
            // Mostrar mensaje de advertencia
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Realizar el inicio de sesión
        Usuario usuario = usuarioDao.login(username, password);

        if (usuario != null) {
            PrefManager prefManager = new PrefManager(this);
            prefManager.saveUserId(usuario.getId());
            Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
            // Verificar el rol y redirigir a la actividad correspondiente
            long rolId = usuario.getRolId();
            String rol = rolDao.getRolById(rolId).getNombre();
            Intent intent;
            switch (rol) {
                case Constants.ROL_JEFE:
                    intent = new Intent(MainActivity.this, InicioJefe.class);
                    break;
                case Constants.ROL_EMPLEADO:
                    intent = new Intent(MainActivity.this, InicioEmpleado.class);
                    break;
                case Constants.ROL_ADMIN:
                    intent = new Intent(MainActivity.this, InicioAdmin.class);
                    break;
                default:
                    // Rol no reconocido, manejar según sea necesario
                    return;
            }

            startActivity(intent);
            finish();

        } else {
            Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
        }
    }
}
