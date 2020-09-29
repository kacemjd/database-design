package fr.ensimag.model;

import fr.ensimag.controler.InterventionOnDB;

import java.sql.SQLException;

public class Section {
    private int sectionID;
    private int tripID;
    private String startCity;
    private String endCity;
    private Stop startCoord; // TODO Init
    private Stop endCoord; // TODO Init

    private int distance;
    private int sectionTime;
    private int waitingTime;

    private  String startLocation;
    private  String endLocation;



    public Section (int sectionID, int tripID, String startCity, String endCity, int distance, int sectionTime,
                    int waitingTime)
    {
        this.sectionID = sectionID;
        this.tripID = tripID;
        this.startCity = startCity;
        this.endCity = endCity;
        this.distance = distance;
        this.sectionTime = sectionTime;
        this.waitingTime = waitingTime;
    }



    public Section (int sectionID, int tripID,
                    String startCity, String endCity,
                    float startLongitude, float startLatitude,
                    float endLongitude, float endLatitude,
                    int distance, int sectionTime, int waitingTime,
                    String startLocation, String endLocation)
    {
        this.sectionID = sectionID;
        this.tripID = tripID;
        this.startCity = startCity;
        this.startCoord = new Stop(startLatitude, startLongitude);
        this.endCoord = new Stop(endLatitude, endLongitude);
        this.endCity = endCity;
        this.distance = distance;
        this.sectionTime = sectionTime;
        this.waitingTime = waitingTime;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public int getSectionID() {
        return sectionID;
    }

    public int getTripID() {
        return tripID;
    }

    public String getStartCity() {
        return startCity;
    }

    public String getEndCity() {
        return endCity;
    }

    public Stop getStartCoord() {
        return startCoord;
    }

    public float getStartLongitude(){
        return startCoord.getLongitude();
    }

    public float getStartLatitude(){
        return startCoord.getLatitude();
    }

    public float getEndLongitude(){
        return endCoord.getLongitude();
    }

    public float getEndLatitude(){
        return endCoord.getLatitude();
    }

    public Stop getEndCoord() {
        return endCoord;
    }

    public int getDistance() {
        return distance;
    }

    public int getSectionTime() {
        return sectionTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public double costCalculation() throws SQLException {
        InterventionOnDB iCost = new InterventionOnDB();
        double cost = iCost.costCalculation(this.sectionID);
        iCost.closeTransaction();
        return cost;
    }
}
