/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverreset;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class Servereset implements Serializable{
    
    private boolean pokazbutton;
    
    @PostConstruct
    private void init() { //E.m(this);
        pokazbutton = true;
    }
    
    public void reset() {
        try {
            String path="cmd /c start G:\\payara174\\bin\\reset.bat";
            Runtime rn=Runtime.getRuntime();
            Process pr=rn.exec(path);
            pokazbutton = false;
        } catch (Exception ex) {
            // Logger.getLogger(Servereset.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isPokazbutton() {
        return pokazbutton;
    }

    public void setPokazbutton(boolean pokazbutton) {
        this.pokazbutton = pokazbutton;
    }
    
    
}
