package utp.ac.pa.domain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Base64;

public class Usuario {
    private String id;
    private String nombre;
    private String correo;
    private String sal;
    private String passwordHash;
    private boolean validado;

    public Usuario(String id, String nombre, String correo, String password) {
        this(id, nombre, correo, password, false);
    }

    protected Usuario(String id, String nombre, String correo, String password, boolean validado) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.sal = generarSal();
        this.passwordHash = hash(password, this.sal);
        this.validado = validado;
    }

    public boolean autenticar(String password) {
        return passwordHash.equals(hash(password, sal));
    }

    public Prestamo solicitarPrestamo(Equipo equipo, LocalDate fechaLimite) {
        if (!validado) {
            throw new IllegalStateException("El usuario aún no ha sido validado por un administrador.");
        }
        if (!equipo.estaDisponible()) {
            throw new IllegalStateException("El equipo " + equipo.getCodigo() + " no está disponible.");
        }
        return new Prestamo(this, equipo, LocalDate.now(), fechaLimite);
    }

    public Rol getRol() {
        return Rol.USUARIO;
    }

    void marcarComoValidado() {
        this.validado = true;
    }

    private static String generarSal() {
        byte[] bytes = new byte[16];
        new SecureRandom().nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }

    private static String hash(String password, String sal) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest((sal + password).getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 no disponible", e);
        }
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public boolean isValidado() { return validado; }

    @Override
    public String toString() {
        return getRol() + " " + nombre + " (" + id + ")";
    }
}
