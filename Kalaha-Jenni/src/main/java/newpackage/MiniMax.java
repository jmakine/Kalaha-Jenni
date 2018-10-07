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
    public static int[] minimax(int pelaaja, Board lauta, int deapth) {

        System.out.println("Kutsuttu: minimax(" + pelaaja + "," + Arrays.toString(lauta.lauta) + ")");

        int i, k;

        if (lauta.isGameOver() || deapth==0) {
            int[] pisteet = new int[6];
            for (i = 0; i < 6; i++) {
                pisteet[i] = lauta.evaluate();
            }
            return pisteet;
        }

        int[] maksimit = new int[6];
        int[] minimit = new int[6];

        int[] lisamaksimit;
        int[] lisaminimit;

        //tehdään annetusta laudasta 6 kopiota
        Board[] aliPuut = new Board[6];
        lauta.uusiVuoro = false;

        for (i = 0; i < 6; i++) {
            aliPuut[i] = new Board();
        }

//------------------------------------PELAAJA IHMINEN-----------------  
        if (pelaaja == 1) {
            //---
            System.out.println("pelaaja = " + pelaaja);
            System.out.println("syvyys = " + deapth);

            /*int minLista[] = new int[6];
            for (int q = 0; q < 6; q++) {
                minLista[q] = 1000;
            }

            if (lauta.isGameOver()) {
                System.out.println("peli ohi, palautetaan [1000,...,1000] lista");
                return minLista;
            }*/
            int min=1000;

            int[] aliPuunSiirrot = lauta.mahdollisetSiirrot(pelaaja); //[0,0,1,1,0,1]
            System.out.println("\tMahdolliset siirrot: " + Arrays.toString(aliPuunSiirrot));

            for (i = 0; i < 6; i++) {

                for (int j = 0; j < 14; j++) {
                    aliPuut[i].lauta[j] = lauta.lauta[j];
                }

                //  int[] aliPuunSiirrot = aliPuut[i].mahdollisetSiirrot(pelaaja); //[0,0,1,1,0,1]
                System.out.println("\tMahdolliset siirrot: " + Arrays.toString(aliPuunSiirrot));
                int siirto = aliPuunSiirrot[i];

                if (siirto == 1) { //siirto sallittu //ENUM! 
                    System.out.println("\tsiirto kupista indeksillä " + (i) + " on sallittu");

                    aliPuut[i].teeSiirtoLeikisti(i, pelaaja); //päivittää aliPuut[i].temp -lautaa

                    aliPuut[i].temp.printBoard();
                    System.out.println(aliPuut[i].temp.uusiVuoro);

                    //jos siirrolla ei saa uutta vuoroa, eikä peli ole ohi, siirtyy vuoro toiselle pelaajalle
                    if (!aliPuut[i].temp.uusiVuoro && !aliPuut[i].temp.isGameOver() && deapth!=0) {

                        System.out.println("\tSiirrolla ei saisi uutta vuoroa, peli jatkuisi");

                        maksimit = minimax(pelaaja + 1, aliPuut[i].temp, deapth--); //tietokoneen vuoro palauttaa maksimit
                        System.out.println("Syvyys = " + deapth);

                        //valitaan maksimeista pienin ja asetetaan se minListaan
                        for (k = 0; k < 6; k++) {
                            if (maksimit[k] < min){//minLista[i]) {
                                //minLista[i] = 
                                min=maksimit[k];
                            }
                        }
                        //System.out.println("\tMinimitauluun kohtaan "+i+" tulee " +minLista[i]);
                        minimit[i] = min;//minLista[i];

                    } else if (aliPuut[i].temp.uusiVuoro && !aliPuut[i].temp.isGameOver() && deapth!=0) {

                        System.out.println("\tSaisi uuden vuoron, peli jatkuisi");

                        lisaminimit = minimax(pelaaja, aliPuut[i].temp, deapth--);
                        System.out.println("Syvyys = " + deapth);

                        for (int j = 0; j < 6; j++) {
                            if (lisaminimit[j] < min){//minLista[i]) {
                                //minLista[i] 
                                min= lisaminimit[j];
                            }
                        }
                        //System.out.println("minimitauluun kohtaan "+i+" tulee " +minLista[i]);
                        minimit[i] = min;// minLista[i];

                    } else if (aliPuut[i].isGameOver()) {
                        System.out.println("\tSiirron jälkeen peli olisi ohi ja arvoksi kohtaan "+i+" tulisi "+aliPuut[i].evaluate());
                        minimit[i] = aliPuut[i].evaluate();
                    }
                    else if (deapth==0){
                        System.out.println("Syvyys nolla!");
                        minimit[i]=aliPuut[i].temp.evaluate();
                    }
                    
                    min=1000;//minLista[i] = 1000;

                } else if (siirto == 0) {
                    //System.out.println("\tSiirto kupista "+i+" ei olisi sallittu");
                    minimit[i] = 1000;//aliPuut[i].evaluate();
                } 

            }

            System.out.println("Lopulta palautetaan minimit: "+ Arrays.toString(minimit));
            return minimit;

        } 
//-----------------------TIETOKONEEN SIIRTO-------------------------------------
        else { //if pelaaja=2

            System.out.println("pelaaja = " + pelaaja);
            System.out.println("syvyys = " + deapth);

            /*int[] maxLista = new int[6];
            for (int q = 0; q < 6; q++) {
                maxLista[q] = -1000;
            }*/

            /*if (lauta.isGameOver()) {
                return maxLista;
            }*/
            int max=-1000;
            
            int[] aliPuunSiirrot = lauta.mahdollisetSiirrot(pelaaja); //[0,0,1,1,0,1]
            System.out.println("Mahdolliset siirrot: " + Arrays.toString(aliPuunSiirrot));

            //tutkitaan pelitilanteen kaikki mahdolliset siirrot
            for (i = 0; i < 6; i++) {

                for (int j = 0; j < 14; j++) {
                    aliPuut[i].lauta[j] = lauta.lauta[j];
                }

                //int[] aliPuunSiirrot = aliPuut[i].mahdollisetSiirrot(pelaaja); //[0, 1, 0, 1, 1, 1]
                System.out.println("Mahdolliset siirrot: " + Arrays.toString(aliPuunSiirrot));
                int siirto = aliPuunSiirrot[i];

                if (siirto == 1) {

                    System.out.println("siirto kupista(indeksi) " + (12 - i) + " on sallittu, tehdään siirto leikisti:");
                    aliPuut[i].teeSiirtoLeikisti(12 - i, pelaaja);
                    aliPuut[i].temp.printBoard();

                    if (!aliPuut[i].temp.uusiVuoro && !aliPuut[i].temp.isGameOver() && deapth!=0) {

                        System.out.println("\tSiirrolla ei saisi uutta vuoroa, peli jatkuisi");

                        minimit = minimax(pelaaja - 1, aliPuut[i].temp, deapth--);
                        System.out.println("Syvyys = " + deapth);

                        for (k = 0; k < 6; k++) {
                            if (minimit[k] > max){// maxLista[i]) {
                                //maxLista[i] 
                                max = minimit[k];
                            }

                        }
                        //System.out.println("maksimitauluun kohtaan "+i+" tulee " +maxLista[i]);

                        maksimit[i] = max;// maxLista[i];

                    } else if (aliPuut[i].temp.uusiVuoro && !aliPuut[i].temp.isGameOver() && deapth!=0) {

                        System.out.println("\tSiirrolla saisi uuden vuoron, peli jatkuisi");

                        lisamaksimit = minimax(pelaaja, aliPuut[i].temp, deapth--);
                        System.out.println("Syvyys = " + deapth);

                        for (int j = 0; j < 6; j++) {
                            if (lisamaksimit[j] > max){// maxLista[i]) {
                                //maxLista[i] 
                                max = lisamaksimit[j];
                            }
                        }
                        //System.out.println("maksimitauluun kohtaan "+i+" tulee " +maxLista[i]);

                        maksimit[i] = max;//maxLista[i];

                    } else if (aliPuut[i].temp.isGameOver()) {
                        System.out.println("\tSiirron jälkeen peli olisi ohi");
                        maksimit[i] = aliPuut[i].temp.evaluate();
                    } else if (deapth==0){
                        System.out.println("Syvyys nolla: ");
                        maksimit[i]=aliPuut[i].temp.evaluate();
                    }

                    //maxLista[i] 
                    max = 1000;
                } else if (siirto == 0) {
                    //System.out.println("\tSiirto kupista "+(12-i)+" ei olisi sallittu");
                    maksimit[i] = 1000;//aliPuut[i].evaluate();
                }
            }
            System.out.println("lopulta palautetaan maksimit: "+ Arrays.toString(maksimit));
            return maksimit;
        }

    }
    
    /*public static int[] minimoiJaVuoroSiirtyy(Board b){
       // if (!b.temp.uusiVuoro && !b.temp.isGameOver()) {

            int minimit[]=new int[6];//{1000, 1000, 1000, 1000, 1000, 1000};
                        System.out.println("\tSiirrolla ei saisi uutta vuoroa, peli jatkuisi");

                        minimit = minimax(2, b.temp, 0);
                        //System.out.println("Syvyys = " + deapth);

                        int max=-1000;
                        for (int i = 0; i < 6; i++) {
                            if (minimit[i] > max){// maxLista[i]) {
                                //maxLista[i] 
                                max = minimit[k];
                            }
                        }
                        
                        //System.out.println("maksimitauluun kohtaan "+i+" tulee " +maxLista[i]);

                        //maksimit[i] = max;// maxLista[i];
    }*/

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
        int pelaaja = 1;
        int[] human, machine;//=new int[6];
        human = minimax(pelaaja, b, 1);
        System.out.println(Arrays.toString(human)); //ihmisen minmax -> [2, 2, 2, 2, 2, 6] eli ihminen maximoi mahdollisuutensa 
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

