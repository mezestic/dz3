/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3.mvc;

import hr.foi.uzdiz.t2_09.zadaca3.composite.AbstractComponent;
import hr.foi.uzdiz.t2_09.zadaca3.composite.FolderComponent;
import hr.foi.uzdiz.t2_09.zadaca3.iterator.FileRepository;
import hr.foi.uzdiz.t2_09.zadaca3.iterator.Iterator;
import java.awt.Point;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mezestic
 */
public class View {

    private static final String ANSI_ESC = "\033[";
    private static final DecimalFormat MY_FORMATTER = new DecimalFormat("###,###.###");

    private Point primaryCursorPos;
    private Point secondaryCursorPos;
    private Point inputCursorPos;
    private boolean vertical;
    private int rowNum;
    private int colNum;
    
    private static boolean unos=false;

   

    private int brojDirektorija = 0;
    private int brojDatoteka = 0;
    private long ukupnaVelicina = 0;

    public View(boolean vertical, int rowNum, int colNum) {
        this.vertical = vertical;
        this.rowNum = rowNum;
        this.colNum = colNum;

        this.primaryCursorPos = this.getPrimaryStart();
        this.secondaryCursorPos = this.getSecondaryStart();

        this.inputCursorPos = this.getInputStart();
    }
    
     public static void setUnos(boolean unos) {
        View.unos = unos;
    }
//---------CLEAN--------------------------

    public void cleanScreen() {
        System.out.print(ANSI_ESC + "2J");

        if (this.vertical) {
            for (int i = 0; i < this.rowNum; i++) {
                this.setCursorPos(new Point(this.getSecondaryStart().x - 1, i));
                this.printLnOut("|");
            }
        } else {
            this.setCursorPos(new Point(0, this.getPrimaryEnd().y + 1));
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < this.colNum; i++) {
                sb.append("-");
            }
            this.printLnOut(sb.toString());
        }

        this.setCursorPos(new Point(0, this.inputCursorPos.y - 1));
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < this.colNum; i++) {
            sb.append("-");
        }
        this.printLnOut(sb.toString());

        this.primaryCursorPos = this.getPrimaryStart();
        this.secondaryCursorPos = this.getSecondaryStart();
    }

    public void cleanPrimaryScreen() {
        for (int i = this.getPrimaryStart().y; i < this.getPrimaryEnd().y + 1; i++) {
            this.setCursorPos(new Point(0, i));
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < this.getPrimaryEnd().x + 1; j++) {
                sb.append(" ");
            }
            this.printLnOut(sb.toString());
        }

        this.primaryCursorPos = this.getPrimaryStart();
    }

    public void cleanSecondaryScreen() {
        for (int i = this.getSecondaryStart().y; i < this.getSecondaryEnd().y + 1; i++) {
            this.setCursorPos(new Point(this.getSecondaryStart().x, i));
            StringBuilder sb = new StringBuilder();
            for (int j = this.getSecondaryStart().x; j < this.getSecondaryEnd().x + 1; j++) {
                sb.append(" ");
            }
            this.printLnOut(sb.toString());
        }

        this.secondaryCursorPos = this.getSecondaryStart();
    }

    public void cleanInputScreen() {
        this.inputCursorPos = this.getInputStart();
        for (int i = 0; i < 12; i++) { // 11 = max input print rows
            this.setCursorPos(new Point(0, this.inputCursorPos.y));
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < this.colNum; j++) {
                sb.append(" ");
            }
            this.printLnOut(sb.toString());
            this.inputCursorPos.y += 1;
        }

        this.inputCursorPos = this.getInputStart();
    }

    ///------------------------ PRINT------------------------------
    public void printMenu() {
        this.cleanInputScreen();
        this.printLnToInput("-1  -  ispis ukupnog broja direktorija i datoteka u strukturi");
        this.printLnToInput("-2  -  ispis sadržaja strukture direktorija i datoteka");
        this.printLnToInput("-3  -  izvršavanje dretve");
        this.printLnToInput("-4  -  prekid izvršavanja dretve");
        this.printLnToInput("-5  -  ispis informacija o svim spremljenim stanjima");
        this.printLnToInput("-6 n - postavljanje stanja strukture na promjenu s rednim brojem n");
        this.printLnToInput("-7 m - uspoređivanje trenutnog stanja strukture i promjene s rednim brojem m");
        this.printLnToInput("-8  -  ponovno učitavanje strukture uz poništavanje svih spremljenih ");
        this.printLnToInput("\t stanja strukture");
        this.printLnToInput("-9  -  dodana vlastita funkcionalnost");
        this.printLnToInput("-Q  -  prekid rada programa.");
    }

    public String requestChoice() {
        this.setCursorPos(this.inputCursorPos);
        Scanner inputReader = new Scanner(System.in);
        String option = inputReader.nextLine();
        this.cleanInputScreen();
        return option;
    }

    private void printLn(String line) {
        this.printLnToPrimary(line);
    }

    private void printLn(String line, boolean primary) {
        if (primary) {
            this.printLnToPrimary(line);
        } else {
            this.printLnToSecondary(line);
        }
    }

    public void printLnToPrimary(String line) {
        while (line != null && line.length() > 0) {
            if (this.primaryCursorPos.y > this.getPrimaryEnd().y) {
                this.primaryCursorPos = this.getPrimaryStart();
                this.cleanPrimaryScreen();
            }

            this.setCursorPos(this.primaryCursorPos);
            int rowSize = this.getPrimaryEnd().x - this.getPrimaryStart().x;
            int end = rowSize > line.length() ? line.length() : rowSize;
            this.printLnOut(line.substring(0, end));
            this.primaryCursorPos.y += 1;
            if (end < line.length()) {
                line = line.substring(end);
            } else {
                line = null;
            }
        }
    }

    public void printLnToSecondary(String line) {
        while (line != null && line.length() > 0) {
            if (this.secondaryCursorPos.y > this.getSecondaryEnd().y) {
                this.secondaryCursorPos = this.getSecondaryStart();
                this.cleanSecondaryScreen();
            }

            this.setCursorPos(this.secondaryCursorPos);
            int rowSize = this.getSecondaryEnd().x - this.getSecondaryStart().x;
            int end = rowSize > line.length() ? line.length() : rowSize;
            this.printLnOut(line.substring(0, end));
            this.secondaryCursorPos.y += 1;
            if (end < line.length()) {
                line = line.substring(end);
            } else {
                line = null;
            }
        }
    }

    public void printLnToInput(String line) {
        while (line != null && line.length() > 0) {
            this.setCursorPos(this.inputCursorPos);
            int rowSize = this.colNum;
            int end = rowSize > line.length() ? line.length() : rowSize;
            this.printLnOut(line.substring(0, end));
            this.inputCursorPos.y += 1;
            if (end < line.length()) {
                line = line.substring(end);
            } else {
                line = null;
            }
        }
    }

    private void printLnOut(String line) {
        System.out.print(line);
        if(!unos){
                 setColor("33");
        }
    
        
    }

    ///----------- SET / GET ----------------
    private void setCursorPos(Point pos) {
        System.out.print(ANSI_ESC + (pos.y + 1) + ";" + (pos.x + 1) + "f");
    }

    private Point getScreenPos(boolean primary, boolean start) {
        if (primary) {
            return this.getPrimaryPos(start);
        } else {
            return this.getSecondaryPos(start);
        }
    }

    private Point getPrimaryPos(boolean start) {
        if (start) {
            return this.getPrimaryStart();
        } else {
            return this.getPrimaryEnd();
        }
    }

    private Point getSecondaryPos(boolean start) {
        if (start) {
            return this.getSecondaryStart();
        } else {
            return this.getSecondaryEnd();
        }
    }

    private Point getPrimaryStart() {
        return new Point(0, 0);
    }

    private Point getPrimaryEnd() {
        if (this.vertical) {
            return new Point(((int) this.colNum / 2) - 1, this.rowNum - 1);
        } else {
            return new Point(this.colNum - 1, ((int) this.rowNum / 2) - 1);
        }
    }

    private Point getSecondaryStart() {
        if (this.vertical) {
            return new Point(((int) this.colNum / 2) + 1, 0);
        } else {
            return new Point(0, ((int) this.rowNum / 2) + 1);
        }
    }

    private Point getSecondaryEnd() {
        if (this.vertical) {
            return new Point(this.colNum, this.rowNum - 1);
        } else {
            return new Point(this.colNum - 1, this.rowNum);
        }
    }

    private Point getInputStart() {
        return new Point(0, (vertical ? rowNum + 1 : rowNum + 2));
    }

    public void setColor(String boja) {
        System.out.print(ANSI_ESC + boja + "m");
    }

    //------------------ ISPISI ............
    public void printStructure(FolderComponent composite, String tab, boolean updateSecond) {
        this.cleanSecondaryScreen();
        this.cleanPrimaryScreen();
        this.printLnToInput("ISPIS STRUKTURE\n");
        this.ispisStrukture(composite, tab, updateSecond);
        this.cleanInputScreen();
        this.printLnToInput("Pritisnite <ENTER> za povratak: ");
    }

    private void ispisStrukture(FolderComponent composite, String tab, boolean updateSecond) {
        FileRepository namesRepository = new FileRepository(composite);
        for (Iterator iter = namesRepository.getIterator(); iter.hasNext();) {
            AbstractComponent c = (AbstractComponent) iter.next();

            String boja;
            if (c.tip.equals("direktorij")) {
                brojDirektorija++;
                setColor("33");
            } else {
                brojDatoteka++;
                ukupnaVelicina += c.velicina;
                setColor("35");
            }

            this.printLnToPrimary(String.format("%-50s", tab + c.ime) + String.format("%-15s", c.tip) + "   " + new SimpleDateFormat("HH:mm:ss  dd-MM-yyyy").format(c.vrijemePromjeneKreiranja) + "   " + MY_FORMATTER.format(c.velicina) + " B");
            if (updateSecond) {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.cleanSecondaryScreen();
                this.printLnToSecondary("Broj dodanih datoteka: " + brojDatoteka);
                this.printLnToSecondary("Broj dodanih direktorija: " + brojDirektorija);
                this.printLnToSecondary("Ukupna velicina: " + MY_FORMATTER.format(ukupnaVelicina) + " B");
            }
            if (c.tip.equals("direktorij")) {
                ispisStrukture((FolderComponent) c, tab + "   ", updateSecond);
            }
        }
    }

    public void printNumberofElements(long direktorij, long datoteke) {
        this.cleanPrimaryScreen();
        this.cleanSecondaryScreen();
        this.printLnToInput("ISPIS KOLICINE ELEMENATA\n");
        this.printLnToPrimary("Ukupni broj datoteka " + datoteke);
        this.printLnToPrimary("Ukupni broj direktorija " + direktorij);
        this.cleanInputScreen();
        this.printLnToInput("Pritisnite <ENTER> za povratak: ");
    }

}
