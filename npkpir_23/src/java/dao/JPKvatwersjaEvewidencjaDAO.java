/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.JPKVATWersja;
import entity.JPKvatwersjaEvewidencja;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class JPKvatwersjaEvewidencjaDAO extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    

    public JPKvatwersjaEvewidencjaDAO() {
        super(JPKvatwersjaEvewidencja.class);
    }
    
    public List findAll() {
        return sessionFacade.findAll(JPKvatwersjaEvewidencja.class);
    }

    public List<JPKvatwersjaEvewidencja> findJPKEwidencje(JPKVATWersja selected) {
        return sessionFacade.getEntityManager().createNamedQuery("JPKvatwersjaEvewidencja.findyByJPKvatwersja").setParameter("jpkvatwersja", selected).getResultList();
    }
    
}
