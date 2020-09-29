package fr.ensimag.model;

public class testStop {
    public static void main() {
        Stop s1 = new Stop(5,6);
        Stop s2 = new Stop(10, 16);

        double distanceEuc = s1.distanceEuclidienne(s2);
        double distanceOiseau = s1.distanceVolOiseau(s2);


        System.out.println(distanceEuc + "\n" + distanceOiseau);
    }
}
