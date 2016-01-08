/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.foi.uzdiz.t2_09.zadaca3.iterator;

import hr.foi.uzdiz.t2_09.zadaca3.composite.FolderComponent;

/**
 *
 * @author atomas
 */
public class FileRepository implements Container {

    FolderComponent struktura;

    public FileRepository(FolderComponent state) {
        struktura = state;
    }

    @Override
    public Iterator getIterator() {
        return new InitialFileIterator();
    }

    private class InitialFileIterator implements Iterator {

        int index;

        @Override
        public boolean hasNext() {
            if (index < struktura.children.size()) {
                return true;
            }
            return false;
        }

        @Override
        public Object next() {
            if (this.hasNext()) {
                return struktura.children.get(index++);
            }
            return null;
        }
    }

}
