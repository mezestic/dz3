/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3;

import hr.foi.uzdiz.t2_09.zadaca3.composite.AbstractComponent;
import hr.foi.uzdiz.t2_09.zadaca3.composite.FileComponent;
import hr.foi.uzdiz.t2_09.zadaca3.composite.FolderComponent;
import hr.foi.uzdiz.t2_09.zadaca3.iterator.FileRepository;
import hr.foi.uzdiz.t2_09.zadaca3.iterator.Iterator;
import hr.foi.uzdiz.t2_09.zadaca3.memento.Caretaker;
import hr.foi.uzdiz.t2_09.zadaca3.mvc.Controller;
import hr.foi.uzdiz.t2_09.zadaca3.mvc.Model;
import hr.foi.uzdiz.t2_09.zadaca3.mvc.View;
import java.io.File;
import static java.lang.Thread.sleep;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author vedran
 */
public class Dretva extends Thread {

    public boolean aktivna = true;
    public boolean runing = true;
    int interval;
    Controller controller;
    View view;
    Model model;
    Caretaker caretaker;

    private static volatile String output = "";

    public Dretva(int interval, Controller controller, View view, Model model, Caretaker caretaker) {
        this.controller = controller;
        this.interval = interval;
        this.view = view;
        this.model = model;
        this.caretaker = caretaker;
    }

    @Override
    public void interrupt() {
        if (aktivna) {
            view.cleanInputScreen();
            view.printLnToInput("DRETVA SE NE MOZE PREKINUTI JER RADI OBRADU");

        } else {
            super.interrupt(); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public void run() {
        view.setUnos(true);
        view.cleanPrimaryScreen();
        view.cleanSecondaryScreen();
        view.cleanInputScreen();
        view.printLnToInput("Dretva je pokrenuta...");
        view.printLnToInput("Pritisnite <ENTER> za povratak: ");

        while (runing) {
            long startTimer = System.currentTimeMillis();
            output = "";
            view.setColor("32");
            FolderComponent stari = model.getState();
            FolderComponent trenutni = kreirajStrukturu();

            if (compareScans(stari, trenutni)) {
                view.printLnToSecondary(output);
                // SPREMANJE STANJA JER IMA PROMJENE
                model.set(trenutni);
                caretaker.addMemento(model.saveToMemento());
            } else {
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                Date date = new Date();
                view.printLnToPrimary(dateFormat.format(date) + "\tNEMA PROMJENE U STRUKTURI");
            }
            stari = trenutni;
            aktivna = false;
            view.setCursorPos(view.getInputCursorPos());
            long trajanje = System.currentTimeMillis() - startTimer;

            try {
                sleep(interval * 1000 - trajanje);
            } catch (InterruptedException ex) {
                view.setUnos(false);
                view.cleanInputScreen();
                view.printLnToInput("Dretva je prekinuta...");
                view.printLnToInput("Pritisnite <ENTER> za povratak: ");
                runing = false;
            }
            aktivna = true;
        }
    }

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

        FileRepository namesRepository = new FileRepository(stari);
        for (Iterator iter = namesRepository.getIterator(); iter.hasNext();) {
            AbstractComponent ac = (AbstractComponent) iter.next();

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

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

    public FolderComponent kreirajStrukturu() {
        FolderComponent structure = new FolderComponent();
        kreirajStrukturu(T2_09_zadaca_3.direktorij, structure);
        return structure;
    }

    private void kreirajStrukturu(String dir, FolderComponent composite) {
        File[] listFile = new File(dir).listFiles();
        File file = new File(dir);
        for (File f : listFile) {
            if (f.isDirectory()) {
                FolderComponent child = new FolderComponent(f.getName(), "direktorij", new Date(f.lastModified()), f.length());
                composite.addChild(child);
                kreirajStrukturu(f.getAbsolutePath(), child);
            } else {
                composite.addChild(new FileComponent(f.getName(), "datoteka", new Date(f.lastModified()), f.length()));
            }
        }
    }
}
