/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3.composite;

import java.util.List;

/**
 *
 * @author vvadjune
 */
public class FileComponent extends AbstractComponent {

    private int velicina;
    
    
    
    public FileComponent(String ime, String tip, String vrijemeKreiranja, String vrijemePromjene, int velicina) {
        super(ime, tip, vrijemeKreiranja, vrijemePromjene);
        this.velicina=velicina;
    }

}
