/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multi;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Osito
 */
public class KreatorWatkow extends Thread {

    public String serwer;
    public int port;

    public KreatorWatkow(String serwer, int port) {
        this.serwer = serwer;
        this.port = port;
    }

    public void run() {
        try {
            Socket socket = new Socket(serwer, port);
            socket.close();
        } catch (IOException ex) {
            //Logger.getLogger(KreatorWatkow.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }

}
