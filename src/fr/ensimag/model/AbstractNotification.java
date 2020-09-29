package fr.ensimag.model;

public abstract class AbstractNotification {
	public AbstractNotification() {
		
	}
	
	/*
	 * 	Reaction de l'application a la notification
	 */
	public abstract void handle();
}
