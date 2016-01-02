/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3.composite;

import java.util.List;

/**
 *
 * @author mezestic
 */
public interface AbstractComponent {
      void add(AbstractComponent file);

    List<AbstractComponent> getFiles();

    void remove(AbstractComponent file);

    AbstractComponent getChild(int i);

    void addChild(AbstractComponent file);

    List<AbstractComponent> getChildren();

    void addParent(AbstractComponent file);

    List<AbstractComponent> getParents();

    void print();
}
