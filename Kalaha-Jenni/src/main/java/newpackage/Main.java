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
        //Gui gui = new Gui(board);

        //aloittaja arvotaan:
        System.out.println("Arvotaan aloittaja");
        System.out.println("Valitse numero 0 tai 1");

//--------------ALOITTAJA ARVOTAAN----------------------------
        int aloittaja;        
       
        int pelaajanValinta = input.nextInt();        
        System.out.println("Valitsit: " + pelaajanValinta);
           
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

            //koneen vuoro:
            while (vuoro == 2 ) {
                board.uusiVuoro = false;

                int[] maxpisteet = minimax(2, board);
                System.out.println(maxpisteet[0] + " " + maxpisteet[1] + " " + maxpisteet[2] + " " + maxpisteet[3] + " " + maxpisteet[4] + " " + maxpisteet[5]);

                //kone siirtää siitä kiposta, jonka pisteet on korkeimmat
                int koneSiirtaa = -1;
                int max = -1000;

                for (int ind = 0; ind < 6; ind++) {
                    if (maxpisteet[ind] > max) {
                        max = maxpisteet[ind];
                        koneSiirtaa = ind + 7;
                    }
                }

                System.out.println("Kone siirtää kupista " + (koneSiirtaa-6));
                board.teeSiirtoOikeasti(koneSiirtaa, vuoro); //uusiVuoro -> true jos saa uuden vuoron, päivittää: temp.board=board
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

//-----------------------pelaajan vuoro-----------------------
            while (vuoro == 1){
                
                board.uusiVuoro = false;

                System.out.println("Valitse siirtosi: ");
                int siirto = input.nextInt() - 1;

                System.out.println("Valitsit " + (siirto+1));
                board.teeSiirtoOikeasti(siirto, 1); //päivittää parametrin uusiVuoro

                System.out.println("Pelitilanne: ");
                board.printBoard();

                if (board.isGameOver(1)) { 
                    break;
                }

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

        int[] maksimit = new int[6];
        int[] minimit = new int[6];
        int[] lisaminimit = new int[6];
        int[] lisamaksimit = new int[6];
        int i, k;
        Board[] alipuut = new Board[6];
        
        //MITEN SAAN KOPIOITUA KÄSITELTÄVÄN LAUDAN KUUTEEN ERI BOARD-OLIOON?
        //SITEN ETTÄ ALKUPERÄINEN BOARD EI MUUTU KUN KOPIOITA KÄSITELLÄÄN
        //BOARD2=KOPIO ALKUPERÄISESTÄ
        //BOARD2.muuta() -> BOARD2!=BOARD, !.equals()

//------------------------------------PELAAJA IHMINEN-----------------  
        if (pelaaja == 1 && !lauta.isGameOver()) {

            //alustetaan minimit -> 1000
            for (k = 0; k < 6; k++) {
                minimit[k] = 1000;
            }

            for (k = 0; k < 6; k++) {
                lisaminimit[k] = 1000;
            }

            //kopioidaan pelitilanne alipuihin
            for (i = 0; i < 6; i++) {
                //Board temp=lauta;
                aliPuut[i] = lauta;
            }

            //tutkitaan jokainen mahdollinen siirto
            for (i = 0; i < 6; i++) {

                int[] aliPuunSiirrot = aliPuut[i].mahdollisetSiirrot(pelaaja); //[0,0,1,1,0,1], ind 0->5

                int siirto = aliPuunSiirrot[i];

                if (siirto == 1) { //siirto sallittu

                    aliPuut[i].teeSiirtoLeikisti(i, pelaaja); //päivittää aliPuut[i].temp -lautaa

                    //jos siirrolla ei saa uutta vuoroa, eikä peli ole ohi, siirtyy vuoro toiselle pelaajalle
                    if (!aliPuut[i].temp.uusiVuoro && !aliPuut[i].temp.isGameOver(pelaaja+1)) {

                        maksimit = minimax(pelaaja + 1, aliPuut[i].temp); //tietokoneen vuoro palauttaa maksimit

                        //pelaaja pyrkii etsimään palautetuista pisteistä pienimmän
                        for (k = 0; k < 6; k++) {
                            if (maksimit[k] < minimit[i]) {
                                minimit[i] = maksimit[k];
                            }
                        }

                    }
                    //jos pelaaja saa uuden siirron, eikä peli ole ohi

                    while (aliPuut[i].temp.uusiVuoro && !aliPuut[i].temp.isGameOver(pelaaja)) { //niin kauan, kun pelaaja saa uuden vuoron

                        lisaminimit = minimax(pelaaja, aliPuut[i].temp); //pelaaja päivittää maksimitauluun uuden vuoron jälkeiset pisteet (minimit)

                        for (k = 0; k < 6; k++) {
                            if (lisaminimit[k] < minimit[k]) { //jos lisäkierroksen jälkeen pistetilanne on pelaajan kannalta parempi, päivitetään se maksimeihin
                                minimit[k] = lisaminimit[k];
                            }
                        }
                    }

                    if (aliPuut[i].temp.isGameOver()) {
                        minimit[i] = aliPuut[i].temp.evaluate();

                    }

                } else if (siirto == 0) {
                    minimit[i] = aliPuut[i].temp.evaluate();
                }
            }
            return minimit;
        } 
//-----------TIETOKONEEN SIIRTO-----------------
        else if (pelaaja == 2 && !lauta.isGameOver(2)) { //STACK OVERFLOW

            for (i = 0; i < 6; i++) {
                aliPuut[i] = lauta;
            }

            for (k = 0; k < 6; k++) {
                maksimit[k] = -1000;
            }

            for (k = 0; k < 6; k++) {
                lisamaksimit[k] = -1000;
            }

            //katsotaan kaikki mahdolliset pelin etenemisvaihtoehdot
            for (i = 0; i < 6; i++) {

                int[] puunSiirrot = aliPuut[i].mahdollisetSiirrot(pelaaja); //[0, 1, 0, 1, 1, 1]

                int siirto = puunSiirrot[i]; //int[6], jonka paikan arvo on 0, jos siirto tästä indeksistä ei ole sallittu ja 1 jos on sallittu

                if (siirto == 1) {

                    aliPuut[i].teeSiirtoLeikisti(i + 7, pelaaja);

                    if (!aliPuut[i].temp.uusiVuoro && !aliPuut[i].temp.isGameOver(pelaaja-1)) {

                        //NULLPOINTER !!! MIKÄ TÄSSÄ ON PIELESSÄ    
                        //minimit = minimax(pelaaja - 1, aliPuut[i].temp); //ihmisen minimax palauttaa minimipisteet kullekkin siirrolle

                        //kone etsii suurimman palautetuista minimeistä
                        for (k = 0; k < 6; k++) {
                            if (minimit[k] > maksimit[i]) { //max=-1000
                                maksimit[i] = minimit[k];
                            }
                        }

                    }

                    while (aliPuut[i].temp.uusiVuoro == true && !aliPuut[i].temp.isGameOver(pelaaja)) {

                        lisamaksimit = minimax(pelaaja, aliPuut[i].temp);

                        //haetaan näistä suurin
                        for (k = 0; k < 6; k++) {
                            if (lisamaksimit[k] > maksimit[i]) {
                                maksimit[i] = lisamaksimit[k];
                            }
                        }
                    }

                    if (aliPuut[i].temp.isGameOver(pelaaja)) { //jos peli on ohi tämän vuoron jälkeen, lasketaan sen arvo                       
                        maksimit[i] = aliPuut[i].temp.evaluate();
                        
                    }
                } //siirto suoritettu

                if (siirto == 0) {
                    maksimit[i] = aliPuut[i].evaluate();
                }

            } //for loppuu, eli kaikki siirrot käyty läpi              
            return maksimit;
        }

        if (lauta.isGameOver(1)) {
            return minimit;
        } else {
            return maksimit;
        }

    }//MINIMAX
    
 

} //CLASS MAIN
