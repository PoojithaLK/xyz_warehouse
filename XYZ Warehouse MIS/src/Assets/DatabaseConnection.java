package Assets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;



public class DatabaseConnection {

    Connection conn = null;

    public static Connection connectToDb() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/xyz", "root", "");
            return conn;
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database Connection Error\n" + ex);
        }
        return null;
    }
}
