package fr.ensimag.view;

import fr.ensimag.controler.*;
import fr.ensimag.model.Trip;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DriverTrip extends JPanel{
    private Trip trip;
    private JLabel date;
    private JButton see;
    private JPanel rootPanel;
    private JLabel TripTitle;
    private int nbTroncons;
    private int offset = 1;
    private  boolean show;
    private ArrayList<JButton> validateButtonsList;

    public DriverTrip(Trip trip, DriverTripControl control){
        this.trip = trip;
        this.date = new JLabel(trip.getTripDate());
        this.see = new JButton("see");
        this.nbTroncons = trip.getTroncons().size();
        this.TripTitle = new JLabel(trip.getSectionN(0).getStartCity() + " - " + trip.getSectionN(nbTroncons-1).getEndCity());
        this.TripTitle.setFont(new Font("Courier New", Font.BOLD, 15));
        this.show= false;

        validateButtonsList = new ArrayList<>(); //Will contain all the trip section validate buttons

        rootPanel = new JPanel();
        rootPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JPanel panel;

        c.gridy=0;
        c.gridwidth=4;

        panel = new JPanel();
        panel.setBackground(Color.red);
        panel.setLayout(new FlowLayout());
        panel.add(this.date);
        panel.add(this.TripTitle);
        panel.add(this.see);
        rootPanel.add(panel, c);

        for(int i = 0 ; i < trip.getTroncons().size(); i++){
            c.gridy=i+1;
            panel = new JPanel();

            panel.setLayout(new FlowLayout());
            panel.add(new JLabel(trip.getSectionN(i).getStartCity()));
            panel.add(new JLabel(" - " + trip.getSectionN(i).getEndCity()));
            validateButtonsList.add(new JButton("Validate"));
            panel.add(validateButtonsList.get(i));
            rootPanel.add(panel, c);

            final int final_i = i;
            validateButtonsList.get(i).addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if((final_i>0 && !validateButtonsList.get(final_i-1).isEnabled()) || final_i ==0){
                            Object[] options1 = {"Good, validate it!",
                                    "Not done yet, wait."};
                            int n1 = JOptionPane.showOptionDialog(null,
                                    "How was this section?",
                                    "Section validation",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,     //do not use a custom Icon
                                    options1,  //the titles of buttons
                                    options1[0]); //default button title
                            if(n1 == 0){
                                Response res = control.validateTroncon(trip.getSectionN(final_i).getTripID());
                                JOptionPane.showMessageDialog(null, res.getResponse());

                                if(res.getStatusCode()) validateButtonsList.get(final_i).setText("Validated");
                                if(res.getStatusCode()) validateButtonsList.get(final_i).setEnabled(false);


                                trip.incrNbValidatedSections();

                                if(trip.isTripTerminated()){
                                    Object[] options = {"Yes, it went fine",
                                            "No, not yet"};
                                    int n = JOptionPane.showOptionDialog(null,
                                            "Is the trip done?",
                                            "End of trip",
                                            JOptionPane.YES_NO_OPTION,
                                            JOptionPane.QUESTION_MESSAGE,
                                            null,     //do not use a custom Icon
                                            options,  //the titles of buttons
                                            options[0]); //default button title
                                    if(n==0){
                                        rootPanel.getComponent(0).setBackground(Color.green);
                                        Response res2 = control.validateTrip(trip.getSectionN(final_i).getTripID());
                                        JOptionPane.showMessageDialog(null, res2.getResponse());
                                    }
                                }
                            }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Please validate previous section(s)");
                    }
                }
            });
        }

        showDetails(false); //Hide the trip sections at initialization

        add(rootPanel);

        //Listen to a click on "see" button to hide/show trip sections
        see.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                show = !show;
                showDetails(show);
            }
        });

    }

    //If visible true, show all the sections of the trip
    public void showDetails(boolean visible){
        this.show = visible;
        for(int j = 1 ; j< rootPanel.getComponentCount() ; j++){
            rootPanel.getComponent(j).setVisible(visible);
        }
    }
}
