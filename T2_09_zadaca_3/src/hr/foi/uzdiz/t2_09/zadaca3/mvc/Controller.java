/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3.mvc;

import hr.foi.uzdiz.t2_09.zadaca3.Dretva;
import hr.foi.uzdiz.t2_09.zadaca3.composite.AbstractComponent;
import hr.foi.uzdiz.t2_09.zadaca3.composite.FileComponent;
import hr.foi.uzdiz.t2_09.zadaca3.composite.FolderComponent;
import hr.foi.uzdiz.t2_09.zadaca3.memento.Caretaker;
import hr.foi.uzdiz.t2_09.zadaca3.memento.Memento;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author mezestic
 */
public class Controller {

    Dretva dt;
    private Model model;
    private View view;

    Caretaker caretaker = new Caretaker();

    private long brojDirektorija = 0;
    private long brojFajlova = 0;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        this.view.cleanScreen();
        // kreirajStrukturu();
    }

    public ArrayList<Memento> getMementos() {
        return caretaker.getSavedStates();
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
        this.model.set(structure);
        this.view.printStructure(model.getState(), "", true);
        caretaker.addMemento(model.saveToMemento());

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
                this.view.requestChoice();
                break;
            case "2":
                this.view.printStructure(model.getState(), "", false);
                this.view.requestChoice();
                break;
            case "3":
                dt = new Dretva(model.getBrojSekundi(), this, view);
                dt.start();
                this.view.requestChoice();
                break;
            case "4":
                dt.interrupt();
                this.view.requestChoice();
                break;
            case "5":
                //  ispis stanja - redni broj i vrijeme spremanja  
                // todo U načelu stvar funkcionira ali treba sloziti da dretva sprema stanja i da ih ovdje dohvatim (za sada imamo samo jedno spremljeno stanje - ono prilikom ucitavanja)
                ArrayList<Memento> mementos = getMementos();
                for (int i = 0; i < mementos.size(); i++) {
                    System.out.println(i + 1 + "\t" + mementos.get(i).getTimeOfSave());
                }
                this.view.requestChoice();
                break;
            case "6":
                // 
                this.view.requestChoice();
                break;
            default:
        }
    }

}
