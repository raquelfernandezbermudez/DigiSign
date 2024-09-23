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

import com.example.digisign.clases.Departamento;
import com.example.digisign.dao.DepartamentoDao;

import java.util.List;

public class DepartamentoAdminVer extends AppCompatActivity {

    private AppDatabase appDatabase;

    private DepartamentoDao departamentoDao;

    private ListView listViewDepartamentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.departamentoadminver);

        appDatabase = AppDatabase.getInstance(this);
        departamentoDao = appDatabase.departamentoDao();

        // Obtener la lista de departamentos
        List<Departamento> departamentos = appDatabase.departamentoDao().getAllDepartamentos();

        // Configurar el adaptador para la lista de departamentos
        ArrayAdapter<Departamento> adapter = new ArrayAdapter<Departamento>(this, R.layout.item_departamento, departamentos) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_departamento, parent, false);
                }

                Departamento departamento = getItem(position);

                // Configurar las vistas del diseño personalizado con la información del departamento
                TextView textViewId = convertView.findViewById(R.id.textViewIdDepartamento);
                TextView textViewNombre = convertView.findViewById(R.id.textViewNombreDepartamento);

                if (departamento != null) {
                    textViewId.setText(String.valueOf(departamento.getId()));
                    textViewNombre.setText(departamento.getNombre());
                }

                return convertView;
            }
        };

        // Enlazar el adaptador a la ListView
        listViewDepartamentos = findViewById(R.id.listViewDepartamentos);
        listViewDepartamentos.setAdapter(adapter);
    }
}
