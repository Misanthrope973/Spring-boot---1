package com.Tienda.TiendaRPG.Controller;

import com.Tienda.TiendaRPG.DTO.AdministradorDTO;
import com.Tienda.TiendaRPG.DTO.ProveedorDTO;
import com.Tienda.TiendaRPG.Model.AdministradorModel;
import com.Tienda.TiendaRPG.Service.AdministradorService;
import com.Tienda.TiendaRPG.Service.ProveedorService;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/administrador")
public class AdministradorControl {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AdministradorControl.class);
    
    //Instanciar la clase
    private AdministradorService admService;
    private ProveedorService provService;
    @Autowired
    public AdministradorControl(AdministradorService admService, ProveedorService provService){
        this.admService = admService;
        this.provService = provService;
    }
    
    /**
     * @param admService
     * @param admDTO
     * @return ResponseEntity
     */
    
    //Registrar
    @PostMapping("/registrar")
    public ResponseEntity<?> register(@RequestBody AdministradorDTO admDTO){
        try{
            logger.info("Haciendo petición a /administrador/registrar");
            logger.info("Datos ingresados: " + admDTO);
            
            AdministradorModel admModel = admService.registrarAdm(admDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(admModel); //Puedes poner admModel o admDTO, la diferencia es que en el response
        } catch (IllegalArgumentException e){                                //Entity mostrará o no el ID, no es necesario mostrar el ID para el admin o proveedor
            return ResponseEntity.badRequest().body("Error al registrarse: " + e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error por parte del servidor" + e.getMessage());
        }
    }
    /**
     * @param admService
     * @param admDTO
     * @return ResponseEntity
     */
    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody AdministradorDTO admDTO){
        try{
            logger.info("Haciendo peticion a /administrador/editar");
            logger.info("Datos ingresados: " + admDTO);
            
            AdministradorModel admModel = admService.editarAdm(id, admDTO);
            return ResponseEntity.ok(admModel);
        } catch (IllegalArgumentException e){
            logger.warn("Error al registrar: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error al editar: " + e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error por parte del servidor: " + e.getMessage());
        }
    }
    //-----------------------------PROVEEDORES----------------------------------
    /**
     * @param id
     * @return proveedor(por id)
     */
    @GetMapping("/proveedor/{id}")
    public ResponseEntity<?> getProveedor(@PathVariable Long id){
        return admService.getProveedorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * @param admService
     * @param proveedor
     * @return ResponseEntity
     */
    @GetMapping("/proveedores/todos")
    public ResponseEntity<List<ProveedorDTO>> getAllProveedores(){
        List<ProveedorDTO> proveedor = admService.getAllProveedores();
        return ResponseEntity.ok(proveedor);
    }

    /**
     * @param id
     * @return ResponseEntity
     */
    @DeleteMapping("/eliminar/proveedor/{id}")
    public ResponseEntity<?> deleteProveedor(@PathVariable Long id){
        try{
            logger.info("Haciendo peticion a administradores/elimiminar/proveedor/");
            logger.info("ID ingresado: " + id);
            admService.eliminarProv(id);
            return ResponseEntity.ok("Proveedor eliminado con éxito");
        } catch(IllegalArgumentException e){
            logger.warn("Error al eliminar: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error al eliminar: " + e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar al proveedor: " + e.getMessage());
        }   
    }
}
