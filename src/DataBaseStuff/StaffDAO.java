package DataBaseStuff;
import CosasDePersonas.Staff;
import CosasDePersonas.ETipoUsuario;
import DataBaseStuff.conectionDB;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class StaffDAO {

    private Set<Staff> listaTempStaff;

    public StaffDAO() {
        this.listaTempStaff = new HashSet<>();
        cargarListaTemporal();
    }

    //Carga o recarga la lista de staff activo desde la base de datos
    public void cargarListaTemporal() {
        listaTempStaff.clear();
        String sql = "SELECT id_Empleado, nombre, dni, domicilio, localidad, puesto, salarioBase, contrasenia, estadoStaff FROM staff WHERE estadoStaff = 1";

        try (Connection conn = conectionDB.conectar();
             Statement sta = conn.createStatement();
             ResultSet rs = sta.executeQuery(sql)) {

            while (rs.next()) {
                Staff staffTemp = new Staff(
                        rs.getInt("id_Empleado"),
                        rs.getString("nombre"),
                        rs.getString("dni"),
                        rs.getString("domicilio"),
                        rs.getString("localidad"),
                        ETipoUsuario.valueOf(rs.getString("puesto")),
                        rs.getDouble("salarioBase"),
                        rs.getString("contrasenia")
                );
                listaTempStaff.add(staffTemp);
            }

            System.out.println("Lista temporal de staff actualizada. Total activos: " + listaTempStaff.size());

        } catch (SQLException e) {
            throw new RuntimeException("Error al cargar la lista de staff desde la BD", e);
        }
    }

    //Agrega un miembro del staff a la tabla y recarga la lista (Sin incluir id_Empleado porque es AUTO_INCREMENT)
    public String agregarStaffADB(Staff staff) {
        String sql = "INSERT INTO staff (nombre, dni, domicilio, localidad, puesto, salarioBase, contrasenia, estadoStaff) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = conectionDB.conectar();
             PreparedStatement pt = conn.prepareStatement(sql)) {

            pt.setString(1, staff.getNombre());
            pt.setString(2, staff.getDni());
            pt.setString(3, staff.getDomicilio());
            pt.setString(4, staff.getLocalidad());
            pt.setString(5, staff.getPuesto().name());
            pt.setDouble(6, staff.getSalarioBase());
            pt.setString(7, staff.getContrasenia());
            pt.setBoolean(8, true); // Activo por defecto

            pt.executeUpdate();

            //recarga de la lista
            cargarListaTemporal();

            return "Miembro de staff agregado a la base de datos con exito.";

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar el staff en la BD", e);
        }
    }

    //eliminar(Soft Delete) cambiando el boolean de estado a false y recargando
    public String eliminarStaffConIdDeDB(int idEmpleado) {
        String sql = "UPDATE staff SET estadoStaff = 0 WHERE id_Empleado = ?";

        try (Connection conn = conectionDB.conectar();
             PreparedStatement pt = conn.prepareStatement(sql)) {

            pt.setInt(1, idEmpleado);
            int filasAfectadas = pt.executeUpdate();

            if (filasAfectadas > 0) {
                cargarListaTemporal();
                return "Miembro de staff dado de baja (soft delete) con exito.";
            } else {
                return "No se encontro ningun empleado con ese ID.";
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al dar de baja al staff en la BD", e);
        }
    }

    //Getter para cuando se necesita acceder a la lista
    public Set<Staff> getListaTempStaff() {
        return listaTempStaff;
    }
}