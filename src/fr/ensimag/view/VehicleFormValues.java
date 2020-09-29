package fr.ensimag.view;

import fr.ensimag.model.energyType;

public class VehicleFormValues {
    private String registration;
    private String brand;
    private String model;
    private energyType energy;
    private String taxPower;
    private int numberOfSeats;

    public VehicleFormValues(String registration, String brand, String model, String energy,
                             String taxPower, int numberOfSeats){
        setRegistration(registration);
        setBrand(brand);
        setModel(model);
        setEnergy(energy);
        setTaxPower(taxPower);
        setNumberOfSeats(numberOfSeats);
    }

    @Override
    public String toString() {
        return "VehicleFormValues{" +
                "registration='" + registration + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", energy='" + energy + '\'' +
                ", taxPower='" + taxPower + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                '}';
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public energyType getEnergy() {
        return energy;
    }

    public void setEnergy(String energy) {
        if (energy.equals("diesel"))
            this.energy = energyType.Diesel;
        else if (energy.equals("electrique"))
            this.energy = energyType.Electrique;
        else this.energy = energyType.Essence;
    }

    public String getTaxPower() {
        return taxPower;
    }

    public void setTaxPower(String taxPower) {
        this.taxPower = taxPower;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
}
