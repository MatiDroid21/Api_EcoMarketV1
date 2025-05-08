package cl.duoc.demomicroservicio.services;

import java.util.*;
import cl.duoc.demomicroservicio.model.Usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.duoc.demomicroservicio.repository.UsuarioRepository;
import cl.duoc.demomicroservicio.repository.UsuariosRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuariosService {
    @Autowired
    private UsuariosRepository ur;

    public List<Usuarios> listarTodos(){
        return ur.findAll();
    }

    public Usuarios buscarXid(Long usuario_id){
        return ur.findById(usuario_id).get();
    }

    public Usuarios buscarXrut(String rut){
        return ur.findByRut(rut).orElse(null);
    }

    public Usuarios guardar(Usuarios usuario){
       return ur.save(usuario);
    }

    public void eliminar(String rut){
        ur.deleteByRut(rut);
    }
}