import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EnrollmentGUI extends JFrame {
    private Student student;
    private JTextField searchField;
    private JTable coursesTable;
    private DefaultTableModel tableModel;
    private JLabel statusLabel;
    private ArrayList<Student> allStudents;
    private ArrayList<Instructor> allInstructors;
    private ArrayList<Admin> allAdmins;
    private ArrayList<Course> allCourses;


    public EnrollmentGUI(Student student, ArrayList<Student> students, ArrayList<Instructor> instructors, ArrayList<Admin> admins, ArrayList<Course> courses) {
        this.student = student;
        this.allStudents = students;
        this.allInstructors = instructors;
        this.allAdmins = admins;
        this.allCourses = courses;

        // Frame setup
        setTitle("Course Enrollment - " + student.getName());
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Header
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Center: Search + Table
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(240, 240, 240));

        JPanel searchPanel = createSearchPanel();
        centerPanel.add(searchPanel, BorderLayout.NORTH);

        JPanel tablePanel = createTablePanel();
        centerPanel.add(tablePanel, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Status panel
        JPanel statusPanel = createStatusPanel();
        mainPanel.add(statusPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    /**
     * Create header panel with title and student info
     */
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));

        JLabel titleLabel = new JLabel("Enroll in Courses");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLACK);

        // Use backend student data
        JLabel infoLabel = new JLabel("Student: " + student.getName()
                + " | CGPA: " + student.calculateCGPA()
                + " | Enrolled: " + student.enrolledCourses.size() + "/5");
        infoLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
        infoLabel.setForeground(Color.DARK_GRAY);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(240, 240, 240));
        leftPanel.add(titleLabel);
        leftPanel.add(infoLabel);

        panel.add(leftPanel, BorderLayout.WEST);
        return panel;
    }

    /**
     * Create search panel
     */
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel searchLabel = new JLabel("Search Course:");
        searchLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));

        searchField = new JTextField(20);
        searchField.setFont(new Font("Tahoma", Font.PLAIN, 11));

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchButton.addActionListener(e -> searchCourses());

        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearButton.addActionListener(e -> clearSearch());

        panel.add(searchLabel);
        panel.add(searchField);
        panel.add(searchButton);
        panel.add(clearButton);

        return panel;
    }

    /**
     * Create table panel with courses
     */
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 240, 240));

        // Create table model
        String[] columns = { "Course ID", "Course Name", "Credits", "Instructor", "Capacity", "Enrolled",
                "Available" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Create table
        coursesTable = new JTable(tableModel);
        coursesTable.setFont(new Font("Tahoma", Font.PLAIN, 11));
        coursesTable.setRowHeight(25);
        coursesTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 11));

        // Populate table with backend course data
        populateTable(allCourses);

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(coursesTable);
        scrollPane.setBackground(new Color(240, 240, 240));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Populate table with courses from backend
     */
    private void populateTable(ArrayList<Course> courses) {
        tableModel.setRowCount(0);
        for (Course course : courses) {
            Object[] row = {
                    course.getCourseId(),
                    course.getCourseName(),
                    course.getCreditHours(),
                    course.getInstructor().getName(),
                    course.getMaxEnrollment(),
                    course.getEnrolledCount(),
                    course.getMaxEnrollment() - course.getEnrolledCount()
            };
            tableModel.addRow(row);
        }
    }

    /**
     * Create status panel with action buttons
     */
    private JPanel createStatusPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        statusLabel = new JLabel("Select a course and click Enroll to register");
        statusLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
        statusLabel.setForeground(Color.DARK_GRAY);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(240, 240, 240));

        JButton enrollButton = new JButton("Enroll Selected");
        enrollButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        enrollButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        enrollButton.addActionListener(e -> enrollSelected());

        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(e -> dispose());

        buttonPanel.add(enrollButton);
        buttonPanel.add(closeButton);

        panel.add(statusLabel, BorderLayout.WEST);
        panel.add(buttonPanel, BorderLayout.EAST);

        return panel;
    }

    /**
     * Search courses by name or ID
     */
    private void searchCourses() {
        String searchTerm = searchField.getText().toLowerCase().trim();

        if (searchTerm.isEmpty()) {
            populateTable(allCourses);
            return;
        }

        ArrayList<Course> filteredCourses = new ArrayList<>();
        for (Course course : allCourses) {
            if (course.getCourseName().toLowerCase().contains(searchTerm) ||
                    course.getCourseId().toLowerCase().contains(searchTerm)) {
                filteredCourses.add(course);
            }
        }

        populateTable(filteredCourses);
    }

    /**
     * Clear search and show all courses
     */
    private void clearSearch() {
        searchField.setText("");
        populateTable(allCourses);
    }


    private void enrollSelected() {
        int selectedRow = coursesTable.getSelectedRow();

        if (selectedRow == -1) {
            statusLabel.setText("Please select a course to enroll");
            statusLabel.setForeground(new Color(220, 38, 38));
            return;
        }

        // Get course ID from table
        String courseId = (String) tableModel.getValueAt(selectedRow, 0);

        // Find course object from shared list
        Course selectedCourse = null;
        for (Course course : allCourses) {
            if (course.getCourseId().equals(courseId)) {
                selectedCourse = course;
                break;
            }
        }

        if (selectedCourse == null) {
            statusLabel.setText("Course not found");
            statusLabel.setForeground(new Color(220, 38, 38));
            return;
        }

        // Check if course is full using backend isFull()
        if (selectedCourse.isFull()) {
            statusLabel.setText("Cannot enroll: Course is full");
            statusLabel.setForeground(new Color(220, 38, 38));
            return;
        }

        // Check if already enrolled
        if (student.enrolledCourses.contains(selectedCourse)) {
            statusLabel.setText("Cannot enroll: Already enrolled in this course");
            statusLabel.setForeground(new Color(220, 38, 38));
            return;
        }

        try {
            // Use backend registerCourse() method
            if (student.registerCourse(selectedCourse)) {
                selectedCourse.addStudent(student);

                // FIX: Check if enrollment already exists to avoid duplicates
                Enrollment existingEnrollment = selectedCourse.getEnrollmentFor(student);
                if (existingEnrollment == null) {
                    // Create Enrollment object for grade tracking only if it doesn't exist
                    int enrollId = (int) (System.currentTimeMillis() % 100000);
                    Enrollment enrollment = new Enrollment(enrollId, student, selectedCourse, "",
                            java.time.LocalDate.now().toString(), "ENROLLED", student.getSemester());
                    selectedCourse.addEnrollment(enrollment);
                } else {
                    // Ensure existing enrollment has correct status
                    if (existingEnrollment.getStatus().equals("DROPPED")) {
                        existingEnrollment.assignGrade(""); // Reset grade if previously dropped
                    }
                }

                // Save enrollments to file using FileManager
                try {
                    FileManager.saveSystemState(allStudents, allInstructors, allAdmins, allCourses, "system.dat");
                    
                    // Also save enrollments separately
                    ArrayList<Enrollment> allEnrollments = new ArrayList<>();
                    for (Course c : allCourses) {
                        allEnrollments.addAll(c.getEnrollments());
                    }
                    FileManager.saveEnrollments(allEnrollments, "enrollments.dat");
                } catch (Exception fileEx) {
                    System.out.println("Warning: Could not save enrollments to file: " + fileEx.getMessage());
                }

                statusLabel.setText("Successfully enrolled in " + selectedCourse.getCourseName());
                statusLabel.setForeground(new Color(34, 197, 94));
                populateTable(allCourses);
            } else {
                statusLabel.setText("Enrollment failed. Maximum 5 courses allowed");
                statusLabel.setForeground(new Color(220, 38, 38));
            }
        } catch (IllegalArgumentException ex) {
            statusLabel.setText("Enrollment error: " + ex.getMessage());
            statusLabel.setForeground(new Color(220, 38, 38));
        } catch (IllegalStateException ex) {
            statusLabel.setText("Course full: " + ex.getMessage());
            statusLabel.setForeground(new Color(220, 38, 38));
        } catch (Exception ex) {
            statusLabel.setText("Unexpected error: " + ex.getMessage());
            statusLabel.setForeground(new Color(220, 38, 38));
        }
    }
}