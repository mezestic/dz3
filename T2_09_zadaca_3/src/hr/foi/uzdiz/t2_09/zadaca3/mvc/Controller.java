/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3.mvc;

import hr.foi.uzdiz.t2_09.zadaca3.composite.AbstractComponent;
import hr.foi.uzdiz.t2_09.zadaca3.composite.FileComponent;
import hr.foi.uzdiz.t2_09.zadaca3.composite.FolderComponent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mezestic
 */
public class Controller {

    private Model model;
    private View view;

    private long brojDirektorija = 0;
    private long brojFajlova = 0;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        this.view.cleanScreen();
        kreirajStrukturu();
    }

    public void run() {
        String choice = "";
        while (!choice.equalsIgnoreCase("Q")) {
            this.view.printMenu();
            choice = this.view.requestChoice();
            this.executeChoice(choice);
        }
    }

    public void kreirajStrukturu() {
        FolderComponent structure = new FolderComponent();
        kreirajStrukturu(model.getDirektorij(), structure);
        model.set(structure);
        view.ispisStrukture(model.getState(), "", true);
        //  caretaker.addMemento(m.saveToMemento());
    }

    private void kreirajStrukturu(String dir, FolderComponent composite) {
        File[] listFile = new File(dir).listFiles();
        File file = new File(dir);
        for (File f : listFile) {
            if (f.isDirectory()) {
                FolderComponent child = new FolderComponent(f.getName(), "direktorij", new Date(f.lastModified()), velicinaDirektorija(file));
                composite.addChild(child);
                kreirajStrukturu(f.getAbsolutePath(), child);
            } else {
                composite.addChild(new FileComponent(f.getName(), "datoteka", new Date(f.lastModified()), f.length()));
            }
        }
    }

    private long velicinaDirektorija(File directory) {
        long size = 0;
        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                size += file.length();
            } else {
                size += velicinaDirektorija(file);
            }
        }
        return size;
    }

    public void brojElemenata() {
        brojDirektorija = 0;
        brojFajlova = 0;
        izracunajBrojElemenata(model.getState());
    }

    public void izracunajBrojElemenata(FolderComponent composite) {
        for (AbstractComponent c : composite.children) {
            if (c.tip.equals("direktorij")) {
                brojDirektorija++;
                izracunajBrojElemenata((FolderComponent) c);
            } else {
                brojFajlova++;
            }
        }
    }

    private void executeChoice(String choice) {
        switch (choice) {
            case "1":
                brojElemenata();
                this.view.printNumberofElements(brojDirektorija, brojFajlova);
                choice = this.view.requestChoice();
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
