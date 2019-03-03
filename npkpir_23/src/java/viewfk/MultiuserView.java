/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.MultiuserSettingsDAO;
import dao.PodatnikDAO;
import dao.UzDAO;
import entity.MultiuserSettings;
import entity.Podatnik;
import entity.Uz;
import error.E;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class MultiuserView   implements Serializable{
    private static final long serialVersionUID = 1L;
    private List<Uz> listamutliuserow;
    private List<MultiuserSettings> listapodpietychfirm;
    private List<Podatnik> listafirm;
    private List<Podatnik> listawyboru;
    @Inject
    private UzDAO uzDAO;
    @Inject
    private Uz selected;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private MultiuserSettingsDAO multiuserSettingsDAO;

    public MultiuserView() {
         E.m(this);
        listamutliuserow = Collections.synchronizedList(new ArrayList<>());
    }
    
    @PostConstruct
    private void init() {E.m(this);
        listamutliuserow.addAll(uzDAO.findByUprawnienia("Multiuser"));
        listamutliuserow.addAll(uzDAO.findByUprawnienia("GuestFaktura"));
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Principal principal = request.getUserPrincipal();
        String name = principal.getName();
        Uz uz = uzDAO.findUzByLogin(name);
        List<MultiuserSettings> lista = multiuserSettingsDAO.findByUser(uz);
        listawyboru = Collections.synchronizedList(new ArrayList<>());
        for (MultiuserSettings p : lista) {
            listawyboru.add(p.getPodatnik());
        }
    }
    
    public void pokazpodpietefirmy() {
        listapodpietychfirm = multiuserSettingsDAO.findByUser(selected);
        listafirm = podatnikDAO.findAll();
        for (MultiuserSettings p : listapodpietychfirm) {
            if (listafirm.contains(p.getPodatnik())) {
                listafirm.remove(p.getPodatnik());
            }
        }
    }
    
    public void podepnijfirme(Podatnik podatnik) {
        MultiuserSettings ms = new MultiuserSettings();
        ms.setPodatnik(podatnik);
        ms.setUser(selected);
        ms.setLevel(0);
        multiuserSettingsDAO.dodaj(ms);
        listafirm.remove(podatnik);
        listapodpietychfirm.add(ms);
    }
    
    public void odepnijfirme(MultiuserSettings ms) {
        multiuserSettingsDAO.destroy(ms);
        listafirm.add(ms.getPodatnik());
        listapodpietychfirm.remove(ms);
    }

    public List<Uz> getListamutliuserow() {
        return listamutliuserow;
    }

    public void setListamutliuserow(List<Uz> listamutliuserow) {
        this.listamutliuserow = listamutliuserow;
    }

    public Uz getSelected() {
        return selected;
    }

    public void setSelected(Uz selected) {
        this.selected = selected;
    }

    public List<MultiuserSettings> getListapodpietychfirm() {
        return listapodpietychfirm;
    }

    public void setListapodpietychfirm(List<MultiuserSettings> listapodpietychfirm) {
        this.listapodpietychfirm = listapodpietychfirm;
    }

    public List<Podatnik> getListafirm() {
        return listafirm;
    }

    public void setListafirm(List<Podatnik> listafirm) {
        this.listafirm = listafirm;
    }

    public List<Podatnik> getListawyboru() {
        return listawyboru;
    }

    public void setListawyboru(List<Podatnik> listawyboru) {
        this.listawyboru = listawyboru;
    }
    
    
    
    
}
