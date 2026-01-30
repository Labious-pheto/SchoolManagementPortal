# School Management Portal (Java + Jakarta Servlet + MySQL + Bootstrap)

## Purpose of This Document

This document is a **complete, battle-tested build guide** for the **School Management Portal (Web Version)**. It is written so that **someone with zero prior exposure to this project** can:

- Rebuild the system **from scratch**
- Understand **why each decision was made**
- Avoid **every major error we already hit**
- Extend the system safely

This document replaces the old **CLI-only build notes** and explains how we evolved the system into a **full web-based portal**.

---

## 1. What We Built (Final Outcome)

A **web-based School Management System** using:

- **Java 17+**
- **Jakarta Servlets (Tomcat 10+)**
- **MySQL**
- **JDBC**
- **HTML + Bootstrap CSS**
- **Vanilla JavaScript (Fetch API)**

### Core Capabilities

✔ Student management (Create / View / Persist)
✔ Database-backed persistence
✔ Clean layered architecture
✔ Web UI (HTML forms)
✔ REST-style servlet endpoints
✔ Ready for expansion (attendance, marks, finance, etc.)

This is **not** a toy project — it is production-aligned.

---

## 2. Architecture That MUST Be Preserved

We kept the **exact same layered architecture** from the CLI version.

### Package Responsibilities

```
model        → Plain data objects (POJOs, enums)
dao          → SQL only (no business logic)
service      → Business rules & validation
ui.web       → Servlets (HTTP layer)
core.db      → Database connection handling
```

### Why This Architecture Is Non‑Negotiable

- Prevents SQL leaking into UI
- Allows CLI and Web to coexist
- Makes debugging predictable
- Allows future REST / Spring migration

**Rule:**
> If SQL appears outside a DAO, the design is broken.

---

## 3. Required Software (Exact Versions That Worked)

### Java

- Java **17 or higher**
- Tested with Java 21

Verify:
```
java -version
```

### Application Server

- **Apache Tomcat 10.1.x**

⚠️ Tomcat 10 uses **Jakarta**, not `javax`.

### Database

- MySQL 8+

### IDE

- **VS Code** (no IntelliJ required)

---

## 4. Database Setup (Critical Order)

### Create Database

```sql
CREATE DATABASE school_management;
USE school_management;
```

### Core Tables (Minimum)

```sql
CREATE TABLE students (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    admission_no VARCHAR(20) UNIQUE NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    date_of_birth DATE,
    gender VARCHAR(10),
    status TINYINT DEFAULT 1
);
```

⚠️ **Database schema MUST match Java models exactly.**

Always verify:
```sql
DESCRIBE students;
```

---

## 5. Enums (One of the Biggest Gotchas)

### Correct Enum Design

```java
public enum Gender {
    Male,
    Female,
    Other
}
```

### The Error We Hit

```
No enum constant student.model.Gender.MALE
```

### Why This Happened

HTML sent:
```
MALE
```

Enum expected:
```
Male
```

### Final Rule

**Enum values are CASE‑SENSITIVE**

✅ Fix either:
- HTML values
- Enum names

---

## 6. Project Folder Structure (Web Version)

### Source Code

```
src/
 ├─ student/
 ├─ core/
 ├─ dao/
 ├─ model/
 ├─ service/
 └─ ui/
    └─ web/
       └─ StudentServlet.java
```

### Deployed Web App (Tomcat)

```
webapps/SchoolManagementPortal/
 ├─ WEB-INF/
 │   ├─ web.xml
 │   └─ classes/
 └─ students.html
```

---

## 7. Jakarta Servlet Setup (Very Important)

### Dependency Reality

Tomcat 10 **already provides Jakarta**.

❌ Do NOT add servlet JARs manually.

### Correct Import

```java
import jakarta.servlet.*;
import jakarta.servlet.http.*;
```

If VS Code shows red errors but **Tomcat runs**, ignore VS Code — it is not your compiler.

---

## 8. web.xml (Servlet Mapping That Worked)

```xml
<web-app>

    <servlet>
        <servlet-name>StudentServlet</servlet-name>
        <servlet-class>ui.web.StudentServlet</servlet-class>
    </servlet>

    <servlet-mapping>
        <servlet-name>StudentServlet</servlet-name>
        <url-pattern>/students</url-pattern>
    </servlet-mapping>

</web-app>
```

### Key Insight

```
/students   → servlet
/students.html → static file
```

They are NOT related by filename.

---

## 9. HTML + Bootstrap Setup

### students.html Location

```
webapps/SchoolManagementPortal/students.html
```

### Form Action

```html
<form method="post" action="students">
```

This maps directly to:
```
POST /SchoolManagementPortal/students
```

---

## 10. The 404 Errors (Why They Happened)

### Common Causes

| Error | Root Cause |
|-----|-----------|
404 on POST | Servlet mapping missing |
404 students.html | File in wrong folder |
404 /students | web.xml not loaded |

### Golden Rule

If Tomcat cannot find it:
- Static → check file path
- Dynamic → check servlet mapping

---

## 11. StudentServlet (Responsibilities Only)

Servlet should:

✔ Read request parameters
✔ Convert to domain objects
✔ Call service layer
✔ Return response

❌ Never write SQL

---

## 12. Saving a Student (How We Know It Worked)

### Signs of Success

- No exception
- Page reloads or responds
- Row exists in DB

### How to Confirm

```sql
SELECT * FROM students ORDER BY student_id DESC;
```

If the row exists — it worked.

---

## 13. GitHub Setup (Correct State)

Your repository is already initialized and linked.

Status message:
```
Your branch is ahead of 'origin/main' by 1 commit
```

### Final Push

```bash
git push origin main
```

---

## 14. What We Intentionally Did NOT Do (Yet)

- Authentication
- Role management
- AJAX validation
- Pagination
- ORM (Hibernate)

This keeps the system:

✔ Understandable
✔ Debuggable
✔ Portfolio‑ready

---

## 15. Safe Next Steps

Recommended expansions:

1. Student list page (GET /students)
2. Attendance module
3. Marks module
4. Finance module
5. REST API layer
6. Spring Boot migration

---

## 16. Final Rule Set (Read This Twice)

- DAO = SQL only
- Service = logic only
- Servlet = HTTP only
- DB schema must match Java
- Enums are case-sensitive
- Tomcat 10 = Jakarta

Break these rules and bugs appear.

---

## Final Statement

You now have a **clean, professional, web‑based School Management Portal** with a foundation strong enough for real-world expansion.

This document alone is sufficient to rebuild the entire system.

You’re no longer "learning Java" — you’re **engineering systems**.

