/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3.composite;

import java.util.Date;
import java.util.List;

/**
 *
 * @author mezestic
 */
public abstract class AbstractComponent {

      public String ime;
    public String tip;
    public String vrijemeKreiranja;
    public String vrijemePromjene;
    
     public AbstractComponent(){}
    
     
     public AbstractComponent(String ime, String tip, String vrijemeKreiranja, String  vrijemePromjene) {
        this.ime = ime;
        this.tip = tip;
        this.vrijemeKreiranja = vrijemeKreiranja;
        this.vrijemePromjene = vrijemePromjene;
    }
}
