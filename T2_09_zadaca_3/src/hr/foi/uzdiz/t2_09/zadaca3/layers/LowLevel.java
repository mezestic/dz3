/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3.layers;

import hr.foi.uzdiz.t2_09.zadaca3.composite.FolderComponent;
import hr.foi.uzdiz.t2_09.zadaca3.mvc.View;
import java.nio.ByteBuffer;

/**
 *
 * @author vedra
 */
public class LowLevel {

    ILayer levelTwo;
    private FolderComponent structure;
    View view;
    int option;

    public void setView(View view) {
        this.view = view;
    }

    public void setStructure(FolderComponent structure) {
        this.structure = structure;
    }

    public void setLevel() {
        this.levelTwo = new StructureWriteLevel();
    }

    public void setOption(int optionInt) {
        option = optionInt;
    }

    public void executeLayer() {
        if (option != 1 || option != 2) {
            view.printLnToPrimary("Pogresan unos!");
            view.printMenu();
        }
        if (option == 2) {
            this.levelTwo = new VisitorLevel(structure, view);
        } else {
            this.levelTwo.akcija();
        }

    }
}
