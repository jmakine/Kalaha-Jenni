/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Jenni
 */
public class Gui extends JFrame {

    private JTextField tekstikenttä, koneensiirto, omasiirto;
    private JButton nappi;
    private JLabel otsikko;

    class Kuuntelija implements ActionListener {

        public void actionPerformed(ActionEvent e) {

        }
    }

    private Board lauta;
    private Main ohjelma;

    public Gui() {

        //ikkuna ilmestyy kuvaruudun keskelle
        setLocationRelativeTo(null);
        //ohjelma sulkeutuu ikkunan suljettua
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //asetetaan koko
        this.setSize(200, 300);

        /*        JPanel paneli = new JPanel();
        JPanel paneli1 = new JPanel();
        JPanel paneli2 = new JPanel();
        paneli.setLayout(new BorderLayout());
        paneli1.setLayout(new FlowLayout(FlowLayout.CENTER));
        paneli2.setLayout(new FlowLayout(FlowLayout.LEFT));
        paneli.add(nappi);
      paneli.add(this.koneensiirto);
         */
        this.koneensiirto = new JTextField();
        this.omasiirto = new JTextField();
        this.otsikko = new JLabel("Kalaha");

        this.nappi = new JButton();
        this.tekstikenttä = new JTextField();
        //nappi.addActionListener(new Kuuntelija());
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Gui();
    }
}
