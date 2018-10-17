package newpackage;

import java.util.Arrays;

/**
 *
 * @author Jenni
 */
public class MiniMax {
    
    /**
     * Minimax-algoritmi palauttaa pisteet kullekkin mahdolliselle siirrolle. 
     * Metodia kutsutaan minimax(1 tai 2, Board, 0, -1000, 1000) 
     * @param alpha tietokoneelle taattu maksimipistemäärä
     * @param beta pelaajalle taattu minimipistemäärä
     * @param deapth pelipuun syvyys, johon asti tulevia siirtoja tutkitaan
     * @param pelaaja ihminen(1) tai tietokone(2)
     * @param lauta pelilauta, jolle minimax suoritetaan.
     * @return 6-paikkainen pistetaulu.
     */
    public static int[] minimax(int pelaaja, Board lauta, int deapth, int alpha, int beta) {
        
        int i, k, j;
        int min = 1000;
        int max = -1000;

        int[] maksimit = new int[6];
        int[] minimit = new int[6];

        int[] lisamaksimit;
        int[] lisaminimit;
        
        //tutkittava syvyys saavutettu tai peli ohi, palautetaan pisteet
        int[] pisteet=new int[6];
        if(deapth==20 || lauta.isGameOver()){
            for(i=0; i<6;i++){
                    pisteet[i]=lauta.evaluate();
                }
            return pisteet;
        }
        
        //tehdään annetusta laudasta 6 kopiota
        Board[] aliPuut = new Board[6];
        for (i = 0; i < 6; i++) {
            aliPuut[i] = new Board();
            for (j = 0; j < 14; j++) {
                aliPuut[i].lauta[j] = lauta.lauta[j];
            }
        }
        

        lauta.uusiVuoro = false;
        int[] aliPuunSiirrot = lauta.mahdollisetSiirrot(pelaaja); //muotoa [0,0,1,1,0,1]

//------------------PELAAJAN SIIRTO PALAUTTAA MINIMIT-----------------  
        if (pelaaja == 1) {   
            
            for (i = 0; i < 6; i++) {
                    
                int siirto = aliPuunSiirrot[i];

                if (siirto == 1) { //siirto sallittu 

                    aliPuut[i].teeSiirtoLeikisti(i, pelaaja); //päivittää aliPuut[i].temp -lautaa

                    if (aliPuut[i].temp.isGameOver()) {
                        minimit[i] = aliPuut[i].temp.evaluate();
                    }
                    
                    else if (!aliPuut[i].temp.uusiVuoro) {

                        maksimit = minimax(pelaaja+1, aliPuut[i].temp, deapth+1, alpha, beta); 
                        
                        //pelaaja valitsee maksimeista pienimmän
                        for (k = 0; k < 6; k++) {
                            if (maksimit[k] < min && maksimit[k]!=-1000){
                                min = maksimit[k];
                                minimit[i] = min;
                            }
                        }
                        
                        //tässä katsotaan, tarvitseeko pelitilanteen muita haaroja tutkia
                        beta = Math.min(beta, minimit[i]);
                        if (beta <= alpha) {                            
                            for (j = i + 1; j < 6; j++) {
                                minimit[j] = aliPuut[i].temp.evaluate();
                            }
                            return minimit;
                        }

                    }                    
                    else if (aliPuut[i].temp.uusiVuoro && !aliPuut[i].temp.isGameOver()) {
                        
                        lisaminimit = minimax(pelaaja, aliPuut[i].temp, deapth+1, alpha, beta);
                        
                        //pelaaja valitsee pienimmän
                        for (j = 0; j < 6; j++) {
                            if (lisaminimit[j] < min && lisaminimit[j]!=-1000){ 
                                min = lisaminimit[j];
                                minimit[i] = min;
                                }
                            }

                        beta = Math.min(beta, minimit[i]);
                        if (beta <= alpha) {
                            for (j = i + 1; j < 6; j++) {
                                minimit[j] = aliPuut[i].temp.evaluate();
                            }
                            return minimit;
                        }
                        
                    }
                    min = 1000;

                }
                else if (siirto == 0) { //siirto ei sallittu
                    minimit[i] = -1000;
                }
                
            }

            return minimit;

//-----------------------TIETOKONEEN SIIRTO PALAUTTAA MAKSIMIT-------------------------------------
        
        } else { //pelaaja=2

            for (i = 0; i < 6; i++) {
                
                int h=i+7;
                int siirto = aliPuunSiirrot[i];

                if (siirto == 1) {

                    aliPuut[i].teeSiirtoLeikisti(h, pelaaja);

                    if (aliPuut[i].temp.isGameOver()) {
                        maksimit[i] = aliPuut[i].temp.evaluate();
                    
                    } else if (!aliPuut[i].temp.uusiVuoro) {

                        minimit = minimax(pelaaja -1 , aliPuut[i].temp, deapth+1, alpha, beta);

                        //kone valitsee suurimman
                        for (k = 0; k < 6; k++) {
                            if (minimit[k] > max && minimit[k]!=-1000) {
                                max = minimit[k];
                                maksimit[i] = max;
                            }
                        }

                        //tässä katsotaan, tarvitseeko pelitilanteen muita haaroja tutkia
                        alpha = Math.max(alpha, maksimit[i]);
                        if (beta <= alpha) {
                            for (j = i + 1; j < 6; j++) {
                                maksimit[j] = aliPuut[i].temp.evaluate();
                            }
                            return maksimit;
                        }

                    } else if (aliPuut[i].temp.uusiVuoro && !aliPuut[i].temp.isGameOver()) {

                        lisamaksimit = minimax(pelaaja, aliPuut[i].temp, deapth+1, alpha, beta);

                        //valitsee suurimman
                        for (j = 0; j < 6; j++) {
                            if (lisamaksimit[j] > max && lisamaksimit[j]!=1000){
                                max = lisamaksimit[j];
                                maksimit[i] = max;                                
                            }
                        }
                                                
                        alpha = Math.max(alpha, maksimit[i]);
                        if (beta <= alpha) {
                            for (j = i + 1; j < 6; j++) {
                                maksimit[j] = aliPuut[i].temp.evaluate();
                            }
                            return maksimit;
                        }
                    }

                   max = -1000;
                    
                } else if (siirto == 0) {
                    maksimit[i] = 1000;
                }
                                             
            }
            return maksimit;
        }

    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        Board b = new Board();
        //b.startBoard();
        for (int i = 0; i < 14; i++) {
            b.lauta[i] = 0;
        }
        b.lauta[8] = 2;
        b.lauta[5] = 4;
        b.lauta[11] = 3;
        b.lauta[9]=8;
        b.lauta[2] = 4;
        b.lauta[1]=3;
        b.lauta[0]=6;
        b.printBoard();
        int pelaaja = 2;
        int[] minimaxPalauttaa;
        minimaxPalauttaa = minimax(pelaaja, b, 0, -1000, 1000);
        System.out.println(Arrays.toString(minimaxPalauttaa)); 
    }

} 

