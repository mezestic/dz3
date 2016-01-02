/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3.memento;

import hr.foi.uzdiz.t2_09.zadaca3.composite.FolderComponent;
import java.util.Date;

/**
 *
 * @author vedra
 */
public class Memento {
      private FolderComponent state;
    private Date timeOfSave;
    
    public Date getTimeOfSave() {
        return timeOfSave;
    }

    public Memento(FolderComponent stateToSave, Date timeOfSave) {
        this.timeOfSave = timeOfSave;
        this.state = new FolderComponent(stateToSave.ime, stateToSave.tip, stateToSave.vrijemePromjeneKreiranja, stateToSave.velicina);
      //  copyState(stateToSave, state);
    }

    public FolderComponent getSavedState() {
        return state;
    }
}
