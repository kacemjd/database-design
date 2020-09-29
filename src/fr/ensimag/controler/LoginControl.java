package fr.ensimag.controler;

import fr.ensimag.model.Model;
import java.sql.SQLException;

public class LoginControl {
    private Model model;

    public LoginControl (Model model) {
        this.model = model;
    }

    public Response checkLoginValues(String email, String pwd) throws SQLException {
        Response res;

        res = checkEmailFormat(email);
        if(!res.getStatusCode()) return res;

        res = checkPasswordStrength(pwd);
        if(!res.getStatusCode()) return res;

        res = userExists(email, pwd);
        if(!res.getStatusCode()) return res;

        this.model.setUserKey(email);

        return new Response(true,"Login OK");
    }

    private Response checkEmailFormat(String email){
        //TODO: voir pour implémenter un package vérificateur
        boolean condition;

        condition = email.contains("@") && email.contains(".") && email.length() > 5;

        if(!condition) return new Response(false, "Check your email format");

        return new Response(true, "Email format OK");
    }

    private Response checkPasswordStrength(String pwd) {
        //TODO: implémenter package de vérification de la force du mdp?

        boolean condition = pwd.length() > 7;
        if (!condition) return new Response(false, "The password must be at least 8 char long");

        return new Response(true, "Password strength OK");
    }

    private Response userExists (String email, String pwd) throws SQLException {
        InterventionOnDB i = new InterventionOnDB();
        boolean isPresent = i.isPresent("select * from Utilisateur where idemail=", email, 0);
        i.closeTransaction();
        if (isPresent) {
            InterventionOnDB iPwd = new InterventionOnDB();
            boolean passOK = iPwd.checkPassword(email, pwd);
            iPwd.closeTransaction();
            if (passOK) return new Response(passOK, "User exists and passwords matches.");
            return new Response(passOK, "User exists but passwords doesn't match ...");
        }
        else return new Response(isPresent, "This user doesn't exist!");
    }
}
