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
        
        Board board = new Board();
       
        Scanner input = new Scanner(System.in);
        
        int[] maxpisteet = new int[6];
       
        //aloittaja arvotaan:
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
        int pelaaja=aloittaja;

//-----------PELI ALKAA---------------------------------------
        //Asetetaan pelilaudalle aloitustilanne
        board.startBoard();
        board.printBoard();

//----------ENSIMMÄINEN SIIRTO (JA MAHDOLLISESTI TOINENKIN)---------
       
        /*if (aloittaja == 2) {
            //aloittajan kannattaa aloittaa kupista 3
            System.out.println("Kone aloittaa kupista 3");
            board.teeSiirtoOikeasti(9, aloittaja); 
            board.printBoard();
            //tällä siirrolla saa uuden vuoron
            System.out.println("Saan uuden vuoron:");
        } 
        else { //
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
        }*/

//------------------TÄSTÄ PELI JATKAA ENSIMMÄISEN TAI ENSIMMÄISTEN SIIRTOJEN JÄLKEEN-----------------
//-------------------------------VUORO JÄI TIETOKONEELLE--------------------------------------
     //   int vuoro = 2;

        while (!board.isGameOver()) {

            board.uusiVuoro=true;
            
            while (pelaaja == 2 && board.uusiVuoro) {
                board.uusiVuoro = false;

                maxpisteet = MiniMax.minimax(2, board, 0, -1000, 1000); //ensimmäinen indeksi maxpisteissä on laudan indeksin 12 lasketut pisteet
                //System.out.println("\tMinimax palauttaa maksimipisteet "+maxpisteet[0] + " " + maxpisteet[1] + " " + maxpisteet[2] + " " + maxpisteet[3] + " " + maxpisteet[4] + " " + maxpisteet[5]);

                //kone siirtää siitä kiposta, jonka pisteet on korkeimmat, ja joka ei ole tyhjä
                int koneSiirtaa = -1;
                int max=-1000;
                for (int r = 0; r < 6; r++){
                    if (maxpisteet[r] > max && board.lauta[12-r]!=0 && maxpisteet[r]!=1000){//r + 7] != 0) {
                        max = maxpisteet[r];
                        koneSiirtaa = 12-r;
                    }
                }
                
                System.out.println("Kone siirtää kupista " + (koneSiirtaa - 6));
                board.teeSiirtoOikeasti(koneSiirtaa, pelaaja); //uusiVuoro -> true jos saa uuden vuoron, päivittää: temp.board=board?
                System.out.println("Siirron jälkeen tilanne:");
                board.printBoard();
                
                if (board.isGameOver()) {
                    break;
                }
                
                if(board.uusiVuoro){
                    System.out.println("Saan uuden vuoron");
                } else{ 
                    pelaaja = 1;                    
                } 
            }

            board.uusiVuoro=true;
            
//-----------------------pelaajan vuoro-----------------------
            while (pelaaja == 1 && board.uusiVuoro) {

                board.uusiVuoro = false;

                System.out.println("Valitse siirtosi: ");
                int siirto = input.nextInt() - 1;

                //System.out.println("Valitsit " + (siirto + 1));
                while((siirto+1)>6 || (siirto+1)<0){                    
                    System.out.println("Voit valita kipoista 1-6.\nValitse uudelleen:");
                    siirto=input.nextInt()-1;
                }
                while(board.lauta[siirto] == 0) {
                    System.out.println("Et voi tehdä siirtoa tyhjästä kiposta.\nValitse uudelleen.");
                    siirto = input.nextInt()-1;
                }
                
                board.teeSiirtoOikeasti(siirto, 1); //päivittää parametrin uusiVuoro

                System.out.println("Pelitilanne: ");
                board.printBoard(); //päivittää laudan pelitilanteen -> string
                //System.out.println(board.pelitilanne);

                if (board.isGameOver()) {
                    //vuoro = 2;
                    break;
                }

                if (board.uusiVuoro) {
                    System.out.println("Saat uuden vuoron:");
                } else {
                    pelaaja = 2;
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