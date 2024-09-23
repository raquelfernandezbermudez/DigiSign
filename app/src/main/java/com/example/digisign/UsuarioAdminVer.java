package com.example.digisign;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.digisign.clases.Usuario;
import com.example.digisign.dao.UsuarioDao;

import java.util.List;

public class UsuarioAdminVer extends AppCompatActivity {

    private AppDatabase appDatabase;

    private UsuarioDao usuarioDao;

    private ListView listViewUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuarioadminver);
        appDatabase = AppDatabase.getInstance(this);
        usuarioDao = appDatabase.usuarioDao();

        // Obtener la lista de usuarios
        List<Usuario> usuarios = appDatabase.usuarioDao().getAllUsuarios();

        // Configurar el adaptador
        ArrayAdapter<Usuario> adapter = new ArrayAdapter<Usuario>(this, R.layout.item_usuario, usuarios) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_usuario, parent, false);
                }

                Usuario usuario = getItem(position);

                //TextView textViewId = convertView.findViewById(R.id.textViewId);
                TextView textViewNombre = convertView.findViewById(R.id.textViewNombre);
                TextView textViewApellidos = convertView.findViewById(R.id.textViewApellidos);
                TextView textViewCorreo = convertView.findViewById(R.id.textViewCorreo);
                TextView textViewRol = convertView.findViewById(R.id.textViewRol);
                TextView textViewDepartamento = convertView.findViewById(R.id.textViewDepartamento);

                if (usuario != null) {
                   // textViewId.setText(String.valueOf(usuario.getId()));
                    textViewNombre.setText(usuario.getNombre());
                    textViewApellidos.setText(usuario.getApellidos());
                    textViewCorreo.setText(usuario.getCorreo());
                    textViewRol.setText(usuarioDao.getRolNombre(usuario.getId()));
                    textViewDepartamento.setText(usuarioDao.getDepartamentoNombre(usuario.getId()));
                }

                return convertView;
            }
        };

        // Enlazar el adaptador a la ListView
        listViewUsuarios = findViewById(R.id.listViewUsuarios);

        // Cabecera
        View headerView = LayoutInflater.from(this).inflate(R.layout.table_header, listViewUsuarios, false);

        // Agregar cabecera
        listViewUsuarios.addHeaderView(headerView);
        listViewUsuarios.setDividerHeight(5);
        listViewUsuarios.setAdapter(adapter);
    }

}
