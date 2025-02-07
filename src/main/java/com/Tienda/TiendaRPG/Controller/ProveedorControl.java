package com.Tienda.TiendaRPG.Controller;

import com.Tienda.TiendaRPG.DTO.ProveedorDTO;
import com.Tienda.TiendaRPG.Model.ProveedorModel;
import com.Tienda.TiendaRPG.Service.ProveedorService;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proveedor")
public class ProveedorControl {

    /**
     * 
     * @param id
     * @param proveedorDTO
     * @return 
     */
    
    /*
    * El logger de abajo ayuda a ver la información de la clase y ver a tiempo real y con más detalle
    * Se puede configurar con .info; para inforar si algo transcurre con normalidad, datos ingresados, etc,
    * .warn; sirve para ver problemas durante la ejecución pero que no afectan con el funcionamiento de la clase.
    * Y .error; para visualizar los errores cometidos durante la ejecución.
    */
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ProveedorControl.class);
    private final ProveedorService proveedorService;
    
    public ProveedorControl(ProveedorService proveedorService){
        this.proveedorService = proveedorService;
    }
    
    @PostMapping("/registrar")
    public ResponseEntity<?> register(@RequestBody ProveedorDTO proveedorDTO){
        try{
            logger.info("Haciendo petición a /registrar");
            logger.info("Datos ingresados: " + proveedorDTO);
            
            ProveedorModel provMod = proveedorService.registrarProveedor(proveedorDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(provMod);
            
            /*
            * Verificar si el proveedor está registrado, si es así, retorna IllegalArgumentException que es llamado desde el Service ya que
            * Ahí se genera.
            * Esta exepción se presenta en 2 casos.
            * 1. Si el usuario ya está registrado
            * 2. La contraseña no cumple con los requisitos requerido (caracteres > 8 y < 16).
            */
        } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body("No puedes registrarte: " + e.getMessage());
        
        /*
        * Lo de abajo a pesar de ser similar a la excepción de arriba es para retornar errores si el causante es el servidor
        */
        } catch(Exception e){
            logger.error("Error al registrarse " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ERROR AL REGISTRARSE");
        }
    }
    /**
     * Abajo actualiza todos los campos y para actualización parcial
     */
    
    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody ProveedorDTO proveedorDTO){
        try{
            
            ProveedorModel provMod = proveedorService.editarProveedor(id, proveedorDTO);
            return ResponseEntity.ok(provMod);
            
        } catch (IllegalArgumentException e){
            
            logger.warn("No puedes actualizar: " + e.getMessage());
            return ResponseEntity.badRequest().body("No puedes actualizar: " + e);
        
        } catch (Exception e) {
        
            logger.error("Error al actualizar al proveedor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el producto: " + e.getMessage());
        }
    }
}
