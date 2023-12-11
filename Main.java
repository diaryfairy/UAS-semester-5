import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/mahasiswa";
        String username = "(root)";
        String password = "(password)";

        System.out.println("Connecting to database...");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected successfully!");
        } catch (SQLException e) {
            System.out.println("Error occurred while connecting to database.");
            e.printStackTrace();
        }
    }
}