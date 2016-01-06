/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3.visitor;

import java.util.List;

/**
 *
 * @author Pero Pero
 */
public interface ContentElementVisitor {
    long visit(ContentOperationSize size);
    List visit(ContentOperationDates dates);
}
