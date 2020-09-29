package fr.ensimag.view;

import java.awt.*;
import javax.swing.*;

public class BackgroundImage extends JPanel {

    private static final long serialVersionUID = 1L;

    private Image img;

    public BackgroundImage(Image img){
        this.img = img;
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
        Image logo = new ImageIcon("logo.png").getImage();
        g.drawImage(logo, 45, 20, 80,160,null);

        Font font = new Font("Verdana", Font.BOLD, 20);
        g.setFont(font);
        g.setColor(new Color(116, 83, 227));
        g.drawString("VerbiageVoiture", 10, 200);
    }
}