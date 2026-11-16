import CosasDeHabitaciones.EEstadoHabitacion;
import CosasDeHabitaciones.ETipoHabitacion;
import CosasDeHabitaciones.Habitacion;
import CosasDePersonas.Cliente;
import CosasDePersonas.Staff;
import Excepciones.EErrorLogIn;
import jdk.swing.interop.SwingInterOpUtils;

import javax.management.relation.Role;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import static CosasDePersonas.ETipoUsuario.Recepcionista;

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
            String contrasenia=sc.nextLine();

            try {
                Staff empleado = hotel.validarLogin(dni, contrasenia);

                System.out.println("\nBienvenidos al sistema");

                //se determina el menu segun el tipo de empleado
                switch (empleado.getPuesto()) {
                    case Administrador -> {
                        System.out.println(menuAdministrador());
                        break;
                    }
                    case Recepcionista -> {
                        System.out.println(menuRecepcionista());
                        break;
                    }
                }
            } catch (EErrorLogIn e) {
                throw new RuntimeException(e);
            }
        }

        private String menuRecepcionista(){
            String mensaje="";
            mensaje="\n=====Menu Recepcionista=====" +
                    "\n1.RegistrarReservas." +
                    "\n2.Realizar CheckIn." +
                    "\n3.Realizar CheckOut" +
                    "\n4.Consultar Habitaciones disponibles" +
                    "\n5.0 para finalizar" +
                    "\n======================================";
            return mensaje;
        }

        private String menuAdministrador(){
            String mensaje="";
            mensaje="\n===== Menu Administrador =====" +
                    "\n1.Registrar Clientes" +
                    "\n2.Registrar Habitaciones" +
                    "\n3.Consultar HAbitaciones disponibles" +
                    "\n4.Consultar calendario de habitaciones";
            return mensaje;
        }

        public void iteracionMenuRecepcionista(){
            //Switch para manejar el trabajo del administrador y el trabajo del recepcionista

            int opcion=1;
            int control=1;
            while(control!=0 && opcion!=0){
                System.out.println("\nIndique tarea a ejecutar: ");
                opcion=sc.nextInt();
                switch(opcion){
                    case 1:
                        //1.Definir el formato esperado de la fecha
                        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        System.out.println("\nIndique fecha checkIn formato dd/MM/yyyy: ");
                        String checkITexto= sc.nextLine();
                        System.out.println("\nIndique fecha de checkOut en el mismo formato: ");
                        String checkOutTexto = sc.nextLine();
                        System.out.println("\nIngresar dni cliente");
                        String dni=sc.nextLine();
                        System.out.println("\nIngresar nro habitacion: ");
                        int nroHabitacioon=sc.nextInt();
                        try{
                            //2.Convertir el texto a un objeto LocalDate
                            LocalDate checkIn = LocalDate.parse(checkITexto,formato);
                            System.out.println("\nFecha valida ingresada: "+checkIn);
                            LocalDate checkOut = LocalDate.parse(checkOutTexto,formato);
                            System.out.println("\nFecha valida ingresada: "+checkOut);
                            hotel.crearReserva(checkIn,checkOut,dni,nroHabitacioon);
                        }catch(DateTimeParseException e){
                            //3.Manejar el error si el usuario escribe mal el formato o la fecha es invalida
                            System.out.println("Error:El formato debe ser dd/MM/yyyy");
                        }
                    break;
                    case 2:
                        System.out.println("\nIngrese numero de habitacion:");
                        int nroHabitacion=sc.nextInt();
                        System.out.println("\nIngrese nombre del cliente: ");
                        String nombre=sc.nextLine();
                        System.out.println("\nIngrese dni: ");
                        String dnii=sc.nextLine();
                        System.out.println("\nIngrese localidad: ");
                        String localidad=sc.nextLine();
                        System.out.println("\nIngresar Domicilio: ");
                        String domicilio=sc.nextLine();
                        System.out.println();
                        Cliente c1=new Cliente(nombre,dnii,localidad,domicilio);
                        hotel.hacerCheckIn(c1.getDni(),nroHabitacion);
                    break;
                    case 3:
                        System.out.println("\nIngrese nroHabitacion");
                        int nroHabitacionn = sc.nextInt();
                        System.out.println("\nIngrese dni cliente");
                        String dniCliente=sc.nextLine();
                        hotel.hacerCheckOut(dniCliente,nroHabitacionn);
                    break;
                    case 4:
                        //1.Definir el formato esperado de la fecha
                        DateTimeFormatter formatoo = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        System.out.println("\nIndique fecha checkIn formato dd/MM/yyyy: ");
                        String checkInTexto= sc.nextLine();
                        System.out.println("\nIndique fecha de checkOut en el mismo formato: ");
                        String checkOuttTexto = sc.nextLine();
                        try{
                            //2.Convertir el texto a un objeto LocalDate
                            LocalDate checkIn = LocalDate.parse(checkInTexto,formatoo);
                            System.out.println("\nFecha valida ingresada: "+checkIn);
                            LocalDate checkOut = LocalDate.parse(checkOuttTexto,formatoo);
                            System.out.println("\nFecha valida ingresada: "+checkOut);
                            hotel.mostrarHabitacionesDisponibles(checkIn,checkOut);
                        }catch(DateTimeParseException e){
                            //3.Manejar el error si el usuario escribe mal el formato o la fecha es invalida
                            System.out.println("Error:El formato debe ser dd/MM/yyyy");
                        }
                    break;
                    case 0:
                        System.out.println("\nFinalizo la interaccion con el menu");
                    break;
                }
                System.out.println("\nIngrese 1 para continuar o 0 para finalizar: ");
                control=sc.nextInt();
            }
        }

        public void iteracionMenuAdministrador(){
            int opcion=1;
            int control=1;
            while(control!=0 && opcion!=0){
                System.out.println("\nIngrese la opcion del menu: ");
                opcion=sc.nextInt();
                switch(opcion){
                    case 1:
                        System.out.println("\nIngresar dni del cliente");
                        String dni=sc.nextLine();
                        System.out.println("\nIngrese nombre cliente: ");
                        String nombre=sc.nextLine();
                        System.out.println("\nIngrese localidad:");
                        String localidad=sc.nextLine();
                        System.out.println("\nIngrese direccion: ");
                        String direccion=sc.nextLine();
                        Cliente C1=new Cliente(nombre,dni,localidad,direccion);
                        hotel.registrarCliente(C1);
                    break;
                    case 2:
                        System.out.println("\nIngrese nro de habitacion: ");
                        int nroHabitacion=sc.nextInt();
                        sc.nextLine();//Limpio el buffer
                        System.out.println("\nIngrese estado Habitacion" +
                                "DISPONIBLE" +
                                "LIMPIEZA" +
                                "OCUPADA" +
                                "REPARACION" +
                                "DESINFECCION: ");
                        String estadoHabitacionTexto=sc.nextLine();
                        System.out.println("\nIngrese tipo de habitacion" +
                                "SINGLE" +
                                "DOBLE" +
                                "TRIPLE" +
                                "SUIT: ");
                        String tipoHabitacionTexto=sc.nextLine();
                        try{
                            //Convierte el String al tipo enum correspondiente
                            EEstadoHabitacion estadoHabitacion = EEstadoHabitacion.valueOf(estadoHabitacionTexto);
                            ETipoHabitacion tipoHabitacion = ETipoHabitacion.valueOf(tipoHabitacionTexto);
                            Habitacion H1=new Habitacion(nroHabitacion,estadoHabitacion,tipoHabitacion);
                            hotel.registrarHabitacion(nroHabitacion,H1);
                        }catch(IllegalArgumentException e){
                            //Ejecuta si el usuario escribio algo que no esta en el enum
                            System.out.println("\nError: el rol asignado no esta en el enum");
                        }
                    break;
                    case 3:
                        //1.Definir el formato esperado de la fecha
                        DateTimeFormatter formatoo = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        System.out.println("\nIndique fecha checkIn formato dd/MM/yyyy: ");
                        String checkInTexto= sc.nextLine();
                        System.out.println("\nIndique fecha de checkOut en el mismo formato: ");
                        String checkOuttTexto = sc.nextLine();
                        try{
                            //2.Convertir el texto a un objeto LocalDate
                            LocalDate checkIn = LocalDate.parse(checkInTexto,formatoo);
                            System.out.println("\nFecha valida ingresada: "+checkIn);
                            LocalDate checkOut = LocalDate.parse(checkOuttTexto,formatoo);
                            System.out.println("\nFecha valida ingresada: "+checkOut);
                            hotel.mostrarHabitacionesDisponibles(checkIn,checkOut);
                        }catch(DateTimeParseException e){
                            //3.Manejar el error si el usuario escribe mal el formato o la fecha es invalida
                            System.out.println("Error:El formato debe ser dd/MM/yyyy");
                        }
                    break;
                    case 4:
                        System.out.println("\nIngrese año: ");
                        int anio=sc.nextInt();
                        System.out.println("\nIngrese mes: ");

                        hotel.mostrarCalendarioHabitacion();
                    break;
                }
            }
        }
    }



