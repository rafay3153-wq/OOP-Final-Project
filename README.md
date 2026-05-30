# Student Enrollment System 🎓

A comprehensive Java-based student enrollment management system with a graphical user interface. This system enables students, instructors, and administrators to manage course enrollments, grades, transcripts, and perform various educational administrative tasks.

---

## 📋 Table of Contents

- [Features](#features)
- [System Architecture](#system-architecture)
- [File Structure](#file-structure)
- [How to Run](#how-to-run)
- [User Roles & Permissions](#user-roles--permissions)
- [Validation & Security](#validation--security)
- [Data Persistence](#data-persistence)
- [Class Descriptions](#class-descriptions)

---

## ✨ Features

### Student Features
- **Course Enrollment**: Register for courses
- **Course Dropping**: Drop enrolled courses
- **Transcript Management**: View academic transcripts with completed courses
- **Grade Viewing**: Check grades for enrolled courses
- **CGPA Tracking**: Automatic CGPA calculation based on grades and credit hours
- **Eligibility Checking**: System validates enrollment eligibility

### Instructor Features
- **Course Assignment**: View assigned courses
- **Student Management**: View enrolled students in courses
- **Grade Assignment**: Assign and submit grades for students
- **Course Details**: Access complete course information

### Admin Features
- **User Management**: Add/remove students, instructors, and admins
- **Course Management**: Add and remove courses from the system
- **Enrollment Management**: Manage student enrollments
- **Instructor Assignment**: Assign instructors to courses
- **System Reports**: Generate performance and enrollment reports

### General Features
- **User Authentication**: Secure login system for all user types
- **Data Persistence**: Save and load system state using serialization
- **Input Validation**: Comprehensive validation for emails, phone numbers, course codes, and passwords
- **GUI Interface**: User-friendly graphical interface for all operations

---

## 🏗️ System Architecture

The system follows **Object-Oriented Programming (OOP)** principles with clear separation of concerns:

```
┌─────────────────────────────────────────────┐
│          GUI Layer (Swing)                  │
│  Login → Dashboard → Enrollment/AdminPanel  │
└────────────────────┬────────────────────────┘
                     │
┌────────────────────▼────────────────────────┐
│       Logic Layer (Backend)                 │
│  Person, Student, Instructor, Admin         │
├─────────────────────────────────────────────┤
│  Course, Enrollment, Transcript             │
│  PerformanceAnalyzer, ValidationUtility     │
└────────────────────┬────────────────────────┘
                     │
┌────────────────────▼────────────────────────┐
│       Data Persistence Layer                │
│  FileManager (Serialization)                │
│  system.dat (System State)                  │
└─────────────────────────────────────────────┘
```

---

## 📁 File Structure

```
OOP-Final-Project/
├── GUIApp.java                    # Main entry point
├── StudentEnrollmentSystem.java   # Core logic (all classes)
├── Login.java                     # User authentication GUI
├── Dashboard.java                 # Main dashboard GUI
├── EnrollmentGUI.java             # Course enrollment GUI
├── AdminPanel.java                # Admin management GUI
├── system.dat                     # Serialized system state (Store data)
└── README.md                      # This file
```

---

## ▶️ How to Run

### Compile the Project
   ```bash
   javac *.java
   ```
   This will compile all Java files and generate `.class` files.
   
### Starting the Application
```bash
java GUIApp
```

The application will start with the Login screen. Use the following test credentials:

**Student Account:**
- ID: `1001`
- Password: `Student@123`

**Instructor Account:**
- ID: `2001`
- Password: `Instructor@123`

**Admin Account:**
- ID: `3001`
- Password: `Admin@123`

### Workflow

1. **Login**: Enter your ID and password on the login screen
2. **Dashboard**: After successful login, access role-specific dashboard
3. **Perform Actions**: Based on your role, perform the appropriate actions
4. **Logout**: Click logout to return to the login screen

---

## 👥 User Roles & Permissions

### Student Role
| Permission | Description |
|-----------|-------------|
| View Dashboard | Access personal academic information |
| Enroll in Courses | Register for available courses (max 5) |
| Drop Courses | Remove enrolled courses |
| View Grades | Check grades for enrolled courses |
| View Transcript | Access complete academic transcript |
| View CGPA | Check cumulative GPA |

### Instructor Role
| Permission | Description |
|-----------|-------------|
| View Assigned Courses | See courses assigned to them |
| View Enrolled Students | List all students in each course |
| Assign Grades | Enter and submit grades for students |
| View Course Details | Access complete course information |

### Admin Role
| Permission | Description |
|-----------|-------------|
| Manage Students | Add/remove student accounts |
| Manage Instructors | Add/remove instructor accounts |
| Manage Courses | Add/remove courses from system |
| Assign Instructors | Assign instructors to courses |
| Manage Enrollments | Override student enrollments |
| Generate Reports | Create performance reports |

---

## 🔒 Validation & Security

### Input Validation Rules

| Field | Rules |
|-------|-------|
| **Email** | Must contain @ and . |
| **Phone** | Format: +923001234567 (13 digits) |
| **Name** | 3-50 characters, letters and spaces only |
| **Password** | 8-20 chars, 1+ uppercase, 1+ lowercase, 1+ digit |
| **Course Code** | 2-4 letters + 2-4 digits (e.g., CS101) |
| **Credit Hours** | 1-4 hours |
| **Grade** | A, A-, B+, B, B-, C+, C, C-, D, F |

### Security Features
- **Password Validation**: Strong password requirements enforced
- **Input Sanitization**: Remove potentially harmful characters
- **Exception Handling**: Comprehensive error handling throughout
- **Data Validation**: All inputs validated before processing
- **Role-Based Access Control**: Each role has specific permissions

---

## 💾 Data Persistence

The system uses **Java Serialization** to persist data:

### How It Works
1. All data objects (Students, Courses, Enrollments, etc.) implement `Serializable`
2. `FileManager` class handles all save/load operations
3. System state is saved to `system.dat` file
4. Data is automatically restored when the application starts

### Data Files
- `system.dat`: Complete system state (students, instructors, admins, courses)

### Key Methods
```java
// Save system state
FileManager.saveSystemState(students, instructors, admins, courses, "system.dat");

// Load system state
ArrayList<Object> state = FileManager.loadSystemState("system.dat");
```

---

## 📚 Class Descriptions

### Detailed Class Breakdown

#### **StudentEnrollmentSystem.java Contents**
This is the main file containing all core classes:

1. **Person** - Base class (abstract)
2. **Student** - Student user type
3. **Instructor** - Instructor user type
4. **Admin** - Administrator user type
5. **Course** - Course entity
6. **Enrollment** - Student-Course relationship
7. **Transcript** - Academic transcript
8. **PerformanceAnalyzer** - Academic analytics
9. **ValidationUtility** - Input validation
10. **FileManager** - Data persistence

#### **GUI Classes**
- **GUIApp**: Application entry point
- **Login**: Authentication interface
- **Dashboard**: Main user interface (different for each role)
- **EnrollmentGUI**: Course enrollment interface
- **AdminPanel**: Administrative management interface

---

## 📊 Grade Point Scale

| Grade | Points |
|-------|--------|
| A | 4.0 |
| A- | 3.7 |
| B+ | 3.3 |
| B | 3.0 |
| B- | 2.7 |
| C+ | 2.3 |
| C | 2.0 |
| C- | 1.7 |
| D | 1.0 |
| F | 0.0 |

**CGPA Calculation**: (Sum of (Grade Points × Credit Hours)) / Total Credit Hours

---

## 🐛 Troubleshooting

| Issue | Solution |
|-------|----------|
| **Compilation Error** | Ensure all 6 Java files are in the same directory |
| **Cannot Find Class** | Run `javac *.java` first to compile all files |
| **Login Fails** | Check ID format (numeric) and password case sensitivity |
| **Data Not Saving** | Ensure write permissions in the project directory |
| **GUI Doesn't Appear** | Check Java version (JDK 11+) and run with `java GUIApp` |

---

## 📝 Example Usage

### Enrolling in a Course (Student)
1. Login as Student
2. Go to Dashboard
3. Click "Enroll in Course"
4. Select available course from list
5. Click "Enroll" button
6. Confirmation message displays

### Assigning Grades (Instructor)
1. Login as Instructor
2. View assigned courses
3. Select course and view students
4. Enter grade for each student
5. Submit grades

### Managing System (Admin)
1. Login as Admin
2. Access Admin Panel
3. Choose operation: Add Student, Add Course, etc.
4. Enter required information
5. Confirm changes

---

## 🎯 Learning Outcomes

This project demonstrates:
- **OOP Concepts**: Inheritance, Polymorphism, Encapsulation, Abstraction
- **Data Structures**: ArrayList for dynamic collections
- **GUI Development**: Swing components and event handling
- **File I/O**: Serialization for data persistence
- **Input Validation**: Comprehensive validation using utility classes
- **Exception Handling**: Try-catch blocks for robust error 
