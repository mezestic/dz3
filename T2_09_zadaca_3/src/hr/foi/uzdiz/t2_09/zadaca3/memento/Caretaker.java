/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3.memento;

import java.util.ArrayList;

/**
 *
 * @author vedra
 */
public class Caretaker {
     private ArrayList<Memento> savedStates = new ArrayList<Memento>();

    public ArrayList<Memento> getSavedStates() {
        return savedStates;
    }
    
    public void addMemento(Memento m) {
        savedStates.add(m);
    }

    public Memento getMemento(int index) {
        return savedStates.get(index);
    }
    
   
}
