USE school_management;

-- ======================
-- Grades
-- ======================
CREATE TABLE grades (
    grade_id INT AUTO_INCREMENT PRIMARY KEY,
    grade_name VARCHAR(20) NOT NULL UNIQUE
);

-- ======================
-- Students
-- ======================
CREATE TABLE students (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    admission_no VARCHAR(20) NOT NULL UNIQUE,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    date_of_birth DATE NOT NULL,
    gender ENUM('Male','Female','Other') NOT NULL,
    grade_id INT NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (grade_id) REFERENCES grades(grade_id)
);

-- ======================
-- Classes
-- ======================
CREATE TABLE classes (
    class_id INT AUTO_INCREMENT PRIMARY KEY,
    class_name VARCHAR(50) NOT NULL,
    grade_id INT NOT NULL,
    academic_year VARCHAR(9) NOT NULL,
    FOREIGN KEY (grade_id) REFERENCES grades(grade_id)
);

-- ======================
-- Attendance
-- ======================
CREATE TABLE attendance (
    attendance_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    class_id INT NOT NULL,
    attendance_date DATE NOT NULL,
    status ENUM('PRESENT','ABSENT') NOT NULL,
    UNIQUE (student_id, class_id, attendance_date),
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (class_id) REFERENCES classes(class_id)
);

-- ======================
-- Subjects
-- ======================
CREATE TABLE subjects (
    subject_id INT AUTO_INCREMENT PRIMARY KEY,
    subject_name VARCHAR(50) NOT NULL UNIQUE
);

-- ======================
-- Student Subjects (7 per student)
-- ======================
CREATE TABLE student_subjects (
    student_id INT NOT NULL,
    subject_id INT NOT NULL,
    PRIMARY KEY (student_id, subject_id),
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (subject_id) REFERENCES subjects(subject_id)
);

-- ======================
-- Assessments
-- ======================
CREATE TABLE assessments (
    assessment_id INT AUTO_INCREMENT PRIMARY KEY,
    subject_id INT NOT NULL,
    term INT NOT NULL,
    assessment_name VARCHAR(50),
    max_mark INT NOT NULL,
    weight DECIMAL(5,2) NOT NULL,
    FOREIGN KEY (subject_id) REFERENCES subjects(subject_id)
);

-- ======================
-- Student Marks
-- ======================
CREATE TABLE student_marks (
    student_id INT NOT NULL,
    assessment_id INT NOT NULL,
    mark INT NOT NULL,
    PRIMARY KEY (student_id, assessment_id),
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (assessment_id) REFERENCES assessments(assessment_id)
);
