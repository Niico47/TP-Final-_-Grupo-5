import CosasDeHabitaciones.EEstadoHabitacion;
import CosasDeHabitaciones.Habitacion;
import CosasDeHabitaciones.Reserva;
import CosasDePersonas.Cliente;
import CosasDePersonas.Staff;
import Excepciones.*;

import java.time.LocalDate;
import java.time.YearMonth;
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


    public Reserva crearReserva(LocalDate fechaCheckIn, LocalDate fechaCheckOut, String dniCliente, int numeroHabitacion){

        Cliente clienteTemp = obtenerClientePorDni(dniCliente); //Se verifica que exista el cliente, sino tira una exepcion
        Habitacion habitacionTemp = obtenerHabitacionPorId(numeroHabitacion); //Se verifica que exista la habitacion, sino tira una exepcion

        //Se checkea el estado de la habitacion
        if (habitacionTemp.getEstadoHabitacion() != EEstadoHabitacion.DISPONIBLE){
            throw new EHabitacionNoDisponible("La habitacion no se encuentra disponible: "+habitacionTemp.getEstadoHabitacion().getDescripcion());
        }

        //Se checkea si el usuario es tonto
        if (fechaCheckOut.isBefore(fechaCheckIn) || fechaCheckOut.isEqual(fechaCheckIn)){
            throw new EFechasNoValidas("ERROR: La fecha de Check Out tiene que ser posterior a la fecha de Check In");
        }

        //Se checkea que las fechas dadas no entren en conflicto con otras reservas para la habitacion dada
        checkDisponibilidadFecha(fechaCheckIn, fechaCheckOut, numeroHabitacion);

        Reserva reservaTemp = new Reserva(dniCliente, numeroHabitacion, fechaCheckIn, fechaCheckOut);
        listaReservas.add(reservaTemp);
        return reservaTemp;
    }

    public void checkDisponibilidadFecha(LocalDate fechaCheckIn, LocalDate fechaCheckOut, int numeroHabitacion) throws EFechaNoDisponible {
        //Se recorren todas las reservas guardadas en el hotel
        for (Reserva reservaExistente : listaReservas) {

            // Se filtran las reservas que correspondan a la habitacion dada
            if (reservaExistente.getNumeroHabitacion() == numeroHabitacion) {

                // Si entra al 'if', hay conflicto de fechas
                if (fechaCheckIn.isBefore(reservaExistente.getGetFechaCheckOut()) && fechaCheckOut.isAfter(reservaExistente.getFechaCheckIn())) {
                    throw new EFechaNoDisponible
                            ("La habitación N° " + numeroHabitacion + " ya se encuentra reservada del " +
                                    reservaExistente.getFechaCheckIn() + " al " + reservaExistente.getGetFechaCheckOut());
                }
            }
        }
        // Si se llega aca sin lanzar exepcion la fecha esta libre.
    }



    //--------------------------------------------------------------------------------------------------------------------------------------
    //--------------------------- CREACION Y MUESTRA DE CALENDARIO -------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------------------------------------
    public void mostrarCalendarioHabitacion (int anio, int mes, int numeroHabitacion) {

        YearMonth yearMonth = YearMonth.of(anio, mes); //Agarro el mes deseado
        int totalDiasMes = yearMonth.lengthOfMonth(); //Guardo cuantos dias tiene el mes
        Habitacion habitacionTemp = obtenerHabitacionPorId(numeroHabitacion); //Obtengo la habitacion
        EEstadoHabitacion estado = habitacionTemp.getEstadoHabitacion(); //Obtengo el estado de la habitacion para usarlo en obtenerColorDia

        // que dia de la semana cae el dia 1 del mes (1 = Lunes, 7 = Domingo)
        int primerDiaSemana = yearMonth.atDay(1).getDayOfWeek().getValue();

        System.out.println("\n=== CALENDARIO: " + mes + "/" + anio + " (Habitacion " + numeroHabitacion + ") ===\n");
        System.out.println("  Lu   Ma   Mi   Ju   Vi   Sa   Do  ");

        // poner espacios iniciales antes del primer dia del mes
        for (int i = 1; i < primerDiaSemana; i++){
            System.out.print("     ");
        }

        // se recorre dia por dia el mes
        for (int dia = 1; dia <= totalDiasMes; dia++) {
            LocalDate fechaActual = yearMonth.atDay(dia);

            //dentro del bucle for se elije el color del dia segun disponibilidad
            String color = obtenerColorDia(fechaActual, numeroHabitacion, estado);

            // Esto me lo dio la IA, sirve para que los numeros queden alineados
            //EL reset sirve para volver al color original, ya que no lo hace solo
            System.out.printf("%s%4d %s", color, dia, Colores.RESET);

            // salto de linea cada vez que se lega al Domingo
            if ((dia + primerDiaSemana - 1) % 7 == 0) {
                System.out.println();
            }
        }
        System.out.println("\n========================================================");
        System.out.println(" ["
                        +Colores.GRIS+ "Gris" +Colores.RESET+ ": Dias que ya pasaron | "
                        + Colores.ROJO +"Rojo"+ Colores.RESET +": Reservado | "
                        + Colores.BLANCO +"Blanco"+ Colores.RESET +": Libre | "
                        + Colores.NARANJA +"Naranja"+ Colores.RESET +": No disponible]");
    }

    private String obtenerColorDia(LocalDate fechaDada, int numeroHabitacion, EEstadoHabitacion estado){

        //Devuelve gris si la fecha ya paso
        if (fechaDada.isBefore(LocalDate.now())) {
            return Colores.GRIS;
        }
        //Si esta reservada para esa fecha devuelve rojo
        if (estaReservadaEnFecha(numeroHabitacion, fechaDada)) {
            return Colores.ROJO;
        }

        //Verifica el estado de la habitacion, LIMPIEZA, REPARACION, DESINFECCION devuelve naranja.
        if (estado == EEstadoHabitacion.LIMPIEZA ||
                estado == EEstadoHabitacion.REPARACION ||
                estado == EEstadoHabitacion.DESINFECCION) {
            return Colores.NARANJA;
        }
        //Si llega a este return, la fecha esta libre y devuelve blanco
        return Colores.BLANCO;
    }

    public class Colores { //Colores para el calendario
        public static final String RESET = "\u001B[0m";
        public static final String GRIS = "\u001B[90m";
        public static final String ROJO = "\u001B[31m";
        public static final String BLANCO = "\u001B[97m";
        public static final String NARANJA = "\u001B[38;5;208m";
    }
    //--------------------------------------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------------------------------------

    //Revisa una fecha especifica de una habitacion para ver si esta reservada ese dia o no.
    private boolean estaReservadaEnFecha(int numeroHabitacion, LocalDate fecha) {
        for (Reserva reservaTemp : listaReservas) {
            if (reservaTemp.getNumeroHabitacion() == numeroHabitacion && reservaTemp.isEstadoReserva()) { //isEstadoReserva verifica que la reserva este activa y no se halla dado de baja

                if ((fecha.isEqual(reservaTemp.getFechaCheckIn()) || fecha.isAfter(reservaTemp.getFechaCheckIn())) && fecha.isBefore(reservaTemp.getGetFechaCheckOut())) {
                    return true; //si devuelve true es que si esta ocupada ese dia
                }
            }
        }
        return false; //si devuelve false esta libre
    }




    public String clienteHaceReserva(String dni){
        Cliente clienteTemp = obtenerClientePorDni(dni);
        return clienteTemp.hacerReserva();
    }


}
