package newpackage;

//import java.lang.reflect.Array;
import java.util.Arrays;
//import newpackage.Board;

public class MiniMax {

    public static int pienin(int[] maksimit){
        int min=1000;
        for (int i = 0; i < 6; i++) {
                            if (maksimit[i] < min){
                                min = maksimit[i];
                                
                            }
                        }
        return min;
    }
    
    public static int suurin(int[] minimit){
        int max=-1000;
        for (int i = 0; i < 6; i++) {
                            if (minimit[i] < max){
                                max = minimit[i];
                                
                            }
                        }
        return max;
    }
    
//---------------------MIN-MAX-------------------------------------------------------------------
    /**
     * Minimax-algoritmi palauttaa pisteet kullekkin mahdolliselle siirrolle
     * lähtötilanteesta.
     * @param alpha 
     * @param beta
     * @param deapth
     * @param pelaaja ihminen(1) tai tietokone(2)
     * @param lauta pelilauta, jolle minimax suoritetaan.
     * @return 6-paikkainen pistetaulu.
     */
    public static int[] minimax(int pelaaja, Board lauta, int deapth, int alpha, int beta) {
        //Metodia kutsutaan minimax(1 tai 2, Board, 0, -1000, 1000)
        //System.out.println("Kutsuttu: minimax(" + pelaaja + "," + Arrays.toString(lauta.lauta) + ")");

        int i, k, j;
        int min = 1000;
        int max = -1000;

        int[] maksimit = new int[6];
        int[] minimit = new int[6];

        int[] lisamaksimit;
        int[] lisaminimit;
        
        //loppu
        int[] pisteet=new int[6];
        if(deapth==15|| lauta.isGameOver()){
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
        int[] aliPuunSiirrot = lauta.mahdollisetSiirrot(pelaaja); //[0,0,1,1,0,1]

//------------------PELAAJA PALAUTTAA MINIMIT-----------------  
        if (pelaaja == 1) {          
            
            for (i = 0; i < 6; i++) {
                
                int siirto = aliPuunSiirrot[i];

                if (siirto == 1) { //siirto sallittu //ENUM! 

                    aliPuut[i].teeSiirtoLeikisti(i, pelaaja); //päivittää aliPuut[i].temp -lautaa

                    if (aliPuut[i].temp.isGameOver()) {
                        minimit[i] = aliPuut[i].temp.evaluate();
                    } //ei saa uutta vuoroa
                    else if (!aliPuut[i].temp.uusiVuoro) {

                        maksimit = minimax(pelaaja + 1, aliPuut[i].temp, deapth+1, alpha, beta); //tietokoneen vuoro palauttaa maksimit

                        //pelaaja valitsee maksimeista pienimmän
                        for (k = 0; k < 6; k++) {
                            if (maksimit[k] < min){
                                min = maksimit[k];
                                minimit[i] = min;
                            }
                        }
                        
                        //minimit[i]=pienin(maksimit);

                        beta = Math.min(beta, minimit[i]);
                        //System.out.println("Beta: " + beta);
                        if (beta <= alpha) {
                            //break;
                            for (j = i + 1; j < 6; j++) {
                                minimit[j] = aliPuut[i].temp.evaluate();
                            }
                            //System.out.println("Beta>Alpha: Palautetaan minimit: " + Arrays.toString(minimit));
                            return minimit;
                        }

                    }                    
                    else if (aliPuut[i].temp.uusiVuoro && !aliPuut[i].temp.isGameOver()) {
                        
                        lisaminimit = minimax(pelaaja, aliPuut[i].temp, deapth+1, alpha, beta);
                        
                        //pelaaja valitsee lisäsiirron palauttamista minimeistä pienimmän
                        for (j = 0; j < 6; j++) {
                            if (lisaminimit[j] < min && lisaminimit[j]!=-1000) { //UUSI
                                min = lisaminimit[j];
                                if(min<minimit[i]){
                                minimit[i] = min;
                                }
                            }
                        }
                        
                       // minimit[i]=pienin(lisaminimit);

                        beta = Math.min(beta, minimit[i]);
                        //System.out.println("Beta: " + beta);

                        if (beta <= alpha) {
                            //break;
                            for (j = i + 1; j < 6; j++) {
                                minimit[j] = aliPuut[i].temp.evaluate();
                            }
                            //System.out.println("Beta<Alpha: Palautetaan minimit: " + Arrays.toString(minimit));
                            return minimit;
                        }
                    }
                    min = 1000;

                }//siirto ei ole sallittu
                else if (siirto == 0) {
                    minimit[i] = -1000;
                }

            }

            //System.out.println("Pelaajan vuoron jälkeen palautetaan minimit: " + Arrays.toString(minimit));
            return minimit;

//-----------------------TIETOKONE PALAUTTAA MAKSIMIT-------------------------------------
        
        } else {

            for (i = 0; i < 6; i++) {
                
                int h=i+7;
                int siirto = aliPuunSiirrot[i];

                if (siirto == 1) {

                    //aliPuut[i].teeSiirtoLeikisti(12 - i, pelaaja);
                    aliPuut[i].teeSiirtoLeikisti(h, pelaaja);

                    if (aliPuut[i].temp.isGameOver()) {
                        maksimit[i] = aliPuut[i].temp.evaluate();
                    
                    } else if (!aliPuut[i].temp.uusiVuoro) {

                        minimit = minimax(pelaaja - 1, aliPuut[i].temp, deapth+1, alpha, beta);

                        //kone valitsee pelaajan palauttammista minimeistä suurimman
                        for (k = 0; k < 6; k++) {
                            if (minimit[k] > max && minimit[k]!=-1000) { //UUSI
                                max = minimit[k];
                                maksimit[i] = max;
                            }
                        }
                        //maksimit[i]=suurin(minimit);

                       alpha = Math.max(alpha, maksimit[i]);
                       //System.out.println("Alpha: " + alpha);

                        if (beta <= alpha) {
                            //break;
                            for (j = i + 1; j < 6; j++) {
                             
                                maksimit[j] =aliPuut[i].temp.evaluate();//1000;
                            }
                            //System.out.println("Beta<Alpha: Palautetaan maksimit: " + Arrays.toString(maksimit));
                            return maksimit;
                        }

                    } else if (aliPuut[i].temp.uusiVuoro && !aliPuut[i].temp.isGameOver()) {

                        lisamaksimit = minimax(pelaaja, aliPuut[i].temp, deapth+1, alpha, beta);

                        //valitsee suurimman
                        for (j = 0; j < 6; j++) {
                            if (lisamaksimit[j] > max && lisamaksimit[j]!=1000) {
                                max = lisamaksimit[j];
                                if(max>maksimit[i]){
                                maksimit[i] = max;
                                }
                            }
                        }
                        
                        //maksimit[i]=suurin(lisamaksimit);

                        alpha = Math.max(alpha, maksimit[i]);
                        //System.out.println("Alpha: "+alpha);
                        if (beta <= alpha) {
                            //break;
                            for (j = i + 1; j < 6; j++) {
                                maksimit[j] = aliPuut[i].temp.evaluate();
                            }
                            //System.out.println("Beta<Alpha: Palautetaan maksimit: " + Arrays.toString(maksimit));
                            return maksimit;
                        }
                    }

                    max = -1000;
                    
                } else if (siirto == 0) {
                    maksimit[i] = 1000;
                }
            }
            //System.out.println("Koneen vuoron jälkeen palautetaan maksimit: " + Arrays.toString(maksimit));
            return maksimit;
        }

    }

    public static void main(String[] args) {
        Board b = new Board();
        //b.startBoard();
        for (int i = 0; i < 14; i++) {
            b.lauta[i] = 0;
        }
        b.lauta[8] = 2;
        b.lauta[5] = 4;
        b.lauta[10] = 6;
        b.lauta[2] = 8;
        b.printBoard();
        int pelaaja = 2;
        int[] minimaxPalauttaa;// machine;//=new int[6];
        minimaxPalauttaa = minimax(pelaaja, b, 0, -1000, 1000);
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

