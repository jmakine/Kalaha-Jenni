/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * käyttis
 *
 * @author Jenni
 */
/*public class Gui extends JFrame {

    private JTextField omasiirto, koneensiirto;
    private JButton nappi;
    private JLabel pelitilanne, info;

    class Kuuntelija implements ActionListener {

        public void actionPerformed(ActionEvent e) {

        }
    }

    //private Board lauta;
    //private Main ohjelma;

    /**
     * konstuktori
 */
 /*public Gui() {

        //ikkuna ilmestyy kuvaruudun keskelle
        setLocationRelativeTo(null);
        //ohjelma sulkeutuu ikkunan suljettua
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //asetetaan koko
        this.setSize(200, 300);

        JPanel mainPaneli = new JPanel();
        JPanel paneli1 = new JPanel();
        //JPanel paneli2 = new JPanel();
        mainPaneli.setLayout(new BorderLayout());
        paneli1.setLayout(new FlowLayout(FlowLayout.CENTER));
       // paneli2.setLayout(new FlowLayout(FlowLayout.LEFT));
        paneli1.add(nappi);
        mainPaneli.add(this.koneensiirto);
         
        this.info = new JLabel();
        this.omasiirto = new JTextField();
        this.nappi = new JButton("Ok");
        this.koneensiirto = new JTextField();
        this.pelitilanne = new JLabel();
        //nappi.addActionListener(new Kuuntelija());
        this.setVisible(true);
    }*/
//public static void main(String[] args) {
//  new Gui();
// } 
//}
//Imports are listed in full to show what's being used
//could just import javax.swing.* and java.awt.* etc..
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Gui {
    
        JLabel pelinKulku;
        JLabel pelilauta;
        JTextField syote;              
        JButton nappi;
        

    public static void main(String[] args) {
        Board b = new Board();
        b.startBoard();
        new Gui(b);
    }

    public Gui(Board b) {
        JFrame guiFrame = new JFrame();        
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("Kalaha");
        guiFrame.setSize(300, 250);
        guiFrame.setLocationRelativeTo(null);
        
        
        //ylemmässä JPanel:ssa on kaksi JLabeliä: pelin ohjeistus ja pelilaudan tilanne
        final JPanel ohjeJApeliLauta = new JPanel();
        ohjeJApeliLauta.setLayout(new GridLayout(1,2));
        pelinKulku = new JLabel(); //tähän aina nappia painamalla päivittyy tekstiä
        pelilauta = new JLabel(b.pelitilanne); //kun tehdään siirto, päivittyy pelilauta=String
        ohjeJApeliLauta.add(pelinKulku);
        ohjeJApeliLauta.add(pelilauta);
        ohjeJApeliLauta.setVisible(true);
        

        //Toinen JPanel sisältää napin ja syötekentän, jonka arvo otetaan käyttöön nappia painamalla

        final JPanel syoteJaNappi = new JPanel();   
        syoteJaNappi.setLocation(2, 1);
        syote = new JTextField();              
        nappi = new JButton("Ok");

        nappi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                
                
            }
        });
        
        syoteJaNappi.add(syote);
        syoteJaNappi.add(nappi);
        syoteJaNappi.setVisible(true);

        guiFrame.add(ohjeJApeliLauta, BorderLayout.NORTH);
        guiFrame.add(syoteJaNappi, BorderLayout.SOUTH);
        
        guiFrame.setVisible(true);
    }
}
