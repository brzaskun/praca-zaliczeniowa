/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewadmin;

import beansLogowanie.Liczniklogowan;
import dao.RejestrlogowanDAO;
import entity.Rejestrlogowan;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class RejestrlogowanView implements Serializable{
    private static final long serialVersionUID = 1L;
    private List<Rejestrlogowan> rejestrlogowan;
    @Inject
    private RejestrlogowanDAO rejestrlogowanDAO;

    public RejestrlogowanView() {
        rejestrlogowan = Collections.synchronizedList(new ArrayList<>());
    }
    
    @PostConstruct
    private void init() { //E.m(this);
        rejestrlogowan = rejestrlogowanDAO.findByLiczbalogowan0();
    }

    public List<Rejestrlogowan> getRejestrlogowan() {
        return rejestrlogowan;
    }

    public void setRejestrlogowan(List<Rejestrlogowan> rejestrlogowan) {
        this.rejestrlogowan = rejestrlogowan;
    }
    
    public void odblokujIPusera(Rejestrlogowan logowanie) {
        Liczniklogowan.odblokujIPusera(logowanie, rejestrlogowanDAO);
        rejestrlogowan.remove(logowanie);
        Msg.msg("Odblokowano ip: "+logowanie.getIpusera());
        
    }
    
    
}
