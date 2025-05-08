package cl.duoc.demomicroservicio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.*;

@Entity
@Table(name = "PRODUCTOS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCTO_ID")
    private Long productoId;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "DESCRIPCION", nullable = false)
    private String descripcion;

    @Column(name = "PRECIO", nullable = false)
    private Double precio;

    @Column(name = "CATEGORIA", nullable = false)
    private String categoria;

    @Column(name = "COD_PRODUCTO", nullable = false)
    private String codProducto;

    @Column(name = "FECHA_CREACION", nullable = false)
    private String fechaCreacion;

    @Column(name = "ACTIVO", nullable = false)
    private Boolean activo = true;
    
    @Column(name = "FECHA_ACTUALIZACION", nullable = false)    
    private String fechaActualizacion;

}
