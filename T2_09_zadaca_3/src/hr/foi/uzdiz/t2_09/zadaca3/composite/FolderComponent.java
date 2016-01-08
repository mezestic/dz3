/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3.composite;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author vvadjune
 */
//DIREKTORIJ
public class FolderComponent extends AbstractComponent {

    public List<AbstractComponent> children = new ArrayList<AbstractComponent>();

    public FolderComponent() {
    }

    public FolderComponent(String ime, String tip, Date vrijemePromjeneKreiranja, long velicina) {
        super(ime, tip, vrijemePromjeneKreiranja, velicina);
    }

    public void addChild(AbstractComponent component) {
        children.add(component);
    }
}
