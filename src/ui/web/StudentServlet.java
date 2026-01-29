package ui.web;

import student.service.StudentService;
import student.model.Gender;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;

public class StudentServlet extends HttpServlet {

    private StudentService studentService;

    @Override
    public void init() {
        studentService = new StudentService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String admissionNo = req.getParameter("admissionNo");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        LocalDate dob = LocalDate.parse(req.getParameter("dob"));
        Gender gender = Gender.valueOf(req.getParameter("gender").toUpperCase());

        studentService.registerStudent(
                admissionNo,
                firstName,
                lastName,
                dob,
                gender
        );

        resp.sendRedirect("/students.html");
    }
}
