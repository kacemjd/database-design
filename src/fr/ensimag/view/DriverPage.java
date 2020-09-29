package fr.ensimag.view;

import fr.ensimag.controler.UserPageControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DriverPage extends JPanel{
    private JLabel titleLabel;
    private JPanel rootPanel;
    private JButton logoutButton;
    private JButton backButton;
    private JButton viewMyProfileButton;
    private JButton addAVehicleButton;
    private JButton shareMyTripButton;
    private JButton myTripsButton;

    public DriverPage(CardLayout cl, JPanel windowPanel, String[] listContent, ProfilePage profilePage, DriverBuildTrip buildTripPage, DriverTripsPage driverTripsPage, UserPageControl control){
//        List<Trip> trips = new ArrayList<>();
//
//        Troncon t1 = new Troncon("Lille", "Paris", 1);
//        Troncon t2 = new Troncon("Paris", "Lyon", 2);
//        Troncon t3 = new Troncon("Lyon", "Marseille", 3);
//        List<Troncon> troncons = new ArrayList<>();
//        troncons.add(t1);
//        troncons.add(t2);
//        troncons.add(t3);
//
//        Trip trip1 = new Trip("20-02-2020", troncons);
//
//        trips.add(trip1);
//
//        t1 = new Troncon("Brest", "Rouen", 4);
//        t2 = new Troncon("Rouen", "Paris", 5);
//        t3 = new Troncon("Paris", "Strasbourg", 6);
//        troncons = new ArrayList<>();
//        troncons.add(t1);
//        troncons.add(t2);
//        troncons.add(t3);
//
//       trip1 = new Trip("22-03-2020", troncons);
//
//        trips.add(trip1);


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
            public void actionPerformed(ActionEvent e){
                    boolean user = profilePage.setProfileFields();
                    cl.show(windowPanel, listContent[3]);
                }
        });
        addAVehicleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(windowPanel, listContent[6]);
            }
        });
        shareMyTripButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buildTripPage.setList();
                cl.show(windowPanel, listContent[7]);
            }
        });
        myTripsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.setDriverTripsPage(driverTripsPage);

                cl.show(windowPanel, listContent[8]);
            }
        });
    }


}
