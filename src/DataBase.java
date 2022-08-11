import java.sql.*;

public class DataBase {
    Connection dbConnection;
    private String dbHost = "localhost";
    private String dbPort = "3306";
    private String dbUser = "root";
    private String dbPass = "12345";
    private String dbName = "translatebot";

    public Connection getDBConnection() {
        try {
            String connectionString = "jdbc:mysql://" + dbHost + ":"
                    + dbPort + "/" + dbName;
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        } catch (SQLException e) {
            System.out.println("не получилось установить соединение с базой");
        } catch (ClassNotFoundException e) {
            System.out.println("Класс не нашелся");
        }
        return dbConnection;
    }
}
