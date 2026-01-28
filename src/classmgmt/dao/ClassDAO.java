package classmgmt.dao;

import classmgmt.model.SchoolClass;
import core.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClassDAO {

    public boolean addClass(SchoolClass schoolClass) {
        String sql =
            "INSERT INTO classes (class_name, academic_year) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, schoolClass.getClassName());
            ps.setString(2, schoolClass.getAcademicYear());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.err.println("Class insert error: " + e.getMessage());
            return false;
        }
    }

    public List<SchoolClass> getAllClasses() {
        List<SchoolClass> list = new ArrayList<>();

        String sql = "SELECT * FROM classes ORDER BY academic_year, class_name";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SchoolClass c = new SchoolClass();
                c.setClassId(rs.getInt("class_id"));
                c.setClassName(rs.getString("class_name"));
                c.setAcademicYear(rs.getString("academic_year"));
                list.add(c);
            }

        } catch (Exception e) {
            System.err.println("Class fetch error: " + e.getMessage());
        }
        return list;
    }
}
