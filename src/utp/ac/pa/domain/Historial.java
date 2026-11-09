package utp.ac.pa.domain;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class Historial {
    private static final AtomicInteger SECUENCIA = new AtomicInteger(1);

    private int id;
    private LocalDateTime fechaHora;
    private TipoAccion accion;
    private String descripcion;

    private final Usuario actor;
    private final Equipo equipo;
    private Laboratorio laboratorio;

    public Historial(TipoAccion accion, Usuario actor, Equipo equipo, String descripcion) {
        this.id = SECUENCIA.getAndIncrement();
        this.fechaHora = LocalDateTime.now();
        this.accion = accion;
        this.actor = actor;
        this.equipo = equipo;
        this.descripcion = descripcion;
    }

    public boolean perteneceA(Laboratorio lab) {
        if (laboratorio != null) {
            return laboratorio == lab;
        }
        return equipo != null && lab.getEquipos().contains(equipo);
    }

    public int getId() { return id; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public TipoAccion getAccion() { return accion; }
    public String getDescripcion() { return descripcion; }
    public Usuario getActor() { return actor; }
    public Equipo getEquipo() { return equipo; }
    public Laboratorio getLaboratorio() { return laboratorio; }
    public void setLaboratorio(Laboratorio laboratorio) { this.laboratorio = laboratorio; }

    @Override
    public String toString() {
        return fechaHora + " | " + accion + " | " + (actor != null ? actor.getNombre() : "-")
                + " | " + descripcion;
    }
}
