import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Habitacion {
    int nroHabitacion;
    LocalDate checkIn;
    LocalDate checkOut;
    TipoHabitacion tipo;
    boolean disponibilidad;//ocupado o no;
    List<String> estado;

    public Habitacion(int nroHabitacion, LocalDate checkOut, LocalDate checkIn, boolean disponibilidad, TipoHabitacion tipo) {
        this.nroHabitacion = nroHabitacion;
        this.checkOut = checkOut;
        this.checkIn = checkIn;
        this.disponibilidad = disponibilidad;
        this.tipo = tipo;
        this.estado=new ArrayList<>();
    }

    public int getNroHabitacion() {
        return nroHabitacion;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
}
