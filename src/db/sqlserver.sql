-- Tabla de Roles
CREATE TABLE Roles (
    rol_id INT IDENTITY(1,1) PRIMARY KEY,
    nombre NVARCHAR(50) NOT NULL UNIQUE,
    descripcion NVARCHAR(255)
);

-- Insertar roles básicos
INSERT INTO Roles (nombre, descripcion) VALUES 
('Administrador', 'Acceso completo al sistema'),
('Gerente', 'Gestiona tiendas, inventario y reportes'),
('Empleado', 'Procesa ventas y atiende clientes'),
('Logistica', 'Gestiona envíos y proveedores'),
('Cliente', 'Usuario que compra productos');

-- Tabla de Usuarios (empleados y clientes)
CREATE TABLE Usuarios (
    usuario_id INT IDENTITY(1,1) PRIMARY KEY,
    nombre NVARCHAR(100) NOT NULL,
    email NVARCHAR(100) UNIQUE NOT NULL,
    password_hash NVARCHAR(255) NOT NULL,
    rol_id INT NOT NULL,
    telefono NVARCHAR(20),
    direccion NVARCHAR(MAX),
    activo BIT DEFAULT 1,
    FOREIGN KEY (rol_id) REFERENCES Roles(rol_id)
);

-- Tabla de Tiendas
CREATE TABLE Tiendas (
    tienda_id INT IDENTITY(1,1) PRIMARY KEY,
    nombre NVARCHAR(100) NOT NULL,
    direccion NVARCHAR(MAX) NOT NULL,
    ciudad NVARCHAR(50) NOT NULL,
    telefono NVARCHAR(20),
    horario_apertura TIME,
    horario_cierre TIME,
    activa BIT DEFAULT 1
);

-- Tabla de Productos
CREATE TABLE Productos (
    producto_id INT IDENTITY(1,1) PRIMARY KEY,
    nombre NVARCHAR(100) NOT NULL,
    descripcion NVARCHAR(MAX),
    precio DECIMAL(10,2) NOT NULL,
    categoria NVARCHAR(50),
    imagen_url NVARCHAR(255),
    ecoscore NCHAR(1),
    activo BIT DEFAULT 1
);

-- Tabla de Inventario (stock por tienda)
CREATE TABLE Inventario (
    inventario_id INT IDENTITY(1,1) PRIMARY KEY,
    producto_id INT NOT NULL,
    tienda_id INT NOT NULL,
    cantidad INT NOT NULL DEFAULT 0,
    stock_minimo INT DEFAULT 5,
    FOREIGN KEY (producto_id) REFERENCES Productos(producto_id),
    FOREIGN KEY (tienda_id) REFERENCES Tiendas(tienda_id),
    CONSTRAINT UQ_Inventario UNIQUE (producto_id, tienda_id)
);

-- Tabla de Pedidos (ventas)
CREATE TABLE Pedidos (
    pedido_id INT IDENTITY(1,1) PRIMARY KEY,
    cliente_id INT NOT NULL,
    tienda_id INT,
    empleado_id INT, -- Empleado que registró la venta
    fecha_pedido DATETIME2 DEFAULT CURRENT_TIMESTAMP,
    estado NVARCHAR(20) DEFAULT 'pendiente' CHECK (estado IN ('pendiente', 'procesando', 'enviado', 'entregado', 'cancelado')),
    subtotal DECIMAL(10,2) NOT NULL,
    descuento DECIMAL(10,2) DEFAULT 0,
    total DECIMAL(10,2) NOT NULL,
    metodo_pago NVARCHAR(50),
    FOREIGN KEY (cliente_id) REFERENCES Usuarios(usuario_id),
    FOREIGN KEY (tienda_id) REFERENCES Tiendas(tienda_id),
    FOREIGN KEY (empleado_id) REFERENCES Usuarios(usuario_id)
);

-- Detalle de los productos vendidos
CREATE TABLE DetallePedidos (
    detalle_id INT IDENTITY(1,1) PRIMARY KEY,
    pedido_id INT NOT NULL,
    producto_id INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES Pedidos(pedido_id),
    FOREIGN KEY (producto_id) REFERENCES Productos(producto_id)
);

-- Tabla de Reseñas y Reclamos
CREATE TABLE Resenas (
    resena_id INT IDENTITY(1,1) PRIMARY KEY,
    producto_id INT NOT NULL,
    cliente_id INT NOT NULL,
    pedido_id INT, -- Opcional: relacionar con el pedido
    tipo NVARCHAR(20) DEFAULT 'reseña' CHECK (tipo IN ('reseña', 'reclamo')),
    calificacion INT CHECK (calificacion BETWEEN 1 AND 5),
    comentario NVARCHAR(MAX),
    fecha_creacion DATETIME2 DEFAULT CURRENT_TIMESTAMP,
    estado NVARCHAR(20) DEFAULT 'pendiente' CHECK (estado IN ('pendiente', 'aprobado', 'rechazado', 'solucionado')),
    FOREIGN KEY (producto_id) REFERENCES Productos(producto_id),
    FOREIGN KEY (cliente_id) REFERENCES Usuarios(usuario_id),
    FOREIGN KEY (pedido_id) REFERENCES Pedidos(pedido_id)
);

-- Tabla de Proveedores (para logística)
CREATE TABLE Proveedores (
    proveedor_id INT IDENTITY(1,1) PRIMARY KEY,
    nombre NVARCHAR(100) NOT NULL,
    contacto NVARCHAR(100),
    telefono NVARCHAR(20),
    email NVARCHAR(100),
    activo BIT DEFAULT 1
);

-- Insertar usuario administrador inicial
INSERT INTO Usuarios (nombre, email, password_hash, rol_id) VALUES 
('Admin', 'admin@ecomarket.cl', '$2a$10$x...', 1),
('Cliente Ejemplo', 'cliente@ecomarket.cl', '$2a$10$y...', 5);