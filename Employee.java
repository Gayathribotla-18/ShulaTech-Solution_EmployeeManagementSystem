package employee.management;
import java.io.Serializable;
import java.io.*;
import java.util.*;
class Employee implements Serializable {
    private int id;
    private String name;
    private String position;
    private double salary;

    public Employee(int id, String name, String position, double salary) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    public int fetchId() { return id; }
    public String fetchName() { return name; }
    public String fetchPosition() { return position; }
    public double fetchSalary() { return salary; }

    public void setName(String name) { this.name = name; }
    public void setPosition(String position) { this.position = position; }
    public void setSalary(double salary) { this.salary = salary; }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Position: " + position + ", Salary: " + salary;
    }
}
class EMS {
    private List<Employee> employees;
    private static final String FILE_NAME = "employees.txt";

    public EMS() {
        this.employees = loadEmployees();
    }

    public void addEmployee(Employee emp) {
        employees.add(emp);
        saveEmployees();
    }

    public void updateEmployee(int id, String name, String position, double salary) {
        for (Employee emp : employees) {
            if (emp.fetchId() == id) {
                emp.setName(name);
                emp.setPosition(position);
                emp.setSalary(salary);
                saveEmployees();
                System.out.println("Employee updated successfully.");
                return;
            }
        }
        System.out.println("Employee not found.");
    }

    public void deleteEmployee(int id) {
        employees.removeIf(emp -> emp.fetchId() == id);
        saveEmployees();
    }

    public void searchEmployee(int id) {
        for (Employee emp : employees) {
            if (emp.fetchId() == id) {
                System.out.println(emp);
                return;
            }
        }
        System.out.println("Employee not found.");
    }

    public void displayEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            employees.forEach(System.out::println);
        }
    }

    private void saveEmployees() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Employee emp : employees) {
                writer.write(emp.fetchId() + "," + emp.fetchName() + "," + emp.fetchPosition() + "," + emp.fetchSalary());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    private List<Employee> loadEmployees() {
        List<Employee> empList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                String position = data[2];
                double salary = Double.parseDouble(data[3]);
                empList.add(new Employee(id, name, position, salary));
            }
        } catch (IOException e) {
            System.out.println("No existing employee data found. Starting fresh.");
        }
        return empList;
    }
}

public class Employee {
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EMS manager = new EMS();

        while (true) {
            System.out.println("\nEmployee Management System - Created by Gayathri");
            System.out.println("1. Add Employee");
            System.out.println("2. Update Employee");
            System.out.println("3. Delete Employee");
            System.out.println("4. Search Employee");
            System.out.println("5. Display Employees");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter ID: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Position: ");
                        String position = scanner.nextLine();
                        System.out.print("Enter Salary: ");
                        double salary = scanner.nextDouble();
                        manager.addEmployee(new Employee(id, name, position, salary));
                        break;
                    case 2:
                        System.out.print("Enter ID to update: ");
                        id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter New Name: ");
                        name = scanner.nextLine();
                        System.out.print("Enter New Position: ");
                        position = scanner.nextLine();
                        System.out.print("Enter New Salary: ");
                        salary = scanner.nextDouble();
                        manager.updateEmployee(id, name, position, salary);
                        break;
                    case 3:
                        System.out.print("Enter ID to delete: ");
                        id = scanner.nextInt();
                        manager.deleteEmployee(id);
                        break;
                    case 4:
                        System.out.print("Enter ID to search: ");
                        id = scanner.nextInt();
                        manager.searchEmployee(id);
                        break;
                    case 5:
                        manager.displayEmployees();
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter valid values.");
                scanner.nextLine();
            }
        }
    }
}


