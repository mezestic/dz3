/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3.visitor;

import hr.foi.uzdiz.t2_09.zadaca3.composite.AbstractComponent;
import hr.foi.uzdiz.t2_09.zadaca3.composite.FolderComponent;
import hr.foi.uzdiz.t2_09.zadaca3.iterator.FileRepository;
import hr.foi.uzdiz.t2_09.zadaca3.iterator.Iterator;
import hr.foi.uzdiz.t2_09.zadaca3.mvc.View;

/**
 *
 * @author vedra
 */
public class SizeConverter implements ContentElement {

    private View view;
    long ukupnaVelicina = 0;

    SizeConverter(FolderComponent structure, View view) {
        this.view = view;
        izracunaj(structure);
    }

    private void izracunaj(FolderComponent composite) {
        FileRepository namesRepository = new FileRepository(composite);
        for (Iterator iter = namesRepository.getIterator(); iter.hasNext();) {
            AbstractComponent c = (AbstractComponent) iter.next();
            if (c.tip.equals("datoteka")) {
                ukupnaVelicina += c.velicina;
            }
            if (c.tip.equals("direktorij")) {
                izracunaj((FolderComponent) c);
            }
        }
    }

    long getUkupnaVelicina() {
        return this.ukupnaVelicina;
    }

    public View getView() {
        return view;
    }

    @Override
    public void accept(ContentVisitor visitor) {
        visitor.visit(this);
    }

}
