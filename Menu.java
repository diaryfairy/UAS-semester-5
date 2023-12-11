import java.util.Scanner;
import java.sql.*;

public class Menu {
    private static Scanner scanner = new Scanner(System.in);
    private static Connection connection;

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mahasiswa", "(root)", "(pass)");
            System.out.println("Connected to database successfully.");

            while (true) {
                int menu = displayMenu();
                if (menu == 0) {
                    break;
                }
                switch (menu) {
                    case 1:
                        insertData();
                        break;
                    case 2:
                        viewData();
                        break;
                    case 3:
                        updateData();
                        break;
                    default:
                        System.out.println("Invalid choice. Please choose again.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error occurred while connecting to database.");
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static int displayMenu() {
        System.out.println("\n1. Input Data");
        System.out.println("2. Tampil Data");
        System.out.println("3. Update Data");
        System.out.println("0. Keluar");
        System.out.print("PILIHAN: ");
        return scanner.nextInt();
    }

    private static void insertData() {
        try {
            System.out.print("Enter name: ");
            String name = scanner.next();
            System.out.print("Enter address: ");
            String address = scanner.next();

            PreparedStatement statement = connection.prepareStatement("INSERT INTO mahasiswa (nama, alamat) VALUES (?, ?)");
            statement.setString(1, name);
            statement.setString(2, address);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Data inserted successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while inserting data.");
            e.printStackTrace();
        }
    }

    private static void viewData() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM mahasiswa");

            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id"));
                System.out.println("Name: " + resultSet.getString("nama"));
                System.out.println("Address: " + resultSet.getString("alamat"));
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while fetching data.");
            e.printStackTrace();
        }
    }

    private static void updateData() {
        try {
            System.out.print("Enter ID: ");
            int id = scanner.nextInt();

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM mahasiswa WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                System.out.print("Enter new name: ");
                String name = scanner.next();
                System.out.print("Enter new address: ");
                String address = scanner.next();

                PreparedStatement updateStatement = connection.prepareStatement("UPDATE mahasiswa SET nama = ?, alamat = ? WHERE id = ?");
                updateStatement.setString(1, name);
                updateStatement.setString(2, address);
                updateStatement.setInt(3, id);
                int rowsUpdated = updateStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Data updated successfully.");
                }
            } else {
                System.out.println("ID not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while updating data.");
            e.printStackTrace();
        }
    }
}