package marks.service;

import marks.dao.MarksDAO;
import java.sql.ResultSet;

public class MarksService {

    private final MarksDAO dao = new MarksDAO();

    public boolean addMark(int studentId, int assessmentId, double mark) {
        if (mark < 0 || mark > 100) {
            System.out.println("Invalid mark.");
            return false;
        }
        return dao.insertMark(studentId, assessmentId, mark);
    }

    public void printFinalMarks(int studentId) {
        try {
            ResultSet rs = dao.getFinalMarksByStudent(studentId);

            System.out.println("\nSubject | Final Mark | Result");
            System.out.println("--------------------------------");

            while (rs.next()) {
                double finalMark = rs.getDouble("final_mark");
                String result = finalMark >= 40 ? "PASS" : "FAIL";

                System.out.printf(
                    "%s | %.2f | %s%n",
                    rs.getString("subject_name"),
                    finalMark,
                    result
                );
            }

        } catch (Exception e) {
            System.out.println("Error loading marks: " + e.getMessage());
        }
    }
}
