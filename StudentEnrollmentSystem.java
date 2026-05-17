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
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public abstract String getDetails();

    public boolean logIn() {
        return true;
    }

    public void logOut() {
        System.out.println(name + " logged out.");
    }
}

class Student extends Person {
    private int semester;
    private double cgpa;
    ArrayList<Course> enrolledCourses;

    public Student(String name, String email, String phoneNumber, int semester, double cgpa, int id, String password) {
        super(name, email, phoneNumber, id, password);
        this.semester = semester;
        this.cgpa = cgpa;
        enrolledCourses = new ArrayList<>();
    }

    public boolean registerCourse(Course course) {
        if (enrolledCourses.size() < 5) {
            enrolledCourses.add(course);
            return true;
        }
        return false;
    }

    public boolean dropCourse(Course course) {
        return enrolledCourses.remove(course);
    }

    public String getDetails() {
        return "Student Name: " + name + ", Email: " + email + ", Phone: " + phoneNumber + ", Semester: " + semester
                + ", CGPA: " + cgpa;
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
            Enrollment e = enrolledCourses.get(i).getEnrollmentFor(this);
            if (e != null && e.isGraded()) {
                totalPoints += e.getGradePoints() * enrolledCourses.get(i).getCreditHours();
                totalCredits += enrolledCourses.get(i).getCreditHours();
            }
        }
        if (totalCredits == 0) {
            return 0.0;
        }
        return Math.round((totalPoints / totalCredits) * 100.0) / 100.0;
    }

    public void updateCGPA() {
        this.cgpa = calculateCGPA();
    }

    public boolean isEligibleToEnroll(Course course) {
        if (course == null) {
            return false;
        }
        if (enrolledCourses.contains(course)) {
            return false;
        }
        if (enrolledCourses.size() >= 5) {
            return false;
        }
        if (course.isFull()) {
            return false;
        }
        return true;
    }

    public ArrayList<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public ArrayList<Enrollment> viewGrades() {
        ArrayList<Enrollment> grades = new ArrayList<>();
        for (int i = 0; i < enrolledCourses.size(); i++) {
            Enrollment e = enrolledCourses.get(i).getEnrollmentFor(this);
            if (e != null) {
                grades.add(e);
            }
        }
        return grades;
    }

    public Transcript getTranscript() {
        return viewTranscript();
    }

    public int belongsToSemester() {
        return semester;
    }
}

class Instructor extends Person {
    private String department;
    private String designation;
    private ArrayList<Course> assignedCourses;

    public Instructor(String name, String email, String phoneNumber, String department, String designation, int id,
            String password) {
        super(name, email, phoneNumber, id, password);
        this.department = department;
        this.designation = designation;
        assignedCourses = new ArrayList<>();
    }

    public void addCourseTaught(Course course) {
        assignedCourses.add(course);
    }

    public void removeCourseTaught(Course course) {
        assignedCourses.remove(course);
    }

    public String getDetails() {
        return "Instructor Name: " + name + ", Email: " + email + ", Phone: " + phoneNumber + ", Department: "
                + department + ", Designation: " + designation;
    }

    public ArrayList<Course> getAssignedCourses() {
        return assignedCourses;
    }

    public ArrayList<Student> viewEnrolledStudents(Course course) {
        return course.getEnrolledStudents();
    }

    public void assignGrade(Enrollment enrollment, String grade) {
        enrollment.setGrade(grade);
    }

    public void submitGrades(Course course) {
        ArrayList<Enrollment> enrollments = course.getEnrollments();
        for (int i = 0; i < enrollments.size(); i++) {
            if (!enrollments.get(i).isGraded()) {
                System.out.println(
                        enrollments.get(i).getStudent().getName() + " has no grade in " + course.getCourseName());
            }
        }
    }
}

class Admin extends Person {
    private int adminLevel;
    private ArrayList<String> permissions;

    public Admin(String name, String email, String phoneNumber, int id, String password) {
        super(name, email, phoneNumber, id, password);
        this.adminLevel = 0;
        this.permissions = new ArrayList<>();
    }

    public String getDetails() {
        return "Admin Name: " + name + ", Email: " + email + ", Phone: " + phoneNumber;
    }

    public boolean addCourse(Course course) {
        return true;
    }

    public boolean removeCourse(Course course) {
        return true;
    }

    public boolean addInstructor(Instructor instructor) {
        return true;
    }

    public boolean removeInstructor(Instructor i) {
        return true;
    }

    public boolean addStudent(Student student) {
        return true;
    }

    public boolean removeStudent(Student student) {
        return true;
    }

    public String generateReport() {
        return "Report Generated";
    }

    public void assignInstructorToCourse(Instructor instructor, Course course) {
        instructor.addCourseTaught(course);
        course.setInstructor(instructor);
    }

    public void manageEnrollments(Student student, Course course, boolean enroll) {
        if (enroll) {
            student.registerCourse(course);
        } else {
            student.dropCourse(course);
        }
    }
}

class ValidationUtility {
    public static boolean validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        if (!email.contains("@")) {
            return false;
        }
        if (!email.contains(".")) {
            return false;
        }

        int atIndex = email.indexOf("@");
        int dotIndex = email.lastIndexOf(".");

        if (atIndex == 0) {
            return false;
        }
        if (dotIndex < atIndex) {
            return false;
        }
        if (dotIndex == email.length() - 1) {
            return false;
        }
        if (email.indexOf("@") != email.lastIndexOf("@")) {
            return false;
        }
        return true;
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return false;
        }
        if (!phoneNumber.startsWith("+92")) {
            return false;
        }
        if (phoneNumber.length() != 13) {
            return false;
        }
        String digits = phoneNumber.substring(3); // strip "+92"
        for (int i = 0; i < digits.length(); i++) {
            char c = digits.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    public static boolean validateCourseCode(String courseCode) {
        if (courseCode == null || courseCode.trim().isEmpty()) {
            return false;
        }
        if (courseCode.length() < 4 || courseCode.length() > 8) {
            return false;
        }
        int splitAt = -1;
        for (int i = 0; i < courseCode.length(); i++) {
            if (courseCode.charAt(i) >= '0' && courseCode.charAt(i) <= '9') {
                splitAt = i;
                break;
            }
        }
        if (splitAt == -1) {
            return false;
        }
        if (splitAt == 0) {
            return false;
        }
        String letters = courseCode.substring(0, splitAt);
        String digits = courseCode.substring(splitAt);
        if (letters.length() < 2 || letters.length() > 4) {
            return false;
        }
        if (digits.length() < 2 || digits.length() > 4) {
            return false;
        }
        for (int i = 0; i < letters.length(); i++) {
            char c = letters.charAt(i);
            if (!((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))) {
                return false;
            }
        }
        for (int i = 0; i < digits.length(); i++) {
            if (digits.charAt(i) < '0' || digits.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

   public static boolean validateName(String name) {
    if (name == null || name.trim().isEmpty()) {
        return false;
    }
    if (name.trim().length() < 3 || name.trim().length() > 50) {
        return false;
    }
    for (int i = 0; i < name.length(); i++) {
        char c = name.charAt(i);
        boolean isLetter = (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
        boolean isSpace  = (c == ' ');
        if (!isLetter && !isSpace) {
            return false;
        }
    }
    if (!name.contains(" ")) {
        return false;
    }
    return true;
}
    public static boolean validatePassword(String password) {
        if (password == null) {
            return false;
        }
        if (password.length() < 8 || password.length() > 20) {
            return false;
        }
        boolean hasDigit = false;
        boolean hasUpper = false;
        boolean hasLower = false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (c >= '0' && c <= '9') {
                hasDigit = true;
            }
            if (c >= 'A' && c <= 'Z') {
                hasUpper = true;
            }
            if (c >= 'a' && c <= 'z') {
                hasLower = true;
            }
        }
        if (!hasDigit) {
            return false;
        }
        if (!hasUpper) {
            return false;
        }
        if (!hasLower) {
            return false;
        }
        return true;
    }

    public static boolean validateGrade(String grade) {
        if (grade == null || grade.trim().isEmpty()) {
            return false;
        }
        String[] validGrades = { "A", "A-", "B+", "B", "B-",
                "C+", "C", "C-", "D", "F" };
        String trimmed = grade.trim().toUpperCase();
        for (int i = 0; i < validGrades.length; i++) {
            if (trimmed.equals(validGrades[i])) {
                return true;
            }
        }
        return false;
    }

    public static boolean validateCreditHours(int creditHours) {
        return creditHours >= 1 && creditHours <= 4;
    }

    public static boolean validateId(int id) {
        return id > 0;
    }

    public static String sanitizeInput(String input) {
        if (input == null) {
            return "";
        }
        input = input.trim();
        input = input.replace("<", "");
        input = input.replace(">", "");
        input = input.replace("\"", "");
        input = input.replace(";", "");
        input = input.replace("\\", "");
        return input;
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
    try {
        if(student == null) {
            throw new IllegalArgumentException("Student cannot be null.");
        }
        if(isFull()) {
            throw new IllegalStateException("Course is full.");
        }
        if(enrolledStudents.contains(student)) {
            throw new IllegalArgumentException("Student already enrolled.");
        }
        enrolledStudents.add(student);
        return true;
    } catch(IllegalArgumentException | IllegalStateException e) {
        System.out.println("Enrollment Error: " + e.getMessage());
        return false;
        }
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
    try {
        if (grade == null) {
            throw new IllegalArgumentException("Grade cannot be null.");
        }
        if(!(grade.equalsIgnoreCase("A") || grade.equalsIgnoreCase("B") || grade.equalsIgnoreCase("C") ||
            grade.equalsIgnoreCase("D") || grade.equalsIgnoreCase("F"))) {
            throw new IllegalArgumentException("Invalid grade entered.");
        }
        this.grade = grade.toUpperCase();
        this.gradePoints = calculateGradePoints();
    } catch(Exception e) {
        System.out.println("Grade Assignment Error: " + e.getMessage());
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
        try {
        if (sem <= 0) {
            throw new IllegalArgumentException("Semester must be greater than 0.");
        }
        double totalGradePoints = 0.0;
        int totalCreditHours = 0;
        for (Enrollment e : completedCourses) {
            if (e.getSemester() == sem && e.isGraded()) {
                totalGradePoints += e.getGradePoints() * e.getCourse().getCreditHours();
                totalCreditHours += e.getCourse().getCreditHours();
            }
        }
        if(totalCreditHours == 0) {
            throw new IllegalStateException("No graded courses found for semester " + sem);
        }
        return totalGradePoints / totalCreditHours;
    } catch(IllegalArgumentException | IllegalStateException e) {
        System.out.println("Error calculating semester GPA: " + e.getMessage());
        return 0.0;
    } catch(ArithmeticException e) {
        System.out.println("Arithmetic error calculating semester GPA: " + e.getMessage());
        return 0.0;
    } catch(Exception e) {
        System.out.println("Unexpected error calculating semester GPA: " + e.getMessage());
        return 0.0;
    }
}
    public int getCompletedCredits() {
        int credits=0;
        for(Enrollment e : completedCourses) {
            if(e.isGraded() && e.getGrade() != null && !e.getGrade().equalsIgnoreCase("F")) {
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
class FileManager {
    public static void saveCourses(ArrayList<Course> courses, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(courses);
            System.out.println("Courses saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving courses: " + e.getMessage());
        }
    }
    public static ArrayList<Course> loadCourses(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            ArrayList<Course> courses = (ArrayList<Course>) in.readObject();
            System.out.println("Courses loaded from " + filename);
            return courses;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading courses: " + e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    public static void saveEnrollments(ArrayList<Enrollment> enrollments, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(enrollments);
            System.out.println("Enrollments saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving enrollments: " + e.getMessage());
        }
    }
    public static ArrayList<Enrollment> loadEnrollments(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            ArrayList<Enrollment> enrollments = (ArrayList<Enrollment>) in.readObject();
            System.out.println("Enrollments loaded from " + filename);
            return enrollments;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading enrollments: " + e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    public static void saveTranscripts(ArrayList<Transcript> transcripts, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(transcripts);
            System.out.println("Transcripts saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving transcripts: " + e.getMessage());
        } 
    }
    public static ArrayList<Transcript> loadTranscripts(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            ArrayList<Transcript> transcripts = (ArrayList<Transcript>) in.readObject();
            System.out.println("Transcripts loaded from " + filename);
            return transcripts;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading transcripts: " + e.getMessage());
            return new ArrayList<>();
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
