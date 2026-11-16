package DataBaseStuff;
import CosasDePersonas.Cliente;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ClienteDAO {

    private Set<Cliente> listaTempClientes;

    public ClienteDAO() {
        this.listaTempClientes = new HashSet<>();
        cargarListaTemporal();
    }

    //Carga/recarga la lista de clientes activos desde la base de datos
    public void cargarListaTemporal() {
        listaTempClientes.clear();
        String sql = "SELECT dni, nombre, domicilio, localidad, estadoCliente FROM clientes WHERE estadoCliente = 1";

        try (Connection conn = conectionDB.conectar();
             Statement sta = conn.createStatement();
             ResultSet rs = sta.executeQuery(sql)) {

            while (rs.next()) {
                Cliente clienteTemp = new Cliente(
                        rs.getString("nombre"),
                        rs.getString("dni"),
                        rs.getString("domicilio"),
                        rs.getString("localidad")
                );
                listaTempClientes.add(clienteTemp);
            }

            System.out.println("Lista temporal de clientes actualizada. Total activos: " + listaTempClientes.size());

        } catch (SQLException e) {
            throw new RuntimeException("Error al cargar la lista de clientes desde la BD", e);
        }
    }

    //Agrega datos a la tabla y la recarga
    public String agregarClienteADB(Cliente cliente) {
        String sql = "INSERT INTO clientes (dni, nombre, domicilio, localidad, estadoCliente) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = conectionDB.conectar();
             PreparedStatement pt = conn.prepareStatement(sql)) {

            pt.setString(1, cliente.getDni());
            pt.setString(2, cliente.getNombre());
            pt.setString(3, cliente.getDomicilio());
            pt.setString(4, cliente.getLocalidad());
            pt.setBoolean(5, true); //activo por defecto

            pt.executeUpdate();

            //recarga de la lista
            cargarListaTemporal();

            return "Cliente agregado a la base de datos con exito.";

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar el cliente en la BD", e);
        }
    }

    //eliminar(Soft Delete) cambiando el boolean de estado a false y recargando
    public String eliminarClienteConDniDeDB(String dni) {
        String sql = "UPDATE clientes SET estadoCliente = 0 WHERE dni = ?";

        try (Connection conn = conectionDB.conectar();
             PreparedStatement pt = conn.prepareStatement(sql)) {

            pt.setString(1, dni);
            int filasAfectadas = pt.executeUpdate();

            if (filasAfectadas > 0) {
                // Recargamos la lista
                cargarListaTemporal();
                return "Cliente dado de baja (soft delete) con exito.";
            } else {
                return "No se encontro ningún cliente con ese DNI.";
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al dar de baja al cliente en la BD", e);
        }
    }

    //Getter para cuando se necesita acceder a la lista
    public Set<Cliente> getListaTempClientes() {
        return listaTempClientes;
    }
}