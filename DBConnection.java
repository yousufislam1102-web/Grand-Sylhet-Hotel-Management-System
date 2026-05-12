import java.sql.*;
public class DBConnection {
    public Connection con;
    public Statement stmt;

    public DBConnection() {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            try {

                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_db", "root", "");
                stmt = con.createStatement();
            } catch (Exception e) {
                System.out.println(e);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
