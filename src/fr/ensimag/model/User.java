package fr.ensimag.model;

import java.util.Iterator;
import java.util.LinkedList;

public abstract class User {
    private String email;
    private String firstName;
    private String name;
    private String city;
    private String password;

    private int solde;

    public String getEmail() { return email; }

    public String getFirstName() { return firstName; }

    public String getName() { return name; }

    public String getCity() { return city; }

    public String getPassword() { return password; }

    public int getSolde() { return solde; }

    public void setSolde(int solde) { this.solde = solde; }

    public User(String email, String firstName, String name, String city, String password, int solde)
    {
        this.email = email;
        this.firstName = firstName;
        this.name = name;
        this.city = city;
        this.password = password;
        this.solde = solde;
    }
}

