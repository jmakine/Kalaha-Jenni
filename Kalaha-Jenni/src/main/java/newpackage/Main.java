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
 * Main-luokassa on ohjelman suoritus. Board = pelitilanne GUI = käyttöliittymä
 * (myöhemmin)
 */
public class Main {

    //Gui gui = new Gui();
    public static void main(String[] args) {

        //kayttis myöhemmin;
        Scanner input = new Scanner(System.in);

        //aloittaja arvotaan:
        System.out.println("Arvotaan aloittaja");
        System.out.println("Valitse numero 0 tai 1");

        int aloittaja;
        //pelaaja valitsee 0 tai 1
        int pelaajanValinta = input.nextInt();
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
        //Asetetaan pelilaudalle aloitustilanne
        Board board = new Board();
        board.startBoard();
        //laudan-temp-lauta asetetaan vastaamaan varsinaista lautaa      
        //20.9 pois board.temp = board;

        board.printBoard();
        //int vuoro = 0;

        //jos tietokone aloittaa
        if (aloittaja == 2) {
            //optimaalinen aloitussiirto on pelaajan 3. kupista, tietokoneella indeksi 9 -> uusi vuoro
            System.out.println("Kone aloittaa kupista 3");
            board.teeSiirtoOikeasti(9, aloittaja); //muuttaa myös temp-laudan vastaamaan nykyistä lautaa
            board.printBoard();
            //tällä siirrolla saa uuden vuoron mutta ei ole mahdollista saada aloituksessa kahta uutta vuoroa  
            System.out.println("Saan uuden vuoron:");
            /* Board tmp = board;
            board.temp = tmp;*/

        } else if (aloittaja == 1) {
            System.out.println("Valitse siirto kupeista 1-6:");
            int ekaSiirto = input.nextInt() - 1;
            board.teeSiirtoOikeasti(ekaSiirto, aloittaja);
            board.printBoard();

            if (ekaSiirto == 2) {
                System.out.println("Saat uuden vuoron:");
                System.out.println("Valitse siirto kupeista 1-6:");
                int tokaSiirto = input.nextInt() - 1;
                board.teeSiirtoOikeasti(tokaSiirto, aloittaja);
                board.printBoard();
            }
            /* Board tmp = board;
            board.temp = tmp;*/
        }

        int vuoro = 2;

        System.out.println("Peli loppu: " + board.isGameOver());

        while (!board.isGameOver()) {

            //koneen vuoro:
            while (vuoro == 2) {

                board.uusiVuoro = false;
                int[] pisteet = minimax(2, board);
                System.out.println(pisteet[0] + "" + pisteet[1] + "" + pisteet[2] + "" + pisteet[3] + "" + pisteet[4] + "" + pisteet[5]);
                //kone siirtää siitä kiposta, jonka pisteet on korkeimmat
                int koneSiirtaa = Integer.MIN_VALUE;

                for (int ind = 0; ind < 6; ind++) {
                    if (pisteet[ind] > koneSiirtaa) {
                        koneSiirtaa = ind + 6;
                    }
                }

                System.out.println("Kone siirtää kupista " + koneSiirtaa);
                board.teeSiirtoOikeasti(koneSiirtaa, 2); //uusiVuoro -> true jos saa uuden vuoron, päivittää: temp.board=board
                System.out.println("Siirron jälkeen tilanne:");
                board.printBoard();

                if (!board.uusiVuoro) {
                    vuoro = 1;
                }
            }

            //pelaajan vuoro:
            while (vuoro == 1) {
                board.uusiVuoro = false;

                System.out.println("Valitse siirtosi: ");
                int siirto = input.nextInt() - 1;

                System.out.println("Valitsit " + siirto);
                board.teeSiirtoOikeasti(siirto, 1); //päivittää parametrin uusiVuoro
                System.out.println("Pelitilanne: ");
                board.printBoard();

                if (board.uusiVuoro) {
                    System.out.println("Saat uuden vuoron:");
                } else {
                    vuoro = 2;
                }
            }
        }

        int loppupisteet = board.evaluate();
        if (loppupisteet < 0) {
            System.out.println("Peli päättyi, voitit!");
        } else if (loppupisteet > 0) {
            System.out.println("Hävisit!");
        } else {
            System.out.println("Tasapeli!");
        }
    }

    //---------------------MIN-MAX-------------------------------------------------------------------
    /**
     * Minimax-algoritmi palauttaa pisteet kullekkin mahdolliselle siirrolle
     * lähtötilanteesta.
     *
     * @param pelaaja on joko 1=ihminen tai 2=tietokone
     * @param lauta on se pelilauta, jolle minimax suoritetaan.
     * @return 6-paikkainen pistetaulu.
     */
    public static int[] minimax(int pelaaja, Board lauta) {

        Board[] aliPuut = new Board[6];
        lauta.uusiVuoro = false;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        
        int[] pisteet = new int[6];
        int[] lisapisteet = new int[6]; //!!! not used
        int i, j;
        
        int[] minimit = new int[6];
        for (int k = 0; k < 6; k++) {
            minimit[k] = Integer.MAX_VALUE;
        }

        for (i = 0; i < 6; i++) {
            aliPuut[i] = new Board();
        }

        if (pelaaja == 1) { 

            //kopioidaan pelitilanne alipuihin
            for (i = 0; i < 6; i++) {
                aliPuut[i] = lauta;
            }

            //tutkitaan jokainen mahdollinen siirto
            for (i = 0; i < 6; i++) {
                
                List<Integer> aliPuunSiirrot = aliPuut[i].mahdollisetSiirrot(pelaaja); 

                for (int l = 0; l < aliPuunSiirrot.size(); l++) {
                    int siirto = aliPuunSiirrot.get(l);
                    aliPuut[i].teeSiirtoLeikisti(siirto, pelaaja);
                    
                    if (aliPuut[i].temp.uusiVuoro != true) {
                        pisteet[i] = aliPuut[i].temp.evaluate(); 
                    } 
                    
                    else if (aliPuut[i].temp.uusiVuoro == true) {
                        lisapisteet = minimax(pelaaja, aliPuut[i].temp);

                        //uusintasiirron jälkeen etsitään minimi
                        for (int k = 0; k < 6; k++) {
                            if (lisapisteet[k] < min) {
                                min = lisapisteet[k];
                            }
                            pisteet[i] = min;
                        }

                    }
                }
                min = Integer.MAX_VALUE;
            }

            return pisteet;
        }

//-----------TIETOKONEEN SIIRTO-----------------
        if (pelaaja == 2 && !lauta.isGameOver()) {

            for (i = 0; i < 6; i++) {
                aliPuut[i] = lauta;
            }

            //katsotaan joka siirto
            for (i = 0; i < 6; i++) {

                List<Integer> puunSiirrot = aliPuut[i].mahdollisetSiirrot(pelaaja); 
                
                for (int l = 0; l < puunSiirrot.size(); l++) {

                    int siirto = puunSiirrot.get(l);
                    aliPuut[i].teeSiirtoLeikisti(siirto, pelaaja);

                    if (aliPuut[i].temp.uusiVuoro != true) {
                        minimit = minimax(pelaaja - 1, aliPuut[i].temp); 

                        //etsitään minimi mahdollisista pelaajan siirroista
                        for (int k = 0; k < 6; k++) {
                            if (minimit[k] > pisteet[k]) {
                                minimit[k] = pisteet[k];
                            }
                        }

                    } else if (aliPuut[i].temp.uusiVuoro == true) {
                        lisapisteet = minimax(pelaaja, aliPuut[i].temp);//palauttaa uuden vuoron jälkeisen pistetaulun

                        for (int k = 0; k < 6; k++) {
                            if (lisapisteet[k] > max) {
                                max = lisapisteet[k];
                            }

                            minimit[k] = max;
                        }
                    }

                    max = Integer.MIN_VALUE;

                }
                return minimit;
            }
        }
        
        if (lauta.isGameOver() && pelaaja == 2) {            
            return pisteet;
        } else {
            return minimit;
        }
    }

}
