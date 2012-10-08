/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.SrodekTrw;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class STRDAO implements Serializable{
    private List<SrodekTrw> EwidencjaSrodkowTrwalych;

    public STRDAO() {
        EwidencjaSrodkowTrwalych = new ArrayList<SrodekTrw>();
    }
    
    

    public List<SrodekTrw> getEwidencjaSrodkowTrwalych() {
        return EwidencjaSrodkowTrwalych;
    }

    public void setEwidencjaSrodkowTrwalych(List<SrodekTrw> EwidencjaSrodkowTrwalych) {
        this.EwidencjaSrodkowTrwalych = EwidencjaSrodkowTrwalych;
    }

    
    
    
}
