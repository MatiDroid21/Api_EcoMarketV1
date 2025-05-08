package cl.duoc.demomicroservicio.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.demomicroservicio.model.Producto;
import cl.duoc.demomicroservicio.repository.ProductosRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductosServices {
    @Autowired  
    private ProductosRepository pr;

    public List<Producto> listarTodos(){
        return pr.findAll();
    }

    public Producto buscarCodProd(String codProducto){
        return pr.findByCodProducto(codProducto).orElse(null);
    }
  
    public Producto guardar(Producto producto){
        return pr.save(producto);
    }

    
    
}
