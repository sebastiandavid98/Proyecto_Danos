package com.universidad.reportedanos.modelo;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    // Email como value object incrustado
    @Embedded
    private Email email;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private Rol rol;

    @Column(name = "activo", nullable = false)
    private boolean activo;

    // Reportes creados por este usuario
    @OneToMany(mappedBy = "creador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reporte> reportes = new ArrayList<>();

    // JPA lo necesita
    protected Usuario() {}

    public Usuario(String nombre, Email email, Rol rol) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (email == null) {
            throw new IllegalArgumentException("El email es obligatorio");
        }
        if (rol == null) {
            throw new IllegalArgumentException("El rol es obligatorio");
        }
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
        this.activo = true;
    }

    // Desactiva al usuario
    public void desactivar() {
        this.activo = false;
    }

    public void reactivar() {
        this.activo = true;
    }

    // Verifica si puede crear reportes
    public boolean puedeReportarDanos() {
        return this.activo && (this.rol == Rol.ESTUDIANTE_DOCENTE || this.rol == Rol.PERSONAL_ENCARGADO);
    }

    // Verifica si puede gestionar reportes
    public boolean puedeGestionarReportes() {
        return this.activo && (this.rol == Rol.PERSONAL_ENCARGADO || this.rol == Rol.ADMINISTRADOR);
    }

    // Agrega un reporte al usuario
    public void agregarReporte(Reporte reporte) {
        if (!puedeReportarDanos()) {
            throw new IllegalStateException("El usuario no puede crear reportes");
        }
        this.reportes.add(reporte);
    }

    public UUID getId() { return id; }
    public String getNombre() { return nombre; }
    public Email getEmail() { return email; }
    public Rol getRol() { return rol; }
    public boolean isActivo() { return activo; }

    // Vista no modificable para evitar cambios externos
    public List<Reporte> getReportes() {
        return Collections.unmodifiableList(reportes);
    }
}
