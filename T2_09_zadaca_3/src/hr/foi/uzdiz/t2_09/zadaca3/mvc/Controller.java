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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        
        // POCETNU STRUKTURU NE TREBA SPREMATI 
        //caretaker.addMemento(model.saveToMemento());
        //this.view.printStructure(caretaker.getMemento(0).getSavedState(), "", false);
    }

    public FolderComponent kreirajStrukturu(Boolean ret) {
        FolderComponent structure = new FolderComponent();
        kreirajStrukturu(model.getDirektorij(), structure);
        return structure;
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
                dt = new Dretva(model.getBrojSekundi(), this, view, model, caretaker);
                dt.start();
                this.view.requestChoice();
                break;
            case "4":
                dt.interrupt();
                this.view.requestChoice();
                break;
            case "5":
                ArrayList<Memento> mementos = getMementos();
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd:MM.yyyy");
                view.cleanPrimaryScreen();
                for (int i = 0; i < mementos.size(); i++) {
                    view.printLnToPrimary(i + 1 + ".\tPromjena\t" + dateFormat.format(mementos.get(i).getTimeOfSave()));
                }
                this.view.requestChoice();
                break;
            case "8":

                this.view.requestChoice();
                break;

            case "9":

                this.view.requestChoice();
                break;

            default:
                Pattern p = Pattern.compile("^(\\d) (\\d{1,3})$");
                Matcher m = p.matcher(choice);
                if (m.matches()) {

                    this.view.cleanPrimaryScreen();
                    ArrayList<Memento> savedMementos = this.getMementos();
                    switch (m.group(1)) {

                        case "6":
                            int newStateNum = Integer.parseInt(m.group(2));

                            if (newStateNum >= savedMementos.size()) {
                                this.view.printLnToPrimary("Stanje s tim rednim brojem ne postoji!");
                            } else {
                                this.model.set(savedMementos.get(newStateNum).getSavedState());
                                this.view.printStructure(model.getState(), "", false);
                            }
                            break;
                        case "7":

                            int oldState = Integer.parseInt(m.group(2));
                            if (oldState >= savedMementos.size()) {
                                this.view.printLnToPrimary("Stanje s tim rednim brojem ne postoji!");
                            } else {
                                FolderComponent trenutno = this.kreirajStrukturu(true);
                                FolderComponent staro = savedMementos.get(oldState).getSavedState();
                                //  String changeText = Controller.compareScans(staro, trenutno);
                                if (compareScans(staro, trenutno)) {
                                    this.view.cleanPrimaryScreen();
                                    this.view.printLnToPrimary(output);

                                }

                            }
                            break;
                    }
                    this.view.cleanInputScreen();
                    this.view.printLnToInput("Pritisnite <ENTER> za povratak");
                    this.view.requestChoice();
                }
        }
    }
    /*
     public static String compareScans(FolderComponent stari, FolderComponent trenutni) {
     String changeText = "";
     Controller.compareScans(stari, trenutni, changeText, new ArrayList<>(), "-> OBRISANO");
     Controller.compareScans(trenutni, stari, changeText, new ArrayList<>(), "-> PREIMENOVANO/DODANO");
     return changeText;
     }

     private static void compareScans(FolderComponent stari, FolderComponent trenutni, String changeText, ArrayList<String> putanje, String poruka) {
     for (AbstractComponent ac : stari.children) {
     if (ac.tip.equals("direktorij")) {
     putanje.add(ac.ime);
     compareScans((FolderComponent) ac, trenutni, changeText, putanje, poruka);
     putanje.remove(putanje.size() - 1);
     }
     putanje.add(ac.ime);
     String putanjaPuna = ".";
     for (String s : putanje) {
     putanjaPuna += "/" + s;
     }
     DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
     Date date = new Date();
     String text = dateFormat.format(date) + "   " + ac.tip + "   " + putanjaPuna;
     boolean print = false;
     int ret = Dretva.pronadji(trenutni, -1, putanje, 0, ac.vrijemePromjeneKreiranja);
     if (ret == -1) {
     print = true;
     text += poruka + "   ";
     } else if (ret == 1) {
     print = true;
     if (ac.tip.equals("direktorij")) {
     text = "";
     } else {
     text += "-> IZMJENJEN SADRZAJ" + "\n";
     }
     }
     if (print) {
     changeText += text;
     text = "";
     }
     putanje.remove(putanje.size() - 1);
     }
     }
     */
    private static volatile String output = "";

    public static boolean compareScans(FolderComponent stari, FolderComponent trenutni) {
        boolean promjena = false;
        if (compareScans(stari, trenutni, false, new ArrayList<>(), "-> OBRISANO")) {
            promjena = true;
        }
        if (compareScans(trenutni, stari, false, new ArrayList<>(), "-> PREIMENOVANO/DODANO")) {
            promjena = true;
        }
        return promjena;
    }

    private static boolean compareScans(FolderComponent stari, FolderComponent trenutni, boolean promjena, ArrayList<String> putanje, String poruka) {

        for (AbstractComponent ac : stari.children) {
            if (ac.tip.equals("direktorij")) {
                putanje.add(ac.ime);
                promjena = compareScans((FolderComponent) ac, trenutni, promjena, putanje, poruka);
                putanje.remove(putanje.size() - 1);
            }
            putanje.add(ac.ime);
            String putanjaPuna = ".";
            for (String s : putanje) {
                putanjaPuna += "/" + s;
            }
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            String text = dateFormat.format(date) + "   " + ac.tip + "   " + putanjaPuna;
            boolean print = false;
            int ret = pronadji(trenutni, -1, putanje, 0, ac.vrijemePromjeneKreiranja);
            if (ret == -1) {
                promjena = true;
                print = true;
                text += poruka + "   ";
            } else if (ret == 1) {
                promjena = true;
                print = true;
                if (ac.tip.equals("direktorij")) {
                    text = "";
                } else {
                    text += "-> IZMJENJEN SADRZAJ" + "\n";
                }
            }
            if (print) {
                output += text;
                text = "";
            }
            putanje.remove(putanje.size() - 1);
        }
        return promjena;
    }

    public static int pronadji(FolderComponent file, int pronadjen, ArrayList<String> putanja, int index, Date zadnjaPromjena) {
        String findName = putanja.get(index);
        for (AbstractComponent fileEntry : file.children) {
            if (fileEntry.ime.equals(findName)) {
                if ((index + 1) == putanja.size()) {
                    if (fileEntry.vrijemePromjeneKreiranja.equals(zadnjaPromjena)) {
                        return 0;
                    } else {
                        return 1;
                    }
                } else if (fileEntry.tip.equals("direktorij")) {
                    pronadjen = pronadji((FolderComponent) fileEntry, pronadjen, putanja, index + 1, zadnjaPromjena);
                } else {
                    return -1;
                }
            }
        }
        return pronadjen;
    }
}
