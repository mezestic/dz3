/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3.layers;
import hr.foi.uzdiz.t2_09.zadaca3.visitor.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Pero Pero
 */
public class OperationLayer implements AdditionalFunctionalityInterface{
    private static List<String> lista = new ArrayList<String>();

    public static List<String> getLista() {
        return lista;
    }

    public static long getVelicina() {
        return velicina;
    }
    private static long velicina;
    @Override
    public void foo() {
        ContentElementVisitor cev = new ContentElementVisitorImpl();
        ContentOperationDates datum=new ContentOperationDates();
        ContentOperationSize vel=new ContentOperationSize();
       // ContentElement content=new ContentOperationDates();
        datum.accept(cev);
        vel.accept(cev);
        lista=cev.visit(datum);
        velicina=cev.visit(vel);
    }
    
}
