/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3.visitor;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pero Pero
 */
public class ContentElementVisitorImpl implements ContentElementVisitor {

    @Override
    public long visit(ContentOperationSize size) {
        return size.getUkupnaVelicina() / 1000; //u megabajtima
    }

    @Override
    public List visit(ContentOperationDates dates) {
        List<String> listaNovih = new ArrayList<String>();
        for (int i = 0; i < dates.getLista().size(); i++) {
            String[] lines = dates.getLista().get(i).toString().split(System.getProperty(": "));
            if(Integer.parseInt(lines[1])>2015){
                listaNovih.add(dates.getLista().get(i).toString());
            }
        }
        return listaNovih;
    }
}
