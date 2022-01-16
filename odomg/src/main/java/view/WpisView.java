/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;


import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
/**
 *
 * @author Osito
 */
@Named
@SessionScoped
public class WpisView implements Serializable {
    private static final long serialVersionUID = 1L;

    private String uzer;
    private String rola;
 
    

    public WpisView() {
        }
    

    @PostConstruct
    public void init() { //E.m(this);
        
    }

    public void init(String uzer, String navto) {
        this.uzer = uzer;
        this.rola = navto;
    }

    public String getUzer() {
        return uzer;
    }

    public void setUzer(String uzer) {
        this.uzer = uzer;
    }

    public String getRola() {
        return rola;
    }

    public void setRola(String rola) {
        this.rola = rola;
    }
    
    
    

  }
