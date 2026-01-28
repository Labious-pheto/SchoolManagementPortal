package ui.cli;

import student.model.Gender;
import student.model.Student;
import student.service.StudentService;
import attendance.model.Attendance;
import attendance.service.AttendanceService;
import classmgmt.service.ClassService;
import marks.service.MarksService;

import java.time.LocalDate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import javax.swing.text.View;

import attendance.model.Attendance;
import attendance.model.AttendanceReport;
import classmgmt.model.SchoolClass;
import classmgmt.service.ClassService;



public class StudentCLI {

    private final StudentService studentService;
    private final Scanner scanner;
    private final AttendanceService attendanceService;
    private final ClassService classService;
    private final MarksService marksService = new MarksService();


    public StudentCLI() {
        this.studentService = new StudentService();
        this.scanner = new Scanner(System.in);
        this.attendanceService = new AttendanceService();
        this.classService = new ClassService();
    }

    public void start() {
        int choice;

        do {
            showMenu();
            choice = readInt("Choose option: ");

            switch (choice) {
                case 1 -> registerStudent();
                case 2 -> viewStudent();
                case 3 -> listStudents();
                case 4 -> updateStudent();
                case 5 -> deactivateStudent();
                case 6 -> markAttendance();
                case 7 -> viewAttendanceByStudent();
                case 8 -> viewAttendanceByDate();
                case 9 -> addClass();              // ✅ THIS was created in SERALENG
                case 10 -> listClasses();           // ✅ THIS was created in SERALENG
                case 11 -> addStudentMark();
                case 12 -> viewFinalMarks();
                case 0 -> System.out.println("Exiting Student Module...");
                default -> System.out.println("Invalid option!");
            }

        } while (choice != 0);
    }

    private void showMenu() {
        System.out.println("\n===== STUDENT MANAGEMENT =====");
        System.out.println("1. Register Student");
        System.out.println("2. View Student by ID");
        System.out.println("3. List All Students");
        System.out.println("4. Update Student");
        System.out.println("5. Deactivate Student");
        System.out.println("6. Mark Attendance");
        System.out.println("7. View Attendance by Student");
        System.out.println("8. View Attendance by Date");
        System.out.println("9. Add Class");
        System.out.println("10. List Classes");
        System.out.println("11. Add Student Mark");
        System.out.println("12. View Final Marks");
        System.out.println("0. Exit");
    }

    // ---------- Menu Actions ----------

private void registerStudent() {

    System.out.print("Admission No: ");
    String admissionNo = scanner.nextLine();

    System.out.print("First Name: ");
    String firstName = scanner.nextLine();

    System.out.print("Last Name: ");
    String lastName = scanner.nextLine();

    System.out.print("Date of Birth (YYYY-MM-DD): ");
    LocalDate dob = LocalDate.parse(scanner.nextLine());

    System.out.print("Gender (Male/Female/Other): ");
    String genderInput = scanner.nextLine().trim();
    genderInput = genderInput.substring(0,1).toUpperCase()
                + genderInput.substring(1).toLowerCase();

    Gender gender = Gender.valueOf(genderInput);


    System.out.print("Grade ID: ");
    int gradeId = Integer.parseInt(scanner.nextLine());

    // ✅ CREATE STUDENT HERE
    Student student = new Student();

    student.setAdmissionNo(admissionNo);
    student.setFirstName(firstName);
    student.setLastName(lastName);
    student.setDateOfBirth(dob);
    student.setGender(gender);
    student.setGradeId(gradeId);

    boolean success = studentService.registerStudent(student);

    if (success) {
        System.out.println("Student registered successfully.");
    } else {
        System.out.println("Failed to register student.");
    }
}


    private void viewStudent() {
        int id = readInt("Enter Student ID: ");

        Optional<Student> student = studentService.findStudentById(id);
        student.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Student not found.")
        );
    }

    private void listStudents() {
        List<Student> students = studentService.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        students.forEach(System.out::println);
    }

    private void updateStudent() {
        int id = readInt("Enter Student ID to update: ");

        Optional<Student> optionalStudent = studentService.findStudentById(id);
        if (optionalStudent.isEmpty()) {
            System.out.println("Student not found.");
            return;
        }

        Student student = optionalStudent.get();

        System.out.print("New First Name (" + student.getFirstName() + "): ");
        student.setFirstName(scanner.nextLine());

        System.out.print("New Last Name (" + student.getLastName() + "): ");
        student.setLastName(scanner.nextLine());

        boolean success = studentService.updateStudent(student);
        System.out.println(success ? "Student updated." : "Update failed.");
    }

    private void deactivateStudent() {
        int id = readInt("Enter Student ID to deactivate: ");

        boolean success = studentService.deactivateStudent(id);
        System.out.println(success ? "Student deactivated." : "Operation failed.");
    }

    private void markAttendance() {
    try {
        System.out.print("Student ID: ");
        int studentId = Integer.parseInt(scanner.nextLine());

        System.out.print("Class ID: ");
        int classId = Integer.parseInt(scanner.nextLine());

        System.out.print("Date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        System.out.print("Status (PRESENT/ABSENT): ");
        String status = scanner.nextLine().toUpperCase();

        Attendance attendance =
            new Attendance(studentId, classId, date, status);

        boolean success = attendanceService.markAttendance(attendance);

        System.out.println(success
            ? "Attendance marked successfully."
            : "Failed to mark attendance.");

    } catch (Exception e) {
        System.out.println("Invalid input.");
    }
}


private void viewAttendanceByDate() {
    System.out.print("Date (YYYY-MM-DD): ");
    LocalDate date = LocalDate.parse(scanner.nextLine());

    var records = attendanceService.getAttendanceReportByDate(date);

    if (records.isEmpty()) {
        System.out.println("No attendance records found.");
        return;
    }

    System.out.println("\nID | Name                | Class | Status");
    System.out.println("------------------------------------------------");

    for (var r : records) {
        System.out.printf("%d | %-18s | %d | %s%n",
            r.getStudentId(),
            r.getFullName(),
            r.getClassId(),
            r.getStatus());
    }
}
private void viewAttendanceByStudent() {
    System.out.print("Student ID: ");
    int studentId = Integer.parseInt(scanner.nextLine());

    var records = attendanceService.getAttendanceReportByStudent(studentId);

    if (records.isEmpty()) {
        System.out.println("No attendance records found.");
        return;
    }

    System.out.println("\nDate       | Class | Status");
    System.out.println("--------------------------------");

    for (var r : records) {
        System.out.printf("%s | %d | %s%n",
            r.getDate(),
            r.getClassId(),
            r.getStatus());
    }
}

private void addClass() {
    System.out.print("Class name: ");
    String name = scanner.nextLine();

    System.out.print("Academic year: ");
    String year = scanner.nextLine();

    boolean success =
        classService.addClass(new SchoolClass(name, year));

    System.out.println(success
        ? "Class added successfully."
        : "Failed to add class.");
}

private void listClasses() {
    var classes = classService.getAllClasses();

    if (classes.isEmpty()) {
        System.out.println("No classes found.");
        return;
    }

    System.out.println("\nID | Class Name              | Year");
    System.out.println("----------------------------------------");

    for (var c : classes) {
        System.out.printf("%d | %-22s | %s%n",
            c.getClassId(),
            c.getClassName(),
            c.getAcademicYear());
    }
}

private void addStudentMark() {
    System.out.print("Student ID: ");
    int studentId = Integer.parseInt(scanner.nextLine());

    System.out.print("Assessment ID: ");
    int assessmentId = Integer.parseInt(scanner.nextLine());

    System.out.print("Mark: ");
    double mark = Double.parseDouble(scanner.nextLine());

    boolean success = marksService.addMark(studentId, assessmentId, mark);

    System.out.println(success ? "Mark recorded." : "Failed to add mark.");
}


private void viewFinalMarks() {
    System.out.print("Student ID: ");
    int studentId = Integer.parseInt(scanner.nextLine());
    marksService.printFinalMarks(studentId);
}





    // ---------- Helpers ----------

    private int readInt(String message) {
        System.out.print(message);
        int value = scanner.nextInt();
        scanner.nextLine(); // clear buffer
        return value;
    }
}
