package fr.ensimag.controler;

import fr.ensimag.view.RegisterFormValues;

import java.sql.SQLException;


public class RegistrationControl {

    public Response checkRegistrationValues(RegisterFormValues formValues) {
        String firstName = formValues.getFirstName();
        String lastName = formValues.getLastName();
        String email = formValues.getEmail();
        String city = formValues.getCity();
        String pwd = formValues.getPassword();
        String pwdConf = formValues.getPasswordConfirm();

        Response res;

        res=checkFirstNameLastName(firstName, lastName);
        if(!res.getStatusCode()) return res;

        try {
            res = checkEmail(email);
            System.out.println("Test présence email success");
            if(!res.getStatusCode()) return res;
        } catch (SQLException s) {
            System.out.println("Test présence email failed");
            s.printStackTrace();
        }

        res = checkPassword(pwd, pwdConf);
        if(!res.getStatusCode()) return res;

        try {
            InterventionOnDB i = new InterventionOnDB();
            i.insertNewUser(email, lastName, firstName, city, pwd, 1);
            i.closeTransaction();
        } catch (SQLException s) {
            s.printStackTrace();
            return new Response(false, "User not created");
        }

        return new Response(true, "User well created");
    }

    public Response checkFirstNameLastName(String fname, String lname){
        Response res;

        res = checkFirstNameFormat(fname);
        if(!res.getStatusCode()) return res;

        res = checkLastNameFormat(lname);
        if(!res.getStatusCode()) return res;

        return new Response(true, "Firstname and Lastname OK");
    }

    private Response checkFirstNameFormat(String fname){
        //TODO: Implémenter test présence carac spéciaux avec expression regulière
        boolean isOK = fname.length() > 3;

        if(!isOK) return new Response(false, "Check your firstname format");

        return new Response(true, "Firstname format OK");
    }

    private Response checkLastNameFormat(String lname){
        //TODO: Implémenter test présence carac spéciaux avec expression regulière
        boolean isOK = lname.length() > 3;

        if(!isOK) return new Response(false, "Check your lastname format");

        return new Response(true, "Lastname format OK");
    }

    public Response checkEmail(String email) throws SQLException {
        Response res;

        res= checkEmailFormat(email);
        if(!res.getStatusCode()) return new Response(false, "Check your email format");

        res=emailIsUsed(email);
        if(res.getStatusCode()) return new Response(false, "This email is already used");

        return new Response(true, "Email OK");
    }
    private Response checkEmailFormat(String email){
        //TODO: voir pour implémenter un package vérificateur
        boolean condition;

        condition = email.contains("@") && email.contains(".") && email.length() > 5;

        if(!condition) return new Response(false, "Check your email format");

        return new Response(true, "Email format OK");
    }
    private Response emailIsUsed(String email) throws SQLException {
        //TODO: test bdd si email déjà utilisée
        InterventionOnDB i = new InterventionOnDB();
        boolean isPresent = i.isPresent("select * from Utilisateur where idemail=", email,
                1);
        i.closeTransaction();
        if (isPresent) {
            return new Response(isPresent, "Email used");
        }
        return new Response(isPresent, "Email not used");
    }

    private Response checkPassword(String pwd, String pwdConf){
        boolean condition;

        condition = pwd.equals(pwdConf);
        if(!condition) return new Response(false, "The password confirmation doesn't match the password");

        Response res = checkPasswordStrength(pwd);
        if(!res.getStatusCode()) return res;

        return new Response(true, "Password OK");
    }
    private Response checkPasswordStrength(String pwd) {
        //TODO: implémenter package de vérification de la force du mdp?

        boolean condition = pwd.length() > 7;
        if (!condition) return new Response(false, "The password must be at least 8 char long");

        return new Response(true, "Password strength OK");
    }

}
