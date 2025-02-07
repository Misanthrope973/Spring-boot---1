package com.Tienda.TiendaRPG.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Representa el modelo de un proveedor en la tienda RPG.
 * Utiliza JPA para la persistencia en la base de datos.
 */
@Entity
@Table(name = "proveedores")
public class ProveedorModel {
    /**
     * Identificador único del proveedor. Se genera automáticamente.
     */
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String contrasena;
    private String rol; 
    
    /**
     * Constructor con parámetros.
     *
     * @param nombre      El nombre del proveedor.
     * @param contraseña  La contraseña del proveedor.
     * @param rol         El rol del proveedor.
     */
    public ProveedorModel(String nombre, String contrasena, String rol){
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.rol = rol;
    }
    
    /**
    * ---Constructor requerido por JPA----
    * JPA (Java Persistence API) requiere un constructor vacío o sin argumentos para garantizar que puede 
    * instanciar objetos de las entidades cuando necesita hacerlo. Esto es necesario porque JPA utiliza 
    * reflexión para crear objetos de las entidades, y esta técnica no permite invocar 
    * constructores que requieran parámetros sin especificar manualmente esos valores.
    */
    public ProveedorModel() {}
    
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

    public void setRol(String Rol) {
        this.rol = Rol;
    }
}
