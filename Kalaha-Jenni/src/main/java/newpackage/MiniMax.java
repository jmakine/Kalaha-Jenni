/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

// A simple java program to find maximum score that 
// maximizing player can get. 
import java.io.*;

class MiniMax {

// Returns the optimal value a maximizer can obtain. 
// depth is current depth in game tree. 
// nodeIndex is index of current node in scores[]. 
// isMax is true if current move is of maximizer, else false 
// scores[] stores leaves of Game tree. 
// h is maximum height of Game tree 
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
      
//------------------------------------PELAAJA IHMINEN-----------------  
        if (pelaaja == 1 && !lauta.isGameOver(pelaaja)) {

            //alustetaan minimit -> 1000
            for (k = 0; k < 6; k++) {
                minimit[k] = 1000;
            }

            for (k = 0; k < 6; k++) {
                lisaminimit[k] = 1000;
            }
            

            //kopioidaan pelitilanne alipuihin
            //for(k=0; k<5; k++){
            //for (i = 0; i < 14; i++) {
                
                //aliPuut[i].uusiVuoro=false;
                //lauta.temp=lauta;
                //if(aliPuut[i].i) {
                //} else {
                    //if(lauta.isGameOver(pelaaja))
              //      aliPuut[k].lauta[i] = lauta.lauta[i];
               // }
            //}
        //}
        for (i = 0; i < 6; i++) {
                aliPuut[i] = lauta;
            }

            //tutkitaan jokainen mahdollinen siirto
            for (i = 0; i < 6; i++) {

                int[] aliPuunSiirrot = aliPuut[i].mahdollisetSiirrot(pelaaja); //[0,0,1,1,0,1], ind 0->5

                int siirto = aliPuunSiirrot[i];

                if (siirto == 1) { //siirto sallittu

                    aliPuut[i].teeSiirtoLeikisti(i, pelaaja); //päivittää aliPuut[i].temp -lautaa

                    //jos siirrolla ei saa uutta vuoroa, ja vastustajan puolella on vielä kiviä, siirtyy vuoro toiselle pelaajalle
                    if (!aliPuut[i].uusiVuoro && !aliPuut[i].isGameOver(pelaaja+1)) {

                        maksimit = minimax(pelaaja + 1, aliPuut[i]); //tietokoneen vuoro palauttaa maksimit

                        //pelaaja pyrkii etsimään palautetuista pisteistä pienimmän
                        for (k = 0; k < 6; k++) {
                            if (maksimit[k] < minimit[i]) {
                                minimit[i] = maksimit[k];
                            }
                        }

                    }
                    //jos pelaaja saa uuden siirron, eikä peli ole ohi

                    while (aliPuut[i].uusiVuoro && !aliPuut[i].isGameOver(pelaaja)) { //niin kauan, kun pelaaja saa uuden vuoron

                        lisaminimit = minimax(pelaaja, aliPuut[i]); //pelaaja päivittää maksimitauluun uuden vuoron jälkeiset pisteet (minimit)

                        for (k = 0; k < 6; k++) {
                            if (lisaminimit[k] < minimit[k]) { //jos lisäkierroksen jälkeen pistetilanne on pelaajan kannalta parempi, päivitetään se maksimeihin
                                minimit[k] = lisaminimit[k];
                            }
                        }
                    }

                    if (aliPuut[i].isGameOver(pelaaja)) {
                        minimit[i] = aliPuut[i].evaluate();

                    }

                } else if (siirto == 0) {
                  minimit[i]=+1000;//aliPuut[i].temp.evaluate();
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

                    if (!aliPuut[i].uusiVuoro && !aliPuut[i].isGameOver(pelaaja-1)) {

                       minimit = minimax(pelaaja - 1, aliPuut[i]); //ihmisen minimax palauttaa minimipisteet kullekkin siirrolle

                        //kone etsii suurimman palautetuista minimeistä
                        for (k = 0; k < 6; k++) {
                            if (minimit[k] > maksimit[i]) { //max=-1000
                                maksimit[i] = minimit[k];
                            }
                        }

                    }

                    while (aliPuut[i].uusiVuoro == true && !aliPuut[i].isGameOver(pelaaja)) {

                        lisamaksimit = minimax(pelaaja, aliPuut[i]);

                        //haetaan näistä suurin
                        for (k = 0; k < 6; k++) {
                            if (lisamaksimit[k] > maksimit[i]) {
                                maksimit[i] = lisamaksimit[k];
                            }
                        }
                    }
                    
                    for(i=0;i<6;i++){
                    lisamaksimit[i]=-1000;
                    }

                    if (aliPuut[i].isGameOver(pelaaja)) { //jos peli on ohi tämän vuoron jälkeen, lasketaan sen arvo                       
                        maksimit[i] = aliPuut[i].evaluate();                        
                        //koneenVuoro=true;
                    }
                } //sallittu siirto suoritettu

                else if (siirto == 0) {
                    maksimit[i] = -1000;//aliPuut[i].evaluate();
                }

            } //for loppuu, eli kaikki siirrot käyty läpi              
            return maksimit;
            
        }

        if (lauta.isGameOver(1)) {
            return minimit;
        } else 
        {//if(lauta.temp.isGameOver(2)){
            return maksimit;
                    }
    

    }//MINIMAX
    
 

} //CLASS MiniMax
