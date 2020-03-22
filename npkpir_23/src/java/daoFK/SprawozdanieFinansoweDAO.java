/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.SprawozdanieFinansowe;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import view.WpisView;
/**
 *
 * @author Osito
 */
@Named
public class SprawozdanieFinansoweDAO  extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;

    public SprawozdanieFinansoweDAO() {
    }
    
    public SprawozdanieFinansoweDAO(Class entityClass) {
        super(entityClass);
    }

    public List<SprawozdanieFinansowe> findSprawozdanieRokPodatnik(WpisView wpisView, String rok) {
        return sessionFacade.findSprawozdanieRokPodatnik(wpisView, rok);
    }
    
    public List<SprawozdanieFinansowe> findSprawozdanieRok(String rok) {
        return sessionFacade.findSprawozdanieRok(rok);
    }
    
    public List<SprawozdanieFinansowe> findAll() {
        return sessionFacade.findAll(SprawozdanieFinansowe.class);
    }
}
