package cl.duoc.demomicroservicio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.demomicroservicio.model.Usuarios;
import cl.duoc.demomicroservicio.services.UsuariosService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/users")
public class UsuariosController {

    @Autowired
    private UsuariosService usuariosService;

    @GetMapping
    public ResponseEntity<List<Usuarios>> ListarTodos() {
        List<Usuarios> usuarios = usuariosService.listarTodos();
       if(usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
            //return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.ok(usuarios);
        }
    }

    @GetMapping("/{rut}") // buscar por rut
    public ResponseEntity<Usuarios> buscarPorRut(@PathVariable String rut) {
        Usuarios usuario = usuariosService.buscarXrut(rut);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> guardarUsuario(@RequestBody Usuarios us) {
        try {
            Usuarios usuarioCrear = usuariosService.buscarXrut(us.getRut());
            if (usuarioCrear != null) {
                
                //return ResponseEntity.status(HttpStatus.CONFLICT).build();
                return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya existe con rut: " + us.getRut());
            } else {
                Usuarios usuarioNuevo = usuariosService.guardar(us);
                return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado con exito");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el usuario: " + e.getMessage());

        }
    }

    @PutMapping("/{rut}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable String rut, @RequestBody Usuarios us) {
        try{
            Usuarios usuarioEditar = usuariosService.buscarXrut(rut);
            if (usuarioEditar == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado con rut: " + rut);
            }
            usuarioEditar.setRut(us.getRut());
            usuarioEditar.setNombre(us.getNombre());
            usuarioEditar.setEmail(us.getEmail());
            usuarioEditar.setPasswordHash(us.getPasswordHash());
            usuarioEditar.setId_rol(us.getId_rol());
            usuarioEditar.setTelefono(us.getTelefono());
            usuarioEditar.setDireccion(us.getDireccion());
            usuarioEditar.setActivo(us.getActivo());
            usuariosService.guardar(usuarioEditar);
            return ResponseEntity.ok("Usuario actualizado correctamente");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el usuario: " + e.getMessage());
        }
    }

    @DeleteMapping("/{rut}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable String rut) {
        try {
            Usuarios usuarioExistente = usuariosService.buscarXrut(rut);
            if (usuarioExistente != null) {
                usuariosService.eliminar(rut);
                return ResponseEntity.status(HttpStatus.CREATED).body("Usuario eliminado con exito");
                
            } else {
                // return ResponseEntity.notFound().build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el usuario: " + e.getMessage());
        }
    }
}
