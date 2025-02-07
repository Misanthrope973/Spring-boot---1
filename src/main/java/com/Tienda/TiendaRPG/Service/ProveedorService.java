package com.Tienda.TiendaRPG.Service;

import com.Tienda.TiendaRPG.DTO.ProveedorDTO;
import com.Tienda.TiendaRPG.Model.ProveedorModel;
import com.Tienda.TiendaRPG.Repository.ProveedorRepository;
import com.Tienda.TiendaRPG.funcionesExtras.Funciones;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProveedorService {
    
    /**
    * Este código es un ejemplo de inyección de dependencias, donde el repositorio 
    * (ProveedorRepository) es inyectado en el servicio (ProveedorService). 
    * Este patrón ayuda a desacoplar las clases, mejorar la prueba de unidades y la flexibilidad del código.
    */
    
    private ProveedorRepository proveedorRepository; //Trae el repositorio el cual se encarga de hacer el CRUD a la base de datos.
    
    @Autowired
    public ProveedorService(ProveedorRepository proveedorRepository){
        this.proveedorRepository = proveedorRepository;
    }
    
    
    public ProveedorModel registrarProveedor(ProveedorDTO proveedorDTO){ //El DTO trae los datos que ingresa el usuario y los envía al Model.
        if(proveedorRepository.findByNombre(proveedorDTO.nombre()).isPresent()){
            throw new IllegalArgumentException("El proveedor ya está registrado");
        }
        ProveedorModel provMod = new ProveedorModel();
        
        if(proveedorDTO.contrasena() == null || proveedorDTO.nombre() == null){
            throw new IllegalArgumentException("Por favor, llenar todos los campos.");
        }
        
        provMod.setNombre(proveedorDTO.nombre());
        
        //Verificar si la contraseña cumple Con los parámetros establecidos.
        Funciones contra = new Funciones();
        try{
            contra.verificarContrasena(proveedorDTO.contrasena());
            provMod.setContrasena(proveedorDTO.contrasena());
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }
        
        provMod.setRol(proveedorDTO.rol());
        return proveedorRepository.save(provMod);
    }
    /*
    * La función editarProveedor tiene el objetivo de funcionar para hacer solicitudes put y path, -
    * sin tener que agregar más lineas de código.
    * Pero con la función de abajo podrá trabajar sin ningún problema con  las actualizaciones parciales.
    */
    public ProveedorModel editarProveedor(Long id, ProveedorDTO proveedorDTO){
        ProveedorModel provMod = proveedorRepository.findById(id) //Por alguna razón esto actualiza
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado"));

        if(proveedorRepository.findByNombre(proveedorDTO.nombre()).isPresent()){
            throw new IllegalArgumentException("No puedes cambiarte a esze nombre, el nombre ya se encuentra en uso.");
        }
        
        if (proveedorDTO.contrasena() == null){
            System.out.println("La contraseña no fue ingresada");
        } else {
           if(proveedorDTO.contrasena().length() > 16){
            throw new IllegalArgumentException("El límite de caracteres permitidos es de 16");
            } else if(proveedorDTO.contrasena().length() < 8){
                throw new IllegalArgumentException("El mínimo de carácteres permitidos es de 8");
            } else {
                provMod.setContrasena(proveedorDTO.contrasena());
            } 
        }
        
        if (proveedorDTO.nombre() == null){
            System.out.println("NOMBRE no ingresado, actualización parcial");
        } else {
            provMod.setNombre(proveedorDTO.nombre());
        }
        
        if (proveedorDTO.rol() == null){
            System.out.println("ROL  no ingresado, actualización parcial");
        } else {
            provMod.setRol(proveedorDTO.rol());
        }
        return proveedorRepository.save(provMod);
    }
    
    /**
     * Registro: Cuando se llama a save con una entidad nueva 
     * (es decir, una entidad cuyo identificador (id) aún no existe en la base de datos o es null),-
     * el save la inserta como un nuevo registro en la base de datos.
     * 
     * Actualización: Cuando se llama a save con una entidad cuyo identificador ya existe en la base de datos 
     * (es decir, el id no es null y ya está asociado con un registro en la base de datos), -
     * save actualiza ese registro con los nuevos valores del objeto.
     */
    public Optional<ProveedorModel> getProveedorById(Long id){
        return proveedorRepository.findById(id);
    }
}
