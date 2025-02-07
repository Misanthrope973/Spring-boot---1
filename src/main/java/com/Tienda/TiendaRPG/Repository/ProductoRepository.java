package com.Tienda.TiendaRPG.Repository;

import com.Tienda.TiendaRPG.Model.ProductoModel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoModel, Long>{
    Optional<ProductoModel> findByNombre(String nombre);
}
