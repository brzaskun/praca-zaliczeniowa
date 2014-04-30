/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansDok.ListaEwidencjiVat;
import comparator.Rodzajedokcomparator;
import comparator.Vatcomparator;
import dao.DeklaracjevatDAO;
import embeddable.EwidencjaAddwiad;
import embeddable.VatKorektaDok;
import entity.Deklaracjevat;
import entity.Podatnik;
import entity.Rodzajedok;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;
import params.Params;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class VatKorektaView implements Serializable {
    @Inject
    private ListaEwidencjiVat listaEwidencjiVat;

    private static final long serialVersionUID = 1L;

    @Inject
    private DeklaracjevatDAO deklaracjevatDAO;
    private List<Deklaracjevat> deklaracjeWyslane;
    private List<Rodzajedok> rodzajedokKlienta;
    @Inject
    private VatKorektaDok vatKorektaDok;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

    public VatKorektaView() {
        deklaracjeWyslane = new ArrayList<>();
        rodzajedokKlienta = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        try {
            deklaracjeWyslane = deklaracjevatDAO.findDeklaracjeWyslane200(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            Podatnik podatnik = wpisView.getPodatnikObiekt();
            ArrayList<Rodzajedok> rodzajedokumentow = (ArrayList<Rodzajedok>) podatnik.getDokumentyksiegowe();
            Collections.sort(rodzajedokumentow, new Rodzajedokcomparator());
            rodzajedokKlienta.addAll(rodzajedokumentow);
        } catch (Exception e) {
        }
        Collections.sort(deklaracjeWyslane, new Vatcomparator());
    }

    public void podepnijEwidencjeVat() {
        vatKorektaDok = new VatKorektaDok();
        String skrotRT = (String) Params.params("wprowadzDokument:rodzajTrans");
        String transakcjiRodzaj = "";
        for (Rodzajedok temp : rodzajedokKlienta) {
            if (temp.getSkrot().equals(skrotRT)) {
                transakcjiRodzaj = temp.getRodzajtransakcji();
                break;
            }
        }
        /*wyswietlamy ewidencje VAT*/
        List opisewidencji = new ArrayList<>();
        opisewidencji.addAll(listaEwidencjiVat.pobierzOpisyEwidencji(transakcjiRodzaj));
        List<EwidencjaAddwiad> ewidencja = new ArrayList<>();
        int k = 0;
        for (Object p : opisewidencji) {
            EwidencjaAddwiad ewidencjaAddwiad = new EwidencjaAddwiad();
            ewidencjaAddwiad.setLp(k++);
            ewidencjaAddwiad.setOpis((String) p);
            ewidencjaAddwiad.setNetto(0.0);
            ewidencjaAddwiad.setVat(0.0);
            ewidencjaAddwiad.setBrutto(0.0);
            ewidencjaAddwiad.setOpzw("op");
            ewidencja.add(ewidencjaAddwiad);
        }
        vatKorektaDok.setEwidencjaVAT(ewidencja);
    }
    
    public void wyliczbrutto(EwidencjaAddwiad e) {
        int lp = e.getLp();
        vatKorektaDok.getEwidencjaVAT().get(lp).setBrutto(e.getNetto() + e.getVat());
        String update = "wprowadzDokument:tablicavat:" + lp + ":brutto";
        RequestContext.getCurrentInstance().update(update);
        String activate = "document.getElementById('wprowadzDokument:tablicavat:" + lp + ":brutto_input').select();";
        RequestContext.getCurrentInstance().execute(activate);
    }

    public List<Deklaracjevat> getDeklaracjeWyslane() {
        return deklaracjeWyslane;
    }

    public void setDeklaracjeWyslane(List<Deklaracjevat> deklaracjeWyslane) {
        this.deklaracjeWyslane = deklaracjeWyslane;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public VatKorektaDok getVatKorektaDok() {
        return vatKorektaDok;
    }

    public void setVatKorektaDok(VatKorektaDok vatKorektaDok) {
        this.vatKorektaDok = vatKorektaDok;
    }

    public List<Rodzajedok> getRodzajedokKlienta() {
        return rodzajedokKlienta;
    }

    public void setRodzajedokKlienta(List<Rodzajedok> rodzajedokKlienta) {
        this.rodzajedokKlienta = rodzajedokKlienta;
    }

    
}
