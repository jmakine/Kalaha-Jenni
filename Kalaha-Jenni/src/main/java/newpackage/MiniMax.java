package newpackage;

import java.util.Arrays;
//import newpackage.Board;

public class MiniMax {

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
        int[] lisamaksimit;// = new int[6];
        int pelaajanLisapisteet = 1000;
        int koneenLisapisteet = -1000;
        int i, k;

        for (i = 0; i < 6; i++) {
            aliPuut[i] = new Board();
        }

//------------------------------------PELAAJA IHMINEN-----------------  
        if (pelaaja == 1) {//) && !lauta.isGameOver()) {

            for (i = 0; i < 6; i++) {

                for (int j = 0; j < 14; j++) {
                    aliPuut[i].lauta[j] = lauta.lauta[j];
                }

                int[] aliPuunSiirrot = aliPuut[i].mahdollisetSiirrot(pelaaja); //[0,0,1,1,0,1], ind 0->5

                int siirto = aliPuunSiirrot[i];

                if (siirto == 1) { //siirto sallittu

                    aliPuut[i].teeSiirtoLeikisti(i, pelaaja); //päivittää aliPuut[i].temp -lautaa

                    //jos siirrolla ei saa uutta vuoroa //, eikä peli ole ohi, siirtyy vuoro toiselle pelaajalle
                    if (!aliPuut[i].temp.uusiVuoro) {// && !aliPuut[i].temp.isGameOver(pelaaja+1)) {
                        maksimit[i] = aliPuut[i].temp.evaluate();
                    }

                    //jos pelaaja saa uuden siirron, eikä peli ole ohi
                    while (aliPuut[i].temp.uusiVuoro && !aliPuut[i].temp.isGameOver(pelaaja)) { //niin kauan, kun pelaaja saa uuden vuoron

                        lisamaksimit = minimax(pelaaja, aliPuut[i].temp); //pelaaja päivittää maksimitauluun uuden vuoron jälkeiset pisteet (minimit)

                        for (int j = 0; j < 6; j++) {
                            if (lisamaksimit[j] < pelaajanLisapisteet && aliPuut[i].temp.lauta[j] != 0) {//lisamaksimit[j]<lisamin && aliPuut[i].temp.lauta[j] != 0) {
                                pelaajanLisapisteet = lisamaksimit[j];
                            }
                        }
                        maksimit[i] = pelaajanLisapisteet;

                    }
                    pelaajanLisapisteet = 1000;
                }
            }
            return maksimit;
        }
        
//-----------------------TIETOKONEEN SIIRTO-------------------------------------
        
        else if (pelaaja == 2){// && !lauta.isGameOver(2)) {

            //kopioidaan käsiteltävän laudan pelitilanne kuuteen alipuuhun
            for (i = 0; i < 6; i++) {
                for (int j = 0; j < 14; j++) {
                    aliPuut[i].lauta[j] = lauta.lauta[j];
                }
            }

            int[] minimit = new int[6];
            for (int q = 0; q < 6; q++) {
                minimit[q] = 1000;
            }

            //katsotaan kaikki mahdolliset pelin etenemisvaihtoehdot
            for (i = 0; i < 6; i++) {

                int[] puunSiirrot = aliPuut[i].mahdollisetSiirrot(pelaaja); //[0, 1, 0, 1, 1, 1]

                int siirto = puunSiirrot[i]; //int[6], jonka paikan arvo on 0, jos siirto tästä indeksistä ei ole sallittu ja 1 jos on sallittu

                if (siirto == 1) {

                    aliPuut[i].teeSiirtoLeikisti(i + 7, pelaaja);

                    if (!aliPuut[i].temp.uusiVuoro) {
                        
                        //maksimit
                        minimit = minimax(pelaaja - 1, aliPuut[i].temp); //ihmisen minimax palauttaa minimipisteet kullekkin siirrolle

                        
                        //kone etsii suurimman palautetuista minimeistä
                       for (k = 0; k < 6; k++) {
                            if (minimit[k] > maksimit[i]) {
                                maksimit[i] =minimit[k];
                            }
                        }//??
                       maksimit[i]=aliPuut[i].temp.evaluate();
                        
                    } else if (aliPuut[i].temp.uusiVuoro && !aliPuut[i].temp.isGameOver(pelaaja)) {

                        lisamaksimit = minimax(pelaaja, aliPuut[i].temp);

                        if (aliPuut[i].temp.isGameOver()) {
                            minimit[i] = 1000;
                        }

                        for (int j = 0; j < 6; j++) {
                            if (lisamaksimit[j] > koneenLisapisteet && aliPuut[i].lauta[j + 7] != 0 && lauta.isGameOver()) {
                                koneenLisapisteet = lisamaksimit[j];
                            }
                        }
                        minimit[i] = koneenLisapisteet;
                    }
                }
                koneenLisapisteet = -1000;
            }
            return minimit;
        }

        return maksimit;

    }

    public static void main(String[] args) {
        Board b = new Board();
        b.startBoard();
        b.lauta[0] = 5;
        b.lauta[8] = 7;
        b.lauta[1] = 0;
        int pelaaja = 1;
        int[] human, machine;//=new int[6];
        human = minimax(pelaaja, b);
        System.out.println(Arrays.toString(human));
        machine = minimax(pelaaja + 1, b);
        System.out.println(Arrays.toString(machine));

        Board c = new Board();
        c.startBoard();
        for (int i = 0; i < 14; i++) {
            c.lauta[i] = 0;
        }
        c.lauta[2] = 4;
        c.lauta[8] = 5;
        //int p=1;
        //int[] human, machine;//=new int[6];
        human = minimax(pelaaja, c);
        System.out.println(Arrays.toString(human));
        machine = minimax(pelaaja + 1, c);
        System.out.println(Arrays.toString(machine));

    }

} //CLASS 

