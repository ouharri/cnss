package com.macnss.view.Administrator;

import com.macnss.app.Enums.AgentStatus;
import com.macnss.app.Enums.Gender;
import com.macnss.app.Models.user.Administrator;
import com.macnss.app.Models.user.AgentCNSS;
import com.macnss.app.Services.EmailService;
import com.macnss.dao.AgentCNSSDao;
import com.macnss.helpers.AuthenticationHelpers;
import com.macnss.view.Authentication.SigningAgentCNS;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class index extends JFrame implements ActionListener {

    private Administrator Admin;
    private final JButton agent_btn, patient_btn, medecine_btn, analyse_btn, radio_btn, visit_btn, scanner_btn, logout_btn;
    private JButton add_agent_button, setting_agent_button, delete_book_button, medecine_btn_book_button;
    private JTable agentTable;
    private JScrollPane scrollPane;
    private JLabel book_label;
    private DefaultTableModel tableModel;
    private JTextField medecine_btn_field;
    private addAgentDialog addAgentDialog;
    String CurrentBookClicked = null;
    int CurrentAgentClickedRow = -1;
    private EditBookDialog editBookDialog;


    public index(Administrator u) throws SQLException {


        setTitle("Administrator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(null);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        ImageIcon logoIcon = new ImageIcon("assets/images/app/user.png");
        Image UserLogo = logoIcon.getImage();

        ImageIcon Agent_icon = new ImageIcon("assets/icon/agent.png");
        ImageIcon Patient_icon = new ImageIcon("assets/icon/patient.png");
        ImageIcon Medecine_icon = new ImageIcon("assets/icon/medicine.png");
        ImageIcon Laboratory_icon = new ImageIcon("assets/icon/laboratory.png");
        ImageIcon Radio_icon = new ImageIcon("assets/icon/radio.png");
        ImageIcon Scanner_icon = new ImageIcon("assets/icon/scanner.png");
        ImageIcon Visit_icon = new ImageIcon("assets/icon/visit.png");
        ImageIcon Logout_icon = new ImageIcon("assets/icon/logout.png");

        agent_btn = new JButton("  Agent", Agent_icon);
        patient_btn = new JButton("  Patient", Patient_icon);
        medecine_btn = new JButton("  Medecine", Medecine_icon);
        analyse_btn = new JButton("  Analyse", Laboratory_icon);
        radio_btn = new JButton(" Radio", Radio_icon);
        visit_btn = new JButton("  Visit", Visit_icon);
        scanner_btn = new JButton("  Scanner", Scanner_icon);
        logout_btn = new JButton("  Logout", Logout_icon);

        setButtons(agent_btn);
        setButtons(patient_btn);
        setButtons(medecine_btn);
        setButtons(analyse_btn);
        setButtons(radio_btn);
        setButtons(visit_btn);
        setButtons(scanner_btn);
        setButtons(logout_btn);

        int y = 150;
        int y2 = 10;

        JLabel logoLabel = new JLabel(new ImageIcon(UserLogo));
        logoLabel.setBounds(50, 60, 64, 64);

        ImageIcon AppLogoIcon = new ImageIcon("assets/images/app/app.png");
        Image logo = AppLogoIcon.getImage();

        ImageIcon AppBackgroundIcon = new ImageIcon("assets/images/app/background.png");
        Image background = AppBackgroundIcon.getImage();

        setIconImage(logo);

        JLabel AppLogoLabel = new JLabel(new ImageIcon(logo));
        AppLogoLabel.setBounds(screenWidth - 120, screenHeight - 140, 100, 92);

        JLabel AppBackgroundBaLabel = new JLabel(new ImageIcon(background));
        AppBackgroundBaLabel.setBounds((screenWidth - 230) / 2, (screenHeight - 229) / 2, 414, 229);

        String user_roles = "ADMIN";
        JLabel user_role = new JLabel(user_roles);
        user_role.setFont(new Font("Arial", Font.ITALIC, 10));
        user_role.setBounds(65, 130, 300, 15);

        JLabel user_name = new JLabel(u.getFullName());
        user_name.setFont(new Font("Arial", Font.PLAIN, 25));
        user_name.setBounds(127, 70 + y2, 200, 17);

        JLabel user_contact = new JLabel((u.getEmail() != null ? u.getEmail() : u.getPhone() != null ? u.getPhone() : u.getCnie()));
        user_contact.setFont(new Font("Arial", Font.PLAIN, 13));
        user_contact.setBounds(127, 90 + y2, 300, 15);

        agent_btn.setBounds(50, 100 + y, 250, 30);
        patient_btn.setBounds(50, 155 + y, 250, 30);
        medecine_btn.setBounds(50, 210 + y, 200, 30);
        analyse_btn.setBounds(50, 265 + y, 250, 30);
        radio_btn.setBounds(50, 320 + y, 250, 30);
        visit_btn.setBounds(50, 375 + y, 250, 30);
        scanner_btn.setBounds(50, 430 + y, 250, 30);
        logout_btn.setBounds(50, 485 + y, 250, 30);

        agent_btn.addActionListener(this);
        medecine_btn.addActionListener(this);
        patient_btn.addActionListener(this);
        analyse_btn.addActionListener(this);
        radio_btn.addActionListener(this);
        visit_btn.addActionListener(this);
        scanner_btn.addActionListener(this);
        logout_btn.addActionListener(this);

        add(user_name);
        add(user_contact);
        add(user_role);
        add(AppLogoLabel);


        add(logoLabel);
        add(agent_btn);
        add(patient_btn);
        add(medecine_btn);
        add(analyse_btn);
        add(radio_btn);
        add(visit_btn);
        add(scanner_btn);
        add(logout_btn);

        add(AppBackgroundBaLabel);
        setVisible(true);

    }

    private void agentManagement() throws SQLException {
        book_label = new JLabel("Agent CNSS :");
        book_label.setFont(new Font("Arial", Font.PLAIN, 25));
        book_label.setBounds(400, 140, 300, 50);

        tableModel = new DefaultTableModel(new String[]{"id", "cnie", "First Name", "Last Name", "email", "phone", "birthday", "gender", "status"}, 0);
        agentTable = new JTable(tableModel);
        agentTable.setRowHeight(30);

        agentTable.getColumnModel().getColumn(0).setPreferredWidth(3);
        agentTable.getColumnModel().getColumn(4).setPreferredWidth(40);

        ImageIcon add_icon = new ImageIcon("assets/icon/addAgent.png");
        ImageIcon setting_icon = new ImageIcon("assets/icon/setting.png");

        add_agent_button = new JButton("", add_icon);
        setting_agent_button = new JButton("", setting_icon);


        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setBackground(Color.WHITE);
        agentTable.setDefaultRenderer(Object.class, renderer);

        setButtons(add_agent_button);
        setButtons(setting_agent_button);

        add_agent_button.setBounds(1350, 150, 50, 24);
        setting_agent_button.setBounds(1300, 150, 50, 24);

        addAgentDialog = new addAgentDialog(this);
//        editBookDialog = new EditBookDialog(this);

        add_agent_button.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        addAgentDialog.setVisible(true);
                    }
                }
        );
//        setting_agent_button.addActionListener(
//                new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        try {
//                            editBookDialog.display();
//                        } catch (SQLException ex) {
//                            throw new RuntimeException(ex);
//                        }
//                    }
//                }
//        );
//        delete_book_button.addActionListener(
//                new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        try {
//                            deleteBookDialog.display();
//                        } catch (SQLException ex) {
//                            throw new RuntimeException(ex);
//                        }
//                    }
//                }
//        );

        scrollPane = new JScrollPane(agentTable);
        scrollPane.setBounds(400, 190, 1000, 550);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        try (AgentCNSSDao dao = new AgentCNSSDao(new AgentCNSS())) {
            List<AgentCNSS> agents = dao.getAll();
            addAgentsToTable(agents);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }


        add(book_label);
        add(add_agent_button);
        add(setting_agent_button);
        add(scrollPane);


        revalidate();
        repaint();
    }

    private static class addAgentDialog extends JDialog {
        public addAgentDialog(Frame owner) {
            super(owner, "Add Agent", true);
            setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            setSize(500, 680);
            setLocationRelativeTo(null);
            setLayout(null);

            JLabel isbn, quantity, pages, title, edition, language, description, author, category;
            JTextField isbn_field, quantity_field, pages_field, title_field, edition_field;
            JTextArea description_field;

            JComboBox<Gender> gender_field;
            JComboBox<AgentStatus> status_field;


            BasicComboBoxRenderer renderer = new BasicComboBoxRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value != null) {
                        setText(value.toString());
                        setBackground(Color.WHITE);
                    }
                    return this;
                }
            };

            isbn = new JLabel("cnie");
            quantity = new JLabel("First Name");
            pages = new JLabel("last Name");
            title = new JLabel("email");
            edition = new JLabel("phone");
            language = new JLabel("gender");
            description = new JLabel("birthday");
            author = new JLabel("status");

            isbn_field = new JTextField();
            quantity_field = new JTextField();
            pages_field = new JTextField();
            title_field = new JTextField();
            edition_field = new JTextField();
            description_field = new JTextArea();

            gender_field = new JComboBox<Gender>(Gender.values());
            status_field = new JComboBox<AgentStatus>(AgentStatus.values());

            JButton add_agent = new JButton("Add Agent");

            isbn.setBounds(50, 50, 400, 30);
            isbn_field.setBounds(50, 80, 400, 30);

            title.setBounds(50, 110, 400, 30);
            title_field.setBounds(50, 140, 400, 30);

            pages.setBounds(50, 170, 400, 30);
            pages_field.setBounds(50, 200, 400, 30);

            edition.setBounds(50, 230, 400, 30);
            edition_field.setBounds(50, 260, 400, 30);

            quantity.setBounds(50, 290, 400, 30);
            quantity_field.setBounds(50, 320, 400, 30);

            language.setBounds(50, 350, 400, 30);
            gender_field.setBounds(50, 380, 400, 30);


            Date today = new Date();
            JDateChooser birthday_field = new JDateChooser();
            description.setBounds(50, 410, 400, 30);
            birthday_field.setBounds(50, 440, 400, 30);
            birthday_field.setMaxSelectableDate(today);


            author.setBounds(50, 470, 400, 30);
            status_field.setBounds(50, 500, 400, 30);

            add_agent.setBounds(350, 570, 100, 30);
            add_agent.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            add_agent.setContentAreaFilled(false);
            add_agent.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            add_agent.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String cnie = isbn_field.getText();
                    String firstName = quantity_field.getText();
                    String lastName = pages_field.getText();
                    String email = title_field.getText();
                    String phone = edition_field.getText();
                    AgentStatus status = AgentStatus.valueOf(Objects.requireNonNull(status_field.getSelectedItem()).toString());
                    Gender gender = Gender.valueOf(Objects.requireNonNull(gender_field.getSelectedItem()).toString());
                    java.sql.Date selectedDate = new java.sql.Date(birthday_field.getDate().getTime());

                    if (cnie.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() || status == null || selectedDate == null) {
                        JOptionPane.showMessageDialog(null, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String password = String.valueOf(AuthenticationHelpers.generateRandomCode(10));

                    AgentCNSS newAgent = new AgentCNSS();

                    newAgent.setUser(
                            cnie,
                            firstName,
                            lastName,
                            selectedDate,
                            gender,
                            email,
                            phone,
                            AuthenticationHelpers.hashPassword(password)
                    );
                    newAgent.setStatus(status);

                    try (AgentCNSSDao dao = new AgentCNSSDao(newAgent)) {
                        if (dao.save().isPresent()) {
                            EmailService emailService = new EmailService(email, "Authentication Password MaCNSS");
                            Thread emailThread = new Thread(emailService);
                            emailService.setText("Your Password : " + password + "\n" + "Please change your password after login.");
                            emailThread.start();
                            JOptionPane.showMessageDialog(null, "Agent added successfully, here password is sent to \" " + email + "\" ", "Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }

//
//                    int[] authorIds = new int[selectedAuthors.length];
//                    for (int i = 0; i < selectedAuthors.length; i++) {
//                        authorIds[i] = extractId(selectedAuthors[i]);
//                    }
//
//                    int[] categoryIds = new int[selectedCategories.length];
//                    for (int i = 0; i < selectedCategories.length; i++) {
//                        categoryIds[i] = extractId(selectedCategories[i]);
//                    }
//
//                    try {
//                        Book b = bc.addBook(isbn, title, description, language, quantity, pages, edition, authorIds, categoryIds);
//                        if (b != null) {
//                            tableModel.addRow(new Object[]{isbn, title, pages, edition, quantity, language, concatenateTexts(selectedAuthors), concatenateTexts(selectedCategories), description});
//                            JOptionPane.showMessageDialog(null, "Book added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
//                            setVisible(false);
//                            addBookDialog.removeAll();
//                            revalidate();
//                            repaint();
//                            addBookDialog.dispose();
//                            remove(addBookDialog);
//                        }
//                    } catch (SQLException throwables) {
//                        throwables.printStackTrace();
//                    }
                }
            });

            add(isbn);
            add(isbn_field);
            add(title);
            add(title_field);
            add(pages);
            add(pages_field);
            add(edition);
            add(edition_field);
            add(quantity);
            add(quantity_field);
            add(language);
            add(gender_field);
            add(status_field);
            add(description);
            add(description_field);
            add(author);
            add(add_agent);
            add(birthday_field);
        }
    }

    private void addAgentsToTable(List<AgentCNSS> agents) {
        removeAllRowFromTable();
        for (AgentCNSS agent : agents) {
            String id = String.valueOf(agent.getAgent_cns_id());
            String cnie = agent.getCnie();
            String firstName = agent.getFirstName();
            String lastName = agent.getLastName();
            String email = agent.getEmail();
            String phone = agent.getPhone();
            String birthday = String.valueOf(agent.getBirthday());
            String gender = agent.getGender().toString();
            String status = agent.getStatus().toString();

            tableModel.addRow(new Object[]{id, cnie, firstName, lastName, email, phone, birthday, gender, status});
            agentTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int row = agentTable.rowAtPoint(e.getPoint());
                    Object cellValue = agentTable.getValueAt(row, 0);
                    CurrentBookClicked = cellValue.toString();
                    CurrentAgentClickedRow = row;
                }
            });
        }

        agentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = agentTable.rowAtPoint(e.getPoint());
                Object cellValue = agentTable.getValueAt(row, 0);
                CurrentBookClicked = cellValue.toString();
                CurrentAgentClickedRow = row;
            }
        });
    }


    private void refresh() {
        if (book_label != null) remove(book_label);
        if (add_agent_button != null) remove(add_agent_button);
        if (setting_agent_button != null) remove(setting_agent_button);
        if (delete_book_button != null) remove(delete_book_button);
        if (scrollPane != null) remove(scrollPane);
        if (medecine_btn_field != null) remove(medecine_btn_field);
        if (medecine_btn_book_button != null) remove(medecine_btn_book_button);
        if (editBookDialog != null) editBookDialog.dispose();
        if (agentTable != null) agentTable = null;
        if (tableModel != null) tableModel = null;
        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == agent_btn) {
            refresh();
            try {
                agentManagement();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == patient_btn) {
            refresh();
            try {
                displayAllBook();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == medecine_btn) {
            refresh();
            try {
                displaySearchBook();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        } else if (e.getSource() == analyse_btn) {
            refresh();
            try {
                displayAvailableBook();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource() == radio_btn) {
            refresh();
        } else if (e.getSource() == visit_btn) {
            refresh();
        } else if (e.getSource() == scanner_btn) {
            refresh();
        } else if (e.getSource() == logout_btn) {
            int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                dispose();
                new SigningAgentCNS();
            }
        }
    }


    private void displayAllBook() throws SQLException {

        revalidate();
        repaint();
    }

    private void displayAvailableBook() throws SQLException {


        revalidate();
        repaint();
    }

    private void displaySearchBook() throws SQLException {


        revalidate();
        repaint();
        setVisible(true);
    }

    private void displayStatistics() {


    }

    private class EditBookDialog extends JDialog {
        public EditBookDialog(Frame owner) {
            super(owner, "Edit Book", true);
            setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            setSize(500, 720);
            setLocationRelativeTo(null);
            setLayout(null);
        }

    }


    private int extractId(String authorString) {
        String[] parts = authorString.split(",");

        if (parts.length == 2) {
            try {
                return Integer.parseInt(parts[0].trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        return -1;
    }

    private String concatenateTexts(String[] textsWithId) {
        StringBuilder concatenatedText = new StringBuilder();

        for (String text : textsWithId) {
            String[] parts = text.split(",");
            if (parts.length == 2) {
                if (concatenatedText.length() > 0) {
                    concatenatedText.append(", ");
                }
                concatenatedText.append(parts[1].trim());
            }
        }

        return concatenatedText.toString();
    }


    private void removeAllRowFromTable() {
        int rowCount = tableModel.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
    }


    private void setButtons(JButton butt) {
        butt.setBorderPainted(false);
        butt.setHorizontalAlignment(SwingConstants.LEFT);
        butt.setContentAreaFilled(false);
        butt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        Font police = new Font("SansSerif", Font.BOLD, 15);
        butt.setFont(police);
    }

    public static void main(String[] args) throws SQLException {
        new index(new Administrator());
    }


}