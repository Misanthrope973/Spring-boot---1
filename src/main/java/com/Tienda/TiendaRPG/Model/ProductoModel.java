package com.Tienda.TiendaRPG.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="productos")
public class ProductoModel {
    /**
     * Generador automático del ID
     */
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private int id_proveedores;
    private String nombre;
    private String descripcion;
    private float precio;
    private int stock;
    
    /**
     * Constructor con parámetros.
     * @param id_proveedores El ID de los proveedores encargados de llevar el producto.
     * @param nombre        El nombre del proveedor.
     * @param descripcion   La descripción del producto.
     * @param precio        El precio del producto.
     * @param stock         El stock del producto.
     */
    
    public ProductoModel(Long id, int id_proveedores, String nombre, String descripcion, float precio, int stock){
        this.id = id;
        this.id_proveedores = id_proveedores;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
    }
    
    /**
     * Constructor vacío requerido por JPA
     * Este constructor vacío no realiza ninguna operación, pero es esencial 
     * para que JPA pueda instanciar la entidad y mapear sus campos desde la base de datos.
     */
    
    public ProductoModel(){}
    
    /**
     * GETTERS Y SETTER. Se utilizarán más adelante para hacer CRUD.}
     * Recibirá los datos de ProductoDTO(Datos ingresados por el proveedor) y los enviará a ProductoRepository
     * Que a su vez serán enviados a la base de datos.
     */
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getId_proveedores() {
        return id_proveedores;
    }

    public void setId_proveedores(int id_proveedores) {
        this.id_proveedores = id_proveedores;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
}
