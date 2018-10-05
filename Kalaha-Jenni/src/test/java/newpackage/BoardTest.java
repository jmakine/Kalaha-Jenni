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
        b.lauta[indeksi]=23;
        int pelaaja = 2;        
        b.teeSiirtoOikeasti(indeksi, pelaaja);         
        assertEquals(b.lauta[7], 1);  
       
    }
    
    /**
     * Test of teeSiirtoOikeasti method, of class Board.
     */
    @Test
    public void siirtoToimiiPelaajanSuurillaLuvuilla(){
        b.startBoard();
        int indeksi = 2;
        b.lauta[indeksi]=23;
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
        
        b.teeSiirtoLeikisti(indeksi, pelaaja);
        b.temp.printBoard();
        b.printBoard();
        assertEquals(b.temp.lauta[11], 5); 
        assertEquals(b.lauta[11], 4);
        b.temp.lauta[0]=10;
        assertEquals(b.lauta[0], 4);
    }

    /**
     * Test of evaluate method, of class Board.
     */
    @Test
    public void testEvaluate() { 
        b.startBoard();
        Board c=new Board();
        c.lauta[8]=5;
        for(int i=0; i<8; i++){
            c.lauta[i]=3; //21
        }
        for(int j=9; j<14; j++){
            c.lauta[j]=1;//5+3+5=23
        }
        int pelaajanPisteet = 0;
        for(int i=0; i<8; i++){
            pelaajanPisteet += c.lauta[i];
        }
        int koneenPisteet = 23;
        //3,3,3,3,3,3,3,  5,1,1,1,1,1,1
        int resultB = b.evaluate();
       // int resultC = c.evaluate();
        assertEquals(0, resultB);
        assertEquals(pelaajanPisteet, 24);
        assertEquals(3, c.lauta[6]); //21
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
        for(int i=7; i<12; i++){
            b.lauta[i]=1;
        }
        b.lauta[12]=0;
       int[] siirrot = new int[]{0,1,1,1,1,1};     
        
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
        //b.lauta[indeksi]=4;
        //b.lauta[indeksi]=7; //tietokone siirtää
        //b.startBoard();
        b.viimeisenKivenIndeksi(indeksi);       
        assertEquals(b.viimeinenKiviInd, 6); //6
    }    
    
    @Test
    public void testPeliOhiPelaajalla(){
        b.startBoard();
        
        for(int i=0; i<6; i++){
        b.lauta[i]=0;
        }    
        
        assertEquals(true, b.isGameOver(1));
        assertEquals(false, b.isGameOver(2));
        
    }
    
}
