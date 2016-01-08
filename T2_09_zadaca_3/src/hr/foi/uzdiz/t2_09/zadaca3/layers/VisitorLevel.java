/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3.layers;

import hr.foi.uzdiz.t2_09.zadaca3.composite.FolderComponent;
import hr.foi.uzdiz.t2_09.zadaca3.mvc.View;
import hr.foi.uzdiz.t2_09.zadaca3.visitor.Content;
import hr.foi.uzdiz.t2_09.zadaca3.visitor.ContentElementDoVisitor;
import hr.foi.uzdiz.t2_09.zadaca3.visitor.ContentVisitor;

/**
 *
 * @author vedra
 */
public class VisitorLevel implements ILayer {

    FolderComponent structure;
    View view;
    VisitorLevel(FolderComponent structure, View view) {
       this.structure=structure;
       this.view=view;
    }

    @Override
    public void akcija() {
        Content content = new Content(structure, view);
        ContentVisitor doVisitor = new ContentElementDoVisitor();
        doVisitor.visitMethods(content);
    }

}
