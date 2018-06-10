/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansVAT.VATZDBean;
import daoFK.DokDAOfk;
import data.Data;
import deklaracje.vatzd.WniosekVATZD;
import entity.Dok;
import entity.VATZDpozycja;
import entityfk.Dokfk;
import error.E;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
public class VATZDView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Dok> dokumentykp;
    private List<Dokfk> dokumentyfk;
    private List<Dokfk> dokumentyfkselected;
    private List<Dokfk> dokumentyfkfiltered;
    private List<VATZDpozycja> wykazdovatzd;
    @Inject
    private DokDAOfk dokDAOfk;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    
    @PostConstruct
    public void init() {
        dokumentyfk = dokDAOfk.findDokfkPodatnikRokMc(wpisView);
        for (Iterator<Dokfk> it = dokumentyfk.iterator(); it.hasNext(); ) {
            Dokfk dok = it.next();
            if (dok.getEwidencjaVAT()==null || dok.getEwidencjaVAT().size()==0) {
                it.remove();
            } else if (dok.getVATVAT()==0.0) {
                it.remove();
            }
        }
    }

    public void edytujtermin(Dokfk dok) {
        try {
            if (dok.getTerminPlatnosci().equals("") || dok.getTerminPlatnosci().length()==10) {
                if (dok.getTerminPlatnosci().equals("")) {
                    dok.setTerminPlatnosci(null);
                } else {
                    String regex = "^((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$";
                    Pattern p = Pattern.compile(regex);
                    Matcher m = p.matcher(dok.getTerminPlatnosci());
                    boolean pozniejsza = Data.compare(dok.getTerminPlatnosci(), dok.getDatadokumentu()) > -1;
                    if (m.matches() && pozniejsza) {
                        dokDAOfk.edit(dok);
                        Msg.dP();
                    } else {
                        Msg.msg("e", "Nieprawidłowa data!");
                    }
                }
            } else {
                Msg.msg("e","Nieprawidłowa data");
            }
        } catch (Exception e) {
            Msg.dPe();
        }
    }

    public void vatzd() {
        try {
            WniosekVATZD dek = null;
            if (dokumentyfkselected!=null) {
                dek = VATZDBean.createVATZD(dokumentyfkselected);
            } else if (dokumentyfkfiltered!=null) {
                dek = VATZDBean.createVATZD(dokumentyfkfiltered);
            } else {
                dek = VATZDBean.createVATZD(dokumentyfk);
            }
            String zalacznik = VATZDBean.marszajuldoStringu(dek);
            System.out.println(zalacznik);
            Msg.dP();
        } catch (Exception e) {
            E.e(e);
            Msg.dPe();
        }
    }
    
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

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<Dokfk> getDokumentyfkselected() {
        return dokumentyfkselected;
    }

    public void setDokumentyfkselected(List<Dokfk> dokumentyfkselected) {
        this.dokumentyfkselected = dokumentyfkselected;
    }

    public List<Dokfk> getDokumentyfkfiltered() {
        return dokumentyfkfiltered;
    }

    public void setDokumentyfkfiltered(List<Dokfk> dokumentyfkfiltered) {
        this.dokumentyfkfiltered = dokumentyfkfiltered;
    }
    
    
    
}
