/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3.layers;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pero Pero
 */
public class SaveLayer implements AdditionalFunctionalityInterface {

    @Override
    public void foo() {
        
        try {
            FileWriter writer;
            writer = new FileWriter("output.txt");
            writer.write("Ukupna velicina u MB: "+OperationLayer.getVelicina()+"MB \n");
            for (String str : OperationLayer.getLista()) {
                writer.write(str+"\n");
            }
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(SaveLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
