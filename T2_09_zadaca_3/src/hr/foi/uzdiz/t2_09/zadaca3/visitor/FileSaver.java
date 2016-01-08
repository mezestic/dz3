/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3.visitor;

import hr.foi.uzdiz.t2_09.zadaca3.composite.FolderComponent;
import hr.foi.uzdiz.t2_09.zadaca3.mvc.View;

/**
 *
 * @author vedra
 */
public class FileSaver implements ContentElement {

    private View view;

    FileSaver(View view) {
        this.view = view;

    }

    public View getView() {
        return view;
    }

    @Override
    public void accept(ContentVisitor visitor) {
        visitor.visit(this);
    }

}
