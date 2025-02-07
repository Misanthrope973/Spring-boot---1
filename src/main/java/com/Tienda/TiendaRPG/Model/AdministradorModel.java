package com.Tienda.TiendaRPG.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="administradores")
public class AdministradorModel {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String contrasena;
    private String rol; 
    
    /**
     * @param id
     * @param id_pedidos
     * @param nombre
     * @param contrasena
     * @param rol 
     */
    public AdministradorModel(String nombre, String contrasena, String rol){
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.rol = rol;
    }
    
    //Parámetro vació requerido por JPA
    public AdministradorModel(){}
    
    //GETTERS AND SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
}
