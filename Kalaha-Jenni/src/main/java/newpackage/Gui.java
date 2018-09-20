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
 * @author Jenni
 */
public class Gui extends JFrame {

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
    public Gui() {

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
    }

      
    
    
    public static void main(String[] args) {
        new Gui();
    } 

}
