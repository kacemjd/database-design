package fr.ensimag.view;

import fr.ensimag.controler.UserPageControl;
import fr.ensimag.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PassengerPage extends JPanel{
    private JPanel rootPanel;
    private JLabel titleLabel;
    private JButton backButton;
    private JButton logoutButton;
    private JButton viewMyProfileButton;
    private JButton createARouteButton;
    private JButton myTripsButton;

    public PassengerPage(CardLayout cl, JPanel windowPanel, String[] listContent, ProfilePage profilePage, PassengerTripsPage passengerTripsPage, UserPageControl control){
        titleLabel.setFont(new Font("Courier New", Font.ITALIC, 17));

        add(rootPanel);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(windowPanel, listContent[2]);
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(windowPanel, listContent[1]);
            }
        });
        viewMyProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean user = profilePage.setProfileFields();
                cl.show(windowPanel, listContent[3]);
            }
        });
        createARouteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(windowPanel, listContent[9]);
            }
        });
        myTripsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.setPassengerTripsPage(passengerTripsPage);

                cl.show(windowPanel, listContent[10]);
            }
        });
    }
}
