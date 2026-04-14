import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class SalesPerson {
    private String name;
    private String city;
    private double commission;
    private double totalSales;

    public SalesPerson(String name, String city, double commission, double totalSales) {
        this.name = name;
        this.city = city;
        this.commission = commission;
        this.totalSales = totalSales;
    }

    public String getName() {
        return name;
    }

    public double getCommission() {
        return commission;
    }

    public double getTotalSales() {
        return totalSales;
    }

    @Override
    public String toString() {
        return "SalesPerson [name=" + name + ", city=" + city + ", commission=" + commission + ", totalSales=" + totalSales + "]";
    }
}

public class SalesReport {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:sqlite:C:\\Users\\vfisc\\Downloads\\Sqlite\\labwork.db";
        String user = "Fiscella";
        String password = "Horse";

        List<SalesPerson> salesPersonList = new ArrayList<>();

        String query = "SELECT s.name, s.city, s.commission, SUM(o.purchase_amt) AS total_sales " + "FROM salesman s " + "LEFT JOIN orders o ON s.salesman_id = o.salesman_id " + "GROUP BY s.salesman_id, s.name, s.city, s.commission";

        Connection conn = DriverManager.getConnection(url, user, password);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            SalesPerson sp = new SalesPerson(
                rs.getString("name"),
                rs.getString("city"),
                rs.getDouble("commission"),
                rs.getDouble("total_sales")
            );
            salesPersonList.add(sp);
        }

        System.out.println("--- Total Earnings ---");
        System.out.printf("%-20s | %-15s\n", "Name", "Total Sales");
        System.out.println("-------------------------------------------");
        salesPersonList.stream().forEach(s -> System.out.printf("%-20s | %-15.2f\n", s.getName(), s.getTotalSales()));

        System.out.println("\n--- Total Commissions ---");
        System.out.printf("%-20s | %-15s\n", "Name", "Commission");
        System.out.println("-------------------------------------------");
        salesPersonList.stream().forEach(s -> System.out.printf("%-20s | %-15.2f\n", s.getName(), (s.getTotalSales() * s.getCommission())));
        
        rs.close();
        stmt.close();
        conn.close();
    }
}