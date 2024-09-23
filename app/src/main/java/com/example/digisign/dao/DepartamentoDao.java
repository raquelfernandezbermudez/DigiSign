package com.example.digisign.dao;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.digisign.clases.Departamento;

import java.util.List;

@Dao
public interface DepartamentoDao {
    @Insert
    long insertDepartamento(Departamento departamento);

    @Update
    void updateDepartamento(Departamento departamento);

    @Delete
    void deleteDepartamento(Departamento departamento);

    @Query("SELECT * FROM departamentos")
    List<Departamento> getAllDepartamentos();

    @Query("SELECT * FROM departamentos WHERE id = :id")
    Departamento getDepartamentoById(long id);

    @Query("SELECT * FROM departamentos WHERE nombre = :nombre")
    Departamento getDepartamentoByNombre(String nombre);
}
