package marks.model;

public class StudentMark {

    private int studentId;
    private int assessmentId;
    private double mark;

    public StudentMark(int studentId, int assessmentId, double mark) {
        this.studentId = studentId;
        this.assessmentId = assessmentId;
        this.mark = mark;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public double getMark() {
        return mark;
    }
}
