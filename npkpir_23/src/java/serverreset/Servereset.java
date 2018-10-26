/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverreset;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class Servereset {
    
    private boolean pokazbutton;
    
    @PostConstruct
    private void init() {
        pokazbutton = true;
    }
    
    public void reset() {
        try {
            String path="cmd /c start G:\\payara174\\bin\\reset.bat";
            Runtime rn=Runtime.getRuntime();
            Process pr=rn.exec(path);
            pokazbutton = false;
        } catch (Exception ex) {
            Logger.getLogger(Servereset.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isPokazbutton() {
        return pokazbutton;
    }

    public void setPokazbutton(boolean pokazbutton) {
        this.pokazbutton = pokazbutton;
    }
    
    
}
