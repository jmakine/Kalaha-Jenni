package newpackage;

//import java.lang.reflect.Array;
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
    public static int[] minimax(int pelaaja, Board lauta, int alpha, int beta) {
        //Metodia kutsutaan minimax(1 tai 2, Board, 0, -1000, 1000)
        //System.out.println("Kutsuttu: minimax(" + pelaaja + "," + Arrays.toString(lauta.lauta) + ")");

        int i, k, j;
        int min = 1000;
        int max = -1000;

        int[] maksimit = new int[6];
        int[] minimit = new int[6];

        int[] lisamaksimit;
        int[] lisaminimit;

        //tehdään annetusta laudasta 6 kopiota
        Board[] aliPuut = new Board[6];
        for (i = 0; i < 6; i++) {
            aliPuut[i] = new Board();
            for (j = 0; j < 14; j++) {
                aliPuut[i].lauta[j] = lauta.lauta[j];
            }
        }

        lauta.uusiVuoro = false;
        int[] aliPuunSiirrot = lauta.mahdollisetSiirrot(pelaaja); //[0,0,1,1,0,1]

//------------------PELAAJA PALAUTTAA MINIMIT-----------------  
        if (pelaaja == 1) {

            //System.out.println("\tMahdolliset siirrot: " + Arrays.toString(aliPuunSiirrot));
            for (i = 0; i < 6; i++) {

                int siirto = aliPuunSiirrot[i];

                if (siirto == 1) { //siirto sallittu //ENUM! 
                    System.out.println("siirto kupista indeksillä " + (i) + " on sallittu, tehdään siirto leikisti:");

                    aliPuut[i].teeSiirtoLeikisti(i, pelaaja); //päivittää aliPuut[i].temp -lautaa

                    aliPuut[i].temp.printBoard();

                    if (aliPuut[i].temp.isGameOver()) {
                        minimit[i] = aliPuut[i].temp.evaluate();
                    } //ei saa uutta vuoroa
                    else if (!aliPuut[i].temp.uusiVuoro) {// && !aliPuut[i].temp.isGameOver() && deapth!=0) {

                        System.out.println("Siirrolla ei saa uutta vuoroa");
                        maksimit = minimax(pelaaja + 1, aliPuut[i].temp, alpha, beta); //tietokoneen vuoro palauttaa maksimit

                        //pelaaja valitsee maksimeista pienimmän
                        for (k = 0; k < 6; k++) {
                            if (maksimit[k] < min) { //min=1000
                                min = maksimit[k];
                                minimit[i] = min;
                            }
                        }

                        System.out.println("\tMinimitauluun kohtaan " + i + " tulee " + min);

                        beta = Math.min(beta, minimit[i]);
                        if (beta <= alpha) {
                            System.out.println("beta<=alfa, ei tutkita");
                            for (j = i + 1; j < 6; j++) {
                                minimit[j] = 1000;
                            }
                            System.out.println("Palautetaan minimit: " + Arrays.toString(minimit));
                            return minimit;
                        }

                    }                    
                    else if (aliPuut[i].temp.uusiVuoro) {
                        
                        System.out.println("Siirrolla saa uuden vuoron ");
                        lisaminimit = minimax(pelaaja, aliPuut[i].temp, alpha, beta);
                        
                        //pelaaja valitsee lisäsiirron palauttamista minimeistä pienimmän
                        for (j = 0; j < 6; j++) {
                            if (lisaminimit[j] < min && lisaminimit[j]!=-1000) {
                                min = lisaminimit[j];
                                minimit[i] = min;
                            }
                        }
                        System.out.println("minimitauluun kohtaan " + i + " tulee " + min);

                        beta = Math.min(beta, minimit[i]);
                        if (beta <= alpha) {
                            System.out.println("beta<=alfa, ei tutkita");
                            for (j = i + 1; j < 6; j++) {
                                minimit[j] = 1000;
                            }
                            System.out.println("Palautetaan minimit: " + Arrays.toString(minimit));
                            return minimit;
                        }
                    }
                    min = 1000;

                }//siirto ei ole sallittu
                else if (siirto == 0) {
                    System.out.println("\tSiirto kupista " + i + " ei olisi sallittu");
                    minimit[i] = -1000;
                }

            }

            System.out.println("Lopulta palautetaan minimit: " + Arrays.toString(minimit));
            return minimit;

//-----------------------TIETOKONE PALAUTTAA MAKSIMIT-------------------------------------
        
        } else {

            for (i = 0; i < 6; i++) {

                int siirto = aliPuunSiirrot[i];

                if (siirto == 1) {

                    System.out.println("siirto kupista(indeksi) " + (12 - i) + " on sallittu, tehdään siirto leikisti:");
                    aliPuut[i].teeSiirtoLeikisti(12 - i, pelaaja);
                    aliPuut[i].temp.printBoard();

                    if (aliPuut[i].temp.isGameOver()) {
                        maksimit[i] = aliPuut[i].temp.evaluate();
                    
                    } else if (!aliPuut[i].temp.uusiVuoro) {

                        System.out.println("\tSiirrolla ei saisi uutta vuoroa, peli jatkuisi");

                        minimit = minimax(pelaaja - 1, aliPuut[i].temp, alpha, beta);

                        //kone valitsee pelaajan palauttammista minimeistä suurimman
                        for (k = 0; k < 6; k++) {
                            if (minimit[k] > max) {// max=-1000
                                max = minimit[k];
                                maksimit[i] = max;
                            }
                        }

                        alpha = Math.max(alpha, maksimit[i]);
                        if (beta <= alpha) {
                            System.out.println("beta<=alfa, ei tutkita");
                            for (j = i + 1; j < 6; j++) {
                                maksimit[j] = 1000;
                            }
                            System.out.println("Palautetaan maksimit: " + Arrays.toString(maksimit));
                            return maksimit;
                        }

                        System.out.println("maksimitauluun kohtaan " + i + " tulee " + max);

                    } else if (aliPuut[i].temp.uusiVuoro) {// && !aliPuut[i].temp.isGameOver() && (deapth-1)!=0) {

                        System.out.println("\tSiirrolla saisi uuden vuoron, peli jatkuisi");

                        lisamaksimit = minimax(pelaaja, aliPuut[i].temp, alpha, beta);

                        //valitsee suurimman
                        for (j = 0; j < 6; j++) {
                            if (lisamaksimit[j] > max && lisamaksimit[j]!=1000) {
                                max = lisamaksimit[j];
                                maksimit[i] = max;
                            }
                        }
                        System.out.println("maksimitauluun kohtaan " + i + " tulee " + max);

                        alpha = Math.max(alpha, maksimit[i]);
                        System.out.println("beta<=alfa, ei tutkita");
                        if (beta <= alpha) {
                            for (j = i + 1; j < 6; j++) {
                                maksimit[j] = 1000;
                            }
                            System.out.println("Palautetaan maksimit: " + Arrays.toString(maksimit));
                            return maksimit;
                        }
                    }

                    max = -1000;
                    
                } else if (siirto == 0) {
                    System.out.println("\tSiirto kupista indeksillä " + (12 - i) + " ei olisi sallittu");
                    maksimit[i] = 1000;
                }
            }
            System.out.println("lopulta palautetaan maksimit: " + Arrays.toString(maksimit));
            return maksimit;
        }

    }

    public static void main(String[] args) {
        Board b = new Board();
        //b.startBoard();
        for (int i = 0; i < 14; i++) {
            b.lauta[i] = 0;
        }
        b.lauta[8] = 7;
        b.lauta[5] = 3;
        b.lauta[10] = 2;
        b.lauta[2] = 4;
        b.printBoard();
        int pelaaja = 2;
        int[] minimaxPalauttaa;// machine;//=new int[6];
        minimaxPalauttaa = minimax(pelaaja, b, -1000, 1000);
        System.out.println(Arrays.toString(minimaxPalauttaa)); //ihmisen minmax -> [2, 2, 2, 2, 2, 6] eli ihminen maximoi mahdollisuutensa 
        //machine = minimax(pelaaja + 1, b);
        //System.out.println(Arrays.toString(machine));

        /*Board c = new Board();
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
         */
    }

} //CLASS 

