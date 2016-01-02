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
    
    private boolean vertical;

    public View(boolean vertical) {
        this.vertical = vertical;
    }
    
    public void cleanScreen() {
        System.out.print(ANSI_ESC + "2J");
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
        Scanner inputReader = new Scanner(System.in);
        String option = inputReader.nextLine();
//        System.out.println("\n");
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
        // TODO: set cursor to primary
        this.printLnOut(line);
    }
    
    private void printLnToSecondary(String line){
        // TODO: set cursor to secondary
        this.printLnOut(line);
    }
    
    private void printLnOut(String line){
        System.out.println(line);
    }
    
}
