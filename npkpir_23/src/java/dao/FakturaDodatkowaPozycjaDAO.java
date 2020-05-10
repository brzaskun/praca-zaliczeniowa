/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.FakturaDodatkowaPozycja;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
public class FakturaDodatkowaPozycjaDAO  extends DAO implements Serializable {

    @Inject
    private SessionFacade sessionFacade;

    public FakturaDodatkowaPozycjaDAO() {
        super(FakturaDodatkowaPozycja.class);
    }
    
     public  List<FakturaDodatkowaPozycja> findAll(){
        try {
            return sessionFacade.findAll(FakturaDodatkowaPozycja.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
}
