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

                long startTime = System.nanoTime();

                maxpisteet = MiniMax.minimax(2, board, 0, -1000, 1000);

                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 1000000000;
                System.out.println("Minimax suorittamiseen meni " + duration + " sekuntia.");

                koneSiirtää(board, maxpisteet);

                if (board.isGameOver()) {
                    break;
                }

                if (board.uusiVuoro) {
                    System.out.println("Saan uuden vuoron");
                    Thread.sleep(1000);

                } else {
                    pelaaja = 1;
                }
            }

            board.uusiVuoro = true;

//-----------------------pelaajan vuoro-----------------------
            while (pelaaja == 1 && board.uusiVuoro) {

                board.uusiVuoro = false;

                int siirto = pelaajaValitseeSiirron(board);

                board.teeSiirtoOikeasti(siirto, 1);

                System.out.println("Pelitilanne siirron jälkeen: ");
                board.printBoard();

                if (board.isGameOver()) {
                    break;
                }

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
     * Kysyy pelaajalta luvun, joka on joko 1 tai 0 ja arpoo pelin aloittajan.
     * @return joko 1, jos arvottu aloittaja on pelaaja tai 2, jos aloittaja on tietokone
     */
    public static int arvotaanAloittaja() {

        System.out.println("Arvotaan aloittaja, valitse 1 tai 0");

        int aloittaja;
        Scanner input = new Scanner(System.in);

        int pelaajanValinta = input.nextInt();//gui.pelaajanLuku;//input.nextInt();
        //System.out.println("Valitsit: " + pelaajanValinta);

        long luku = Math.round(Math.random());
        System.out.println("Kone arpoi: " + luku);
        if (luku != pelaajanValinta) {
            aloittaja = 2;
            System.out.println("Tietokone aloittaa");
        } else {
            aloittaja = 1;
            System.out.println("Saat aloittaa");
        }
        return aloittaja;
    }

    /**
     *
     * @param board on pelilauta, jolle siirto suoritetaan
     * @param makspisteet on minimax algoritmin palauttamat pisteet kullekin mahdolliselle siirrolle
     * @throws InterruptedException
     */
    public void koneSiirtää(Board board, int[] makspisteet) throws InterruptedException {
        //kone siirtää siitä kiposta, jolle lasketut pisteet on korkeimmat
        int koneSiirtaa = -1;
        int max = -1000;
        for (int r = 0; r < 6; r++) {
            if (maxpisteet[r] > max && board.lauta[12 - r] != 0 && maxpisteet[r] != 1000) {//r + 7] != 0) {
                max = maxpisteet[r];
                koneSiirtaa = 12 - r;
            }
        }

        //tehdään valittu siirto ja tulostetaan pelitilanne
        System.out.println("Kone siirtää kupista " + (koneSiirtaa - 6));
        Thread.sleep(1000);
        board.teeSiirtoOikeasti(koneSiirtaa, 2);
        System.out.println("Pelitilanne siirron jälkeen:");
        board.printBoard();
        Thread.sleep(1000);
    }

    /**
     * Tässä varmistetaan, että pelaajan antama syöte kelpaa.
     * @param board on pelilauta, jolle siirto suoritetaan
     * @return pelilaudan indeksin, josta pelaaja siirtää
     */
    public int pelaajaValitseeSiirron(Board board) {
        System.out.println("Valitse siirtosi: ");
        int syote = input.nextInt();

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
     * Kun peli loppuu, tämä metodi laskee pelin pistetilanteen ja siirtää loput kivistä sen pelaajan mancalaan, jonka puolella kiviä vielä on. 
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
        Thread.sleep(1000);
        board.printBoard();
        Thread.sleep(1000);

        if (loppupisteet < 0) {
            System.out.println("Loppupisteet: " + loppupisteet + " Voitit!");
        } else if (loppupisteet > 0) {
            System.out.println("Loppupisteet: " + loppupisteet + " Hävisit!");
        } else {
            System.out.println("Tasapeli!");
        }
    }

}
