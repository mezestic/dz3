/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3;

import hr.foi.uzdiz.t2_09.zadaca3.mvc.Controller;
import static java.lang.Thread.sleep;
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

            System.out.println("Promjena strukture!");
            //        c.createStructure();     
            
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

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }
}
