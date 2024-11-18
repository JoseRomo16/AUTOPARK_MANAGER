package model;

import java.time.LocalDateTime;

public class Ticket {
    private int idTicket;
    private LocalDateTime horaIngreso;
    private LocalDateTime horaSalida;
    private double tarifaTotal;
    private int idAutomovil;
    private int idEspacioParqueo;

    // Constructor con parámetros para inicializar algunos atributos
    public Ticket(LocalDateTime horaIngreso, int idAutomovil, int idEspacioParqueo) {
        this.horaIngreso = horaIngreso;
        this.idAutomovil = idAutomovil;
        this.idEspacioParqueo = idEspacioParqueo;
    }

    // Constructor vacío (opcional)
    public Ticket() {}

    // Getters y Setters

    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    public LocalDateTime getHoraIngreso() {
        return horaIngreso;
    }

    public void setHoraIngreso(LocalDateTime horaIngreso) {
        this.horaIngreso = horaIngreso;
    }

    public LocalDateTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalDateTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public double getTarifaTotal() {
        return tarifaTotal;
    }

    public void setTarifaTotal(double tarifaTotal) {
        this.tarifaTotal = tarifaTotal;
    }

    public int getIdAutomovil() {
        return idAutomovil;
    }

    public void setIdAutomovil(int idAutomovil) {
        this.idAutomovil = idAutomovil;
    }

    public int getIdEspacioParqueo() {
        return idEspacioParqueo;
    }

    public void setIdEspacioParqueo(int idEspacioParqueo) {
        this.idEspacioParqueo = idEspacioParqueo;
    }
}

