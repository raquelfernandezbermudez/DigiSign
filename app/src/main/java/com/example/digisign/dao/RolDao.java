package com.example.digisign.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.digisign.clases.Rol;

import java.util.List;

@Dao
public interface RolDao {
    @Insert
    long insertRol(Rol rol);

    @Update
    void updateRol(Rol rol);

    @Delete
    void deleteRol(Rol rol);

    @Query("SELECT * FROM roles")
    List<Rol> getAllRoles();

    @Query("SELECT * FROM roles WHERE id = :id")
    Rol getRolById(long id);

    @Query("SELECT * FROM roles WHERE nombre = :nombre")
    Rol getRolByNombre(String nombre);
}

