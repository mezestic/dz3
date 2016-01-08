/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3.visitor;

import hr.foi.uzdiz.t2_09.zadaca3.composite.FolderComponent;
import hr.foi.uzdiz.t2_09.zadaca3.mvc.View;

/**
 *
 * @author pvrbek
 */
public class Content {
    
     ContentElement[] elements;

    public ContentElement[] getElements() {
        return elements.clone(); // Return a copy of the array of references.
    }

    public Content(FolderComponent structure,View view) {
        this.elements = new ContentElement[]{ new SizeConverter(structure,view),new FileSaver(view)};
    }
}
