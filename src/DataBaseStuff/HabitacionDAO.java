package DataBaseStuff;
import CosasDeHabitaciones.Habitacion;
import CosasDeHabitaciones.ETipoHabitacion;
import CosasDeHabitaciones.EEstadoHabitacion;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class HabitacionDAO {

    private Map<Integer, Habitacion> listaTempHabitaciones;

    public HabitacionDAO() {
        this.listaTempHabitaciones = new HashMap<>();
        cargarListaTemporal();
    }

    //Carga/recarga el mapa de habitaciones activas desde la base de datos
    public void cargarListaTemporal() {
        listaTempHabitaciones.clear();
        String sql = "SELECT numeroHabitacion, tipoHabitacion, estadoHabitacion, estadoHabitacion2, precioXnoche FROM habitaciones WHERE estadoHabitacion2 = 1";

        try (Connection conn = conectionDB.conectar();
             Statement sta = conn.createStatement();
             ResultSet rs = sta.executeQuery(sql)) {

            while (rs.next()) {
                int numeroHab = rs.getInt("numeroHabitacion");
                Habitacion habitacionTemp = new Habitacion(
                        numeroHab,
                        EEstadoHabitacion.valueOf(rs.getString("estadoHabitacion")),
                        ETipoHabitacion.valueOf(rs.getString("tipoHabitacion"))
                );
                listaTempHabitaciones.put(numeroHab, habitacionTemp);
            }

            System.out.println("Mapa temporal de habitaciones actualizado. Total activas: " + listaTempHabitaciones.size());

        } catch (SQLException e) {
            throw new RuntimeException("Error al cargar la lista de habitaciones desde la BD", e);
        }
    }

    //Agrega una habitacion a la tabla y recarga la lista
    public String agregarHabitacionADB(Habitacion habitacion) {
        String sql = "INSERT INTO habitaciones (numeroHabitacion, tipoHabitacion, estadoHabitacion, estadoHabitacion2, precioXnoche) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = conectionDB.conectar();
             PreparedStatement pt = conn.prepareStatement(sql)) {

            pt.setInt(1, habitacion.getNumeroHabitacion());
            pt.setString(2, habitacion.getTipoHabitacion().name());
            pt.setString(3, habitacion.getEstadoHabitacion().name());
            pt.setBoolean(4, true); // Activa por defecto
            pt.setDouble(5, habitacion.getPrecioXnoche());

            pt.executeUpdate();

            //recarga de la lista
            cargarListaTemporal();

            return "Habitación agregada a la base de datos con éxito.";

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar la habitación en la BD", e);
        }
    }

    //eliminar(Soft Delete) cambiando el boolean de estado a false y recargando
    public String eliminarHabitacionPorNumeroDeDB(int numeroHabitacion) {
        String sql = "UPDATE habitaciones SET estadoHabitacion2 = 0 WHERE numeroHabitacion = ?";

        try (Connection conn = conectionDB.conectar();
             PreparedStatement pt = conn.prepareStatement(sql)) {

            pt.setInt(1, numeroHabitacion);
            int filasAfectadas = pt.executeUpdate();

            if (filasAfectadas > 0) {
                cargarListaTemporal();
                return "Habitacion dada de baja (soft delete) con exito.";
            } else {
                return "No se encontró ninguna habitación con ese número.";
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al dar de baja la habitacion en la BD", e);
        }
    }

    //Getter para cuando se necesita acceder al mapa
    public Map<Integer, Habitacion> getListaTempHabitaciones() {
        return listaTempHabitaciones;
    }
}