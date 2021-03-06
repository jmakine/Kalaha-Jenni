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
 * Game-luokassa on pelin kulku. Kutsutaan Main-luokassa.
 */
public class Game {

    Scanner input = new Scanner(System.in);
    int[] maxpisteet;

    /**
     * Tässä metodissa on koko pelin kulku alusta loppuun.
     * @param board on pelilauta, jolla pelataan.
     * @throws InterruptedException
     */
    public void pelaa(Board board) throws InterruptedException {
        int pelaaja = arvotaanAloittaja();

        board.startBoard();
        board.printBoard();

        while (!board.isGameOver()) {

            board.uusiVuoro = true;

            while (pelaaja == 2 && board.uusiVuoro) {

                board.uusiVuoro = false;

                //Mitataan minimax-algoritmin suoritusaika.
                //long startTime = System.nanoTime();

                maxpisteet = MiniMax.minimax(pelaaja, board, 0, -1000, 1000);
                //System.out.println("minimax palauttaa maksimipisteet: "+Arrays.toString(maxpisteet));

                //long endTime = System.nanoTime();
                //long duration = (endTime - startTime)/1000000;
                
                //System.out.println("Minimax suorittamiseen meni " + duration + " millisekuntia.");
                                
                koneSiirtää(board, maxpisteet);

                if (board.isGameOver()) {
                    break;
                }

                //Jos kone ei saa uutta vuoroa, siirtyy vuoro pelaajalle.
                if (board.uusiVuoro) {
                    System.out.println("Saan uuden vuoron");
                } 
                else {
                    pelaaja = 1;
                }
            }

            board.uusiVuoro = true;

//-----------------------pelaajan vuoro-----------------------
            while (pelaaja == 1 && board.uusiVuoro) {

                board.uusiVuoro = false;

                int siirto = pelaajaValitseeSiirron(board);

                board.teeSiirtoOikeasti(siirto, pelaaja);

                System.out.println("Pelitilanne siirron jälkeen: ");
                board.printBoard();

                if (board.isGameOver()) {
                    break;
                }

                //Jos pelaaja ei saa uutta vuoroa, siirtyy vuoro koneelle.
                if (board.uusiVuoro) {
                    System.out.println("Saat uuden vuoron:");
                } else {
                    pelaaja = 2;
                }
            }

        }
        laskePisteetJaTulostaLopputilanne(board);

    }

    /**
     * Kysyy pelaajalta luvun, 1 tai 0 ja arpoo pelin aloittajan.
     * @return 1, jos arvottu aloittaja on pelaaja tai 2, jos aloittaja on tietokone
     */
    public int arvotaanAloittaja() { //static

        System.out.println("Arvotaan aloittaja, valitse 1 tai 0");

        int aloittaja;
        Scanner input1 = new Scanner(System.in);        
        int valinta= input1.nextInt();
        
        //kone arpoo 1 tai 0 ja vertaa sitä pelaajan antamaan lukuun
        long luku = Math.round(Math.random());
        System.out.println("Kone arpoi: " + luku);
        if (luku != valinta) {
            aloittaja = 2;
            System.out.println("Tietokone aloittaa");
        } else {
            aloittaja = 1;
            System.out.println("Saat aloittaa");
        }
        return aloittaja;
    }
    
    /**
     * Kone valitsee siirron siitä kiposta, jolle lasketut pisteet on korkeimmat.
     * @param board on pelilauta, jolle siirto suoritetaan
     * @param makspisteet on minimax algoritmin palauttamat pisteet kullekin mahdolliselle siirrolle
     * @throws InterruptedException
     */
    public void koneSiirtää(Board board, int[] makspisteet) throws InterruptedException {
        
        int koneSiirtaa = -1;
        int max = -1000;
        for (int r = 0; r < 6; r++) {
            if (maxpisteet[r] > max && board.lauta[12 - r] != 0 && maxpisteet[r]!=1000) {
                max = maxpisteet[r];
                koneSiirtaa = 12 - r;
            }
        }

        //tehdään valittu siirto ja tulostetaan pelitilanne. (Tulosteiden esitystä hidastettu.)
        System.out.println("Kone siirtää kupista " + (koneSiirtaa - 6));
        Thread.sleep(2000);
        board.teeSiirtoOikeasti(koneSiirtaa, 2);
        System.out.println("Pelitilanne siirron jälkeen:");
        board.printBoard();
        Thread.sleep(2000);
    }

    /**
     * Tässä varmistetaan, että pelaajan antama syöte kelpaa.
     * @param board on pelilauta, jolle siirto suoritetaan
     * @return pelilaudan indeksin, josta pelaaja siirtää
     */
    public int pelaajaValitseeSiirron(Board board) {
        System.out.println("Valitse siirtosi: ");
        int syote = input.nextInt();

        //valinta yli/alle sallitun indeksin tai valittu tyhjä kippo
        while (syote > 6 || syote < 1 || board.lauta[syote - 1] == 0) {
            if (syote > 6 || syote < 1) {
                System.out.println("Voit valita kipoista 1-6.\nValitse uudelleen:");
                syote = input.nextInt();
            }            
            if (board.lauta[syote - 1] == 0) {
                System.out.println("Et voi tehdä siirtoa tyhjästä kiposta.\nValitse uudelleen.");
                syote = input.nextInt();
            }
        }
        return syote - 1;
    }

    /**
     * Kun peli loppuu, tämä metodi laskee pelin pistetilanteen ja siirtää loput kivistä sen pelaajan mancalaan, 
     * jonka puolella kiviä vielä on. 
     * 
     * @param board on pelilauta, jonka pistetilanne lasketaan. 
     * @throws InterruptedException
     */
    public void laskePisteetJaTulostaLopputilanne(Board board) throws InterruptedException {
        int loppupisteet = board.evaluate();
        for (int i = 0; i < 6; i++) {
            board.lauta[6] += board.lauta[i];
            board.lauta[i] = 0;
            board.lauta[13] += board.lauta[12 - i];
            board.lauta[12 - i] = 0;
        }
        System.out.println("Peli loppui!");
        Thread.sleep(2000);
        board.printBoard();
        Thread.sleep(1000);

        if (loppupisteet < 0) {
            System.out.println("Loppupisteet: " + loppupisteet + "\nVoitit!");
        } else if (loppupisteet > 0) {
            System.out.println("Loppupisteet: " + loppupisteet + "\nHävisit!");
        } else {
            System.out.println("Tasapeli!");
        }
    }
    
}
