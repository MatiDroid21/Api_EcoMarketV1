package cl.duoc.demomicroservicio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.demomicroservicio.model.Usuarios;
import java.util.Optional;
public interface UsuariosRepository extends JpaRepository <Usuarios, Long>{
      Optional<Usuarios> findByRut(String rut);
      void deleteByRut(String rut);
}