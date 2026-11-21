package com.fixtrack.fixtrack_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "linea_parte")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class LineaParte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLinea;

    @ManyToOne
    @JoinColumn(name = "id_parte", nullable = false)
    private ParteTrabajo parte;

    @ManyToOne
    @JoinColumn(name = "id_articulo", nullable = false)
    private Articulo articulo;

    @Column(nullable = false,  columnDefinition = "DEFAULT 1")
    private int unidades = 0;

    @Column(nullable = false, columnDefinition = "NUMERIC(5, 2) DEFAULT 0.00")
    private BigDecimal horasEmpleadas = BigDecimal.ZERO;
}
