package com.universidad.reportedanos.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

// Value Object: donde ocurrio el dano
@Embeddable
public record Ubicacion(
    @Column(name = "edificio", nullable = false, length = 100)
    String edificio,
    @Column(name = "espacio", nullable = false, length = 100)
    String espacio
) {
    public Ubicacion {
        if (edificio == null || edificio.isBlank()) {
            throw new IllegalArgumentException("El edificio no puede estar vacio");
        }
        if (espacio == null || espacio.isBlank()) {
            throw new IllegalArgumentException("El espacio no puede estar vacio");
        }
    }

    // Representacion legible del lugar
    public String descripcion() {
        return edificio + " - " + espacio;
    }
}
