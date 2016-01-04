/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3;

import hr.foi.uzdiz.t2_09.zadaca3.composite.FolderComponent;
import hr.foi.uzdiz.t2_09.zadaca3.mvc.Controller;
import hr.foi.uzdiz.t2_09.zadaca3.mvc.Model;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
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
        while (runing) {
            long startTimer = System.currentTimeMillis();

            System.out.println("Dretva pokrenuta!");

            FolderComponent old = Model.getState();
            FolderComponent recent = null; // todo DOHVATITI TRENUTNU STRUKTURU SA DISKA
            
            if (compareScans(old, recent, 1)) {
                System.out.println("IMA PROMJENE");
              //  Backup.addToBackup(Reader.rootFolder);
                //   Reader.rootFolder = recent;

            } else {
                System.out.println("NEMA PROMJENE");
                //  ScanThread.setThreadOutputBuffer(Reader.getCurrentTime() + "\t" + "Nema promjene u strukturi\n");
            }

            //   model.getState();
            long trajanje = System.currentTimeMillis() - startTimer;
            try {
                System.out.println("SPAVA!");
                aktivna = false;
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

        if (compareScans(old, recent, false, new ArrayList<>(), "- obrisano", bufferIndex)) {
            changed = true;
        }
        if (compareScans(recent, old, false, new ArrayList<>(), "+ dodano", bufferIndex)) {
            changed = true;
        }

        return changed;
    }

    private static boolean compareScans(FolderComponent old, FolderComponent recent, boolean changed, ArrayList<String> path, String mess, int bufferIndex) {
 /*
        for (AbstractComponent fileEntry : old.getFolder()) {
            if (fileEntry.isDir()) {
                path.add(fileEntry.getName());
                changed = compareScans(fileEntry, recent, changed, path, mess, bufferIndex);
                path.remove(path.size() - 1);
            }
            path.add(fileEntry.getName());
            String fullPath = ".";
            for (String s : path) {
                fullPath += "/" + s;
            }
            String text = Reader.getCurrentTime() + "\t" + fullPath + "\t";
            boolean print = false;
            int ret = findInPath(recent, -1, path, 0, fileEntry.getLastModified());
            if (ret == -1) {
                changed = true;
                print = true;
                text += mess + "\n";
            } else if (ret == 1 && mess.charAt(0) == '+') {
                changed = true;
                print = true;
                text += "* izmjenjeno" + "\n";
            }
            if (print) {
                switch (bufferIndex) {
                    case 1:
                        ScanThread.setThreadOutputBuffer(text);
                        break;
                    case 2:
                        Reader.setOutputBuffer(text);
                        break;
                }
                text = "";
            }
            path.remove(path.size() - 1);
        }
        */
        return changed;
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }
}
