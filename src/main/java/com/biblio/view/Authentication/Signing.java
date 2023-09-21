package com.biblio.view.Authentication;

import com.biblio.app.Services.AuthenticationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Signing extends JFrame implements ActionListener {

    AuthenticationController auth;

    private JTextField username;
    private JPasswordField password;
    private JButton loginButton, resetButton,forgetPasswordButton;
    private JLabel usernameLabel, passwordLabel;
    private ImageIcon userIcon, passwordIcon;
    private Image logo;

    public Signing() {
        auth = new AuthenticationController();

        setTitle("Signing");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);


        userIcon = new ImageIcon("user.png");
        passwordIcon = new ImageIcon("password.png");

        ImageIcon logoIcon = new ImageIcon("assets/images/app/cnss.png");
        Image logo = logoIcon.getImage();

        username = new JTextField();
        password = new JPasswordField();
        loginButton = new JButton("Login");
        forgetPasswordButton = new JButton("Forget Password ?");
        resetButton = new JButton("R");
        usernameLabel = new JLabel("Username :");
        passwordLabel = new JLabel("Password :");

        JLabel logoLabel = new JLabel(new ImageIcon(logo));
        logoLabel.setBounds(175, 10, 250, 250);

        usernameLabel.setBounds(60, 300, 470, 30);
        usernameLabel.setIcon(userIcon);
        username.setBounds(60, 335, 460, 35);

        passwordLabel.setBounds(60, 390, 470, 30);
        passwordLabel.setIcon(passwordIcon);
        password.setBounds(60, 425, 460, 35);

        loginButton.setBounds(60, 550, 175, 35);
        loginButton.setForeground(new Color(29, 170, 172));
//        loginButton.setBorder(BorderFactory.createLineBorder(new Color(26, 71, 132), 2));
        loginButton.setFont(new Font("Arial", Font.PLAIN, 16));
        loginButton.setOpaque(true);


        forgetPasswordButton.setBounds(360, 550, 300, 35);
        forgetPasswordButton.setForeground(new Color(26, 71, 132));
        forgetPasswordButton.setFont(new Font("Arial", Font.PLAIN, 16));
        forgetPasswordButton.setBorderPainted(false);
        forgetPasswordButton.setHorizontalAlignment(SwingConstants.LEFT);
        forgetPasswordButton.setContentAreaFilled(false);
        forgetPasswordButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


        loginButton.setContentAreaFilled(false);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        loginButton.addActionListener(this);
        resetButton.addActionListener(this);

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
            String enteredUsername = username.getText();
            String enteredPassword = new String(password.getPassword());

            if (auth.authenticate(enteredUsername, enteredPassword)) {
                JOptionPane.showMessageDialog(this, "You are successfully logged in", "Success", JOptionPane.INFORMATION_MESSAGE);

                dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Username or Password Incorrect", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == resetButton) {
            username.setText("");
            password.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Signing().setVisible(true);
        });
    }
}