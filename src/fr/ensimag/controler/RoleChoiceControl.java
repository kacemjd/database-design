package fr.ensimag.controler;

import fr.ensimag.model.FabriqueDriver;
import fr.ensimag.model.FabriquePassenger;
import fr.ensimag.model.Model;

import java.sql.SQLException;

public class RoleChoiceControl {
    private Model model;

    public RoleChoiceControl(Model model) {
        this.model = model;
    }

    public Response passengerClick(){
        try {
            this.model.fabrique = new FabriquePassenger();
            this.model.setUser(model.fabrique.createUser(model.getUserKey()));
            return new Response(true, "OK");
        } catch (SQLException s) {
            s.printStackTrace();
            return new Response(false, "Le passager n'a pas correctement été instancié en mémoire.");
        }
    }

    public Response driverClick(){
        try {
            this.model.fabrique = new FabriqueDriver();
            this.model.setUser(model.fabrique.createUser(model.getUserKey()));
            return new Response(true, "OK");
        } catch (SQLException s) {
            s.printStackTrace();
            return new Response(false, "Le conducteur n'a pas correctement été instancié en mémoire.");
        }
    }
}
