package utp.ac.pa.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Compra {
    private static final AtomicInteger SECUENCIA = new AtomicInteger(1);

    private int id;
    private LocalDate fecha;
    private BigDecimal montoTotal = BigDecimal.ZERO;

    private final Proveedor proveedor;
    private final List<Equipo> equipos = new ArrayList<>();

    public Compra(Proveedor proveedor, LocalDate fecha) {
        this.id = SECUENCIA.getAndIncrement();
        this.proveedor = proveedor;
        this.fecha = fecha;
    }

    public void agregarEquipo(Equipo equipo, BigDecimal costo) {
        if (costo.signum() < 0) {
            throw new IllegalArgumentException("El costo no puede ser negativo.");
        }
        equipos.add(equipo);
        montoTotal = montoTotal.add(costo);
    }

    public int getId() { return id; }
    public LocalDate getFecha() { return fecha; }
    public BigDecimal getMontoTotal() { return montoTotal; }
    public Proveedor getProveedor() { return proveedor; }
    public List<Equipo> getEquipos() { return equipos; }
}
