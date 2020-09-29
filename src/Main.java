import fr.ensimag.controler.Controler;

import fr.ensimag.model.Model;

import fr.ensimag.view.View;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException, SQLException {
		Model model = new Model();
		Controler controler = new Controler(model);
		View view = new View(controler);
	}

}
