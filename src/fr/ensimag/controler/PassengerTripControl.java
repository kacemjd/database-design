package fr.ensimag.controler;

import fr.ensimag.model.*;

import java.sql.SQLException;

public class PassengerTripControl {
    private Model model;
    public PassengerTripControl(Model model) {
        super();
        this.model = model;
    }

    public Response validateTroncon(int idTronconToValidate) {
        // System.out.println("id to validate : " + idTronconToValidate);
        InterventionOnDB ioDB;
        Response response = new Response(false, "Section was not validated");

        try {
            ioDB = new InterventionOnDB();
            ioDB.validateSectionPassenger(idTronconToValidate);
            ioDB.closeTransaction();
            response = new Response(true, "Section well validated");
            return response;
        } catch (SQLException e) {
            System.out.println("Erreur de validation : fin de tronçon");
            e.printStackTrace();
        } finally {
            return response;
        }
    }

    public Response processPayment(int idTrip) throws SQLException {
//        System.out.println("l'id du trajet a payer est: " + idTrip);
        InterventionOnDB ioDB;
        Response response = new Response(false, "Payment unsuccessful");

        response = hasEnoughMoney(getPrice(idTrip));
        if(!response.getStatusCode()) return response;

        try {
            ioDB = new InterventionOnDB();
            ioDB.validateTripPassenger(this.model, idTrip);
            ioDB.closeTransaction();
            response = new Response(true, "Payment validated");
        } catch (SQLException e) {
            System.out.println("Erreur de validation : fin de voyage");
            e.printStackTrace();
        } finally {
            return response;
        }
    }

    private Response hasEnoughMoney(double price) {
        double solde = model.getUser().getSolde();

        if(solde < price)
            return new Response(false, "You don't have enough money, please refill");
        else
            return new Response(true, "You have enough money");
    }

    public double getPrice(int idTrip) throws SQLException {
        return model.getPosInTripsFromParcoursID(idTrip).costCalculation();
    }

    public void updateProfile(){
        //System.out.println("update solde du user en local (celui en bdd est bien deja update)");
        // trajet validé => update solde (et autres si besoin?) dans le fabrique user local (pas en bdd)
        try {
            InterventionOnDB iSolde = new InterventionOnDB();
            int solde = iSolde.getSolde(model.getUserKey());
            iSolde.closeTransaction();
            this.model.getUser().setSolde(solde);
        }
        catch (SQLException s) {
            s.printStackTrace();
            System.out.println("Impossible d'update le solde en local");
        }
    }

    public boolean isTripDone(int tripID) {
        Trip trip = model.getPosInTripsFromParcoursID(tripID);
        for (Section s : trip.getTroncons()) {
            if (!isSectionDone(tripID, s.getSectionID()))
                return false;
        }

        return true;
    }

    public boolean isSectionDone(int tripID, int sectionID) {
        boolean answer;
        try {
            InterventionOnDB ioDB = new InterventionOnDB();
            answer = ioDB.isSectionDone(tripID, sectionID);
            ioDB.closeTransaction();
        } catch (SQLException e) {
            System.out.println("Erreur en testant si la section est validée");
            e.printStackTrace();
            answer = false;
        }

        return answer;
    }
}
