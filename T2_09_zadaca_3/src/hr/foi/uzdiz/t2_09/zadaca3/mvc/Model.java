/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3.mvc;

import hr.foi.uzdiz.t2_09.zadaca3.composite.FolderComponent;
import hr.foi.uzdiz.t2_09.zadaca3.memento.Memento;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author mezestic
 */
public class Model {
     private static int brojSekundi;

    
     private String direktorij;
      private FolderComponent state;

    public Model(String direktorij, int brojSekundi) {
     this.direktorij=direktorij;
     this.brojSekundi=brojSekundi;
    }

    public static int getBrojSekundi() {
        return brojSekundi;
    }
    
    public String getDirektorij() {
        return direktorij;
    }
    

   public void set(FolderComponent state) {
       this.state = state; 
   }

    public FolderComponent getState() {
        return state;
    }
   
   public Memento saveToMemento() { 
       return new Memento(state, new Date()); 
   }
   
   public void restoreFromMemento(Memento m) {
       state = m.getSavedState();
   }
      
}
