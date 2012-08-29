/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Kl;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Osito
 */
@ManagedBean(name="KlDAO")
@RequestScoped
public class KlDAO implements Serializable{
    private static int klientId;

    public int getKlientId() {
        return klientId;
    }

    public void setKlientId(int klientId) {
        KlDAO.klientId = klientId;
    }
    
    public Kl toObject(){
        Kl kl;
        kl = new Kl();
        kl=kl.getKlList().get(klientId);
        return kl;
    }
    
}
