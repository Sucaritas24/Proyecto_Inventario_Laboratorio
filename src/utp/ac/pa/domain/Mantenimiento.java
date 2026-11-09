package utp.ac.pa.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class Mantenimiento {
    private static final AtomicInteger SECUENCIA = new AtomicInteger(1);

    private int id;
    private TipoMantenimiento tipo;
    private LocalDate fechaProgramada;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String descripcion;
    private BigDecimal costo = BigDecimal.ZERO;
    private EstadoMantenimiento estado;

    private final List<Alerta> alertas = new ArrayList<>();

    Mantenimiento(TipoMantenimiento tipo, LocalDate fechaProgramada, String descripcion) {
        this.id = SECUENCIA.getAndIncrement();
        this.tipo = tipo;
        this.fechaProgramada = fechaProgramada;
        this.descripcion = descripcion;
        this.estado = EstadoMantenimiento.PROGRAMADO;
    }

    public void iniciar(LocalDate fecha) {
        if (estado != EstadoMantenimiento.PROGRAMADO) {
            throw new IllegalStateException("El mantenimiento no está programado.");
        }
        this.fechaInicio = fecha;
        this.estado = EstadoMantenimiento.EN_CURSO;
    }

    public void finalizar(LocalDate fecha, BigDecimal costo) {
        if (estado != EstadoMantenimiento.EN_CURSO) {
            throw new IllegalStateException("El mantenimiento no está en curso.");
        }
        this.fechaFin = fecha;
        this.costo = costo;
        this.estado = EstadoMantenimiento.FINALIZADO;
    }

    public boolean estaPendiente(LocalDate hoy) {
        return estado == EstadoMantenimiento.PROGRAMADO && !fechaProgramada.isAfter(hoy);
    }

    public Optional<Alerta> generarAlertaSiPendiente(LocalDate hoy) {
        if (!estaPendiente(hoy)) {
            return Optional.empty();
        }
        PrioridadAlerta prioridad = (tipo == TipoMantenimiento.CORRECTIVO)
                ? PrioridadAlerta.ALTA : PrioridadAlerta.MEDIA;
        Alerta alerta = new Alerta("Mantenimiento " + tipo + " pendiente desde " + fechaProgramada
                + ": " + descripcion, prioridad);
        alertas.add(alerta);
        return Optional.of(alerta);
    }

    public int getId() { return id; }
    public TipoMantenimiento getTipo() { return tipo; }
    public LocalDate getFechaProgramada() { return fechaProgramada; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public String getDescripcion() { return descripcion; }
    public BigDecimal getCosto() { return costo; }
    public EstadoMantenimiento getEstado() { return estado; }
    public List<Alerta> getAlertas() { return alertas; }
}
