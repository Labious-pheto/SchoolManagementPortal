package attendance.dao;

import attendance.model.Attendance;
import core.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import attendance.model.AttendanceReport;


public class AttendanceDAO {

    private static final String INSERT =
        "INSERT INTO attendance (student_id, class_id, attendance_date, status) " +
        "VALUES (?, ?, ?, ?)";

    public boolean markAttendance(Attendance attendance) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT)) {

            ps.setInt(1, attendance.getStudentId());
            ps.setInt(2, attendance.getClassId());
            ps.setDate(3, java.sql.Date.valueOf(attendance.getAttendanceDate()));
            ps.setString(4, attendance.getStatus());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.err.println("Attendance error: " + e.getMessage());
            return false;
        }
    }  // ✅ THIS BRACE WAS MISSING

    // ================= REPORT METHODS =================

    public List<Attendance> getAttendanceByStudent(int studentId) {
        List<Attendance> list = new ArrayList<>();

        String sql =
            "SELECT * FROM attendance WHERE student_id = ? ORDER BY attendance_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Attendance a = new Attendance();
                a.setAttendanceId(rs.getInt("attendance_id"));
                a.setStudentId(rs.getInt("student_id"));
                a.setClassId(rs.getInt("class_id"));
                a.setAttendanceDate(
                    rs.getDate("attendance_date").toLocalDate()
                );
                a.setStatus(rs.getString("status"));
                list.add(a);
            }

        } catch (Exception e) {
            System.err.println("Report error: " + e.getMessage());
        }
        return list;
    }

    public List<Attendance> getAttendanceByDate(LocalDate date) {
        List<Attendance> list = new ArrayList<>();

        String sql =
            "SELECT * FROM attendance WHERE attendance_date = ? ORDER BY student_id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(date));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Attendance a = new Attendance();
                a.setAttendanceId(rs.getInt("attendance_id"));
                a.setStudentId(rs.getInt("student_id"));
                a.setClassId(rs.getInt("class_id"));
                a.setAttendanceDate(
                    rs.getDate("attendance_date").toLocalDate()
                );
                a.setStatus(rs.getString("status"));
                list.add(a);
            }

        } catch (Exception e) {
            System.err.println("Report error: " + e.getMessage());
        }
        return list;
    }
    public List<AttendanceReport> getAttendanceReportByDate(LocalDate date) {
    List<AttendanceReport> list = new ArrayList<>();

    String sql =
        "SELECT a.student_id, " +
        "       CONCAT(s.first_name, ' ', s.last_name) AS full_name, " +
        "       a.class_id, a.attendance_date, a.status " +
        "FROM attendance a " +
        "JOIN students s ON a.student_id = s.student_id " +
        "WHERE a.attendance_date = ? " +
        "ORDER BY full_name";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setDate(1, java.sql.Date.valueOf(date));
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            list.add(new AttendanceReport(
                rs.getInt("student_id"),
                rs.getString("full_name"),
                rs.getInt("class_id"),
                rs.getDate("attendance_date").toLocalDate(),
                rs.getString("status")
            ));
        }

    } catch (Exception e) {
        System.err.println("Join report error: " + e.getMessage());
    }

    return list;
}
public List<AttendanceReport> getAttendanceReportByStudent(int studentId) {
    List<AttendanceReport> list = new ArrayList<>();

    String sql =
        "SELECT a.student_id, " +
        "       CONCAT(s.first_name, ' ', s.last_name) AS full_name, " +
        "       a.class_id, a.attendance_date, a.status " +
        "FROM attendance a " +
        "JOIN students s ON a.student_id = s.student_id " +
        "WHERE a.student_id = ? " +
        "ORDER BY a.attendance_date DESC";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, studentId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            list.add(new AttendanceReport(
                rs.getInt("student_id"),
                rs.getString("full_name"),
                rs.getInt("class_id"),
                rs.getDate("attendance_date").toLocalDate(),
                rs.getString("status")
            ));
        }

    } catch (Exception e) {
        System.err.println("Join report error: " + e.getMessage());
    }

    return list;
}

}
