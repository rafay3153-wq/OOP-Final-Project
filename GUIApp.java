/**
 * GUIApp.java - Main Entry Point for the Application
 * 
 * This is the ONLY file you need to run to start the application.
 * 
 * How to use:
 * 1. Place StudentEnrollmentSystem.java and this file in the same folder
 * 2. Place Login_Updated.java, Dashboard_Updated.java, Enrollment_Updated.java, AdminPanel_Updated.java in the same folder
 * 3. Rename the updated files (remove "_Updated" from names)
 * 4. Compile: javac *.java
 * 5. Run: java GUIApp
 * 
 * File Structure:
 * StudentEnrollmentSystem.java (your backend - contains Person, Student, Instructor, Admin, Course, etc.)
 * GUIApp.java (this file - entry point)
 * Login.java (GUI login screen)
 * Dashboard.java (main dashboard)
 * Enrollment.java (course enrollment)
 * AdminPanel.java (admin management)
 */

public class GUIApp {
    public static void main(String[] args) {
        // Start the application with the Login screen
        // The Login class will use classes from StudentEnrollmentSystem.java
        new Login();
    }
}
