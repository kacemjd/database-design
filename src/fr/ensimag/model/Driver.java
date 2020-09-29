package fr.ensimag.model;

import java.util.Iterator;
import java.util.LinkedList;

public class Driver extends User {
    LinkedList<Car> cars;

    public Driver(String email, String firstName, String name, String city, String password, int solde)
    {
        super(email, firstName, name, city, password, solde);
        this.cars = new LinkedList<>();
    }

    public Driver(User user){
        super(user.getEmail(), user.getFirstName(), user.getName(), user.getCity(), user.getPassword(), user.getSolde());
        this.cars = new LinkedList<>();
    }

    public Iterator<Car> iteratorCars() {
        return cars.iterator();
    }

    public void addCar (String idImmatriculation, String marque, String modele, String energie,
                        int puissance, int nbPlaces) {
        Car car = new Car(idImmatriculation, marque, modele, puissance, nbPlaces);
        car.setEnergie(energie);
        cars.add(car);
    }
}
