/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.WynikFKRokMc;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import session.SessionFacade;
import view.WpisView;

/**
 *
 * @author Osito
 */
public class WynikFKRokMcDAO extends DAO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private SessionFacade sessionFacade;

    public WynikFKRokMcDAO() {
        super(WynikFKRokMc.class);
    }

    public WynikFKRokMc findWynikFKRokMc(WynikFKRokMc wynikFKRokMc) {
        return sessionFacade.findWynikFKRokMc(wynikFKRokMc);
    }

    public List<WynikFKRokMc> findWynikFKPodatnikRok(WpisView wpisView) {
        return sessionFacade.findWynikFKPodatnikRok(wpisView);
    }
    
    public List<WynikFKRokMc> findWynikFKPodatnikRokUdzialowiec(WpisView wpisView) {
        return sessionFacade.findWynikFKPodatnikRokUdzialowiec(wpisView);
    }

    public WynikFKRokMc findWynikFKPodatnikRokUdzialowiec(WynikFKRokMc wynikFKRokMc) {
        return sessionFacade.findWynikFKRokMcUdzialowiec(wynikFKRokMc);
    }

   
    
}
