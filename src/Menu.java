import CosasDeHabitaciones.Habitacion;
import CosasDePersonas.Staff;
import Excepciones.EErrorLogIn;
import jdk.swing.interop.SwingInterOpUtils;

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
<<<<<<< HEAD
        public void iniciarSesion(){
            //Defino el formato esperado para leer fechas
            DateTimeFormatter formato=DateTimeFormatter.ofPattern("dd/MM/yyyy");
            int control=1;
            int control2=1;
            int opcion=0;
=======
        public void iniciarSesion() {
>>>>>>> ccf11b47d18ee7e79b08a2a7300682c8bb5fd75a
            System.out.println("------------INICIO DE SESION-------------");

            System.out.println("\nIngrese dni: ");
            String dni = sc.nextLine();

            System.out.println("\nIngrese contrasenia: ");
            String contrasenia=sc.nextLine();

            try {
                Staff empleado=hotel.validarLogin(dni,contrasenia);

                System.out.println("\nBienvenidos al sistema");

                //se determina el menu segun el tipo de empleado
                switch (empleado.getPuesto()) {
                    case Administrador -> {
<<<<<<< HEAD
                        System.out.println(menuAdministrador());
                        break;
                    }
                    case Recepcionista -> {
                        System.out.println(menuRecepcionista());
                        break;
                    }
                    default -> {
                        System.out.println("\nTipo de empleado no reconocido");
                        break;
=======
                        menuAdministrador();
//                        break; El breack no es necesatrio ema ciando se usa la flechita ->
                    }
                    case Recepcionista -> {
                        menuRecepcionista();
                    }
                    default -> {
                        System.out.println("\nTipo de empleado no reconocido");
>>>>>>> ccf11b47d18ee7e79b08a2a7300682c8bb5fd75a
                    }
                }

            } catch (EErrorLogIn e) {
                System.out.println(e.getMessage());
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

        public void IteracionMenuRecepcionista(){
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
                        try{
                            //2.Convertir el texto a un objeto LocalDate
                            LocalDate checkIn = LocalDate.parse(checkITexto,formato);
                            System.out.println("\nFecha valida ingresada: "+checkIn);
                            LocalDate checkOut = LocalDate.parse(checkOutTexto,formato);
                            System.out.println("\nFecha valida ingresada: "+checkOut);
                        }catch(DateTimeParseException e){
                            //3.Manejar el error si el usuario escribe mal el formato o la fecha es invalida
                            System.out.println("Error:El formato debe ser dd/MM/yyyy");
                        }
                    break;
                    case 2:

                    break;
                }
            }
        }
    }
