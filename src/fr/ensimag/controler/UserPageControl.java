package fr.ensimag.controler;

import fr.ensimag.model.Model;
import fr.ensimag.model.User;
import fr.ensimag.view.DriverTripsPage;
import fr.ensimag.view.PassengerTripsPage;

import java.lang.annotation.Repeatable;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserPageControl {
    Model model;

    public UserPageControl (Model model) {
        this.model = model;
    }

    public void setDriverTripsPage(DriverTripsPage driverTripsPage){
        try {
            this.model.setTrips(model.fabrique.createTrip(model.getUserKey()));
            driverTripsPage.setTrips(model.getTrips());
        } catch (SQLException s) {
            s.printStackTrace();
            System.out.println("Problème dans l'instanciation des trajets en mémoire");
        } catch (NullPointerException n) {
            n.printStackTrace();
            System.out.println("Pas de trajet prévu");
        }
    }

    public void setPassengerTripsPage(PassengerTripsPage passengerTripsPage) {
        try {
            this.model.setTrips(model.fabrique.createTrip(model.getUserKey()));
            passengerTripsPage.setTrips(model.getTrips());
        } catch (NullPointerException n) {
            n.printStackTrace();
            System.out.println("Pas de voyage prévu");
        } catch (SQLException s) {
            s.printStackTrace();
            System.out.println("Problème dans l'instanciation des parcours en mémoire");
        }
    }

    public User getUserInfo() {
        return model.getUser();
    }

    public Response updateWallet(int valueToRefill) {
        if (valueToRefill == 0)
            return new Response(false, "Please, select an amount to refill.");
        try {
            InterventionOnDB iMoney = new InterventionOnDB();
            iMoney.addMoney(this.model, valueToRefill);
            iMoney.closeTransaction();
            model.getUser().setSolde(model.getUser().getSolde() + valueToRefill);
            return new Response(true,  "$" + model.getUser().getSolde());
        } catch (SQLException s) {
            s.printStackTrace();
            return new Response(false, "Failed to refill your wallet...");
        }
    }

    public Response updateProfile(User currentUserInfos, String newFirstName, String newLastName, String newCity, String oldPwd, String newPwd, String newPwdConf){
        Response res;

        if(!newFirstName.equals(currentUserInfos.getFirstName())){
            res = checkName(newFirstName);
            if(!res.getStatusCode()) return res;
        }

        if(!newLastName.equals(currentUserInfos.getName())){
            res = checkName(newLastName);
            if(!res.getStatusCode()) return res;
        }

        if(!newCity.equals(currentUserInfos.getCity())){
            res = checkCity(newCity);
            if(!res.getStatusCode()) return res;
        }

        if(oldPwd.length() > 0 || newPwd.length() > 0 || newPwdConf.length() > 0){
            res = checkPwd(oldPwd, newPwd, newPwdConf);
            if(!res.getStatusCode()) return res;
        }

        //TODO: update bdd with the params received

        return new Response(true, "User well updated");
    }

    private Response checkName(String nameToCheck){
        if(!checkFormat(nameToCheck)) return new Response(false, "Please check your firstname and lastname format");

        return new Response(true, "OK");
    }

    private boolean checkFormat(String s){
        if(s.length() < 2) return false;

        Pattern regex = Pattern.compile("[!@#$%^&*(),.?\":{}|<>0-9]");
        Matcher matcher = regex.matcher(s);

        if(matcher.find()) return false;

        return true;
    }

    private Response checkCity(String cityToCheck){
        if(!checkFormat(cityToCheck)) return new Response(false, "Please check your city format");

        return new Response(true, "OK");
    }

    private Response checkPwd(String oldPwd, String newPwd, String newPwdConf){
        System.out.println(model.getUser().getPassword());

        if(!oldPwd.equals(model.getUser().getPassword())) return new Response(false, "Old password doesn't match!");

        if(newPwd.length() < 8) return new Response(false, "New password must be at least 8 char long");

        if(!newPwd.equals(newPwdConf)) return new Response(false, "The new passwword doesn't match with the confirmation!");

        return new Response(true, "Password OK");
    }

}
