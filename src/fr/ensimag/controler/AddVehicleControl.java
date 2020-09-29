package fr.ensimag.controler;

import fr.ensimag.model.Model;
import fr.ensimag.view.VehicleFormValues;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddVehicleControl {
    Model model;

    public AddVehicleControl(Model model) {
        this.model = model;
    }

    public Response checkAndAddVehicle(VehicleFormValues vehicleValues) throws SQLException {

        if(!checkRegistrationFormat(vehicleValues.getRegistration())) return new Response(false, "Bad registration format");

        if(!checkModelFormat(vehicleValues.getModel())) return new Response(false, "Bad model format");

        if(!checkTaxPowerFormat(vehicleValues.getTaxPower())) return new Response(false, "Bad tax power format");

        InterventionOnDB iCar = new InterventionOnDB();
        boolean insert = iCar.insertNewCar(this. model, vehicleValues.getRegistration(),
                vehicleValues.getBrand(), vehicleValues.getModel(), vehicleValues.getEnergy(),
                Integer.parseInt(vehicleValues.getTaxPower()), vehicleValues.getNumberOfSeats());
        iCar.closeTransaction();
        if (insert) return new Response(insert, "Vehicle well added !");
        else return new Response(insert, "Error in adding new vehicule...");

    }

    private boolean checkRegistrationFormat(String registration){
        //Format accepté : ab-123-ab
        Pattern regex = Pattern.compile("[A-Z]{2}-[0-9]{3}-[A-Z]{2}");
        Matcher matcher = regex.matcher(registration);

        if(!matcher.find()) return false;

        return true;
    }

    private boolean checkModelFormat(String model){
        if(model.length() < 4) return false;

        //Format: interdit aux caractères spéciaux
        Pattern regex = Pattern.compile("[!@#$%^&*(),.?\":{}|<>]");
        Matcher matcher = regex.matcher(model);

        if(matcher.find()) return false;

        return true;
    }

    private boolean checkTaxPowerFormat(String taxPower){
        //Format: 2 ou 3 chiffres
        Pattern regex = Pattern.compile("^[0-9]{2,3}$");
        Matcher matcher = regex.matcher(taxPower);

        if(!matcher.find()) return false;

        return true;
    }
}
