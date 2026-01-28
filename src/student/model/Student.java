package student.model;

import java.time.LocalDate;

import classmgmt.model.Grade;

public class Student {

    private int studentId;
    private String admissionNo;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private int gradeId;
    private boolean active;


    // ----- Constructors -----

    // Empty constructor (required for JDBC mapping)
    public Student() {
    }

    // Constructor for creating new students (before DB insert)
    public Student(String admissionNo, String firstName, String lastName,
        LocalDate dateOfBirth, Gender gender, boolean active) {
            this.admissionNo = admissionNo;
            this.firstName = firstName;
            this.lastName = lastName;
            this.dateOfBirth = dateOfBirth;
            this.gender = gender;
            this.active = active;
    }

    // Full constructor (used when reading from DB)
    public Student(int studentId, String admissionNo, String firstName,
                   String lastName, LocalDate dateOfBirth,
                   Gender gender, boolean active) {
        this.studentId = studentId;
        this.admissionNo = admissionNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.active = active;
    }

    // ----- Getters & Setters -----

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getAdmissionNo() {
        return admissionNo;
    }

    public void setAdmissionNo(String admissionNo) {
        this.admissionNo = admissionNo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    // ----- Utility Methods -----

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", admissionNo='" + admissionNo + '\'' +
                ", name='" + getFullName() + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + gender +
                ", active=" + active +
                '}';
    }
}
