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
import hr.foi.uzdiz.t2_09.zadaca3.iterator.FileRepository;
import hr.foi.uzdiz.t2_09.zadaca3.iterator.Iterator;
import hr.foi.uzdiz.t2_09.zadaca3.layers.LowLevel;
import hr.foi.uzdiz.t2_09.zadaca3.memento.Caretaker;
import hr.foi.uzdiz.t2_09.zadaca3.memento.Memento;
import hr.foi.uzdiz.t2_09.zadaca3.visitor.Content;
import hr.foi.uzdiz.t2_09.zadaca3.visitor.ContentElementDoVisitor;
import hr.foi.uzdiz.t2_09.zadaca3.visitor.ContentVisitor;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
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

    boolean printPromjena = false;

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
        // SPREMANJE POCETNE STRUKTURE
        caretaker.addMemento(model.saveToMemento());
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
                File file2 = new File(f.getAbsolutePath());
                FolderComponent child = new FolderComponent(f.getName(), "direktorij", new Date(f.lastModified()), velicinaDirektorija(file2));
                composite.addChild(child);
                kreirajStrukturu(f.getAbsolutePath(), child);
            } else {
                composite.addChild(new FileComponent(f.getName(), "datoteka", new Date(f.lastModified()), f.length()));
            }
        }
    }

    private long velicinaDirektorija(File directory) {
        long length = 0;
        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                length += file.length();
            } else {
                length += velicinaDirektorija(file);
            }
        }
        return length;
    }

    public void brojElemenata() {
        brojDirektorija = 0;
        brojFajlova = 0;
        izracunajBrojElemenata(model.getState());
    }

    public void izracunajBrojElemenata(FolderComponent composite) {
        FileRepository namesRepository = new FileRepository(composite);
        for (Iterator iter = namesRepository.getIterator(); iter.hasNext();) {
            AbstractComponent c = (AbstractComponent) iter.next();
            if (c.tip.equals("direktorij")) {
                brojDirektorija++;
                izracunajBrojElemenata((FolderComponent) c);
            } else {
                brojFajlova++;
            }
        }
    }

    private void executeChoice(String choice) {
        ArrayList<Memento> savedMementos = this.getMementos();
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
                if (dt == null) {
                    dt = new Dretva(model.getBrojSekundi(), this, view, model, caretaker);
                    dt.start();
                } else {
                    this.view.printLnToInput("Dretva je već pokrenuta...");
                    this.view.printLnToInput("Pritisnite <ENTER> za povratak: ");
                }
                this.view.requestChoice();
                break;
            case "4":
                dt.interrupt();
                this.view.requestChoice();
                this.view.printLnToInput("Pritisnite <ENTER> za povratak: ");
                break;
            case "5":
                this.view.printLnToPrimary("-- ISPIS SPREMLJENIH STANJA --");
                ArrayList<Memento> mementos = getMementos();
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd:MM.yyyy");
                view.cleanPrimaryScreen();
                view.cleanSecondaryScreen();
                for (int i = 0; i < mementos.size(); i++) {
                    if (i == 0) {
                        view.printLnToPrimary(i + ".\tPocetno.\t" + dateFormat.format(mementos.get(i).getTimeOfSave()));

                    } else {
                        view.printLnToPrimary(i + ".\tPromjena\t" + dateFormat.format(mementos.get(i).getTimeOfSave()));
                    }
                }
                this.view.printLnToInput("Pritisnite <ENTER> za povratak: ");
                this.view.requestChoice();
                break;
            case "8":
                this.model.set(savedMementos.get(0).getSavedState());
                this.caretaker.setSavedStates(new ArrayList<Memento>(savedMementos.subList(0, 1)));
                this.view.printStructure(this.model.getState(), "", false);
                this.view.cleanInputScreen();
                this.view.printLnToInput("Pritisnite <ENTER> za povratak");
                this.view.requestChoice();
                break;
            case "9":
                this.view.cleanPrimaryScreen();
                this.view.cleanSecondaryScreen();
                this.view.printLnToPrimary("Vlastita funkcionalnost");

                this.view.printLnToInput("-> Izaberi opciju <-");
                this.view.printLnToInput("1) LAYERS: Upis strukture u datoteku");
                this.view.printLnToInput("2) VISITOR: Konvertiranje ukupne velicine i upis u datoteku");
                this.view.printLnToInput(" ");

                Scanner inputReader = new Scanner(System.in);
                String option = inputReader.nextLine();
                int optionInt = Integer.parseInt(option);

                // VISITOR
                LowLevel layer = new LowLevel();
                layer.setStructure(model.getState());
                layer.setView(view);
                layer.setOption(optionInt);
                layer.setLevel();
                layer.executeLayer();

                this.view.requestChoice();
                break;
            default:
                Pattern p = Pattern.compile("^(\\d) (\\d{1,3})$");
                Matcher m = p.matcher(choice);
                if (m.matches()) {
                    this.view.cleanPrimaryScreen();
                    switch (m.group(1)) {
                        case "6":
                            int newStateNum = Integer.parseInt(m.group(2)) - 1;
                            if (newStateNum < 0 || newStateNum >= savedMementos.size()) {
                                this.view.printLnToPrimary("Stanje s tim rednim brojem ne postoji!");
                            } else {
                                this.model.set(savedMementos.get(newStateNum).getSavedState());
                                this.view.printLnToInput("-- NOVO STANJE --");
                                this.view.printStructure(model.getState(), "", false);
                            }
                            break;
                        case "7":
                            int oldState = Integer.parseInt(m.group(2));
                            if (oldState < 0 || oldState >= savedMementos.size()) {
                                this.view.printLnToPrimary("Stanje s tim rednim brojem ne postoji!");
                            } else {
                                this.compareAndPrintScans(savedMementos.get(oldState).getSavedState(), this.model.getState());
                                if (!printPromjena) {
                                    this.view.printLnToPrimary("Strukture su identicne...");
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

    public void compareAndPrintScans(FolderComponent stari, FolderComponent trenutni) {
        this.compareAndPrintScans(stari, trenutni, new ArrayList<>(), "-> OBRISANO");
        this.compareAndPrintScans(trenutni, stari, new ArrayList<>(), "-> PREIMENOVANO/DODANO");
    }

    private void compareAndPrintScans(FolderComponent stari, FolderComponent trenutni, ArrayList<String> putanje, String poruka) {

        FileRepository namesRepository = new FileRepository(stari);
        for (Iterator iter = namesRepository.getIterator(); iter.hasNext();) {
            AbstractComponent ac = (AbstractComponent) iter.next();
            if (ac.tip.equals("direktorij")) {
                putanje.add(ac.ime);
                this.compareAndPrintScans((FolderComponent) ac, trenutni, putanje, poruka);
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
            printPromjena = false;
            int ret = Dretva.pronadji(trenutni, -1, putanje, 0, ac.vrijemePromjeneKreiranja);
            if (ret == -1) {
                printPromjena = true;
                text += poruka + "   ";
            } else if (ret == 1) {
                printPromjena = true;
                if (ac.tip.equals("direktorij")) {
                    text = "";
                } else {
                    text += "-> IZMJENJEN SADRZAJ" + "\n";
                }
            }
            if (printPromjena) {
                this.view.printLnToPrimary(text);
                text = "";
            }
            putanje.remove(putanje.size() - 1);
        }
    }
}
