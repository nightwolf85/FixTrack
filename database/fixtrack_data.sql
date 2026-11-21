-- =========================================================================
-- FICHERO 2: fixtrack_data.sql
-- Descripción: Inserción de datos iniciales (roles, usuarios, clientes, artículos, partes y líneas)
-- =========================================================================

-- Inserción de Roles
INSERT INTO rol (id_rol, nombre_rol) VALUES 
(1, 'ADMIN'),
(2, 'ADMINISTRATIVO'),
(3, 'TECNICO');

-- Inserción de Usuarios asociados a cada rol
INSERT INTO usuario (id_usuario, nombre, email, password, id_rol) VALUES 
(1, 'Carlos Admin', 'admin@fixtrack.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 1),
(2, 'Laura Oficina', 'administrativo@fixtrack.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 2),
(3, 'Juan Técnico', 'tecnico1@fixtrack.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 3),
(4, 'Pedro Operario', 'tecnico2@fixtrack.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 3);

-- Inserción de Clientes (5 registros)
INSERT INTO cliente (id_cliente, nombre, nif, telefono, direccion, email) VALUES 
(1, 'Comunidad de Vecinos Sol', 'B12345678', '912345678', 'Calle Mayor 12, Madrid', 'comunidad@sol.com'),
(2, 'Talleres Mecánicos Aluche', 'A87654321', '914567890', 'Avenida de los Poblados 45, Madrid', 'info@aluche.com'),
(3, 'Restaurante El Buen Sabor', 'F11223344', '913334455', 'Calle Toledo 88, Madrid', 'contacto@buensabor.es'),
(4, 'María Dolores Gómez', '03456789X', '655443322', 'Calle Goya 5, 2ºB, Madrid', 'mdolores@gmail.com'),
(5, 'Oficinas Centrales Tech', 'B99887766', '918887766', 'Paseo de la Castellana 100, Madrid', 'it@tech.es');

-- Inserción de Artículos / Repuestos (5 registros)
INSERT INTO articulo (id_articulo, codigo, descripcion, precio_unitario, stock_actual) VALUES 
(1, 'REP-VAL01', 'Válvula de paso esférica 1/2"', 18.50, 25),
(2, 'REP-TUB02', 'Tubo de cobre flexible 15mm (metro)', 12.00, 50),
(3, 'REP-FIL03', 'Filtro de partículas aire acondicionado', 35.00, 15),
(4, 'REP-TER04', 'Termostato digital programable', 45.90, 10),
(5, 'REP-CABLE05', 'Cable coaxial apantallado (bobina 10m)', 22.00, 30);

-- Inserción de Partes de Trabajo (5 registros)
INSERT INTO parte_trabajo (id_parte, fecha_emision, descripcion_averia, estado, firma_cliente, id_cliente, id_usuario_tecnico) VALUES 
(1, '2026-03-01 09:30:00', 'Fuga de agua en válvula principal de la comunidad.', 'FINALIZADO', 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAAB...', 1, 2),
(2, '2026-03-01 11:15:00', 'Revisión periódica y cambio de filtros en climatizador.', 'FINALIZADO', 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAAB...', 3, 2),
(3, '2026-03-02 08:00:00', 'Sustitución de termostato averiado en despacho.', 'EN_CURSO', NULL, 5, 3),
(4, '2026-03-02 10:30:00', 'Reparación de acometida eléctrica y cableado.', 'PENDIENTE', NULL, 2, 3),
(5, '2026-03-02 15:00:00', 'Revisión general de instalación de fontanería.', 'PENDIENTE', NULL, 4, 2);

-- Inserción de Líneas de Partes (5 registros de relación N:M)
INSERT INTO linea_parte (id_linea, id_parte, id_articulo, unidades, horas_empleadas) VALUES 
(1, 1, 1, 1, 1.50),
(2, 1, 2, 2, 0.00),
(3, 2, 3, 1, 1.00),
(4, 2, 4, 1, 0.50),
(5, 3, 4, 1, 2.00);