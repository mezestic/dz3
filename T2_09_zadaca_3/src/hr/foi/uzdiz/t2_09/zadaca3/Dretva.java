/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3;

import hr.foi.uzdiz.t2_09.zadaca3.composite.AbstractComponent;
import hr.foi.uzdiz.t2_09.zadaca3.composite.FileComponent;
import hr.foi.uzdiz.t2_09.zadaca3.composite.FolderComponent;
import hr.foi.uzdiz.t2_09.zadaca3.mvc.Controller;
import hr.foi.uzdiz.t2_09.zadaca3.mvc.Model;
import hr.foi.uzdiz.t2_09.zadaca3.mvc.View;
import java.io.File;
import static java.lang.Thread.sleep;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vedra
 */
public class Dretva extends Thread {

    public boolean aktivna = true;
    public boolean runing = true;
    int interval;
    Controller c;

    private static volatile String threadOutputBuffer = "";

    public Dretva(int interval, Controller controller) {
        this.c = controller;
        this.interval = interval;
    }

    @Override
    public void interrupt() {
        if (aktivna) {
            System.out.println("DRETVA RADI PA SE NE MOZE PREKINUTI");
        } else {
            super.interrupt(); //To change body of generated methods, choose Tools | Templates.
        }

    }

    @Override
    public void run() {
        
      //  MVC
       //  Model model = new Model("",22);
       // View view = new View(true, 22, 22);
       // Controller controller = new Controller(model, view);
        
        
        while (runing) {
            long startTimer = System.currentTimeMillis();

            System.out.println("Dretva pokrenuta!");

            threadOutputBuffer = "";

            FolderComponent old = Model.getState();
            FolderComponent recent = kreirajStrukturu();

         //    ispisStrukture(old, "", false);
            //         ispisStrukture(recent, "", false);
            if (compareScans(old, recent, 1)) {
                System.out.println("IMA PROMJENE");
                //  Backup.addToBackup(Reader.rootFolder);
                //   Reader.rootFolder = recent;
                System.out.println(threadOutputBuffer);

            } else {
                System.out.println("NEMA PROMJENE"); // ISPISATI NA ZASLON 2 Ako nije bilo promjene ispisuje se na 1. prozoru da nije bilo promjene (vrijeme, tekst).
                //  ScanThread.setThreadOutputBuffer(Reader.getCurrentTime() + "\t" + "Nema promjene u strukturi\n");
            }

            //   todo ZAPAMTITI STANJE
            long trajanje = System.currentTimeMillis() - startTimer;

            System.out.println("SPAVA!");
            aktivna = false;
            try {
                sleep(interval * 1000 - trajanje);
            } catch (InterruptedException ex) {
                System.out.println("KRAJ DRETVE");
                runing = false;
                //todo ispis na zaslon stanje -> pogledati u uputama
            }
            aktivna = true;
        }

    }

    public static boolean compareScans(FolderComponent old, FolderComponent recent, int bufferIndex) {
        boolean changed = false;

    //    if (compareScans(old, recent, false, new ArrayList<>(), "- obrisano", bufferIndex)) {
        //       changed = true;
        //    }
        if (compareScans(recent, old, false, new ArrayList<>(), "-> PREIMENOVANO", bufferIndex)) {
            changed = true;
        }

        return changed;
    }

    private static boolean compareScans(FolderComponent old, FolderComponent recent, boolean changed, ArrayList<String> path, String mess, int bufferIndex) {

        for (AbstractComponent fileEntry : old.children) {
            if (fileEntry.tip.equals("direktorij")) {
                path.add(fileEntry.ime);
                changed = compareScans((FolderComponent) fileEntry, recent, changed, path, mess, bufferIndex);

                //  System.out.println("Changed0: "+changed);
                path.remove(path.size() - 1);
            }
            path.add(fileEntry.ime);
            String fullPath = ".";
            for (String s : path) {
                fullPath += "/" + s;
            }

            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
            Date date = new Date();
          
            String text = dateFormat.format(date) + "\t" + fullPath + "\t";
            boolean print = false;

            int ret = findInPath(recent, -1, path, 0, fileEntry.vrijemePromjeneKreiranja);
            //  System.out.println("RETURENED: "+ret);
            if (ret == -1) {
                changed = true;
                //  System.out.println("Changed1: "+changed);
                print = true;
                text += mess + "\n";
                //   break;
            } else if (ret == 1 && mess.charAt(0) == '-') {
                changed = true;
                print = true;
                if (fileEntry.tip.equals("direktorij")) {
                    text = "";

                } else {
                    text += "* IZMJENJEN SADRZAJ FAJLA" + "\n";
                    //  break;
                }
               //   text += "* izmjenjeno" + "\n";

            } // Ako je došlo do bilo koje promjene u odnosu na prethodni sadržaj strukture potrebno je u 2. prozoru ispisati informaciju (vrijeme provjere, putanja, naziv elementa, vrsta promjene).

            if (print) {
                threadOutputBuffer += text;
                switch (bufferIndex) {
                    case 1:
                  //      ScanThread.setThreadOutputBuffer(text);
                        //  threadOutputBuffer+=text;
                        break;
                    case 2:
                        threadOutputBuffer += text;
                        //    Reader.setOutputBuffer(text);
                        break;
                }
                text = "";
            }
            path.remove(path.size() - 1);

        }
       //   */ 
        // System.out.println("Changed OVERAL: "+changed);
        return changed;
    }

    private static int findInPath(FolderComponent file, int found, ArrayList<String> path, int index, Date lastMod) {
        String findName = path.get(index);
        for (AbstractComponent fileEntry : file.children) {
            if (fileEntry.ime.equals(findName)) {
                if ((index + 1) == path.size()) {
                    if (fileEntry.vrijemePromjeneKreiranja.equals(lastMod)) {
                        return 0;
                    } else {
                        System.out.println("MOD");
                        return 1;
                    }
                } else if (fileEntry.tip.equals("direktorij")) {
                    found = findInPath((FolderComponent) fileEntry, found, path, index + 1, lastMod);
                } else {
                    System.out.println("DEL");

                    return -1;
                }

            }
        }
        return found;
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

    public FolderComponent kreirajStrukturu() {
        FolderComponent structure = new FolderComponent();
        kreirajStrukturu(T2_09_zadaca_3.direktorij1, structure);
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

    public void ispisStrukture(FolderComponent composite, String tab, boolean updateSecond) {
        DecimalFormat myFormatter = new DecimalFormat("###,###.###");
        for (AbstractComponent c : composite.children) {

            System.out.println(String.format("%-50s", tab + c.ime) + String.format("%-15s", c.tip) + "   " + new SimpleDateFormat("HH:mm:ss  dd-MM-yyyy").format(c.vrijemePromjeneKreiranja) + "   " + myFormatter.format(c.velicina) + " B");

            if (c.tip.equals("direktorij")) {
                ispisStrukture((FolderComponent) c, tab + "   ", updateSecond);
            }
        }
    }
}
