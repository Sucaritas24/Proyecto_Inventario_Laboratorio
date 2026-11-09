package utp.ac.pa.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Equipo {
    private String codigo;
    private String nombre;
    private String marca;
    private EstadoEquipo estado;

    private Categoria categoria;
    private final List<Mantenimiento> mantenimientos = new ArrayList<>();

    public Equipo(String codigo, String nombre, String marca, Categoria categoria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.marca = marca;
        this.categoria = categoria;
        this.estado = EstadoEquipo.DISPONIBLE;
    }

    public boolean estaDisponible() {
        return estado == EstadoEquipo.DISPONIBLE;
    }

    public Mantenimiento programarMantenimiento(TipoMantenimiento tipo, LocalDate fecha, String descripcion) {
        if (estado == EstadoEquipo.DADO_DE_BAJA) {
            throw new IllegalStateException("El equipo está dado de baja.");
        }
        Mantenimiento m = new Mantenimiento(tipo, fecha, descripcion);
        mantenimientos.add(m);
        return m;
    }

    public List<Mantenimiento> getMantenimientosPendientes(LocalDate hoy) {
        List<Mantenimiento> pendientes = new ArrayList<>();
        for (Mantenimiento m : mantenimientos) {
            if (m.estaPendiente(hoy)) {
                pendientes.add(m);
            }
        }
        return pendientes;
    }

    public void darDeBaja() {
        if (estado == EstadoEquipo.PRESTADO) {
            throw new IllegalStateException("No se puede dar de baja un equipo prestado.");
        }
        estado = EstadoEquipo.DADO_DE_BAJA;
    }

    void marcarComoPrestado() {
        if (estado != EstadoEquipo.DISPONIBLE) {
            throw new IllegalStateException("El equipo no está disponible para préstamo.");
        }
        estado = EstadoEquipo.PRESTADO;
    }

    void marcarComoDevuelto() {
        if (estado != EstadoEquipo.PRESTADO) {
            throw new IllegalStateException("El equipo no estaba prestado.");
        }
        estado = EstadoEquipo.DISPONIBLE;
    }

    void iniciarMantenimiento() {
        if (estado != EstadoEquipo.DISPONIBLE) {
            throw new IllegalStateException("Solo un equipo disponible puede entrar a mantenimiento.");
        }
        estado = EstadoEquipo.EN_MANTENIMIENTO;
    }

    void finalizarMantenimiento() {
        if (estado != EstadoEquipo.EN_MANTENIMIENTO) {
            throw new IllegalStateException("El equipo no está en mantenimiento.");
        }
        estado = EstadoEquipo.DISPONIBLE;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getMarca() { return marca; }
    public EstadoEquipo getEstado() { return estado; }
    public Categoria getCategoria() { return categoria; }
    public List<Mantenimiento> getMantenimientos() { return mantenimientos; }

    @Override
    public String toString() {
        return codigo + " - " + nombre + " [" + estado + "]";
    }
}
