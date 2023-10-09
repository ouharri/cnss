package com.macnss.view.Authentication;

import com.macnss.app.Models.company;
import com.macnss.app.Models.user.AgentCNSS;
import com.macnss.app.Services.Authentication;
import com.macnss.database.dao.AgentCNSSDao;
import com.macnss.database.dao.CompanyDao;
import com.macnss.view.Agent.index;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class SigningCompany extends JFrame implements ActionListener {

    Authentication auth;

    private JTextField username, code1, code2, code3, code4, code5, code6;
    private JPasswordField password;
    private JButton loginButton, resetButton, forgetPasswordButton, confirmButton, resendCode;
    private JLabel usernameLabel, passwordLabel, codeLabel;
    private ImageIcon userIcon, passwordIcon;
    private Image logo;

    private String enteredUsername = null, enteredPassword = null;

    public SigningCompany() {
        auth = new Authentication();

        setTitle("Signing Company");
        setSize(560, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);


        userIcon = new ImageIcon("user.png");
        passwordIcon = new ImageIcon("password.png");

        ImageIcon logoIcon = new ImageIcon("assets/images/app/cnss.png");
        Image logo = logoIcon.getImage();

        setIconImage(logo);

        username = new JTextField();
        password = new JPasswordField();
        loginButton = new JButton("Login");
        forgetPasswordButton = new JButton("Forget Password ?");
        resetButton = new JButton("R");
        usernameLabel = new JLabel("Username :");
        passwordLabel = new JLabel("Password :");

        codeLabel = new JLabel("Code Sending to your email :");
        code1 = new JTextField();
        code2 = new JTextField();
        code3 = new JTextField();
        code4 = new JTextField();
        code5 = new JTextField();
        code6 = new JTextField();
        confirmButton = new JButton("Confirm");
        resendCode = new JButton("Resend Code ?");

        JLabel logoLabel = new JLabel(new ImageIcon(logo));
        logoLabel.setBounds(130, 40, 300, 282);

        usernameLabel.setBounds(40, 340, 470, 30);
        usernameLabel.setIcon(userIcon);
        username.setBounds(40, 375, 460, 35);

        passwordLabel.setBounds(40, 430, 470, 30);
        passwordLabel.setIcon(passwordIcon);
        password.setBounds(40, 475, 460, 35);

        loginButton.setBounds(45, 560, 170, 30);
        loginButton.setForeground(new Color(29, 170, 172));
        loginButton.setFont(new Font("Arial", Font.PLAIN, 16));
        loginButton.setOpaque(true);
        loginButton.setContentAreaFilled(false);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


        forgetPasswordButton.setBounds(340, 560, 300, 35);
        forgetPasswordButton.setForeground(new Color(26, 71, 132));
        forgetPasswordButton.setFont(new Font("Arial", Font.PLAIN, 16));
        forgetPasswordButton.setBorderPainted(false);
        forgetPasswordButton.setHorizontalAlignment(SwingConstants.LEFT);
        forgetPasswordButton.setContentAreaFilled(false);
        forgetPasswordButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        codeLabel.setBounds(70, 355, 470, 30);
        code1.setBounds(70, 400, 50, 50);
        code2.setBounds(140, 400, 50, 50);
        code3.setBounds(210, 400, 50, 50);
        code4.setBounds(280, 400, 50, 50);
        code5.setBounds(350, 400, 50, 50);
        code6.setBounds(430, 400, 50, 50);

        confirmButton.setBounds(70, 520, 170, 30);
        confirmButton.setForeground(new Color(29, 170, 172));
        confirmButton.setFont(new Font("Arial", Font.PLAIN, 16));
        confirmButton.setOpaque(true);
        confirmButton.setContentAreaFilled(false);
        confirmButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        resendCode.setBounds(340, 520, 300, 35);
        resendCode.setForeground(new Color(26, 71, 132));
        resendCode.setFont(new Font("Arial", Font.PLAIN, 16));
        resendCode.setBorderPainted(false);
        resendCode.setHorizontalAlignment(SwingConstants.LEFT);
        resendCode.setContentAreaFilled(false);
        resendCode.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
        forgetPasswordButton.addActionListener(this);
        confirmButton.addActionListener(this);

        add(logoLabel);
        add(usernameLabel);
        add(username);
        add(passwordLabel);
        add(password);
        add(loginButton);
        add(forgetPasswordButton);


        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            enteredUsername = username.getText();
            enteredPassword = new String(password.getPassword());

            if (auth.authenticateCompany(enteredUsername, enteredPassword)) {

                company Company = null;
                    Company = new CompanyDao().where("email", enteredUsername).and("psw_hash", enteredPassword).findOne();
                try (CompanyDao dao = new CompanyDao()) {
                } catch (Exception ex) {
                    return;
                }


                try {
                    new index(new AgentCNSS());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                remove(usernameLabel);
                remove(username);
                remove(passwordLabel);
                remove(password);
                remove(loginButton);
                remove(forgetPasswordButton);

                add(codeLabel);
                add(code1);
                add(code2);
                add(code3);
                add(code4);
                add(code5);
                add(code6);
                add(confirmButton);
                add(resendCode);

                revalidate();
                repaint();
                dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Username or Password Incorrect", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == forgetPasswordButton) {
            JOptionPane.showMessageDialog(this, "username or password  incorrect !", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (e.getSource() == resetButton) {
            username.setText("");
            password.setText("");
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SigningAgentCNS().setVisible(true);
        });
    }
}
