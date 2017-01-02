/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extclass;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Osito
 */
public class ReverseIterator<T> implements Iterator<T>, Iterable<T> {

    private final List<T> list;
    private int position;

    public ReverseIterator(List<T> list) {
        this.list = list;
        this.position = list.size() - 1;
    }

    @Override
    public Iterator<T> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return position >= 0;
    }

    @Override
    public T next() {
        return list.get(position--);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

}
