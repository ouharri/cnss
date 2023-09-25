package com.macnss.view.Dashboard.AdminDashboard.AgentManagement;

import lombok.Builder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AgentManagemenetFrame extends JFrame implements ActionListener {
    private JButton createAgentButton;
    private JButton updateAgentButton;
    private JButton deleteAgentButton;
    private JButton displayAgentsButton;
    private JLabel cnssLabel;
    private JPanel currentPanel;

    private JTextField cnieField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField phoneField;
    private JTextField birthdayField;
    private JComboBox<String> genderComboBox;



    public AgentManagemenetFrame() {
        setTitle("Agent management");
        setSize(1400,900);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // logo

        ImageIcon logoIcon = new ImageIcon("assets/images/app/cnss.png");
        cnssLabel = new JLabel(logoIcon);
        add(cnssLabel);
        // Create panel for the sidebar

        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new GridLayout(4,1));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        // create placeholder panel
        currentPanel = new JPanel();
        currentPanel.setBounds(0,100,1600,600);
        // icons
        ImageIcon createIcon = new ImageIcon("create");
        ImageIcon updateIcon = new ImageIcon("update");
        ImageIcon deleteIcon = new ImageIcon("delete");
        ImageIcon displayIcon = new ImageIcon("display");

        // Buttons

        createAgentButton = new JButton("Create agent",createIcon);
        createAgentButton.setBackground(new Color(80, 168, 87));
        createAgentButton.setBorderPainted(false);
        createAgentButton.addActionListener(this);

        updateAgentButton = new JButton("Update agent",updateIcon);
        updateAgentButton.setBackground(new Color(255, 215, 0));
        updateAgentButton.setBorderPainted(false);
        updateAgentButton.addActionListener(this);


        deleteAgentButton = new JButton("Delete agent",deleteIcon);
        deleteAgentButton.setBackground(new Color(220, 53, 69));
        deleteAgentButton.setBorderPainted(false);
        deleteAgentButton.addActionListener(this);


        displayAgentsButton = new JButton("Display agents",displayIcon);
        displayAgentsButton.setBorderPainted(false);
        displayAgentsButton.setBackground(new Color(139, 69, 19));
        displayAgentsButton.addActionListener(this);



        sidebarPanel.add(createAgentButton);
        sidebarPanel.add(updateAgentButton);
        sidebarPanel.add(deleteAgentButton);
        sidebarPanel.add(displayAgentsButton);

        sidebarPanel.setPreferredSize(new Dimension(150, getHeight()));

        add(sidebarPanel,BorderLayout.WEST);
        add(currentPanel,BorderLayout.CENTER);


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AgentManagemenetFrame().setVisible(true);
        });
    }
    private Component createPanel() {
        JPanel agentPanel = new JPanel();
        setSize(1401,900);


        agentPanel.setLayout(new GridLayout(7,2,10,10));
        // Create labels and text fields for agent properties
        JLabel cnieLabel = new JLabel("CNIE:");
        cnieField = new JTextField();
        cnieField.setPreferredSize(new Dimension(250,60));

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameField = new JTextField();

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameField = new JTextField();

        JLabel phoneLabel = new JLabel("Phone:");
        phoneField = new JTextField();

        JLabel birthdayLabel = new JLabel("Birthday (yyyy-MM-dd):");
        birthdayField = new JTextField();

        JLabel genderLabel = new JLabel("Gender:");
        genderComboBox = new JComboBox<>(new String[]{"Male", "Female"});

        JButton createButton = new JButton("Create Agent");
        createButton.setBackground(new Color(80, 168, 87));

        createButton.addActionListener(this);




        // Add components to the panel
        agentPanel.add(cnieLabel);
        agentPanel.add(cnieField);

        agentPanel.add(firstNameLabel);
        agentPanel.add(firstNameField);

        agentPanel.add(lastNameLabel);
        agentPanel.add(lastNameField);

        agentPanel.add(phoneLabel);
        agentPanel.add(phoneField);

        agentPanel.add(birthdayLabel);
        agentPanel.add(birthdayField);

        agentPanel.add(genderLabel);
        agentPanel.add(genderComboBox);

        agentPanel.add(new JLabel()); // Empty label for spacing
        agentPanel.add(createButton);

        return agentPanel;
    }

    private Component updatePanel() {
        JPanel agentPanel = new JPanel();
        setSize(1401,900);


        agentPanel.setLayout(new GridLayout(7,2,10,10));
        // Create labels and text fields for agent properties
        JLabel cnieLabel = new JLabel("CNIE:");
        JTextField cnieField = new JTextField();
        cnieField.setPreferredSize(new Dimension(250,60));

        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameField = new JTextField();

        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameField = new JTextField();

        JLabel phoneLabel = new JLabel("Phone:");
        JTextField phoneField = new JTextField();

        JLabel birthdayLabel = new JLabel("Birthday (yyyy-MM-dd):");
        JTextField birthdayField = new JTextField();

        JLabel genderLabel = new JLabel("Gender:");
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Male", "Female"});

        JButton updateButton = new JButton("Update Agent");
        updateButton.setBackground(new Color(222, 184, 41));


        // Add components to the panel
        agentPanel.add(cnieLabel);
        agentPanel.add(cnieField);

        agentPanel.add(firstNameLabel);
        agentPanel.add(firstNameField);

        agentPanel.add(lastNameLabel);
        agentPanel.add(lastNameField);

        agentPanel.add(phoneLabel);
        agentPanel.add(phoneField);

        agentPanel.add(birthdayLabel);
        agentPanel.add(birthdayField);

        agentPanel.add(genderLabel);
        agentPanel.add(genderComboBox);

        agentPanel.add(new JLabel()); // Empty label for spacing
        agentPanel.add(updateButton);

        return agentPanel;
    }

    private Component deletePanel() {
        JPanel agentPanel = new JPanel();
        setSize(1401,900);


        agentPanel.setLayout(new GridLayout(7,2,10,10));
        // Create labels and text fields for agent properties
        JLabel cnieLabel = new JLabel("enter the CNIE of agent to be deleted:");
        JTextField cnieField = new JTextField();
        cnieField.setPreferredSize(new Dimension(250,60));

        JButton deleteButton = new JButton("Delete Agent");
        deleteButton.setBackground(new Color(253, 62, 62));

        agentPanel.add(cnieField);
        agentPanel.add(cnieLabel);

        return agentPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == createAgentButton) {
            currentPanel.removeAll();
            currentPanel.add(createPanel());
        }else if(e.getSource() == updateAgentButton) {
            currentPanel.removeAll();
            currentPanel.add(updatePanel());
        }else if(e.getSource() == deleteAgentButton) {
            currentPanel.removeAll();
            currentPanel.add(deletePanel());
        }else{
            JButton sourceButton = (JButton) e.getSource();
            System.out.println(sourceButton);
           if(sourceButton.getText().equals("Create Agent")) {
                String cnie = cnieField.getText();

                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String phone = phoneField.getText();
                // Get the text version
                String birthdayText = birthdayField.getText();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // to later parse the birthday text
                Date birthday;
                String gender = (String) genderComboBox.getSelectedItem();


                try {
                    birthday = dateFormat.parse(birthdayText);
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }

               AgentManagementLogic agentManagemenetLogic = new AgentManagementLogic();
                System.out.println(agentManagemenetLogic);
                agentManagemenetLogic.create(cnie,firstName,lastName,"phone",phone,birthday,"ff",gender);
            }

       }
    }




}
