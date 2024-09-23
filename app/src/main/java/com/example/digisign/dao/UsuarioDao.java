package com.example.digisign.dao;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.digisign.clases.Usuario;

import java.util.List;

@Dao
public interface UsuarioDao {
    @Insert
    long insertUsuario(Usuario usuario);

    @Update
    void updateUsuario(Usuario usuario);

    @Delete
    void deleteUsuario(Usuario usuario);

    @Query("SELECT * FROM usuarios")
    List<Usuario> getAllUsuarios();

    @Query("SELECT * FROM usuarios WHERE id = :id")
    Usuario getUsuarioById(long id);

    @Query("SELECT * FROM usuarios WHERE correo = :correo")
    Usuario getUsuarioByCorreo(String correo);

    @Query("SELECT * FROM usuarios WHERE correo = :correo AND contrasena = :contrasena")
    Usuario login(String correo, String contrasena);

    @Query("SELECT r.nombre FROM roles r inner join usuarios u ON (r.id = u.rolId) where u.id = :id" )
    String getRolNombre(long id);

    @Query("SELECT d.nombre FROM departamentos d inner join usuarios u ON (d.id = u.departamentoId) where u.id = :id" )
    String getDepartamentoNombre(long id);

    @Query("SELECT * FROM usuarios where departamentoId = :departamentoId")
    List<Usuario> getUsuariosByDepartamento(long departamentoId);
}
