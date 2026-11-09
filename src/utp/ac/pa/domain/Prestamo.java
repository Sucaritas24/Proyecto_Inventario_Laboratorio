package utp.ac.pa.domain;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class Prestamo {
    private static final AtomicInteger SECUENCIA = new AtomicInteger(1);

    private int id;
    private LocalDate fechaSolicitud;
    private LocalDate fechaLimite;
    private LocalDate fechaDevolucion;
    private EstadoPrestamo estado;

    private final Usuario solicitante;
    private final Equipo equipo;
    private Administrador revisadoPor;

    Prestamo(Usuario solicitante, Equipo equipo, LocalDate fechaSolicitud, LocalDate fechaLimite) {
        if (fechaLimite.isBefore(fechaSolicitud)) {
            throw new IllegalArgumentException("La fecha límite no puede ser anterior a la solicitud.");
        }
        this.id = SECUENCIA.getAndIncrement();
        this.solicitante = solicitante;
        this.equipo = equipo;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaLimite = fechaLimite;
        this.estado = EstadoPrestamo.SOLICITADO;
    }

    void aprobar(Administrador admin) {
        if (estado != EstadoPrestamo.SOLICITADO) {
            throw new IllegalStateException("Solo se puede aprobar un préstamo en estado SOLICITADO.");
        }
        equipo.marcarComoPrestado();
        this.revisadoPor = admin;
        this.estado = EstadoPrestamo.APROBADO;
    }

    void rechazar(Administrador admin) {
        if (estado != EstadoPrestamo.SOLICITADO) {
            throw new IllegalStateException("Solo se puede rechazar un préstamo en estado SOLICITADO.");
        }
        this.revisadoPor = admin;
        this.estado = EstadoPrestamo.RECHAZADO;
    }

    public void registrarDevolucion(LocalDate fecha) {
        if (estado != EstadoPrestamo.APROBADO) {
            throw new IllegalStateException("Solo se puede devolver un préstamo APROBADO.");
        }
        equipo.marcarComoDevuelto();
        this.fechaDevolucion = fecha;
        this.estado = EstadoPrestamo.DEVUELTO;
    }

    public boolean estaVencido(LocalDate hoy) {
        return estado == EstadoPrestamo.APROBADO && hoy.isAfter(fechaLimite);
    }

    public int getId() { return id; }
    public LocalDate getFechaSolicitud() { return fechaSolicitud; }
    public LocalDate getFechaLimite() { return fechaLimite; }
    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public EstadoPrestamo getEstado() { return estado; }
    public Usuario getSolicitante() { return solicitante; }
    public Equipo getEquipo() { return equipo; }
    public Administrador getRevisadoPor() { return revisadoPor; }
}
