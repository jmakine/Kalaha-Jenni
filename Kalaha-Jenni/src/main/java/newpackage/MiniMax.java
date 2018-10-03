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
        int[] minimit = new int[6];

        int[] lisamaksimit;// = new int[6];
        int[] lisaminimit;// = new int[6];

        int i, k;

        for (i = 0; i < 6; i++) {
            aliPuut[i] = new Board();
        }

        /*        if (lauta.isGameOver() && pelaaja == 1) {
            for (i = 0; i < 6; i++) {
                minimit[i] = lauta.evaluate();
            }
            //return minimit;
        } 
        else if (lauta.isGameOver() && pelaaja == 0) {
            for (i = 0; i < 6; i++) {
                maksimit[i] = lauta.evaluate();
            }
            //return maksimit;
        }
         */
//------------------------------------PELAAJA IHMINEN-----------------  
        if (pelaaja == 1) {

            int minLista[] = new int[6];
            for (int q = 0; q < 6; q++) {
                minLista[q] = 1000;
            }

            for (i = 0; i < 6; i++) {

                for (int j = 0; j < 14; j++) {
                    aliPuut[i].lauta[j] = lauta.lauta[j];
                }

                //aliPuut[i].temp=aliPuut[i]; //tämä on jo sama, board konstruktorissa kopioitu
                int[] aliPuunSiirrot = aliPuut[i].mahdollisetSiirrot(pelaaja); //[0,0,1,1,0,1]

                int siirto = aliPuunSiirrot[i];

                if (siirto == 1) { //siirto sallittu

                    aliPuut[i].teeSiirtoLeikisti(i, pelaaja); //päivittää aliPuut[i].temp -lautaa

                    //jos siirrolla ei saa uutta vuoroa, eikä peli ole ohi, siirtyy vuoro toiselle pelaajalle
                    if (!aliPuut[i].uusiVuoro && !aliPuut[i].isGameOver()) {

                        maksimit = minimax(pelaaja + 1, aliPuut[i]); //tietokoneen vuoro palauttaa maksimit

                        //valitaan maksimeista pienin ja asetetaan se minListaan
                        for (k = 0; k < 6; k++) {
                            if (maksimit[k] < minLista[i]) {
                                minLista[i] = maksimit[k];
                            }
                        }
                        minimit[i] = minLista[i];

                    } else if (aliPuut[i].uusiVuoro && !aliPuut[i].isGameOver()) {

                        lisaminimit = minimax(pelaaja, aliPuut[i]);

                        for (int j = 0; j < 6; j++) {
                            if (lisaminimit[j] < minLista[i]) {
                                minLista[i] = lisaminimit[j];
                            }
                        }
                        minimit[i] = minLista[i];

                    } else if (aliPuut[i].isGameOver()) {
                        minimit[i] = aliPuut[i].evaluate();
                    }

                }
                else if (siirto == 0) {
                    minimit[i] = aliPuut[i].evaluate();
                }

                minLista[i] = 1000;

            }
            //kaikki mahdolliset siirrot käyty läpi
            return minimit;

        } //-----------------------TIETOKONEEN SIIRTO-------------------------------------
        else { //if pelaaja=2

            int[] maxLista = new int[6];
            for (int q = 0; q < 6; q++) {
                maxLista[q] = -1000;
            }

            //tutkitaan kaikki mahdolliset pelin etenemisvaihtoehdot
            for (i = 0; i < 6; i++) {

                for (int j = 0; j < 14; j++) {
                    aliPuut[i].lauta[j] = lauta.lauta[j];
                }

                int[] puunSiirrot = aliPuut[i].mahdollisetSiirrot(pelaaja); //[0, 1, 0, 1, 1, 1]

                int siirto = puunSiirrot[i];

                if (siirto == 1) {

                    aliPuut[i].teeSiirtoLeikisti(i + 7, pelaaja);

                    if (!aliPuut[i].uusiVuoro && !aliPuut[i].isGameOver()) {

                        minimit = minimax(pelaaja - 1, aliPuut[i]);

                        for (k = 0; k < 6; k++) {
                            if (minimit[k] > maxLista[i]) {
                                maxLista[i] = minimit[k];
                            }

                        }
                        maksimit[i] = maxLista[i];
                        
                    } 
                    else if (aliPuut[i].uusiVuoro && !aliPuut[i].isGameOver()) {

                        lisamaksimit = minimax(pelaaja, aliPuut[i]);

                        for (int j = 0; j < 6; j++) {
                            if (lisamaksimit[j] > maxLista[i]) {
                                maxLista[i] = lisamaksimit[j];
                            }
                        }
                        maksimit[i] = maxLista[i];

                    } 
                    else if (aliPuut[i].isGameOver()) {
                        maksimit[i] = aliPuut[i].evaluate();
                    }
                    
                    maxLista[i] = -1000;
                } 
                else if (siirto == 0) {
                    maksimit[i] = aliPuut[i].evaluate();
                }
            }
            return maksimit;
        }

    }

    public static void main(String[] args) {
        Board b = new Board();
        b.startBoard();
        b.lauta[0] = 5;
        b.lauta[8] = 7;
        b.lauta[1] = 0;
        b.printBoard();
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

