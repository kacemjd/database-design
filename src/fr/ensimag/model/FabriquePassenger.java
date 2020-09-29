package fr.ensimag.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class FabriquePassenger extends FabriqueAbstraite {
    public User createUser(String email) throws SQLException {
        Connection c = super.connect();
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("select * from Utilisateur where idemail='" + email + "'");
        rs.next();
        User u = new Driver(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                rs.getString(5), rs.getInt(6));
        rs.close();
        stmt.close();
        c.close();
        return u;
    }

    public LinkedList<Trip> createTrip(String email) throws SQLException {
        LinkedList<Trip> trips = new LinkedList<>();

        LinkedList<Section> troncons = new LinkedList<>();

        Connection c = super.connect();
        Statement stmt = c.createStatement();
        Statement stmtSections = c.createStatement();
        ResultSet rs = stmt.executeQuery("select * from PARCOURS where IDEMAIL='" + email +"'");
        ResultSet rsTroncon = null;
        while (rs.next()) {
            rsTroncon = stmtSections.executeQuery("select T.idtroncon, idtrajet, villedepart, villearrivee," +
                                                    " longitudedepart, latitudedepart, longitudearrivee, latitudearrivee," +
                                                    "distanceparcourue, tempsdeparcours, tempsattente, lieudepart, lieuarrivee " +
                                                    "from TRONCON T, ESTCOMPOSEDE ECD " +
                                                    "where T.idtroncon = ECD.idtroncon and ECD.idparcours=" + rs.getInt(1));
            while (rsTroncon.next()) {
                troncons.add(new Section(rsTroncon.getInt(1), rsTroncon.getInt(2), rsTroncon.getString(3),
                        rsTroncon.getString(4), rsTroncon.getFloat(5), rsTroncon.getFloat(6), rsTroncon.getFloat(7),
                        rsTroncon.getFloat(8), rsTroncon.getInt(9), rsTroncon.getInt(10), rsTroncon.getInt(11),
                        rsTroncon.getString(12), rsTroncon.getString(13)));
            }
            if (troncons.size() > 0) {
                trips.add(new TripPassenger(rs.getInt(1), rs.getString(2), rs.getString(3), troncons));
            }
            troncons = new LinkedList<>();
        }
        rsTroncon.close();
        stmtSections.close();
        rs.close();
        stmt.close();
        c.close();
        return trips;
    }

    public LinkedList<TripDriver> fetchAllTrips() throws SQLException {
        LinkedList<TripDriver> listOfAllTrips = new LinkedList<TripDriver>();
        Connection c = super.connect();
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM TRAJET");
//        int resSize = rs.getFetchSize();
//        for(int i = 0; i < resSize; i++){
//            rs.next();
//            String element = rs.getString(1);
//            listOfDepartureCities.add(element);
//        }
        while(rs.next()){
            TripDriver element = new TripDriver(rs.getInt(1), rs.getString(2), rs.getString(6),
                    rs.getInt(3), rs.getInt(4), rs.getString(5), new LinkedList<Section>());
            listOfAllTrips.add(element);
        }
        rs.close();
        stmt.close();
        c.close();
        return listOfAllTrips;
    }

    public LinkedList<Section> fetchAllSections() throws SQLException {
        LinkedList<Section> listOfAllSections = new LinkedList<Section>();
        Connection c = super.connect();
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM TRONCON");
//        int resSize = rs.getFetchSize();
//        for(int i = 0; i < resSize; i++){
//            rs.next();
//            String element = rs.getString(1);
//            listOfDepartureCities.add(element);
//        }
        while(rs.next()){
            Section element = new Section(rs.getInt(1), rs.getInt(2),
                    rs.getString(3), rs.getString(4),
                    rs.getFloat(5), rs.getFloat(6), rs.getFloat(7), rs.getFloat(8),
                    rs.getInt(9), rs.getInt(10), rs.getInt(11),
                    rs.getString(12), rs.getString(13)
                    );
            listOfAllSections.add(element);
        }
        rs.close();
        stmt.close();
        c.close();
        return listOfAllSections;
    }

    public LinkedList<String> fetchAllDepartureCities() throws SQLException {
        LinkedList<String> listOfDepartureCities = new LinkedList<String>();

        Connection c = super.connect();
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT DISTINCT VilleDepart FROM TRONCON");
//        int resSize = rs.getFetchSize();
//        for(int i = 0; i < resSize; i++){
//            rs.next();
//            String element = rs.getString(1);
//            listOfDepartureCities.add(element);
//        }
        while(rs.next()){

            String element = rs.getString(1);
            listOfDepartureCities.add(element);
        }
        rs.close();
        stmt.close();
        c.close();
        return listOfDepartureCities;
    }

    public LinkedList<String> fetchAllArrivalCities() throws SQLException {
        LinkedList<String> listOfArrivalCities = new LinkedList<String>();
        Connection c = super.connect();
        Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT DISTINCT VilleArrivee FROM TRONCON");
//        int resSize = rs.getFetchSize();
        while(rs.next()){
//            rs.next();
            String element = rs.getString(1);
            listOfArrivalCities.add(element);
        }
        rs.close();
        stmt.close();
        c.close();
        return listOfArrivalCities;
    }

    public LinkedList<String> fetchSelectedDepartureLocations(String villeDepart) throws SQLException {
        LinkedList<String> listOfDepartureLocations = new LinkedList<String>();
        Connection c = super.connect();
        Statement stmt = c.createStatement();
        try{
            ResultSet rs = stmt.executeQuery("select distinct LieuDepart FROM TRONCON WHERE VilleDepart = '"+ villeDepart+ "'");
//            int resSize = rs.getFetchSize();
            while(rs.next()){
//                rs.next();
                String element = rs.getString(1);
                listOfDepartureLocations.add(element);
            }
            rs.close();
        }catch(SQLException s){
            System.out.println("No departure locations found");
            s.printStackTrace();
        }
        stmt.close();
        c.close();
        return listOfDepartureLocations;
    }

    public LinkedList<String> fetchSelectedArrivalLocations(String villeArrivee) throws SQLException {
        LinkedList<String> listOfArrivalLocations = new LinkedList<String>();
        Connection c = super.connect();
        Statement stmt = c.createStatement();
        try{
            ResultSet rs = stmt.executeQuery("select distinct LieuArrivee FROM TRONCON WHERE VilleArrivee = '"+ villeArrivee + "'");
//            int resSize = rs.getFetchSize();
            while(rs.next()){
//                rs.next();
                String element = rs.getString(1);
                listOfArrivalLocations.add(element);
            }
            rs.close();
        } catch(SQLException s){
            System.out.println("No arrival locations found");
            s.printStackTrace();
        }
        stmt.close();
        c.close();
        return listOfArrivalLocations;
    }

}
