package attendance.service;

import attendance.dao.AttendanceDAO;
import attendance.model.Attendance;
import attendance.model.AttendanceReport;


import java.time.LocalDate;
import java.util.List;


public class AttendanceService {

    private final AttendanceDAO attendanceDAO = new AttendanceDAO();

    public boolean markAttendance(Attendance attendance) {
        return attendanceDAO.markAttendance(attendance);
    }

    public List<Attendance> getAttendanceByStudent(int studentId) {
    return attendanceDAO.getAttendanceByStudent(studentId);
}

    public List<Attendance> getAttendanceByDate(LocalDate date) {
    return attendanceDAO.getAttendanceByDate(date);
}

public List<AttendanceReport> getAttendanceReportByDate(LocalDate date) {
    return attendanceDAO.getAttendanceReportByDate(date);
}

public List<AttendanceReport> getAttendanceReportByStudent(int studentId) {
    return attendanceDAO.getAttendanceReportByStudent(studentId);
}


}
