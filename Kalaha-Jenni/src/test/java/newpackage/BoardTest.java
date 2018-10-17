/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

import java.util.Arrays;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jenni
 */
public class BoardTest {

    static Board b;

    public BoardTest() {
    }

    @BeforeClass
    public static void setUp() {
        b = new Board();
    }

    /**
     * Test of teeSiirtoOikeasti method, of class Board.
     */
    @Test
    public void siirtoToimiiPienillaLuvuilla() {
        b.startBoard();
        int indeksi = 7;
        int pelaaja = 2;
        b.teeSiirtoOikeasti(indeksi, pelaaja);
        assertEquals(b.lauta[11], 5);
        b.teeSiirtoOikeasti(0, 1);
        assertEquals(b.lauta[1], 5);
    }

    /**
     * Test of teeSiirtoOikeasti method, of class Board.
     */
    @Test
    public void siirtoToimiiKoneenIsoillaLuvuilla() {
        b.startBoard();
        int indeksi = 7;
        b.lauta[indeksi] = 23;
        int pelaaja = 2;
        b.teeSiirtoOikeasti(indeksi, pelaaja);
        assertEquals(b.lauta[7], 1);

    }

    /**
     * Test of teeSiirtoOikeasti method, of class Board.
     */
    @Test
    public void siirtoToimiiPelaajanSuurillaLuvuilla() {
        b.startBoard();
        int indeksi = 2;
        b.lauta[indeksi] = 23;
        b.printBoard();
        int pelaaja = 1;
        b.teeSiirtoOikeasti(indeksi, pelaaja);
        b.printBoard();
        assertEquals(b.lauta[12], 6);
        assertEquals(b.lauta[0], 5);
    }

    /**
     * Test of teeSiirtoLeikisti method, of class Board.
     */
    @Test
    public void testTeeSiirtoLeikisti() {
        b.startBoard();
        int indeksi = 7;
        int pelaaja = 2;

        //Vain temp-lauta muuttuu simuloivan siirron aikana
        b.teeSiirtoLeikisti(indeksi, pelaaja);
        System.out.println("\tTemp-lauta leikisti-siirron indeksistä 7 jälkeen: ");
        b.temp.printBoard();
        System.out.println("\tVarsinainen pelilauta siirron indeksistä 7 jälkeen: ");
        b.printBoard();
        assertEquals(b.temp.lauta[11], 5);
        assertEquals(b.lauta[11], 4);
        b.temp.lauta[0] = 10;
        assertEquals(b.lauta[0], 4);
        
        //Tietokoneen vuoro, peli päättyy
        Board c = new Board();
        for (int i = 0; i < 14; i++) {
            c.lauta[i] = 0; //21
        }
        c.lauta[4] = 1;
        c.lauta[8] = 1;
        c.lauta[6] = 6;
        c.lauta[13] = 8;
        System.out.println("\tLauta ennen leikisti-siirtoa: ");
        c.printBoard();
        c.teeSiirtoLeikisti(8, 2);
        System.out.println("\tTemp-lauta siirron jälkeen: ");
        c.temp.printBoard();
        System.out.println("temp-lauta: " + Arrays.toString(c.temp.lauta));
        assertEquals(c.temp.lauta[9], 0);
        assertEquals(c.temp.isGameOver(), true);
        
        //Pelaajan vuoro, peli päättyy      
        for (int i = 0; i < 14; i++) {
            c.lauta[i] = 0; //21
        }
        c.lauta[4] = 1;
        c.lauta[8] = 1;
        c.lauta[6] = 6;
        c.lauta[13] = 8;
        System.out.println("\tLauta ennen leikisti-siirtoa: ");
        c.printBoard();
        c.teeSiirtoLeikisti(4, 1);
        System.out.println("\tTemp-lauta siirron jälkeen: ");
        c.temp.printBoard();
        assertEquals(c.temp.lauta[5], 0);
        assertEquals(c.temp.isGameOver(), true);

    }

    /**
     * Test of evaluate method, of class Board.
     */
    @Test
    public void testEvaluate() {
        b.startBoard();
        Board c = new Board();
        c.lauta[8] = 5;
        for (int i = 0; i < 8; i++) {
            c.lauta[i] = 3; //21
        }
        for (int j = 9; j < 14; j++) {
            c.lauta[j] = 1;
        }
        int pelaajanPisteet = 0;
        for (int i = 0; i < 8; i++) {
            pelaajanPisteet += c.lauta[i];
        }
        //int koneenPisteet = 23;
        int arvo = b.evaluate();
        assertEquals(0, arvo);
        assertEquals(pelaajanPisteet, 24);
        assertEquals(3, c.lauta[6]); 
    }

    /**
     * Test of isGameOver method, of class Board.
     */
    @Test
    public void testIsGameOver() {
        b.startBoard();
        for (int i = 0; i < 6; i++) {
            b.lauta[i] = 0;
        }

        assertEquals(true, b.isGameOver());

    }

    /**
     * Test of mahdollisetSiirrot method, of class Board.
     */
    @Test
    public void testMahdollisetSiirrot() {
        int pelaaja = 2;
        b.startBoard();
        for (int i = 7; i < 12; i++) {
            b.lauta[i] = 1;
        }
        b.lauta[12] = 0;
        int[] siirrot = new int[]{0, 1, 1, 1, 1, 1};

        int[] result = b.mahdollisetSiirrot(pelaaja);
        assertArrayEquals(siirrot, result);
    }

    /**
     * Test of viimeisenKivenIndeksi method, of class Board.
     */
    @Test
    public void testViimeisenKivenIndeksi() {
        int indeksi = 2;
        b.startBoard();        
        b.viimeisenKivenIndeksi(indeksi);
        assertEquals(b.viimeinenKiviInd, 6);
    }

   
}
