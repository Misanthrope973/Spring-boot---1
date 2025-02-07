package com.Tienda.TiendaRPG.Controller;

import com.Tienda.TiendaRPG.DTO.ProductoDTO;
import com.Tienda.TiendaRPG.Model.ProductoModel;
import com.Tienda.TiendaRPG.Service.ProductoService;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producto")
public class ProductoControl {
    /**
     * Variables:
     * @param id
     * @param productoDTO
     * @param productoService
     */
    
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ProveedorControl.class);
    
    //Instanciar la clase
    private final ProductoService productoService;
    
    public ProductoControl(ProductoService productoService){
        this.productoService = productoService; //Envía los datos al service que luego enviará a la base de datos
    }
    
    /**
     * Registra un nuevo producto en la base de datos.
     *
     * @param productoDTO Datos del producto a registrar.
     * @return ResponseEntity con el producto registrado o un mensaje de error.
     * @throws IllegalArgumentException Si los datos ingresados son inválidos.
     * @throws Exception Si ocurre un error en el servidor.
     */
    @PostMapping("/registrar")
    public ResponseEntity<?> register(@RequestBody ProductoDTO productoDTO){
        try{
            logger.info("Haciendo petición a: /productos/registrar");
            logger.info("Datos ingresados: " + productoDTO);
            
            ProductoModel prodMod = productoService.registrarProducto(productoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(prodMod);     // return ResponseEntity.CREATED(prodMod); Eso si no
                                                                                // Se quiere mostrar el resultado de la operación 
        
        } catch(IllegalArgumentException e){
            logger.error("Error al registrar el producto: " + e);
            return ResponseEntity.badRequest().body("Error al registrar el "
                    + "producto: " + e.getMessage());   //Mostrar el error en el body de JSON
            
        } catch(Exception e){
            logger.error("Error al registrar el producto: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error por parte del servidor al intentar " +
                            "registrar el producto" + e.getMessage());   //Mostrar el error en el body de JSON
        }
    }
    /**
     * 
     * @param id
     * @return productoService: Devuelve el producto seleccionado
     */
    @GetMapping("/obtener/{id}")
    public ResponseEntity<?> getProductoById(@PathVariable Long id){
        return productoService.getProducto(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    /**
     * @param  ProductoDTO: devuelve los datos del DTO
     * @return ResponseEntity.ok(producto): Devuelve todos los empleados
     */
    @GetMapping("/obtener/todos")
    public ResponseEntity<List<ProductoDTO>> getAllProductos(){
        List<ProductoDTO> producto = productoService.obtenerTodosLosProductos();
        return ResponseEntity.ok(producto);
    }
     /**
     * Edita un producto existente en la base de datos.
     *
     * @param id ID del producto a editar.
     * @param productoDTO Datos actualizados del producto.
     * @return ResponseEntity con el producto actualizado o un mensaje de error.
     * @throws IllegalArgumentException Si los datos ingresados son inválidos.
     * @throws Exception Si ocurre un error en el servidor.
     */
    
    @PutMapping("/editar/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody ProductoDTO productoDTO)
    {
        try{
            logger.info("Haciendo petición a: /productos/editar");
            logger.info("Datos ingresados: " + productoDTO);
            
            ProductoModel prodMod = productoService.editarProducto(id, productoDTO);
                return ResponseEntity.ok(prodMod);
                
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body("Error al editar el producto: " + 
                    e.getMessage());
        
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error por parte del servidor al intentar editar el "+ 
                            "producto" + e.getMessage());
        }
    }
    /**
     * 
     * @param id
     * @return ResponseEntity.ok().build(): elimina al usuario
     */
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id){
        try{
            logger.info("haciendo petición a /borrar");
            logger.info("id ingresado: " + id);
            
            productoService.eliminarProducto(id);
            return ResponseEntity.ok("Producto eliminado con éxito");
        } catch (IllegalArgumentException e){
            
            logger.warn("Error al registrar al usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch(Exception e){
            
            logger.error("ERROR AL ELIMINAR EL PRODUCTO: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar producto: " + e.getMessage());
        }
    }
}
