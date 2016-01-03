/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3;

import hr.foi.uzdiz.t2_09.zadaca3.mvc.Controller;
import hr.foi.uzdiz.t2_09.zadaca3.mvc.Model;
import hr.foi.uzdiz.t2_09.zadaca3.mvc.View;

/**
 *
 * @author T2_09
 */
public class T2_09_zadaca_3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ProvjeraParametara.provjeri(args);

        int brojRedaka = Integer.parseInt(args[0]);
        int brojStupaca = Integer.parseInt(args[1]);
        char podjelaOkvira = args[2].charAt(0);
        String direktorij = args[3];
        int brSekundi = Integer.parseInt(args[4]);

        Model model = new Model(direktorij);
        View view = new View(podjelaOkvira == 'V', brojRedaka, brojStupaca);
        Controller controller = new Controller(model, view);

        controller.run();
    }

//    static void postavi(int x, int y) {
//        System.out.print(ANSI_ESC + x + ";" + y + "f");
//    }
//
//    static void prikazi(int x, int y, int boja, String tekst) {
//        postavi(x, y);
//        System.out.print(ANSI_ESC + boja + "m");
//        System.out.print(tekst);
//        try {
//            Thread.sleep(30);
//        } catch (InterruptedException ex) {
//        }
//    }
}
