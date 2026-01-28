package classmgmt.model;

public class SchoolClass {

    private int classId;
    private String className;
    private String academicYear;

    public SchoolClass() {}

    public SchoolClass(String className, String academicYear) {
        this.className = className;
        this.academicYear = academicYear;
    }

    public int getClassId() { return classId; }
    public void setClassId(int classId) { this.classId = classId; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }
}
