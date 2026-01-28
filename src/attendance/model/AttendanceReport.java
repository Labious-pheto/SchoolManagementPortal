package attendance.model;

import java.time.LocalDate;

public class AttendanceReport {

    private int studentId;
    private String fullName;
    private int classId;
    private LocalDate date;
    private String status;

    public AttendanceReport(int studentId, String fullName,
                            int classId, LocalDate date, String status) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.classId = classId;
        this.date = date;
        this.status = status;
    }

    public int getStudentId() { return studentId; }
    public String getFullName() { return fullName; }
    public int getClassId() { return classId; }
    public LocalDate getDate() { return date; }
    public String getStatus() { return status; }
}
