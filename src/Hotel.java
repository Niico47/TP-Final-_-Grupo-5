import CosasDeHabitaciones.EEstadoHabitacion;
import CosasDeHabitaciones.Habitacion;
import CosasDeHabitaciones.Reserva;
import CosasDePersonas.Cliente;
import CosasDePersonas.Staff;
import Excepciones.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

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

    public Reserva obtenerReservaPorID(int idReserva){
        for (Reserva reserva:listaReservas){
            if (reserva.getIdReserva()==idReserva){
                return reserva;
            }
        }
        throw new ENoExisteReservaParaFecha("No existe la reserva con id: " +idReserva);
    }

    public double calcularCostoReserva(int idReserva){// Calcula el costo total de la estadía correspondiente a una reserva
        Reserva reservaTemp=obtenerReservaPorID(idReserva);
        Habitacion habitacionTemp=obtenerHabitacionPorId(reservaTemp.getNumeroHabitacion());
        int cantnoches=(int) ChronoUnit.DAYS.between(reservaTemp.getFechaCheckIn(),reservaTemp.getFechaCheckOut());// Calcula la diferencia exacta de noches entre la fecha de ingreso y la de salida
        return habitacionTemp.calcularCostoEstadia(cantnoches);
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

    public Staff obtenerStaffPorDni(String dni) throws EClienteNoExiste {
        for (Staff staffTemp : listaStaff) {
            if (staffTemp.getDni().equals(dni))
                return staffTemp; // Lo encontró, devuelve el objeto
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
                if (fechaCheckIn.isBefore(reservaExistente.getFechaCheckOut()) && fechaCheckOut.isAfter(reservaExistente.getFechaCheckIn())) {
                    throw new EFechaNoDisponible
                            ("La habitación N° " + numeroHabitacion + " ya se encuentra reservada del " +
                                    reservaExistente.getFechaCheckIn() + " al " + reservaExistente.getFechaCheckOut());
                }
            }
        }
        // Si se llega aca sin lanzar exepcion la fecha esta libre.
    }

    //Igual que arriba pero no tira exepciones y devueve un boolean
    public boolean checkDisponibilidadFechaSinExepciones(LocalDate fechaCheckIn, LocalDate fechaCheckOut, int numeroHabitacion) {
        //Se recorren todas las reservas guardadas en el hotel
        for (Reserva reservaExistente : listaReservas) {

            // Se filtran las reservas que correspondan a la habitacion dada
            if (reservaExistente.getNumeroHabitacion() == numeroHabitacion) {

                // Si entra al 'if', hay conflicto de fechas
                if (fechaCheckIn.isBefore(reservaExistente.getFechaCheckOut()) && fechaCheckOut.isAfter(reservaExistente.getFechaCheckIn())) {
                    return false;
                }
            }
        }
        return true;
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

    //Revisa una fecha especifica de una habitacion para ver si esta reservada ese dia o no (Se usa para el calendario).
    private boolean estaReservadaEnFecha(int numeroHabitacion, LocalDate fecha) {
        for (Reserva reservaTemp : listaReservas) {
            if (reservaTemp.getNumeroHabitacion() == numeroHabitacion && reservaTemp.isEstadoReserva()) { //isEstadoReserva verifica que la reserva este activa y no se halla dado de baja

                if ((fecha.isEqual(reservaTemp.getFechaCheckIn()) || fecha.isAfter(reservaTemp.getFechaCheckIn())) && fecha.isBefore(reservaTemp.getFechaCheckOut())) {
                    return true; //si devuelve true es que si esta ocupada ese dia
                }
            }
        }
        return false; //si devuelve false esta libre
    }
    //--------------------------------------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------------------------------------



    //Se agregaran a la lista "disponibles" las habitaciones disponibles para las fechas dadas y se devolvera la lista a mostrarHabitacionesDisponibles
    public List<Habitacion> obtenerHabitacionesDisponibles(LocalDate fechaCheckIn, LocalDate fechaCheckOut) {

        if (fechaCheckOut.isBefore(fechaCheckIn) || fechaCheckOut.isEqual(fechaCheckIn)) {
            throw new EFechasNoValidas("ERROR: La fecha de Check Out tiene que ser posterior a la fecha de Check In"); }

        List<Habitacion> disponibles = new ArrayList<>();

        for (Habitacion habitacionTemp : listaHabitaciones.values()) {

            if (habitacionTemp.getEstadoHabitacion() == EEstadoHabitacion.DISPONIBLE &&
                    checkDisponibilidadFechaSinExepciones(fechaCheckIn, fechaCheckOut, habitacionTemp.getNumeroHabitacion())) {
                disponibles.add(habitacionTemp);
            }
        }
        return disponibles;
    }

    //El staff puede consultar un periodo de fechas y todas las habitaciones para ese periodo se mostraran.
    public void mostrarHabitacionesDisponibles(LocalDate fechaCheckIn, LocalDate fechaCheckOut) {

        //Obtenemos la lista de habitaciones disponibles con la siguiente funcion:
        List<Habitacion> disponibles = obtenerHabitacionesDisponibles(fechaCheckIn, fechaCheckOut);

        //Forma recomendada para obtener la cantidad total de dias entre dos fechas, devuelve un long, asi que lo casteamos a int
        int cantidadDias = (int) ChronoUnit.DAYS.between(fechaCheckIn, fechaCheckOut);

        System.out.println("\n=================== HABITACIONES DISPONIBLES ===================");
        System.out.println(" Período: " + fechaCheckIn + " -----> " + fechaCheckOut + "   (" + cantidadDias + " noches)");
        System.out.println("================================================================");

        if (disponibles.isEmpty()) {
            //Si no hay habitaciones disponibles entra aca.
            System.out.println(" No hay habitaciones disponibles para las fechas seleccionadas.");
        } else {
            //Alineado con la ayuda de la IA
            System.out.printf("%-12s %-20s %-18s %-15s%n", "NUMERO", "TIPO", "PRECIO / NOCHE", "MONTO TOTAL");
            System.out.println("----------------------------------------------------------------");

            for (Habitacion habitacionTemp : disponibles) {

                double montoTotal = cantidadDias*habitacionTemp.getPrecioXnoche();

                //Alineado con la ayuda de la IA
                System.out.printf("%-12d %-20s $%-17.2f $%-14.2f%n",
                        habitacionTemp.getNumeroHabitacion(),
                        habitacionTemp.getTipoHabitacion(),
                        habitacionTemp.getPrecioXnoche(),
                        montoTotal
                            );
            }
        }
        System.out.println("================================================================\n");
    }

    //Si ya nos encontramos dentro de la fecha de una reserva activa, y la habitacion y cliente dado son los correctos, se procede a hacer el CheckIn (EEstadoHabitacion.OCUPADA)
    public Reserva hacerCheckIn(String dniCliente, int numeroHabitacion) throws ENoExisteReservaParaFecha{

        LocalDate fechaHOY = LocalDate.now();

        for (Reserva reservaTemp : listaReservas) {
            if (reservaTemp.getNumeroHabitacion() == numeroHabitacion && reservaTemp.isEstadoReserva()) { //isEstadoReserva verifica que la reserva este activa y no se halla dado de baja

                if ((fechaHOY.isEqual(reservaTemp.getFechaCheckIn()) || fechaHOY.isAfter(reservaTemp.getFechaCheckIn())) && fechaHOY.isBefore(reservaTemp.getFechaCheckOut())) {
                    if (dniCliente.equals(reservaTemp.getDniCliente())) {
                        Habitacion habitacionTemp = listaHabitaciones.get(numeroHabitacion);
                        habitacionTemp.setEstadoHabitacion(EEstadoHabitacion.OCUPADA);
                        return reservaTemp;
                    }
                }
            }
        }
        throw new ENoExisteReservaParaFecha("No existe una reserva para este cliente en esta habitacion para hoy.");
    }

    public Reserva hacerCheckOut(String dniCliente, int numeroHabitacion) throws ENoExisteReservaParaFecha{
        for (Reserva reservaTemp : listaReservas) {
            if (reservaTemp.getNumeroHabitacion() == numeroHabitacion && reservaTemp.isEstadoReserva()) { //isEstadoReserva verifica que la reserva este activa y no se halla dado de baja

                    if (dniCliente.equals(reservaTemp.getDniCliente())) {
                        Habitacion habitacionTemp = listaHabitaciones.get(numeroHabitacion);
                        if (habitacionTemp.getEstadoHabitacion() == EEstadoHabitacion.OCUPADA) {
                            habitacionTemp.setEstadoHabitacion(EEstadoHabitacion.DISPONIBLE);
                            reservaTemp.setEstadoReserva(false);
                            return reservaTemp;
                        }
                    }
                }
        }
        throw new ENoExisteReservaParaFecha("No existe una reserva activa para este cliente en esta habitacion para hoy.");
    }

    public Staff validarLogin(String dni,String contrasenia){

        for(Staff empleado:listaStaff){
            //comprueba si coincide el dni y la conrtrasenia
            if(empleado.getDni().equals(dni)) && empleado.getContrasenia().equals(contrasenia)){

                return empleado;
            }
        }

        //si no encuentra coincidencias
        return null;
    }


}
