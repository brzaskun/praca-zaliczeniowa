/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multi;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Osito
 */
public class PrintLine extends Thread {

    private String linia;

    public PrintLine(String linia) {
        this.linia = linia;
    }

    public void run() {
        try {
            System.out.println(linia);
        } catch (Exception ex) {
            Logger.getLogger(KreatorWatkow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
