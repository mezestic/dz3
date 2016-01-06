/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3.visitor;
import hr.foi.uzdiz.t2_09.zadaca3.mvc.*;
import java.util.List;
/**
 *
 * @author Pero Pero
 */
public class ContentOperationDates implements ContentElement{
    private List lista;

    public List getLista() {
        return lista;
    }
    public ContentOperationDates(){
    this.lista=View.getDatumiDatoteka();
    }
    @Override
    public void accept(ContentElementVisitor visitor) {
        visitor.visit(this);
    }
    
}
