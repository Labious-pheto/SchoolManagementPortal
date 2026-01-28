package student.dao;

import core.db.DBConnection;
import student.model.Student;
import student.model.Gender;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDAO {

    // ---------- CREATE ----------
    public boolean insertStudent(Student student) {
        String sql = """
            INSERT INTO students
            (admission_no, first_name, last_name, date_of_birth, gender, grade_id, active)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {



            ps.setString(1, student.getAdmissionNo());
            ps.setString(2, student.getFirstName());
            ps.setString(3, student.getLastName());
            ps.setDate(4, Date.valueOf(student.getDateOfBirth()));
            ps.setString(5, student.getGender().name()); // enum → String
            //ps.setInt(6, student.getGrade().getGradeId()); // Grade object → FK
            ps.setInt(6, student.getGradeId());
            ps.setBoolean(7, student.isActive());

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------- READ (by ID) ----------
    public Optional<Student> getStudentById(int studentId) {
        String sql = "SELECT * FROM students WHERE student_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToStudent(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // ---------- READ (all) ----------
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                students.add(mapResultSetToStudent(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // ---------- UPDATE ----------
    public boolean updateStudent(Student student) {
        String sql = """
            UPDATE students
            SET first_name = ?, last_name = ?, date_of_birth = ?, gender = ?, active = ?
            WHERE student_id = ?
            """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, student.getFirstName());
            ps.setString(2, student.getLastName());
            ps.setDate(3, Date.valueOf(student.getDateOfBirth()));
            ps.setString(4, student.getGender().name());
            ps.setBoolean(5, student.isActive());
            ps.setInt(6, student.getStudentId());

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------- SOFT DELETE (Deactivate) ----------
    public boolean deactivateStudent(int studentId) {
        String sql = "UPDATE students SET status = 'INACTIVE' WHERE student_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------- Helper Mapper ----------
private Student mapResultSetToStudent(ResultSet rs) throws SQLException {

    return new Student(
        rs.getInt("student_id"),
        rs.getString("admission_no"),
        rs.getString("first_name"),
        rs.getString("last_name"),
        rs.getDate("date_of_birth").toLocalDate(),
        Gender.valueOf(rs.getString("gender")),
        rs.getBoolean("active")
    );
}

}
