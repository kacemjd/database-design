package fr.ensimag.model;

import java.sql.*;
import java.util.LinkedList;

public abstract class FabriqueAbstraite {
    public abstract User createUser(String email) throws SQLException;
    public abstract LinkedList<Trip> createTrip(String email) throws SQLException;

    public Connection connect() {
        try {
            String URL = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";
            return DriverManager.getConnection(URL, "svenssop", "75BACTACBorrusque38");
        } catch (SQLException s) {
            s.printStackTrace();
            return null;
        }
    }
}
