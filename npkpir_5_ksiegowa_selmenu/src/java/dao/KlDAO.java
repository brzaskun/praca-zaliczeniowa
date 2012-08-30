/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import embeddable.Kl;
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
    private static String klientNip;
    private static Kl selectedKontr;

    public KlDAO() {

    }

    
    public String getKlientId() {
        return klientNip;
    }

    public void setKlientId(String klientNip) {
        KlDAO.klientNip = klientNip;
    }

    public  Kl getSelectedKontr() {
        return selectedKontr;
    }

    public void setSelectedKontr(Kl selectedKontr) {
        KlDAO.selectedKontr = selectedKontr;
    }
    
    
    
    public Kl toObject(){
        Kl kl = new Kl();
        return kl.getKlList().get(Integer.parseInt(klientNip));
    }
    
    
}
