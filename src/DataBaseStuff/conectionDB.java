package DataBaseStuff;

import java.sql .*;

public class conectionDB {

    // URL general del servidor (sin especificar la base de datos)
    private static String URL_SERVIDOR = "jdbc:mysql://localhost:3306/";
    // URL especifica de la base de datos usada
    private static String URL_DB = "jdbc:mysql://localhost:3306/tpf5";

    private static String user = "root"; //nombre de usuario
    private static String password = "1234QWER-"; //contrasenia de mysql, cambiar segun la tuya

// METODOS STATIC DE CONECCION --------------------------------------------------------------------------------------------------------------

    // metodo default para crear la base de datos automaticamente si no existe
    public static void inicializarBaseDeDatos() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 1. Nos conectamos al servidor general y creamos la base de datos
            try (Connection connServidor = DriverManager.getConnection(URL_SERVIDOR, user, password);
                 Statement stmt = connServidor.createStatement()) {

                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS tpf5;");
                System.out.println("Base de datos 'tpf5' verificada o creada con éxito.");
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Error al inicializar la base de datos", e);
        }
    }

    // metodo para conectarse a la base de datos una vez ya existe
    public static Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL_DB, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Error al conectar desde la interfaz", e);
        }
    }

//--------------------------------------------------------------------------------------------------------------

}
