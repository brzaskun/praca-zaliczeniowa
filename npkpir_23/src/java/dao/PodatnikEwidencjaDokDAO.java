/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Podatnik;
import entity.PodatnikEwidencjaDok;
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
public class PodatnikEwidencjaDokDAO  extends DAO implements Serializable{
    @Inject
    private SessionFacade sessionFacade;

    public PodatnikEwidencjaDokDAO() {
        super(PodatnikEwidencjaDok.class);
    }

    
    public List<PodatnikEwidencjaDok> findOpodatkowaniePodatnik(Podatnik podatnik) {
        return sessionFacade.findPodatnikEwidencjaByPodatnik(podatnik);
    }
    
    
}
