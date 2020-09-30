/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Podatnik;
import entity.PodatnikEwidencjaDok;
import error.E;
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
    private SessionFacade session1Facade;

    public PodatnikEwidencjaDokDAO() {
        super(PodatnikEwidencjaDok.class);
    }

    
    public List<PodatnikEwidencjaDok> findOpodatkowaniePodatnik(Podatnik podatnik) {
        return session1Facade.findPodatnikEwidencjaByPodatnik(podatnik);
    }
    public  List<PodatnikEwidencjaDok> findAll(){
        List<PodatnikEwidencjaDok> zwrot = null;
        try {
            zwrot = session1Facade.findAll(PodatnikEwidencjaDok.class);
        } catch (Exception e) { 
            E.e(e);
        }
        return zwrot;
    }
    
}
