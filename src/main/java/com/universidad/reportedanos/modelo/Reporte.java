package com.universidad.reportedanos.modelo;

import comportamentales.observer.GestorEventosReporte;
import comportamentales.state.EstadoPendiente;
import comportamentales.state.EstadoReporte;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reportes")
public class Reporte {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    // Quien creo el reporte
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "creador_id", nullable = false)
    private Usuario creador;

    // Quien esta atendiendo el reporte (puede ser null al inicio)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsable_id")
    private Usuario responsable;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private Categoria categoria;

    // Ubicacion como value object incrustado
    @Embedded
    private Ubicacion ubicacion;

    @Column(name = "descripcion", nullable = false, length = 500)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridad", nullable = false)
    private Prioridad prioridad;

    // Estado persistido con converter, igual que en Reserva
    @Convert(converter = EstadoReporteConverter.class)
    @Column(name = "estado", nullable = false)
    private EstadoReporte estado;

    @Column(name = "solucion", length = 500)
    private String solucion;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    // El gestor de eventos no se persiste
    @Transient
    private GestorEventosReporte gestorEventos;

    // JPA lo necesita
    protected Reporte() {}

    public Reporte(Usuario creador, Categoria categoria, Ubicacion ubicacion,
                   String descripcion, Prioridad prioridad) {
        if (creador == null) throw new IllegalArgumentException("El creador es obligatorio");
        if (!creador.puedeReportarDanos()) throw new IllegalStateException("El usuario no puede crear reportes");
        if (categoria == null) throw new IllegalArgumentException("La categoria es obligatoria");
        if (ubicacion == null) throw new IllegalArgumentException("La ubicacion es obligatoria");
        if (descripcion == null || descripcion.isBlank()) throw new IllegalArgumentException("La descripcion es obligatoria");
        if (prioridad == null) throw new IllegalArgumentException("La prioridad es obligatoria");

        this.id = UUID.randomUUID();
        this.creador = creador;
        this.categoria = categoria;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.fechaCreacion = LocalDateTime.now();

        // El reporte nace en estado pendiente
        this.estado = new EstadoPendiente();
    }

    // --- METODOS DE DELEGACION (el estado decide que es valido) ---

    public void iniciarRevision() {
        this.estado.iniciarRevision(this);
    }

    public void asignarResponsable(Usuario responsable) {
        if (responsable == null) throw new IllegalArgumentException("El responsable no puede ser nulo");
        if (!responsable.isActivo()) throw new IllegalStateException("El responsable no esta activo");
        this.responsable = responsable;
    }

    public void iniciarProceso() {
        this.estado.iniciarProceso(this);
    }

    public void registrarSolucion(String descripcionSolucion) {
        this.estado.registrarSolucion(this, descripcionSolucion);
    }

    public void cerrar() {
        this.estado.cerrar(this);
    }

    // Usado por los estados para cambiar el estado interno
    public void setEstado(EstadoReporte nuevoEstado) {
        this.estado = nuevoEstado;
    }

    // Usado por EstadoEnProceso al registrar la solucion
    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }

    public void setGestorEventos(GestorEventosReporte gestor) {
        this.gestorEventos = gestor;
    }

    public GestorEventosReporte getGestorEventos() {
        return gestorEventos;
    }

    public UUID getId() { return id; }
    public Usuario getCreador() { return creador; }
    public Usuario getResponsable() { return responsable; }
    public Categoria getCategoria() { return categoria; }
    public Ubicacion getUbicacion() { return ubicacion; }
    public String getDescripcion() { return descripcion; }
    public Prioridad getPrioridad() { return prioridad; }
    public EstadoReporte getEstado() { return estado; }
    public String getSolucion() { return solucion; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
}
