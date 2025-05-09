package cl.duoc.demomicroservicio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cl.duoc.demomicroservicio.model.Producto;
import cl.duoc.demomicroservicio.services.ProductosServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/productos")
public class ProductosController {

    @Autowired
    private ProductosServices productosServices;

    @GetMapping
   public ResponseEntity<List<Producto>> listarTodos(@RequestParam(required = false) String codProducto) {
        List<Producto> productos = productosServices.listarTodos();
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(productos);
        }
    }

    @GetMapping("/{codProducto}") // buscar por codProducto}
    public ResponseEntity<Producto> buscarPorCodProd(@PathVariable String codProducto) {
        Producto producto = productosServices.buscarCodProd(codProducto);
        if (producto != null) {
            return ResponseEntity.ok(producto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody Producto producto) {
       try{
        // Verificar si el producto ya existe
        Producto ProductoCrear = productosServices.buscarCodProd(producto.getCodProducto());
        if(ProductoCrear != null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El producto ya existe con el codigo: " + producto.getCodProducto());
        }else{
            // Guardar el nuevo producto
            Producto ProductoNuevo = productosServices.guardar(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Producto creado con exito");
        }
       }catch(Exception e){
        Producto ProductoNuevo = productosServices.guardar(producto);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el producto: " + e.getMessage());
       }
    }

    @PutMapping("/{codProducto}")
    public ResponseEntity<?> actualizarProducto(@PathVariable String codProducto, @RequestBody Producto prod){
        try{
            Producto productosEditar = productosServices.buscarCodProd(codProducto);
            if (productosEditar == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado con el codigo: " + codProducto);
            }
            productosEditar.setNombre(prod.getNombre());
            productosEditar.setDescripcion(prod.getDescripcion());
            productosEditar.setPrecio(prod.getPrecio());
            productosEditar.setCategoria(prod.getCategoria());
            productosEditar.setCodProducto(prod.getCodProducto());
            productosEditar.setFechaCreacion(prod.getFechaCreacion());
            productosEditar.setActivo(prod.getActivo());
            productosEditar.setFechaActualizacion(prod.getFechaActualizacion());
            productosServices.guardar(productosEditar);
            return ResponseEntity.ok("Producto actualizado correctamente");
            
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el producto: " + e.getMessage());
        }
    }

    @PutMapping("/eliminar/{codProducto}")
    public ResponseEntity<?> eliminarProducto(@PathVariable String codProducto) {
        try{
            Producto productosEliminar = productosServices.buscarCodProd(codProducto);
            if (productosEliminar == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado con el codigo: " + codProducto);
            }
            productosEliminar.setActivo(false);
            productosServices.guardar(productosEliminar);
            return ResponseEntity.ok("Producto eliminado correctamente o se quedo sin stock");
            
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el producto: " + e.getMessage());
        }
    }

    @PutMapping("/restaurar/{codProducto}")
    public ResponseEntity<?> restaurarProducto(@PathVariable String codProducto) {
        try{
            Producto productosRestaurar = productosServices.buscarCodProd(codProducto);
            if (productosRestaurar == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado con el codigo: " + codProducto);
            }
            productosRestaurar.setActivo(true);
            productosServices.guardar(productosRestaurar);
            return ResponseEntity.ok("Producto restaurado correctamente");
            
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al restaurar el producto: " + e.getMessage());
        }
    }
}
   
    
