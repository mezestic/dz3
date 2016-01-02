/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3;

/**
 *
 * @author Melita
 */
public class T2_09_zadaca_3 {
    
    public static final String ANSI_ESC = "\033[";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        System.out.print(ANSI_ESC + "2J");
        int i = 1;
        int j = 80;
        for (; i < 38; i++) {
            prikazi(i, 2 * i, 31, "*");
        }
        for (; j > 1; j=j - 2) {
            prikazi(i, j, 32, "-");
        }
        for (; i > 1; i--) {
            prikazi(i, (80 - (2 * i)), 33, "+");
        }
        for (j = 80; j > 1;j=j - 2) {
            prikazi(i, j, 37, "#");
        }
        System.out.print(ANSI_ESC + "41;1f");
        System.out.print(ANSI_ESC + "31m" + "Crvena " + ANSI_ESC + "33m" + "Zelena " + ANSI_ESC + "32m" + "Plava" + 
                ANSI_ESC + "0m");
        for (int k=30; k < 38; k++) {
            prikazi(42, k - 29, k, "@");
        }
    }
    
    static void postavi(int x, int y) {
        System.out.print(ANSI_ESC + x + ";" + y + "f");
    }

    static void prikazi(int x, int y, int boja, String tekst) {
        postavi(x, y);
        System.out.print(ANSI_ESC + boja + "m");
        System.out.print(tekst);
        try {
            Thread.sleep(30);
        } catch (InterruptedException ex) {
        }
    }
    
}
