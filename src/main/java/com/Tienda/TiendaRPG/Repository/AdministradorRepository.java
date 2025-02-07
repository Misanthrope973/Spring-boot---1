package com.Tienda.TiendaRPG.Repository;

import com.Tienda.TiendaRPG.Model.AdministradorModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministradorRepository extends JpaRepository<AdministradorModel, Long>{
    Optional<AdministradorModel> findByNombre(String nombre);
}
