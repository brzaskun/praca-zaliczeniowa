/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansVAT.VATZDBean;
import dao.VATZDDAO;
import dao.WniosekVATZDEntityDAO;
import dao.DokDAOfk;
import data.Data;
import deklaracje.vatzd.WniosekVATZD;
import entity.Dok;
import entity.DokSuper;
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
import javax.inject.Named;

import javax.faces.view.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import msg.Msg;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class VATZDView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Dok> dokumentykp;
    private List<Dokfk> dokumentyfksprzedaz;
    private List<Dokfk> dokumentyfksprzedazselected;
    private List<Dokfk> dokumentyfksprzedazfiltered;
    private List<Dokfk> dokumentyfkzakup;
    private List<Dokfk> dokumentyfkzakupselected;
    private List<Dokfk> dokumentyfkzakupfiltered;
    private List<VATZDpozycja> wykazdovatzd;
    private List<VATZDpozycja> pozycje;
    private List<WniosekVATZDEntity> wniosekVATZDEntityList;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private VATZDDAO vatzddao;
    @Inject
    private WniosekVATZDEntityDAO wniosekVATZDEntityDAO;
    @Inject
    private WpisView wpisView;
    private WniosekVATZDEntity wniosekVATZDEntity;
    private boolean tylkowybrane;
    private String rokdek;
    private String mcdek;

    public VATZDView() {
        this.pozycje  = new ArrayList<>();
        this.dokumentyfksprzedaz  = new ArrayList<>();
        this.dokumentyfkzakup  = new ArrayList<>();
    }


    
    public void init() { //E.m(this);
        pozycje = vatzddao.findByPodatnikRokMcFK(wpisView);
        List<Dokfk> dokumentyfk = dokDAOfk.findDokfkPodatnikRokMcVAT(wpisView);
        wniosekVATZDEntityList = wniosekVATZDEntityDAO.findByPodatnikRokMcFK(wpisView);
        if(wniosekVATZDEntityList!=null && wniosekVATZDEntityList.size()>0) {
            wniosekVATZDEntity = wniosekVATZDEntityList.get(0);
        }
        for (Iterator<Dokfk> it = dokumentyfk.iterator(); it.hasNext(); ) {
            Dokfk dok = it.next();
            if (dok.getEwidencjaVAT()==null || dok.getEwidencjaVAT().size()==0) {
                it.remove();
            } else if (dok.getVATVAT()==0.0) {
                it.remove();
            } else if (dok.getRodzajedok().getKategoriadokumentu()==2) {
                dokumentyfksprzedaz.add(dok);
            }  else if (dok.getRodzajedok().getKategoriadokumentu()==1) {
                dokumentyfkzakup.add(dok);
            }
        }
        rokdek = wpisView.getRokWpisuSt();
        mcdek = wpisView.getMiesiacWpisu();
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

    public void wybierzdokumentyfk(List<Dokfk> lista) {
        if (!lista.isEmpty()) {
            for (Dokfk dok : lista) {
                if (Z.z(dok.getNiezaplacone()) == 0.0 ) {
                    Msg.msg("w","Niektóre dokumenty są spłacone w całości. Nie można ich korygować.");
                } else if (!wpisanotermin(dok)) {
                    Msg.msg("w","Brak terminu płatności na niektórych dok.");
                } else if(niemanalisciefk(dok)) {
                    VATZDpozycja poz = new VATZDpozycja(dok, wpisView);
                    pozycje.add(poz);
                } else {
                    Msg.msg("w","Niektóre wybrane dokumenty są już na liście");
                }
            }
            lista = null;
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
            String zalacznik = null;
            if (!pozycje.isEmpty()) {
                List pozycjesprzedaz = przetworzpozycje(pozycje);
                if (!pozycjesprzedaz.isEmpty()) {
                    wniosekVATZDsprzedaz = VATZDBean.createVATZD(pozycjesprzedaz);
                    zalacznik = VATZDBean.marszajuldoStringu(wniosekVATZDsprzedaz);
                } else {
                    wniosekVATZDsprzedaz = null;
                    zalacznik = null;
                }
            } else {
                wniosekVATZDsprzedaz = null;
                zalacznik = null;
            }
            wniosekVATZDEntity = new WniosekVATZDEntity();
            wniosekVATZDEntity.setZawierafk(new ArrayList<>());
            for (VATZDpozycja p : pozycje) {
                p.getDokfk().setWniosekVATZDEntity(wniosekVATZDEntity);
                wniosekVATZDEntity.getZawierafk().add(p.getDokfk());
            }
            wniosekVATZDEntity.setWniosek(wniosekVATZDsprzedaz);
            wniosekVATZDEntity.setZalacznik(zalacznik);
            wniosekVATZDEntity.setPodatnik(wpisView.getPodatnikObiekt());
            wniosekVATZDEntity.setRok(rokdek);
            wniosekVATZDEntity.setMc(mcdek);
            wniosekVATZDEntity.setNaliczonyzmniejszenie(przetworznaliczony(pozycje,true));
            //wniosekVATZDEntity.setNaliczonyzwiekszenie(przetworznaliczony(pozycje,false));
            this.wniosekVATZDEntityList.add(wniosekVATZDEntity);
            error.E.s(zalacznik);
            Msg.dP();
        } catch (Exception e) {
            E.e(e);
            Msg.dPe();
        }
    }
    
    private List przetworzpozycje(List<VATZDpozycja> pozycje) {
        List<VATZDpozycja> zwrot = new ArrayList<>();
        for (VATZDpozycja poz : pozycje) {
            DokSuper d = poz.getDokfk() !=null ? (DokSuper)poz.getDokfk() : (DokSuper)poz.getDok();
            if (d.getRodzajedok().getKategoriadokumentu()==2) {
                zwrot.add(poz);
            }
        }
        return zwrot;
    }
    
    private double przetworznaliczony(List<VATZDpozycja> pozycje, boolean b) {
        double zwrot = 0.0;
        for (VATZDpozycja poz : pozycje) {
            DokSuper d = poz.getDokfk() !=null ? (DokSuper)poz.getDokfk() : (DokSuper)poz.getDok();
            if (d.getRodzajedok().getKategoriadokumentu()==1) {
                if (b) {
                    zwrot -= poz.getDokfk()!=null ? poz.getDokfk().getVATVAT(): poz.getDok().getVat();
                } else {
                    zwrot += poz.getDokfk()!=null ? poz.getDokfk().getVATVAT(): poz.getDok().getVat();
                }
            }
        }
        return zwrot;
    }

    
    public void vatzdback() {
        try {
            for (VATZDpozycja p : pozycje) {
                vatzddao.remove(p);
                pozycje.remove(p);
                Dokfk dok = p.getDokfk();
                dok.setWniosekVATZDEntity(null);
                dokDAOfk.edit(dok);
            }
            Msg.msg("Usunięto pozycje VAT-ZD");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e","Wystąpił błąd podczas usuwania pozycji VAT-ZD");
        }
    }
    
    public void zachowajwniosek() {
        try {
            vatzddao.create(pozycje);
            wniosekVATZDEntityDAO.create(wniosekVATZDEntity);
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
            wniosekVATZDEntityDAO.remove(wniosekVATZDEntity);
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
            vatzddao.remove(poz);
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

    public List<Dokfk> getDokumentyfkzakup() {
        return dokumentyfkzakup;
    }

    public void setDokumentyfkzakup(List<Dokfk> dokumentyfkzakup) {
        this.dokumentyfkzakup = dokumentyfkzakup;
    }

    public List<Dokfk> getDokumentyfkzakupselected() {
        return dokumentyfkzakupselected;
    }

    public void setDokumentyfkzakupselected(List<Dokfk> dokumentyfkzakupselected) {
        this.dokumentyfkzakupselected = dokumentyfkzakupselected;
    }

    public List<Dokfk> getDokumentyfkzakupfiltered() {
        return dokumentyfkzakupfiltered;
    }

    public void setDokumentyfkzakupfiltered(List<Dokfk> dokumentyfkzakupfiltered) {
        this.dokumentyfkzakupfiltered = dokumentyfkzakupfiltered;
    }

    public String getRokdek() {
        return rokdek;
    }

    public void setRokdek(String rokdek) {
        this.rokdek = rokdek;
    }

    public String getMcdek() {
        return mcdek;
    }

    public void setMcdek(String mcdek) {
        this.mcdek = mcdek;
    }

    
    


   
    
    
    
    
}
