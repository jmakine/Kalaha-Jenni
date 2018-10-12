/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

/**
 * Ohjelma suoritetaan tästä luokasta.
 * @author Jenni
 */
public class Main {    
    
    public static void main(String[] args) throws InterruptedException {
        Game peli=new Game();
        Board b=new Board();
        peli.pelaa(b);
    }
    
}
