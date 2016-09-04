/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.DeklaracjaVatPozycjeKoncowe;
import entity.DeklaracjaVatWierszSumaryczny;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class DeklaracjaVatPozycjeKoncoweDAO  extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
      
    public DeklaracjaVatPozycjeKoncoweDAO() {
        super(DeklaracjaVatWierszSumaryczny.class);
    }
    
    public List findAll() {
        return sessionFacade.findAll(DeklaracjaVatPozycjeKoncowe.class);
    }

    public DeklaracjaVatPozycjeKoncowe findWiersz(String nazwa) {
        return (DeklaracjaVatPozycjeKoncowe) sessionFacade.getEntityManager().createNamedQuery("DeklaracjaVatPozycjeKoncowe.findWiersz").setParameter("nazwapozycji", nazwa).getSingleResult();
    }
    
}
