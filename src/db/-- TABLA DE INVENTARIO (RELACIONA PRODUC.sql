-- TABLA DE INVENTARIO (RELACIONA PRODUCTOS CON TIENDAS)
CREATE TABLE INVENTARIO (
    INVENTARIO_ID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    PRODUCTO_ID NUMBER NOT NULL,
    TIENDA_ID NUMBER NOT NULL,
    CANTIDAD NUMBER NOT NULL DEFAULT 0,
    STOCK_MINIMO NUMBER DEFAULT 5,
    ULTIMA_ACTUALIZACION TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FK_INVENTARIO_PRODUCTO FOREIGN KEY (PRODUCTO_ID) REFERENCES PRODUCTOS(PRODUCTO_ID),
    CONSTRAINT FK_INVENTARIO_TIENDA FOREIGN KEY (TIENDA_ID) REFERENCES TIENDA(TIENDA_ID),
    CONSTRAINT UQ_PRODUCTO_TIENDA UNIQUE (PRODUCTO_ID, TIENDA_ID)
);


select * from productos;