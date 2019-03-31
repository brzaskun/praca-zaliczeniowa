/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import comparator.SaldoKontocomparator;
import embeddablefk.SaldoKonto;
import error.E;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import msg.Msg;
import pdf.PdfKonta;
import pdffk.PdfBilans;
import pdffk.PdfPlanKont;
import pdffk.PdfZaksiegowaneView;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class DrukujsprawozdaniefinansoweView  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value = "#{pozycjaBRView}")
    private PozycjaBRView pozycjaBRView;
    @ManagedProperty(value = "#{pozycjaBRZestawienieView}")
    private PozycjaBRZestawienieView pozycjaBRZestawienieView;
    @ManagedProperty(value = "#{saldoAnalitykaView}")
    private SaldoAnalitykaView saldoAnalitykaView;
    @ManagedProperty(value = "#{saldoSyntetykaView}")
    private SaldoSyntetykaView saldoSyntetykaView;
    @ManagedProperty(value = "#{dokfkView}")
    private DokfkView dokfkView;
    @ManagedProperty(value = "#{pdfZaksiegowaneView}")
    private PdfZaksiegowaneView pdfZaksiegowaneView;
    @ManagedProperty(value = "#{planKontView}")
    private PlanKontView planKontView;
    
    
    
    public void drukuj() {
        try {
            pozycjaBRView.init();
            pozycjaBRZestawienieView.init();
            saldoAnalitykaView.init();
            saldoSyntetykaView.init();
            planKontView.init();
            pozycjaBRView.obliczBilansOtwarciaBilansDataWybierz();
            PdfBilans.drukujBilansBODataAP(pozycjaBRView.getRootBilansAktywa(),pozycjaBRView.getRootBilansPasywa(), wpisView, " do sprawozdania finansowego", pozycjaBRView.getSumabilansowapasywaBO(), pozycjaBRView.getSumabilansowaaktywa(), pozycjaBRView.getSumabilansowapasywa(), pozycjaBRView.getBilansnadzien(), pozycjaBRView.getBilansoddnia(), false);
            pozycjaBRZestawienieView.obliczRZiSOtwarciaRZiSData();
            pozycjaBRZestawienieView.drukujRZiSBO();
            saldoAnalitykaView.odswiezsaldoanalityczne();
            List<SaldoKonto> saldokontolist = saldoAnalitykaView.getListaSaldoKonto();
            Collections.sort(saldokontolist, new SaldoKontocomparator());
            PdfKonta.drukuj(saldokontolist, wpisView, 1, 0, wpisView.getMiesiacWpisu(), saldoAnalitykaView.getSumaSaldoKonto());
            PdfKonta.drukuj(saldokontolist, wpisView, 4, 0, wpisView.getMiesiacWpisu(), saldoAnalitykaView.getSumaSaldoKonto());
            PdfKonta.drukuj(saldokontolist, wpisView, 2, 0, wpisView.getMiesiacWpisu(), saldoAnalitykaView.getSumaSaldoKonto());
            saldoSyntetykaView.odswiezsaldosyntetyczne();
            saldokontolist = saldoSyntetykaView.getListaSaldoKonto();
            Collections.sort(saldokontolist, new SaldoKontocomparator());
            PdfKonta.drukuj(saldokontolist, wpisView, 1, 1, wpisView.getMiesiacWpisu(), saldoSyntetykaView.getSumaSaldoKonto());
            dokfkView.init();
            dokfkView.setMiesiacWpisuPokaz("CR");
            dokfkView.odswiezzaksiegowane();
            dokfkView.setWybranakategoriadok("wszystkie");;
            pdfZaksiegowaneView.drukujzaksiegowanydokument(dokfkView.getWykazZaksiegowanychDokumentow(), dokfkView.getSelectedlist());
            PdfPlanKont.drukujPlanKont(planKontView.getWykazkont(), wpisView);
            Msg.msg("Wydrukowano sprawozdanie finansowe");
        } catch (Exception e) {
            Msg.msg("e","Wystąpił błąd podczas drukowania sprawozdanie finansowego "+E.e(e));
        }
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public PozycjaBRView getPozycjaBRView() {
        return pozycjaBRView;
    }

    public void setPozycjaBRView(PozycjaBRView pozycjaBRView) {
        this.pozycjaBRView = pozycjaBRView;
    }

    public PozycjaBRZestawienieView getPozycjaBRZestawienieView() {
        return pozycjaBRZestawienieView;
    }

    public void setPozycjaBRZestawienieView(PozycjaBRZestawienieView pozycjaBRZestawienieView) {
        this.pozycjaBRZestawienieView = pozycjaBRZestawienieView;
    }

    public SaldoAnalitykaView getSaldoAnalitykaView() {
        return saldoAnalitykaView;
    }

    public void setSaldoAnalitykaView(SaldoAnalitykaView saldoAnalitykaView) {
        this.saldoAnalitykaView = saldoAnalitykaView;
    }

    public SaldoSyntetykaView getSaldoSyntetykaView() {
        return saldoSyntetykaView;
    }

    public void setSaldoSyntetykaView(SaldoSyntetykaView saldoSyntetykaView) {
        this.saldoSyntetykaView = saldoSyntetykaView;
    }

    public DokfkView getDokfkView() {
        return dokfkView;
    }

    public void setDokfkView(DokfkView dokfkView) {
        this.dokfkView = dokfkView;
    }

    public PdfZaksiegowaneView getPdfZaksiegowaneView() {
        return pdfZaksiegowaneView;
    }

    public void setPdfZaksiegowaneView(PdfZaksiegowaneView pdfZaksiegowaneView) {
        this.pdfZaksiegowaneView = pdfZaksiegowaneView;
    }

    public PlanKontView getPlanKontView() {
        return planKontView;
    }

    public void setPlanKontView(PlanKontView planKontView) {
        this.planKontView = planKontView;
    }
    
    
    
    
}
