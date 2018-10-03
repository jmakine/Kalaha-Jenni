/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JTextField;
import newpackage.Board;

/**
 *
 * @author Jenni
 */
public class SiirtoKuuntelija implements ActionListener {

    private LaudanPaivitys rajapinta;
    private JLabel pelilauta;
    private JTextField syote;

    public SiirtoKuuntelija(JTextField syote, JLabel pelilauta) {
        this.rajapinta = rajapinta;
        this.pelilauta = pelilauta;
        this.syote = syote;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
    //  Board b = new Board(pelilauta.getText(), syote.getText());
      //this.laudanPaivitys.talleta(henkilo);
    }

  //  @Override
  //  public void actionPerformed(ActionEvent ae) {
  //      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
