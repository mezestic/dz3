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
    public static String direktorij;
    public static int brojRedaka;
    public static int brojStupaca;
    public static char podjelaOkvira;
    public static int brSekundi;

    public static void main(String[] args) {

        ProvjeraParametara.provjeri(args);

        brojRedaka = Integer.parseInt(args[0]);
        brojStupaca = Integer.parseInt(args[1]);
        podjelaOkvira = args[2].charAt(0);
        direktorij = args[3];
        brSekundi = Integer.parseInt(args[4]);

        Model model = new Model(direktorij, brSekundi);
        View view = new View(podjelaOkvira == 'V', brojRedaka, brojStupaca);
        Controller controller = new Controller(model, view);
        controller.kreirajStrukturu();
        controller.run();
        
    }

}
