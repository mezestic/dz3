/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3.composite;

import java.util.Date;


/**
 *
 * @author vvadjune
 */
public abstract class AbstractComponent {

      public String ime;
    public String tip;
    public Date vrijemePromjeneKreiranja;
    public long velicina;
    
     public AbstractComponent(){}
    
     
     public AbstractComponent(String ime, String tip, Date vrijemePromjeneKreiranja,long velicina) {
        this.ime = ime;
        this.tip = tip;
        this.vrijemePromjeneKreiranja = vrijemePromjeneKreiranja;
        this.velicina=velicina;
    }
}
