package utp.ac.pa.domain;

import java.util.List;

public class Administrador extends Usuario {

    public Administrador(String id, String nombre, String correo, String password) {
        super(id, nombre, correo, password, true);
    }

    @Override
    public Rol getRol() {
        return Rol.ADMINISTRADOR;
    }

    public void validarUsuario(Usuario usuario) {
        usuario.marcarComoValidado();
    }

    public void aprobarPrestamo(Prestamo prestamo) {
        prestamo.aprobar(this);
    }

    public void rechazarPrestamo(Prestamo prestamo) {
        prestamo.rechazar(this);
    }

    public Reporte generarReporteDisponibilidad(List<Laboratorio> labs) {
        return Reporte.deDisponibilidad(this, labs);
    }

    public Reporte generarReporteHistorial(Laboratorio lab, List<Historial> registros) {
        return Reporte.deHistorial(this, lab, registros);
    }
}
