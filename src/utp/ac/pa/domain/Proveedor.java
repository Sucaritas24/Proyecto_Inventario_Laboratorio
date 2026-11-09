package utp.ac.pa.domain;

import java.util.concurrent.atomic.AtomicInteger;

public class Proveedor {
    private static final AtomicInteger SECUENCIA = new AtomicInteger(1);

    private int id;
    private String nombre;
    private String telefono;
    private String correo;
    private String direccion;

    public Proveedor(String nombre, String telefono, String correo, String direccion) {
        this.id = SECUENCIA.getAndIncrement();
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getTelefono() { return telefono; }
    public String getCorreo() { return correo; }
    public String getDireccion() { return direccion; }
}
