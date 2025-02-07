package com.Tienda.TiendaRPG.Service;

import com.Tienda.TiendaRPG.DTO.AdministradorDTO;
import com.Tienda.TiendaRPG.DTO.ProveedorDTO;
import com.Tienda.TiendaRPG.Model.AdministradorModel;
import com.Tienda.TiendaRPG.Model.ProveedorModel;
import com.Tienda.TiendaRPG.Repository.AdministradorRepository;
import com.Tienda.TiendaRPG.Repository.ProveedorRepository;
import com.Tienda.TiendaRPG.funcionesExtras.Funciones;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministradorService {
    private AdministradorRepository admRepository;
    private ProveedorRepository provRepository;
    
    /**
     * @param admRepository Instancia de la clase AdministradorRepository
     */
    @Autowired
    public AdministradorService(AdministradorRepository admRepository, ProveedorRepository provRepository){
        this.admRepository = admRepository;
        this.provRepository = provRepository;
    }
    
    /**
     *  
     * @param admDTO
     * @param admModel
     * 
     * @return admRepository
     */
    public AdministradorModel registrarAdm(AdministradorDTO admDTO){
        if(admRepository.findByNombre(admDTO.nombre()).isPresent()){
            throw new IllegalArgumentException("Administrador ya registrado.");
        }
        
        if(admDTO.contrasena() == null || admDTO.contrasena() == null || admDTO.nombre() == null){
            throw new IllegalArgumentException("Debes llenar los acmpos antes de continuar ");
        }
        
        AdministradorModel admModel = new AdministradorModel();
        admModel.getId();
        //VERIFICAR SI LA CONTRASEÑA CUMPLE CON LOS PARÁMETROS REQUERIDOS
        Funciones contra = new Funciones();
        try {
            contra.verificarContrasena(admDTO.contrasena());
            
            admModel.setContrasena(admDTO.contrasena());
            
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        
        admModel.setNombre(admDTO.nombre());
        admModel.setRol(admDTO.rol());
        
        return admRepository.save(admModel);
    }
    /**
     * 
     * @param id
     * @param admDTO
     * @return 
     */
    public AdministradorModel editarAdm(Long id, AdministradorDTO admDTO){
        //VERIFICAR SI EL ADMIN EXISTE
        AdministradorModel admModel = admRepository.findById(id) //Por alguna razón esto actualiza
                .orElseThrow(() -> new IllegalArgumentException("El administrador que intenta editar no existe..."));
        
        //VERIFICAR SI EL ADMIN EXISTE, SI ES ASÍ, RETORNAR ERROR AL REGISTRAR
        if(admRepository.findByNombre(admDTO.nombre()).isPresent()){
            throw new IllegalArgumentException("El administrador ya se encuentra registrado");
        }
        
        if(admDTO.nombre() == null){
            System.out.println("Nombre no ingresado");
        } else {
            admModel.setNombre(admDTO.nombre());
        }
        
        if(admDTO.contrasena() == null){
            System.out.println("Contrasena no ingresada");
        } else {
            admModel.setContrasena(admDTO.contrasena());
        }
        
        if(admDTO.rol() == null){
            System.out.println("rol no ingresado");
        } else {
            admModel.setRol(admDTO.rol());
        }
        return admRepository.save(admModel);
    }
    
    //Abajo es para ver al/los proveedor/es
    /**
     * 
     * @param id
     * @return 
     */
    public Optional<ProveedorModel> getProveedorById(Long id){
        ProveedorModel provMod = provRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El proveedor no existe..."));
        
        return provRepository.findById(id);
    }
    
    /**
     * 
     * @return 
     */
    public List<ProveedorDTO> getAllProveedores(){
        return provRepository.findAll().stream()
            .map(ProveedorDTO -> new ProveedorDTO(
                ProveedorDTO.getId(),
                ProveedorDTO.getNombre(),
                ProveedorDTO.getContrasena(),
                ProveedorDTO.getRol()
            ))
            .collect(Collectors.toList());
    }
    
    public void eliminarProv(Long id){
        ProveedorModel provMod = provRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El proveedor no existe-"));
        
        provRepository.deleteById(id);
    }
}
