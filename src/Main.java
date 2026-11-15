//Realización y entrega de un Diagrama UML en el cual quede diseñado la estructura del sistema.
//Utilización de los 4 pilares de la POO (Herencia, Polimorfismo, Abstracción y Encapsulamiento).
//Creación de un mínimo de 6 clases en su sistema (Sin contar la clase Main, interfaces ni excepciones).
//Implementar al menos una Colección y un Mapa.
//Crear mínimamente 1 Clase Abstracta, 1 interfaz y 1 enumeración para implementar.
//Aplicación del concepto de manejo de errores con al menos 2 clases de excepción personalizadas.
//
//
//Creación y uso de una Clase Genérica.
//Manejo de Archivos de texto o binarios o JSON para la persistencia de datos.
//Utilización de la herramienta GIT/GITHUB para el versionado de código y el trabajo colaborativo.



//  TP FINAL GESTOR DE HOTEL

import CosasDeHabitaciones.EEstadoHabitacion;
import CosasDeHabitaciones.ETipoHabitacion;
import CosasDeHabitaciones.Habitacion;
import CosasDeHabitaciones.Reserva;
import CosasDePersonas.Cliente;
import CosasDePersonas.ETipoUsuario;
import CosasDePersonas.Staff;

void main() {

//habitaciones pasajeros/clientes del hotel, ClasesPersonas.Administrador,ClasesPersonas.Recepcionista, Pasajero

//El sistema deberá permitir realizar reservas de habitaciones, hacer el check-in y check-out de los pasajeros,
// listar las habitaciones actualmente ocupadas y los datos de los ocupantes, las habitaciones disponibles,
// y las habitaciones que no estén disponibles por algún motivo
// (limpieza, reparación, desinfección, etc. Detallar el motivo).
    Scanner sc=new Scanner(System.in);
    Cliente cliente1 = new Cliente("Juan", "123", "Buenos Aires", "Luro 1234");
    Habitacion habitacion1 = new Habitacion(1, EEstadoHabitacion.DISPONIBLE, ETipoHabitacion.SINGLE);
    Habitacion habitacion2 = new Habitacion(2, EEstadoHabitacion.DISPONIBLE, ETipoHabitacion.DOBLE);
    Habitacion habitacion3 = new Habitacion(3, EEstadoHabitacion.DISPONIBLE, ETipoHabitacion.SUIT);
    Habitacion habitacion4 = new Habitacion(4, EEstadoHabitacion.DISPONIBLE, ETipoHabitacion.TRIPLE);
    Habitacion habitacion5 = new Habitacion(5, EEstadoHabitacion.DISPONIBLE, ETipoHabitacion.SINGLE);
    Staff S1=new Staff<>("Ema","41431326","123456","Acevedo 6468","Mar del plata",1234, ETipoUsuario.Recepcionista,300);
    Staff S2=new Staff<>("Nico","43632161","123457","Constitucion 5500","Mar del plata",123,ETipoUsuario.Administrador,300);

    Hotel hotel1 = new Hotel("SuperHotel", "Av Martin 321");

    System.out.println(hotel1.registrarCliente(cliente1));
    System.out.println(hotel1.registrarHabitacion(habitacion1.getNumeroHabitacion(), habitacion1));
    System.out.println(hotel1.registrarHabitacion(habitacion2.getNumeroHabitacion(), habitacion2));
    System.out.println(hotel1.registrarHabitacion(habitacion3.getNumeroHabitacion(), habitacion3));
    System.out.println(hotel1.registrarHabitacion(habitacion4.getNumeroHabitacion(), habitacion4));
    System.out.println(hotel1.registrarHabitacion(habitacion5.getNumeroHabitacion(), habitacion5));


    Reserva reserva_registrada = hotel1.crearReserva(LocalDate.of(2026, 11, 12),LocalDate.of(2026, 11, 18),"123", 1);
    System.out.println(reserva_registrada.getFechaCheckIn());
    Reserva reserva_registrada2 = hotel1.crearReserva(LocalDate.of(2026, 11, 19),LocalDate.of(2026, 11, 23),"123", 1);
    System.out.println(reserva_registrada2.getFechaCheckIn());
    Reserva reserva_registrada3 = hotel1.crearReserva(LocalDate.of(2026, 11, 19),LocalDate.of(2026, 11, 23),"123", 3);


//    habitacion1.setEstadoHabitacion(EEstadoHabitacion.LIMPIEZA);

    hotel1.mostrarCalendarioHabitacion(2026, 11, 1);

    hotel1.mostrarHabitacionesDisponibles(LocalDate.of(2026, 11, 10), LocalDate.of(2026, 11, 25));

    System.out.println(hotel1.hacerCheckIn("123", 1));
    System.out.println(hotel1.hacerCheckOut("123", 1));

    Menu m1=new Menu(hotel1);
    hotel1.registrarStaff(S1);
    hotel1.registrarStaff(S2);
    m1.iniciarSesion();

}
