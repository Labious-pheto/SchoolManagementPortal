package marks.dao;

import core.db.DBConnection;
import java.sql.*;

public class MarksDAO {

    public boolean insertMark(int studentId, int assessmentId, double mark) {
        String sql = """
            INSERT INTO student_marks (student_id, assessment_id, mark)
            VALUES (?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, assessmentId);
            ps.setDouble(3, mark);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("DB error: " + e.getMessage());
            return false;
        }
    }

    public ResultSet getFinalMarksByStudent(int studentId) throws SQLException {
        String sql = """
            SELECT
                t.subject_name,
                ROUND(AVG(t.term_avg), 2) AS final_mark
            FROM (
                SELECT
                    subj.subject_name,
                    a.term,
                    SUM(sm.mark * a.weight / a.max_mark) AS term_avg
                FROM student_marks sm
                JOIN assessments a ON sm.assessment_id = a.assessment_id
                JOIN subjects subj ON a.subject_id = subj.subject_id
                WHERE sm.student_id = ?
                GROUP BY subj.subject_name, a.term
            ) t
            GROUP BY t.subject_name
        """;

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, studentId);
        return ps.executeQuery();
    }
}
