package fr.ensimag.view;

import fr.ensimag.controler.Controler;
import fr.ensimag.observer.Observer;

import javax.swing.*;


import java.io.IOException;
import java.sql.SQLException;

public class View implements Observer {
    private Window window;

    public View(Controler control) throws IOException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, SQLException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                window = null;
                try {
                    window = new Window(control);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException s) {
                    s.printStackTrace();
                }
                window.setVisible(true);
				/*RegisterForm myGUIForm = new RegisterForm(checkRegistration);
				myGUIForm.setVisible(true);*/
            }
        });

        window = new Window(control);
    }

    public void update() {

    }
}
