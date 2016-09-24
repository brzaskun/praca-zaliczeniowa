/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entity.Dok;
import entity.VATZDpozycja;
import entityfk.Dokfk;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class VATZDView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Dok> dokumentykp;
    private List<Dokfk> dokumentyfk;
    private List<VATZDpozycja> wykazdovatzd;

    
    
    public List<Dok> getDokumentykp() {
        return dokumentykp;
    }

    public void setDokumentykp(List<Dok> dokumentykp) {
        this.dokumentykp = dokumentykp;
    }

    public List<Dokfk> getDokumentyfk() {
        return dokumentyfk;
    }

    public void setDokumentyfk(List<Dokfk> dokumentyfk) {
        this.dokumentyfk = dokumentyfk;
    }

    public List<VATZDpozycja> getWykazdovatzd() {
        return wykazdovatzd;
    }

    public void setWykazdovatzd(List<VATZDpozycja> wykazdovatzd) {
        this.wykazdovatzd = wykazdovatzd;
    }
    
    
}
