/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3;

/**
 *
 * @author vedra
 */
public class ProvjeraParametara {
    
    public static void provjeri(String args[]){
        
           
         int brojRedaka = Integer.parseInt(args[0]);
        int brojStupaca = Integer.parseInt(args[1]);
        char podjelaOkvira = args[2].charAt(0);
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
            if (brojStupaca < 80 || brojStupaca > 160) {
                System.err.println("Broj stupaca mora biti u intervalu: 80 - 120");
                System.exit(0);
            }

            if (podjelaOkvira != 'V' && podjelaOkvira != 'O') {

                System.err.println("Podjela okvira V ili O");
                System.exit(0);
            }

            if (brSekundi < 1 || brSekundi > 120) {
                System.err.println("Broj sekundi drevtve mora biti u intervalu: 1-120");
                System.exit(0);
            }
        }
       
    }
}
