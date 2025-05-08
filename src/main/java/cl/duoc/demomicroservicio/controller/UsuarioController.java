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

import cl.duoc.demomicroservicio.model.Usuario;
import cl.duoc.demomicroservicio.services.UsuarioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/Usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioservice;

    @GetMapping
    public ResponseEntity<List<Usuario>> Listar() {
        List<Usuario> usuarios = usuarioservice.findAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(usuarios);
        }
    }

    @GetMapping("/{rut}")
    public ResponseEntity<Usuario> BuscarUsuario(@PathVariable Long rut) {
        try {
            Usuario usuario = usuarioservice.findById(rut);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> guardarUsuario(@RequestBody Usuario usuario) {

        try {
            Usuario usuarioCrear = usuarioservice.findById(usuario.getRut());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuario con ese rut existe");
        } catch (Exception e) {
            Usuario usuarioNuevo = usuarioservice.guardarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado con exito");
        }
    }

    @DeleteMapping("/{rut}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long rut) {

        try {
            Usuario usuarioEliminar = usuarioservice.findById(rut);
            usuarioservice.eliminarUsuario(rut);
            return ResponseEntity.status(HttpStatus.OK).body("Usuario eliminado con exito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

    }

    @PutMapping("path/{rut}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long rut, @RequestBody Usuario usuario) {
        try {
            Usuario usuarioEditar = usuarioservice.findById(rut);
            if (usuarioEditar == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado con rut: " + rut);
            }

            usuarioEditar.setRut(rut);
            usuarioEditar.setNombre(usuario.getNombre());
            usuarioEditar.setMail(usuario.getMail());
            usuarioEditar.setIdcurso(usuario.getIdcurso());
            usuarioservice.guardarUsuario(usuarioEditar);

            return ResponseEntity.ok("Usuario actualizado correctamente");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el usuario: " + e.getMessage());
        }
    }
}
