package CosasDeHabitaciones;

import java.time.LocalDate;

public class Reserva {

    private  int idReserva;
    private String dniCliente;
    private int numeroHabitacion;
    private LocalDate fechaCheckIn;
    private LocalDate fechaCheckOut;
    private boolean estadoReserva;

    public Reserva(int idReserva, String dniCliente, int numeroHabitacion, LocalDate fechaCheckIn, LocalDate getFechaCheckOut, boolean estadoReserva) {
        this.idReserva = idReserva;
        this.dniCliente = dniCliente;
        this.numeroHabitacion = numeroHabitacion;
        this.fechaCheckIn = fechaCheckIn;
        this.fechaCheckOut = getFechaCheckOut;
        this.estadoReserva = estadoReserva;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public int getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public void setNumeroHabitacion(int numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
    }

    public LocalDate getFechaCheckIn() {
        return fechaCheckIn;
    }

    public void setFechaCheckIn(LocalDate fechaCheckIn) {
        this.fechaCheckIn = fechaCheckIn;
    }

    public LocalDate getGetFechaCheckOut() {
        return fechaCheckOut;
    }

    public void setGetFechaCheckOut(LocalDate getFechaCheckOut) {
        this.fechaCheckOut = getFechaCheckOut;
    }

    public boolean isEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(boolean estadoReserva) {
        this.estadoReserva = estadoReserva;
    }
}
