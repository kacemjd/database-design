package fr.ensimag.controler;

import fr.ensimag.model.*;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.*;

public class InterventionOnDB {
    private Connection con;

    private static final String URL = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";
    private static final String username = "svenssop";
    private static final String password = "75BACTACBorrusque38";

    public InterventionOnDB() throws SQLException {
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        this.con = DriverManager.getConnection(URL, username, password);
        this.con.setAutoCommit(false);
    }

    public void closeTransaction() throws SQLException {
        this.con.commit();
        this.con.close();
    }

    public void doRollback() throws SQLException {
        this.con.rollback();
    }

    public boolean isPresent (String s, String item, int column) throws SQLException {
        boolean b = false;
        Statement stmt = this.con.createStatement();
        ResultSet rs = stmt.executeQuery(s+"'"+item+"'");

        if (rs.next()) { // rs.getString(column) == item
            b = true;
        }

        rs.close();
        stmt.close();
        return b;
    }

    public boolean checkPassword(String email, String pwd) throws SQLException {
        boolean passOK = false;
        Statement stmt = this.con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from Utilisateur where idemail='"+email+"'");
        rs.next();
        if (rs.getString(5).equals(pwd)) {
            passOK = true;
        }
        rs.close();
        stmt.close();
        return passOK;
    }

    public boolean insertNewUser (String email, String nom, String prenom, String ville_residence,
                                  String mdp, int solde) {
        try {
            Statement stmt = this.con.createStatement();
            ResultSet rset = stmt.executeQuery("insert into Utilisateur values ('" + email + "', '"
                    + nom + "', '" + prenom + "', '" + ville_residence + "', '" + mdp + "', "
                    + solde + ")");
            rset.close();
            stmt.close();
            return true;
        } catch (SQLException s) {
            System.out.println("Problème lors de l'insertion d'un nouvel utilisateur");
            s.printStackTrace();
            return false;
        }
    }

    public boolean insertNewCar(Model model, String idImmatriculation, String marque, String modele,
                                energyType energie, int puissance, int nbPlaces) {
        try {
            Statement stmt = this.con.createStatement();
            ResultSet rset1 = stmt.executeQuery("insert into Vehicule values ('" + idImmatriculation
                    + "', '" + marque + "', '" + modele + "', '" + energie.toString() + "', "
                    + puissance + ", " + nbPlaces + ")");
            rset1.close();
            ResultSet rset2 = stmt.executeQuery("insert into PEUTCONDUIRE values ('"
                    + model.getUserKey() + "', '" + idImmatriculation + "')");
            rset2.close();
            stmt.close();
            return true;
        } catch (SQLException s) {
            System.out.println("Problème lors de l'insertion d'un nouveau véhicule");
            s.printStackTrace();
            return false;
        }
    }

    public int getSolde(String email) throws SQLException {
        Statement stmt = this.con.createStatement();
        ResultSet rs = stmt.executeQuery("select solde from utilisateur where idemail = '"
                + email + "'");
        rs.next();
        int solde = rs.getInt(1);
        rs.close();
        stmt.close();
        return solde;
    }

    public boolean addMoney (Model model, int solde) {
        try {
            Statement stmt = this.con.createStatement();
            ResultSet rset = stmt.executeQuery("update Utilisateur set SOLDE = SOLDE +"
                    + solde + "where IDEMAIL='" + model.getUserKey() + "'");
            rset.close();
            stmt.close();
            return true;
        } catch (SQLException s) {
            System.out.println("Problème lors du remplissage du porte monnaie");
            s.printStackTrace();
            return false;
        }
    }

    public boolean addDriverTrip (Model model, String date, String vehicle, int nbSeats, String departureCity,
                                  float startLatitude, float startLongitude, int waitingTime,String arrivalCity,
                                  float arrivalLatitude, float arrivalLongitude, String startLieuDit, String endLieuDit,
                                  LinkedList<Stop> stops) throws SQLException, NullPointerException {
        Stop s1, s2 = null;
        double distance;
        int idTrajet = 0;
        double tempsParcours;

        int nbStops = stops.size();

        // Gestion du trajet à ajouter

        Statement stmt = this.con.createStatement();
        stmt.executeQuery("alter session set nls_timestamp_format='DD-MM-YYYY HH24:MI:SS'");
        stmt.executeQuery("insert into Trajet values (NULL, '" + date + "', " +nbSeats + ", " + nbStops + ", '"
                    + vehicle + "', '" + model.getUserKey() + "')"); // to_timestamp ('" + date + "', 'DD/MM/YYYY HH24:MI:SS.FF')
        ResultSet rset = stmt.executeQuery("select idtrajet from Trajet");
        while (rset.next()) idTrajet = rset.getInt(1);
        rset.close();
        stmt.close();

        // Gestion des tronçons à ajouter

        stops.addFirst(new Stop(departureCity, startLieuDit, startLatitude, startLongitude, waitingTime));
        Iterator<Stop> it = stops.iterator();

        Statement stmtStops = this.con.createStatement();
        ResultSet rsetStops;

        if (it.hasNext()) s2 = it.next();

        while (it.hasNext()) {
            s1 = s2.makeEgual();
            if (it.hasNext()) {
                s2 = it.next();
                distance = s1.distanceVolOiseau(new Stop(s2.getLatitude(), s2.getLongitude())); // distance en temps
                tempsParcours = Stop.tempsParcours(distance); // temps en minutes
                stmtStops.executeQuery("insert into Troncon values (NULL, " + idTrajet + ", '"
                        + s1.getCityName() + "', '" + s2.getCityName() + "', " + s1.getLongitude()
                        + ", " + s1.getLatitude() + ", " + s2.getLongitude() + ", " + s2.getLatitude() + ", " + distance
                        + ", " + tempsParcours + ", " + s1.getWaitingTime() + ", '" + s1.getLieuDit() + "', '" + s2.getLieuDit() + "')");
            }
        }

        distance = s2.distanceVolOiseau(new Stop(arrivalLatitude, arrivalLongitude));
        tempsParcours = Stop.tempsParcours(distance); // temps en minutes

        rsetStops = stmtStops.executeQuery("insert into Troncon values (NULL, " + idTrajet + ", '"
                + s2.getCityName() + "', '" + arrivalCity + "', " + s2.getLongitude() + ", " + s2.getLatitude()
                + ", " + arrivalLongitude + ", " + arrivalLatitude + ", " + distance
                + ", " + tempsParcours + ", " + s2.getWaitingTime() + ", '" + s2.getLieuDit() + "', '" + endLieuDit +"')");
        rsetStops.close();
        stmtStops.close();

        return true;
    }

    public boolean addPassengerTrip(Model model, Timestamp departureDate, String departureCity, String arrivalCity,
                                    float departureLongitude, float departureLatitude,
                                    float arrivalLongitude, float arrivalLatitude,
                                    LinkedList<Section> passengerListOfSections) throws SQLException, NullPointerException {


        int nbSections = passengerListOfSections.size();

        int idParcours = 0;

        Statement stmt = this.con.createStatement();
        stmt.executeQuery("alter session set nls_timestamp_format='YYYY-MM-DD HH24:MI:SS.FF1'");

        System.out.println("departureDate.toString()" + departureDate.toString());
        System.out.println("departureCity" + departureCity);
        System.out.println("arrivalCity" + arrivalCity);
        System.out.println("model.getUser().getEmail()" + model.getUser().getEmail());


        stmt.executeQuery("insert into Parcours values (NULL, '"
                + departureDate.toString()
                + "', '" + model.getUser().getEmail()
                + "', '" + departureCity + "', " + departureLongitude + ", " + departureLatitude
                + ", '" + arrivalCity + "', " + arrivalLongitude + ", " + arrivalLatitude +
                ")");
        ResultSet rset = stmt.executeQuery("select idparcours from Parcours order by idparcours desc");
        rset.next();
        idParcours = rset.getInt(1);
        rset.close();
        stmt.close();

        Iterator<Section> it = passengerListOfSections.iterator();

        Statement stmtSections = this.con.createStatement();

        while (it.hasNext()) {
            Section elementSection = it.next();
            stmtSections.executeQuery("insert into EstComposeDe values (" + idParcours + ", " + elementSection.getSectionID() + ", 'Debut')");
        }

        stmtSections.close();

        return true;
    }


    @Deprecated
    public void startSectionPassenger(int parcoursID, int tronconID) {
        try {
            Statement stmt = this.con.createStatement();
            stmt.executeQuery("update ESTCOMPOSEDE set SUIVIPASSAGER = 'Debut' where IDPARCOURS=" + parcoursID
                    + " and IDTRONCON=" + tronconID );
            stmt.close();
        } catch (SQLException s) {
            s.printStackTrace();
            System.out.println("Erreur de validation : début de tronçon");
        }
    }

    public void validateSectionPassenger(int tronconID) throws SQLException {
//        System.out.println("troncon:"+tronconID);
        int parcoursID;
        Statement getParcoursStatement = this.con.createStatement();
        ResultSet parcoursRS = getParcoursStatement.executeQuery("select idparcours " +
                        "from ESTCOMPOSEDE " +
                        "where idtroncon=" + tronconID);
        if (!parcoursRS.next()) {
            throw new SQLException("Parcours non trouvé");
        } else {
            parcoursID = parcoursRS.getInt(1);
        }
        getParcoursStatement.close();

        Statement stmt = this.con.createStatement();
        stmt.executeQuery("update ESTCOMPOSEDE set SUIVIPASSAGER = 'Fin' where IDPARCOURS=" + parcoursID
                + " and IDTRONCON=" + tronconID );
        stmt.close();
    }

    public double costCalculation(int sectionID) {
        try {
            double cost;
            double alpha;

            Statement stmt = this.con.createStatement();
            ResultSet rs = stmt.executeQuery("select v.idimmatriculation, v.energie, v.puissance, "
                    + "tro.distanceparcourue from trajet tra, troncon tro, vehicule v "
                    + "where tro.idtrajet = tra.idtrajet"
                    + " and tra.idimmatriculation = v.idimmatriculation"
                    + " and tro.idtroncon =" + sectionID);

            rs.next();
            if (rs.getString(2).equals("Essence")) alpha = 1;
            else if (rs.getString(2).equals("Diesel")) alpha = 1.5;
            else alpha = 0.5;

            cost = rs.getInt(3) * 0.10 * alpha * rs.getInt(4);

            rs.close();
            stmt.close();
            return cost;
        } catch (SQLException s) {
            s.printStackTrace();
            System.out.println("Erreur lors du calcul du prix d'une section");
            return 0;
        }
    }

    public void validateTripPassenger(Model model, int parcoursID) throws SQLException {
        int cost = (int)(model.getPosInTripsFromParcoursID(parcoursID).costCalculation());
//        System.out.println("Cout du parcours pour le passager = " + cost);

        // On récupère l'email du passager
        Statement passengerStatement = this.con.createStatement();
        String passengerEmail;
        ResultSet passengerRS = passengerStatement.executeQuery("select idemail from PARCOURS where idparcours=" + parcoursID);
        if (!passengerRS.next()) {
            throw new SQLException("Utilisateur non trouvé");
        } else {
            passengerEmail = passengerRS.getString(1);
        }
        passengerRS.close();

        // Déduire le cout du porte-monnaie du passager
        try {
            passengerStatement.executeQuery("update UTILISATEUR set solde=solde-" + cost + " where idemail='" + passengerEmail + "'");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            passengerStatement.close();
        }
    }

    public void validateSectionDriver(int tronconID) throws SQLException {

    }

    public Response validateTripDriver(Model model, int trajetID) throws SQLException {
        Response response = new Response(true, "Trip has been validated");
        boolean tripValidated = true;

        // Test si les passagers ont validé le trajet
        Statement tronconStatement = this.con.createStatement();
        Statement suiviStatement = this.con.createStatement();
        ResultSet tronconRS, suiviRS;
        try {
            tronconRS = tronconStatement.executeQuery("select idtroncon from TRONCON where idtrajet=" + trajetID);
            int tronconIndex = 1;
            while (tronconRS.next()) {
                suiviRS = suiviStatement.executeQuery("select suivipassager from ESTCOMPOSEDE where idtroncon=" + tronconRS.getInt(tronconIndex));
                while (suiviRS.next()) {
                    if (!suiviRS.getString(1).equals("Fin")) {
                        response = new Response(false, "Trip is not validated by all passengers");
                        tripValidated = false;
                        break;
                    }
                }
                suiviRS.close();
                tronconIndex += 1;
                if (!tripValidated)
                    break;
            }
            tronconRS.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return new Response(false, "Error while validating trip");
        } finally {
            suiviStatement.close();
            tronconStatement.close();
        }

        // Paiement du conducteur
        if (tripValidated) {
            // calcul du cout
            int cost = 0;
            Statement costStatement = this.con.createStatement();
            LinkedList<Integer[]> troncons = new LinkedList<>();
            ResultSet costRS;
            try {
                costRS = costStatement.executeQuery("select T.idtroncon, ECD.idparcours " +
                        "from ESTCOMPOSEDE ECD, TRONCON T " +
                        "where T.idtroncon=ECD.idtroncon and T.idtrajet=" +
                        trajetID);
                while (costRS.next()) {
                    troncons.add(new Integer[] {costRS.getInt(1), costRS.getInt(2)});
                }
            } catch (SQLException e) {
                System.out.println("Error while retrieving user trips");
                e.printStackTrace();
            }
            Iterator<Integer[]> it = troncons.iterator();
            Integer[] p;
            while (it.hasNext()) {
                p = it.next();
                cost += (int)(model.getPosInTripsFromParcoursID(p[1].intValue()).getSectionFromID(p[0].intValue()).costCalculation());
            }
//            System.out.println("Credit conducteur : " + cost);

            // acquérir email conducteur
            Statement driverStatement = this.con.createStatement();
            String driverEmail;
            ResultSet driverRS = driverStatement.executeQuery("select idemail from Trajet where idtrajet=" + trajetID);
            if (!driverRS.next()) {
                return new Response(false, "Error while paying driver");
            } else {
                driverEmail = driverRS.getString(1);
            }
            driverRS.close();

            // créditer conducteur
            try {
                driverStatement.executeQuery("update UTILISATEUR set solde=solde+" + cost + " where idemail='" + driverEmail + "'");
            } catch (SQLException e) {
                e.printStackTrace();
                return new Response(false, "Error while paying driver");
            } finally {
                driverStatement.close();
            }
        }

        return response;
    }

    public boolean isSectionDone(int tripID, int sectionID) throws SQLException {
        boolean answer;
        Statement stmt = this.con.createStatement();
        ResultSet rs = stmt.executeQuery("select suivipassager from ESTCOMPOSEDE where idparcours=" + tripID + " and idtroncon=" + sectionID);
        if (!rs.next() || rs.getString(1).equals("Debut")) {
            answer = false;
        } else {
            answer = true;
        }
        rs.close();
        stmt.close();
        return answer;
    }
}
