/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

import java.util.Scanner;

/**
 * Luokka Board alustaa pelilaudan lähtötilanteeseen. 
 * Luokka hoitaa siirtojen aiheuttamat muutokset pelilautaan. 
 * Tässä luokassa myös pelilaudan tulostus. 
 * @author Jenni
 */


public class Board {
    
    Scanner input = new Scanner(System.in);   
    int[] lauta;

    public Board() {
        this.lauta = new int[]{4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 0};

    }

    public void printBoard() {
        System.out.println("|" + this.lauta[13] + "|" + this.lauta[12] + "|" + this.lauta[11] + "|" + this.lauta[10] + "|" + this.lauta[9] + "|" + this.lauta[8] + "|" + this.lauta[7] + "\n"
                + "  |" + this.lauta[0] + "|" + this.lauta[1] + "|" + this.lauta[2] + "|" + this.lauta[3] + "|" + this.lauta[4] + "|" + this.lauta[5] + "|" + this.lauta[6] + "|");
    }

    //päivittää pelilaudan tilanteen annetun siirron mukaan
    //siirto kupista 3 on taulun paikassa this.lauta[2], siksi siirto-1
    public void humanMakeAmove() {
        
        System.out.println("Tee siirtosi:");
        int siirto=input.nextInt();
        if(siirto>6){
            System.out.println("Saat valita kipoista 1-6");
            humanMakeAmove();
        }
        
        //tyhjästä kiposta ei voi siirtää
        if (this.lauta[siirto - 1] == 0) {
            System.out.println("Et voi tehdä siirtoa tyhjästä kiposta, valitse uudelleen:");
        }

        //otetaan valitun kipon kivet
        int kiviaKipossa = this.lauta[siirto - 1];
        //System.out.println(kiviaKipossa);

        //tyhjennetään kippo
        this.lauta[siirto - 1] = 0;

        //lasketaan indeksi paikalle, johon annetulla siirrolla viimeinen kivi osuu
        int viimeinenKiviInd = siirto - 1 + kiviaKipossa;
        //System.out.println(viimeinenKiviInd);

        int vastustajanMancala = 13;
        int mancala = 6;
        
        if (viimeinenKiviInd >= 13) {
            viimeinenKiviInd = 13 - (siirto - 1 + kiviaKipossa);
        }

        //kaikki kipossa olevat kivet jaetaan tuleviin kuppeihin
        //i=indeksi,kaikki tulevat indeksit käydään läpi
        for (int i = siirto; i < siirto + kiviaKipossa; i++) {            
                //jos i = vastustajan mancalan indeksi
                if (i == vastustajanMancala) {
                    kiviaKipossa += 1;
                    //ei lisätä kiveä 
                } else if (i < vastustajanMancala) {
                    this.lauta[i] += 1;
                } else if (i > vastustajanMancala) { //i=14
                    int j = i - 14;
                    this.lauta[j] += 1;
                }           
        }

        //katsotaan, mihin viimeinen kivi laitetaan:
        //System.out.println(viimeinenKiviInd);
        //jos osuu omaan mancalaan, saa uuden vuoron
        if (viimeinenKiviInd == mancala) {
            System.out.println("Sait viimeisen kiven omaan mancalaan. Saat uuden vuoron!");
            printBoard();
            //int toinenSiirto = input.nextInt();
            humanMakeAmove();
        }         
        //jos osuu tyhjään omaan kippoon, saa myös vastapuolen kipon kivet
        else if (viimeinenKiviInd < 6 && this.lauta[viimeinenKiviInd] == 1) {
            int vastustajanKivet = this.lauta[vastapuolenKivet(viimeinenKiviInd)];
            this.lauta[viimeinenKiviInd] = 0;
            this.lauta[mancala] += vastustajanKivet + 1;
            this.lauta[vastapuolenKivet(viimeinenKiviInd)] = 0;

        }
    }

//----------------------------------------------------------------------------------------
    //palauttaa annettua indeksiä vastapäätä olevan kipon indeksin
    public int vastapuolenKivet(int indeksi) {
        //0=ihminen, 1=kone 
        int vastapuolenIndeksi;
        if(indeksi>6){
        vastapuolenIndeksi = 12 - indeksi;
        } else{
        vastapuolenIndeksi = indeksi-12;
    }
        return vastapuolenIndeksi;
    }
//----------------------------------------------------------------------------------------
    
    public void machineMakeAmove(){
        
    }
//----------------------------------------------------------------------------------------    
    
//-----------------------------------------------------------------------------------------    
    public static void main(String[] args) {
        Board lauta = new Board();
        lauta.printBoard();        
        lauta.humanMakeAmove();
        lauta.printBoard();
        
    }
}
