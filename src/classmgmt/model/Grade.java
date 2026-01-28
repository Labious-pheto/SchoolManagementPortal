package classmgmt.model;

public class Grade {

    private int gradeId;
    private String gradeName;

    public Grade(int gradeId, String gradeName) {
        this.gradeId = gradeId;
        this.gradeName = gradeName;
    }

    public int getGradeId() {
        return gradeId;
    }

    public String getGradeName() {
        return gradeName;
    }

    @Override
    public String toString() {
        return gradeName;
    }
}
