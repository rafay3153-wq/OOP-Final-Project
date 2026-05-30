import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.border.EmptyBorder; // Added for padding

/**
 * Login Class - Handles user authentication
 * 
 * This class uses the backend classes from StudentEnrollmentSystem.java:
 * - Person, Student, Instructor, Admin classes
 * - ValidationUtility for input validation
 * - FileManager for loading user data
 * 
 * Responsibilities:
 * - Display login form with ID and password fields
 * - Validate user credentials using backend
 * - Authenticate users from backend data
 * - Launch Dashboard on successful login
 */
public class Login extends JFrame {
    private JTextField idField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel errorLabel;
    private Person currentUser;
    private ArrayList<Student> students;
    private ArrayList<Instructor> instructors;
    private ArrayList<Admin> admins;
    private ArrayList<Course> courses;

    public Login() {
        // Initialize demo users (in real app, load from FileManager)
        initializeDemoUsers();

        // Frame setup
        setTitle("Student Enrollment System - Login");
        setSize(500, 260); // Compact size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20)); // Reduced padding

        // Form panel inside titled border
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(new Color(240, 240, 240));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(),
                        " Login ",
                        javax.swing.border.TitledBorder.LEFT,
                        javax.swing.border.TitledBorder.TOP,
                        new Font("Tahoma", Font.PLAIN, 16),
                        new Color(255, 140, 0)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        // ID Label and Field
        JLabel idLabel = new JLabel("User ID:");
        idLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        idLabel.setForeground(Color.BLACK);

        idField = new JTextField(13);
        idField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                new EmptyBorder(5, 5, 5, 5)));

        // Password Label and Field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        passwordLabel.setForeground(Color.BLACK);

        passwordField = new JPasswordField(13);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                new EmptyBorder(5, 5, 5, 5)));

        // Simple grid for aligning labels and text fields horizontally
        JPanel fieldsPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        fieldsPanel.setBackground(new Color(240, 240, 240));

        fieldsPanel.add(idLabel);
        fieldsPanel.add(idField);
        fieldsPanel.add(passwordLabel);
        fieldsPanel.add(passwordField);

        // Error Label
        errorLabel = new JLabel("");
        errorLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Login Button
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        loginButton.setPreferredSize(new Dimension(150, 26));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(new LoginAction());

        // Assemble Form Panel
        formPanel.add(Box.createVerticalGlue()); // Absorb excess space at top
        formPanel.add(fieldsPanel);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(errorLabel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(loginButton);
        formPanel.add(Box.createVerticalGlue()); // Absorb excess space at bottom

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);
        setVisible(true);
    }

    /**
     * Initialize demo users using backend classes
     */
    @SuppressWarnings("unchecked")
    private void initializeDemoUsers() {
        // Try loading from new text-based files
        ArrayList<Object> state = FileManager.loadSystemState("system.dat");

        if (state != null && state.size() == 4) {
            students = (ArrayList<Student>) state.get(0);
            instructors = (ArrayList<Instructor>) state.get(1);
            admins = (ArrayList<Admin>) state.get(2);
            courses = (ArrayList<Course>) state.get(3);
        } else {
            students = new ArrayList<>();
            instructors = new ArrayList<>();
            admins = new ArrayList<>();
            courses = new ArrayList<>();

            // Create demo students using Student class from StudentEnrollmentSystem
            students.add(new Student("Ali Ahmed", "ali@university.edu", "+923001234567", 1, 3.5, 1001, "Student@123"));
            students.add(new Student("Fatima Khan", "fatima@university.edu", "+923001234568", 2, 3.8, 1002, "Student@123"));

            // Create demo instructors using Instructor class from StudentEnrollmentSystem
            Instructor inst1 = new Instructor("Dr. Hassan", "hassan@university.edu", "+923001234569", "Computer Science",
                    "Professor", 2001, "Instructor@123");
            Instructor inst2 = new Instructor("Dr. Ayesha", "ayesha@university.edu", "+923001234570", "Mathematics",
                    "Associate Professor", 2002, "Instructor@123");
            instructors.add(inst1);
            instructors.add(inst2);

            // Create demo admin using Admin class from StudentEnrollmentSystem
            admins.add(new Admin("Admin User", "admin@university.edu", "+923001234571", 3001, "Admin@123"));

            // Create demo courses and link to instructors
            Course cs101 = new Course("CS101", "Introduction to Programming", 3, 30, inst1);
            Course cs102 = new Course("CS102", "Data Structures", 3, 25, inst1);
            Course cs103 = new Course("CS103", "Web Development", 3, 20, inst1);
            Course math201 = new Course("MATH201", "Calculus I", 4, 35, inst2);
            Course eng101 = new Course("ENG101", "English Composition", 3, 40, inst2);
            courses.add(cs101);
            courses.add(cs102);
            courses.add(cs103);
            courses.add(math201);
            courses.add(eng101);

            // Assign courses to instructors
            inst1.addCourseTaught(cs101);
            inst1.addCourseTaught(cs102);
            inst1.addCourseTaught(cs103);
            inst2.addCourseTaught(math201);
            inst2.addCourseTaught(eng101);

            // Save the newly created demo data using text-based system
            FileManager.saveSystemState(students, instructors, admins, courses, "system.dat");
        }
    }

    /**
     * Inner class to handle login button action
     */
    private class LoginAction implements ActionListener {
        
        public void actionPerformed(ActionEvent e) {
            String id = idField.getText().trim();
            String password = new String(passwordField.getPassword());

            // Validate input using ValidationUtility from backend
            if (id.isEmpty() || password.isEmpty()) {
                errorLabel.setText("Please enter both ID and password");
                return;
            }

            // Authenticate user
            currentUser = authenticateUser(id, password);

            if (currentUser != null) {
                errorLabel.setText("");
                // Open Dashboard and close Login
                new Dashboard(currentUser, students, instructors, admins, courses);
                dispose();
            } else {
                errorLabel.setText("Invalid ID or password");
                passwordField.setText("");
            }
        }
    }

    /**
     * Authenticate user credentials using backend data
     * Checks against Student, Instructor, and Admin lists
     */
    private Person authenticateUser(String id, String password) {
        int userId;
        try {
            userId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return null;
        }

        // Check students
        for (Student student : students) {
            if (student.getId() == userId && student.getPassword().equals(password)) {
                return student;
            }
        }

        // Check instructors
        for (Instructor instructor : instructors) {
            if (instructor.getId() == userId && instructor.getPassword().equals(password)) {
                return instructor;
            }
        }

        // Check admins
        for (Admin admin : admins) {
            if (admin.getId() == userId && admin.getPassword().equals(password)) {
                return admin;
            }
        }

        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login());
    }
}
