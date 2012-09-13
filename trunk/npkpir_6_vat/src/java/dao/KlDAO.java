/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import embeddable.Kl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Osito
 */
@ManagedBean(name="KlDAO")
@RequestScoped
public class KlDAO implements Serializable{
   
    private static Kl selectedKontr;

    public KlDAO() {

    }
   
  
    public  Kl getSelectedKontr() {
        return selectedKontr;
    }

    public void setSelectedKontr(Kl selectedKontr) {
        KlDAO.selectedKontr = selectedKontr;
    }

       
     public List<Kl> complete(String query) {  
        List<Kl> results = new ArrayList<Kl>();  
        Kl kl = new Kl();
         for(Kl p : kl.getKlList()) {  
            if(p.getNIP().startsWith(query)) {
                 results.add(p);
             }
        }  
         results.add(new Kl(9999999,"nowy klient","nowy klient"));
        return results;  
    }  
    
}
