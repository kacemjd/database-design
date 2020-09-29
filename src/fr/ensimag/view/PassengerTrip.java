package fr.ensimag.view;

import fr.ensimag.controler.*;
import fr.ensimag.model.Trip;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class PassengerTrip extends JPanel {
    private Trip trip;
    private JLabel date;
    private JButton see;
    private JPanel rootPanel;
    private JLabel TripTitle;
    private int nbTroncons;
    private int offset = 1;
    private  boolean show;
    private ArrayList<JButton> validateButtonsList;
    private boolean tripIsDone;
    private boolean sectionIdDone;

    public PassengerTrip(CardLayout cl, JPanel windowPanel, String[] listContent, ProfilePage profilePage, Trip trip, PassengerTripControl control){
        this.trip = trip;
        this.date = new JLabel(trip.getTripDate());
        this.see = new JButton("see");
        this.nbTroncons = trip.getTroncons().size();
        this.TripTitle = new JLabel(trip.getSectionN(0).getStartCity() + " - " + trip.getSectionN(nbTroncons-1).getEndCity());
        this.TripTitle.setFont(new Font("Courier New", Font.BOLD, 15));
        this.show= false;
        this.tripIsDone = control.isTripDone(trip.getTripID());

        validateButtonsList = new ArrayList<>(); //Will contain all the trip section validate buttons

        rootPanel = new JPanel();
        rootPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JPanel panel;

        c.gridy=0;
        c.gridwidth=4;

        panel = new JPanel();
        if(tripIsDone){
            panel.setBackground(Color.green);
        }
        else{
            panel.setBackground(Color.red);
        }

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
            JButton jb = new JButton();
            if(control.isSectionDone(trip.getTripID(), trip.getSectionN(i).getSectionID())){
                jb.setText("Validated");
                jb.setEnabled(false);
            }
            else{
                jb.setText("Validate");
                jb.setEnabled(true);
            }
            validateButtonsList.add(jb);
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
                            trip.incrNbValidatedSections();

                            if(trip.isTripTerminated()){
                                //System.out.println("end of trip");
                                Object[] options = {"Yes, please",
                                        "No, I had an issue"};
                                int n = 0; //default button title
                                try {
                                    n = JOptionPane.showOptionDialog(null,
                                            "End of trip:\nWould you like to process the $"+ control.getPrice(trip.getSectionN(final_i).getTripID()) +" payment?",
                                            "Trip payment",
                                            JOptionPane.YES_NO_OPTION,
                                            JOptionPane.QUESTION_MESSAGE,
                                            null,     //do not use a custom Icon
                                            options,  //the titles of buttons
                                            options[0]);
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }
                                if(n==0){

                                    Response res2 = null;
                                    try {
                                        res2 = control.processPayment(trip.getSectionN(final_i).getTripID());
                                    } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                    }
                                    JOptionPane.showMessageDialog(null, res2.getResponse());
                                    if(res2.getStatusCode() == false){
                                        trip.decrNbValidatedSections();
                                        boolean user = profilePage.setProfileFields();
                                        cl.show(windowPanel, listContent[3]);
                                    }
                                    else{
                                        rootPanel.getComponent(0).setBackground(Color.green);
                                        validateButtonsList.get(final_i).setText("Validated");
                                        validateButtonsList.get(final_i).setEnabled(false);
                                        control.updateProfile();
                                    }
                                }
                            }
                            else{
                                Response res = control.validateTroncon(trip.getSectionN(final_i).getSectionID());
                                JOptionPane.showMessageDialog(null, res.getResponse());

                                if(res.getStatusCode() == true){
                                    if(res.getStatusCode()) validateButtonsList.get(final_i).setText("Validated");
                                    if(res.getStatusCode()) validateButtonsList.get(final_i).setEnabled(false);
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
