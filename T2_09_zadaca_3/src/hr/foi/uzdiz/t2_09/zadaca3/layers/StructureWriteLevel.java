/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3.layers;

import hr.foi.uzdiz.t2_09.zadaca3.composite.AbstractComponent;
import hr.foi.uzdiz.t2_09.zadaca3.composite.FolderComponent;
import hr.foi.uzdiz.t2_09.zadaca3.iterator.FileRepository;
import hr.foi.uzdiz.t2_09.zadaca3.iterator.Iterator;
import hr.foi.uzdiz.t2_09.zadaca3.mvc.View;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vedra
 */
public class StructureWriteLevel implements ILayer {
    
    FolderComponent structure;
    View view;
    
    StructureWriteLevel(FolderComponent structure, View view) {
        this.structure = structure;
        this.view = view;
    }
    
    @Override
    public void akcija() {
        
        File file = new File("D:\\structureOutput.txt");
        double converted = 0;
       
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(file));
            ispisStrukture(structure, "", file, bw);
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(StructureWriteLevel.class.getName()).log(Level.SEVERE, null, ex);
        }
        view.printLnToPrimary(" ");
        view.printLnToPrimary("FileSaver:");
        view.printLnToPrimary("Struktura spremljena u datoteku: structureOutput.txt");
    }
    
    private void ispisStrukture(FolderComponent composite, String tab, File file, BufferedWriter bw) {
        // ITERATOR ZA PROLAZENJE KROZ STRUKTURU
        FileRepository namesRepository = new FileRepository(composite);
        for (Iterator iter = namesRepository.getIterator(); iter.hasNext();) {
            AbstractComponent c = (AbstractComponent) iter.next();
            
            try {
                bw.write(String.format("%-40s", tab + c.ime) + String.format("%-12s", c.tip) + "  " + new SimpleDateFormat("HH:mm:ss").format(c.vrijemePromjeneKreiranja) + "   " + c.velicina + " B");
                bw.write("\n");
            } catch (IOException ex) {
                Logger.getLogger(StructureWriteLevel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (c.tip.equals("direktorij")) {
                ispisStrukture((FolderComponent) c, tab + "   ", file, bw);
            }
        }
    }
    
}
