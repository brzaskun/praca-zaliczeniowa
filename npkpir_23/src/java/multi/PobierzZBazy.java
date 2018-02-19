/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multi;

import daoFK.KontoDAOfk;
import entityfk.Konto;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Osito
 */
public class PobierzZBazy extends Thread {

    private KontoDAOfk kontoDAOfk;
    private int id;

    public PobierzZBazy(KontoDAOfk kontoDAOfk, int id) {
        this.kontoDAOfk = kontoDAOfk;
        this.id = id;
    }

    public void run() {
        try {
            Konto k = kontoDAOfk.findKonto2(id);
            if (k != null) {
            }
        } catch (Exception ex) {

        }
    }
}
