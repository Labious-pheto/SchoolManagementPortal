package ui.web;

import student.service.StudentService;
import student.model.Gender;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/students")
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
        String genderParam = req.getParameter("gender");
        Gender gender;
        try {
            gender = Gender.valueOf(genderParam);
        } catch (IllegalArgumentException e) {
    throw new ServletException("Invalid gender value: " + genderParam);
}

        studentService.registerStudent(
                admissionNo,
                firstName,
                lastName,
                dob,
                gender
        );

        resp.sendRedirect("students.html");
    }
}
