package fr.ensimag.view;

import fr.ensimag.controler.AddVehicleControl;
import fr.ensimag.controler.Response;
import fr.ensimag.model.energyType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AddVehiclePage extends JPanel{
    private JPanel rootPanel;
    private JLabel addAVehicleLabel;
    private JButton backButton;
    private JTextField registrationField;
    private JComboBox brandMenu;
    private JComboBox seatsMenu;
    private JTextField taxPowerField;
    private JComboBox energyMenu;
    private JTextField modelField;
    private JLabel numberOfSeatsLabel;
    private JLabel taxPowerLabel;
    private JLabel energyLabel;
    private JLabel modelLabel;
    private JLabel brandLabel;
    private JLabel registrationLabel;
    private JButton addThisVehicleButton;

    public AddVehiclePage(CardLayout cl, JPanel windowPanel, String[] listContent, AddVehicleControl control){
        addAVehicleLabel.setFont(new Font("Courier New", Font.ITALIC, 17));

        add(rootPanel);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(windowPanel, listContent[5]);
            }
        });
        addThisVehicleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VehicleFormValues vehicleValues = new VehicleFormValues(registrationField.getText(),
                        (String) brandMenu.getSelectedItem(), modelField.getText(),
                        (String) energyMenu.getSelectedItem(), (String) taxPowerField.getText(),
                        Integer.parseInt((String) seatsMenu.getSelectedItem()));
                Response res = null;
                try {
                    res = control.checkAndAddVehicle(vehicleValues);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(windowPanel, res.getResponse());
            }
        });
    }
}
