package fr.ensimag.controler;

import fr.ensimag.model.Model;
import fr.ensimag.model.User;
import fr.ensimag.view.DriverBuildTripFormValues;

import java.sql.SQLException;

public class DriverBuildTripControl {
    private Model model;

    public DriverBuildTripControl (Model model) {
        this.model = model;
    }

    public User getUserInfo() {
        return model.getUser();
    }

    public Response checkAndAddDriverTrip(DriverBuildTripFormValues formValues){
        //TODO implement control of fields


        try {
            InterventionOnDB iTrip = new InterventionOnDB();
            boolean trip = iTrip.addDriverTrip(this.model, formValues.getDate(), formValues.getVehicle(), formValues.getNbSeats(),
                    formValues.getDepartureCity(), formValues.getStartLatitude(), formValues.getStartLongitude(), formValues.getWaitingTime(),
                    formValues.getArrivalCity(), formValues.getArrivalLatitude(), formValues.getArrivalLongitude(), formValues.getStartLieuDit(),
                    formValues.getEndlieuDit(), formValues.getStops());
            iTrip.closeTransaction();
            return new Response(trip, "Your trip has been well added!");
        } catch (SQLException s) {
            s.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("Coordonnées incorrectes");
        }
        return new Response(false, "An error occured in adding your trip ...");
    }
}
