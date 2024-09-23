package com.example.digisign;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.digisign.clases.Departamento;
import com.example.digisign.clases.Fichaje;
import com.example.digisign.clases.Rol;
import com.example.digisign.clases.Usuario;
import com.example.digisign.dao.DepartamentoDao;
import com.example.digisign.dao.FichajeDao;
import com.example.digisign.dao.RolDao;
import com.example.digisign.dao.UsuarioDao;

@Database(entities = {Usuario.class, Rol.class, Departamento.class, Fichaje.class}, version = 3,  exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UsuarioDao usuarioDao();
    public abstract RolDao rolDao();
    public abstract DepartamentoDao departamentoDao();
    public abstract FichajeDao fichajeDao();

    // Instancia estatica de la AppDatabase
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "digisign").
                allowMainThreadQueries(). // Esto solo para pruebas
                fallbackToDestructiveMigration(). // Esto recrea la bbdd cada vez
                build();
        }
        return instance;
    }

    private static Long crearRol(RolDao rolDao, String nombre) {
        Rol rol = rolDao.getRolByNombre(nombre);
        if (rol == null) {
            Rol adminRol = new Rol();
            adminRol.setNombre(nombre);
            return rolDao.insertRol(adminRol);
        } else {
            return rol.getId();
        }
    }

    private static Long crearDepartamento(DepartamentoDao departamentoDao, String nombre) {
        Departamento departamento = departamentoDao.getDepartamentoByNombre(nombre);
        if (departamento == null) {
            Departamento adminDepartamento = new Departamento();
            adminDepartamento.setNombre(nombre);
            return departamentoDao.insertDepartamento(adminDepartamento);
        } else {
            return departamento.getId();
        }
    }

    public static void initializeDatabase(Context applicationContext) {
        new Thread(() -> {
            UsuarioDao usuarioDao = getInstance(applicationContext).usuarioDao();
            DepartamentoDao departamentoDao = getInstance(applicationContext).departamentoDao();
            RolDao rolDao = getInstance(applicationContext).rolDao();

            long departamentoId = crearDepartamento(departamentoDao, Constants.DEPARTAMENTO_ADMIN);
            long rolId = crearRol(rolDao, Constants.ROL_ADMIN);

            if (usuarioDao.getUsuarioByCorreo(Constants.CORREO_ADMIN) == null) {
                Usuario adminUser = new Usuario();
                adminUser.setNombre("admin");
                adminUser.setApellidos("admin");
                adminUser.setContrasena("admin");
                adminUser.setCorreo(Constants.CORREO_ADMIN);
                adminUser.setDepartamentoId(departamentoId);
                adminUser.setRolId(rolId);

                usuarioDao.insertUsuario(adminUser);
            }

            crearRol(rolDao, Constants.ROL_JEFE);
            crearRol(rolDao, Constants.ROL_EMPLEADO);
        }).start();
    }

}
