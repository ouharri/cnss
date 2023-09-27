package com.macnss.view.Agent;

import com.macnss.app.Models.user.Administrator;
import com.macnss.app.Models.user.AgentCNSS;
import com.macnss.view.Authentication.SigningAdministrator;
import com.macnss.view.Authentication.SigningAgentCNS;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class index extends JFrame implements ActionListener {

    private Administrator Admin;
    private final JButton agent_btn, patient_btn, medecine_btn, analyse_btn, radio_btn, visit_btn, scanner_btn, logout_btn;
    private JButton add_agent_button, setting_agent_button, delete_book_button, medecine_btn_book_button;
    private JTable agentTable;
    private JScrollPane scrollPane;
    private JLabel book_label;
    private DefaultTableModel tableModel;
    private JTextField medecine_btn_field;

    public index(AgentCNSS u) throws SQLException {

        setTitle("Agent CNSS");
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


    private void refresh() {
        if (book_label != null) remove(book_label);
        if (add_agent_button != null) remove(add_agent_button);
        if (setting_agent_button != null) remove(setting_agent_button);
        if (delete_book_button != null) remove(delete_book_button);
        if (scrollPane != null) remove(scrollPane);
        if (medecine_btn_field != null) remove(medecine_btn_field);
        if (medecine_btn_book_button != null) remove(medecine_btn_book_button);
        if (agentTable != null) agentTable = null;
        if (tableModel != null) tableModel = null;
        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == agent_btn) {
            refresh();

        } else if (e.getSource() == patient_btn) {
            refresh();
        } else if (e.getSource() == medecine_btn) {
            refresh();


        } else if (e.getSource() == analyse_btn) {
            refresh();
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


}