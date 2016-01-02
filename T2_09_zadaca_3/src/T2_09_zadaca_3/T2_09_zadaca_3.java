/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package T2_09_zadaca_3;

/**
 *
 * @author Melita
 */
public class T2_09_zadaca_3 {

    /**
     * @param args the command line arguments
     */
    public static final String ANSI_ESC = "\033[";

    public static void main(String[] args) {

        int brojRedaka = Integer.parseInt(args[0]);
        int brojStupaca = Integer.parseInt(args[1]);
        String podjelaOkvira = args[2];
        String direktorij = args[3];
        int brSekundi = Integer.parseInt(args[4]);

        if (args.length != 5) {
            System.err.println("Pogresan broj parametara");
            System.exit(0);
        } else {
            if (brojRedaka < 24 || brojRedaka > 40) {
                System.err.println("Broj redaka mora biti u intervalu: 24 - 40");
                System.exit(0);
            }
            if (brojStupaca < 80 || brojStupaca > 120) {
                System.err.println("Broj stupaca mora biti u intervalu: 80 - 120");
                System.exit(0);
            }
            if (!podjelaOkvira.equals('V') || !podjelaOkvira.equals('O')) {
                System.err.println("Podjela okvira V ili O");
                System.exit(0);
            }
                if (brSekundi<1 || brSekundi>120) {
                System.err.println("Broj sekundi drevtve mora biti u intervalu: 1-120");
                System.exit(0);
            }
        }
        
        
        /*
         System.out.print(ANSI_ESC + "2J");
         int i = 1;
         int j = 80;
         for (; i < 38; i++) {
         prikazi(i, 2 * i, 31, "*");
         }
         for (; j > 1; j = j - 2) {
         prikazi(i, j, 32, "-");
         }
         for (; i > 1; i--) {
         prikazi(i, (80 - (2 * i)), 33, "+");
         }
         for (j = 80; j > 1; j = j - 2) {
         prikazi(i, j, 37, "#");
         }
         System.out.print(ANSI_ESC + "41;1f");
         System.out.print(ANSI_ESC + "31m" + "Crvena " + ANSI_ESC + "33m" + "Zelena " + ANSI_ESC + "32m" + "Plava"
         + ANSI_ESC + "0m");
         for (int k = 30; k < 38; k++) {
         prikazi(42, k - 29, k, "@");
         }
         */
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
