public class EmployeeProcessor {
    public static void main(String[] args) {
        List<Employee> employeesList = new ArrayList<>();
        String url = "jdbc:mariadb://localhost:3306/employee";
        String user = "root";
        String password = ""; //

        // 1. Retrieve data using JDBC
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, name, salary FROM employees")) {
            
            while (rs.next()) {
                employeesList.add(new Employee(
                    rs.getInt("id"), 
                    rs.getString("name"), 
                    rs.getDouble("salary")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 2. Print all employees
        System.out.println("Full Employee List:");
        employeesList.forEach(System.out::println);

        // 3. Define Predicate for salary > $50,000
        Predicate<Employee> highSalaryPred = e -> e.getSalary() > 50000;

        // 4. Print list of high earners
        System.out.println("\nHigh Earners (> $50,000):");
        employeesList.stream().filter(highSalaryPred).forEach(System.out::println);

        // 5. Define Tax Function
        Function<Employee, Employee> applyTax = e -> {e.setSalary(e.getSalary() * 0.85);
            return e;
        };

        // 6. Format Salary Function
        Function<Employee, String> formatSalary = e -> String.format("%s: $%.2f", e.getName(), e.getSalary());

        // 7. Combined Stream: Filter, Apply Tax, and Collect
        List<Employee> taxedHighEarners = employeesList.stream().filter(highSalaryPred).map(applyTax).collect(Collectors.toList());

        // 8. Extra
        System.out.println("\nFormatted Tax Report:");
        employeesList.stream()
                .map(e -> {double rate = (e.getSalary() > 50000) ? 0.85 : 0.90;e.setSalary(e.getSalary() * rate);
                    return e;
                })
                .map(formatSalary).forEach(System.out::println);
    }
}
class Employee{
    private int id;
    private String name;
    private double salary;
    public Employee(int id,String name,double salary)
    {
        this.id=id;
        this.name=name;
        this.salary=salary;
}
public String toString()
{
return String.format("Employee{id=%d, name='%s', salary=%.2f}", id, name, salary);
}
public int getId()
{
return id;
}
public String getName()
{
return name;
}
public double getSalary()
{
return salary;
}
}