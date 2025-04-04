package com.example;

// /**
//  * Hello world!
//  *
//  */
// public class App 
// {
//     public static void main( String[] args )
//     {
//         System.out.println( "Hello World!" );
//     }
// }



import java.io.*;
import java.util.*;

class Student {
    private int id;
    private String name;
    private int age;
    private String course;

    public Student(int id, String name, int age, String course) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
    }

    @Override
    public String toString() {
        return id + ", " + name + ", " + age + ", " + course;
    }
}

public class App {
    private static final String FILE_NAME = "students.txt";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== Student Admission System =====");
            System.out.println("1. Add Student");
            System.out.println("2. Display Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Count Students");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            String input = scanner.nextLine();
            int choice;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    displayStudents();
                    break;
                case 3:
                    searchStudentById();
                    break;
                case 4:
                    countStudents();
                    break;
                case 5:
                    System.out.println("👋 Exiting...");
                    return;
                default:
                    System.out.println("❌ Invalid choice. Try again.");
            }
        }
    }

    private static void addStudent() {
        try (FileWriter fw = new FileWriter(FILE_NAME, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            System.out.print("Enter Student ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Age: ");
            int age = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Course: ");
            String course = scanner.nextLine();

            Student student = new Student(id, name, age, course);
            out.println(student);
            System.out.println("✅ Student added successfully!");

        } catch (IOException e) {
            System.out.println("❌ Error writing to file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input. ID and Age must be numbers.");
        }
    }

    private static void displayStudents() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("📂 No students found.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            System.out.println("\n📋 List of Students:");
            boolean found = false;

            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    System.out.println(line);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No student records to display.");
            }

        } catch (IOException e) {
            System.out.println("❌ Error reading file: " + e.getMessage());
        }
    }

    private static void searchStudentById() {
        System.out.print("Enter Student ID to search: ");
        String input = scanner.nextLine();
        int searchId;

        try {
            searchId = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid ID. Please enter a number.");
            return;
        }

        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("❌ No student records found.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean found = false;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                if (data.length >= 1 && Integer.parseInt(data[0]) == searchId) {
                    System.out.println("✅ Student Found: " + line);
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("❌ Student with ID " + searchId + " not found.");
            }

        } catch (IOException e) {
            System.out.println("❌ Error reading file: " + e.getMessage());
        }
    }

    private static void countStudents() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("📂 No student records found.");
            return;
        }

        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    count++;
                }
            }
            System.out.println("👥 Total number of students: " + count);
        } catch (IOException e) {
            System.out.println("❌ Error reading file: " + e.getMessage());
        }
    }
}
