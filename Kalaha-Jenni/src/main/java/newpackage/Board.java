/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

import java.util.ArrayList;
import java.util.List;

/**
 * Luokassa Board on siirtojen suoritus sekä mahdollisten siirtojen simulointi.
 * Oikeat siirrot päivittävät varsinaisen pelilaudan asetelman, simuloiva siirto
 * päivittää väliaikaista "temp"-Boardia. Pelilaudan toiminnallisuus sääntöineen
 * on tässä luokassa.
 *
 * @version 1.0.
 * @author Jenni
 */
public class Board {

    //int turn;
    int[] lauta;
    int viimeinenKiviInd;
    boolean uusiVuoro;
    Board temp;
    int pistetilanne;

    /**
     * Pelaaja 1 on aina ihminen.
     */
    public static final int HUMAN = 1;

    /**
     * Pelaaja 2 on aina tietokone.
     */
    public static final int COMPUTER = 2;

    
//------------ASETTAA LAUDAN ALOITUSASETELMAAN-------------------------------------------------------------------
    /**
     * Asettaa laudalle pelin alkutilanteen: pienissä kupeissa 4 kiveä, mancalat
     * tyhjiä.
     */
    public void startBoard() {
        this.lauta = new int[]{4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 0};
    }

//---------TULOSTAA LAUDAN-----------------------------------------------------------------------  
    /**
     * Tulostaa laudan sen hetkisen tilanteen.
     */
    public void printBoard() {
        System.out.println("|" + this.lauta[13] + "|" + this.lauta[12] + "|" + this.lauta[11] + "|" + this.lauta[10] + "|" + this.lauta[9] + "|" + this.lauta[8] + "|" + this.lauta[7] + "\n"
                + "  |" + this.lauta[0] + "|" + this.lauta[1] + "|" + this.lauta[2] + "|" + this.lauta[3] + "|" + this.lauta[4] + "|" + this.lauta[5] + "|" + this.lauta[6] + "|");
    }

//----------VASTAPUOLELLA OLEVAN KUPIN INDEKSI-----------------------------------------------------------
    /**
     *
     * @param indeksi kupin indeksi, jonka vastapuolen kupin indeksin haluamme
     * tietää
     * @return annetun indeksin vastapuoleisen kupin indeksin
     */
    public int vastapuolenInd(int indeksi) {

        int vastapuolenIndeksi = 12-indeksi;
       /* if (indeksi > 6 && indeksi < 13) {
            vastapuolenIndeksi = 12-indeksi;

        } else if (indeksi < 6 && indeksi > -1) {
            vastapuolenIndeksi = 12 - indeksi;
        }*/
        return vastapuolenIndeksi;
    }

//-----------TEKEE SIIRRON OIKEASTI-----------------------------------------------------------------------------
    /**
     * Päivittää varsinaisen laudan pelitilanteen pelaajan ja annetun indeksin
     * perusteella muuttaa this.uusiVuoro arvon true, jos siirrolla saa uuden
     * vuoron. Lopuksi muuttaa temp-laudan vastaamaan uutta pelitilannetta.
     *
     * @param indeksi, johon siirto tehdään
     * @param pelaaja, jonka siirto on kyseessä
     * @return true, jos siirto on sallittu
     */
    public boolean teeSiirtoOikeasti(int indeksi, int pelaaja) { //ind 6 ja pelaaja 2 =kone

        // boolean uusiVuoro = false;
        int kiviaKipossa = this.lauta[indeksi];
        int vastustajanMancala;
        int mancala;

        if (pelaaja == HUMAN) {
            vastustajanMancala = 13;
            mancala = 6;

            this.viimeisenKivenIndeksi(indeksi);

            if (this.lauta[indeksi] == 0) {
                System.out.println("Et voi tehdä siirtoa tyhjästä kiposta.");
                return false;
            } 
            else if ((indeksi > 6 || indeksi < 0)) {
                System.out.println("Voit valita kipoista 1-6");
                return false;
            } 
            else {
                //tyhjennetään valittu kippo
                this.lauta[indeksi] = 0;
            

                //kaikki kiposta nostetut kivet jaetaan seuraaviin kippoihin
            for (int i = indeksi + 1; i <= indeksi+1 + kiviaKipossa; i++) {
                if (i < 13 && i>0){
                    this.lauta[i]++;
                } 
                else if(i == 13){ //vastustajan mancala = 13 ja 27.                    
                    kiviaKipossa++;
                }
                else if (i > 13) {
                    this.lauta[i - 14]++;                
            }
            }
            }
             /*   //ja jaetaan kivet seuraaviin kippoihin                
                for (int i = indeksi + 1; i <= indeksi + kiviaKipossa; i++) {

                    if (i < vastustajanMancala) {
                        this.lauta[i]++;
                    } else if (i > vastustajanMancala) {
                        this.lauta[i - 14]++;
                    } else if (i == vastustajanMancala) {
                        kiviaKipossa += 1;
                    }
                }
            }*/

            if (this.viimeinenKiviInd == mancala) {
                this.uusiVuoro = true;
            }
            
            else if (this.viimeinenKiviInd < mancala && this.lauta[this.viimeinenKiviInd] == 1) { //viimeisenInd =indeksi+kiviäKipossa
                int vastapuolenIndeksi = vastapuolenInd(this.viimeinenKiviInd);
                this.lauta[this.viimeinenKiviInd] = 0;
                this.lauta[mancala] += this.lauta[vastapuolenIndeksi] + 1;
                this.lauta[vastapuolenIndeksi] = 0;
                this.uusiVuoro = false;
            }
            

        } else if (pelaaja == COMPUTER) {
            vastustajanMancala = 6;
            mancala = 13;

            this.viimeisenKivenIndeksi(indeksi);

            this.lauta[indeksi] = 0;

            //kaikki kiposta nostetut kivet jaetaan seuraaviin kippoihin
            for (int i = indeksi + 1; i <= 1+indeksi + kiviaKipossa; i++) {
                if (i <= 13 && i>6){
                    this.lauta[i]++;
                } 
                else if(i == 20){ //vastustajan mancala = 20 ja 34.
                    kiviaKipossa++;
                }
                else if (i > 13 && i < 20) {
                    this.lauta[i - 14]++;
                }
                else if(i>20){
                    this.lauta[i-14]++; 
                }
            }

            if (this.viimeinenKiviInd == mancala) {
                this.uusiVuoro = true;
            } //jos osuu tyhjään omaan kippoon, saa myös vastapuolen kipon kivet            
            
            else if (this.viimeinenKiviInd < mancala && this.viimeinenKiviInd > vastustajanMancala && this.lauta[this.viimeinenKiviInd] == 1) {
                int vastapuolenIndeksi = vastapuolenInd(this.viimeinenKiviInd);
                this.lauta[this.viimeinenKiviInd] = 0;
                this.lauta[mancala] += this.lauta[vastapuolenIndeksi] + 1;
                this.lauta[vastapuolenIndeksi] = 0;
                this.uusiVuoro = false;
            }
        }
        //päivittää temp-laudan ajantasalle
        Board tmp = this;
        this.temp = tmp;
        return this.uusiVuoro;
    }

//--------------SIMULOI TEHTÄVÄÄ SIIRTOA------------------------
    /**
     * Päivittää temp-lautaa (eli ei varsinaista oikeaa pelitilannetta.
     * Päivittää uusiVuoro=true jos siirrolla saa uuden vuoron.
     *
     * @param indeksi josta siirto tehdään
     * @param pelaaja kone vai pelaaja
     *
     */
    
    /*public void teeSiirtoLeikisti(int indeksi, int pelaaja) {
        //this.temp.uusiVuoro = false;
        //temp.lauta[indeksi]=this.lauta[indeksi];
        int vastustajanMancala;
        int mancala;
        this.temp=this;

        int kiviaKipossa = lauta[indeksi];

        if (pelaaja == HUMAN) {
            vastustajanMancala = 13;
            mancala = 6;

            this.viimeisenKivenIndeksi(indeksi, kiviaKipossa);

            this.lauta[indeksi] = 0;

            //ja jaetaan kivet seuraaviin kippoihin (temp laudan)               
            for (int i = indeksi + 1; i <= indeksi + kiviaKipossa; i++) {

                if (i < vastustajanMancala) {
                    this.lauta[i]++;
                } else if (i > vastustajanMancala) {
                    this.lauta[i - 14]++;
                } else if (i == vastustajanMancala) {
                    kiviaKipossa += 1;
                }
            }

            if (this.viimeinenKiviInd == mancala) {
                this.uusiVuoro = true;
            } 
            //jos osuu tyhjään omaan kippoon, saa myös vastapuolen kipon kivet
            else if (this.viimeinenKiviInd < mancala && this.lauta[this.viimeinenKiviInd] == 1) { //viimeisenInd =indeksi+kiviäKipossa
                int vastapuolenIndeksi = vastapuolenInd(this.viimeinenKiviInd);
                this.lauta[this.viimeinenKiviInd] = 0;
                this.lauta[mancala] += this.lauta[vastapuolenIndeksi] + 1;
                this.lauta[vastapuolenIndeksi] = 0;
                this.uusiVuoro = false;
            }

        } else if (pelaaja == COMPUTER) {
            vastustajanMancala = 6;
            mancala = 13;

            this.viimeisenKivenIndeksi(indeksi, kiviaKipossa);

            this.lauta[indeksi] = 0;

            //kaikki kiposta nostetut kivet jaetaan seuraaviin kippoihin
            for (int i = indeksi + 1; i <= indeksi + kiviaKipossa; i++) {
                if (i <= 13 || i > 20) {
                    this.lauta[i]++;
                } //ei ole vastustajan mancala
                else if (i > 14 && i < 7) {
                    this.lauta[i - 14]++;
                } else if (i == 20) {
                    kiviaKipossa += 1;
                }
            }

            if (this.viimeinenKiviInd == mancala) {
                this.uusiVuoro = true;
            } //jos osuu tyhjään omaan kippoon, saa myös vastapuolen kipon kivet            
            else if (this.viimeinenKiviInd < mancala && this.viimeinenKiviInd > vastustajanMancala && this.lauta[this.viimeinenKiviInd] == 1) {
                int vastapuolenIndeksi = vastapuolenInd(this.temp.viimeinenKiviInd);
                this.lauta[this.viimeinenKiviInd] = 0;
                this.lauta[mancala] += this.lauta[vastapuolenIndeksi] + 1;
                this.lauta[vastapuolenIndeksi] = 0;
                this.uusiVuoro = false;
            }
        }
    
    }*/
    
    public void teeSiirtoLeikisti(int indeksi, int pelaaja) {
        //this.temp.uusiVuoro = false;
        //temp.lauta[indeksi]=this.lauta[indeksi];
        int vastustajanMancala;
        int mancala;
        this.temp=this;

        int kiviaKipossa = lauta[indeksi];

        if (pelaaja == HUMAN) {
            vastustajanMancala = 13;
            mancala = 6;

            this.viimeisenKivenIndeksi(indeksi);

            this.temp.lauta[indeksi] = 0;

            //ja jaetaan kivet seuraaviin kippoihin (temp laudan)  
                //kaikki kiposta nostetut kivet jaetaan seuraaviin kippoihin
            for (int i = indeksi + 1; i <= indeksi+1 + kiviaKipossa; i++) {
                if (i < 13 && i>0){
                    this.temp.lauta[i]++;
                } 
                else if(i == 13){ //vastustajan mancala = 13 ja 27.                    
                    kiviaKipossa++;
                }
                else if (i > 13) {
                    this.temp.lauta[i - 14]++;                
            }
            }
            

            if (this.temp.viimeinenKiviInd == mancala) {
                this.temp.uusiVuoro = true;
            } 
            //jos osuu tyhjään omaan kippoon, saa myös vastapuolen kipon kivet
            else if (this.temp.viimeinenKiviInd < mancala && this.temp.lauta[this.temp.viimeinenKiviInd] == 1) { //viimeisenInd =indeksi+kiviäKipossa
                int vastapuolenIndeksi = vastapuolenInd(this.temp.viimeinenKiviInd);
                this.temp.lauta[this.temp.viimeinenKiviInd] = 0;
                this.temp.lauta[mancala] += this.lauta[vastapuolenIndeksi] + 1;
                this.temp.lauta[vastapuolenIndeksi] = 0;
                this.temp.uusiVuoro = false;
            }

        } else if (pelaaja == COMPUTER) {
            vastustajanMancala = 6;
            mancala = 13;

            this.viimeisenKivenIndeksi(indeksi);

            this.temp.lauta[indeksi] = 0;

            //kaikki kiposta nostetut kivet jaetaan seuraaviin kippoihin
                        //kaikki kiposta nostetut kivet jaetaan seuraaviin kippoihin
                        //kaikki kiposta nostetut kivet jaetaan seuraaviin kippoihin
            for (int i = indeksi + 1; i <= 1+indeksi + kiviaKipossa; i++) {
                if (i <= 13 && i>6){
                    this.temp.lauta[i]++;
                } 
                else if(i == 20){ //vastustajan mancala = 20 ja 34.
                    kiviaKipossa++;
                }
                else if (i > 13 && i < 20) {
                    this.temp.lauta[i - 14]++;
                }
                else if(i>20){
                    this.temp.lauta[i-14]++; 
                }
            }
            
            /*for (int i = indeksi + 1; i <= indeksi + kiviaKipossa; i++) {
                if (i <= 13 || i > 20) {
                    this.temp.lauta[i]++;
                } //ei ole vastustajan mancala
                else if (i > 14 && i < 7) {
                    this.temp.lauta[i - 14]++;
                } else if (i == 20) {
                    kiviaKipossa += 1;
                }
            }*/

            if (this.temp.viimeinenKiviInd == mancala) {
                this.temp.uusiVuoro = true;
            } //jos osuu tyhjään omaan kippoon, saa myös vastapuolen kipon kivet            
            else if (this.temp.viimeinenKiviInd < mancala && this.temp.viimeinenKiviInd > vastustajanMancala && this.temp.lauta[this.temp.viimeinenKiviInd] == 1) {
                int vastapuolenIndeksi = vastapuolenInd(this.temp.viimeinenKiviInd);
                this.temp.lauta[this.temp.viimeinenKiviInd] = 0;
                this.temp.lauta[mancala] += this.temp.lauta[vastapuolenIndeksi] + 1;
                this.temp.lauta[vastapuolenIndeksi] = 0;
                this.temp.uusiVuoro = false;
            }
        }
    }
    
    //-----------------PERUU LAUDALLE TEHDYN SIIRRON---------------
    
     public void peruSiirto() {
            Board nyt= this.temp;
            this.lauta=nyt.lauta;
        }
    

//---------LASKEE PELIN LOPPUPISTEET------------------------------------------------------------------------------- 
    /**
     * @return palauttaa pelilaudan pistetilanteen + on tietokone johtaa/voitti,
     * - on pelaaja johtaa/voitti. Erotuksen suuruus vastaa lopputilanteen
     * piste-eroa. Laskee arvon, vaikkei peli olisi ohi. Silloin oman puolen
     * kivet lasketaan omiksi pisteiksi.
     */
    public int evaluate() {
        int value = 0;
        int pelaajanPisteet = this.lauta[6];
        int koneenPisteet = this.lauta[13];
        for (int i = 0; i < 6; i++) {
            pelaajanPisteet =+ this.lauta[i];
        }
        for (int j = 7; j < 13; j++) {
            koneenPisteet =+ this.lauta[j];
        }
        value = koneenPisteet - pelaajanPisteet;
        return value;
    }

//----------TARKASTAA ONKO PELI PÄÄTTYNYT------------------------------------------------------------------------------
    /**
     * Peli on ohi, kun jomman kumman pelaajan kupit on tyhänä.
     *
     * @return ture jos pelissä ei ole enää mahdollisia siirtoja
     */
    public boolean isGameOver() {
        boolean ohi=false;
        
        for(int i=0; i<6; i++){
            if(this.lauta[i]==0){
                ohi=true;
            }
        }
        
        for(int i=7; i<13; i++){
            if(this.lauta[i]==0){
                ohi=true;
            }
        }        
        return ohi;
    }

//------------HAETAAN KAIKKI MAHDOLLISET SIIRROT---------------------------------------------------------------------------
    /**
     * seuraavaa siirtoa varten sallitut vaihtoehdot listana sallittujen
     * siirtojen indekseistä
     *
     * @param pelaaja (1=pelaaja, 2=tietokone)
     * @return 6-paikkainen lista annetun pelaajan mahdollisista siirroista. 
     * Esim. tietokoneen mahdollisten siirtojen lista[0] tarkoittaa, että tietokone saa siirtää kupistaan 1, eli laudan indeksistä 7.
     */
    public int[] mahdollisetSiirrot(int pelaaja) {
        int[] indeksit = new int[6];

        if (pelaaja == COMPUTER) {
            for (int i = 12; i > 6; i--) {
                if (this.lauta[i] != 0) {
                    indeksit[12-i]=1;
                } else indeksit[12-i]=0;
            }
        } else if (pelaaja == HUMAN) {
            for (int i = 0; i < 6; i++) {
                if (this.lauta[i] != 0) {
                    indeksit[i]=1;
                } else indeksit[i]=0;
            }
        }
        return indeksit; //palauttaa annetun pelaajan siirrot int[6], esim [0, 1, 1, 1, 0, 1], jossa 0=ei mahdollinen, 1=mahdollinen
    }

    

//---------------LASKEE VIIMEISEN KIVEN INDEKSIN--------------------------------------  
//päivittää annetun Board:n viimeisenKivenIndeksi 
    /**
     * 
     * @param indeksi josta kivet tullaan siirtämään
     */
    
    /*public void viimeisenKivenIndeksi(int indeksi, int kivet) {
        //katsotaan, mihin indeksiin viimeinen kivi menee
        if (indeksi<13 && indeksi>6) { //eli jos pelaaja on tietokone
            if ((indeksi + kivet) % 13 == 0) {
                viimeinenKiviInd = 13; //oma mancala
            } else if ((indeksi + kivet) % 13 == 6) {
                viimeinenKiviInd = 7; //vastustajan mancalasta seuraava oma kuppi
            } else if ((indeksi + kivet) % 13 != 0 && (indeksi + kivet) > 13 && (indeksi + kivet) % 13 < 6) {
                viimeinenKiviInd = (indeksi + kivet) % 13 - (Math.floorDiv(indeksi + kivet, 13));
            } else if ((indeksi + kivet) % 13 != 0 && (indeksi + kivet) > 13 && (indeksi + kivet) % 13 > 6) {
                viimeinenKiviInd = (indeksi + kivet) % 13 + 1 - (Math.floorDiv(indeksi + kivet, 13));
            }
        } else {
            if ((indeksi + kivet) % 13 == 6) {
                viimeinenKiviInd = 6; //oma mancala
            } else if ((indeksi + kivet) % 13 == 0) {
                viimeinenKiviInd = 0; //vastustajan mancalasta seuraava oma kuppi
            } else if ((indeksi + kivet) % 13 != 6 && (indeksi + kivet) > 13 && (indeksi + kivet) % 13 < 7) {
                viimeinenKiviInd = (indeksi + kivet) % 13 - (Math.floorDiv(indeksi + kivet, 13));
            } else if ((indeksi + kivet) % 13 != 0 && (indeksi + kivet) > 13 && (indeksi + kivet) % 13 > 7) {
                viimeinenKiviInd = (indeksi + kivet) % 13 + 1 - (Math.floorDiv(indeksi + kivet, 13));
            }
        }*/
    public void viimeisenKivenIndeksi(int indeksi) {
        int kivet=this.lauta[indeksi];
        
        if (indeksi<13 && indeksi>6) { //tietokone siirtää 12+5 -> 3
            if(indeksi+kivet>13){
                viimeinenKiviInd=indeksi+kivet-14;
            }
            if(indeksi+kivet<13){
                viimeinenKiviInd=indeksi+kivet;
            }
        }
        
        if(indeksi<6 && indeksi>=0){
            if(indeksi+kivet>13){
                viimeinenKiviInd=indeksi+kivet-13; //pelaaja siirtää 5+9 -> 1
            }
            if(indeksi+kivet<13){
                viimeinenKiviInd=kivet+indeksi;
            }
        }
    }

}




