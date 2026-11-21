package com.fixtrack.fixtrack_backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "parte_trabajo")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ParteTrabajo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idParte;

    @Column(nullable = false,  columnDefinition = "DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaEmision;

    @Column(nullable = false)
    private String descripcionAveria;

    @Column(nullable = false, length = 30, columnDefinition = "VARCHAR(30) DEFAULT 'PENDIENTE'")
    private String estado;

    private String firmaCliente;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_usuario_tecnico", nullable = false)
    private Usuario usuario;
}
