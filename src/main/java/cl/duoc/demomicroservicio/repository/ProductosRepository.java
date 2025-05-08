package cl.duoc.demomicroservicio.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.demomicroservicio.model.Producto;
import java.util.Optional;
public interface ProductosRepository extends JpaRepository <Producto, Long> {  
    Optional<Producto> findByCodProducto(String codProducto);
    //void deleteByCodProducto(String codProducto);
}
