package DataBaseStuff;

import CosasDeHabitaciones.Reserva;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    private List<Reserva> listaTempReservas;

    public ReservaDAO() {
        this.listaTempReservas = new ArrayList<>();
        cargarListaTemporal();
    }

    //Carga/recarga la lista de reservas
    public void cargarListaTemporal() {
        listaTempReservas.clear();
        String sql = "SELECT idReserva, dniCliente, numeroHabitacion, fechaCheckIn, fechaCheckOut, estadoReserva FROM reservas WHERE estadoReserva = 1";
        try (Connection conn = conectionDB.conectar();
             Statement sta = conn.createStatement();
             ResultSet rs = sta.executeQuery(sql)) {

            while (rs.next()) {
                Reserva reservaTemp = new Reserva(
                        rs.getInt("idReserva"),
                        rs.getString("dniCliente"),
                        rs.getInt("numeroHabitacion"),
                        rs.getDate("fechaCheckIn").toLocalDate(),
                        rs.getDate("fechaCheckOut").toLocalDate()
                );
                listaTempReservas.add(reservaTemp);
            }

            System.out.println("Lista temporal de reservas actualizada. Total activas: " + listaTempReservas.size());

        } catch (SQLException e) {
            throw new RuntimeException("Error al cargar la lista de reservas desde la BD", e);
        }
    }

    // Agrega una reserva a la tabla y recarga la lista (Sin incluir idReserva porque es AUTO_INCREMENT)
    public String agregarReservaADB(Reserva reserva) {
        // Corregido: Usamos fechaCheckIn y fechaCheckOut para que coincidan con la BD
        String sql = "INSERT INTO reservas (dniCliente, numeroHabitacion, fechaCheckIn, fechaCheckOut, estadoReserva) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = conectionDB.conectar();
             PreparedStatement pt = conn.prepareStatement(sql)) {

            pt.setString(1, reserva.getDniCliente());
            pt.setInt(2, reserva.getNumeroHabitacion());
            pt.setDate(3, Date.valueOf(reserva.getFechaCheckIn()));
            pt.setDate(4, Date.valueOf(reserva.getFechaCheckOut()));
            pt.setBoolean(5, true); // Activa por defecto

            pt.executeUpdate();

            // Recarga de la lista
            cargarListaTemporal();

            return "Reserva agregada a la base de datos con exito.";

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar la reserva en la BD", e);
        }
    }

    //eliminar(Soft Delete) cambiando el boolean de estado a false y recargando
    public String eliminarReservaPorIdDeDB(int idReserva) {
        String sql = "UPDATE reservas SET estadoReserva = 0 WHERE idReserva = ?";

        try (Connection conn = conectionDB.conectar();
             PreparedStatement pt = conn.prepareStatement(sql)) {

            pt.setInt(1, idReserva);
            int filasAfectadas = pt.executeUpdate();

            if (filasAfectadas > 0) {
                cargarListaTemporal();
                return "Reserva dada de baja (soft delete) con exito.";
            } else {
                return "No se encontro ninguna reserva con ese ID.";
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al dar de baja la reserva en la BD", e);
        }
    }

    //Getter para cuando se necesita acceder a la lista
    public List<Reserva> getListaTempReservas() {
        return listaTempReservas;
    }
}