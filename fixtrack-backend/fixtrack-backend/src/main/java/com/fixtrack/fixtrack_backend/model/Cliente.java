package com.fixtrack.fixtrack_backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @Column(nullable = false, length = 150)
    private String nombreCliente;

    @Column(nullable = false, unique = true, length = 20)
    private String nifCliente;

    @Column(nullable = false, length = 20)
    private String telefonoCliente;

    @Column(nullable = false, length = 250)
    private String direccionCliente;

    @Column(length = 150)
    private String emailCliente;
}
