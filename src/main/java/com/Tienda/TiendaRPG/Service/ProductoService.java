package com.Tienda.TiendaRPG.Service;

import com.Tienda.TiendaRPG.DTO.ProductoDTO;
import com.Tienda.TiendaRPG.Model.ProductoModel;
import com.Tienda.TiendaRPG.Repository.ProductoRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {
    
    /**
     * Variables en esta clase: 
     * @param productoRepository  |  Es donde se realiza el CRUD.
     * @param poructoModel        |  Es donde ingresan los datos.
     * @param productoDTO         |  Es de donde se toman los datos.
     */
    
    /**
     * El repositorio se trae para luego utilizarlo
     */
    private ProductoRepository productoRepository;
    
    @Autowired
    public ProductoService(ProductoRepository productoRepository){
        this.productoRepository = productoRepository;
    }
    
    public ProductoModel registrarProducto (ProductoDTO productoDTO){
        
        /**
         * @param productoRepository  |  Es donde se envían los datos a la base de datos.
         * @param productoDTO         |  Es donde los datos ingresados por el usuario paran para
         *                            |  luego pasar acá, el service, que funciona como un puente entre el DTO
         *                            |  Y el Repository
         * @param prodMod             |   Envía los datos al Repository
         */
        
        // verificar si el producto ya existe.
        if(productoRepository.findByNombre(productoDTO.nombre()).isPresent()){
            throw new IllegalArgumentException("El producto ya está registrado");
        }
        
        // Verificar si todos los datos han sido ingresados.
        if(productoDTO.descripcion() == null || productoDTO.id_proveedores() == 0 || productoDTO.nombre() == null 
                || productoDTO.precio() == 0 || productoDTO.stock() == 0){
            throw new IllegalArgumentException("Debes ingresar todos los datos antes de registrar el producto");
        }
        // Ingresa los datos para luego enviarlos al Repository
        ProductoModel prodMod = new ProductoModel();
        prodMod.setId_proveedores(productoDTO.id_proveedores());
        prodMod.setNombre(productoDTO.nombre());
        prodMod.setDescripcion(productoDTO.descripcion());
        prodMod.setPrecio(productoDTO.precio());
        prodMod.setStock(productoDTO.stock());
        
        return productoRepository.save(prodMod);
    }
    /**
     * @param id: busca los datos mediante del id
     * @return productoRepository.findById(id);
     */
    public Optional<ProductoModel> getProducto(Long id){
        return productoRepository.findById(id);
    }
    /**
     * @param productoDTO: envía todos los datos en formato JSON
     * @return 
     */
    public List<ProductoDTO> obtenerTodosLosProductos(){
        return productoRepository.findAll().stream()
            .map(productoDTO -> new ProductoDTO(
                productoDTO.getId(),
                productoDTO.getId_proveedores(),
                productoDTO.getNombre(),
                productoDTO.getDescripcion(),
                productoDTO.getPrecio(),
                productoDTO.getStock()
            ))
            .collect(Collectors.toList());
    }
    /**
     * 
     * @param id: Verificar si el ID existe, si es así actualiza, sino, lanza error
     * @param prodMod: Envía los datos a la entidad.
     * @param productoDTO: Toma los datos enviados por el cliente y los envía al Repository
     * @return productoRepository: Envía los datos a la base de datos.
     */
    
    public ProductoModel editarProducto(Long id, ProductoDTO productoDTO){

        ProductoModel prodMod = productoRepository.findById(id) //Por alguna razón esto actualiza
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado"));

        if(productoRepository.findByNombre(productoDTO.nombre()).isPresent()){
            throw new IllegalArgumentException("No puedes cambiar el nombre del "
                    + "producto a ese, ya se encuentra registrado.");
        }
        
        if(productoDTO.id_proveedores() == 0){
            System.out.println("El id del proveedor no se actualizó");
        } else {
            System.out.println("Proveedor actualizado");
            prodMod.setId_proveedores(productoDTO.id_proveedores());
        }
        
        if(productoDTO.nombre() == null){
            System.out.println("El nombre del producto no se actualizó");
        } else {
            System.out.println("El nombre del producto se actualizó a el de: " + 
                    productoDTO.nombre());
            
            prodMod.setNombre(productoDTO.nombre());
        }
        
        if(productoDTO.descripcion() == null){
            System.out.println("Descripcion no actualizada");
        } else {
            System.out.println("La descripción nueva es: " + productoDTO.descripcion());
            prodMod.setDescripcion(productoDTO.descripcion());
        }
        
        if(productoDTO.precio() == 0){
            System.out.println("Precio no actualizado");
        } else {
            System.out.println("Precio actualizado, nuevo precio: " 
                    + productoDTO.precio());
            
            prodMod.setPrecio(productoDTO.precio());
        }
        
        if(productoDTO.stock() == 0){
            System.out.println("Stock no actualizado");
        } else {
            System.out.println("Precio actualizado, nuevo precio: " + 
                    productoDTO.stock());
            
            prodMod.setStock(productoDTO.stock());
        }
        
        return productoRepository.save(prodMod);
        
        /**
         * Si quitas algún condicional, a la hora de ejecutar te saldrá error porque no llenastes algún campo.
         */
    }
    
    /**
     * 
     * @param id 
     */
    public void eliminarProducto(Long id){
        if (!productoRepository.existsById(id)){
            throw new IllegalArgumentException("Producto no encontrado");
        }
        productoRepository.deleteById(id);
    }
}
