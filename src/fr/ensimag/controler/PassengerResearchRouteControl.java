package fr.ensimag.controler;

import fr.ensimag.model.Model;
import fr.ensimag.model.User;
import fr.ensimag.view.PassengerCreateRouteFromValues;

import java.sql.SQLException;

public class PassengerResearchRouteControl {
    private Model model;

    public PassengerResearchRouteControl (Model model) {
        this.model = model;
    }


    public Response checkResearchResults(int nbOfSections){
        if(nbOfSections == 0){
            return new Response(false, "No routes corresponding to your research criterias were found");
        }
        else{
            return new Response(true, "Research results ready !");
        }
    }



    public Response checkPassengerRoute(PassengerCreateRouteFromValues values, boolean errMultibleTripsChosen, boolean errOnSelectedObjects){

        if(errMultibleTripsChosen){
            return new Response(false, "You are not allowed to choose sections from more than one trip");
        }

        if(errOnSelectedObjects){
            return new Response(false, "You cannot choose something other than a section or a section group that is belonging to a single trip");
        }

        try{
            InterventionOnDB iPassengerTrip = new InterventionOnDB();
            boolean passengerTrip = iPassengerTrip.addPassengerTrip(this.model, values.getDepartureDate(),
                    values.getDepartureCity(), values.getArrivalCity(),
                    values.getDepartureLongitude(), values.getDepartureLatitude(),
                    values.getArrivalLongitude(), values.getArrivalLatitude(),
                    values.getPassengerListOfSections());
            iPassengerTrip.closeTransaction();
            return new Response(passengerTrip, "Your trip has been well added!");

        } catch (SQLException s){
            s.printStackTrace();

        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("Coordonnées incorrectes");
        }

        return new Response(false, "An error occured in adding your trip ...");    }
}
