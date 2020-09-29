package fr.ensimag.model;

public class Car {
    private String idImmatriculation;
    private String marque;
    private String modele;
    private energyType energie;
    private int puissance;
    private int nbPlaces;

    public String getIdImmatriculation() {
        return idImmatriculation;
    }

    public String getMarque() {
        return marque;
    }

    public String getModele() {
        return modele;
    }

    public energyType getEnergie() {
        return energie;
    }

    public int getPuissance() {
        return puissance;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public Car (String idImmatriculation, String marque, String modele, int puissance, int nbPlaces) {
        this.idImmatriculation = idImmatriculation;
        this.marque = marque;
        this.modele = modele;
        this.puissance = puissance;
        this.nbPlaces = nbPlaces;
    }

    public void setEnergie(String energie) {
        if (energie.equals("diesel"))
            this.energie = energyType.Diesel;
        else if (energie.equals("electrique")) {
            this.energie = energyType.Electrique;
        }
        else this.energie = energyType.Essence;
    }

}
