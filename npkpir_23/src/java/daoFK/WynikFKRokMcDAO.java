/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entity.Podatnik;
import entityfk.WynikFKRokMc;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;
import view.WpisView;
/**
 *
 * @author Osito
 */
@Named
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
    
    public WynikFKRokMc findWynikFKRokMcFirma(WynikFKRokMc wynikFKRokMc) {
        return sessionFacade.findWynikFKRokMcFirma(wynikFKRokMc);
    }
    public List<WynikFKRokMc> findWynikFKPodatnikRokFirma(WpisView wpisView) {
        return sessionFacade.findWynikFKPodatnikRokFirma(wpisView);
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
    
    public WynikFKRokMc findWynikFKPodatnikRokUdzialowiec(Podatnik podatnik, String rok, String mc, String udzialowiec) {
        return sessionFacade.findWynikFKRokMcUdzialowiec(podatnik, rok, mc, udzialowiec);
    }
    

    public List<WynikFKRokMc> findAll() {
        return sessionFacade.findAll(WynikFKRokMc.class);
    }
    
}
