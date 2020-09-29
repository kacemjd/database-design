package fr.ensimag.model;

import java.sql.*;
import java.util.LinkedList;

public class FabriqueDriver extends FabriqueAbstraite {
    public User createUser(String email) throws SQLException {
        Connection c = super.connect();
        Statement stmt = c.createStatement();
        Statement stmtCar = c.createStatement();
        ResultSet rs = stmt.executeQuery("select * from Utilisateur where idemail='" + email + "'");
        rs.next();
        Driver u = new Driver(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                rs.getString(5), rs.getInt(6 ));
        rs = stmt.executeQuery("select idimmatriculation from PEUTCONDUIRE where idemail='"
                + email + "'");

        try {
            ResultSet rsCar = null;
            while (rs.next()) {
                rsCar = stmtCar.executeQuery("select * from VEHICULE where idimmatriculation='" +
                        rs.getString(1) + "'");
                rsCar.next();
                u.addCar(rsCar.getString(1), rsCar.getString(2), rsCar.getString(3),
                        rsCar.getString(4), rsCar.getInt(5), rsCar.getInt(6));
                //System.out.println(rsCar.getString(1) + " - ");
            }
            rsCar.close();
            stmtCar.close();
        } catch (SQLException s) {
            System.out.println("Erreur lors de l'accès à la BD : Instanciation voiture user");
            s.printStackTrace();
        } catch (NullPointerException n) {
            n.printStackTrace();
            System.out.println("L'utilisateur ne possède pas de voiture?");
        }

        rs.close();
        stmt.close();
        c.close();
        return u;
    }

    public LinkedList<Trip> createTrip(String email) throws SQLException, NullPointerException {
        LinkedList<Trip> trips = new LinkedList<>();
        LinkedList<Section> troncons = new LinkedList<>();

        Connection c = super.connect();
        Statement stmt = c.createStatement();

        Statement stmtSections = c.createStatement();
        ResultSet rs = stmt.executeQuery("select * from TRAJET where IDEMAIL='" + email +"'");
        ResultSet rsTroncon = null;
        while (rs.next()) {
            rsTroncon = stmtSections.executeQuery("select * from TRONCON where idtrajet=" + rs.getInt(1));
            while(rsTroncon.next()) {
                troncons.add(new Section(rsTroncon.getInt(1), rsTroncon.getInt(2), rsTroncon.getString(3),
                        rsTroncon.getString(4), rsTroncon.getFloat(5), rsTroncon.getFloat(6),
                        rsTroncon.getFloat(7), rsTroncon.getFloat(8), rsTroncon.getInt(9),
                        rsTroncon.getInt(10), rsTroncon.getInt(11),
                        rsTroncon.getString(12), rsTroncon.getString(13)));
            }
            trips.add(new TripDriver(rs.getInt(1), rs.getString(2), rs.getString(6), rs.getInt(3),
                    rs.getInt(4), rs.getString(5), troncons));
            troncons = new LinkedList<>();
        }
        rsTroncon.close();
        stmtSections.close();
        rs.close();
        stmt.close();
        c.close();
        return trips;
    }
}
