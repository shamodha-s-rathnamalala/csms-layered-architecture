package lk.ijse.computershop.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dbConnection;
    private Connection connection;
    private DBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/csms", "root", "1234");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Failed to load the database");
        }
    }
    public static DBConnection getInstance(){
        return dbConnection==null?(dbConnection=new DBConnection()):dbConnection;
    }
    public Connection getConnection(){
        return connection;
    }
}
