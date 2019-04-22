/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.SMTPSettingsDAO;
import entity.SMTPSettings;
import error.E;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class SMTPSettingsView implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private SMTPSettingsDAO sMTPSettingsDAO;
    @Inject
    private SMTPSettings nowy;
    private SMTPSettings selected;
    
    @PostConstruct
    private void init() {
        try {
            selected = sMTPSettingsDAO.findSprawaByUzytkownik(wpisView.getUzer());
        } catch (Exception e) {
        }
    }

    public void dodaj() {
        try {
            if (nowy.getUsername() != null) {
                nowy.setUzytkownik(wpisView.getUzer());
                nowy.setGlowne(true);
                sMTPSettingsDAO.edit(nowy);
                Msg.dP();
                nowy = new SMTPSettings();
                selected = sMTPSettingsDAO.findSprawaByUzytkownik(wpisView.getUzer());
            }
        } catch (Exception e) {
            E.e(e);
            Msg.dPe();
        }
    }
    
    public void dodajuser() {
        try {
            if (nowy.getUsername() != null) {
                nowy.setUzytkownik(wpisView.getUzer());
                nowy.setGlowne(false);
                sMTPSettingsDAO.edit(nowy);
                Msg.dP();
                nowy = new SMTPSettings();
                selected = sMTPSettingsDAO.findSprawaByUzytkownik(wpisView.getUzer());
            }
        } catch (Exception e) {
            E.e(e);
            Msg.dPe();
        }
    }
    
    public void przygotujedycja() {
        nowy = selected;
        Msg.msg("Pobrano dane do edycji");
    }
    
    public void usunsmtp() {
        nowy = new SMTPSettings();
        if (selected != null) {
            sMTPSettingsDAO.destroy(selected);
            selected = new SMTPSettings();
        }
        Msg.msg("Usunięto konfigurację maila do wysyłki faktur");
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public SMTPSettings getNowy() {
        return nowy;
    }

    public void setNowy(SMTPSettings nowy) {
        this.nowy = nowy;
    }

    public SMTPSettings getSelected() {
        return selected;
    }

    public void setSelected(SMTPSettings selected) {
        this.selected = selected;
    }
    
    
    
    
}
