/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3.mvc;

import java.awt.Point;
import java.util.Scanner;

/**
 *
 * @author mezestic
 */
public class View {
    
    private static final String ANSI_ESC = "\033[";
    private Point primaryCursorPos;
    private Point secondaryCursorPos;
    private Point inputCursorPos;
    private boolean vertical;
    private int rowNum;
    private int colNum;

    public View(boolean vertical, int rowNum, int colNum) {
        this.vertical = vertical;
        this.rowNum = rowNum;
        this.colNum = colNum;
        
        this.primaryCursorPos = this.getPrimaryStart();
        this.secondaryCursorPos = this.getSecondaryStart();
        
        this.inputCursorPos = new Point(0, (vertical ? rowNum + 1 : rowNum + 2));
    }
    
    public void cleanScreen() {
        System.out.print(ANSI_ESC + "2J");
        
        if (this.vertical) {
            for (int i = 0; i < this.rowNum; i++) {
                this.setCursorPos(new Point(((int) this.colNum / 2) + 1, i));
                this.printLnOut("|");
            }
        } else {
            this.setCursorPos(new Point(0, ((int) this.rowNum / 2) + 1));
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

    public void printMenu() {
        this.printLn("-1 - ispis ukupnog broja direktorija i datoteka u strukturi");
        this.printLn("-2 - ispis sadržaja strukture direktorija i datoteka");
        this.printLn("-3 - izvršavanje dretve (prikaz u 1. prozoru)");
        this.printLn("-4 - prekid izvršavanja dretve");
        this.printLn("-5 - ispis informacija o svim spremljenim stanjima ");
        this.printLn("-6 n - postavljanje stanja strukture na promjenu s rednim brojem n");
        this.printLn("-7 m - uspoređivanje trenutnog stanja strukture i promjene s rednim brojem m");
        this.printLn("-8 - ponovno učitavanje strukture uz poništavanje svih spremljenih stanja strukture");
        this.printLn("-9 - dodana vlastita funkcionalnost");
        this.printLn("-Q - prekid rada programa.");
    }

    public String requestChoice() {
        this.setCursorPos(this.inputCursorPos);
        Scanner inputReader = new Scanner(System.in);
        String option = inputReader.nextLine();
        return option;
    }
    
    private void printLn(String line){
        this.printLnToPrimary(line);
    }
    
    private void printLn(String line, boolean primary){
        if (primary) {
            this.printLnToPrimary(line);
        } else {
            this.printLnToSecondary(line);
        }
    }
    
    private void printLnToPrimary(String line){
        this.setCursorPos(this.primaryCursorPos);
        this.printLnOut(line);
        this.primaryCursorPos.y += 1;
    }
    
    private void printLnToSecondary(String line){
        this.setCursorPos(this.secondaryCursorPos);
        this.printLnOut(line);
        this.secondaryCursorPos.y += 1;
    }
    
    private void printLnOut(String line){
        System.out.print(line);
    }
    
    private void setCursorPos(Point pos){
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
            return new Point(0, ((int) this.colNum / 2) + 1);
        } else {
            return new Point(((int) this.rowNum / 2) + 1, 0);
        }
    }

    private Point getSecondaryEnd() {
        if (this.vertical) {
            return new Point(this.colNum, this.rowNum - 1);
        } else {
            return new Point(this.colNum - 1, this.rowNum);
        }
    }
    
}
