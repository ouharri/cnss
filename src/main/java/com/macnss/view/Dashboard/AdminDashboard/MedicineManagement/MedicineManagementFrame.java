package com.macnss.view.Dashboard.AdminDashboard.MedicineManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MedicineManagementFrame extends JFrame implements ActionListener {
    private JButton createMedicineButton;
    private JButton updateMedicineButton;
    private JButton deleteMedicineButton;
    private JButton displayMedicinesButton;
    private JLabel cnssLabel;
    private JPanel currentPanel;

    public MedicineManagementFrame() {
        setTitle("Medicine management");
        setSize(1400, 900);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Logo
        ImageIcon logoIcon = new ImageIcon("assets/images/app/cnss.png");
        cnssLabel = new JLabel(logoIcon);
        add(cnssLabel);

        // Create panel for the sidebar
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new GridLayout(4, 1));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        // Create placeholder panel
        currentPanel = new JPanel();
        currentPanel.setLayout(new BorderLayout());

        // Buttons
        createMedicineButton = createSidebarButton("Create medicine", "create", new Color(80, 168, 87));
        updateMedicineButton = createSidebarButton("Update medicine", "update", new Color(255, 215, 0));
        deleteMedicineButton = createSidebarButton("Delete medicine", "delete", new Color(220, 53, 69));
        displayMedicinesButton = createSidebarButton("Display medicines", "display", new Color(139, 69, 19));

        // Add buttons to sidebar
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

    private JButton createSidebarButton(String text, String actionCommand, Color backgroundColor) {
        ImageIcon icon = new ImageIcon(actionCommand);
        JButton button = new JButton(text, icon);
        button.setBackground(backgroundColor);
        button.setBorderPainted(false);
        button.setActionCommand(actionCommand);
        button.addActionListener(this);
        return button;
    }

    private JPanel createMedicinePanel(String buttonLabel, Color buttonColor) {
        JPanel medicinePanel = new JPanel();
        medicinePanel.setLayout(new GridLayout(7, 2, 10, 10));

        // Create labels and text fields for medicine properties
        JTextField nameField = createTextField("Name:");
        JTextField manufacturerField = createTextField("Manufacturer:");
        JTextField dosageField = createTextField("Dosage:");
        JTextField priceField = createTextField("Price:");
        JTextField expiryDateField = createTextField("Expiry Date (yyyy-MM-dd):");

        JComboBox<String> categoryComboBox = new JComboBox<>(new String[]{"Category 1", "Category 2"});

        JButton actionButton = new JButton(buttonLabel);
        actionButton.setBackground(buttonColor);

        // Add components to the panel
        medicinePanel.add(createLabelAndField("Name:", nameField));
        medicinePanel.add(createLabelAndField("Manufacturer:", manufacturerField));
        medicinePanel.add(createLabelAndField("Dosage:", dosageField));
        medicinePanel.add(createLabelAndField("Price:", priceField));
        medicinePanel.add(createLabelAndField("Expiry Date (yyyy-MM-dd):", expiryDateField));
        medicinePanel.add(createLabelAndField("Category:", categoryComboBox));

        medicinePanel.add(new JLabel()); // Empty label for spacing
        medicinePanel.add(actionButton);

        actionButton.addActionListener(e -> {
            // Handle the button's action here
            String actionCommand = e.getActionCommand();
            switch (actionCommand) {
                case "Create medicine":
                    // Handle creating medicine
                    break;
                case "Update medicine":
                    // Handle updating medicine
                    break;
                case "Delete medicine":
                    // Handle deleting medicine
                    break;
                default:
                    break;
            }
        });

        return medicinePanel;
    }

    private JTextField createTextField(String label) {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(250, 60));
        return textField;
    }

    private JPanel createLabelAndField(String label, JComponent field) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(label));
        panel.add(field);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch (actionCommand) {
            case "Create medicine":
                currentPanel.removeAll();
                currentPanel.add(createMedicinePanel("Create medicine", new Color(80, 168, 87)));
                break;
            case "Update medicine":
                currentPanel.removeAll();
                currentPanel.add(createMedicinePanel("Update medicine", new Color(255, 215, 0)));
                break;
            case "Delete medicine":
                currentPanel.removeAll();
                currentPanel.add(createMedicinePanel("Delete medicine", new Color(220, 53, 69)));
                break;
            default:
                break;
        }
        currentPanel.revalidate();
        currentPanel.repaint();
    }
}
