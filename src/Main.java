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

void main() {

//habitaciones pasajeros/clientes del hotel, ClasesPersonas.Administrador,ClasesPersonas.Recepcionista, Pasajero

//El sistema deberá permitir realizar reservas de habitaciones, hacer el check-in y check-out de los pasajeros,
// listar las habitaciones actualmente ocupadas y los datos de los ocupantes, las habitaciones disponibles,
// y las habitaciones que no estén disponibles por algún motivo
// (limpieza, reparación, desinfección, etc. Detallar el motivo).

    Cliente cliente1 = new Cliente("Juan", "123", "Buenos Aires", "Luro 1234");
    Habitacion habitacion1 = new Habitacion(1, EEstadoHabitacion.DISPONIBLE, ETipoHabitacion.SINGLE);

    Hotel hotel1 = new Hotel("SuperHotel", "Av Martin 321");

    System.out.println(hotel1.registrarCliente(cliente1));
    System.out.println(hotel1.clienteHaceReserva("123"));
    System.out.println(hotel1.registrarHabitacion(habitacion1.getNumeroHabitacion(), habitacion1));

    Reserva reserva_registrada = hotel1.crearReserva(LocalDate.of(2026, 10, 4),LocalDate.of(2026, 10, 16),"123", 1);
    System.out.println(reserva_registrada.getFechaCheckIn());
    Reserva reserva_registrada2 = hotel1.crearReserva(LocalDate.of(2026, 10, 16),LocalDate.of(2026, 10, 20),"123", 1);
    System.out.println(reserva_registrada2.getFechaCheckIn());
}
