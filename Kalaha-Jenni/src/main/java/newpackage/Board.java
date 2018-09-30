/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    //List<Integer> lauta;
    int[] lauta;
    int viimeinenKiviInd;
    boolean uusiVuoro;
    Board temp;
    int pistetilanne;
    String pelitilanne;

    /**
     * Pelaaja 1 on aina ihminen.
     */
    public static final int HUMAN = 1;

    /**
     * Pelaaja 2 on aina tietokone.
     */
    public static final int COMPUTER = 2;
    
    public Board(){

        this.lauta=new int[14];
        this.uusiVuoro=false;
    }
    

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
       // this.pelitilanne "|" + this.lauta[13] + "|" + this.lauta[12] + "|" + this.lauta[11] + "|" + this.lauta[10] + "|" + this.lauta[9] + "|" + this.lauta[8] + "|" + this.lauta[7] + "\n"
         //       + "  |" + this.lauta[0] + "|" + this.lauta[1] + "|" + this.lauta[2] + "|" + this.lauta[3] + "|" + this.lauta[4] + "|" + this.lauta[5] + "|" + this.lauta[6] + "|");
        System.out.println
                        ("      6 " +"  5 " +"  4 " +"  3 "+"  2 " +"  1   "+ "\n"
                        + "    -------------------------" + "\n"                        
                        +"    | " + this.lauta[12] + " | " + this.lauta[11] + " | " + this.lauta[10] + " | " + this.lauta[9] + " | " + this.lauta[8] + " | " + this.lauta[7] +" |   " +"\n"
                + "| "+ this.lauta[13] +" |                       | " +  this.lauta[6] + " |" +"\n"
                                + "    | " +this.lauta[0] + " | " + this.lauta[1] + " | " + this.lauta[2] + " | " + this.lauta[3] + " | " + this.lauta[4] + " | " + this.lauta[5] + " |    " + "\n"
                       + "    -------------------------" + "\n"
                       +"      1 " +"  2 " +"  3 " +"  4 "+"  5 " +"  6   ");
            } 

//----------VASTAPUOLELLA OLEVAN KUPIN INDEKSI-----------------------------------------------------------
    /**
     *
     * @param indeksi jonka vastapuolen indeksin metodi palauttaa
     * @return annetun indeksin vastapuoleisen kupin indeksi
     */
    public int vastapuolenInd(int indeksi) {
        return 12 - indeksi;
    }

//-----------TEKEE SIIRRON OIKEASTI-----------------------------------------------------------------------------
    /**
     * Päivittää varsinaisen laudan pelitilanteen pelaajan ja annetun indeksin
     * perusteella muuttaa this.uusiVuoro arvon true, jos siirrolla saa uuden
     * vuoron.
     *
     * @param indeksi, johon siirto tehdään
     * @param pelaaja, jonka siirto on kyseessä
     * @return true, jos siirto on sallittu
     */
    public boolean teeSiirtoOikeasti(int indeksi, int pelaaja) {

        int kiviaKipossa = this.lauta[indeksi];
        int vastustajanMancala;
        int mancala;

        if (pelaaja == HUMAN) {
            mancala = 6;

            this.viimeisenKivenIndeksi(indeksi);

            if (this.lauta[indeksi] == 0) {
                System.out.println("Et voi tehdä siirtoa tyhjästä kiposta.");
                return false;
            } else if ((indeksi > 6 || indeksi < 0)) {
                System.out.println("Voit valita kipoista 1-6");
                return false;
            } else {
                //tyhjennetään valittu kippo
                this.lauta[indeksi] = 0;

                //kaikki kiposta nostetut kivet jaetaan seuraaviin kippoihin
                for (int i = indeksi + 1; i <= indeksi + kiviaKipossa; i++) {

                    if (i < 13 && i > 0) {
                        this.lauta[i]++;
                    } else if (i == 13) { //vastustajan mancala = 13 ja 27.                    
                        kiviaKipossa++;
                    } else if (i > 13) {
                        this.lauta[i - 14]++;
                    }
                }

            }

            if (this.viimeinenKiviInd == mancala) {
                this.uusiVuoro = true;
            } else if (this.viimeinenKiviInd < mancala && this.lauta[this.viimeinenKiviInd] == 1) { //viimeisenInd =indeksi+kiviäKipossa
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
            for (int i = indeksi + 1; i <= indeksi + kiviaKipossa; i++) {
                if (i <= 13 && i > 6) {
                    this.lauta[i]++;
                } else if (i == 20) { //vastustajan mancala = 20 ja 34.
                    kiviaKipossa++;
                } else if ((i > 13 && i < 20)|| (i>20)) {
                    this.lauta[i - 14]++;
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
        }

        return this.uusiVuoro;
    }
    

//--------------SIMULOI TEHTÄVÄÄ SIIRTOA------------------------
    /**
     * Päivittää temp-lautaa annetun siirron mukaan. Ei muuta sen pelilaudan
     * tilannetta, jolle kutsu tehdään vaan sen laudan temp-lautaa. Päivittää
     * this.temp.uusiVuoro=true jos siirrolla saa uuden vuoron.
     *
     * @param indeksi josta siirto tehdään
     * @param pelaaja pelaaja=1, kone=2
     *
     */
    public void teeSiirtoLeikisti(int indeksi, int pelaaja) {
       
        int mancala;
        this.temp=new Board();
        for(int i=0; i<14; i++){
            int a = this.lauta[i];
            this.temp.lauta[i]=a;
        }

        int kiviaKipossa = this.temp.lauta[indeksi];

        if (pelaaja == HUMAN) {
            
            mancala = 6;

            this.viimeisenKivenIndeksi(indeksi);

            this.temp.lauta[indeksi] = 0;

            //ja jaetaan kivet seuraaviin kippoihin (temp laudan)  
            //kaikki kiposta nostetut kivet jaetaan seuraaviin kippoihin
            //tässä oli i<=indeksi+1+kiviakipossa
            for (int i = indeksi + 1; i <= indeksi + kiviaKipossa; i++) {

                if (i < 13 && i > 0) {
                    this.temp.lauta[i]++;
                } else if (i == 13 || i==27) {
                    kiviaKipossa++;
                } else if (i > 13 && i < 27) {
                    this.temp.lauta[i - 14]++;
                }
            }

            if (this.viimeinenKiviInd == mancala) {
                this.temp.uusiVuoro = true;
            } //jos osuu tyhjään omaan kippoon, saa myös vastapuolen kipon kivet
            else if (this.viimeinenKiviInd < mancala && this.temp.lauta[this.viimeinenKiviInd] == 1) { //viimeisenInd =indeksi+kiviäKipossa
                int vastapuolenIndeksi = vastapuolenInd(this.viimeinenKiviInd);
                this.temp.lauta[this.viimeinenKiviInd] = 0;
                this.temp.lauta[mancala] += this.temp.lauta[vastapuolenIndeksi] + 1;
                this.temp.lauta[vastapuolenIndeksi] = 0;
                this.temp.uusiVuoro = false;
            }

        } else if (pelaaja == COMPUTER) {
            
            mancala = 13;

            this.viimeisenKivenIndeksi(indeksi);

            this.temp.lauta[indeksi] = 0;

            //kaikki kiposta nostetut kivet jaetaan seuraaviin kippoihin
            for (int i = indeksi + 1; i <= indeksi + kiviaKipossa; i++) {

                if (i > 6 && i<=13){                       
                    this.temp.lauta[i]++;
                } 
                else if((i>13 && i<20) || (i > 20 && i<=27)) {
                    this.temp.lauta[i-14]++;
                }
                else if (i == 20 || i==34) { //vastustajan mancala = 20 ja 34.
                    kiviaKipossa++;                                
                }

            if (this.viimeinenKiviInd == mancala) {
                this.temp.uusiVuoro = true;
            } //jos osuu tyhjään omaan kippoon, saa myös vastapuolen kipon kivet            
            else if (this.viimeinenKiviInd < mancala && this.viimeinenKiviInd > 6 && this.temp.lauta[this.viimeinenKiviInd] == 1) {
                int vastapuolenIndeksi = vastapuolenInd(this.temp.viimeinenKiviInd);
                this.temp.lauta[this.viimeinenKiviInd] = 0;
                this.temp.lauta[mancala] += this.temp.lauta[vastapuolenIndeksi] + 1;
                this.temp.lauta[vastapuolenIndeksi] = 0;
                this.temp.uusiVuoro = false;
            }
        }
    }
        
    }

//---------LASKEE PELIN LOPPUPISTEET------------------------------------------------------------------------------- 
    /**
     * @return palauttaa pelilaudan pistetilanteen + on tietokone johtaa/voitti,
     * - on pelaaja johtaa/voitti. Erotuksen suuruus vastaa lopputilanteen
     * piste-eroa. Laskee pelitilanteen arvon, vaikkei peli olisi ohi. Silloin
     * oman puolen kivet lasketaan omiksi pisteiksi.
     */
    public int evaluate() {
        int value;
        int pelaajanPisteet = 0;
        int koneenPisteet = 0;
        for (int i = 0; i < 7; i++) {
            pelaajanPisteet += this.lauta[i];
        }
        for (int j = 7; j < 14; j++) {
            koneenPisteet +=this.lauta[j];
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
        boolean ohiPelaaja = true;
        boolean ohiKone = true;
        boolean ohi = false;

        for (int i = 0; i < 6; i++) {
            if (this.lauta[i] != 0) {
                ohiPelaaja = false;
            }
        }

        for (int i = 7; i < 13; i++) {
            if (this.lauta[i] != 0) {
                ohiKone = false;
            }
        }

        if (ohiKone || ohiPelaaja) {
            ohi = true;
        }

        return ohi;
    }

//-------------------PELIN PÄÄTTYMINEN TIETYN PELAAJAN OSALTA-------------------------
    
    public boolean isGameOver(int pelaaja) {
        
        boolean ohi = true;

        if (pelaaja == HUMAN) {
            for (int i = 0; i < 6; i++) {
                if (this.lauta[i] != 0) {
                    ohi = false;
                }
            }
        }

        if (pelaaja == COMPUTER) {
            for (int i = 7; i < 13; i++) {
                if (this.lauta[i] != 0) {
                    ohi = false;
                }
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
     * Esim. [1, 1, 0, 0, 1, 0] tarkoittaa, että pelaaja saa siirtää 1., 2. ja
     * 5. kupistaan.
     */
    public int[] mahdollisetSiirrot(int pelaaja) {
        int[] indeksit = new int[6];

        if (pelaaja == COMPUTER) {
            for (int i = 12; i > 6; i--) {
                if (this.lauta[i] != 0) {
                    indeksit[12 - i] = 1;
                } else {
                    indeksit[12 - i] = 0;
                }
            }
        } else if (pelaaja == HUMAN) {
            for (int i = 0; i < 6; i++) {
                if (this.lauta[i] != 0) {
                    indeksit[i] = 1;
                } else {
                    indeksit[i] = 0;
                }
            }
        }
        return indeksit; //palauttaa annetun pelaajan siirrot int[6], esim [0, 1, 1, 1, 0, 1], jossa 0=ei mahdollinen, 1=mahdollinen
    }

//---------------LASKEE VIIMEISEN KIVEN INDEKSIN--------------------------------------  
// 
    /**
     * Päivittää this.viimeinenKiviInd arvon
     *
     * @param indeksi josta kivet tullaan siirtämään
     */
    public void viimeisenKivenIndeksi(int indeksi) {
        int kivet = this.lauta[indeksi];

        if ((indeksi < 13 && indeksi > 6) || (indeksi < 6 && indeksi >= 0)) { //esim. tietokone siirtää 12+5 -> 4, pelaaja siirtää 5+9 -> 1
            if (indeksi + kivet > 13) {
                viimeinenKiviInd = indeksi + kivet - 13;
            }
            if (indeksi + kivet < 13) {
                viimeinenKiviInd = indeksi + kivet;
            }
        }
    }
    
 

    public static void main(String[] args) {
        Board board = new Board();
        Board b = new Board();
        board.startBoard();
        b.lauta=board.lauta;
        // System.arraycopy(board.lauta, 0, b.lauta, 0, 14);        
        b.printBoard();
    }
}
