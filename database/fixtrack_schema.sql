-- =========================================================================
-- FICHERO 1: fixtrack_schema.sql
-- Descripción: Estructura de tablas, claves primarias y restricciones FK
-- =========================================================================

DROP TABLE IF EXISTS linea_parte CASCADE;
DROP TABLE IF EXISTS parte_trabajo CASCADE;
DROP TABLE IF EXISTS articulo CASCADE;
DROP TABLE IF EXISTS cliente CASCADE;
DROP TABLE IF EXISTS usuario CASCADE;
DROP TABLE IF EXISTS rol CASCADE;

CREATE TABLE rol (
    id_rol SERIAL PRIMARY KEY,
    nombre_rol VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE usuario (
    id_usuario SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    id_rol INT NOT NULL,
    CONSTRAINT fk_usuario_rol FOREIGN KEY (id_rol) REFERENCES rol(id_rol) ON DELETE RESTRICT
);

CREATE TABLE cliente (
    id_cliente SERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    nif VARCHAR(20) NOT NULL UNIQUE,
    telefono VARCHAR(20) NOT NULL,
    direccion VARCHAR(250) NOT NULL,
    email VARCHAR(150)
);

CREATE TABLE articulo (
    id_articulo SERIAL PRIMARY KEY,
    codigo VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(200) NOT NULL,
    precio_unitario NUMERIC(10, 2) NOT NULL DEFAULT 0.00,
    stock_actual INT NOT NULL DEFAULT 0
);

CREATE TABLE parte_trabajo (
    id_parte SERIAL PRIMARY KEY,
    fecha_emision TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    descripcion_averia TEXT NOT NULL,
    estado VARCHAR(30) NOT NULL DEFAULT 'PENDIENTE',
    firma_cliente TEXT,
    id_cliente INT NOT NULL,
    id_usuario_tecnico INT NOT NULL,
    CONSTRAINT fk_parte_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente) ON DELETE CASCADE,
    CONSTRAINT fk_parte_tecnico FOREIGN KEY (id_usuario_tecnico) REFERENCES usuario(id_usuario) ON DELETE RESTRICT
);

CREATE TABLE linea_parte (
    id_linea SERIAL PRIMARY KEY,
    id_parte INT NOT NULL,
    id_articulo INT NOT NULL,
    unidades INT NOT NULL DEFAULT 1,
    horas_empleadas NUMERIC(5, 2) NOT NULL DEFAULT 0.00,
    CONSTRAINT fk_linea_parte FOREIGN KEY (id_parte) REFERENCES parte_trabajo(id_parte) ON DELETE CASCADE,
    CONSTRAINT fk_linea_articulo FOREIGN KEY (id_articulo) REFERENCES articulo(id_articulo) ON DELETE RESTRICT
);