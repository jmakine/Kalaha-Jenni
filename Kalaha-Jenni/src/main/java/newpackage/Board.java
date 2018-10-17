/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

/**
 * Luokassa Board on siirtojen suoritus sekä mahdollisten siirtojen simulointi.
 * Oikeat siirrot päivittävät varsinaisen pelilaudan asetelman, simuloiva siirto
 * päivittää "temp"-Boardia. Pelilaudan säännöt sisältyvät tämän luokan metodeihin.
 *
 * @version 1.0.
 * @author Jenni
 */
public class Board {

    /**
     *Pelilauta on 14-paikkainen lista.
     */
    public int[] lauta;
    int viimeinenKiviInd;

    /**
     * Kertoo, saako ko laudalla uutta vuoroa.
     */
    public boolean uusiVuoro;

    /**
     * Board-olion muuttuja, jolla simuloidaan siirtoja.
     */
    public Board temp;
    

    /**
     * Pelaaja 1 on aina ihminen.
     */
    public static final int HUMAN = 1;

    /**
     * Pelaaja 2 on aina tietokone.
     */
    public static final int COMPUTER = 2;

    /**
     * Konstruktoi Board-olion.
     */
    public Board() {

        this.lauta = new int[14];
        this.uusiVuoro = false;
        
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
     * Tulostaa laudan tilanteen.
     */
    public void printBoard() {
        System.out.println 
                ("       6 " + "  5 " + "  4 " + "  3 " + "  2 " + "  1   " + "\n"
                        + "     -------------------------" + "\n"
                        + "     | " + this.lauta[12] + " | " + this.lauta[11] + " | " + this.lauta[10] + " | " + this.lauta[9] + " | " + this.lauta[8] + " | " + this.lauta[7] + " |   " + "\n"
                        + " | " + this.lauta[13] + " |                       | " + this.lauta[6] + " |" + "\n"
                        + "     | " + this.lauta[0] + " | " + this.lauta[1] + " | " + this.lauta[2] + " | " + this.lauta[3] + " | " + this.lauta[4] + " | " + this.lauta[5] + " |    " + "\n"
                        + "     -------------------------" + "\n"
                        + "       1 " + "  2 " + "  3 " + "  4 " + "  5 " + "  6   ");
    }

//-----------TEKEE SIIRRON OIKEASTI-----------------------------------------------------------------------------
    /**
     * Päivittää varsinaisen laudan pelitilanteen pelaajan ja annetun indeksin
     * perusteella muuttaa this.uusiVuoro arvon true, jos siirrolla saa uuden
     * vuoron.
     *
     * @param indeksi, johon siirto tehdään
     * @param pelaaja, jonka siirto on kyseessä
     */
    
    public void teeSiirtoOikeasti(int indeksi, int pelaaja) { 

        int kiviaKipossa = this.lauta[indeksi];

        if (pelaaja == HUMAN) {

            this.viimeisenKivenIndeksi(indeksi);

            //tyhjennetään valittu kippo
            this.lauta[indeksi] = 0;

            //lasketaan kuinka monta kertaa mennään vastustajan mancalan yli
            if (indeksi + kiviaKipossa >= 13 && indeksi + kiviaKipossa < 27) {
                kiviaKipossa += 1;
            }
            if (indeksi + kiviaKipossa >= 27) {
                kiviaKipossa += 2;
            }

            //kaikki kiposta nostetut kivet jaetaan seuraaviin kippoihin, poislukien vastustajan mancala
            for (int i = indeksi + 1; i <= indeksi + kiviaKipossa; i++) {

                if (i < 13) {
                    this.lauta[i]++;
                }
                if (i > 13 && i < 27) {
                    this.lauta[i - 14]++;
                }
                if (i > 27) {
                    this.lauta[i - 28]++;
                } else;
            }

            viimeisenKivenToimenpiteet(HUMAN);

        } else if (pelaaja == COMPUTER) {

            //päivittää this.viimeinenKiviInd
            this.viimeisenKivenIndeksi(indeksi);

            //lasketaan kuinka monta kertaa mennään vastustajan mancalan yli
            if (indeksi + kiviaKipossa >= 20 && indeksi + kiviaKipossa < 34) {
                kiviaKipossa += 1;
            }
            if (indeksi + kiviaKipossa >= 34) {
                kiviaKipossa += 2;
            }

            this.lauta[indeksi] = 0;

            //kaikki kiposta nostetut kivet jaetaan seuraaviin kippoihin, poislukien vastustajan mancala
            for (int i = indeksi + 1; i <= indeksi + kiviaKipossa; i++) {
                if (i <= 13) {
                    this.lauta[i] += 1;
                }
                if ((i > 13 && i < 20) || (i > 20 && i <= 27)) {
                    this.lauta[i - 14] += 1;
                }
                if (i > 27 && i < 34) {
                    this.lauta[i - 28] += 1;
                } else;
            }

            viimeisenKivenToimenpiteet(COMPUTER);

        }

    }
    
//------------------OIKEASTI----------------------------------------

    /**
     *
     * @param pelaaja
     */
    public void viimeisenKivenToimenpiteet(int pelaaja) {
        int mancala;
        if (pelaaja == HUMAN) {
            mancala = 6;
        } else {
            mancala = 13;
        }

        if (this.viimeinenKiviInd == mancala) {
            this.uusiVuoro = true;
        } 
        else if (this.viimeinenKiviInd < mancala && this.lauta[this.viimeinenKiviInd] == 1) { //viimeisenInd =indeksi+kiviäKipossa
            int vastapuolenIndeksi = 12-this.viimeinenKiviInd;
            this.lauta[this.viimeinenKiviInd] = 0;
            this.lauta[mancala] += this.lauta[vastapuolenIndeksi] + 1;
            this.lauta[vastapuolenIndeksi] = 0;
            this.uusiVuoro = false;
        }
    }

//---------------------------SIMULAATIO--------------------------------------------------------
    
    /**
     * Jos viimeinen kivi osuu mancalaan tai tyhjään omaan kippoon, suoritetaan asiaan kuuluvat toimenpiteet.
     * @param pelaaja
     */
    public void viimeisenKivenToimenpiteetLeikisti(int pelaaja) {
        int mancala;
        if (pelaaja == 1) {
            mancala = 6;
        } else {
            mancala = 13;
        }

        if (this.viimeinenKiviInd == mancala) {
            this.temp.uusiVuoro = true;
        } 
        else if (this.viimeinenKiviInd < mancala && this.viimeinenKiviInd > 6 && this.temp.lauta[this.viimeinenKiviInd] == 1) {
            int vastapuolenIndeksi = 12-this.viimeinenKiviInd;
            this.temp.lauta[this.viimeinenKiviInd] = 0;
            this.temp.lauta[mancala] += this.temp.lauta[vastapuolenIndeksi] + 1;
            this.temp.lauta[vastapuolenIndeksi] = 0;
            this.temp.uusiVuoro = false;
        }
    }

//--------------SIMULOI TEHTÄVÄÄ SIIRTOA------------------------
    /**
     * Päivittää temp-lautaa annetun siirron mukaan. Ei muuta sen pelilaudan
     * tilannetta, jolle kutsu tehdään vaan kyseisen laudan temp-lautaa.
     * Päivittää this.temp.uusiVuoro=true jos siirrolla saa uuden vuoron.
     *
     * @param indeksi josta siirto tehdään
     * @param pelaaja pelaaja=1, kone=2
     *
     */
    public void teeSiirtoLeikisti(int indeksi, int pelaaja) {

        // int mancala;
        int kiviaKipossa = this.lauta[indeksi];

        this.temp = new Board();
        for (int i = 0; i < 14; i++) {
            int a = this.lauta[i];
            this.temp.lauta[i] = a;
        }

        this.viimeisenKivenIndeksi(indeksi);

        if (pelaaja == HUMAN) {

            //tyhjennetään valittu kippo
            this.temp.lauta[indeksi] = 0;

            if (indeksi + kiviaKipossa >= 13 && indeksi + kiviaKipossa < 27) {
                kiviaKipossa += 1;
            }
            if (indeksi + kiviaKipossa >= 27) {
                kiviaKipossa += 2;
            }

            //kaikki kiposta nostetut kivet jaetaan seuraaviin kippoihin
            for (int i = indeksi + 1; i <= indeksi + kiviaKipossa; i++) {

                if (i < 13) {
                    this.temp.lauta[i]++;
                }
                if (i > 13 && i < 27) {
                    this.temp.lauta[i - 14]++;
                }
                if (i > 27) {
                    this.temp.lauta[i - 28]++;
                } else;
            }

            viimeisenKivenToimenpiteetLeikisti(HUMAN);

        } else if (pelaaja == COMPUTER) {

            if (indeksi + kiviaKipossa >= 20 && indeksi + kiviaKipossa < 34) {
                kiviaKipossa += 1;
            }
            if (indeksi + kiviaKipossa >= 34) {
                kiviaKipossa += 2;
            }

            this.temp.lauta[indeksi] = 0;

            for (int i = indeksi + 1; i <= indeksi + kiviaKipossa; i++) {

                if (i <= 13) {
                    this.temp.lauta[i]++;
                }
                if ((i > 13 && i < 20) || (i > 20 && i <= 27)) {
                    this.temp.lauta[i - 14]++;
                }
                if (i > 27) {
                    this.temp.lauta[i - 28]++;
                } else;
            }

            viimeisenKivenToimenpiteetLeikisti(HUMAN);
        }
    }

//---------LASKEE PELIN LOPPUPISTEET------------------------------------------------------------------------------- 
    /**
     * Laskee pelilaudalle arvon.
     * @return palauttaa pelilaudan pistetilanteen + on tietokone johtaa/voitti,
     * - on pelaaja johtaa/voitti. Erotuksen suuruus vastaa lopputilanteen
     * piste-eroa. 
     */
    public int evaluate() {
        int value;
        int pelaajanPisteet = 0;
        int koneenPisteet = 0;
        for (int i = 0; i < 7; i++) {
            pelaajanPisteet += this.lauta[i];
        }
        for (int j = 7; j < 14; j++) {
            koneenPisteet += this.lauta[j];
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
        return indeksit;
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

        if (indeksi < 13 && indeksi > 6) { //tietokone siirtää
            if (indeksi + kivet > 13 && indeksi + kivet < 20) {
                viimeinenKiviInd = indeksi + kivet - 13;
            } else if (indeksi + kivet > 20 && indeksi + kivet <= 27) {
                viimeinenKiviInd = indeksi + kivet - 14;
            } else if (indeksi + kivet == 20 || indeksi + kivet == 34) { //vastustajan mancala
                viimeinenKiviInd = 7;
            } else if (indeksi + kivet <= 13) {
                viimeinenKiviInd = indeksi + kivet;
            } else if (indeksi + kivet > 20 && indeksi + kivet < 34) {
                viimeinenKiviInd = indeksi + kivet - 27;
            }
        } else if (indeksi < 6 && indeksi >= 0) { //pelaaja siirtää

            if (indeksi + kivet > 13 && indeksi + kivet < 27) {
                viimeinenKiviInd = indeksi + kivet - 14;
            } else if (indeksi + kivet < 13) { //vastustajan mancala
                viimeinenKiviInd = indeksi + kivet;
            } else if (indeksi + kivet == 13 || indeksi + kivet == 27) {
                viimeinenKiviInd = 0;
            } else if (indeksi + kivet > 27) {
                viimeinenKiviInd = indeksi + kivet - 27; //yhdessä kipossa alle 30kiveä
            }
        }
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        Board b = new Board();
        b.startBoard();
        b.startBoard();
        b.lauta[1] = 25;
        b.printBoard();
        int indeksi = 1;
        int pelaaja = 1;
        b.teeSiirtoOikeasti(indeksi, pelaaja);
        System.out.println(b.viimeinenKiviInd);
        b.printBoard();

        b.teeSiirtoLeikisti(7, 2);
        b.printBoard();
        b.temp.printBoard();
        System.out.println(b.temp.uusiVuoro);
        b.temp.teeSiirtoLeikisti(11, 2);
        b.temp.temp.printBoard();

    }
}
