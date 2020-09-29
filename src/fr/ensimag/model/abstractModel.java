package fr.ensimag.model;

import fr.ensimag.observer.Observer;
import fr.ensimag.observer.Subject;

import java.util.ArrayList;

public class abstractModel extends Subject {
    private ArrayList<Observer> listObserver;

    public abstractModel() {
        listObserver = new ArrayList<>();
    }

    public void addObserver(Observer o){
        this.listObserver.add(o);
    }

    public void removeObserver(Observer o){
        this.listObserver.remove(o);
    }

    public void notifyObserver(){
        for(Observer o : listObserver) {
            o.update();
        }
    }
}
