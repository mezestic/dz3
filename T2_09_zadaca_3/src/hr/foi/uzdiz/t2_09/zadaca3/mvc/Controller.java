/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3.mvc;

import hr.foi.uzdiz.t2_09.zadaca3.composite.AbstractComponent;
import hr.foi.uzdiz.t2_09.zadaca3.composite.FileComponent;
import hr.foi.uzdiz.t2_09.zadaca3.composite.FolderComponent;
import hr.foi.uzdiz.t2_09.zadaca3.iterator.FileRepository;
import hr.foi.uzdiz.t2_09.zadaca3.iterator.Iterator;
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
        kreirajStrukturu();
    }

    public void kreirajStrukturu() {
        FolderComponent structure = new FolderComponent();
        kreirajStrukturu(model.getDirektorij(), structure);
        System.out.println("--> Struktura direktorija ucitana <--");
        model.set(structure);
        //  caretaker.addMemento(m.saveToMemento());
    }

    private void kreirajStrukturu(String dir, FolderComponent composite) {
        File[] listFile = new File(dir).listFiles();
        long ukupna_velicina = 0;
        for (File f : listFile) {

            if (f.isDirectory()) {
                FolderComponent child = new FolderComponent(f.getName(), "direktorij", new Date(f.lastModified()), ukupna_velicina);
                composite.addChild(child);
                kreirajStrukturu(f.getAbsolutePath(), child);
            } else {
                ukupna_velicina += f.length();
                composite.addChild(new FileComponent(f.getName(), "datoteka", new Date(f.lastModified()), f.length()));
            }
        }
    }

    public void run() {
        String choice = "";
        this.view.cleanScreen();
        while (!choice.equalsIgnoreCase("Q")) {
            this.view.printMenu();
            choice = this.view.requestChoice();
            this.executeChoice(choice);
        }
    }

    private void executeChoice(String choice) {
        switch (choice) {
            case "1":
                break;
            case "2":
                this.view.printStructure(model.getState(), "");

                /*
             //   PREKO ITERATORA
                FileRepository namesRepository = new FileRepository(model.getState());
                for (Iterator iter = namesRepository.getIterator(); iter.hasNext();) {
                    AbstractComponent ac = (AbstractComponent) iter.next();
                    System.out.println(ac.ime);
                    System.out.println(ac.tip);
                     System.out.println(ac.vrijemePromjeneKreiranja);
                    System.out.println(ac.velicina);
                }
*/
                choice = this.view.requestChoice();
                break;
            default:

        }
    }

}
