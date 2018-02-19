/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Osito
 */
public class UsunPusteKontaPLL extends Thread {
    
    private List<Integer> listakont;
    private Iterator it;

    public UsunPusteKontaPLL(List<Integer> listakont, Iterator it) {
        this.listakont = listakont;
        this.it = it;
    }
    
    
    
    @Override
    public void run() {
       it.remove();
    }
    
    private static List<Integer> zrobliste() {
        List<Integer> l = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            l.add(new Integer(i+i*3/5));
        }
        return l;
    }
    
    public static void main(String[] args) {
        List<Integer> l = zrobliste();
        for (Iterator<Integer> it = l.iterator(); it.hasNext();) {
        }
    }
    
}
