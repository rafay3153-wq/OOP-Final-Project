import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


 */
public class AdminPanel extends JFrame {
    private Admin admin;
    private JTabbedPane tabbedPane;
    private ArrayList<Student> students;
    private ArrayList<Instructor> instructors;
    private ArrayList<Admin> admins;
    private ArrayList<Course> courses;

    public AdminPanel(Admin admin, ArrayList<Student> students, ArrayList<Instructor> instructors,
            ArrayList<Admin> admins, ArrayList<Course> courses) {
        this.admin = admin;
        this.students = students;
        this.instructors = instructors;
        this.admins = admins;
        this.courses = courses;

        // Frame setup
        setTitle("Admin Panel - " + admin.getName());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));

        // Header
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(240, 240, 240));
        tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 11));

        // Add tabs
        tabbedPane.addTab("Dashboard", createDashboardTab());
        tabbedPane.addTab("Add Student", createAddStudentTab());
        tabbedPane.addTab("Add Instructor", createAddInstructorTab());
        tabbedPane.addTab("Manage Courses", createManageCoursesTab());
        tabbedPane.addTab("View Users", createViewUsersTab());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    /**
     * Create header panel
     */
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)));

        JLabel titleLabel = new JLabel("Admin Panel");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLACK);

        JLabel adminLabel = new JLabel("Admin: " + admin.getName());
        adminLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        adminLabel.setForeground(Color.DARK_GRAY);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(240, 240, 240));
        leftPanel.add(titleLabel);
        leftPanel.add(adminLabel);

        panel.add(leftPanel, BorderLayout.WEST);
        return panel;
    }

    /**
     * Create dashboard tab with system overview
     */
    private JPanel createDashboardTab() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("System Overview");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        titleLabel.setForeground(Color.BLACK);
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(15));

        // Statistics cards using backend data
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        statsPanel.setBackground(new Color(240, 240, 240));

        statsPanel.add(createStatCard("Total Students", String.valueOf(students.size())));
        statsPanel.add(createStatCard("Total Instructors", String.valueOf(instructors.size())));
        statsPanel.add(createStatCard("Total Courses", String.valueOf(courses.size())));

        // Calculate total enrollments
        int totalEnrollments = 0;
        for (Course course : courses) {
            totalEnrollments += course.getEnrolledCount();
        }
        statsPanel.add(createStatCard("Total Enrollments", String.valueOf(totalEnrollments)));

        panel.add(statsPanel);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    /**
     * Create stat card
     */
    private JPanel createStatCard(String title, String value) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(240, 240, 240));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(),
                        title,
                        javax.swing.border.TitledBorder.LEFT,
                        javax.swing.border.TitledBorder.TOP,
                        new Font("Tahoma", Font.BOLD, 11),
                        new Color(255, 140, 0)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
        valueLabel.setForeground(Color.BLACK);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(valueLabel);

        return card;
    }

    /**
     * Create add student tab
     */
    private JPanel createAddStudentTab() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Add New Student");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        titleLabel.setForeground(Color.BLACK);
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(15));

        // Form fields
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(6, 2, 10, 10));
        formPanel.setBackground(new Color(240, 240, 240));
        formPanel.setMaximumSize(new Dimension(500, 350));
        formPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField nameField = createFormField();
        JTextField emailField = createFormField();
        JTextField phoneField = createFormField();
        JTextField idField = createFormField();
        JTextField passwordField = createFormField();
        JTextField semesterField = createFormField();

        formPanel.add(new JLabel("Full Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Phone (+92...):"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Student ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Semester:"));
        formPanel.add(semesterField);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);

        panel.add(formPanel);
        panel.add(Box.createVerticalStrut(15));

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton addButton = new JButton("Add Student");
        addButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.addActionListener(e -> {
            try {
                String name = ValidationUtility.sanitizeInput(nameField.getText());
                String email = emailField.getText().trim();
                String phone = phoneField.getText().trim();
                String idText = idField.getText().trim();
                String semText = semesterField.getText().trim();
                String pass = passwordField.getText();

                if (!ValidationUtility.validateName(name)) {
                    throw new IllegalArgumentException(
                            "Invalid name. Must contain at least a first and last name, 3-50 chars.");
                }
                if (!ValidationUtility.validateEmail(email)) {
                    throw new IllegalArgumentException("Invalid email format.");
                }
                if (!ValidationUtility.validatePhoneNumber(phone)) {
                    throw new IllegalArgumentException(
                            "Invalid phone format. Must start with +92 followed by 10 digits.");
                }
                if (!ValidationUtility.validatePassword(pass)) {
                    throw new IllegalArgumentException(
                            "Invalid password. Must be 8-20 chars, with at least one digit, one uppercase, one lowercase.");
                }

                int id = Integer.parseInt(idText);
                if (!ValidationUtility.validateId(id)) {
                    throw new IllegalArgumentException("ID must be positive.");
                }

                // Check duplicate ID
                for (Student s : students) {
                    if (s.getId() == id) {
                        throw new IllegalArgumentException("Student with ID " + id + " already exists.");
                    }
                }

                int semester = Integer.parseInt(semText);
                if (semester < 1 || semester > 8) {
                    throw new IllegalArgumentException("Semester must be between 1 and 8.");
                }

                Student newStudent = new Student(name, email, phone, semester, 0.0, id, pass);
                if (admin.addStudent(newStudent)) {
                    students.add(newStudent);
                    FileManager.saveSystemState(students, instructors, admins, courses, "system.dat");
                    JOptionPane.showMessageDialog(this, "Student added successfully!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    nameField.setText("");
                    emailField.setText("");
                    phoneField.setText("");
                    idField.setText("");
                    semesterField.setText("");
                    passwordField.setText("");

                    // Refresh dashboard tab stats
                    tabbedPane.setComponentAt(0, createDashboardTab());
                    // Refresh users tab
                    tabbedPane.setComponentAt(4, createViewUsersTab());
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric values for ID and Semester.",
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error adding student: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearButton.addActionListener(e -> {
            nameField.setText("");
            emailField.setText("");
            phoneField.setText("");
            idField.setText("");
            semesterField.setText("");
            passwordField.setText("");
        });

        buttonPanel.add(addButton);
        buttonPanel.add(clearButton);
        panel.add(buttonPanel);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    /**
     * Create add instructor tab
     */
    private JPanel createAddInstructorTab() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Add New Instructor");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        titleLabel.setForeground(Color.BLACK);
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(15));

        // Form fields
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(7, 2, 10, 10));
        formPanel.setBackground(new Color(240, 240, 240));
        formPanel.setMaximumSize(new Dimension(500, 315));
        formPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField nameField = createFormField();
        JTextField emailField = createFormField();
        JTextField phoneField = createFormField();
        JTextField idField = createFormField();
        JTextField departmentField = createFormField();
        JTextField designationField = createFormField();
        JTextField passwordField = createFormField();

        formPanel.add(new JLabel("Full Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Phone (+92...):"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Instructor ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Department:"));
        formPanel.add(departmentField);
        formPanel.add(new JLabel("Designation:"));
        formPanel.add(designationField);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(passwordField);

        panel.add(formPanel);
        panel.add(Box.createVerticalStrut(15));

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton addButton = new JButton("Add Instructor");
        addButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.addActionListener(e -> {
            try {
                String name = ValidationUtility.sanitizeInput(nameField.getText());
                String email = emailField.getText().trim();
                String phone = phoneField.getText().trim();
                String idText = idField.getText().trim();
                String dept = ValidationUtility.sanitizeInput(departmentField.getText());
                String desig = ValidationUtility.sanitizeInput(designationField.getText());
                String pass = passwordField.getText();

                if (!ValidationUtility.validateName(name)) {
                    throw new IllegalArgumentException(
                            "Invalid name. Must contain at least a first and last name, 3-50 chars.");
                }
                if (!ValidationUtility.validateEmail(email)) {
                    throw new IllegalArgumentException("Invalid email format.");
                }
                if (!ValidationUtility.validatePhoneNumber(phone)) {
                    throw new IllegalArgumentException(
                            "Invalid phone format. Must start with +92 followed by 10 digits.");
                }
                if (!ValidationUtility.validatePassword(pass)) {
                    throw new IllegalArgumentException(
                            "Invalid password. Must be 8-20 chars, with at least one digit, one uppercase, one lowercase.");
                }
                if (dept.isEmpty() || desig.isEmpty()) {
                    throw new IllegalArgumentException("Department and Designation are required.");
                }

                int id = Integer.parseInt(idText);
                if (!ValidationUtility.validateId(id)) {
                    throw new IllegalArgumentException("ID must be positive.");
                }

                // Check duplicate ID
                for (Instructor inst : instructors) {
                    if (inst.getId() == id) {
                        throw new IllegalArgumentException("Instructor with ID " + id + " already exists.");
                    }
                }

                Instructor newInstructor = new Instructor(name, email, phone, dept, desig, id, pass);
                if (admin.addInstructor(newInstructor)) {
                    instructors.add(newInstructor);
                    FileManager.saveSystemState(students, instructors, admins, courses, "system.dat");
                    JOptionPane.showMessageDialog(this, "Instructor added successfully!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    nameField.setText("");
                    emailField.setText("");
                    phoneField.setText("");
                    idField.setText("");
                    departmentField.setText("");
                    designationField.setText("");
                    passwordField.setText("");

                    // Refresh tabs
                    tabbedPane.setComponentAt(0, createDashboardTab());
                    tabbedPane.setComponentAt(3, createManageCoursesTab());
                    tabbedPane.setComponentAt(4, createViewUsersTab());
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid numeric ID.", "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error adding instructor: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearButton.addActionListener(e -> {
            nameField.setText("");
            emailField.setText("");
            phoneField.setText("");
            idField.setText("");
            departmentField.setText("");
            designationField.setText("");
            passwordField.setText("");
        });

        buttonPanel.add(addButton);
        buttonPanel.add(clearButton);
        panel.add(buttonPanel);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    /**
     * Create manage courses tab
     */
    private JPanel createManageCoursesTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Manage Courses");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        titleLabel.setForeground(Color.BLACK);

        // Create table using backend course data
        String[] columns = { "Course ID", "Course Name", "Credits", "Instructor", "Capacity", "Enrolled" };
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        for (Course course : courses) {
            Object[] row = {
                    course.getCourseId(),
                    course.getCourseName(),
                    course.getCreditHours(),
                    course.getInstructor().getName(),
                    course.getMaxEnrollment(),
                    course.getEnrolledCount()
            };
            tableModel.addRow(row);
        }

        JTable table = new JTable(tableModel);
        table.setFont(new Font("Tahoma", Font.PLAIN, 11));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 11));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(new Color(240, 240, 240));
        scrollPane.setPreferredSize(new Dimension(700, 150));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(240, 240, 240));
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(240, 240, 240));
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Delete Course Button
        JPanel deletePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        deletePanel.setBackground(new Color(240, 240, 240));
        JButton deleteBtn = new JButton("Delete Selected Course");
        deleteBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
        deleteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a course to delete.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Delete course '" + tableModel.getValueAt(row, 1) + "'?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    Course toRemove = courses.get(row);
                    admin.removeCourse(toRemove);
                    courses.remove(toRemove);
                    tableModel.removeRow(row);
                    FileManager.saveSystemState(students, instructors, admins, courses, "system.dat");
                    JOptionPane.showMessageDialog(this, "Course deleted successfully.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    tabbedPane.setComponentAt(0, createDashboardTab());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error deleting course: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        deletePanel.add(deleteBtn);
        centerPanel.add(deletePanel, BorderLayout.SOUTH);

        // Add Course Form
        JPanel addPanel = new JPanel();
        addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));
        addPanel.setBackground(new Color(240, 240, 240));
        addPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 0, 0, 0),
                BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Add New Course",
                        javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP,
                        new Font("Tahoma", Font.BOLD, 12), new Color(255, 140, 0))));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBackground(new Color(240, 240, 240));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JTextField courseIdField = createFormField();
        JTextField courseNameField = createFormField();
        JTextField creditsField = createFormField();
        JTextField capacityField = createFormField();
        capacityField.setText("30");

        JComboBox<String> instructorCombo = new JComboBox<>();
        for (Instructor inst : instructors) {
            instructorCombo.addItem(inst.getId() + " - " + inst.getName());
        }
        instructorCombo.setFont(new Font("Tahoma", Font.PLAIN, 11));

        formPanel.add(new JLabel("Course ID (e.g. CS101):"));
        formPanel.add(courseIdField);
        formPanel.add(new JLabel("Course Name:"));
        formPanel.add(courseNameField);
        formPanel.add(new JLabel("Credits (1-4):"));
        formPanel.add(creditsField);
        formPanel.add(new JLabel("Capacity (1-100):"));
        formPanel.add(capacityField);
        formPanel.add(new JLabel("Instructor:"));
        formPanel.add(instructorCombo);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPanel.setBackground(new Color(240, 240, 240));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        JButton addBtn = new JButton("Add Course");
        addBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
        addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addBtn.addActionListener(e -> {
            try {
                String cId = ValidationUtility.sanitizeInput(courseIdField.getText());
                String cName = ValidationUtility.sanitizeInput(courseNameField.getText());
                String credTxt = creditsField.getText().trim();
                String capTxt = capacityField.getText().trim();

                if (cId.isEmpty() || cName.isEmpty() || credTxt.isEmpty() || capTxt.isEmpty()) {
                    throw new IllegalArgumentException("All fields are required.");
                }
                if (!ValidationUtility.validateCourseCode(cId)) {
                    throw new IllegalArgumentException("Invalid course code. E.g. CS101");
                }
                int credits = Integer.parseInt(credTxt);
                if (!ValidationUtility.validateCreditHours(credits)) {
                    throw new IllegalArgumentException("Credits must be 1-4.");
                }
                int capacity = Integer.parseInt(capTxt);
                if (capacity < 1 || capacity > 100) {
                    throw new IllegalArgumentException("Capacity must be between 1 and 100.");
                }

                for (Course c : courses) {
                    if (c.getCourseId().equalsIgnoreCase(cId)) {
                        throw new IllegalArgumentException("Course ID already exists.");
                    }
                }

                int idx = instructorCombo.getSelectedIndex();
                if (idx < 0)
                    throw new IllegalArgumentException("Please select an instructor.");
                Instructor inst = instructors.get(idx);

                Course newCourse = new Course(cId, cName, credits, capacity, inst);
                courses.add(newCourse);
                inst.addCourseTaught(newCourse);
                admin.addCourse(newCourse);

                FileManager.saveSystemState(students, instructors, admins, courses, "system.dat");

                tableModel.addRow(new Object[] { cId, cName, credits, inst.getName(), capacity, 0 });
                courseIdField.setText("");
                courseNameField.setText("");
                creditsField.setText("");
                capacityField.setText("30");
                JOptionPane.showMessageDialog(this, "Course added successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                tabbedPane.setComponentAt(0, createDashboardTab());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid credits or capacity format.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error adding course: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        btnPanel.add(addBtn);

        addPanel.add(formPanel);
        addPanel.add(btnPanel);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(addPanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Create view users tab
     */
    private JPanel createViewUsersTab() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("All Users");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        titleLabel.setForeground(Color.BLACK);

        // Create table using backend user data
        String[] columns = { "ID", "Name", "Email", "Role", "Status" };
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        for (Student student : students) {
            Object[] row = { student.getId(), student.getName(), student.getEmail(), "Student", "Active" };
            tableModel.addRow(row);
        }

        for (Instructor instructor : instructors) {
            Object[] row = { instructor.getId(), instructor.getName(), instructor.getEmail(), "Instructor", "Active" };
            tableModel.addRow(row);
        }

        JTable table = new JTable(tableModel);
        table.setFont(new Font("Tahoma", Font.PLAIN, 11));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 11));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(new Color(240, 240, 240));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(240, 240, 240));
        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);

        // Delete User Button
        JPanel deletePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        deletePanel.setBackground(new Color(240, 240, 240));
        JButton deleteBtn = new JButton("Delete Selected User");
        deleteBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
        deleteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Please select a user to delete.", "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = (int) tableModel.getValueAt(row, 0);
            String role = (String) tableModel.getValueAt(row, 3);
            String name = (String) tableModel.getValueAt(row, 1);

            int confirm = JOptionPane.showConfirmDialog(this, "Delete " + role + " '" + name + "'?", "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    if (role.equals("Student")) {
                        Student toRemove = null;
                        for (Student s : students) {
                            if (s.getId() == id) {
                                toRemove = s;
                                break;
                            }
                        }
                        if (toRemove != null) {
                            admin.removeStudent(toRemove);
                            students.remove(toRemove);
                            FileManager.saveSystemState(students, instructors, admins, courses, "system.dat");
                        }
                    } else if (role.equals("Instructor")) {
                        Instructor toRemove = null;
                        for (Instructor inst : instructors) {
                            if (inst.getId() == id) {
                                toRemove = inst;
                                break;
                            }
                        }
                        if (toRemove != null) {
                            admin.removeInstructor(toRemove);
                            instructors.remove(toRemove);
                            FileManager.saveSystemState(students, instructors, admins, courses, "system.dat");
                        }
                    }
                    tableModel.removeRow(row);
                    JOptionPane.showMessageDialog(this, role + " deleted successfully.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    tabbedPane.setComponentAt(0, createDashboardTab());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error deleting user: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        deletePanel.add(deleteBtn);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(deletePanel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Create form field
     */
    private JTextField createFormField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Tahoma", Font.PLAIN, 13));
        return field;
    }
}
