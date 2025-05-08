-- Tabla de Roles
CREATE TABLE Roles (
    rol_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR2(50) NOT NULL UNIQUE,
    descripcion VARCHAR2(255)
);

-- Insertar roles básicos
INSERT INTO Roles (nombre, descripcion) VALUES 
('Administrador', 'Acceso completo al sistema');
INSERT INTO Roles (nombre, descripcion) VALUES 
('Gerente', 'Gestiona tiendas, inventario y reportes');
INSERT INTO Roles (nombre, descripcion) VALUES 
('Empleado', 'Procesa ventas y atiende clientes');
INSERT INTO Roles (nombre, descripcion) VALUES 
('Logistica', 'Gestiona envíos y proveedores');
INSERT INTO Roles (nombre, descripcion) VALUES 
('Cliente', 'Usuario que compra productos');

-- Tabla de Usuarios (empleados y clientes)
CREATE TABLE Usuarios (
    usuario_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR2(100) NOT NULL,
    email VARCHAR2(100) UNIQUE NOT NULL,
    password_hash VARCHAR2(255) NOT NULL,
    rol_id NUMBER NOT NULL,
    telefono VARCHAR2(20),
    direccion VARCHAR2(4000),
    activo NUMBER(1) DEFAULT 1,
    CONSTRAINT fk_usuario_rol FOREIGN KEY (rol_id) REFERENCES Roles(rol_id)
);

-- Tabla de Tiendas
CREATE TABLE Tiendas (
    tienda_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR2(100) NOT NULL,
    direccion VARCHAR2(4000) NOT NULL,
    ciudad VARCHAR2(50) NOT NULL,
    telefono VARCHAR2(20),
    horario_apertura VARCHAR2(8),
    horario_cierre VARCHAR2(8),
    activa NUMBER(1) DEFAULT 1
);

-- Tabla de Productos
CREATE TABLE Productos (
    producto_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR2(100) NOT NULL,
    descripcion VARCHAR2(4000),
    precio NUMBER(10,2) NOT NULL,
    categoria VARCHAR2(50),
    imagen_url VARCHAR2(255),
    ecoscore VARCHAR2(1),
    activo NUMBER(1) DEFAULT 1
);

-- Tabla de Inventario (stock por tienda)
CREATE TABLE Inventario (
    inventario_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    producto_id NUMBER NOT NULL,
    tienda_id NUMBER NOT NULL,
    cantidad NUMBER NOT NULL DEFAULT 0,
    stock_minimo NUMBER DEFAULT 5,
    CONSTRAINT fk_inventario_producto FOREIGN KEY (producto_id) REFERENCES Productos(producto_id),
    CONSTRAINT fk_inventario_tienda FOREIGN KEY (tienda_id) REFERENCES Tiendas(tienda_id),
    CONSTRAINT uq_inventario UNIQUE (producto_id, tienda_id)
);

-- Tabla de Pedidos (ventas)
CREATE TABLE Pedidos (
    pedido_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    cliente_id NUMBER NOT NULL,
    tienda_id NUMBER,
    empleado_id NUMBER, -- Empleado que registró la venta
    fecha_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR2(20) DEFAULT 'pendiente' CHECK (estado IN ('pendiente', 'procesando', 'enviado', 'entregado', 'cancelado')),
    subtotal NUMBER(10,2) NOT NULL,
    descuento NUMBER(10,2) DEFAULT 0,
    total NUMBER(10,2) NOT NULL,
    metodo_pago VARCHAR2(50),
    CONSTRAINT fk_pedido_cliente FOREIGN KEY (cliente_id) REFERENCES Usuarios(usuario_id),
    CONSTRAINT fk_pedido_tienda FOREIGN KEY (tienda_id) REFERENCES Tiendas(tienda_id),
    CONSTRAINT fk_pedido_empleado FOREIGN KEY (empleado_id) REFERENCES Usuarios(usuario_id)
);

-- Detalle de los productos vendidos
CREATE TABLE DetallePedidos (
    detalle_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    pedido_id NUMBER NOT NULL,
    producto_id NUMBER NOT NULL,
    cantidad NUMBER NOT NULL,
    precio_unitario NUMBER(10,2) NOT NULL,
    CONSTRAINT fk_detalle_pedido FOREIGN KEY (pedido_id) REFERENCES Pedidos(pedido_id),
    CONSTRAINT fk_detalle_producto FOREIGN KEY (producto_id) REFERENCES Productos(producto_id)
);

-- Tabla de Reseñas y Reclamos
CREATE TABLE Resenas (
    resena_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    producto_id NUMBER NOT NULL,
    cliente_id NUMBER NOT NULL,
    pedido_id NUMBER, -- Opcional: relacionar con el pedido
    tipo VARCHAR2(20) DEFAULT 'reseña' CHECK (tipo IN ('reseña', 'reclamo')),
    calificacion NUMBER CHECK (calificacion BETWEEN 1 AND 5),
    comentario VARCHAR2(4000),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR2(20) DEFAULT 'pendiente' CHECK (estado IN ('pendiente', 'aprobado', 'rechazado', 'solucionado')),
    CONSTRAINT fk_resena_producto FOREIGN KEY (producto_id) REFERENCES Productos(producto_id),
    CONSTRAINT fk_resena_cliente FOREIGN KEY (cliente_id) REFERENCES Usuarios(usuario_id),
    CONSTRAINT fk_resena_pedido FOREIGN KEY (pedido_id) REFERENCES Pedidos(pedido_id)
);

-- Tabla de Proveedores (para logística)
CREATE TABLE Proveedores (
    proveedor_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR2(100) NOT NULL,
    contacto VARCHAR2(100),
    telefono VARCHAR2(20),
    email VARCHAR2(100),
    activo NUMBER(1) DEFAULT 1
);

-- Insertar usuario administrador inicial
INSERT INTO Usuarios (nombre, email, password_hash, rol_id) VALUES 
('Admin', 'admin@ecomarket.cl', '$2a$10$x...', 1);
INSERT INTO Usuarios (nombre, email, password_hash, rol_id) VALUES 
('Cliente Ejemplo', 'cliente@ecomarket.cl', '$2a$10$y...', 5);

COMMIT;