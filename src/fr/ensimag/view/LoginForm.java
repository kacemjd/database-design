package fr.ensimag.view;

import fr.ensimag.controler.LoginControl;
import fr.ensimag.controler.Response;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginForm extends JPanel{
    private JTextField emailField;
    private JButton goButton;
    private JPasswordField passwordField;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JLabel signInLabel;
    private JPanel rootPanel;
    private JButton createAnAccountButton;
    private JButton forgotYourPasswordButton;

    public LoginForm(CardLayout cl, JPanel windowPanel, String[] listContent, LoginControl control) throws SQLException {
        signInLabel.setFont(new Font("Courier New", Font.ITALIC, 17));

        add(rootPanel);
        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Response res = control.checkLoginValues(emailField.getText(), new String(passwordField.getPassword()));

                    if(res.getStatusCode() == true) {
                        cl.show(windowPanel, listContent[2]);
                    }

                    JOptionPane.showMessageDialog(windowPanel, res.getResponse());
                } catch (SQLException s) {
                    System.out.println("Connection error");
                }
            }
        });
        createAnAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(windowPanel, listContent[0]);
            }
        });
        forgotYourPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(windowPanel, "Not implemented yet");
            }
        });
    }
}
