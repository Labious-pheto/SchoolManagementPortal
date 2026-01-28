package attendance.model;

import java.time.LocalDate;

public class Attendance {

    private int attendanceId;
    private int studentId;
    private int classId;
    private LocalDate attendanceDate;
    private String status;

    public Attendance() {}

    public Attendance(int studentId, int classId,
                      LocalDate attendanceDate, String status) {
        this.studentId = studentId;
        this.classId = classId;
        this.attendanceDate = attendanceDate;
        this.status = status;
    }

    public int getAttendanceId() { return attendanceId; }
    public void setAttendanceId(int attendanceId) { this.attendanceId = attendanceId; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getClassId() { return classId; }
    public void setClassId(int classId) { this.classId = classId; }

    public LocalDate getAttendanceDate() { return attendanceDate; }
    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
