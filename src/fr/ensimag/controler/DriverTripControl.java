package fr.ensimag.controler;

import fr.ensimag.model.Model;

import java.sql.SQLException;

public class DriverTripControl {
    private Model model;
    public DriverTripControl(Model model) {
        super();
        this.model = model;
    }

    public Response validateTroncon(int idTronconToValidate) {
        Response response = new Response(true, new String("Section validated"));

        return response;
    }

    public Response validateTrip(int idTrip) {
        Response response = new Response(false, new String("Trip was not validated"));
        InterventionOnDB ioDB = null;

        try {
            ioDB = new InterventionOnDB();
            response = ioDB.validateTripDriver(this.model, idTrip);
            ioDB.closeTransaction();
        } catch (SQLException e) {
            System.out.println("Erreur de validation : fin de voyage");
            e.printStackTrace();
        } finally {
            return response;
        }
    }
}