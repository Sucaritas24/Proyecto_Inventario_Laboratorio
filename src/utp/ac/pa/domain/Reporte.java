package utp.ac.pa.domain;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Reporte implements Exportable {
    private static final AtomicInteger SECUENCIA = new AtomicInteger(1);

    private int id;
    private TipoReporte tipo;
    private LocalDateTime fechaGeneracion;
    private String contenido;

    private final Administrador generadoPor;

    private Reporte(TipoReporte tipo, Administrador generadoPor, String contenido) {
        this.id = SECUENCIA.getAndIncrement();
        this.tipo = tipo;
        this.generadoPor = generadoPor;
        this.fechaGeneracion = LocalDateTime.now();
        this.contenido = contenido;
    }

    static Reporte deDisponibilidad(Administrador admin, List<Laboratorio> labs) {
        StringBuilder sb = new StringBuilder("REPORTE DE DISPONIBILIDAD\n");
        for (Laboratorio lab : labs) {
            sb.append(lab.getNombre()).append(": ");
            for (EstadoEquipo estado : EstadoEquipo.values()) {
                sb.append(estado).append("=").append(lab.contarPorEstado(estado)).append(" ");
            }
            sb.append("\n");
        }
        return new Reporte(TipoReporte.DISPONIBILIDAD, admin, sb.toString());
    }

    static Reporte deHistorial(Administrador admin, Laboratorio lab, List<Historial> registros) {
        StringBuilder sb = new StringBuilder("HISTORIAL DEL LABORATORIO " + lab.getNombre() + "\n");
        for (Historial h : registros) {
            if (h.perteneceA(lab)) {
                sb.append(h).append("\n");
            }
        }
        return new Reporte(TipoReporte.HISTORIAL_LABORATORIO, admin, sb.toString());
    }

    @Override
    public void exportar(Path destino) {
        try {
            Files.writeString(destino, contenido);
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo exportar el reporte", e);
        }
    }

    public int getId() { return id; }
    public TipoReporte getTipo() { return tipo; }
    public LocalDateTime getFechaGeneracion() { return fechaGeneracion; }
    public String getContenido() { return contenido; }
    public Administrador getGeneradoPor() { return generadoPor; }
}
