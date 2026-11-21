package com.fixtrack.fixtrack_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "articulo")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Articulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArticulo;

    @Column(nullable = false, unique = true, length = 50)
    private String codigo;

    @Column(nullable = false, length = 200)
    private String descripcion;

    @Column(nullable = false, columnDefinition = "NUMERIC(10, 2) DEFAULT 0.00")
    private BigDecimal precioUnitario = BigDecimal.ZERO;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int stockActual = 0;
}
