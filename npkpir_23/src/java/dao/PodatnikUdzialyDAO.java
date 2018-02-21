/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.PodatnikUdzialy;
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
public class PodatnikUdzialyDAO extends DAO implements Serializable{
    @Inject
    private SessionFacade sessionFacade;

    public PodatnikUdzialyDAO() {
        super(PodatnikUdzialy.class);
    }

    public List<PodatnikUdzialy> findUdzialyPodatnik(WpisView wpisView) {
        return sessionFacade.findUdzialyPodatnik(wpisView);
    }

    public List<PodatnikUdzialy> findAll() {
        return sessionFacade.findAll(PodatnikUdzialy.class);
    }
    
    
    
}
