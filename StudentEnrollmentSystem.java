import java.util.*;
import java.io.*; 

    

abstract class Person {
    protected String name;
    protected String email;
    protected String phoneNumber;
    protected int id;
    protected String password;
    public Person(String name, String email, String phoneNumber, int id, String password) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.id = id;
        this.password = password;
        //EXCEPTION HANDLING
    }
    public void setName(String name) {
        this.name = name;
        //EXCEPTION HANDLING
    }
    public String getName() {
        return name;
    }
    public void setEmail(String email) {
        this.email = email;
        //EXCEPTION HANDLING
    }
    public String getEmail() {
        return email;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        //EXCEPTION HANDLING
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setId(int id) {
        this.id = id;
        //EXCEPTION HANDLING
    }
    public int getId() {
        return id;
    }
    public void setPassword(String password) {
        this.password = password;
        //EXCEPTION HANDLING
    }
    public String getPassword() {
        return password;
    }
    public abstract String getDetails();
    public boolean logIn(){
        //EXCEPTION HANDLING
        return true;
    };
    public void logOut()
    {
        //EXCEPTION HANDLING
    }
}
class Student extends Person {
    private int Semester;
    private double cgpa;
    ArrayList<Course> enrolledCourses;
    public Student(String name, String email, String phoneNumber, int Semester, double cgpa, int id, String password) {
        super(name, email, phoneNumber, id, password);
        this.Semester = Semester;
        this.cgpa = cgpa;
        enrolledCourses = new ArrayList<>();
    }
    public boolean registerCourse(Course course) {
        if (enrolledCourses.size() < 5) {
            enrolledCourses.add(course);
            return true;
        } 
        else {
            return false; 
        }
    }
    public boolean dropCourse(Course course) {
        enrolledCourses.remove(course);
        return true;
    }
    public String getDetails() {
        return "Student Name: " + name + ", Email: " + email + ", Phone: " + phoneNumber + ", Semester: " + Semester + ", CGPA: " + cgpa;
    }
    public Transcript viewTranscript() {
        Transcript transcript = new Transcript(this);
        for (int i = 0; i < enrolledCourses.size(); i++) {
            transcript.addCourse(enrolledCourses.get(i));
        }
        return transcript;
    }
    public double calculateCGPA() {
        double totalPoints = 0;
        int totalCredits = 0;
        for (int i = 0; i < enrolledCourses.size(); i++) {
            Course course = enrolledCourses.get(i);
            totalPoints += course.getGradePoints();
            totalCredits += course.getCredits();
        }
        if(totalCredits == 0) {
            return 0;
        }
        return totalPoints / totalCredits;
    }
    public void updateCGPA() {
        this.cgpa = calculateCGPA();
    }
    public boolean isEligibleToEnroll(Course course) {

    }
    public ArrayList<Course> getEnrolledCourses() {
        return enrolledCourses;
    }
    public ArrayList<Enrollment> viewGrades() {
        ArrayList<Enrollment> grades = new ArrayList<>();
        for (int i = 0; i < enrolledCourses.size(); i++) {
            Course course = enrolledCourses.get(i);
            Enrollment enrollment = new Enrollment(this, course);
            grades.add(enrollment);
        }
        return grades;
    }
    public Transcript getTranscript() {
        Transcript transcript = new Transcript(this);
        for (int i = 0; i < enrolledCourses.size(); i++) {
            Course course = enrolledCourses.get(i);
            transcript.addCourse(course);
        }
        return transcript;
    }
    public int belonngsToSemester() {
        return Semester;
    }
}
class Instructor extends Person {
    private String department;
    private String designation;
    private ArrayList<Course> AssignedCourses;
    public Instructor(String name, String email, String phoneNumber, String department, String designation, int id, String password) {
        super(name, email, phoneNumber, id, password);
        this.department = department;
        this.designation = designation;
        AssignedCourses = new ArrayList<>();
    }
    public void addCourseTaught(Course course) {
        AssignedCourses.add(course);
    }
    public void removeCourseTaught(Course course) {
        AssignedCourses.remove(course);
    }
    public String getDetails() {
        return "Instructor Name: " + name + ", Email: " + email + ", Phone: " + phoneNumber + ", Department: " + department + ", Designation: " + designation;
    }
    public ArrayList<Course> getAssignedCourses() {
        return AssignedCourses;
    }
    public ArrayList<Student> viewEnrolledStudents(Course course) {
        ArrayList<Student> enrolledStudents = course.getEnrolledStudents();

        return enrolledStudents;
    }
    public void assignGrade(Enrollment enrollment, String grade) {
        enrollment.setGrade(grade);
    }
    public void submitGrades(Course course) {
        //EXCEPTION HANDLING
    }
}
class Validationutility {
    public static boolean validateEmail(String email) {
        // EXCEPTION HANDLING
        return true;
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        // EXCEPTION HANDLING
        return true;
    }

    public static boolean validateCourseCode(String courseCode) {
        // EXCEPTION HANDLING
        return true;
    }

    public static boolean validateName(String name) {
        // EXCEPTION HANDLING
        return true;
    }

    public static boolean validatePassword(String password) {
        // EXCEPTION HANDLING
        return true;
    }

    public static boolean validateGrade(String grade) {
        // EXCEPTION HANDLING
        return true;
    }

    public static boolean validateCreditHours(int semester) {
        // EXCEPTION HANDLING
        return true;
    }

    public static boolean validateid(int id) {
        // EXCEPTION HANDLING
        return true;
    }

    public static String sanitizeInput(String input) {
        // EXCEPTION HANDLING
        return input.trim();
    }

}
class Course implements Serializable {
    private String courseId;
    private String courseName;
    private int creditHours;
    private int maxEnrollment;
    private Instructor instructor;
    private ArrayList<Student> enrolledStudents;
    public Course(String courseId, String courseName, int creditHours, int maxEnrollment, Instructor instructor) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.maxEnrollment = maxEnrollment;
        this.creditHours = creditHours;
        this.instructor = instructor;
        this.enrolledStudents = new ArrayList<>();
    }
    public boolean addStudent(Student student) {
        if (enrolledStudents.size() < maxEnrollment && !enrolledStudents.contains(student)) {
            enrolledStudents.add(student);
            return true;
        }
        return false;
        }
        public boolean removeStudent(Student student) {
            return enrolledStudents.remove(student);
        }
        public String getCourseDetails() {
            return courseName +" ("+ courseId +") - " + creditHours + " credit hours, Instructor: " + instructor.getName();
        }
        public String getCourseId() {
            return courseId;
        }
        public int getCreditHours() {
            return creditHours;
        }
        public String toString() {
            return courseName;
        }
        public Instructor getInstructor() {
            return instructor;
        }
        public String getCourseName() {
            return courseName;
        }
        public int getMaxEnrollment() {
            return maxEnrollment;
        }
        public boolean isFull() {
            return enrolledStudents.size() >= maxEnrollment;
        }
    }
class Enrollment implements Serializable {
    private int enrollmentId;
    private Student student;
    private Course course;
    private String grade;
    private double gradePoints;
    private String enrollmentDate;
    private String status;
    private int semester;
    public Enrollment(int enrollmentId, Student student, Course course, String grade, String enrollmentDate, String enrolled, int semester) {
        this.enrollmentId = enrollmentId;
        this.student = student;
        this.course = course;
        this.grade = grade;
        this.enrollmentDate = enrollmentDate;
        this.status = enrolled;
        this.gradePoints = calculateGradePoints();
        this.semester = semester;
    }
public void assignGrade(String grade) {
    if (grade == null) {
        return; 
    }
    if(grade.equalsIgnoreCase("A") || grade.equalsIgnoreCase("B") || grade.equalsIgnoreCase("C") || 
       grade.equalsIgnoreCase("D") || grade.equalsIgnoreCase("F")) {
        this.grade = grade.toUpperCase(); 
        this.gradePoints = calculateGradePoints();
    }
}
    public double getGradePoints() {
        return gradePoints;
    }
    public int getEnrollmentId() {
        return enrollmentId;
    }
    public double calculateGradePoints() {
        if (grade == null || grade.trim().isEmpty()) {
    return 0.0;
    }
    switch (grade.toUpperCase()) {
    case "A":
        return 4.0;
    case "B":
        return 3.0;
    case "C":
        return 2.0;
    case "D":
        return 1.0;
    case "F":
        return 0.0;
    default:
        return 0.0; 
        }
    }
    public boolean isGraded() {
        return grade != null && !grade.trim().isEmpty();
    }
    public String getEnrollmentDetails() {
        return student.getName() + " enrolled in " + course.getCourseDetails() + " on " + enrollmentDate;
    }
    public Course getCourse() {
        return course;
    }
    public String getGrade() {
    return grade;
    }
    public String getStatus() {
        return status;
    }
    public String toString() {
        return student.getName() + " - " + course.getCourseName() + ": " + grade;
    }
    public int getSemester() {
        return semester;
    }
}
class Transcript implements Serializable {
    private int transcriptId;
    private Student student;
    private ArrayList<Enrollment> completedCourses;
    private double cgpa;
    private String transcriptDate;
    private int semester;
    public Transcript(Student student, int semester, int transcriptId) {
        this.student = student;
        this.completedCourses = new ArrayList<>();
        this.cgpa = 0.0;
        this.transcriptId = transcriptId;
        this.semester = semester;
        this.transcriptDate = java.time.LocalDate.now().toString();
    }
    public void addEnrollment(Enrollment e) {
        if(e != null) {
            completedCourses.add(e);
        }
    }
    public void generateTranscript() {
        System.out.println("Transcript ID: " + transcriptId);
        System.out.println("Student: " + student.getName());
        System.out.println("Semester: " + semester);
        System.out.println("Generated Date: " + transcriptDate);
        System.out.println("Completed Courses:");
        for (Enrollment e : completedCourses) {
            System.out.println(e.getCourse().getCourseName() + " | Grade: " + e.getGrade() + " | Credits: " + e.getCourse().getCreditHours()
);
        }
        System.out.println("CGPA: " + String.format("%.2f", calculateCGPA()));
    }
    public double getSemesterGPA(int sem) {
        double totalGradePoints = 0.0;
        int totalCreditHours = 0;
        for (Enrollment e : completedCourses) {
            if (e.getSemester() == sem && e.isGraded()) {
                totalGradePoints += e.getGradePoints() * e.getCourse().getCreditHours();
                totalCreditHours += e.getCourse().getCreditHours();
            }
        }
        return totalCreditHours > 0 ? totalGradePoints / totalCreditHours : 0.0;
    }
    public int getCompletedCredits() {
        int credits=0;
        for(Enrollment e : completedCourses) {
            if(e.isGraded() && !e.getGrade().equalsIgnoreCase("F")) {
                credits += e.getCourse().getCreditHours();
            }
        }
        return credits;
    }
    public void exportToPDF() {
        System.out.println("Exporting transcript to PDF for " + student.getName()+" to PDF...");
    }
    public double calculateCGPA() {
        double totalGradePoints = 0.0;
        int totalCreditHours = 0;
        for (Enrollment e : completedCourses) {
            if (e.isGraded()) {
                totalGradePoints += e.getGradePoints() * e.getCourse().getCreditHours();
                totalCreditHours += e.getCourse().getCreditHours();
            }
        }
        this.cgpa = totalCreditHours > 0 ? totalGradePoints / totalCreditHours : 0.0;
        return cgpa;
    }
    public int getSemester() {
        return semester;
    }
    public String toString() {
        return "Transcript for " + student.getName() + " with CGPA: " + String.format("%.2f", cgpa);
    }
}
class PerformanceAnalyzer implements Serializable {
    private ArrayList<Enrollment> enrollmentList;
    private ArrayList<Course> courseList;
    public PerformanceAnalyzer(ArrayList<Enrollment> enrollmentList, ArrayList<Course> courseList) {
        this.enrollmentList = enrollmentList;
        this.courseList = courseList;
    }
    public double calculateAverage(String courseId) {
        double total = 0.0;
        int count = 0;
        for (Enrollment e : enrollmentList) {
            if (e.getCourse().getCourseId().equals(courseId)) {
                total += e.getGradePoints();
                count++;
            }
        }
        return count > 0 ? total / count : 0.0;
    }
    public String getPassFailStats(Course c) {
        int passCount = 0;
        int failCount = 0;
        for (Enrollment e : enrollmentList) {
            if (e.getCourse().equals(c) && e.isGraded()) {
                if (e.getGradePoints()>= 1.0) {
                    passCount++;
                } else {
                    failCount++;
                }
            }
        }
        return "Course: " + c.getCourseName() + " - Pass: " + passCount + ", Fail: " + failCount;
    }
    public double getCoursePassRate(Course c) {
        int total=0;
        int passed=0;
        for (Enrollment e : enrollmentList) {
            if (e.getCourse().equals(c) && e.isGraded()) {
                total++;
                if (e.getGradePoints() >= 1.0) {
                    passed++;
                }
            }
        }
        return total > 0 ? (double) passed / total : 0.0;
    }
    public String toString() {
        return "PerformanceAnalyzer with " + enrollmentList.size() + " enrollments and " + courseList.size() + " courses.";
    }
}


