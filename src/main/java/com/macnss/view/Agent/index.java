package com.macnss.view.Agent;

import com.macnss.app.Models.user.Administrator;
import com.macnss.app.Models.user.AgentCNSS;
import com.macnss.view.Authentication.SigningAdministrator;

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
    private JButton statistic, all_book, search, available_book, brr_book, lost_book, user, awaiting_list, logout;
    private JButton add_book_button, edit_book_button, delete_book_button, search_book_button;
    private JTable bookTable;
    private JScrollPane scrollPane;
    private JLabel book_label;
    private DefaultTableModel tableModel;
    private JTextField search_field;

    String CurrentBookClicked = null;
    int CurrentBookClickedRow = -1;

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

        ImageIcon Statistic_icon = new ImageIcon("assets/icon/statistics.png");
        ImageIcon Book_icon = new ImageIcon("assets/icon/book.png");
        ImageIcon Search_icon = new ImageIcon("assets/icon/search.png");
        ImageIcon Available_icon = new ImageIcon("assets/icon/available.png");
        ImageIcon Borrowed_icon = new ImageIcon("assets/icon/borrow.png");
        ImageIcon Lost_icon = new ImageIcon("assets/icon/lost.png");
        ImageIcon Users_icon = new ImageIcon("assets/icon/user.png");
        ImageIcon Awaiting_icon = new ImageIcon("assets/icon/waiting.png");
        ImageIcon Logout_icon = new ImageIcon("assets/icon/logout.png");

        statistic = new JButton("  Statistic", Statistic_icon);
        all_book = new JButton("  All books", Book_icon);
        search = new JButton(" Search", Search_icon);
        available_book = new JButton("  Available books", Available_icon);
        brr_book = new JButton("  Borrowed books", Borrowed_icon);
        lost_book = new JButton("  Lost books", Lost_icon);
        user = new JButton("  Users", Users_icon);
        awaiting_list = new JButton("  Awaiting list", Awaiting_icon);
        logout = new JButton("  Logout", Logout_icon);

        setButtons(statistic);
        setButtons(all_book);
        setButtons(search);
        setButtons(available_book);
        setButtons(brr_book);
        setButtons(lost_book);
        setButtons(user);
        setButtons(awaiting_list);
        setButtons(logout);

        int y = 120;
        int y2 = 10;

        JLabel logoLabel = new JLabel(new ImageIcon(UserLogo));
        logoLabel.setBounds(50, 50 + y2, 64, 64);

        ImageIcon AppLogoIcon = new ImageIcon("assets/images/app/app.png");
        Image logo = AppLogoIcon.getImage();

        setIconImage(logo);

        JLabel AppLogoLabel = new JLabel(new ImageIcon(logo));
        AppLogoLabel.setBounds((screenWidth - 180), screenHeight - 200, 150, 139);


        JLabel user_name = new JLabel(u.getFullName());
        user_name.setFont(new Font("Arial", Font.PLAIN, 25));
        user_name.setBounds(127, 70 + y2, 200, 17);


        JLabel user_contact = new JLabel((u.getEmail() != null ? u.getEmail() : u.getPhone() != null ? u.getPhone() : u.getCnie()));
        user_contact.setFont(new Font("Arial", Font.PLAIN, 13));
        user_contact.setBounds(127, 90 + y2, 300, 15);

        String user_roles = "Agent CNSS";
        JLabel user_role = new JLabel(user_roles);
        user_role.setFont(new Font("Arial", Font.ITALIC, 10));
        user_role.setBounds(54, 130, 300, 15);

        statistic.setBounds(50, 100 + y, 250, 30);
        all_book.setBounds(50, 155 + y, 250, 30);
        search.setBounds(50, 210 + y, 200, 30);
        available_book.setBounds(50, 265 + y, 250, 30);
        brr_book.setBounds(50, 320 + y, 250, 30);
        lost_book.setBounds(50, 375 + y, 250, 30);
        user.setBounds(50, 430 + y, 250, 30);
        awaiting_list.setBounds(50, 485 + y, 250, 30);
        logout.setBounds(50, 540 + y, 250, 30);

        statistic.addActionListener(this);
        search.addActionListener(this);
        all_book.addActionListener(this);
        available_book.addActionListener(this);
        brr_book.addActionListener(this);
        lost_book.addActionListener(this);
        user.addActionListener(this);
        awaiting_list.addActionListener(this);
        logout.addActionListener(this);

        add(user_name);
        add(user_contact);
        add(user_role);

        add(logoLabel);
        add(statistic);
        add(all_book);
        add(search);
        add(available_book);
        add(brr_book);
        add(lost_book);
        add(user);
        add(awaiting_list);
        add(logout);
        setVisible(true);
    }


    private void refresh() {
        if (book_label != null) remove(book_label);
        if (add_book_button != null) remove(add_book_button);
        if (edit_book_button != null) remove(edit_book_button);
        if (delete_book_button != null) remove(delete_book_button);
        if (scrollPane != null) remove(scrollPane);

        if (search_field != null) remove(search_field);
        if (search_book_button != null) remove(search_book_button);


        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == statistic) {
            refresh();
        } else if (e.getSource() == all_book) {
            refresh();
        } else if (e.getSource() == search) {
            refresh();
        } else if (e.getSource() == available_book) {
            refresh();
        } else if (e.getSource() == brr_book) {
            refresh();
        } else if (e.getSource() == lost_book) {
            refresh();
        } else if (e.getSource() == user) {
            refresh();
        } else if (e.getSource() == awaiting_list) {
            refresh();
        } else if (e.getSource() == logout) {
            int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                dispose();
                new SigningAdministrator();
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