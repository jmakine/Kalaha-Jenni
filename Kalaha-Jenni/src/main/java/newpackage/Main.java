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
 * (myöhemmin). Sisältää pelin kannalta oleellisen minimax-algoritmin, jonka
 * avulla tietokoneen paras mahdollinen siirto kullekin tilanteelle haetaan.
 * saadaan laskettua.
 */
public class Main {  
    
    public static void main(String[] args) {
        
       // MiniMax minmax= new MiniMax();
        Board board = new Board();
        //Gui gui = new Gui(board);
        
        //int syote = gui.pelaajanLuku;
        Scanner input = new Scanner(System.in);
        
        int[] maxpisteet = new int[6];
        for (int q = 0; q < 6; q++) {
            maxpisteet[q] = -1000;
        }
        int max = -1000;

        //aloittaja arvotaan:
        //gui.pelinKulku.setText("Arvotaan aloittaja, valitse 1 tai 0: ");
        System.out.println("Arvotaan aloittaja, valitse 1 tai 0");

//--------------ALOITTAJA ARVOTAAN----------------------------
        int aloittaja;

        int pelaajanValinta = input.nextInt();//gui.pelaajanLuku;//input.nextInt();
        System.out.println("Valitsit: " + pelaajanValinta);
        //gui.pelinKulku.setText("Valitsit: " + pelaajanValinta);

        long luku = Math.round(Math.random());
        System.out.println("Kone arpoi: " + luku);
        if (luku != pelaajanValinta) {
            aloittaja = 2;
            System.out.println("Tietokone aloittaa");
        } else {
            aloittaja = 1;
            System.out.println("Peli alkaa, saat aloittaa");
        }

//-----------PELI ALKAA---------------------------------------
        //Asetetaan pelilaudalle aloitustilanne
        board.startBoard();
        board.printBoard();

//----------KONEEN ENSIMMÄINEN SIIRTO (->SAA UUDEN VUORON)----------------
//----------PELAAJAN ENSIMMÄINEN SIIRTO (JA MAHDOLLISESTI TOINENKIN)---------
        if (aloittaja == 2) {
            //optimaalinen aloitussiirto on pelaajan 3. kupista, tietokoneella indeksi 9 -> uusi vuoro
            System.out.println("Kone aloittaa kupista 3");
            board.teeSiirtoOikeasti(9, aloittaja); //muuttaa myös temp-laudan vastaamaan nykyistä lautaa
            board.printBoard();
            //tällä siirrolla saa uuden vuoron mutta ei ole mahdollista saada aloituksessa kahta uutta vuoroa  
            System.out.println("Saan uuden vuoron:");
        } //pelaaja aloittaa
        else {
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
        }

//------------------TÄSTÄ PELI JATKAA ENSIMMÄISEN TAI ENSIMMÄISTEN SIIRTOJEN JÄLKEEN-----------------
//-------------------------------VUORO JÄI TIETOKONEELLE--------------------------------------
        int vuoro = 2;

        System.out.println("Peli loppu: " + board.isGameOver());

        while (!board.isGameOver()) {

            board.uusiVuoro=true;
            
            //koneen vuoro:
            while (vuoro == 2 && board.uusiVuoro) {
                board.uusiVuoro = false;

                maxpisteet = MiniMax.minimax(2, board);
                System.out.println(maxpisteet[0] + " " + maxpisteet[1] + " " + maxpisteet[2] + " " + maxpisteet[3] + " " + maxpisteet[4] + " " + maxpisteet[5]);

                //kone siirtää siitä kiposta, jonka pisteet on korkeimmat
                int koneSiirtaa = -1;
                for (int r = 0; r < 6; r++){
                    if (maxpisteet[r] > max && board.lauta[r + 7] != 0) {
                        max = maxpisteet[r];
                        koneSiirtaa = r + 7;
                    }
                }
                max = -1000;

                System.out.println("Kone siirtää kupista " + (koneSiirtaa - 6));
                board.teeSiirtoOikeasti(koneSiirtaa, vuoro); //uusiVuoro -> true jos saa uuden vuoron, päivittää: temp.board=board?
                System.out.println("Siirron jälkeen tilanne:");
                board.printBoard();

                if (!board.uusiVuoro) {
                    vuoro = 1;
                    break;
                }

                if (board.isGameOver(2)) {
                    break;
                }
            }

            board.uusiVuoro=true;
            
//-----------------------pelaajan vuoro-----------------------
            while (vuoro == 1 && board.uusiVuoro) {

                board.uusiVuoro = false;

                System.out.println("Valitse siirtosi: ");
                int siirto = input.nextInt() - 1;

                System.out.println("Valitsit " + (siirto + 1));
                board.teeSiirtoOikeasti(siirto, 1); //päivittää parametrin uusiVuoro

                System.out.println("Pelitilanne: ");
                board.printBoard();

                if (board.isGameOver(1)) {
                    //vuoro = 2;
                    break;
                }

                if (board.uusiVuoro && !board.isGameOver()) {
                    System.out.println("Saat uuden vuoron:");
                } else {
                    vuoro = 2;
                }
            }
            
        }

        int loppupisteet = board.evaluate();
        if (loppupisteet < 0) {
            System.out.println("Loppupisteet: " +loppupisteet+ " Voitit!");
        } else if (loppupisteet > 0) {
            System.out.println("Loppupisteet: " + loppupisteet +" Hävisit!");
        } else {
            System.out.println("Tasapeli!");
        }
    }
}