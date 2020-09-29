package fr.ensimag.model;

import java.sql.SQLException;
import java.util.LinkedList;

public abstract class Trip {
    private int tripID;
    private String tripDate;
    private String idEmail;
    private LinkedList<Section> sections;
    private int nbTotalSections;
    private int nbValidatedSections;

    public Trip(int tripID, String tripDate, String idEmail, LinkedList<Section> sections) {
        this.tripID = tripID;
        this.tripDate = tripDate;
        this.idEmail = idEmail;
        this.sections = sections;
        this.nbTotalSections = sections.size();
        this.nbValidatedSections = 0;
        // System.out.println("il y a "+ nbTotalSections);
    }

    public String getTripDate() {
        return tripDate;
    }

    public LinkedList<Section> getTroncons() {
        return sections;
    }

    public Section getSectionN(int index) {
        return sections.get(index);
    }

    public int getTripID() { return this.tripID; }

    public int getNbTotalSections() {
        return nbTotalSections;
    }

    public int getNbValidatedSections() {
        return nbValidatedSections;
    }

    public void incrNbValidatedSections(){
        this.nbValidatedSections++;
    }

    public void decrNbValidatedSections(){
        this.nbValidatedSections--;
    }

    public boolean isTripTerminated(){
        if(nbTotalSections == nbValidatedSections) return true;
        return false;
    }

    public Section getSectionFromID(int sectionID) {
        for (Section s : this.sections) {
            if(s.getSectionID() == sectionID)
                return s;
        }
        System.out.println("Problème dans la recherche de section : le troncon de que vous souhaitez n'est pas " +
                "instancié en mémoire. ");
        return null;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "tripID=" + tripID +
                ", tripDate='" + tripDate + '\'' +
                ", idEmail='" + idEmail + '\'' +
                ", sections=" + sections +
                '}';
    }

    public String getIdEmail() {
        return idEmail;
    }

    public double costCalculation() throws SQLException {
        double cost = 0;
        for (Section s: getTroncons()) {
            cost += s.costCalculation();
        }
        return cost;

    }

}

