# School Management System

A **Java-based School Management System** built using **JDBC + MySQL** with a clean layered architecture (CLI → Service → DAO → Database).
The system manages students, grades, classes, attendance, and academic marks, and is designed as a foundation for a full-scale school administration platform.

This project is intentionally built close to **real-world enterprise Java practices**, focusing on separation of concerns, data integrity, and maintainability.

---

## 📌 Features

### 🎓 Student Management

* Register new students
* View student details by ID
* List all students
* Update student information
* Activate / deactivate students

### 🏫 Academic Structure

* Grade management (Grade 8 – Grade 12)
* Class management per grade
* Student–grade relationships

### 🗓 Attendance Management

* Mark student attendance
* View attendance by student
* View attendance by date

### 📊 Marks & Assessments

* Add student marks per subject
* Weighted assessments (tests, exams, assignments)
* Calculate final subject averages

---

## 🧱 Project Architecture

```
SchoolManagementSystem/
│
├── src/
│   ├── ui/cli/              # Command-line interface
│   ├── student/
│   │   ├── model/
│   │   ├── dao/
│   │   └── service/
│   ├── attendance/
│   │   ├── model/
│   │   ├── dao/
│   │   └── service/
│   ├── classmgmt/
│   │   ├── model/
│   │   ├── dao/
│   │   └── service/
│   ├── marks/
│   │   ├── model/
│   │   ├── dao/
│   │   └── service/
│   ├── core/db/             # Database connection utilities
│   └── Main.java
│
├── lib/                     # MySQL JDBC Driver
├── out/                     # Compiled classes
├── sql/                     # Database schema & seed scripts
└── README.md
```

---

## 🗄 Database Design

* **students**
* **grades**
* **classes**
* **subjects**
* **student_subjects**
* **attendance**
* **assessments**
* **student_marks**

Relational integrity is enforced via:

* Foreign keys
* Composite primary keys
* Enum constraints (e.g. Gender)

---

## ⚙️ Technologies Used

* **Java (JDK 21+)**
* **MySQL 8+**
* **JDBC (mysql-connector-j)**
* **CLI-based UI**
* **Git & GitHub**

---

## 🚀 Getting Started

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/Labious-pheto/SchoolManagementSystem.git
cd SchoolManagementSystem
```

---

### 2️⃣ Database Setup

Create the database:

```sql
CREATE DATABASE school_management;
USE school_management;
```

Run the SQL scripts located in the `sql/` folder to create tables and seed data.

---

### 3️⃣ Configure Database Connection

Edit the file:

```
src/core/db/DBConnection.java
```

Update credentials:

```java
private static final String URL = "jdbc:mysql://localhost:3306/school_management";
private static final String USER = "root";
private static final String PASSWORD = "your_password";
```

---

### 4️⃣ Compile the Project

From the project root:

```powershell
javac -cp ".;lib/mysql-connector-j-9.6.0.jar" -d out (Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName })
```

---

### 5️⃣ Run the Application

```powershell
java -cp ".;lib/mysql-connector-j-9.6.0.jar;out" Main
```

---

## 🖥 Sample CLI Menu

```
===== STUDENT MANAGEMENT =====
1. Register Student
2. View Student by ID
3. List All Students
4. Update Student
5. Deactivate Student
6. Mark Attendance
7. View Attendance by Student
8. View Attendance by Date
9. Add Class
10. List Classes
11. Add Student Mark
12. View Final Marks
0. Exit
```

---

## 📈 Learning Objectives

This project demonstrates:

* JDBC CRUD operations
* Layered architecture (DAO / Service / UI)
* SQL relational modelling
* Exception handling
* Input validation
* Realistic backend workflows

---

## 🔮 Future Improvements

* Role-based authentication (Admin / Teacher)
* REST API (Spring Boot)
* Web or JavaFX UI
* Reporting (PDF / CSV)
* Pagination & search

---

## 👤 Author

**Labious Phetoane**
Junior Java Developer | SQL | Backend Systems

---

## 📄 License

This project is open-source and intended for educational and portfolio purposes.

---

# Database Setup & Management Guide

This section explains **everything you need to know** to create, reset, and manage the database for this project.

You do **not** need any prior database experience beyond installing MySQL.

---

## 1. Prerequisites

Before starting, ensure you have:

* **MySQL Server 8.0+** installed
* **MySQL Workbench** (recommended for beginners)
* Basic command-line access (PowerShell / Terminal)

Verify MySQL is installed:

```sql
SELECT VERSION();
```

---

## 2. Database Scripts Folder Structure

All database-related work lives in the `db/` folder at the root of the project.

```
db/
├── 00_create_database.sql        -- Creates the database
├── 01_schema.sql                 -- All tables & constraints
├── 02_seed_reference_data.sql    -- Grades and reference data
├── 03_seed_students.sql          -- Sample students
├── 04_seed_classes.sql           -- Classes per grade
├── 05_seed_subjects.sql          -- School subjects
├── 06_relationships.sql          -- Student–subject mappings
├── 07_sanity_checks.sql          -- Validation queries
└── 99_drop_all.sql               -- DANGER: wipes database
```

⚠️ **Never edit the database manually** unless you also update these scripts.

---

## 3. Creating the Database (First-Time Setup)

### Option A: Using MySQL Workbench (Recommended)

1. Open **MySQL Workbench**
2. Connect to your local MySQL instance
3. Open a **new SQL tab**
4. Run the scripts **in this exact order**:

```sql
SOURCE db/00_create_database.sql
SOURCE db/01_schema.sql
SOURCE db/02_seed_reference_data.sql
SOURCE db/03_seed_students.sql
SOURCE db/04_seed_classes.sql
SOURCE db/05_seed_subjects.sql
SOURCE db/06_relationships.sql
SOURCE db/07_sanity_checks.sql
```

If no errors appear, your database is ready.

---

### Option B: Using MySQL Command Line

From the project root:

```bash
mysql -u root -p
```

Then run:

```sql
SOURCE db/00_create_database.sql;
SOURCE db/01_schema.sql;
SOURCE db/02_seed_reference_data.sql;
SOURCE db/03_seed_students.sql;
SOURCE db/04_seed_classes.sql;
SOURCE db/05_seed_subjects.sql;
SOURCE db/06_relationships.sql;
SOURCE db/07_sanity_checks.sql;
```

---

## 4. Understanding the Database Model

### Core Entities

| Table              | Purpose                       |
| ------------------ | ----------------------------- |
| `students`         | All enrolled learners         |
| `grades`           | School grades (8–12)          |
| `classes`          | Classes per grade & year      |
| `subjects`         | School subjects               |
| `student_subjects` | Subjects per student (7 each) |
| `attendance`       | Daily attendance              |
| `assessments`      | Tests, exams, assignments     |
| `student_marks`    | Marks per assessment          |

---

## 5. Resetting the Database (⚠️ DATA LOSS)

To **wipe everything and start over**:

```sql
SOURCE db/99_drop_all.sql
```

Then repeat the setup steps in Section 3.

---

## 6. Sanity Checks (Very Important)

Always run:

```sql
SOURCE db/07_sanity_checks.sql
```

Expected results:

* Students > 0
* Grades = 5
* No orphaned foreign keys

If this fails, **do not run the Java application**.

---

## 7. Common Errors & Fixes

### ❌ `Unknown database 'school_management'`

➡ You skipped `00_create_database.sql`

### ❌ Foreign key constraint fails

➡ You ran scripts out of order

### ❌ Table does not exist

➡ Schema script did not run successfully

---

## 8. How Java Connects to the Database

The application connects using JDBC via:

```java
jdbc:mysql://localhost:3306/school_management
```

Ensure:

* Database name matches exactly
* MySQL service is running
* Credentials are correct in `DBConnection.java`

---

## 9. Best Practices

* ✅ Treat DB scripts as **source code**
* ✅ Commit all changes to Git
* ❌ Do NOT edit production DB manually
* ❌ Do NOT hardcode data in Java

---

## 10. Next Steps

Once the database is working:

1. Compile the Java project
2. Run CLI or Web UI
3. Add new modules safely

---

This database layer is intentionally **simple, strict, and predictable** to make Java development stable and beginner-friendly.

If something breaks, **reset the DB and re-run scripts** — that is expected and normal.


## 🖥 Sample CLI Menu

```
===== STUDENT MANAGEMENT =====
1. Register Student
2. View Student by ID
3. List All Students
4. Update Student
5. Deactivate Student
6. Mark Attendance
7. View Attendance by Student
8. View Attendance by Date
9. Add Class
10. List Classes
11. Add Student Mark
12. View Final Marks
0. Exit
```

---

## 📈 Learning Objectives

This project demonstrates:
- JDBC CRUD operations
- Layered architecture (DAO / Service / UI)
- SQL relational modelling
- Exception handling
- Input validation
- Realistic backend workflows

---

## 🔮 Future Improvements

- Role-based authentication (Admin / Teacher)
- REST API (Spring Boot)
- Web or JavaFX UI
- Reporting (PDF / CSV)
- Pagination & search

---

## 👤 Author

**Labious Phetoane**  
Junior Java Developer | SQL | Backend Systems
+2762 858 5758
estphetoane@gmail.com
---

## 📄 License

This project is open-source and intended for educational and portfolio purposes.

