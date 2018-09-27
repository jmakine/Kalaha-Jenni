/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

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
        b=new Board();
    }
    
   
    /**
     * Test of vastapuolenInd method, of class Board.
     */
    @Test
    public void testVastapuolenIndToimii() {
        assertEquals(b.vastapuolenInd(0), 12);
    }

    /**
     * Test of teeSiirtoOikeasti method, of class Board.
     */
    @Test
    public void testTeeSiirtoOikeasti() {  
        b.startBoard();
        int indeksi = 7;
        int pelaaja = 2;
        
        b.teeSiirtoOikeasti(indeksi, pelaaja);
        assertEquals(b.lauta[11], 5);      
    }
    
    /**
     * Test of teeSiirtoOikeasti method, of class Board.
     */
    @Test
    public void testTeeSiirtoOikeasti2() {  
        b.startBoard();
        b.lauta[5]=14;
        int indeksi = 5;
        int pelaaja = 1;        
        b.teeSiirtoOikeasti(indeksi, pelaaja); //ind 5 + 10 => ind 5 = 1
        assertEquals(b.lauta[5], 1);     
    }

    /**
     * Test of teeSiirtoLeikisti method, of class Board.
     */
    @Test
    public void testTeeSiirtoLeikisti() {
        b.startBoard();
        int indeksi = 7;
        int pelaaja = 2;
        
        b.teeSiirtoLeikisti(indeksi, pelaaja);
        assertEquals(b.temp.lauta[11], 5); 
    }

    /**
     * Test of evaluate method, of class Board.
     */
    @Test
    public void testEvaluate() { 
        b.startBoard();
        int result = b.evaluate();
        assertEquals(0, result);
        
    }

    /**
     * Test of isGameOver method, of class Board.
     */
    @Test
    public void testIsGameOver() {
        
       // Board instance = new Board();
        b.startBoard();
        
        for(int i=0; i<6; i++){
        b.lauta[i]=0;
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
        int[] siirrot = new int[]{1,1,1,1,1,1};     
       for(int i=7; i<13; i++){
            b.lauta[i]=1;
        }
       //b.lauta[12]=0;
       int[] result = b.mahdollisetSiirrot(pelaaja);
        assertArrayEquals(siirrot, result);
    }

    /**
     * Test of viimeisenKivenIndeksi method, of class Board.
     */
    @Test
    public void testViimeisenKivenIndeksi() {        
        int indeksi = 5;
        b.startBoard();
        b.lauta[indeksi]=7; //tietokone siirtää
        //b.startBoard();
        b.viimeisenKivenIndeksi(indeksi);       
        assertEquals(b.viimeinenKiviInd, 12);
    }    
    
}
