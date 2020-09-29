package fr.ensimag.view;

import fr.ensimag.controler.RegistrationControl;
import fr.ensimag.controler.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class RegisterForm extends JPanel{
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JComboBox cityField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton confirmButton;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel emailLabel;
    private JLabel cityLabel;
    private JLabel passwordLabel;
    private JLabel confirmPasswordLabel;
    private JPanel rootPanel;
    private JButton iAlreadyHaveAnButton;
    private JLabel signUpLabel;
    private RegisterFormValues formValues;

    public RegisterForm(CardLayout cl, JPanel windowPanel, String[] listContent, RegistrationControl control){
        signUpLabel.setFont(new Font("Courier New", Font.ITALIC, 17));

        add(rootPanel);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formValues = new RegisterFormValues(
                        firstNameField.getText(),
                        lastNameField.getText(),
                        emailField.getText(),
                        (String) cityField.getSelectedItem(),
                        new String(passwordField.getPassword()),
                        new String (confirmPasswordField.getPassword())
                );

                Response res= control.checkRegistrationValues(formValues);

                System.out.println(res.getResponse());

                if(res.getStatusCode() == true)
                    cl.show(windowPanel, listContent[1]);

                JOptionPane.showMessageDialog(windowPanel, res.getResponse());
            }
        });

        iAlreadyHaveAnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(windowPanel, listContent[1]);
            }
        });
    }
}
