package com.universidad.reportedanos.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.regex.Pattern;

// Value Object: email con validacion de formato
@Embeddable
public record Email(
    @Column(name = "email", nullable = false, length = 150)
    String valor
) {
    private static final Pattern PATRON_EMAIL =
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public Email {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El email no puede estar vacio");
        }
        if (!PATRON_EMAIL.matcher(valor).matches()) {
            throw new IllegalArgumentException("Formato de email invalido: " + valor);
        }
    }
}
