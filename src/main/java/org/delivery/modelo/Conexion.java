package org.delivery.modelo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
@Setter
public class Conexion {

    private final String URL = "jdbc:mysql://localhost:3306/delivery";
    private final String USER = "root";
    private final String PASSWORD = "5547";
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private Connection connection;

    /**
     * Método para conectar la base de datos con la aplicación
     *
     * @return la conexión a la base de datos o null si no se pudo conectar
     */
    public Connection getConexion() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error al conectar la base de datos: " + e.getMessage());
            return null;
        }
        return getConnection();
    }

    /**
     * Método para desconectar la base de datos de la aplicación y liberar recursos
     */
    public void getDesconexion() {
        try {
            getConexion().close();
        } catch (SQLException e) {
            System.out.println("Error al desconectar la base de datos: " + e.getMessage());
        }
    }
}
