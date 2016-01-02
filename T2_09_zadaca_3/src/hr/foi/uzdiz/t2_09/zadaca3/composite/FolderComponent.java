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
 * @author mezestic
 */
public class FolderComponent extends AbstractComponent {

    //direktorij
    public List<FolderComponent> children = new ArrayList<FolderComponent>();

    public FolderComponent() {
    }

    public FolderComponent(String ime, String tip, String vrijemeKreiranja, String vrijemePromjene) {
        super(ime, tip, vrijemeKreiranja, vrijemePromjene);
    }

    public void addChild(FolderComponent component) {
        children.add(component);
    }
}
