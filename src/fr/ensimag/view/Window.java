package fr.ensimag.view;

import fr.ensimag.controler.Controler;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class Window extends JFrame {
    CardLayout cl;
    JPanel content;

    private RegisterForm registerPage;
    private LoginForm loginPage;
    private RoleChoicePage roleChoicePage;
    private ProfilePage profilePage;
    private DriverPage driverPage;
    private PassengerPage passengerPage;
    private AddVehiclePage addVehiclePage;
    private DriverBuildTrip driverBuildTripPage;
    private DriverTripsPage driverTripsPage;
    private PassengerTripsPage passengerTripsPage;
    private PassengerResearchRoute passengerResearchRoutePage;

    String[] listContent = {"CARD_REGISTER", "CARD_LOGIN", "CARD_ROLE_CHOICE", "CARD_PROFILE", "CARD_PASSENGER", "CARD_DRIVER", "CARD_ADD_VEHICLE", "CARD_DRIVER_BUILD_TRIP", "CARD_DRIVER_TRIPS", "CARD_RESEARCH_ROUTES", "CARD_CHOOSE_ROUTE", "CARD_PASSENGER_TRIPS"};

    public Window(Controler control) throws IOException, SQLException {

        this.setContentPane(new BackgroundImage(new ImageIcon("fond.jpg").getImage()));

        setTitle("VerbiageVoiture");
        setSize(999,850);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        setIconImage(new ImageIcon("logo.png").getImage());
        cl = new CardLayout();
        content = new JPanel(cl);


        loginPage = new LoginForm(cl, content, listContent, control.getCheckLogin());
        content.add(loginPage, listContent[1]);

        roleChoicePage = new RoleChoicePage(cl, content, listContent, control.getRoleChoiceControl());
        content.add(roleChoicePage, listContent[2]);

        passengerResearchRoutePage = new PassengerResearchRoute(cl, content, listContent, control.getPassengerResearchRouteControl());
        content.add(passengerResearchRoutePage, listContent[9]);

        driverTripsPage = new DriverTripsPage(cl, content, listContent, control.getDriverTripControl());
        content.add(driverTripsPage, listContent[8]);

        driverBuildTripPage = new DriverBuildTrip(cl, content, listContent, control.getDriverBuildTripControl());
        content.add(driverBuildTripPage, listContent[7]);

        profilePage = new ProfilePage(cl, content, listContent, control.getUserPageControl());
        content.add(profilePage, listContent[3]);

        driverPage = new DriverPage(cl, content, listContent, profilePage, driverBuildTripPage, driverTripsPage, control.getUserPageControl());
        content.add(driverPage, listContent[5]);

        registerPage = new RegisterForm(cl, content, listContent, control.getCheckRegistration());
        content.add(registerPage, listContent[0]);

        passengerTripsPage = new PassengerTripsPage(cl, content, listContent, profilePage, control.getPassengerTripControl());
        content.add(passengerTripsPage, listContent[10]);

        passengerPage = new PassengerPage(cl, content, listContent, profilePage, passengerTripsPage, control.getUserPageControl());
        content.add(passengerPage, listContent[4]);

        addVehiclePage = new AddVehiclePage(cl, content, listContent, control.getAddVehicleControl());
        content.add(addVehiclePage, listContent[6]);

        add(content);
    }
}
