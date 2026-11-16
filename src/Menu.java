import CosasDeHabitaciones.EEstadoHabitacion;
import CosasDeHabitaciones.ETipoHabitacion;
import CosasDeHabitaciones.Habitacion;
import CosasDePersonas.Cliente;
import CosasDePersonas.ETipoUsuario;
import CosasDePersonas.Staff;
import Excepciones.EErrorLogIn;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

// Aca va a ingresar el staff con su contrasenia y nombre de usuario.
// Segun que tipo de staff sea (Administrador o recepcionista) le va a mostrar su menu especifico
//(menu de Recepcionista) o (menu de Administrador)

public class Menu {
    private Hotel hotel;
    private Scanner sc;

    public Menu(Hotel hotel) {
        this.hotel = hotel;
        this.sc = new Scanner(System.in);
    }

    //inicio de sesion
    public void iniciarSesion() {
        System.out.println("------------INICIO DE SESION-------------");

        System.out.println("\nIngrese dni: ");
        String dni = sc.nextLine();

        System.out.println("\nIngrese contrasenia: ");
        String contrasenia = sc.nextLine();

        try {
            Staff empleado = hotel.validarLogin(dni, contrasenia);

            System.out.println("\nBienvenidos al sistema");

            //se determina el menu segun el tipo de empleado
            switch (empleado.getPuesto()) {
                case Administrador -> {
                    System.out.println(menuAdministrador());
                    iteracionMenuAdministrador();
                    break;
                }
                case Recepcionista -> {
                    System.out.println(menuRecepcionista());
                    iteracionMenuRecepcionista();
                    break;
                }
            }
        } catch (EErrorLogIn e) {
            throw new RuntimeException(e);
        }
    }

    private String menuRecepcionista(){
        String mensaje = "";
        mensaje = "\n===== Menu Recepcionista =====" +
                "\n1. Registrar Reserva" +
                "\n2. Realizar CheckIn" +
                "\n3. Realizar CheckOut" +
                "\n4. Consultar Habitaciones disponibles" +
                "\n5.Eliminar reserva" +
                "\n6.Consultar calendario habitacion" +
                "\n0. Para finalizar" +
                "\n======================================";
        return mensaje;
    }

    private String menuAdministrador(){
        String mensaje = "";
        mensaje = "\n===== Menu Administrador =====" +
                "\n1. Registrar Clientes" +
                //Registrar nuevo Staff
                //Eliminar Staff
                "\n2. Registrar Habitacion" +
                "\n3. Consultar Habitaciones disponibles" +
                "\n4. Consultar calendario de habitacion" +
                "\n5.Eliminar clientes" +
                "\n6.Eliminar habitacion" +
                "\n0. Para finalizar" +
                "\n======================================";
        return mensaje;
    }

    public void iteracionMenuRecepcionista(){
        //Variables para controlar el bucle del menu
        int opcion = 1;
        int control = 1;

        //El bucle se mantiene activo mientras no se elija salir (0)
        while(control != 0 && opcion != 0){
            System.out.println("\nIndique tarea a ejecutar: ");
            opcion = sc.nextInt();
            sc.nextLine(); // Limpiar el buffer de entrada despues de leer un numero

            switch(opcion){
                case 1: // Registrar una nueva reserva
                    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    System.out.println("\nIndique fecha checkIn formato dd/MM/yyyy: ");
                    String checkITexto = sc.nextLine();
                    System.out.println("\nIndique fecha de checkOut en el mismo formato: ");
                    String checkOutTexto = sc.nextLine();
                    System.out.println("\nIngresar dni cliente:");
                    String dni = sc.nextLine();
                    System.out.println("\nIngresar nro habitacion: ");
                    int nroHabitacion = sc.nextInt();
                    sc.nextLine(); // Limpiar buffer

                    try{
                        //Convertir texto a LocalDate y validar fechas
                        LocalDate checkIn = LocalDate.parse(checkITexto, formato);
                        System.out.println("\nFecha valida ingresada: " + checkIn);
                        LocalDate checkOut = LocalDate.parse(checkOutTexto, formato);
                        System.out.println("\nFecha valida ingresada: " + checkOut);

                        //Crea la reserva y la guarda en la base de datos a traves del hotel
                        hotel.crearReserva(checkIn, checkOut, dni, nroHabitacion);
                    }catch(DateTimeParseException e){
                        System.out.println("Error: El formato debe ser dd/MM/yyyy");
                    }
                    break;

                case 2: //Realizar el Check-In
                    System.out.println("\nIngrese numero de habitacion:");
                    int nroHabitacionCheckIn = sc.nextInt();
                    sc.nextLine(); // Limpiar buffer
                    System.out.println("\nIngrese dni del cliente: ");
                    String dnii = sc.nextLine();

                    hotel.hacerCheckIn(dnii, nroHabitacionCheckIn);
                    break;

                case 3: //Realizar el Check-Out
                    System.out.println("\nIngrese nro Habitacion:");
                    int nroHabitacionn = sc.nextInt();
                    sc.nextLine(); // Limpiar buffer
                    System.out.println("\nIngrese dni cliente:");
                    String dniCliente = sc.nextLine();

                    hotel.hacerCheckOut(dniCliente, nroHabitacionn);
                    break;

                case 4: //Consultar habitaciones disponibles por rango de fechas
                    DateTimeFormatter formatoo = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    System.out.println("\nIndique fecha checkIn formato dd/MM/yyyy: ");
                    String checkInTexto = sc.nextLine();
                    System.out.println("\nIndique fecha de checkOut en el mismo formato: ");
                    String checkOuttTexto = sc.nextLine();
                    try{
                        LocalDate checkIn = LocalDate.parse(checkInTexto, formatoo);
                        LocalDate checkOut = LocalDate.parse(checkOuttTexto, formatoo);
                        hotel.mostrarHabitacionesDisponibles(checkIn, checkOut);
                    }catch(DateTimeParseException e){
                        System.out.println("Error: El formato debe ser dd/MM/yyyy");
                    }
                    break;
                case 5:
                    //Eliminar reserva
                    System.out.println("\nIndique el id de la reserva a cancelar");
                    int idReserva=sc.nextInt();
                    hotel.cancelarReserva(idReserva);
                break;
                case 6:
                    //Consultar calendario
                    System.out.println("\nIngrese anio: ");
                    int anio = sc.nextInt();
                    System.out.println("\nIngrese mes: ");
                    int mes = sc.nextInt();
                    System.out.println("\nIngrese numero de habitacion: ");
                    nroHabitacionn = sc.nextInt();
                    sc.nextLine(); // Limpiar buffer

                    hotel.mostrarCalendarioHabitacion(anio, mes, nroHabitacionn);
                break;

                case 0: //Salir del menu
                    System.out.println("\nFinalizo la interaccion con el menu");
                    break;

                default:
                    System.out.println("\nOpcion invalida.");
            }

            // Pregunta si desea repetir el proceso o finalizar
            if (opcion != 0) {
                System.out.println("\nIngrese 1 para continuar o 0 para finalizar: ");
                control = sc.nextInt();
                sc.nextLine(); // Limpiar buffer
            }
        }
    }

    public void iteracionMenuAdministrador(){
        //Variables para controlar el bucle del menu
        int opcion = 1;
        int control = 1;

        //El bucle se mantiene activo mientras no se elija salir (0)
        while(control != 0 && opcion != 0){
            System.out.println("\nIngrese la opcion del menu: ");
            opcion = sc.nextInt();
            sc.nextLine(); //Limpiar buffer despues de leer el numero

            switch(opcion){
                case 1: //Registrar un nuevo cliente
                    System.out.println("\nIngresar dni del cliente:");
                    String dni = sc.nextLine();
                    System.out.println("\nIngrese nombre cliente:");
                    String nombre = sc.nextLine();
                    System.out.println("\nIngrese localidad:");
                    String localidad = sc.nextLine();
                    System.out.println("\nIngrese direccion:");
                    String direccion = sc.nextLine();

                    Cliente c1 = new Cliente(nombre, dni, localidad, direccion);
                    //Sincroniza el cliente con la base de datos a traves del hotel
                    hotel.registrarCliente(c1);
                    break;

                case 2: //Registrar una nueva habitacion
                    System.out.println("\nIngrese nro de habitacion: ");
                    int nroHabitacion = sc.nextInt();
                    sc.nextLine(); // Limpiar buffer
                    System.out.println("\nIngrese estado Habitacion (DISPONIBLE, LIMPIEZA, OCUPADA, REPARACION, DESINFECCION): ");
                    String estadoHabitacionTexto = sc.nextLine();
                    System.out.println("\nIngrese tipo de habitacion (SINGLE, DOBLE, TRIPLE, SUIT): ");
                    String tipoHabitacionTexto = sc.nextLine();
                    try{
                        //Convierte los textos a enums y crea la habitacion
                        EEstadoHabitacion estadoHabitacion = EEstadoHabitacion.valueOf(estadoHabitacionTexto.toUpperCase());
                        ETipoHabitacion tipoHabitacion = ETipoHabitacion.valueOf(tipoHabitacionTexto.toUpperCase());
                        Habitacion h1 = new Habitacion(nroHabitacion, estadoHabitacion, tipoHabitacion);
                        hotel.registrarHabitacion(nroHabitacion, h1);
                    }catch(IllegalArgumentException e){
                        System.out.println("\nError: el estado o tipo ingresado no coincide con los valores permitidos.");
                    }
                    break;

                case 3: //Consultar habitaciones disponibles por rango de fechas
                    DateTimeFormatter formatoo = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    System.out.println("\nIndique fecha checkIn formato dd/MM/yyyy: ");
                    String checkInTexto = sc.nextLine();
                    System.out.println("\nIndique fecha de checkOut en el mismo formato: ");
                    String checkOuttTexto = sc.nextLine();
                    try{
                        LocalDate checkIn = LocalDate.parse(checkInTexto, formatoo);
                        LocalDate checkOut = LocalDate.parse(checkOuttTexto, formatoo);
                        hotel.mostrarHabitacionesDisponibles(checkIn, checkOut);
                    }catch(DateTimeParseException e){
                        System.out.println("Error: El formato debe ser dd/MM/yyyy");
                    }
                    break;

                case 4: //Consultar el calendario de ocupacion de una habitacion
                    System.out.println("\nIngrese anio: ");
                    int anio = sc.nextInt();
                    System.out.println("\nIngrese mes: ");
                    int mes = sc.nextInt();
                    System.out.println("\nIngrese numero de habitacion: ");
                    int nroHabitacionn = sc.nextInt();
                    sc.nextLine(); // Limpiar buffer

                    hotel.mostrarCalendarioHabitacion(anio, mes, nroHabitacionn);
                    break;
                case 5:
                    //Eliminar cliente
                    System.out.println("\nIngrese el usuario(dni) del cliente que quiere eliminar: ");
                    dni=sc.nextLine();
                    hotel.eliminarCliente(dni);
                break;
                case 6:
                    //Eliminar habitacion
                    System.out.println("\nIngrese nro de habitacion que quiere eliminar");
                    nroHabitacionn=sc.nextInt();
                    hotel.eliminarHabitacion(nroHabitacionn);
                break;
                case 7:
                    //Registrar Staff
                    System.out.println("\nIngrese el id del empleado: ");
                    int idEmpleado=sc.nextInt();
                    System.out.println("\nIngrese nombre del empleado: ");
                    nombre=sc.nextLine();
                    System.out.println("\nIngrese dni del empleado: ");
                    dni=sc.nextLine();
                    System.out.println("\nIngrese localidad del empleado: ");
                    localidad=sc.nextLine();
                    System.out.println("\nIngrese domicilio del empleado: ");
                    direccion=sc.nextLine();
                    System.out.println("\nIngrese el salario del empleado: ");
                    double salario=sc.nextDouble();
                    System.out.println("\nIngrese contrasenia del empleado: ");
                    String contrasenia=sc.nextLine();
                    System.out.println("\nIngrese el tipo de usuario: " +
                            "Administrador" +
                            "Recepcionista" +
                            "Limpieza" +
                            "Mantenimiento");
                    String tipos=sc.nextLine();
                    try{
                        //Convierte los textos a enums y crea la habitacion
                        ETipoUsuario tipo = ETipoUsuario.valueOf(tipos.toUpperCase());
                        Staff S1=new Staff(idEmpleado,nombre,dni,direccion,localidad,tipo,salario,contrasenia);
                        hotel.registrarStaff(S1);
                    }catch(IllegalArgumentException e){
                        System.out.println("\nError: el estado o tipo ingresado no coincide con los valores permitidos.");
                    }
                break;
                case 0: //Salir del menu
                    System.out.println("\nFinalizo la interaccion con el menu");
                    break;

                default:
                    System.out.println("\nOpcion invalida.");
            }

            //Pregunta si desea continuar en el menu o finalizar
            if (opcion != 0) {
                System.out.println("\nIngrese 1 para continuar o 0 para finalizar: ");
                control = sc.nextInt();
                sc.nextLine(); //Limpiar buffer
            }
        }
    }
}