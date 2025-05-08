select * from productos;

DESCRIBE productos;
-- PRODUCTO_ID         NOT NULL NUMBER         

-- NOMBRE              NOT NULL VARCHAR2(100)  

-- DESCRIPCION                  VARCHAR2(4000) 

-- PRECIO              NOT NULL NUMBER(10,2)   

-- CATEGORIA                    VARCHAR2(50)   

-- COD_PRODUCTO        NOT NULL VARCHAR2(20)   

-- FECHA_CREACION               TIMESTAMP(6)   

-- ACTIVO                       NUMBER(1)      

-- FECHA_ACTUALIZACION          TIMESTAMP(6)


insert into productos (nombre, descripcion, precio, categoria, cod_producto, fecha_creacion, activo)
values ('Producto A', 'Descripción del producto A', 10.99, 'Categoría 1', 'PROD001', SYSTIMESTAMP, 1);