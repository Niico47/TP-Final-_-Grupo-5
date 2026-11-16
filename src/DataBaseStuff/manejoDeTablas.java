package DataBaseStuff;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class manejoDeTablas {

    public static void crearTablas() {

        conectionDB.inicializarBaseDeDatos(); //Inicializamos la base d edatos tpf5 si es que no existe
        conectionDB.conectar(); //Conectamos con la base de datos

        //Tabla Habitaciones
        String tablaHabitaciones = "CREATE TABLE IF NOT EXISTS habitaciones (" +
                "numeroHabitacion INT NOT NULL, " +//PK
                "tipoHabitacion VARCHAR(45) NOT NULL, " +
                "estadoHabitacion VARCHAR(45) NOT NULL, " +
                "estadoHabitacion2 BOOLEAN NOT NULL, " +
                "precioXnoche DOUBLE NOT NULL, " +
                "PRIMARY KEY (numeroHabitacion));";

        // 2. Tabla Staff (Hereda atributos de Persona + propios)
        String tablaStaff = "CREATE TABLE IF NOT EXISTS staff (" +
                "id_Empleado INT NOT NULL, " +//PK
                "nombre VARCHAR(100) NOT NULL, " +
                "dni VARCHAR(20) NOT NULL, " +
                "domicilio VARCHAR(100), " +
                "localidad VARCHAR(50), " +
                "puesto VARCHAR(45) NOT NULL, " +
                "salarioBase DOUBLE NOT NULL, " +
                "contrasenia VARCHAR(45) NOT NULL, " +
                "estadoStaff BOOLEAN NOT NULL, " +
                "PRIMARY KEY (id_Empleado));";

        // 3. Tabla Clientes
        String tablaClientes = "CREATE TABLE IF NOT EXISTS clientes (" +
                "dni VARCHAR(20) NOT NULL, " + //PK
                "nombre VARCHAR(100) NOT NULL, " +
                "domicilio VARCHAR(100), " +
                "localidad VARCHAR(50), " +
                "estadoCliente BOOLEAN NOT NULL, " +
                "PRIMARY KEY (dni));";

        // 4. Tabla Reservas
        String tablaReservas = "CREATE TABLE IF NOT EXISTS reservas (" +
                "idReserva INT NOT NULL, " +//PK
                "dniCliente VARCHAR(20) NOT NULL, " +
                "numeroHabitacion INT NOT NULL, " +
                "fechaCheckIn DATE NOT NULL, " +
                "fechaCheckOut DATE NOT NULL, " +
                "estadoReserva BOOLEAN NOT NULL, " +
                "PRIMARY KEY (idReserva));";

        try (Connection conn = conectionDB.conectar();
             Statement sta = conn.createStatement()) {

            sta.executeUpdate(tablaHabitaciones);
            sta.executeUpdate(tablaStaff);
            sta.executeUpdate(tablaClientes);
            sta.executeUpdate(tablaReservas);

            System.out.println("Tablas creadas con exito si es que no existian.");

        } catch (SQLException e) {
            System.out.println("Error al crear las tablas en la base de datos:");
            e.printStackTrace();
        }
    }
}

