package utp.ac.pa.domain;

import java.util.concurrent.atomic.AtomicInteger;

public class Categoria {
    private static final AtomicInteger SECUENCIA = new AtomicInteger(1);

    private int id;
    private String nombre;
    private String descripcion;

    public Categoria(String nombre, String descripcion) {
        this.id = SECUENCIA.getAndIncrement();
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
}
