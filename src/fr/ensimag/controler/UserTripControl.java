package fr.ensimag.controler;

import fr.ensimag.model.FabriqueAbstraite;
import fr.ensimag.model.Model;
import fr.ensimag.model.User;
import fr.ensimag.view.DriverBuildTripFormValues;
import fr.ensimag.controler.InterventionOnDB;
import java.sql.*;

public class UserTripControl {
    protected Model model;

    public UserTripControl(Model model) {
        this.model = model;
    }

    public Response validateTroncon(int idTronconToValidate) throws InstantiationException {
        throw new InstantiationException("Appel à validateTroncon via la classe mere");
    }

    //TODO
//    public User getUserTrips() {
//        return model.getUserTrips();
//    }
}
