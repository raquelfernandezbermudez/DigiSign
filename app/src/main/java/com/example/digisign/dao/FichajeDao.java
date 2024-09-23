package com.example.digisign.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.digisign.clases.Fichaje;

import java.util.List;

@Dao
public interface FichajeDao {
    @Insert
    long insertFichaje(Fichaje fichaje);

    @Update
    void updateFichaje(Fichaje fichaje);

    @Delete
    void deleteFichaje(Fichaje fichaje);

    @Query("SELECT * FROM fichajes")
    List<Fichaje> getAllFichajes();

    @Query("SELECT * FROM fichajes WHERE id = :id")
    Fichaje getFichajeById(int id);

    @Query("SELECT * FROM fichajes WHERE usuarioId = :usuarioId " +
            " ORDER BY fechaEntrada DESC LIMIT 1")
    Fichaje getUltimoFichajePorUsuario(long usuarioId);

    @Query("SELECT * FROM fichajes where usuarioId = :usuarioId")
    List<Fichaje> getMisFichajes(long usuarioId);
}
