/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3.visitor;
import hr.foi.uzdiz.t2_09.zadaca3.mvc.*;
import hr.foi.uzdiz.t2_09.zadaca3.composite.FolderComponent;
/**
 *
 * @author Pero Pero
 */
public class ContentOperationSize implements ContentElement{
    private long velicine;
    public ContentOperationSize(){
        this.velicine=View.getVelicina();
}
    public long getUkupnaVelicina(){
        return velicine;
    }
    @Override
    public void accept(ContentElementVisitor visitor) {
       visitor.visit(this);
    }
}
