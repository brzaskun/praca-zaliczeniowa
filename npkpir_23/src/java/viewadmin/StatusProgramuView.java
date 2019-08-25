/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewadmin;

import dao.StatusprogramuDAO;
import entity.Statusprogramu;
import error.E;
import java.io.Serializable;
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
public class StatusProgramuView  implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject
    private StatusprogramuDAO statusprogramuDAO;
    @Inject
    private Statusprogramu wprowadzanainformacja;
    private List<Statusprogramu> wprowadzanestatusy;
    private Statusprogramu aktualnystatus;

    public StatusProgramuView() {
    }
    
    @PostConstruct
    private void init() { //E.m(this);
        wprowadzanestatusy = statusprogramuDAO.findAll();
        if (wprowadzanestatusy != null) {
            for (Statusprogramu p : wprowadzanestatusy) {
                if (p.isAktywny() == true) {
                    aktualnystatus = p;
                    break;
                }
            }
        }
    }
    
    
    
    public void wprowadzstatus() {
        try {
            statusprogramuDAO.dodaj(wprowadzanainformacja);
            wprowadzanestatusy.add(wprowadzanainformacja);
            wprowadzanainformacja = new Statusprogramu();
            Msg.msg("Wprowadzono status");
        } catch (Exception e) {
            E.e(e);
        }
    }

    public void destroy(Statusprogramu dousuniecia) {
        try {
            statusprogramuDAO.destroy(dousuniecia);
            wprowadzanestatusy.remove(dousuniecia);
            Msg.msg("Usunięto status");
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    public void edytuj(Statusprogramu biezacy) {
        try {
            for (Statusprogramu p : wprowadzanestatusy) {
                if (p != biezacy && biezacy.isAktywny()) {
                    p.setAktywny(false);
                }
            }
            statusprogramuDAO.editList(wprowadzanestatusy);
            Msg.msg("Aktywowano/deaktywowano status");
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    public Statusprogramu getWprowadzanainformacja() {
        return wprowadzanainformacja;
    }

    public void setWprowadzanainformacja(Statusprogramu wprowadzanainformacja) {
        this.wprowadzanainformacja = wprowadzanainformacja;
    }

    public List<Statusprogramu> getWprowadzanestatusy() {
        return wprowadzanestatusy;
    }

    public void setWprowadzanestatusy(List<Statusprogramu> wprowadzanestatusy) {
        this.wprowadzanestatusy = wprowadzanestatusy;
    }

    public Statusprogramu getAktualnystatus() {
        return aktualnystatus;
    }

    public void setAktualnystatus(Statusprogramu aktualnystatus) {
        this.aktualnystatus = aktualnystatus;
    }

    
   
    
    
    
}
