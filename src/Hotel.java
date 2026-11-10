import CosasDeHabitaciones.EEstadoHabitacion;
import CosasDeHabitaciones.Habitacion;
import CosasDeHabitaciones.Reserva;
import CosasDePersonas.Cliente;
import CosasDePersonas.Staff;
import Excepciones.EClienteNoExiste;
import Excepciones.EFechasNoValidas;
import Excepciones.EHabitacionNoDisponible;
import Excepciones.EHabitacionNoExiste;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Hotel {

    private String nombreHotel;
    private String direccionHotel;
    private HashMap<Integer, Habitacion> listaHabitaciones; // numero de habitacion(Key) - info habitacion(data)
    private HashSet<Staff> listaStaff;
    private HashSet<Cliente> listaClientes;
    private ArrayList<Reserva> listaReservas;

    public Hotel(String nombreHotel, String direccionHotel) {
        this.nombreHotel = nombreHotel;
        this.direccionHotel = direccionHotel;
        this.listaHabitaciones = new HashMap<Integer, Habitacion>();
        this.listaStaff = new HashSet<Staff>();
        this.listaClientes = new HashSet<Cliente>();
        this.listaReservas = new ArrayList<Reserva>();
    }

    public String registrarCliente(Cliente clienteDado){
        if (!listaClientes.add(clienteDado)){
            return "El cliente ya existe en la lista o no se pudo resistrar.\n";
        }
        return "El cliente se ha registrado con exito en la lista de clientes.\n";
    }

    public String registrarStaff(Staff staffDado){

        if (!listaStaff.add(staffDado)){
            return "El staff ya existe en la lista o no se pudo resistrar.\n";
        }
        return "El stadd se ha registrado con exito en la lista de empleados.\n";
    }

    public String registrarHabitacion(Integer numeroHabitacion, Habitacion habitacionDada){
        if (listaHabitaciones.containsKey(numeroHabitacion)){
            return "La habitacion ya existe en la lista o no se pudo resistrar.\n";
        }
        listaHabitaciones.put(numeroHabitacion, habitacionDada);
        return "La habitacion se ha registrado con exito en la lista de habitaciones.\n";
    }

    public Cliente obtenerClientePorDni(String dni) throws EClienteNoExiste {
        for (Cliente clienteTemp : listaClientes) {
            if (clienteTemp.getDni().equals(dni))
                return clienteTemp; // Lo encontró, devuelve el objeto
        }
        throw new EClienteNoExiste();
    }

    public Habitacion obtenerHabitacionPorId(int numeroHabitacion) throws EHabitacionNoExiste {
        Habitacion habitacionTemp = listaHabitaciones.get(numeroHabitacion);
        if (habitacionTemp == null) {
            throw new EHabitacionNoExiste("No existe la habitación número: " + numeroHabitacion);
        }
        return habitacionTemp;
    }


    public void crearReserva(LocalDate fechaCheckIn, LocalDate fechaCheckOut, String dniCliente, int numeroHabitacion){

        Cliente clienteTemp = obtenerClientePorDni(dniCliente);
        Habitacion habitacionTemp = obtenerHabitacionPorId(numeroHabitacion);

        if (habitacionTemp.getEstadoHabitacion() != EEstadoHabitacion.DISPONIBLE){
            throw new EHabitacionNoDisponible("La habitacion no se encuentra disponible: "+habitacionTemp.getEstadoHabitacion().getDescripcion());
        }

        if (fechaCheckOut.isBefore(fechaCheckIn) || fechaCheckOut.isEqual(fechaCheckIn)){
            throw new EFechasNoValidas("ERROR: La fecha de Check Out tiene que ser posterior a la fecha de Check In");
        }
        boolean fechaValida = checkDisponibilidadFecha(fechaCheckIn, fechaCheckOut, numeroHabitacion);
    }

    public boolean checkDisponibilidadFecha(LocalDate fechaCheckIn, LocalDate fechaCheckOut, int numeroHabitacion){
        //Este metodo se fija si la fecha de reserva tiene conflicto con cualquier reserva ya existente en la listaReservas
    }


    public String clienteHaceReserva(String dni){
        Cliente clienteTemp = obtenerClientePorDni(dni);
        return clienteTemp.hacerReserva();
    }
}
