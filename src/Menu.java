import CosasDePersonas.Staff;
import Excepciones.EErrorLogIn;
import jdk.swing.interop.SwingInterOpUtils;

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
            String contrasenia=sc.nextLine();

            try {
                Staff empleado=hotel.validarLogin(dni,contrasenia);

                System.out.println("\nBienvenidos al sistema");

                //se determina el menu segun el tipo de empleado
                switch (empleado.getPuesto()) {
                    case Administrador -> {
                        menuAdministrador();
//                        break; El breack no es necesatrio ema ciando se usa la flechita ->
                    }
                    case Recepcionista -> {
                        menuRecepcionista();
                    }
                    default -> {
                        System.out.println("\nTipo de empleado no reconocido");
                    }
                }

            } catch (EErrorLogIn e) {
                System.out.println(e.getMessage());
            }
        }

        private void menuRecepcionista(){
            String mensaje="";
            mensaje="\n=====Menu Recepcionista=====" +
                    "\n1.RegistrarReservas." +
                    "\n2.Realizar CheckIn." +
                    "\n3.Realizar CheckOut" +
                    "\n4.Consultar Habitaciones disponibles" +
                    "\n======================================";
        }

        private void menuAdministrador(){
            String mensaje="";
            mensaje="\n===== Menu Administrador =====" +
                    "\n1.Registrar Clientes" +
                    "\n2.Registrar Habitaciones" +
                    "\n3.Consultar HAbitaciones disponibles" +
                    "\n4.Consultar calendario de habitaciones";
        }
    }
