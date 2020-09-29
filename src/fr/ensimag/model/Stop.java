package fr.ensimag.model;

public class Stop {
    private String cityName;
    private String lieuDit;
    private float latitude;
    private float longitude;
    private int waitingTime;

    public Stop(String cityName, String lieuDit, float latitude, float longitude, int waitingTime) {
        this.cityName = cityName;
        this.lieuDit = lieuDit;
        this.latitude = latitude;
        this.longitude = longitude;
        this.waitingTime = waitingTime;
    }

    public Stop(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCityName() { return cityName; }

    public float getLatitude() { return latitude; }

    public float getLongitude() { return longitude; }

    public int getWaitingTime() { return waitingTime; }

    public String getLieuDit() { return lieuDit; }

    public static double convertRad(double val) {
        return (Math.PI * val) / 180;
    }

    public static double tempsParcours(double distance) {
        return (60 * distance * Math.pow(10,-3)) / 80;
    }

    public double distanceVolOiseau(Stop coord) {
        double R = 6378000;

        double lat_a = convertRad(this.latitude);
        double long_a = convertRad(this.longitude);
        double lat_b = convertRad(coord.getLatitude());
        double long_b = convertRad(coord.getLongitude());

        return R * (Math.PI/2 - Math.asin( Math.sin(lat_b) * Math.sin(lat_a) + Math.cos(long_b - long_a) * Math.cos(lat_b) * Math.cos(lat_a)));
    }

    public double distanceEuclidienne(Stop coord) {
        double lati = Math.pow(this.latitude - coord.getLatitude(),2);
        double longi = Math.pow(this.longitude - coord.getLongitude(), 2);
        return Math.sqrt(lati + longi);
    }

    public Stop makeEgual() {
        return new Stop(this.cityName, this.lieuDit, this.latitude, this.longitude, this.waitingTime);
    }
}
