package utp.ac.pa.sistema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import utp.ac.pa.domain.*;

public class Main {
    public static void main(String[] args) {
        Administrador admin = new Administrador("A-001", "Ana Admin", "ana@utp.ac.pa", "admin123");
        Usuario estudiante = new Usuario("U-001", "Norlan Gago", "norlan@utp.ac.pa", "clave123");

        Categoria cat = new Categoria("Medición", "Instrumentos de medición");
        Proveedor prov = new Proveedor("TecnoLab", "507-0000", "ventas@tecnolab.com", "Ciudad de Panamá");
        Equipo osciloscopio = new Equipo("EQ-001", "Osciloscopio", "Tektronix", cat);

        Compra compra = new Compra(prov, LocalDate.now());
        compra.agregarEquipo(osciloscopio, new BigDecimal("850.00"));

        Laboratorio lab = new Laboratorio("LAB-1", "Laboratorio de Electrónica", "Edificio 3");
        lab.agregarEquipo(osciloscopio);

        List<Historial> historial = new ArrayList<>();
        System.out.println("Autenticación correcta: " + estudiante.autenticar("clave123"));

        admin.validarUsuario(estudiante);
        historial.add(new Historial(TipoAccion.VALIDACION_USUARIO, admin, null, "Validó a " + estudiante.getNombre()));

        Prestamo prestamo = estudiante.solicitarPrestamo(osciloscopio, LocalDate.now().plusDays(7));
        historial.add(new Historial(TipoAccion.SOLICITUD_PRESTAMO, estudiante, osciloscopio, "Solicitud de préstamo"));
        admin.aprobarPrestamo(prestamo);
        System.out.println("Estado del equipo tras aprobar: " + osciloscopio.getEstado());

        prestamo.registrarDevolucion(LocalDate.now());
        System.out.println("Estado del préstamo: " + prestamo.getEstado()
                + " | equipo: " + osciloscopio.getEstado());

        Mantenimiento m = osciloscopio.programarMantenimiento(
                TipoMantenimiento.PREVENTIVO, LocalDate.now().minusDays(1), "Calibración anual");
        m.generarAlertaSiPendiente(LocalDate.now())
                .ifPresent(a -> System.out.println("Alerta [" + a.getPrioridad() + "]: " + a.getMensaje()));

        Reporte r = admin.generarReporteDisponibilidad(List.of(lab));
        System.out.println(r.getContenido());
        System.out.println(admin.generarReporteHistorial(lab, historial).getContenido());
    }
}
