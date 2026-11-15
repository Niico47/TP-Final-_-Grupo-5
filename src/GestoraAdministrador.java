import CosasDeHabitaciones.Habitacion;
import CosasDePersonas.Cliente;

import java.time.LocalDate;

public class GestoraAdministrador {

    private Hotel hotel;

    public GestoraAdministrador(Hotel hotel){
        this.hotel=hotel;
    }

    //Interactua con el cliente
    public String registrarCliente(Cliente cliente){
        String mensaje= "";
        mensaje=" "+hotel.registrarCliente(cliente);
        return mensaje;
    }

    //Registrar Habitaciones
    public String registrarHabitaciones(Habitacion habitacion){
        String mensaje="";
        mensaje=hotel.registrarHabitacion(habitacion.getNumeroHabitacion(),habitacion);

        return mensaje;
    }

    //Consultar habitaciones disponibles
    public void consultarDisponibilidad(LocalDate checkIn, LocalDate checkOut){

        hotel.mostrarHabitacionesDisponibles(checkIn,checkOut);
    }

    //Consultar Calendario
    public void consultarCalendario(int anio,int mes,int numeroHabitacion){

        hotel.mostrarCalendarioHabitacion(anio,mes,numeroHabitacion);
    }

}
