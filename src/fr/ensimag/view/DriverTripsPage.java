package fr.ensimag.view;

import fr.ensimag.controler.DriverTripControl;
import fr.ensimag.model.Trip;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;


public class DriverTripsPage extends JPanel{
    private JLabel titleLabel;
    private JButton backButton;
    private JPanel rootPanel;
    private LinkedList<fr.ensimag.model.Trip> trips;
    private DriverTripControl control;

    public DriverTripsPage(CardLayout cl, JPanel windowPanel, String[] listContent, DriverTripControl control){
        this.control = control;

        add(rootPanel);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(windowPanel, listContent[5]);
            }
        });
    }

    public void setTrips(LinkedList<Trip> tripss){
        //Setup the trips to display on the page
        this.trips = tripss;

        titleLabel.setFont(new Font("Courier New", Font.ITALIC, 17));

        JPanel test = new JPanel();
        test.setLayout(new GridBagLayout());
        GridBagConstraints cc;

        for(int i = 0; i< trips.size(); i++){
            cc = new GridBagConstraints();
            cc.gridx=0;
            cc.gridy=i;
            test.add (new DriverTrip(trips.get(i), control),cc);
        }

        JScrollPane scrollPane = new JScrollPane(test);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(400, 400));

        GridBagConstraints c= new GridBagConstraints();

        c.gridx=0;
        c.gridy=0;
        rootPanel.add(titleLabel,c);

        c.gridx=0;
        c.gridy=1;
        rootPanel.add(scrollPane,c );

        c.gridx=0;
        c.gridy=2;
        rootPanel.add(backButton,c);
    }
}
