package classmgmt.dao;

import classmgmt.model.Grade;
import core.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradeDAO {

    public Grade findById(int id) {
        String sql = "SELECT * FROM grades WHERE grade_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Grade(
                        rs.getInt("grade_id"),
                        rs.getString("grade_name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Grade> findAll() {
        List<Grade> grades = new ArrayList<>();
        String sql = "SELECT * FROM grades";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                grades.add(new Grade(
                        rs.getInt("grade_id"),
                        rs.getString("grade_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }
}
