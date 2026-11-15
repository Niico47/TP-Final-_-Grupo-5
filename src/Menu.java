import CosasDePersonas.Staff;
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
        public void iniciarSesion(){
            System.out.println("------------INICIO DE SESION-------------");

            System.out.println("\nIngrese dni: ");
            String dni = sc.nextLine();

            System.out.println("\nIngrese contrasenia: ");
            String contrasenia=sc.nextLine();

            Staff empleado=hotel.validarLogin(dni,contrasenia);

            if(empleado==null){
                System.out.println("\nUsuario o contrasenia incorrectos");
            }else {

                System.out.println("\nBienvenidos al sistema");

                //se determina el menu segun el tipo de empleado

                switch(empleado.getPuesto()){}
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
    }
