/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.EVatwpis1;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
public class EVatwpis1DAO  extends DAO implements Serializable {

    @Inject
    private SessionFacade sessionFacade;

    public  List<EVatwpis1> findAll(){
        try {
            return sessionFacade.findAll(EVatwpis1.class);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
   }
}
