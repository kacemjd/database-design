package fr.ensimag.model;

import java.util.LinkedList;

public class Model extends abstractModel {
    public FabriqueAbstraite fabrique;

    private User user;
    private String UserKey;

    private LinkedList<Trip> trips;

    public Model() {
        super();
    }

    public String getUserKey() { return UserKey; }
    public void setUserKey(String userKey) { UserKey = userKey; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public void setTrips (LinkedList<Trip> trips) {
        this.trips = trips;
    }

    public LinkedList<Trip> getTrips() {
        return trips;
    }

    public Trip getPosInTripsFromParcoursID(int parcoursID) {
        for (Trip t: this.trips) {
            if(t.getTripID() == parcoursID) return t;
        }
        System.out.println("Problème dans la recherche de parcours : le troncon de que vous souhaitez valider n'est pas " +
                "instancié en mémoire. ");
        return null;
    }
}
