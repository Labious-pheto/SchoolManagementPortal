USE school_management;

SELECT 'Students' AS table_name, COUNT(*) FROM students;
SELECT 'Grades' AS table_name, COUNT(*) FROM grades;
SELECT 'Classes' AS table_name, COUNT(*) FROM classes;
SELECT 'Attendance' AS table_name, COUNT(*) FROM attendance;
SELECT 'Subjects' AS table_name, COUNT(*) FROM subjects;

-- Orphan check
SELECT s.student_id
FROM students s
LEFT JOIN grades g ON s.grade_id = g.grade_id
WHERE g.grade_id IS NULL;
