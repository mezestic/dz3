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
        for (File f : listFile) {
            if (f.isDirectory()) {
                FolderComponent child = new FolderComponent(f.getName(), "direktorij", new Date(f.lastModified()),f.length());
                composite.addChild(child);
                kreirajStrukturu(f.getAbsolutePath(), child);
            } else {
                composite.addChild(new FileComponent(f.getName(), "datoteka", new Date(f.lastModified()), f.length()));
            }
        }
    }

    public void ispisStrukture() {

        ispisStrukture(model.getState(), "");
    }

    private void ispisStrukture(FolderComponent composite, String tab) {
        for (AbstractComponent c : composite.children) {
            System.out.println(String.format("%-60s", tab + c.ime) + String.format("%-15s", c.tip) + "   " + c.vrijemePromjeneKreiranja + "   " + c.velicina);
            if (c.tip.equals("direktorij")) {
                ispisStrukture((FolderComponent) c, tab + "   ");
            }
        }
    }

    public void run() {
        String choice = "";
        while (!choice.equalsIgnoreCase("Q")) {
          //  this.view.cleanScreen();
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
                System.out.println("ISPIS STRUKTURE");
                ispisStrukture();
                break;
            default:

        }
    }

}
