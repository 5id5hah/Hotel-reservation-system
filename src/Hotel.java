import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static java.lang.System.exit;

public class Hotel {
    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String username = "root";
    private static final String password = "Sid@12345";

    private static Statement statement;

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        try {
            Connection con = DriverManager.getConnection(url, username, password);
            statement = con.createStatement();
            while (true) {
                System.out.println();
                System.out.println("Welcome to Hotel Reservation System");
                Scanner scanner = new Scanner(System.in);
                System.out.println("1.New Reservation");
                System.out.println("2.Check Reservation");
                System.out.println("3.Get your Room Number");
                System.out.println("4.Update Reservation");
                System.out.println("5.Delete Reservation");
                System.out.println("0.Quit");
                System.out.println("Choose an option");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        reserveRoom(con, scanner);
                        break;
                    case 2:
                        viewReservations(con);
                        break;
                    case 3:
                        getRoom(con, scanner);
                        break;
                    case 4:
                        updateReservations(con, scanner);
                        break;
                    case 5:
                        deleteReservation(con, scanner);
                        break;
                    case 0:
                        exit();
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Try again");
                }
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }
    private static void reserveRoom(Connection con,Scanner scanner){
        System.out.println("Enter guest name");
        String gname = scanner.next();
        System.out.println("Enter room number");
        scanner.nextLine();
        int roomnum = scanner.nextInt();
        System.out.println("Enter contact number");
        String contact = scanner.next();

        String sql = "INSERT INTO reserve(guest_name, room_no,contact_no)"+
                "VALUES ('"+gname+"',"+roomnum+",'"+contact+"')";
        try {
            int affectedrows = statement.executeUpdate(sql);
            if(affectedrows > 0){
                System.out.println("Reservation successful..!");
            }else {
                System.out.println("Reservation failed");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private static void viewReservations(Connection con) throws SQLException{
        String sql = "SELECT  reservation_id,guest_name,room_no,contact_no,reservation_date FROM reserve";
        try {
            ResultSet resultSet = statement.executeQuery(sql);
            System.out.println("Current reservations");
            System.out.println("+---------------+------------------+-----------------+-----------------+------------------------+");
            System.out.println("|Reservation ID | Guest            | Room Number     | Contact no      | Date                   |");
            System.out.println("+---------------+------------------+-----------------+-----------------+------------------------+");

            while (resultSet.next()){
                int reservation_id = resultSet.getInt("reservation_id");
                String guest_name = resultSet.getString("guest_name");
                int room_no = resultSet.getInt("room_no");
                String contact_no = resultSet.getString("contact_no");
                String reservation_date = resultSet.getString("reservation_date");

                System.out.printf("%-14d | %-15s | %-15s | %-20s | %-19s%n",
                        reservation_id, guest_name, room_no, contact_no, reservation_date);
                System.out.println("+---------------+------------------+-----------------+-----------------+--------------------+");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private static void getRoom(Connection con, Scanner scanner){
        System.out.println("Enter reservation id");
        int reservationid = scanner.nextInt();
        System.out.println("Enter guest name");
        String gname = scanner.next();

        String sql =" SELECT room_no FROM reserve WHERE reservation_id = "+reservationid+" AND guest_name= '"+gname+"'";
        try {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()){
                int roomnumber = resultSet.getInt("room_no");
                System.out.println("Room reservation for id "+reservationid+" and guest "+gname+" is : "+roomnumber);
            }else {
                System.out.println("Reservation not found ");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private static void updateReservations(Connection con,Scanner scanner){
        try {
            System.out.println("Enter reservation id to update");
            int reservationid = scanner.nextInt();
            scanner.nextLine();
            if (!reservationExists(con,reservationid)){
                System.out.println("Reservation not found for given id. ");
                return;
            }
            System.out.println("Enter updated guest name ");
            String gname = scanner.next();
            System.out.println("Enter updated room number");
            scanner.nextLine();
            int roomnum = scanner.nextInt();
            System.out.println("Enter updated contact number");
            String contact = scanner.next();

            String sql = "UPDATE reserve SET guest_name= '"+gname+"', "+"room_no="+roomnum+","+"contact_no='"+contact+"' "+
                        "WHERE reservation_id = "+reservationid;

            int affectedrows = statement.executeUpdate(sql);
            if (affectedrows > 0){
                System.out.println("Reservation updated successfully.");
            }else{
                System.out.println("Reservation update failed.");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private static void deleteReservation(Connection con,Scanner scanner){
        try {
            System.out.println("Enter reservation id to delete");
            int reservationid = scanner.nextInt();
            scanner.nextLine();
            if (!reservationExists(con,reservationid)){
                System.out.println("Reservation not found for given id. ");
                return;
            }
            String sql = "DELETE FROM reserve WHERE reservation_id = "+reservationid;
            int affectedrows = statement.executeUpdate(sql);
            if (affectedrows > 0){
                System.out.println("Reservation deleted successfully.");
            }else{
                System.out.println("Reservation deletion failed.");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private static void exit() throws InterruptedException{
        System.out.println("Exiting System");
        int i=5;
        while (i!=0){
            System.out.println(".");
            Thread.sleep(450);
            i--;
        }
    }
    private static boolean reservationExists(Connection con, int reservationid)throws SQLException{
        try {
            String sql = "SELECT reservation_id FROM reserve WHERE reservation_id = "+reservationid;
            ResultSet resultSet = statement.executeQuery(sql);

            return resultSet.next();
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
