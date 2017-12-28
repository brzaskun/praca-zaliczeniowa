/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverreset;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class Servereset {
    
    public void reset() {
        try {
            String path="cmd /c start G:\\payara-4.1.2.174\\bin\\reset.bat";
            Runtime rn=Runtime.getRuntime();
            Process pr=rn.exec(path);
        } catch (IOException ex) {
            Logger.getLogger(Servereset.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
