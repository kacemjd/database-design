package fr.ensimag.view;

import fr.ensimag.model.Stop;

import java.util.LinkedList;

public class DriverBuildTripFormValues {
    private String date;
    private String vehicle;
    private int nbSeats;
    private String departureCity;
    private int waitingTime;
    private float startLatitude;
    private float startLongitude;
    private String arrivalCity;
    private float arrivalLatitude;
    private float arrivalLongitude;
    private String startLieuDit;
    private String endlieuDit;
    private LinkedList<Stop> stops;

    public LinkedList<Stop> getStops() { return stops; }

    public DriverBuildTripFormValues(String date, String vehicle,
                                     String nbSeats, String departureCity, String startLieuDit, String waitingTime,
                                     String startLatitude, String startLongitude,
                                     String arrivalCity, String endlieuDit, String arrivalLatitude,
                                     String arrivalLongitude){
        this.date = date;
        this.vehicle = vehicle;
        this.nbSeats = Integer.parseInt(nbSeats);
        this.departureCity = departureCity;
        this.startLieuDit = startLieuDit;
        this.waitingTime = Integer.parseInt(waitingTime);
        this.startLatitude = Float.parseFloat(startLatitude);
        this.startLongitude = Float.parseFloat(startLongitude);
        this.arrivalCity = arrivalCity;
        this.endlieuDit = endlieuDit;
        this.arrivalLatitude = Float.parseFloat(arrivalLatitude);
        this.arrivalLongitude = Float.parseFloat(arrivalLongitude);
        this.stops = new LinkedList<>();
    }

    public void addStop(String cityName, String lieuDit, String latitude, String longitude, String waitingTime) {
        Stop s = new Stop(cityName, lieuDit, Float.parseFloat(latitude), Float.parseFloat(longitude), Integer.parseInt(waitingTime));
        this.stops.add(s);
    }

    public String getDate() { return date; }

    public String getVehicle() { return vehicle; }

    public int getNbSeats() { return nbSeats; }

    public String getDepartureCity() { return departureCity; }

    public int getWaitingTime() { return waitingTime; }

    public float getStartLatitude() { return startLatitude; }

    public float getStartLongitude() { return startLongitude; }

    public String getArrivalCity() { return arrivalCity; }

    public float getArrivalLatitude() { return arrivalLatitude; }

    public float getArrivalLongitude() { return arrivalLongitude; }

    public String getStartLieuDit() { return startLieuDit; }

    public String getEndlieuDit() { return endlieuDit; }

}
