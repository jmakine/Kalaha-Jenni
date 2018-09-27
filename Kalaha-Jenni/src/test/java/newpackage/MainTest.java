/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jenni
 */
public class MainTest {
    
    static Board b; 
    
    public MainTest() {
    }
        
    @BeforeClass
    public static void setUp() {
        b=new Board();
        b.startBoard();
    }
    
    
    
    /**
     * Test of minimax method, of class Main.
     */
    @Test
    public void testMinimax() {
        System.out.println("minimax");
        int pelaaja = 2; 
        b.printBoard();
        for(int i=0; i<5;i++){
            b.lauta[i]=0;
            }
        for(int i=8; i<13;i++){
            b.lauta[i]=0;
            }
        b.lauta[5]=1;
        b.lauta[6]=10;
        b.lauta[7]=1;
        b.lauta[13]=20;
        int[] expResult = new int[]{21, 21, 21, 21, 21, 21};
        int[] result = Main.minimax(pelaaja, b);
        assertArrayEquals(expResult, result);
        
    }
    
}
