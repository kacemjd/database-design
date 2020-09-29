package fr.ensimag.view;

import fr.ensimag.controler.PassengerResearchRouteControl;
import fr.ensimag.controler.Response;
import fr.ensimag.model.FabriquePassenger;
import fr.ensimag.model.Section;
import fr.ensimag.model.TripDriver;
import oracle.sql.TIMESTAMP;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;


public class PassengerResearchRoute extends JPanel{
    private JLabel titleLabel;
    private JPanel rootPanel;
    private JButton researchButton;
    private JButton backButton;
    private JComboBox menuDepartureCities;
    private JComboBox menuArrivalCities;
    private JComboBox menuDepartureLocations;
    private JComboBox menuArrivalLocations;
    private JButton letSGoButton;
    private JTree researchResult;


    public PassengerResearchRoute(CardLayout cl, JPanel windowPanel, String[] listContent, PassengerResearchRouteControl control) throws SQLException {
        titleLabel.setFont(new Font("Courier New", Font.ITALIC, 17));

        add(rootPanel);
        researchResult.removeAll();
        researchResult.setModel(null);


        FabriquePassenger passenger = new FabriquePassenger();
        LinkedList<String> listOfDepartureCities = passenger.fetchAllDepartureCities();
        LinkedList<String> listOfArrivalCities = passenger.fetchAllArrivalCities();

        Collections.sort(listOfDepartureCities);
        Iterator<String> it = listOfDepartureCities.iterator();
        menuDepartureCities.addItem("All cities");
        while(it.hasNext()){
            menuDepartureCities.addItem(it.next());
        }

        Collections.sort(listOfArrivalCities);
        it = listOfArrivalCities.iterator();
        menuArrivalCities.addItem("All cities");
        while(it.hasNext()){
            menuArrivalCities.addItem(it.next());
        }

        menuDepartureCities.addActionListener(new ActionListener() {
                        @Override
            public void actionPerformed(ActionEvent e) {

                menuDepartureLocations.removeAllItems();

                if(!menuDepartureCities.getItemAt(menuDepartureCities.getSelectedIndex()).toString().equals("All cities"))    {

                    LinkedList<String> listOfDepartureLocations = null;
                    try {
                        listOfDepartureLocations = passenger.fetchSelectedDepartureLocations(menuDepartureCities.getItemAt(menuDepartureCities.getSelectedIndex()).toString());
                    } catch (SQLException s) {
                        s.printStackTrace();
                    }

                    Collections.sort(listOfDepartureLocations);
                    Iterator<String> it = listOfDepartureLocations.iterator();
                    while(it.hasNext()){
                        menuDepartureLocations.addItem(it.next());
                    }
//                longitudeDepartureDisplay.setText(((Float)listOfSections.get(currentIndex).getStartLongitude()).toString());
//                latitudeDepartureDisplay.setText(((Float)listOfSections.get(currentIndex).getStartLatitude()).toString());
                }

            }
        });

        menuArrivalCities.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                menuArrivalLocations.removeAllItems();


                if (!menuArrivalCities.getItemAt(menuArrivalCities.getSelectedIndex()).toString().equals("All cities")) {


                    LinkedList<String> listOfArrivalLocations = null;
                    try {
                        listOfArrivalLocations = passenger.fetchSelectedArrivalLocations(menuArrivalCities.getItemAt(menuArrivalCities.getSelectedIndex()).toString());
                    } catch (SQLException s) {
                        s.printStackTrace();
                    }

                    Collections.sort(listOfArrivalLocations);
                    Iterator<String> it = listOfArrivalLocations.iterator();
                    while (it.hasNext()) {
                        menuArrivalLocations.addItem(it.next());
                    }
//                longitudeDepartureDisplay.setText(((Float)listOfSections.get(currentIndex).getStartLongitude()).toString());
//                latitudeDepartureDisplay.setText(((Float)listOfSections.get(currentIndex).getStartLatitude()).toString());
                }

            }
        });

        researchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                menuArrivalLocations.removeAllItems();

                LinkedList<String> listOfArrivalLocations = null;
                try {
                    listOfArrivalLocations = passenger.fetchSelectedArrivalLocations(menuArrivalCities.getItemAt(menuArrivalCities.getSelectedIndex()).toString());
                } catch (SQLException s) {
                    s.printStackTrace();
                }

                Collections.sort(listOfArrivalLocations);
                Iterator<String> it = listOfArrivalLocations.iterator();
                while(it.hasNext()){
                    menuArrivalLocations.addItem(it.next());
                }
//                longitudeDepartureDisplay.setText(((Float)listOfSections.get(currentIndex).getStartLongitude()).toString());
//                latitudeDepartureDisplay.setText(((Float)listOfSections.get(currentIndex).getStartLatitude()).toString());
            }
        });


        researchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int nbOfSectionsFound = 0;

                LinkedList<TripDriver> listOfAllDriverTrips;
                LinkedList<Section> listOfAllSections;

                researchResult.removeAll();
                researchResult.setModel(null);
                DefaultMutableTreeNode rootNode =
                        new DefaultMutableTreeNode("Available trips");


                try {
                    listOfAllDriverTrips = passenger.fetchAllTrips();
                    listOfAllSections = passenger.fetchAllSections();

                    Iterator<TripDriver> itTripDriver = listOfAllDriverTrips.iterator();
                    Iterator<Section> itSection;
                    DefaultMutableTreeNode tripNode;
                    DefaultMutableTreeNode sectionNode;
                    boolean hasStartedRoute;
                    boolean hasFinishedRoute;
                    String departureCity = menuDepartureCities.getItemAt(menuDepartureCities.getSelectedIndex()).toString();
                    String arrivalCity = menuArrivalCities.getItemAt(menuArrivalCities.getSelectedIndex()).toString();
                    String departureLocation = null;
                    if (menuDepartureLocations.getSelectedItem() != null) {
                        departureLocation = (String) menuDepartureLocations.getSelectedItem();
                    }
                    String arrivalLocation = null;
                    if (menuArrivalLocations.getSelectedItem() != null) {
                        arrivalLocation = (String) menuArrivalLocations.getSelectedItem();
                    }

                    Timestamp departureTime, cumulatedTime, sectionDepartureTime, tripDepartureTime;



                    while(itTripDriver.hasNext()){
                        TripDriver elementTripDriver = itTripDriver.next();
                        hasStartedRoute = false;
                        hasFinishedRoute = false;

                        departureTime = Timestamp.valueOf(elementTripDriver.getTripDate());
                        cumulatedTime = new Timestamp(departureTime.getTime());
                        tripDepartureTime = new Timestamp(departureTime.getTime());


                        itSection = listOfAllSections.iterator();
                        tripNode = new DefaultMutableTreeNode();
                        while(itSection.hasNext()) {
                            Section elementSection = itSection.next();
                            if (elementSection.getTripID() == elementTripDriver.getTripID()) {
                                sectionDepartureTime = new Timestamp(cumulatedTime.getTime());
                                String startCity = elementSection.getStartCity();
                                String startLocation = elementSection.getStartLocation();
                                String endCity = elementSection.getEndCity();
                                String endLocation = elementSection.getEndLocation();
                                long sectionTimeInMs =  elementSection.getSectionTime() * 60000;
                                String description = startCity + "/" + startLocation +
                                                     "->" + endCity + "/" + endLocation +
                                                     " @ " + cumulatedTime.toString();
                                SectionData sectionData = new SectionData(elementSection, sectionDepartureTime, description);
                                sectionNode = new DefaultMutableTreeNode(sectionData);
                                if ((!hasStartedRoute) && ((departureCity.equals("All cities") ||
                                    ((startCity.equals(departureCity) && startLocation.equals(departureLocation)))))) {
                                    hasStartedRoute = true;
                                    sectionDepartureTime = new Timestamp(cumulatedTime.getTime());
                                    tripNode.add(sectionNode);
                                } else if (hasStartedRoute && !hasFinishedRoute) {
                                    tripNode.add(sectionNode);
                                }
                                cumulatedTime.setTime(cumulatedTime.getTime() + sectionTimeInMs);
                                if (!arrivalCity.equals("All cities") && (endCity.equals(arrivalCity) && endLocation.equals(arrivalLocation))) {
                                    hasFinishedRoute = true;
                                }
                            }
                        }
                        if ((tripNode.getChildCount() > 0) && (hasFinishedRoute || (hasStartedRoute && arrivalCity.equals("All cities")))) {
                            tripNode.setUserObject("Trip n°" + elementTripDriver.getTripID() +
                                            " from " + tripDepartureTime.toString() + " to " + cumulatedTime.toString()
                            );
                            nbOfSectionsFound = tripNode.getChildCount();
                            rootNode.add(tripNode);
                        }

                    }

                } catch (SQLException s) {
                    s.printStackTrace();

                }

                DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
                researchResult.setModel(treeModel);

                Response res = control.checkResearchResults(nbOfSectionsFound);
                JOptionPane.showMessageDialog(windowPanel, res.getResponse());

//                researchResult.add(new JPanel());

            }
        });


        letSGoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath[] treePaths = researchResult.getSelectionModel().getSelectionPaths();
//                System.out.println(researchResult.getSelectionModel().getSelectionPaths().length);

                int i = 1;
                int length = researchResult.getSelectionModel().getSelectionPaths().length;

                String departureCity = null;
                float departureLongitude = 0;
                float departureLatitude = 0;
                Timestamp departureDate = null;

                String arrivalCity = null;
                float arrivalLongitude = 0;
                float arrivalLatitude = 0;

                int IdTrajet = 0;

                LinkedList<Section> passengerListOfSections = new LinkedList<Section>();

                boolean errMultipleTripsChosen = false;
                boolean errOnSelectedObjects = false;


                for (TreePath treePath: treePaths) {
                    try{
                        DefaultMutableTreeNode selectedElement = (DefaultMutableTreeNode)treePath.getLastPathComponent();
                        SectionData sectionData = (SectionData) selectedElement.getUserObject();

                        if(i == 1){
                            departureCity = sectionData.getSection().getStartCity();
                            departureLongitude = sectionData.getSection().getStartLongitude();
                            departureLatitude = sectionData.getSection().getStartLatitude();
                            departureDate = sectionData.getDepartureDate();
                            IdTrajet = sectionData.getSection().getTripID();
                        }

                        if(sectionData.getSection().getTripID() != IdTrajet){
                            errMultipleTripsChosen = true;
                        }

                        if(i == length){
                            arrivalCity = sectionData.getSection().getEndCity();
                            arrivalLongitude = sectionData.getSection().getEndLongitude();
                            arrivalLatitude = sectionData.getSection().getEndLatitude();
                        }

                        passengerListOfSections.add(sectionData.getSection());

                        i++;

                    }catch (ClassCastException err){
                        errOnSelectedObjects = true;
                    }

                }

                PassengerCreateRouteFromValues values = new PassengerCreateRouteFromValues(departureDate,departureCity,arrivalCity , departureLongitude, departureLatitude,
                        arrivalLongitude, arrivalLatitude, passengerListOfSections);

                Response res = control.checkPassengerRoute(values, errMultipleTripsChosen, errOnSelectedObjects);
                JOptionPane.showMessageDialog(windowPanel, res.getResponse());
                if(res.getStatusCode()) {
                    cl.show(windowPanel, listContent[4]);
                }
                }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(windowPanel, listContent[4]);
            }
        });

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}


class SectionData {

    private Section section;
    private Timestamp departureDate;
    private String description;

    public SectionData(Section section, Timestamp departureDate, String description) {
        this.section = section;
        this.departureDate = departureDate;
        this.description = description;
    }

    public Section getSection() {
        return section;
    }

    public Timestamp getDepartureDate() {
        return departureDate;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return getDescription();
    }
}