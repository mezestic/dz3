/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3.mvc;

import hr.foi.uzdiz.t2_09.zadaca3.composite.AbstractComponent;
import hr.foi.uzdiz.t2_09.zadaca3.composite.FolderComponent;
import java.io.File;
import java.util.Date;

/**
 *
 * @author mezestic
 */
public class Controller {
    
    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        createStructure();
    }
    
    
    
    public void createStructure(){
        FolderComponent structure = new FolderComponent();
        createStructure(model.getDirektorij(), structure);
      //  m.set(structure);
      //  caretaker.addMemento(m.saveToMemento());
    }
     private void createStructure(String dir, FolderComponent composite) {
   /*     File[] listFile = new File(dir).listFiles();
        for (File f : listFile) {
            if (f.isDirectory()) {
                Composite child = new Composite(f.getName(), "directory", new Date(f.lastModified()), f.length());
                composite.addChild(child);
                createStructure(f.getAbsolutePath(), child);
            } else {
                composite.addChild(new Leaf(f.getName(), "file", new Date(f.lastModified()), f.length()));
            }
        }
           */
    }
    
    public void run() {
        String choice = "";
        while(!choice.equalsIgnoreCase("Q")) {
            this.view.cleanScreen();
            this.view.printMenu();
            choice = this.view.requestChoice();
            
            this.executeChoice(choice);
        }
    }

    private void executeChoice(String choice) {
        switch (choice) {
            case "":
                
                break;
            default:
                
        }
    }
    
    
}
