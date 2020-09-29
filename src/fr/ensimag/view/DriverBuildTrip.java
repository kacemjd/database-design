package fr.ensimag.view;

import fr.ensimag.controler.DriverBuildTripControl;
import fr.ensimag.controler.Response;
import fr.ensimag.controler.UserPageControl;
import fr.ensimag.model.Car;
import fr.ensimag.model.Driver;
import fr.ensimag.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

public class DriverBuildTrip extends JPanel{
    private JLabel titleLabel;
    private JComboBox vehiclesMenu;
    private JComboBox seatsMenu;
    private JButton saveButton;
    private JButton addASectionButton;
    private JPanel rootPanel;
    private JComboBox departureCitydMenu;
    private JComboBox arrivalCityMenu;
    private JButton backButton;
    private JTextField dateField;
    private JTextField timeField;
    private JLabel dateLabel;
    private JLabel timeLabel;
    private SectionForm departure;
    private SectionForm section1;
    private SectionForm section2;
    private SectionForm section3;
    private SectionForm arrival;
    private Integer SectionYoffset;
    private Integer sectionsCount;
    private DriverBuildTripControl control;

    public DriverBuildTrip(CardLayout cl, JPanel windowPanel, String[] listContent, DriverBuildTripControl control) {
        setControl(control);
        departure.titleLabel.setText("Departure city");
        section1.titleLabel.setText("Section 1");
        section2.titleLabel.setText("Section 2");
        section3.titleLabel.setText("Section 3");
        arrival.titleLabel.setText("Arrival city");

        titleLabel.setFont(new Font("Courier New", Font.ITALIC, 17));




        vehiclesMenu.removeAllItems();




        add(rootPanel);

        SectionYoffset= 5;
        sectionsCount = 1;

//        saveButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                //TODO
//            }
//        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(windowPanel, listContent[5]);
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] startPlace = departure.cityField.getText().split(", ");
                String[] endPlace = arrival.cityField.getText().split(", ");

                String date = dateField.getText() + " " + timeField.getText() ;
                String vehicle = String.valueOf(vehiclesMenu.getSelectedItem());
                String nbSeats = String.valueOf(seatsMenu.getSelectedItem());
                String departureCity = startPlace[0];
                String waitingTimeDeparture = departure.waitingTimeField.getText();
                String departureLatitude = departure.latitudeField.getText();
                String departureLongitude = departure.longitudeField.getText();
                String startLieuDit = startPlace[1];
                String arrivalCity = endPlace[0];
                String waitingTimeArrival = arrival.waitingTimeField.getText();
                String arrivalLatitude = arrival.latitudeField.getText();
                String arrivalLongitude = arrival.longitudeField.getText();
                String endLieuDit = endPlace[1];

                String[] section1Place = section1.cityField.getText().split(", ");
                String section1City = section1Place[0];
                String waitingTimeSection1 = section1.waitingTimeField.getText();
                String section1Latitude = section1.latitudeField.getText();
                String section1Longitude = section1.longitudeField.getText();
                String section1LieuDit;
                if (!section1City.equals("")) section1LieuDit = section1Place[1];
                else section1LieuDit = "";

                String[] section2Place = section2.cityField.getText().split(", ");
                String section2City = section2Place[0];
                String waitingTimeSection2 = section2.waitingTimeField.getText();
                String section2Latitude = section2.latitudeField.getText();
                String section2Longitude = section2.longitudeField.getText();
                String section2LieuDit;
                if (!section2City.equals("")) section2LieuDit = section2Place[1];
                else section2LieuDit = "";

                String[] section3Place = section3.cityField.getText().split(", ");
                String section3City = section3Place[0];
                String waitingTimeSection3 = section3.waitingTimeField.getText();
                String section3Latitude = section3.latitudeField.getText();
                String section3Longitude = section3.longitudeField.getText();
                String section3LieuDit;
                if (!section3City.equals("")) section3LieuDit = section3Place[1];
                else section3LieuDit = "";

                DriverBuildTripFormValues values = new DriverBuildTripFormValues(date,
                        vehicle,nbSeats, departureCity, startLieuDit, waitingTimeDeparture, departureLatitude, departureLongitude,
                        arrivalCity, endLieuDit, arrivalLatitude, arrivalLongitude);

                if(!section1City.equals(""))
                    values.addStop(section1City, section1LieuDit, section1Latitude, section1Longitude, waitingTimeSection1);

                if(!section2City.equals(""))
                    values.addStop(section2City, section2LieuDit, section2Latitude, section2Longitude, waitingTimeSection2);

                if(!section3City.equals(""))
                    values.addStop(section3City, section3LieuDit, section3Latitude, section3Longitude, waitingTimeSection3);

                Response res = control.checkAndAddDriverTrip(values);
                JOptionPane.showMessageDialog(windowPanel, res.getResponse());
                if(res.getStatusCode()) {
                    cl.show(windowPanel, listContent[5]);
                }
            }
        });
    }

    public void setList(){
        User user = control.getUserInfo();
        //System.out.println("user="+user);
        Driver driver = (Driver)user;

        Iterator<Car> it = driver.iteratorCars();

        while(it.hasNext()){
            Car car = it.next();
            vehiclesMenu.addItem(car.getIdImmatriculation());
        }
    }

    public void setControl(DriverBuildTripControl control) {
        this.control = control;
    }
}
