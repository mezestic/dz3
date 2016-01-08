/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3.visitor;

/**
 *
 * @author vedra
 */
public interface ContentVisitor {

    public void visit(SizeConverter file);

    public void visit(FileSaver file);

    void visitMethods(Content content);
}
