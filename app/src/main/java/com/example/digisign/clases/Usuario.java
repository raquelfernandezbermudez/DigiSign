package com.example.digisign.clases;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.Index;

import com.example.digisign.clases.Departamento;
import com.example.digisign.clases.Rol;

@Entity(tableName = "usuarios",
        foreignKeys = {
                @ForeignKey(entity = Rol.class,
                        parentColumns = "id",
                        childColumns = "rolId"),
                @ForeignKey(entity = Departamento.class,
                        parentColumns = "id",
                        childColumns = "departamentoId")
        },
        indices = {@Index("rolId"), @Index("departamentoId")})
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String nombre;
    private String apellidos;
    private String correo;
    private String contrasena;
    private long rolId; // Clave foránea
    private long departamentoId; // Clave foránea

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public long getRolId() {
        return rolId;
    }

    public void setRolId(long rolId) {
        this.rolId = rolId;
    }

    public long getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(long departamentoId) {
        this.departamentoId = departamentoId;
    }

    @Override
    public String toString() {
        return id + ":" + nombre + ' ' + apellidos + '(' +
                 correo + ')';
    }
}
