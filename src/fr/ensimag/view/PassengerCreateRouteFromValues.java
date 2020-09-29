package fr.ensimag.view;

import fr.ensimag.model.Section;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.LinkedList;

public class PassengerCreateRouteFromValues {

    private Timestamp departureDate;
    private String departureCity;
    private float departureLongitude;
    private float departureLatitude;

    private String arrivalCity;
    private float arrivalLongitude;
    private float arrivalLatitude;

    private LinkedList<Section> passengerListOfSections;

    public PassengerCreateRouteFromValues(
            Timestamp departureDate,
            String departureCity, String arrivalCity, float departureLongitude, float departureLatitude, float arrivalLongitude, float arrivalLatitude,
            LinkedList<Section> passengerListOfSections) {
        this.departureDate = departureDate;
        this.departureCity = departureCity;
        this.departureLongitude = departureLongitude;
        this.departureLatitude = departureLatitude;
        this.arrivalCity = arrivalCity;
        this.arrivalLongitude = arrivalLongitude;
        this.arrivalLatitude = arrivalLatitude;
        this.passengerListOfSections = passengerListOfSections;
    }

    public Timestamp getDepartureDate() {
        return departureDate;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public float getDepartureLongitude() {
        return departureLongitude;
    }

    public float getDepartureLatitude() {
        return departureLatitude;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public float getArrivalLongitude() {
        return arrivalLongitude;
    }

    public float getArrivalLatitude() {
        return arrivalLatitude;
    }

    public Iterator<Section> getPassengerListOfSectionsIterator(){
        return this.passengerListOfSections.iterator();
    }

    public void addPassengerSection(Section section){
        this.passengerListOfSections.add(section);
    }

    public LinkedList<Section> getPassengerListOfSections() {
        return passengerListOfSections;
    }
}
