package cl.duoc.demomicroservicio.services;

import java.util.List;

import cl.duoc.demomicroservicio.model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.demomicroservicio.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuariorepository;

    public List<Usuario> findAll(){
        return usuariorepository.findAll();
    }

    public Usuario findById(Long rut){
        return usuariorepository.findById(rut).get();
    }

    public Usuario guardarUsuario(Usuario usuario){
        return usuariorepository.save(usuario);
    }

    public void eliminarUsuario(Long rut) {
        usuariorepository.deleteById(rut);
    }
    
}
