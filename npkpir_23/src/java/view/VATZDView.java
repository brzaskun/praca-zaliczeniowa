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
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import msg.Msg;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class VATZDView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Dok> dokumentykp;
    private List<Dokfk> dokumentyfksprzedaz;
    private List<Dokfk> dokumentyfksprzedazselected;
    private List<Dokfk> dokumentyfksprzedazfiltered;
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
    private WniosekVATZDEntity wniosekVATZDEntity;
    private boolean tylkowybrane;

    public VATZDView() {
        this.pozycje  = new ArrayList<>();
    }


    
    @PostConstruct
    public void init() {
        pozycje.addAll(vatzddao.findByPodatnikRokMcFK(wpisView));
        dokumentyfksprzedaz = dokDAOfk.findDokfkPodatnikRokMcVAT(wpisView);
        wniosekVATZDEntityList = wniosekVATZDEntityDAO.findByPodatnikRokMcFK(wpisView);
        if(wniosekVATZDEntityList!=null && wniosekVATZDEntityList.size()>0) {
            wniosekVATZDEntity = wniosekVATZDEntityList.get(0);
        }
        for (Iterator<Dokfk> it = dokumentyfksprzedaz.iterator(); it.hasNext(); ) {
            Dokfk dok = it.next();
            if (dok.getRodzajedok().getKategoriadokumentu()!=2) {
                it.remove();
            } else if (dok.getEwidencjaVAT()==null || dok.getEwidencjaVAT().size()==0) {
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
        if (!dokumentyfksprzedazselected.isEmpty()) {
            for (Dokfk dok : dokumentyfksprzedazselected) {
                if (Z.z(dok.getNiezaplacone()) == 0.0 ) {
                    Msg.msg("w","Niektóre dokumenty są spłacone w całości. Nie można ich korygować.");
                } else if (!wpisanotermin(dok)) {
                    Msg.msg("w","Brak terminu płatności na niektórych dok.");
                } else if(niemanalisciefk(dok)) {
                    VATZDpozycja poz = new VATZDpozycja(dok, wpisView);
                    vatzddao.dodaj(poz);
                    pozycje.add(poz);
                } else {
                    Msg.msg("w","Niektóre wybrane dokumenty są już na liście");
                }
            }
            if (pozycje.size()>0) {
                Msg.msg("Zachowano wybrane dokumenty dokumenty");
            } else {
                Msg.msg("e","Nie zachowano/wybrano ani jednego dokumentu");
            }
        } else {
            Msg.msg("e", "Nie wybrano dokumentów");
        }
    }
    
    public void vatzd() {
        try {
            WniosekVATZD wniosekVATZDsprzedaz = null;
            if (pozycje!=null) {
                wniosekVATZDsprzedaz = VATZDBean.createVATZD(pozycje);
            }
            wniosekVATZDEntity = new WniosekVATZDEntity();
            wniosekVATZDEntity.setZawierafk(new ArrayList<>());
            for (VATZDpozycja p : pozycje) {
                p.getDokfk().setWniosekVATZDEntity(wniosekVATZDEntity);
                wniosekVATZDEntity.getZawierafk().add(p.getDokfk());
            }
            String zalacznik = VATZDBean.marszajuldoStringu(wniosekVATZDsprzedaz);
            wniosekVATZDEntity.setWniosek(wniosekVATZDsprzedaz);
            wniosekVATZDEntity.setZalacznik(zalacznik);
            wniosekVATZDEntity.setPodatnik(wpisView.getPodatnikObiekt());
            wniosekVATZDEntity.setRok(wpisView.getRokWpisuSt());
            wniosekVATZDEntity.setMc(wpisView.getMiesiacWpisu());
            this.wniosekVATZDEntityList.add(wniosekVATZDEntity);
            System.out.println(zalacznik);
            Msg.dP();
        } catch (Exception e) {
            E.e(e);
            Msg.dPe();
        }
    }
    
    public void zachowajwniosek() {
        try {
            wniosekVATZDEntityDAO.dodaj(wniosekVATZDEntity);
            dokDAOfk.editList(wniosekVATZDEntity.getZawierafk());
            Msg.msg("Zachowano wniosek");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e","Nie udało się zachować wniosku");
        }
    }
    
    public void usunwniosek() {
        try {
            for (Dokfk d : wniosekVATZDEntity.getZawierafk()) {
                d.setWniosekVATZDEntity(null);
            }
            wniosekVATZDEntityDAO.destroy(wniosekVATZDEntity);
            dokDAOfk.editList(wniosekVATZDEntity.getZawierafk());
            wniosekVATZDEntity = null;
            Msg.msg("Usunięto wniosek");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e","Nie usunięto wniosku");
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
    
    private boolean wpisanotermin(Dokfk dok) {
        boolean zwrot = false;
        if (dok.getTerminPlatnosci()!=null && (!dok.getTerminPlatnosci().equals("") && dok.getTerminPlatnosci().length()==10)) {
            zwrot = true;
        }
        return zwrot;
    }
    
    public void wybierzwybrane(ValueChangeEvent e) {
        boolean wart = (boolean) e.getNewValue();
        if (wart) {
            for (Iterator<Dokfk> it = dokumentyfksprzedaz.iterator(); it.hasNext();) {
                if (it.next().getWniosekVATZDEntity()==null) {
                    it.remove();
                }
            }
        } else {
            dokumentyfksprzedaz = dokDAOfk.findDokfkPodatnikRokMcVAT(wpisView);
            for (Iterator<Dokfk> it = dokumentyfksprzedaz.iterator(); it.hasNext(); ) {
                Dokfk dok = it.next();
                if (dok.getRodzajedok().getKategoriadokumentu()!=2) {
                    it.remove();
                } else if (dok.getEwidencjaVAT()==null || dok.getEwidencjaVAT().size()==0) {
                    it.remove();
                } else if (dok.getVATVAT()==0.0) {
                    it.remove();
                }
            }
        }
        Msg.dP();
    }
    
    public List<Dok> getDokumentykp() {
        return dokumentykp;
    }

    public void setDokumentykp(List<Dok> dokumentykp) {
        this.dokumentykp = dokumentykp;
    }

    public List<Dokfk> getDokumentyfksprzedaz() {
        return dokumentyfksprzedaz;
    }

    public void setDokumentyfksprzedaz(List<Dokfk> dokumentyfksprzedaz) {
        this.dokumentyfksprzedaz = dokumentyfksprzedaz;
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

    public List<Dokfk> getDokumentyfksprzedazselected() {
        return dokumentyfksprzedazselected;
    }

    public void setDokumentyfksprzedazselected(List<Dokfk> dokumentyfksprzedazselected) {
        this.dokumentyfksprzedazselected = dokumentyfksprzedazselected;
    }

    public List<Dokfk> getDokumentyfksprzedazfiltered() {
        return dokumentyfksprzedazfiltered;
    }

    public void setDokumentyfksprzedazfiltered(List<Dokfk> dokumentyfksprzedazfiltered) {
        this.dokumentyfksprzedazfiltered = dokumentyfksprzedazfiltered;
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

    public WniosekVATZDEntity getWniosekVATZDEntity() {
        return wniosekVATZDEntity;
    }

    public void setWniosekVATZDEntity(WniosekVATZDEntity wniosekVATZDEntity) {
        this.wniosekVATZDEntity = wniosekVATZDEntity;
    }

    public boolean isTylkowybrane() {
        return tylkowybrane;
    }

    public void setTylkowybrane(boolean tylkowybrane) {
        this.tylkowybrane = tylkowybrane;
    }


   
    
    
    
    
}
