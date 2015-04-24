/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entity.Fakturadodelementy;
import entity.Fakturaelementygraficzne;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class FakturaelementygraficzneDAO  extends DAO implements Serializable {
    
    @Inject
    private SessionFacade fakturaelementygraficzneFacade;

    public FakturaelementygraficzneDAO() {
        super(Fakturaelementygraficzne.class);
    }
    
    public  Fakturaelementygraficzne findFaktElementyGraficznePodatnik(String podatnik){
        try {
            return fakturaelementygraficzneFacade.findFaktElementyGraficzne(podatnik);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()); 
            return null;
        }
   }
}
