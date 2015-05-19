/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entity.Fakturaelementygraficzne;
import error.E;
import java.io.Serializable;
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
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
}
