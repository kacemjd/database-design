package fr.ensimag.model;

import java.sql.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;
import fr.ensimag.model.AbstractNotification;

import oracle.jdbc.*;
import oracle.jdbc.dcn.*;

public class Listener
implements DatabaseChangeListener {
	/*
	 * Informations de connexion
	 */
	private static String username;
	private static String password;
	private static final String databaseURL = new String("jdbc:oracle:thin:@ensioracle1.imag.fr:1521:ensioracle1");
	
	/*
	 * Requete specifiant les tables a surveiller
	 */
	private String watchQuery = null;
	
	/*
	 * Variables servant a la surveillance
	 */
	// Instance de connexion a la BDD
	private OracleConnection connection = null;
	// Thread de surveillance
	private DatabaseChangeRegistration dcr = null;
	
	/*
	 * File contenant les notifications à gérer
	 */
	private Queue<AbstractNotification> notifications = new LinkedList<AbstractNotification>();
	
	public Listener(String username, String password) {
		Listener.username = username;
		Listener.password = password;
	}
	
	public Listener(String username, String password, String watchQuery) {
		Listener.username = username;
		Listener.password = password;
		this.watchQuery = new String(watchQuery);
	}
	
	/*
	 * Etablit une connexion a la BDD
	 */
	private void connect() 
	throws SQLException {
		connection = (OracleConnection)DriverManager.getConnection(databaseURL, username, password);		
	}
	
	/*
	 * Ferme la connexion a la BDD
	 */
	private void disconnect()
	throws SQLException {
		connection.close();
	}
	
	/*
	 * Met a jour la requete des tables a surveiller
	 * @param newQuery
	 */
	public void setQuery(String newWatchQuery) {
		watchQuery = new String(newWatchQuery);
	}
	
	/*
	 * Demarre la surveillance
	 */
	public void createListener() 
	throws SQLException, NullPointerException {
		connect();
		
		// Configure le thread de surveillance
		Properties properties = new Properties();
		properties.setProperty(OracleConnection.DCN_NOTIFY_ROWIDS, "true");
		properties.setProperty(OracleConnection.DCN_QUERY_CHANGE_NOTIFICATION, "true");
		dcr = connection.registerDatabaseChangeNotification(properties);
		
		try {
			// Ajoute le Listener (this) au thread de surveillance
			dcr.addListener(this);
			
			// Specify the tables to watch
			OracleStatement statement = (OracleStatement)connection.createStatement();
			statement.setDatabaseChangeRegistration(dcr);
			if (watchQuery == null)
				throw new NullPointerException("Undefined watch query");
			ResultSet resultSet = statement.executeQuery(watchQuery);
			while (resultSet.next()) {} //nécessaire, va savoir pourquoi
			// On libere les objets
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			// On stoppe le thread de surveillance et on transmet l'erreur
			connection.unregisterDatabaseChangeNotification(dcr);
			throw e;
		} catch (NullPointerException e) {
			connection.unregisterDatabaseChangeNotification(dcr);
			throw e;
		} finally {
			disconnect();
		}
	}
	
	/*
	 * Stoppe la surveillance
	 */
	public void deleteListener() 
	throws SQLException {
		connect();
		try {
			connection.unregisterDatabaseChangeNotification(dcr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	/*
	 * Gère les notifications de mise à jour de la BDD
	 */
	public void run() {
		Iterator<AbstractNotification> it = notifications.iterator();
		while (it.hasNext()) {
			it.next().handle();
		}
	}

	/*
	 * Affiche une notification de changement dans la BDD dans la console
	 * @param dce notification de changement dans la BDD
	 */
	private void displayNotification(DatabaseChangeEvent dce) {
		System.out.println(dce.toString());
	}
	
	/*
	 * Gere une notification de changement dans la BDD
	 * @param dce notification de changement dans la BDD
	 */
	@Override
	public void onDatabaseChangeNotification(DatabaseChangeEvent dce) {
		AbstractNotification notification = null;
		
		// Affiche la notification (DEBUG ONLY)
		displayNotification(dce);
		
		// SwitchCase pour générer l'instance de Notification adéquate
		// TODO
		
		// Ajout de la notification à la file
		notifications.add(notification);
	}
}
