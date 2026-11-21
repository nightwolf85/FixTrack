package com.fixtrack.fixtrack_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @GetMapping
    public ResponseEntity<?> listarClientes() {
        // Aquí devolveremos  la lista de clientes desde el repositorio JPA
        return ResponseEntity.ok("Lista de clientes protegida por JWT funcionando correctamente.");
    }

    @PostMapping
    public ResponseEntity<?> crearCliente() {
        // Lógica para dar de alta un cliente nuevo (para el rol administrativo)
        return ResponseEntity.ok("Cliente creado con éxito.");
    }
}
