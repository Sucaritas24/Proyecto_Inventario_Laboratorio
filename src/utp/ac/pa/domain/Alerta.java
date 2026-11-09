package utp.ac.pa.domain;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class Alerta {
    private static final AtomicInteger SECUENCIA = new AtomicInteger(1);

    private int id;
    private String mensaje;
    private PrioridadAlerta prioridad;
    private LocalDateTime fechaCreacion;
    private boolean leida;

    Alerta(String mensaje, PrioridadAlerta prioridad) {
        this.id = SECUENCIA.getAndIncrement();
        this.mensaje = mensaje;
        this.prioridad = prioridad;
        this.fechaCreacion = LocalDateTime.now();
        this.leida = false;
    }

    public void marcarComoLeida() {
        this.leida = true;
    }

    public int getId() { return id; }
    public String getMensaje() { return mensaje; }
    public PrioridadAlerta getPrioridad() { return prioridad; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public boolean isLeida() { return leida; }
}
