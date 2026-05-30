import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Dashboard Class - Main interface after login
 * 
 * This class uses backend classes from StudentEnrollmentSystem.java:
 * - Person, Student, Instructor, Admin classes
 * - Enrollment, Transcript, Course classes
 * - FileManager for data persistence
 * - ValidationUtility for input validation
 * 
 * Responsibilities:
 * - Display user-specific information based on role
 * - Show navigation menu for different modules
 * - Display data from backend (enrolled courses, CGPA, etc.)
 * - Provide access to Enrollment, Admin, and other modules
 * - Handle logout functionality
 */
public class Dashboard extends JFrame {
    private Person currentUser;
    private JPanel mainContentPanel;
    private JLabel userNameLabel;
    private JLabel userRoleLabel;
    private ArrayList<Student> students;
    private ArrayList<Instructor> instructors;
    private ArrayList<Admin> admins;
    private ArrayList<Course> courses;

    public Dashboard(Person user, ArrayList<Student> students, ArrayList<Instructor> instructors,
            ArrayList<Admin> admins, ArrayList<Course> courses) {
        this.currentUser = user;
        this.students = students;
        this.instructors = instructors;
        this.admins = admins;
        this.courses = courses;

        // Frame setup
        setTitle("Student Enrollment System - Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main container
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(new Color(240, 240, 240));

        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        container.add(headerPanel, BorderLayout.NORTH);

        // Sidebar Panel
        JPanel sidebarPanel = createSidebarPanel();
        container.add(sidebarPanel, BorderLayout.WEST);

        // Main Content Panel
        mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));
        mainContentPanel.setBackground(new Color(240, 240, 240));
        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(mainContentPanel);
        scrollPane.setBackground(new Color(240, 240, 240));
        scrollPane.getViewport().setBackground(new Color(240, 240, 240));
        container.add(scrollPane, BorderLayout.CENTER);

        // Display appropriate dashboard based on user role
        displayDashboard();

        add(container);
        setVisible(true);
    }

    /**
     * Create header panel with user info and logout button
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(240, 240, 240));
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)));

        // Left side - User info
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS));
        userInfoPanel.setBackground(new Color(240, 240, 240));

        userNameLabel = new JLabel("Welcome, " + currentUser.getName());
        userNameLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        userNameLabel.setForeground(Color.BLACK);

        userRoleLabel = new JLabel("Role: " + getUserRole());
        userRoleLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        userRoleLabel.setForeground(Color.DARK_GRAY);

        userInfoPanel.add(userNameLabel);
        userInfoPanel.add(userRoleLabel);
        headerPanel.add(userInfoPanel, BorderLayout.WEST);

        // Right side - Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.addActionListener(e -> logout());
        headerPanel.add(logoutButton, BorderLayout.EAST);

        return headerPanel;
    }

    /**
     * Create sidebar with navigation menu
     */
    private JPanel createSidebarPanel() {
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(new Color(225, 225, 225));
        sidebarPanel.setPreferredSize(new Dimension(200, 0));
        sidebarPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(20, 10, 20, 10)));

        // Dashboard button
        JButton dashboardBtn = createSidebarButton("Dashboard");
        dashboardBtn.addActionListener(e -> displayDashboard());
        sidebarPanel.add(dashboardBtn);
        sidebarPanel.add(Box.createVerticalStrut(10));

        // Role-specific buttons
        if (currentUser instanceof Student) {
            JButton enrollBtn = createSidebarButton("Enroll Courses");
            enrollBtn.addActionListener(e -> openEnrollment());
            sidebarPanel.add(enrollBtn);
            sidebarPanel.add(Box.createVerticalStrut(10));

            JButton gradesBtn = createSidebarButton("View Grades");
            gradesBtn.addActionListener(e -> viewGrades());
            sidebarPanel.add(gradesBtn);
            sidebarPanel.add(Box.createVerticalStrut(10));

            JButton transcriptBtn = createSidebarButton("Transcript");
            transcriptBtn.addActionListener(e -> viewTranscript());
            sidebarPanel.add(transcriptBtn);
        } else if (currentUser instanceof Instructor) {
            JButton gradesBtn = createSidebarButton("Assign Grades");
            gradesBtn.addActionListener(e -> assignGrades());
            sidebarPanel.add(gradesBtn);
            sidebarPanel.add(Box.createVerticalStrut(10));

            JButton studentsBtn = createSidebarButton("View Students");
            studentsBtn.addActionListener(e -> viewStudents());
            sidebarPanel.add(studentsBtn);
        } else if (currentUser instanceof Admin) {
            JButton adminBtn = createSidebarButton("Admin Panel");
            adminBtn.addActionListener(e -> openAdminPanel());
            sidebarPanel.add(adminBtn);
            sidebarPanel.add(Box.createVerticalStrut(10));

            JButton usersBtn = createSidebarButton("Manage Users");
            usersBtn.addActionListener(e -> manageUsers());
            sidebarPanel.add(usersBtn);
            sidebarPanel.add(Box.createVerticalStrut(10));

            JButton coursesBtn = createSidebarButton("Manage Courses");
            coursesBtn.addActionListener(e -> manageCourses());
            sidebarPanel.add(coursesBtn);
        }

        sidebarPanel.add(Box.createVerticalGlue());
        return sidebarPanel;
    }

    /**
     * Create styled sidebar button
     */
    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(180, 30));
        button.setFont(new Font("Tahoma", Font.PLAIN, 11));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    /**
     * Display dashboard content based on user role
     */
    private void displayDashboard() {
        mainContentPanel.removeAll();

        JLabel titleLabel = new JLabel("Dashboard");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLACK);
        mainContentPanel.add(titleLabel);
        mainContentPanel.add(Box.createVerticalStrut(15));

        if (currentUser instanceof Student) {
            displayStudentDashboard();
        } else if (currentUser instanceof Instructor) {
            displayInstructorDashboard();
        } else if (currentUser instanceof Admin) {
            displayAdminDashboard();
        }

        mainContentPanel.add(Box.createVerticalGlue());
        mainContentPanel.revalidate();
        mainContentPanel.repaint();
    }

    /**
     * Display student-specific dashboard using backend data
     */
    private void displayStudentDashboard() {
        Student student = (Student) currentUser;

        // Student info card
        JPanel infoPanel = createInfoCard("Student Information");
        infoPanel.add(new JLabel("Name: " + student.getName()));
        infoPanel.add(new JLabel("Email: " + student.getEmail()));
        infoPanel.add(new JLabel("Phone: " + student.getPhoneNumber()));
        infoPanel.add(new JLabel("CGPA: " + student.calculateCGPA()));
        infoPanel.add(new JLabel("Semester: " + student.getSemester()));
        infoPanel.add(new JLabel("Enrolled Courses: " + student.enrolledCourses.size()));
        mainContentPanel.add(infoPanel);
        mainContentPanel.add(Box.createVerticalStrut(15));

        // Enrolled courses
        JPanel coursesPanel = createInfoCard("Enrolled Courses");
        if (student.enrolledCourses.isEmpty()) {
            coursesPanel.add(new JLabel("No courses enrolled yet"));
        } else {
            for (Course course : student.enrolledCourses) {
                coursesPanel.add(new JLabel("  " + course.getCourseName() + " (" + course.getCourseId() + ")"));
            }
        }
        mainContentPanel.add(coursesPanel);
    }

    /**
     * Display instructor-specific dashboard using backend data
     */
    private void displayInstructorDashboard() {
        Instructor instructor = (Instructor) currentUser;

        JPanel infoPanel = createInfoCard("Instructor Information");
        infoPanel.add(new JLabel("Name: " + instructor.getName()));
        infoPanel.add(new JLabel("Email: " + instructor.getEmail()));
        infoPanel.add(new JLabel("Phone: " + instructor.getPhoneNumber()));
        infoPanel.add(new JLabel("Department: " + instructor.getDepartment()));
        infoPanel.add(new JLabel("Designation: " + instructor.getDesignation()));
        mainContentPanel.add(infoPanel);
        mainContentPanel.add(Box.createVerticalStrut(15));

        // Show assigned courses
        JPanel coursesPanel = createInfoCard("Assigned Courses");
        ArrayList<Course> assignedCourses = instructor.getAssignedCourses();
        if (assignedCourses.isEmpty()) {
            coursesPanel.add(new JLabel("No courses assigned yet"));
        } else {
            for (Course course : assignedCourses) {
                coursesPanel.add(new JLabel("  " + course.getCourseName() + " (" + course.getCourseId()
                        + ") - " + course.getEnrolledCount() + " students"));
            }
        }
        mainContentPanel.add(coursesPanel);
    }

    /**
     * Display admin-specific dashboard
     */
    private void displayAdminDashboard() {
        JPanel infoPanel = createInfoCard("Admin Dashboard");
        infoPanel.add(new JLabel("Welcome to Admin Panel"));
        infoPanel.add(new JLabel("Total Students: " + students.size()));
        infoPanel.add(new JLabel("Total Instructors: " + instructors.size()));
        infoPanel.add(new JLabel("Total Courses: " + courses.size()));
        infoPanel.add(new JLabel("Use the sidebar menu to manage the system"));
        mainContentPanel.add(infoPanel);
    }

    /**
     * Create styled info card with titled border
     */
    private JPanel createInfoCard(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(),
                        title,
                        javax.swing.border.TitledBorder.LEFT,
                        javax.swing.border.TitledBorder.TOP,
                        new Font("Tahoma", Font.BOLD, 12),
                        new Color(255, 140, 0)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        return panel;
    }

    // ========================== STUDENT FEATURES ==========================

    /**
     * View Grades - Shows student's grades for all enrolled courses
     * Uses student.viewGrades() which returns ArrayList<Enrollment>
     */
    private void viewGrades() {
        if (!(currentUser instanceof Student)) return;
        Student student = (Student) currentUser;

        mainContentPanel.removeAll();

        JLabel titleLabel = new JLabel("My Grades");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLACK);
        mainContentPanel.add(titleLabel);
        mainContentPanel.add(Box.createVerticalStrut(15));

        try {
            ArrayList<Enrollment> grades = student.viewGrades();

            if (grades.isEmpty()) {
                JPanel infoPanel = createInfoCard("Grade Report");
                if (student.enrolledCourses.isEmpty()) {
                    infoPanel.add(new JLabel("You are not enrolled in any courses yet."));
                    infoPanel.add(new JLabel("Go to 'Enroll Courses' to register for courses."));
                } else {
                    infoPanel.add(new JLabel("No grades have been assigned yet."));
                    infoPanel.add(new JLabel("You are enrolled in " + student.enrolledCourses.size() + " course(s)."));
                    infoPanel.add(new JLabel("Grades will appear here once your instructor assigns them."));
                }
                mainContentPanel.add(infoPanel);
            } else {
                // Grades table
                String[] columns = { "Course ID", "Course Name", "Credits", "Grade", "Grade Points", "Status" };
                DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
                    public boolean isCellEditable(int row, int col) {
                        return false;
                    }
                };

                for (Enrollment e : grades) {
                    Object[] row = {
                            e.getCourse().getCourseId(),
                            e.getCourse().getCourseName(),
                            e.getCourse().getCreditHours(),
                            e.isGraded() ? e.getGrade() : "Not Graded",
                            e.isGraded() ? String.format("%.1f", e.getGradePoints()) : "-",
                            e.getStatus()
                    };
                    tableModel.addRow(row);
                }

                JTable gradesTable = new JTable(tableModel);
                gradesTable.setFont(new Font("Tahoma", Font.PLAIN, 11));
                gradesTable.setRowHeight(25);
                gradesTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 11));

                JScrollPane scrollPane = new JScrollPane(gradesTable);
                scrollPane.setPreferredSize(new Dimension(500, 150));
                scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
                mainContentPanel.add(scrollPane);

                mainContentPanel.add(Box.createVerticalStrut(15));

                // Grade summary
                JPanel summaryPanel = createInfoCard("Grade Summary");
                summaryPanel.add(new JLabel("Current CGPA: " + String.format("%.2f", student.calculateCGPA())));
                summaryPanel.add(new JLabel("Total Enrolled Courses: " + student.enrolledCourses.size()));
                mainContentPanel.add(summaryPanel);
            }
        } catch (Exception ex) {
            JPanel errorPanel = createInfoCard("Error");
            errorPanel.add(new JLabel("Error loading grades: " + ex.getMessage()));
            mainContentPanel.add(errorPanel);
        }

        mainContentPanel.add(Box.createVerticalGlue());
        mainContentPanel.revalidate();
        mainContentPanel.repaint();
    }

    /**
     * View Transcript - Shows complete academic transcript
     * Uses student.viewTranscript() and Transcript class methods
     */
    private void viewTranscript() {
        if (!(currentUser instanceof Student)) return;
        Student student = (Student) currentUser;

        mainContentPanel.removeAll();

        JLabel titleLabel = new JLabel("Academic Transcript");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLACK);
        mainContentPanel.add(titleLabel);
        mainContentPanel.add(Box.createVerticalStrut(15));

        try {
            Transcript transcript = student.viewTranscript();

            // Student details card
            JPanel detailsPanel = createInfoCard("Student Details");
            detailsPanel.add(new JLabel("Name: " + student.getName()));
            detailsPanel.add(new JLabel("Student ID: " + student.getId()));
            detailsPanel.add(new JLabel("Email: " + student.getEmail()));
            detailsPanel.add(new JLabel("Semester: " + student.getSemester()));
            detailsPanel.add(new JLabel("Date Generated: " + java.time.LocalDate.now().toString()));
            mainContentPanel.add(detailsPanel);
            mainContentPanel.add(Box.createVerticalStrut(10));

            // Course record table
            if (student.enrolledCourses.isEmpty()) {
                JPanel coursesPanel = createInfoCard("Course Record");
                coursesPanel.add(new JLabel("No courses enrolled yet."));
                mainContentPanel.add(coursesPanel);
            } else {
                String[] columns = { "Course ID", "Course Name", "Credits", "Grade", "Grade Points" };
                DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
                    public boolean isCellEditable(int row, int col) {
                        return false;
                    }
                };

                for (Course course : student.enrolledCourses) {
                    Enrollment e = course.getEnrollmentFor(student);
                    Object[] row = {
                            course.getCourseId(),
                            course.getCourseName(),
                            course.getCreditHours(),
                            (e != null && e.isGraded()) ? e.getGrade() : "In Progress",
                            (e != null && e.isGraded()) ? String.format("%.1f", e.getGradePoints()) : "-"
                    };
                    tableModel.addRow(row);
                }

                JTable table = new JTable(tableModel);
                table.setFont(new Font("Tahoma", Font.PLAIN, 11));
                table.setRowHeight(25);
                table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 11));

                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setPreferredSize(new Dimension(500, 150));
                scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
                mainContentPanel.add(scrollPane);
            }

            mainContentPanel.add(Box.createVerticalStrut(10));

            // Academic summary
            JPanel summaryPanel = createInfoCard("Academic Summary");
            summaryPanel.add(new JLabel("Completed Credits: " + transcript.getCompletedCredits()));
            summaryPanel.add(new JLabel("Cumulative GPA: " + String.format("%.2f", transcript.calculateCGPA())));
            summaryPanel.add(new JLabel("Semester: " + transcript.getSemester()));
            mainContentPanel.add(summaryPanel);

        } catch (Exception ex) {
            JPanel errorPanel = createInfoCard("Error");
            errorPanel.add(new JLabel("Error generating transcript: " + ex.getMessage()));
            mainContentPanel.add(errorPanel);
        }

        mainContentPanel.add(Box.createVerticalGlue());
        mainContentPanel.revalidate();
        mainContentPanel.repaint();
    }

    // ========================== INSTRUCTOR FEATURES ==========================

    /**
     * Assign Grades - Lets instructor select a course and assign grades to students
     * Uses instructor.getAssignedCourses(), course.getEnrolledStudents(), Enrollment.setGrade()
     */
   private void assignGrades() {
        if (!(currentUser instanceof Instructor)) return;

        Instructor instructor = (Instructor) currentUser;

        mainContentPanel.removeAll();

        JLabel title = new JLabel("Assign Grades");
        title.setFont(new Font("Tahoma", Font.BOLD, 18));
        mainContentPanel.add(title);

        ArrayList<Course> assignedCourses = instructor.getAssignedCourses();

        if (assignedCourses.isEmpty()) {
            mainContentPanel.add(new JLabel("No courses assigned."));
            return;
        }

        JComboBox<Course> courseBox = new JComboBox<>();
        for (Course c : assignedCourses) {
            courseBox.addItem(c);
        }

        mainContentPanel.add(courseBox);

        JPanel tableContainer = new JPanel(new BorderLayout());
        mainContentPanel.add(tableContainer);

        courseBox.addActionListener(e -> {
            Course selected = (Course) courseBox.getSelectedItem();
            loadCourseGradesPanel(selected, tableContainer, instructor);
        });

        // load first course initially
        loadCourseGradesPanel(assignedCourses.get(0), tableContainer, instructor);

        mainContentPanel.revalidate();
        mainContentPanel.repaint();
    }
    /**
     * Helper: Load grade assignment panel for a specific course
     */

    private void loadCourseGradesPanel(Course course,
                                      JPanel container,
                                      Instructor instructor) {

        container.removeAll();

        ArrayList<Student> enrolledStudents = course.getEnrolledStudents();

        if (enrolledStudents.isEmpty()) {
            container.add(new JLabel("No students enrolled."));
            container.revalidate();
            return;
        }

        String[] columns = {"Student ID", "Student Name", "Current Grade", "Assign Grade"};

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int r, int c) {
                return c == 3;
            }
        };

        for (Student s : enrolledStudents) {
            Enrollment e = course.getEnrollmentFor(s);
            String grade = (e != null && e.isGraded()) ? e.getGrade() : "";

            model.addRow(new Object[]{
                    s.getId(),
                    s.getName(),
                    grade.isEmpty() ? "Not Graded" : grade,
                    ""
            });
        }

        JTable table = new JTable(model);

        String[] grades = {"", "A", "A-", "B+", "B", "B-", "C+", "C", "D", "F"};
        JComboBox<String> gradeBox = new JComboBox<>(grades);
        table.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(gradeBox));

        container.add(new JScrollPane(table), BorderLayout.CENTER);
                JButton submit = new JButton("Submit Grades");

        submit.addActionListener(e -> {

            try {
                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }

                int updated = 0;

                for (int i = 0; i < model.getRowCount(); i++) {

                    String studentId = model.getValueAt(i, 0).toString();
                    String grade = (String) model.getValueAt(i, 3);

                    if (grade == null || grade.trim().isEmpty()) continue;

                    if (!ValidationUtility.validateGrade(grade)) {
                        throw new IllegalArgumentException("Invalid grade: " + grade);
                    }

                    // ✅ FIX: Find student by ID (NOT by index)
                    Student targetStudent = null;
                    for (Student s : enrolledStudents) {
                        if (s.getId() == Integer.parseInt(studentId)) {
                            targetStudent = s;
                            break;
                        }
                    }

                    if (targetStudent == null) continue;

                    Enrollment enrollment = course.getEnrollmentFor(targetStudent);

                    if (enrollment == null) {
                        enrollment = new Enrollment(
                                (int) System.currentTimeMillis(),
                                targetStudent,
                                course,
                                "",
                                java.time.LocalDate.now().toString(),
                                "ENROLLED",
                                targetStudent.getSemester()
                        );

                        course.addEnrollment(enrollment);
                        course.addStudent(targetStudent);
                        targetStudent.registerCourse(course);
                    }

                    instructor.assignGrade(enrollment, grade);
                    targetStudent.updateCGPA();
                    updated++;
                }

                if (updated > 0) {
                    FileManager.saveSystemState(students, instructors, admins, courses, "system.dat");

                    JOptionPane.showMessageDialog(this,
                            "Grades submitted successfully: " + updated,
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);

                    loadCourseGradesPanel(course, container, instructor);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "No grades selected.",
                            "Info",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel bottom = new JPanel();
        bottom.add(submit);

        container.add(bottom, BorderLayout.SOUTH);

        container.revalidate();
        container.repaint();
    }

    /**
     * View Students - Shows all students enrolled in instructor's courses
     * Uses instructor.viewEnrolledStudents(course) for each assigned course
     */
    private void viewStudents() {
        if (!(currentUser instanceof Instructor)) return;
        Instructor instructor = (Instructor) currentUser;

        mainContentPanel.removeAll();

        JLabel titleLabel = new JLabel("Enrolled Students");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLACK);
        mainContentPanel.add(titleLabel);
        mainContentPanel.add(Box.createVerticalStrut(15));

        try {
            ArrayList<Course> assignedCourses = instructor.getAssignedCourses();

            if (assignedCourses.isEmpty()) {
                JPanel infoPanel = createInfoCard("No Courses");
                infoPanel.add(new JLabel("No courses are assigned to you."));
                mainContentPanel.add(infoPanel);
            } else {
                for (Course course : assignedCourses) {
                    JPanel coursePanel = createInfoCard(
                            course.getCourseId() + " - " + course.getCourseName());
                    ArrayList<Student> enrolled = instructor.viewEnrolledStudents(course);

                    if (enrolled.isEmpty()) {
                        coursePanel.add(new JLabel("No students enrolled in this course."));
                    } else {
                        for (Student s : enrolled) {
                            coursePanel.add(new JLabel("  " + s.getName() + " (ID: " + s.getId()
                                    + ") - Semester: " + s.getSemester()));
                        }
                        coursePanel.add(Box.createVerticalStrut(5));
                        JLabel totalLabel = new JLabel("Total Students: " + enrolled.size());
                        totalLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
                        coursePanel.add(totalLabel);
                    }
                    mainContentPanel.add(coursePanel);
                    mainContentPanel.add(Box.createVerticalStrut(10));
                }
            }
        } catch (Exception ex) {
            JPanel errorPanel = createInfoCard("Error");
            errorPanel.add(new JLabel("Error loading students: " + ex.getMessage()));
            mainContentPanel.add(errorPanel);
        }

        mainContentPanel.add(Box.createVerticalGlue());
        mainContentPanel.revalidate();
        mainContentPanel.repaint();
    }

    // ========================== ADMIN FEATURES ==========================

    /**
     * Manage Users - Shows all students and instructors with delete functionality
     * Uses admin.removeStudent(), admin.removeInstructor()
     */
    private void manageUsers() {
        if (!(currentUser instanceof Admin)) return;

        mainContentPanel.removeAll();

        JLabel titleLabel = new JLabel("Manage Users");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLACK);
        mainContentPanel.add(titleLabel);
        mainContentPanel.add(Box.createVerticalStrut(15));

        try {
            // ---- Students Section ----
            JLabel studentsTitle = new JLabel("Students (" + students.size() + ")");
            studentsTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
            studentsTitle.setForeground(new Color(255, 140, 0));
            studentsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainContentPanel.add(studentsTitle);
            mainContentPanel.add(Box.createVerticalStrut(5));

            String[] studentCols = { "ID", "Name", "Email", "Semester", "CGPA" };
            DefaultTableModel studentModel = new DefaultTableModel(studentCols, 0) {
                public boolean isCellEditable(int row, int col) {
                    return false;
                }
            };
            for (Student s : students) {
                studentModel.addRow(new Object[] { s.getId(), s.getName(), s.getEmail(),
                        s.getSemester(), String.format("%.2f", s.calculateCGPA()) });
            }

            JTable studentTable = new JTable(studentModel);
            studentTable.setFont(new Font("Tahoma", Font.PLAIN, 11));
            studentTable.setRowHeight(25);
            studentTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 11));

            studentTable.setPreferredScrollableViewportSize(new Dimension(500, 120));
            JScrollPane studentScroll = new JScrollPane(studentTable);
            studentScroll.setPreferredSize(new Dimension(500, 120));
            studentScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));
            mainContentPanel.add(studentScroll);

            JPanel studentBtnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            studentBtnPanel.setBackground(new Color(240, 240, 240));
            studentBtnPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
            JButton deleteStudentBtn = new JButton("Delete Selected Student");
            deleteStudentBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
            deleteStudentBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            deleteStudentBtn.addActionListener(e -> {
                int row = studentTable.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(this, "Please select a student to delete.",
                            "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete '" + studentModel.getValueAt(row, 1) + "'?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        Student toRemove = students.get(row);
                        ((Admin) currentUser).removeStudent(toRemove);
                        students.remove(row);
                        studentModel.removeRow(row);
                        JOptionPane.showMessageDialog(this, "Student deleted successfully.",
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            studentBtnPanel.add(deleteStudentBtn);
            mainContentPanel.add(studentBtnPanel);

            mainContentPanel.add(Box.createVerticalStrut(15));

            // ---- Instructors Section ----
            JLabel instructorsTitle = new JLabel("Instructors (" + instructors.size() + ")");
            instructorsTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
            instructorsTitle.setForeground(new Color(255, 140, 0));
            instructorsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainContentPanel.add(instructorsTitle);
            mainContentPanel.add(Box.createVerticalStrut(5));

            String[] instructorCols = { "ID", "Name", "Email", "Department", "Designation" };
            DefaultTableModel instructorModel = new DefaultTableModel(instructorCols, 0) {
                public boolean isCellEditable(int row, int col) {
                    return false;
                }
            };
            for (Instructor inst : instructors) {
                instructorModel.addRow(new Object[] { inst.getId(), inst.getName(), inst.getEmail(),
                        inst.getDepartment(), inst.getDesignation() });
            }

            JTable instructorTable = new JTable(instructorModel);
            instructorTable.setFont(new Font("Tahoma", Font.PLAIN, 11));
            instructorTable.setRowHeight(25);
            instructorTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 11));

            instructorTable.setPreferredScrollableViewportSize(new Dimension(500, 120));
            JScrollPane instructorScroll = new JScrollPane(instructorTable);
            instructorScroll.setPreferredSize(new Dimension(500, 120));
            instructorScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));
            mainContentPanel.add(instructorScroll);

            JPanel instructorBtnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            instructorBtnPanel.setBackground(new Color(240, 240, 240));
            instructorBtnPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
            JButton deleteInstructorBtn = new JButton("Delete Selected Instructor");
            deleteInstructorBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
            deleteInstructorBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            deleteInstructorBtn.addActionListener(e -> {
                int row = instructorTable.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(this, "Please select an instructor to delete.",
                            "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete '" + instructorModel.getValueAt(row, 1) + "'?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        Instructor toRemove = instructors.get(row);
                        ((Admin) currentUser).removeInstructor(toRemove);
                        instructors.remove(row);
                        instructorModel.removeRow(row);
                        JOptionPane.showMessageDialog(this, "Instructor deleted successfully.",
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            instructorBtnPanel.add(deleteInstructorBtn);
            mainContentPanel.add(instructorBtnPanel);

        } catch (Exception ex) {
            JPanel errorPanel = createInfoCard("Error");
            errorPanel.add(new JLabel("Error loading users: " + ex.getMessage()));
            mainContentPanel.add(errorPanel);
        }

        mainContentPanel.add(Box.createVerticalGlue());
        mainContentPanel.revalidate();
        mainContentPanel.repaint();
    }

    /**
     * Manage Courses - Shows all courses with add/delete functionality
     * Uses admin.addCourse(), admin.removeCourse(), ValidationUtility, FileManager
     */
    private void manageCourses() {
        if (!(currentUser instanceof Admin)) return;

        mainContentPanel.removeAll();

        JLabel titleLabel = new JLabel("Manage Courses");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLACK);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(240, 240, 240));
        titlePanel.add(titleLabel);
        mainContentPanel.add(titlePanel);
        mainContentPanel.add(Box.createVerticalStrut(15));

        try {
            // Courses table
            String[] columns = { "Course ID", "Course Name", "Credits", "Instructor", "Capacity", "Enrolled" };
            DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
                public boolean isCellEditable(int row, int col) {
                    return false;
                }
            };
            for (Course c : courses) {
                tableModel.addRow(new Object[] { c.getCourseId(), c.getCourseName(), c.getCreditHours(),
                        c.getInstructor().getName(), c.getMaxEnrollment(), c.getEnrolledCount() });
            }

            JTable coursesTable = new JTable(tableModel);
            coursesTable.setFont(new Font("Tahoma", Font.PLAIN, 11));
            coursesTable.setRowHeight(25);
            coursesTable.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 11));

            JScrollPane scrollPane = new JScrollPane(coursesTable);
            scrollPane.setPreferredSize(new Dimension(500, 130));
            scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));
            mainContentPanel.add(scrollPane);

            // Delete button
            JPanel deleteBtnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            deleteBtnPanel.setBackground(new Color(240, 240, 240));
            deleteBtnPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
            JButton deleteBtn = new JButton("Delete Selected Course");
            deleteBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
            deleteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            deleteBtn.addActionListener(e -> {
                int row = coursesTable.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(this, "Please select a course to delete.",
                            "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete '" + tableModel.getValueAt(row, 1) + "'?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        Course toRemove = courses.get(row);
                        ((Admin) currentUser).removeCourse(toRemove);
                        courses.remove(row);
                        tableModel.removeRow(row);
                        FileManager.saveSystemState(students, instructors, admins, courses, "system.dat");
                        JOptionPane.showMessageDialog(this, "Course deleted successfully.",
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            deleteBtnPanel.add(deleteBtn);
            mainContentPanel.add(deleteBtnPanel);

            mainContentPanel.add(Box.createVerticalStrut(15));

            // Add Course form
            JPanel addPanel = createInfoCard("Add New Course");

            JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 8));
            formPanel.setBackground(new Color(240, 240, 240));

            JTextField courseIdField = new JTextField();
            courseIdField.setFont(new Font("Tahoma", Font.PLAIN, 11));
            JTextField courseNameField = new JTextField();
            courseNameField.setFont(new Font("Tahoma", Font.PLAIN, 11));
            JTextField creditsField = new JTextField();
            creditsField.setFont(new Font("Tahoma", Font.PLAIN, 11));
            JTextField capacityField = new JTextField("30");
            capacityField.setFont(new Font("Tahoma", Font.PLAIN, 11));

            JComboBox<String> instructorCombo = new JComboBox<>();
            for (Instructor inst : instructors) {
                instructorCombo.addItem(inst.getId() + " - " + inst.getName());
            }
            instructorCombo.setFont(new Font("Tahoma", Font.PLAIN, 11));

            formPanel.add(new JLabel("Course ID (e.g. CS101):"));
            formPanel.add(courseIdField);
            formPanel.add(new JLabel("Course Name:"));
            formPanel.add(courseNameField);
            formPanel.add(new JLabel("Credit Hours (1-4):"));
            formPanel.add(creditsField);
            formPanel.add(new JLabel("Capacity (1-100):"));
            formPanel.add(capacityField);
            formPanel.add(new JLabel("Instructor:"));
            formPanel.add(instructorCombo);

            addPanel.add(formPanel);
            addPanel.add(Box.createVerticalStrut(10));

            JButton addBtn = new JButton("Add Course");
            addBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
            addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            addBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
            addBtn.addActionListener(e -> {
                try {
                    String courseId = ValidationUtility.sanitizeInput(courseIdField.getText());
                    String courseName = ValidationUtility.sanitizeInput(courseNameField.getText());
                    String creditsText = creditsField.getText().trim();

                    if (courseId.isEmpty() || courseName.isEmpty() || creditsText.isEmpty()) {
                        throw new IllegalArgumentException("All fields are required.");
                    }
                    if (!ValidationUtility.validateCourseCode(courseId)) {
                        throw new IllegalArgumentException(
                                "Invalid course code. Use letters followed by numbers (e.g. CS101).");
                    }

                    int credits = Integer.parseInt(creditsText);
                    if (!ValidationUtility.validateCreditHours(credits)) {
                        throw new IllegalArgumentException("Credit hours must be between 1 and 4.");
                    }

                    // Check duplicate
                    for (Course c : courses) {
                        if (c.getCourseId().equalsIgnoreCase(courseId)) {
                            throw new IllegalArgumentException("Course ID '" + courseId + "' already exists.");
                        }
                    }

                    int selectedIdx = instructorCombo.getSelectedIndex();
                    if (selectedIdx < 0) {
                        throw new IllegalArgumentException("Please select an instructor.");
                    }
                    Instructor inst = instructors.get(selectedIdx);

                    int capacity = Integer.parseInt(capacityField.getText().trim());
                    if (capacity < 1 || capacity > 100) {
                        throw new IllegalArgumentException("Capacity must be between 1 and 100.");
                    }

                    Course newCourse = new Course(courseId, courseName, credits, capacity, inst);
                    courses.add(newCourse);
                    inst.addCourseTaught(newCourse);
                    ((Admin) currentUser).addCourse(newCourse);

                    FileManager.saveSystemState(students, instructors, admins, courses, "system.dat");

                    tableModel.addRow(new Object[] { courseId, courseName, credits, inst.getName(), capacity, 0 });
                    courseIdField.setText("");
                    courseNameField.setText("");
                    creditsField.setText("");
                    capacityField.setText("30");

                    JOptionPane.showMessageDialog(this, "Course added successfully!",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Credit hours must be a valid number.",
                            "Validation Error", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(),
                            "Validation Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error adding course: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            addPanel.add(addBtn);
            mainContentPanel.add(addPanel);

        } catch (Exception ex) {
            JPanel errorPanel = createInfoCard("Error");
            errorPanel.add(new JLabel("Error loading courses: " + ex.getMessage()));
            mainContentPanel.add(errorPanel);
        }

        mainContentPanel.add(Box.createVerticalGlue());
        mainContentPanel.revalidate();
        mainContentPanel.repaint();
    }

    // ========================== NAVIGATION ==========================

    /**
     * Open enrollment module - passes shared course list
     */
    private void openEnrollment() {
        if (currentUser instanceof Student) {
            EnrollmentGUI enrollmentGUI = new EnrollmentGUI((Student) currentUser, students, instructors, admins, courses);
            enrollmentGUI.setVisible(true);
        }
    }

    /**
     * Open admin panel - passes shared data lists
     */
    private void openAdminPanel() {
        if (currentUser instanceof Admin) {
            new AdminPanel((Admin) currentUser, students, instructors, admins, courses);
        }
    }

    /**
     * Get user role as string
     */
    private String getUserRole() {
        if (currentUser instanceof Student) {
            return "Student";
        } else if (currentUser instanceof Instructor) {
            return "Instructor";
        } else if (currentUser instanceof Admin) {
            return "Administrator";
        }
        return "User";
    }

    /**
     * Logout and return to login screen
     */
    private void logout() {
        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?",
                "Logout", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            currentUser.logOut();
            new Login();
            dispose();
        }
    }
}
