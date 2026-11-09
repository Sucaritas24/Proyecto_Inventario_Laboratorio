package utp.ac.pa.domain;

import java.util.ArrayList;
import java.util.List;

public class Laboratorio {
    private String codigo;
    private String nombre;
    private String ubicacion;
    private final List<Equipo> equipos = new ArrayList<>();

    public Laboratorio(String codigo, String nombre, String ubicacion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
    }

    public void agregarEquipo(Equipo equipo) {
        if (!equipos.contains(equipo)) {
            equipos.add(equipo);
        }
    }

    public void retirarEquipo(Equipo equipo) {
        equipos.remove(equipo);
    }

    public List<Equipo> getEquiposDisponibles() {
        List<Equipo> disponibles = new ArrayList<>();
        for (Equipo e : equipos) {
            if (e.estaDisponible()) {
                disponibles.add(e);
            }
        }
        return disponibles;
    }

    public int contarPorEstado(EstadoEquipo estado) {
        int total = 0;
        for (Equipo e : equipos) {
            if (e.getEstado() == estado) {
                total++;
            }
        }
        return total;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getUbicacion() { return ubicacion; }
    public List<Equipo> getEquipos() { return equipos; }
}
