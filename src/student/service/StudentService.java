package student.service;

import student.dao.StudentDAO;
import student.model.Student;
import student.model.Gender;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class StudentService {

    private final StudentDAO studentDAO;

    public StudentService() {
        this.studentDAO = new StudentDAO();
    }

    // ---------- Register New Student ----------
    public boolean registerStudent(String admissionNo,
                                   String firstName,
                                   String lastName,
                                   LocalDate dateOfBirth,
                                   Gender gender) {

        // Business rules
        if (admissionNo == null || admissionNo.isBlank()) {
            throw new IllegalArgumentException("Admission number is required");
        }
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name is required");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name is required");
        }
        if (dateOfBirth.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date of birth cannot be in the future");
        }

        Student student = new Student(
                admissionNo,
                firstName,
                lastName,
                dateOfBirth,
                gender,true
        );

        return studentDAO.insertStudent(student);
    }

    // ---------- Register New Student (FULL OBJECT) ----------
public boolean registerStudent(Student student) {

    if (student == null) {
        throw new IllegalArgumentException("Student cannot be null");
    }

    if (student.getAdmissionNo() == null || student.getAdmissionNo().isBlank()) {
        throw new IllegalArgumentException("Admission number is required");
    }

    if (student.getFirstName() == null || student.getFirstName().isBlank()) {
        throw new IllegalArgumentException("First name is required");
    }

    if (student.getLastName() == null || student.getLastName().isBlank()) {
        throw new IllegalArgumentException("Last name is required");
    }

    if (student.getDateOfBirth().isAfter(LocalDate.now())) {
        throw new IllegalArgumentException("Date of birth cannot be in the future");
    }

    if (student.getGradeId() <= 0) {
        throw new IllegalArgumentException("Valid grade ID is required");
    }

    return studentDAO.insertStudent(student);
}

    // ---------- Find Student ----------
    public Optional<Student> findStudentById(int studentId) {
        if (studentId <= 0) {
            throw new IllegalArgumentException("Invalid student ID");
        }
        return studentDAO.getStudentById(studentId);
    }

    // ---------- List All Students ----------
    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    // ---------- Update Student ----------
    public boolean updateStudent(Student student) {
        if (student.getStudentId() <= 0) {
            throw new IllegalArgumentException("Student ID is required for update");
        }
        return studentDAO.updateStudent(student);
    }

    // ---------- Deactivate Student ----------
    public boolean deactivateStudent(int studentId) {
        if (studentId <= 0) {
            throw new IllegalArgumentException("Invalid student ID");
        }
        return studentDAO.deactivateStudent(studentId);
    }

    // ---------- Reactivate Student (extra polish) ----------
    public boolean reactivateStudent(Student student) {
        student.setActive(true);
        return studentDAO.updateStudent(student);
    }
}
