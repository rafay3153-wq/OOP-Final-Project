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

