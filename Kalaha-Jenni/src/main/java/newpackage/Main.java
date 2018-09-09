package newpackage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.*;
/**
 *
 * @author Jenni
 */
public class Main {
    
    
        
    
public static void main(String[] args) {
    
    //kayttis myöhemmin;
    Scanner input = new Scanner(System.in);  
        
    //aloittaja arvotaan siten että pelaaja antaa syötteen 0 tai 1, jonka jälkeen kone arpoo satunnaisesti luvun 0 tai 1
    //jos pelaaja arvasi oikein, saa hän aloittaa, muuten kone aloittaa
    
        String aloittaja = new String();
        System.out.println("Arvotaan aloittaja");
        System.out.println("Valitse numero 0 tai 1");
        
    //pelaaja antaa luvun
        long pelaajanValinta = input.nextLong();                
        System.out.println(pelaajanValinta);
    //kone arpoo 0 tai 1    
        long luku = Math.round(Math.random());
        System.out.println("Kone arpoi: "+ luku);
        if (luku!=pelaajanValinta){
            aloittaja = "kone";
            System.out.println("Tietokone aloittaa");
        } else {
            aloittaja = "ihminen";
            System.out.println("Saat aloittaa");
    }
    
    //Peli alkaa:
    
    //Alustetaan ja tulostetaan pelilaudan alkuasetelma
    Board board = new Board();
    board.printBoard();

    //Ihminen aloittaa:
    if(aloittaja.matches("ihminen")){
        //System.out.println("Valitse kuppi, josta haluat tehdä siirron:");
        board.humanMakeAmove();
    }
        
    //tietokone aloittaa
    if(aloittaja.matches("kone")){
        board.machineMakeAmove();
    }
    
    }
        
}