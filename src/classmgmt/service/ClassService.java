package classmgmt.service;

import classmgmt.dao.ClassDAO;
import classmgmt.model.SchoolClass;

import java.util.List;

public class ClassService {

    private final ClassDAO classDAO = new ClassDAO();

    public boolean addClass(SchoolClass schoolClass) {
        return classDAO.addClass(schoolClass);
    }

    public List<SchoolClass> getAllClasses() {
        return classDAO.getAllClasses();
    }
}
