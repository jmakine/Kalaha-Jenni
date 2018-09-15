package newpackage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;

/**
 *
 * @author Jenni
 * @version 1.0.
 * 
 * Main-luokassa on ohjelman suoritus.
 * Board = pelitilanne
 * GUI = käyttöliittymä (myöhemmin)
 */

public class Main {

    
    public static void main(String[] args) {

        //kayttis myöhemmin;
        Scanner input = new Scanner(System.in);

        //aloittaja arvotaan:
        System.out.println("Arvotaan aloittaja");
        System.out.println("Valitse numero 0 tai 1");

        int aloittaja;
        //pelaaja valitsee 0 tai 1
        long pelaajanValinta = input.nextLong();
        System.out.println("Valitsit: " + pelaajanValinta);
        //kone arpoo 0 tai 1    
        long luku = Math.round(Math.random());
        System.out.println("Kone arpoi: " + luku);
        if (luku != pelaajanValinta) {
            aloittaja = 2;
            System.out.println("Tietokone aloittaa");
        } else {
            aloittaja = 1;
            System.out.println("Saat aloittaa");
        }

        //Peli alkaa:
        //Asetetaan pelilaudalle aloitustilanne ja tulostetaan lauta
        Board board = new Board();
        board.startBoard();
        board.printBoard();

        //jos tietokone aloittaa
        if (aloittaja == 2) {
            //optimaalinen aloitussiirto on pelaajan 2. kupista -> uusi vuoro
            System.out.println("Kone aloittaa kiposta 2");
            board.teeSiirto(8, 2);
        }

        while (!board.isGameOver()) {

            boolean siirtoOk = true;

            board.minimax(0, 2);
            System.out.println("Kone siirtää kupista " + board.koneenSiirto);
            board.teeSiirto(board.koneenSiirto, 2);
            board.printBoard();

            do{
            System.out.println("Sinun vuorosi: ");
            int siirto = input.nextInt() - 1;
            board.teeSiirto(siirto, 1);   
            } while(!siirtoOk);            
            board.printBoard();
        }
        
        if (board.isGameOver()) {
            int pisteet = board.evaluate();
            if (pisteet < 0) {
                System.out.println("Peli päättyi, voitit!");
            } else if (pisteet > 0) {
                System.out.println("Hävisit!");
            } else {
                System.out.println("Tasapeli!");
            }
        }
    }
    
}
