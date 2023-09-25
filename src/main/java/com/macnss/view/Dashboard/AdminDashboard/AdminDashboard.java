package com.macnss.view.Dashboard.AdminDashboard;

import com.macnss.view.Dashboard.AdminDashboard.AgentManagement.AgentManagemenetFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboard extends JFrame implements ActionListener {
    private JButton agentManagementButton;
    private JButton medicineManagementButton;
    private JLabel operationLabel;


    public AdminDashboard() {
        setTitle("Admin dashboard");
        setSize(400,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);

        // label
        operationLabel = new JLabel("Choose an operation :");
        operationLabel.setBounds(this.getWidth() / 3, 30, 600, 50);
        operationLabel.setForeground(Color.BLACK);

        add(operationLabel);

        //Agent management button
        agentManagementButton = new JButton("Agent management");
        agentManagementButton.setToolTipText("click to manage agents");
        agentManagementButton.setForeground(Color.white);

        agentManagementButton.setBounds(50,this.getHeight() / 3,150,30);
        agentManagementButton.setBackground(new Color(0, 102, 204));
        agentManagementButton.setBorderPainted(false);


        agentManagementButton.addActionListener(this);
        add(agentManagementButton);

        //Medicine management button
        medicineManagementButton = new JButton("Medicine management");
        medicineManagementButton.setToolTipText("click to manage medicines");
        medicineManagementButton.setForeground(Color.white);

        medicineManagementButton.setBackground(new Color(255,195,0));
        medicineManagementButton.setBounds(220,this.getHeight() / 3 ,150,30);
        medicineManagementButton.setBorderPainted(false);

        medicineManagementButton.addActionListener(this);
        add(medicineManagementButton);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater( () -> {
            if(e.getSource() == agentManagementButton) {
                AgentManagemenetFrame agentManagementFrame = new AgentManagemenetFrame();
                agentManagementFrame.setVisible(true);
            }else if(e.getSource() == medicineManagementButton){
                MedicineManagementFrame medicineManagementFrame = new MedicineManagementFrame();
                medicineManagementFrame.setVisible(true);

            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AdminDashboard().setVisible(true);
        });
    }

    public static class MedicineManagementFrame extends JFrame implements ActionListener {
        private JButton createMedicineButton;
        private JButton updateMedicineButton;
        private JButton deleteMedicineButton;
        private JButton displayMedicinesButton;
        private JLabel cnssLabel;
        private JPanel currentPanel;

        public MedicineManagementFrame() {
            setTitle("Medicine management");
            setSize(1400,900);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            // Logo
            ImageIcon logoIcon = new ImageIcon("assets/images/app/cnss.png");
            cnssLabel = new JLabel(logoIcon);
            add(cnssLabel);

            // Create panel for the sidebar
            JPanel sidebarPanel = new JPanel();
            sidebarPanel.setLayout(new GridLayout(4,1));
            sidebarPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

            // Create placeholder panel
            currentPanel = new JPanel();
            currentPanel.setBounds(0,100,1600,600);
            // Icons
            ImageIcon createIcon = new ImageIcon("create");
            ImageIcon updateIcon = new ImageIcon("update");
            ImageIcon deleteIcon = new ImageIcon("delete");
            ImageIcon displayIcon = new ImageIcon("display");

            // Buttons
            createMedicineButton = new JButton("Create medicine", createIcon);
            createMedicineButton.setBackground(new Color(80, 168, 87));
            createMedicineButton.setBorderPainted(false);
            createMedicineButton.addActionListener(this);

            updateMedicineButton = new JButton("Update medicine", updateIcon);
            updateMedicineButton.setBackground(new Color(255, 215, 0));
            updateMedicineButton.setBorderPainted(false);
            updateMedicineButton.addActionListener(this);

            deleteMedicineButton = new JButton("Delete medicine", deleteIcon);
            deleteMedicineButton.setBackground(new Color(220, 53, 69));
            deleteMedicineButton.setBorderPainted(false);
            deleteMedicineButton.addActionListener(this);

            displayMedicinesButton = new JButton("Display medicines", displayIcon);
            displayMedicinesButton.setBorderPainted(false);
            displayMedicinesButton.setBackground(new Color(139, 69, 19));
            displayMedicinesButton.addActionListener(this);

            sidebarPanel.add(createMedicineButton);
            sidebarPanel.add(updateMedicineButton);
            sidebarPanel.add(deleteMedicineButton);
            sidebarPanel.add(displayMedicinesButton);

            sidebarPanel.setPreferredSize(new Dimension(150, getHeight()));

            add(sidebarPanel, BorderLayout.WEST);
            add(currentPanel, BorderLayout.CENTER);
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                new MedicineManagementFrame().setVisible(true);
            });
        }

        private Component addCreatePanel() {
            JPanel medicinePanel = new JPanel();
            setSize(1401,900);

            medicinePanel.setLayout(new GridLayout(7, 2, 10, 10));
            // Create labels and text fields for medicine properties
            JLabel nameLabel = new JLabel("Name:");
            JTextField nameField = new JTextField();
            nameField.setPreferredSize(new Dimension(250, 60));

            JLabel manufacturerLabel = new JLabel("Manufacturer:");
            JTextField manufacturerField = new JTextField();

            JLabel dosageLabel = new JLabel("Dosage:");
            JTextField dosageField = new JTextField();

            JLabel priceLabel = new JLabel("Price:");
            JTextField priceField = new JTextField();

            JLabel expiryDateLabel = new JLabel("Expiry Date (yyyy-MM-dd):");
            JTextField expiryDateField = new JTextField();

            JLabel categoryLabel = new JLabel("Category:");
            JComboBox<String> categoryComboBox = new JComboBox<>(new String[]{"Category 1", "Category 2"});

            JButton createButton = new JButton("Create Medicine");
            createButton.setBackground(new Color(80, 168, 87));

            // Add components to the panel
            medicinePanel.add(nameLabel);
            medicinePanel.add(nameField);

            medicinePanel.add(manufacturerLabel);
            medicinePanel.add(manufacturerField);

            medicinePanel.add(dosageLabel);
            medicinePanel.add(dosageField);

            medicinePanel.add(priceLabel);
            medicinePanel.add(priceField);

            medicinePanel.add(expiryDateLabel);
            medicinePanel.add(expiryDateField);

            medicinePanel.add(categoryLabel);
            medicinePanel.add(categoryComboBox);

            medicinePanel.add(new JLabel()); // Empty label for spacing
            medicinePanel.add(createButton);

            return medicinePanel;
        }

        private Component addUpdatePanel() {
            JPanel medicinePanel = new JPanel();
            setSize(1401,900);

            medicinePanel.setLayout(new GridLayout(7, 2, 10, 10));
            // Create labels and text fields for medicine properties
            JLabel nameLabel = new JLabel("Name:");
            JTextField nameField = new JTextField();
            nameField.setPreferredSize(new Dimension(250, 60));

            JLabel manufacturerLabel = new JLabel("Manufacturer:");
            JTextField manufacturerField = new JTextField();

            JLabel dosageLabel = new JLabel("Dosage:");
            JTextField dosageField = new JTextField();

            JLabel priceLabel = new JLabel("Price:");
            JTextField priceField = new JTextField();

            JLabel expiryDateLabel = new JLabel("Expiry Date (yyyy-MM-dd):");
            JTextField expiryDateField = new JTextField();

            JLabel categoryLabel = new JLabel("Category:");
            JComboBox<String> categoryComboBox = new JComboBox<>(new String[]{"Category 1", "Category 2"});

            JButton updateButton = new JButton("Update Medicine");
            updateButton.setBackground(new Color(222, 184, 41));

            // Add components to the panel
            medicinePanel.add(nameLabel);
            medicinePanel.add(nameField);

            medicinePanel.add(manufacturerLabel);
            medicinePanel.add(manufacturerField);

            medicinePanel.add(dosageLabel);
            medicinePanel.add(dosageField);

            medicinePanel.add(priceLabel);
            medicinePanel.add(priceField);

            medicinePanel.add(expiryDateLabel);
            medicinePanel.add(expiryDateField);

            medicinePanel.add(categoryLabel);
            medicinePanel.add(categoryComboBox);

            medicinePanel.add(new JLabel()); // Empty label for spacing
            medicinePanel.add(updateButton);

            return medicinePanel;
        }

        private Component addDeletePanel() {
            JPanel medicinePanel = new JPanel();
            setSize(1401,900);

            medicinePanel.setLayout(new GridLayout(7, 2, 10, 10));
            // Create labels and text fields for medicine properties
            JLabel nameLabel = new JLabel("Enter the Name of medicine to be deleted:");
            JTextField nameField = new JTextField();
            nameField.setPreferredSize(new Dimension(250, 60));

            JButton deleteButton = new JButton("Delete Medicine");
            deleteButton.setBackground(new Color(253, 62, 62));

            medicinePanel.add(nameField);
            medicinePanel.add(nameLabel);

            return medicinePanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == createMedicineButton) {
                currentPanel.removeAll();
                currentPanel.add(addCreatePanel());
            } else if(e.getSource() == updateMedicineButton) {
                currentPanel.removeAll();
                currentPanel.add(addUpdatePanel());
            } else if(e.getSource() == deleteMedicineButton) {
                currentPanel.removeAll();
                currentPanel.add(addDeletePanel());
            }
            currentPanel.revalidate();
            currentPanel.repaint();
        }
    }
}
