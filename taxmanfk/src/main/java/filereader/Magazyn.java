/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filereader;

import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Magazyn {
    private String jeden;
    private String dwa;

    public String getJeden() {
        return jeden;
    }

    public void setJeden(String jeden) {
        this.jeden = jeden;
    }

    public String getDwa() {
        return dwa;
    }

    public void setDwa(String dwa) {
        this.dwa = dwa;
    }
    
    
    
}
