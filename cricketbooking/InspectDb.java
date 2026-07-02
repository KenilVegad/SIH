import java.sql.*;
public class InspectDb {
  public static void main(String[] args) throws Exception {
    try (Connection c = DriverManager.getConnection("jdbc:h2:file:./data/cricketbooking", "sa", "")) {
      try (PreparedStatement ps = c.prepareStatement("SELECT DATA_TYPE, DECLARED_DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME='BOOKINGS' AND COLUMN_NAME='STATUS'")) {
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            System.out.println(rs.getString(1) + " | " + rs.getString(2));
          }
        }
      }
      try (Statement st = c.createStatement(); ResultSet rs = st.executeQuery("SELECT DISTINCT STATUS FROM BOOKINGS")) {
        while (rs.next()) {
          System.out.println("STATUS=" + rs.getString(1));
        }
      }
    }
  }
}
