/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import embeddable.Mce;
import entity.Dok;
import entity.DokPKPiR;
import entity.Klienci;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named(value = "DokPKPiRDAO")
public class DokPKPiRDAO extends DAO implements Serializable {
    private static final Logger LOG = Logger.getLogger(DokPKPiRDAO.class.getName());

    @Inject private SessionFacade dokFacade;
    
    //tablica wciagnieta z bazy danych

    public DokPKPiRDAO() {
        super(DokPKPiR.class);
    }

    public DokPKPiRDAO(Class entityClass) {
        super(entityClass);
    }
    
    
    public List<DokPKPiR> findAll(){
        return dokFacade.findAll(DokPKPiR.class);
    }
    
    
  
}
