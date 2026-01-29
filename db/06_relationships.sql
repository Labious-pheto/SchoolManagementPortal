USE school_management;

INSERT INTO student_subjects (student_id, subject_id)
SELECT s.student_id, sub.subject_id
FROM students s
JOIN subjects sub
LIMIT 28;
