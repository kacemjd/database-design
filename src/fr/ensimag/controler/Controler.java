package fr.ensimag.controler;

import fr.ensimag.model.Model;

public class Controler {
    private Model model;
    private RegistrationControl checkRegistration;
    private LoginControl checkLogin;
    private RoleChoiceControl roleChoiceControl;
    private AddVehicleControl addVehicleControl;
    private UserPageControl userPageControl;
    private DriverBuildTripControl driverBuildTripControl;
    private PassengerTripControl passengerTripControl;
    private DriverTripControl driverTripControl;
    private PassengerResearchRouteControl passengerResearchRouteControl;

    public Controler(Model model){
        this.model = model;
        checkRegistration = new RegistrationControl();
        checkLogin = new LoginControl(model);
        roleChoiceControl = new RoleChoiceControl(model);
        addVehicleControl = new AddVehicleControl(model);
        userPageControl = new UserPageControl(model);
        driverBuildTripControl = new DriverBuildTripControl(model);
        passengerTripControl = new PassengerTripControl(model);
        driverTripControl = new DriverTripControl(model);
        passengerResearchRouteControl = new PassengerResearchRouteControl(model);
    }

    public RegistrationControl getCheckRegistration() {
        return checkRegistration;
    }

    public LoginControl getCheckLogin() {
        return checkLogin;
    }

    public RoleChoiceControl getRoleChoiceControl() {
        return roleChoiceControl;
    }

    public AddVehicleControl getAddVehicleControl() {
        return addVehicleControl;
    }

    public UserPageControl getUserPageControl() {
        return userPageControl;
    }

    public DriverBuildTripControl getDriverBuildTripControl() {
        return driverBuildTripControl;
    }

    public PassengerTripControl getPassengerTripControl() {
        return passengerTripControl;
    }

    public DriverTripControl getDriverTripControl() {
        return driverTripControl;
    }

    public PassengerResearchRouteControl getPassengerResearchRouteControl() {
        return passengerResearchRouteControl;
    }
}
