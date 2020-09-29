package fr.ensimag.view;

import fr.ensimag.controler.Response;
import fr.ensimag.controler.UserPageControl;
import fr.ensimag.model.Car;
import fr.ensimag.model.User;
import fr.ensimag.model.Driver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

public class ProfilePage extends JPanel{
    private JPanel rootPanel;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField cityField;
    private JButton saveButton;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel yourProfileLabel;
    private JLabel livingCityLabel;
    private JLabel newPasswordLabel;
    private JLabel confirmationLabel;
    private JLabel changeYourPasswordLabel;
    private JPasswordField confirmationField;
    private JPasswordField newPasswordField;
    private JButton backButton;
    private JButton refillButton;
    private JLabel wallLabel;
    private JComboBox vehiclesMenu;
    private JComboBox refillMenu;
    private JPasswordField oldPasswordField;
    private JLabel oldPasswordLabel;
    private JLabel vehiclesLabel;
    private UserPageControl control;

    public ProfilePage(CardLayout cl, JPanel windowPanel, String[] listContent, UserPageControl control){
        setControl(control);

        clearPasswordFields();

        yourProfileLabel.setFont(new Font("Courier New", Font.ITALIC, 17));
        changeYourPasswordLabel.setFont(new Font("Courier New", Font.BOLD, 14));

        add(rootPanel);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Response res = control.updateProfile(control.getUserInfo(), firstNameField.getText(), lastNameField.getText(), cityField.getText(), String.valueOf(oldPasswordField.getPassword()), String.valueOf(newPasswordField.getPassword()), String.valueOf(confirmationField.getPassword()));
                JOptionPane.showMessageDialog(windowPanel, res.getResponse());
                clearPasswordFields();
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(windowPanel, listContent[2]);
            }
        });
        refillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(refillMenu.getSelectedItem());
                Response res = control.updateWallet(Integer.parseInt(((String) refillMenu.getSelectedItem()).substring(1)));
                if(res.getStatusCode() == true){
                    wallLabel.setText(res.getResponse());
                    JOptionPane.showMessageDialog(windowPanel, "Wallet well updated!");
                }
                else{
                    JOptionPane.showMessageDialog(windowPanel, res.getResponse());
                }

            }
        });
    }

    public boolean setProfileFields(){
        //System.out.println("set ok");
        User user = control.getUserInfo();

        firstNameField.removeAll();
        lastNameField.removeAll();
        cityField.removeAll();
        wallLabel.removeAll();
        vehiclesMenu.removeAllItems();

        firstNameField.setText(user.getFirstName());
        lastNameField.setText(user.getName());
        cityField.setText(user.getCity());
        wallLabel.setText("$" + String.valueOf(user.getSolde()));

        //TODO: construire liste véhicules
        Driver driver = (Driver)user;

        Iterator<Car> it = driver.iteratorCars();

        while(it.hasNext()){
            Car car = (Car)it.next();
            vehiclesMenu.addItem(car.getIdImmatriculation());
        }

        return true;
    }

    private void clearPasswordFields(){
        oldPasswordField.setText("");
        newPasswordField.setText("");
        confirmationField.setText("");
    }

    public void setControl(UserPageControl control) {
        this.control = control;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
