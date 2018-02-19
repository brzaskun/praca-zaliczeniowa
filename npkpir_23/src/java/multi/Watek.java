/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multi;

import daoFK.KontoDAOfk;
import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class Watek implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private KontoDAOfk kontoDAOfk;

    public void pobieraj() {
        ExecutorService ex = Executors.newFixedThreadPool(500);
        for (int i = 302; i < 24000; i++) {
            ex.execute(new PobierzZBazy(kontoDAOfk, i));
        }
        ex.shutdown();
    }

//    public static void main(String[] args) {
//        ExecutorService ex = Executors.newFixedThreadPool(10);
//        for (int i = 1; i < 100; i++) {
//            ex.execute(new PrintLine("linia " + i));
//        }
//        ex.shutdown();
//    }
//    public static void main(String[] args) {
//        ExecutorService ex = Executors.newFixedThreadPool(10);
//        for (int i = 1; i < 100; i++) {
//            ex.execute(new KreatorWatkow(serwer, i));
//        }
//        ex.shutdown();
//    }
//    public static void main(String[] args) {
//        List lista = new ArrayList();
//        if (lista.isEmpty()) {
//            System.out.println("pusta lista");
//        }
//    }
}
