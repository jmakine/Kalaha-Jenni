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

    //Gui gui = new Gui();käyttis myöhemmin
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        //aloittaja arvotaan:
        System.out.println("Arvotaan aloittaja");
        System.out.println("Valitse numero 0 tai 1");

//--------------ALOITTAJA ARVOTAAN----------------------------
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

//-----------PELI ALKAA---------------------------------------
        //Asetetaan pelilaudalle aloitustilanne
        Board board = new Board();
        board.startBoard();
        board.printBoard();

//----------KONEEN ENSIMMÄINEN SIIRTO (->SAA UUDEN VUORON)----------------
//----------PELAAJAN ENSIMMÄINEN SIIRTO (JA MAHDOLLISESTI TOINENKIN)---------
        //jos tietokone aloittaa
        if (aloittaja == 2) {
            //optimaalinen aloitussiirto on pelaajan 3. kupista, tietokoneella indeksi 9 -> uusi vuoro
            System.out.println("Kone aloittaa kupista 3");
            board.teeSiirtoOikeasti(9, aloittaja); //muuttaa myös temp-laudan vastaamaan nykyistä lautaa
            board.printBoard();
            //tällä siirrolla saa uuden vuoron mutta ei ole mahdollista saada aloituksessa kahta uutta vuoroa  
            System.out.println("Saan uuden vuoron:");
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
        }

//------------------TÄSTÄ PELI JATKAA ENSIMMÄISEN TAI ENSIMMÄISTEN SIIRTOJEN JÄLKEEN-----------------
//-------------------------------VUORO JÄI TIETOKONEELLE--------------------------------------
        int vuoro = 2;

        System.out.println("Peli loppu: " + board.isGameOver());

        while (!board.isGameOver()) {

            //koneen vuoro:
            while (vuoro == 2 && board.uusiVuoro) {
                board.uusiVuoro = false;

                // if (board.isGameOver()) {
                //    break;
                //} else { //peli ei ole ohi
                int[] maxpisteet = minimax(2, board, 0);
                System.out.println(maxpisteet[0] + "" + maxpisteet[1] + "" + maxpisteet[2] + "" + maxpisteet[3] + "" + maxpisteet[4] + "" + maxpisteet[5]);

                //kone siirtää siitä kiposta, jonka pisteet on korkeimmat
                int koneSiirtaa = -1;// = Integer.MIN_VALUE;
                int max=Integer.MIN_VALUE;
                
                for (int ind = 0; ind < 6; ind++) {
                    if (maxpisteet[ind] > max) {
                        max = maxpisteet[ind];
                        koneSiirtaa=ind+7;
                    }
                }
                

                System.out.println("Kone siirtää kupista " + koneSiirtaa);
                board.teeSiirtoOikeasti(koneSiirtaa, 2); //uusiVuoro -> true jos saa uuden vuoron, päivittää: temp.board=board
                System.out.println("Siirron jälkeen tilanne:");
                board.printBoard();

                if (!board.uusiVuoro) {
                    vuoro = 1;
                }
                if(board.isGameOver()){
                    break;
                }
            }

//-----------------------pelaajan vuoro-----------------------
            while (vuoro == 1 && !board.isGameOver()) {

                System.out.println("Valitse siirtosi: ");
                int siirto = input.nextInt() - 1;

                System.out.println("Valitsit " + siirto);
                board.teeSiirtoOikeasti(siirto, 1); //päivittää parametrin uusiVuoro
                if (board.isGameOver()) {
                    break;
                }
                System.out.println("Pelitilanne: ");
                board.printBoard();

                if (board.uusiVuoro) {
                    System.out.println("Saat uuden vuoron:");
                } else {
                    vuoro = 2;
                }

            }
            if(board.isGameOver()){
                break;
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
    public static int[] minimax(int pelaaja, Board lauta, int deapth) {

        Board[] aliPuut = new Board[6];
        lauta.uusiVuoro = false;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        int[] pisteet = new int[6];
        int[] lisapisteet = new int[6]; //!!! not used
        int i, j;

        for (i = 0; i < 6; i++) {
            aliPuut[i] = new Board();
        }

//------------------------------------PELAAJA IHMINEN-----------------
        if (pelaaja == 1) {

            int[] minimipisteet = new int[6];
            for (int k = 0; k < 6; k++) {
                minimipisteet[k] = Integer.MAX_VALUE;
            }

            //kopioidaan pelitilanne alipuihin
            for (i = 0; i < 6; i++) {
                Board temp=lauta;
                aliPuut[i] = temp;
            }

            //tutkitaan jokainen mahdollinen siirto
            for (i = 0; i < 6; i++) {

                int[] aliPuunSiirrot = aliPuut[i].mahdollisetSiirrot(pelaaja); //=0 jos siirto ei ole sallittu

                int siirto = aliPuunSiirrot[i];

                if (siirto != 0) { //siirto sallittu
                    aliPuut[i].teeSiirtoLeikisti(i, pelaaja);

                    if (!aliPuut[i].uusiVuoro && deapth < 5 && !aliPuut[i].isGameOver()) {

                        pisteet = minimax(pelaaja + 1, aliPuut[i], deapth + 1); //tietokone maksimoi, pelaaja minimoi
                        aliPuut[i].peruSiirto();
                        
                            //etsitään palautetuista pisteistä pienin
                            for (int k = 0; k < 6; k++) {
                                if (pisteet[k] < min) {
                                    min = pisteet[k];
                                }
                                pisteet[i] = min;
                            }
                                                
                    } 
                    else {
                        while(aliPuut[i].uusiVuoro && !aliPuut[i].isGameOver() && deapth < 5) {

                        lisapisteet = minimax(pelaaja, aliPuut[i], deapth + 1);

                        //uusintasiirron jälkeen etsitään maksimi
                        for (int k = 0; k < 6; k++) {
                            if (lisapisteet[k] < min) {
                                min = lisapisteet[k];
                            }
                            pisteet[i] = min;
                        }
                        }
                     
                    if (aliPuut[i].isGameOver() || deapth>4) {
                            pisteet[i] = aliPuut[i].evaluate();                       
                    }

                }
                min = Integer.MAX_VALUE;

            }

            return pisteet;
        }

//-----------TIETOKONEEN SIIRTO-----------------
        if (pelaaja == 2 && !lauta.isGameOver() && deapth<5) {

            for (i = 0; i < 6; i++) {
                aliPuut[i] = lauta;
            }

            int[] minimit = new int[6];
            for (int k = 0; k < 6; k++) {
                minimit[k] = Integer.MAX_VALUE;
            }

            //katsotaan kaikki mahdolliset pelin etenemisvaihtoehdot
            for (i = 0; i < 6; i++) {

                int[] puunSiirrot = aliPuut[i].mahdollisetSiirrot(pelaaja); //[0, 1, 0, 1, 1, 1]

                //for (int l = 7; l < 14; l++) {
                int siirto = puunSiirrot[i]; //int[6], jonka paikan arvo on 0, jos siirto tästä indeksistä ei ole sallittu ja 1 jos on sallittu

                if (siirto != 0) {
                    aliPuut[i].teeSiirtoLeikisti(siirto, pelaaja);

                    if (!aliPuut[i].uusiVuoro) {

                        if (!aliPuut[i].isGameOver() && deapth < 5) {

                            minimit = minimax(pelaaja - 1, aliPuut[i], deapth + 1);
                            aliPuut[i].peruSiirto();
                            //etsitään suurin palautetuista minimeistä
                            for (int k = 0; k < 6; k++) {
                                if (minimit[k] < max) {
                                    max = minimit[k];
                                }
                            } pisteet[i]=max;
                        } //jos peli on ohi tämän vuoron jälkeen, lasketaan sen arvo
                        else {
                            pisteet[i] = aliPuut[i].evaluate();
                        }
                    } //jos pelaaja saa siirrolla uuden vuoron
                    else if (aliPuut[i].uusiVuoro == true && deapth < 5) {

                        if (!aliPuut[i].isGameOver()) {
                            lisapisteet = minimax(pelaaja, aliPuut[i], deapth + 1);
                            aliPuut[i].peruSiirto();
                            //haetaan näistä suurin
                            for (int k = 0; k < 6; k++) {
                                if (lisapisteet[k] < min) {
                                    min = lisapisteet[k];
                                }
                                pisteet[i] = min;
                            }

                        } else {
                            pisteet[i] = aliPuut[i].evaluate();
                        }
                    }

                    max = Integer.MIN_VALUE;

                }
                
            }
        }
        return pisteet;
    }
return pisteet;
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
/*public static int[] minimax(int pelaaja, Board lauta) {

        Board[] aliPuut = new Board[6];
        lauta.uusiVuoro = false;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        int[] pisteet = new int[6];
        int[] lisapisteet = new int[6]; //!!! not used
        int i, j;

        for (i = 0; i < 6; i++) {
            aliPuut[i] = new Board();
        }

        if (pelaaja == 1) {

            int[] maksimit = new int[6];
            for (int k = 0; k < 6; k++) {
                maksimit[k] = Integer.MIN_VALUE;
            }

            //kopioidaan pelitilanne alipuihin
            for (i = 0; i < 6; i++) {
                aliPuut[i] = lauta;
            }

            //tutkitaan jokainen mahdollinen siirto
            for (i = 0; i < 6; i++) {

                int[] aliPuunSiirrot = aliPuut[i].mahdollisetSiirrot(pelaaja);

                int siirto = aliPuunSiirrot[i];

                if (siirto != 0) {
                    aliPuut[i].teeSiirtoLeikisti(i, pelaaja);

                    if (aliPuut[i].temp.uusiVuoro != true) {

                        if (!aliPuut[i].temp.isGameOver()) {
                            maksimit = minimax(pelaaja - 1, aliPuut[i].temp);

                            //etsitään palautetuista maksimeista pienin
                            for (int k = 0; k < 6; k++) {
                                if (maksimit[k] < pisteet[k]) {
                                    pisteet[k] = maksimit[k];
                                }
                            }

                        } else {
                            pisteet[i] = aliPuut[i].temp.evaluate();
                        }

                    } else if (aliPuut[i].temp.uusiVuoro == true && !aliPuut[i].temp.isGameOver()) {

                        lisapisteet = minimax(pelaaja, aliPuut[i].temp);

                        //uusintasiirron jälkeen etsitään minimi
                        for (int k = 0; k < 6; k++) {
                            if (lisapisteet[k] < min) {
                                min = lisapisteet[k];
                            }
                            pisteet[i] = min;
                        }
                    } else {
                        pisteet[i] = aliPuut[i].temp.evaluate();
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

            int[] minimit = new int[6];
            for (int k = 0; k < 6; k++) {
                minimit[k] = Integer.MAX_VALUE;
            }

            //katsotaan kaikki mahdolliset pelin etenemisvaihtoehdot
            for (i = 0; i < 6; i++) {

                int[] puunSiirrot = aliPuut[i].mahdollisetSiirrot(pelaaja); //[0, 1, 0, 1, 1, 1]

                //for (int l = 7; l < 14; l++) {
                int siirto = puunSiirrot[i]; //int[6], jonka paikan arvo on 0, jos siirto tästä indeksistä ei ole sallittu ja 1 jos on sallittu

                if (siirto != 0) {
                    aliPuut[i].teeSiirtoLeikisti(siirto, pelaaja);

                    if (aliPuut[i].temp.uusiVuoro != true) {

                        if (!aliPuut[i].temp.isGameOver()) {

                            minimit = minimax(pelaaja - 1, aliPuut[i].temp);
                            //etsitään suurin palautetuista minimeistä
                            for (int k = 0; k < 6; k++) {
                                if (minimit[k] > pisteet[k]) {
                                    pisteet[k] = minimit[k];
                                }
                            }
                        } //jos peli on ohi tämän vuoron jälkeen, lasketaan sen arvo
                        else {
                            pisteet[i] = aliPuut[i].temp.evaluate();
                        }
                    } //jos pelaaja saa siirrolla uuden vuoron
                    else if (aliPuut[i].temp.uusiVuoro == true) {

                        if (!aliPuut[i].temp.isGameOver()) {
                            lisapisteet = minimax(pelaaja, aliPuut[i].temp);
                            //haetaan näistä suurin
                            for (int k = 0; k < 6; k++) {
                                if (lisapisteet[k] > max) {
                                    max = lisapisteet[k];
                                }

                                pisteet[i] = max;
                            }

                        } else {
                            pisteet[i] = aliPuut[i].temp.evaluate();
                        }
                    }

                    max = Integer.MIN_VALUE;

                }
            }
        }
        return pisteet;
    }
 */
