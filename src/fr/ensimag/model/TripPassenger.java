package fr.ensimag.model;

import java.util.LinkedList;
import java.sql.SQLException;

import fr.ensimag.controler.InterventionOnDB;

public class TripPassenger extends Trip {

    public TripPassenger(int tripID, String tripDate, String idEmail, LinkedList<Section> sections) {
        super(tripID, tripDate, idEmail, sections);
    }
}