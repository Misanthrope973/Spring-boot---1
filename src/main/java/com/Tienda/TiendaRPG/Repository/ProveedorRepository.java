package com.Tienda.TiendaRPG.Repository;

import com.Tienda.TiendaRPG.Model.ProveedorModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
* Repository es una interfaz que proporciona métodos para interactuar con la base de datos. 
* Es parte del patrón DAO (Data Access Object) y se utiliza para realizar operaciones CRUD 
* (Crear, Leer, Actualizar, Eliminar) y consultas personalizadas sobre las entidades.
*/

@Repository
public interface ProveedorRepository extends JpaRepository<ProveedorModel, Long>{
    Optional<ProveedorModel> findByNombre(String nombre);
}
