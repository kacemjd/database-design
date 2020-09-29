package fr.ensimag.view;

import fr.ensimag.controler.Response;
import fr.ensimag.controler.RoleChoiceControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoleChoicePage extends JPanel{
    private JPanel rootPanel;
    private JButton passengerButton;
    private JButton driverButton;
    private JLabel choseYourSideLabel;

    public RoleChoicePage(CardLayout cl, JPanel windowPanel, String[] listContent, RoleChoiceControl control){ //TODO: ajouter paramètre pour commmuniquer avec modèle directement
        choseYourSideLabel.setFont(new Font("Courier New", Font.ITALIC, 17));

        add(rootPanel);

        passengerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Response res = control.passengerClick();
                if(res.getStatusCode() == true) {
                    cl.show(windowPanel, listContent[4]);
                }
            }
        });
        driverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Response res = control.driverClick();
                if(res.getStatusCode() == true) {
                    cl.show(windowPanel, listContent[5]);
                }
            }
        });
    }
}
