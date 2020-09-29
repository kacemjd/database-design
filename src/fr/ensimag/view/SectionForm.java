package fr.ensimag.view;

import javax.swing.*;

public class SectionForm extends JPanel{
    protected JTextField cityField;
    protected JTextField latitudeField;
    protected JTextField longitudeField;
    protected JPanel rootPanel;
    protected JLabel titleLabel;
    protected JLabel latitudeLabel;
    protected JLabel longitudeLabel;
    protected JTextField waitingTimeField;
    protected JLabel waitingTimeLabel;

    public SectionForm(){
        add(rootPanel);
    }
}
