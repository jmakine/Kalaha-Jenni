/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

import java.util.ArrayList;
import java.util.List;

/**
 * Luokka Board suorittaa siirrot annetuilla parametreilla ja päivittää
 * pelilaudan. Tässä luokassa on myös pelin kannalta oleellinen
 * minimax-algoritmi, jonka avulla tietokoneen paras mahdollinen siirto kullekin
 * tilanteelle saadaan laskettua.
 *
 * @version 1.0.
 * @author Jenni
 */
public class Board {

    int[] lauta;

    /**
     *
     */
    public static final int HUMAN = 1;

    /**
     *
     */
    public static final int COMPUTER = 2;

    /**
     *
     */
    public int koneenSiirto;

//------------ASETTAA LAUDAN ALOITUSASETELMAAN-------------------------------------------------------------------
    /**
     * Asettaa laudalle pelin alkutilanteen: pienissä kupeissa 4 kiveä,
     * mmancalat tyhjät.
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
     * @param indeksi kupin indeksi, jonka vastapuolen kupin indeksin haluamme tietää
     * @return annetun indeksin vastapuoleisen kupin indeksin
     */
    public int vastapuolenKivet(int indeksi) {
        //0=ihminen, 1=kone 
        int vastapuolenIndeksi;
        if (indeksi > 6) {
            vastapuolenIndeksi = 12 - indeksi;
        } else {
            vastapuolenIndeksi = indeksi - 12;
        }
        return vastapuolenIndeksi;
    }

//-----------TEKEE SIIRRON-----------------------------------------------------------------------------
    /**
     * päivittää pelitilanteen pelaajan ja annetun indeksin perusteella
     *
     * @param indeksi, johon siirto tehdään
     * @param pelaaja, jonka siirto on kyseessä
     * @return true, jos siirto on sallittu
     */
    public boolean teeSiirto(int indeksi, int pelaaja) {

        boolean uusiVuoro = false;

        int kiviaKipossa = this.lauta[indeksi];
        int vastustajanMancala;
        int mancala;

        //viimeisen kiven indeksi (mahdollisesti yli taulukon indeksien)
        int viimeinenKiviInd = indeksi + kiviaKipossa;

        if (pelaaja == HUMAN) {
            vastustajanMancala = 13;
            mancala = 6;

            if (this.lauta[indeksi] == 0) {
                System.out.println("Et voi tehdä siirtoa tyhjästä kiposta.");
                return false;
            } else if ((indeksi > 6 || indeksi < 0) && pelaaja != 2) {
                System.out.println("Voit valita kipoista 1-6");
                return false;
            }

            do {

                if (viimeinenKiviInd >= vastustajanMancala) {
                    viimeinenKiviInd = vastustajanMancala - (indeksi + kiviaKipossa);
                }

                //tyhjennetään valittu kippo
                this.lauta[indeksi] = 0;

                //ja jaetaan kivet seuraaviin kippoihin                
                for (int i = indeksi + 1; i < indeksi + 1 + kiviaKipossa; i++) {

                    if (i < vastustajanMancala) {
                        this.lauta[i]++;
                    } else if (i > vastustajanMancala)//vastustajanMancala)
                    {
                        this.lauta[14 - i]++;
                    } else if (i == vastustajanMancala) {
                        kiviaKipossa += 1;
                    }
                }

                if (viimeinenKiviInd == mancala) {
                    uusiVuoro = true;

                } //jos osuu tyhjään omaan kippoon, saa myös vastapuolen kipon kivet
                else if (viimeinenKiviInd < mancala && this.lauta[viimeinenKiviInd] == 1) {
                    int vastustajanKivet = this.lauta[vastapuolenKivet(viimeinenKiviInd)];
                    this.lauta[viimeinenKiviInd] = 0;
                    this.lauta[mancala] += vastustajanKivet + 1;
                    this.lauta[vastapuolenKivet(viimeinenKiviInd)] = 0;
                }

            } while (uusiVuoro);

        } else if (pelaaja == COMPUTER) {
            vastustajanMancala = 6;
            mancala = 13;
            this.lauta[indeksi] = 0;
            uusiVuoro = false;

            do {
                if (viimeinenKiviInd > mancala) {
                    viimeinenKiviInd = indeksi + kiviaKipossa - mancala;
                }

                //kaikki kiposta nostetut kivet jaetaan seuraaviin kippoihin
                for (int i = indeksi + 1; i < indeksi + 1 + kiviaKipossa; i++) {
                    if (i <= mancala) {
                        this.lauta[i]++;
                    } //ei ole vastustajan mancala
                    else if (i > mancala && i != mancala + vastustajanMancala) {
                        this.lauta[i - 14]++;
                    } else if (i == mancala + vastustajanMancala) {
                        kiviaKipossa += 1;
                    } //jos osuu tyhjään omaan kippoon, saa myös vastapuolen kipon kivet
                    else if (viimeinenKiviInd > vastustajanMancala && viimeinenKiviInd != mancala && this.lauta[viimeinenKiviInd] == 1) {
                        int vastustajanKivet = this.lauta[vastapuolenKivet(viimeinenKiviInd)];
                        this.lauta[viimeinenKiviInd] = 0;
                        this.lauta[mancala] += vastustajanKivet + 1;
                        this.lauta[vastapuolenKivet(viimeinenKiviInd)] = 0;
                    } //saako tietokone uuden vuoron
                    else if (viimeinenKiviInd == mancala) {
                        uusiVuoro = true;
                    }
                }
            } while (uusiVuoro);
        }
        return true;
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
            pelaajanPisteet += this.lauta[i];
        }
        for (int j = 7; j < 13; j++) {
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
        if (mahdollisetSiirrot(HUMAN).isEmpty() || mahdollisetSiirrot(COMPUTER).isEmpty()) {
            return true;
        } else {
            return false;
        }

    }

//------------HAETAAN KAIKKI MAHDOLLISET SIIRROT---------------------------------------------------------------------------
    /**
     * seuraavaa siirtoa varten sallitut vaihtoehdot listana sallittujen
     * siirtojen indekseistä
     *
     * @param pelaaja (1=pelaaja, 2=tietokone)
     * @return lista annetun pelaajan mahdollisista siirroista
     */
    
    public List<Integer> mahdollisetSiirrot(int pelaaja) {
        List<Integer> indeksit = new ArrayList<>();

        if (pelaaja == COMPUTER) {
            for (int i = 12; i > 6; i--) {
                if (this.lauta[i] != 0) {
                    indeksit.add(i);
                }
            }
        } else if (pelaaja == HUMAN) {
            for (int i = 0; i < 6; i++) {
                if (this.lauta[i] != 0) {
                    indeksit.add(i);
                }
            }
        }
        return indeksit;
    }

//-------------MINIMAX ALGORITMI---------------------------------------------------------------------------- 
    /**
     * @param depth tutkittava syvyys
     * @param turn pelaaja(=1) vai tietokone(=2)
     * @return max pistetilanteen jos vuoro on tietokoneen ja min jos vuoro on
     * pelaajan
     */
    
    public int minimax(int depth, int turn) {

        List<Integer> sallitutSiirrot = mahdollisetSiirrot(turn);

        if (isGameOver() || sallitutSiirrot.isEmpty() || depth == 0) {
            return evaluate(); //palauttaa pistetilanteen
        }

        int min = Integer.MAX_VALUE; //pelaaja pyrkii minimoimaan 
        int max = Integer.MIN_VALUE; //tietokone maksimoimaan

        for (int i = 0; i < sallitutSiirrot.size(); i++) {
            int j = sallitutSiirrot.get(i);

            if (turn == COMPUTER) {
                teeSiirto(j, COMPUTER);
                int pistetilanne = minimax(depth + 1, HUMAN);
                max = Math.max(pistetilanne, max);

                if (depth == 0) {
                    System.out.println("Koneen siirto kupista: " + j + " saisi pisteiksi: " + pistetilanne);
                }

                if (pistetilanne >= 0) {
                    if (depth == 0) {
                        this.koneenSiirto = j;
                    }
                }

                if (j == sallitutSiirrot.size() - 1 && max < 0) {
                    if (depth == 0) {
                        this.koneenSiirto = j;
                    }
                }

            } else if (turn == HUMAN) {
                teeSiirto(j, HUMAN);
                int pistetilanne = minimax(depth + 1, COMPUTER);
                min = Math.min(pistetilanne, min);
                if (min < 0) {
                    break;
                }
            }
        }

        if (turn == COMPUTER) {
            return max;
        } else {
            return min;
        }
    }

//----------------------------------------------------------------------------------    
    /*public static void main(String[] args) {
        Board board = new Board();
        board.startBoard();
        System.out.println(board.minimax(2, HUMAN));
    }*/
}
