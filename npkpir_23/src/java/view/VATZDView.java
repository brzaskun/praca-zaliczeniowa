/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansVAT.VATZDBean;
import dao.VATZDDAO;
import dao.WniosekVATZDEntityDAO;
import daoFK.DokDAOfk;
import data.Data;
import deklaracje.vatzd.WniosekVATZD;
import deklaracje.vatzd.WniosekVATZDSuper;
import entity.Dok;
import entity.VATZDpozycja;
import entity.WniosekVATZDEntity;
import entityfk.Dokfk;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
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
    private List<VATZDpozycja> pozycje;
    private List<WniosekVATZDEntity> wniosekVATZDEntityList;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private VATZDDAO vatzddao;
    @Inject
    private WniosekVATZDEntityDAO wniosekVATZDEntityDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private WniosekVATZD wniosekVATZD;

    public VATZDView() {
        this.pozycje  = new ArrayList<>();
    }


    
    @PostConstruct
    public void init() {
        pozycje.addAll(vatzddao.findByPodatnikRokMcFK(wpisView));
        dokumentyfk = dokDAOfk.findDokfkPodatnikRokMc(wpisView);
        wniosekVATZDEntityList = wniosekVATZDEntityDAO.findByPodatnikRokMcFK(wpisView);
        for (Iterator<Dokfk> it = dokumentyfk.iterator(); it.hasNext(); ) {
            Dokfk dok = it.next();
            if (dok.getEwidencjaVAT()==null || dok.getEwidencjaVAT().size()==0) {
                it.remove();
            } else if (dok.getVATVAT()==0.0) {
                it.remove();
            }
        }
    }
    
    public void rokinit() {
        wpisView.setRokWpisuSt(String.valueOf(wpisView.getRokWpisu()));
        init();
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

    public void wybierzdokumentyfk() {
        if (!dokumentyfkselected.isEmpty()) {
            for (Dokfk dok : dokumentyfkselected) {
                if(niemanalisciefk(dok)) {
                    VATZDpozycja poz = new VATZDpozycja(dok, wpisView);
                    vatzddao.dodaj(poz);
                    pozycje.add(poz);
                } else {
                    Msg.msg("w","Niektóre wybrane dokumenty są już na liście");
                }
            }
            Msg.msg("Zachowano wybrane dokumenty dokumenty");
        } else {
            Msg.msg("e", "Nie wybrano dokumentów");
        }
    }
    
    public void vatzd() {
        try {
            WniosekVATZD wniosekVATZD = null;
            if (pozycje!=null) {
                wniosekVATZD = VATZDBean.createVATZD(pozycje);
            }
            WniosekVATZDEntity wniosekVATZDEntity = new WniosekVATZDEntity();
            String zalacznik = VATZDBean.marszajuldoStringu(wniosekVATZD);
            wniosekVATZDEntity.setWniosek(wniosekVATZD);
            wniosekVATZDEntity.setZalacznik(zalacznik);
            wniosekVATZDEntity.setPodatnik(wpisView.getPodatnikObiekt());
            wniosekVATZDEntity.setRok(wpisView.getRokWpisuSt());
            wniosekVATZDEntity.setMc(wpisView.getMiesiacWpisu());
            wniosekVATZDEntityDAO.dodaj(this.wniosekVATZDEntityList);
            this.wniosekVATZDEntityList.add(wniosekVATZDEntity);
            System.out.println(zalacznik);
            Msg.dP();
        } catch (Exception e) {
            E.e(e);
            Msg.dPe();
        }
    }
    
    private boolean niemanalisciefk(Dokfk dok) {
        boolean zwrot = true;
        for(VATZDpozycja p : pozycje) {
            if (p.getDokfk().equals(dok)) {
                zwrot = false;
                break;
            }
        }
        return zwrot;
    }
    
    public void usunfk(VATZDpozycja poz) {
        if (poz.getDeklaracjavat()==null) {
            vatzddao.destroy(poz);
            pozycje.remove(poz);
            Msg.msg("Usunięto pozycję");
        } else {
            Msg.msg("e","Załącznik wysłany nie można usunąć");
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

    public List<VATZDpozycja> getPozycje() {
        return pozycje;
    }

    public void setPozycje(List<VATZDpozycja> pozycje) {
        this.pozycje = pozycje;
    }

    public List<WniosekVATZDEntity> getWniosekVATZDEntityList() {
        return wniosekVATZDEntityList;
    }

    public void setWniosekVATZDEntityList(List<WniosekVATZDEntity> wniosekVATZDEntityList) {
        this.wniosekVATZDEntityList = wniosekVATZDEntityList;
    }


    public WniosekVATZD getWniosekVATZD() {
        return wniosekVATZD;
    }

    public void setWniosekVATZD(WniosekVATZD wniosekVATZD) {
        this.wniosekVATZD = wniosekVATZD;
    }

    
    
    
    
}
