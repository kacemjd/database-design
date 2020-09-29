package fr.ensimag.model;

import java.util.LinkedList;

public class TripDriver extends Trip {
    private int startPlaceNb;
    private int remainingSectionNb;
    private String idImmatriculation;


    public TripDriver(int tripID, String tripDate, String idEmail, int startPlaceNb,
                      int remainingSectionNb, String idImmatriculation, LinkedList<Section> sections) {
        super(tripID, tripDate, idEmail, sections);
        this.startPlaceNb = startPlaceNb;
        this.remainingSectionNb = remainingSectionNb;
        this.idImmatriculation = idImmatriculation;
    }

    @Override
    public String toString() {
        return "TripDriver{" +
                "startPlaceNb=" + startPlaceNb +
                ", remainingSectionNb=" + remainingSectionNb +
                ", idImmatriculation='" + idImmatriculation + '\'' +
                '}';
    }
}
