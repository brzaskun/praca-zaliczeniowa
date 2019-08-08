/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import comparator.SaldoKontocomparator;
import dao.PodatnikOpodatkowanieDAO;
import embeddablefk.SaldoKonto;
import entity.PodatnikOpodatkowanieD;
import entityfk.PozycjaRZiSBilans;
import error.E;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;import pdf.PdfKonta;
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
    @Inject
    PodatnikOpodatkowanieDAO podatnikOpodatkowanieDAO;
    
    
    
    public void drukuj() {
        try {
            pozycjaBRView.init();
            pozycjaBRZestawienieView.init();
            saldoAnalitykaView.init();
            saldoSyntetykaView.init();
            planKontView.init();
            PodatnikOpodatkowanieD rokbiezacy = podatnikOpodatkowanieDAO.findOpodatkowaniePodatnikRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            PodatnikOpodatkowanieD rokuprzedni = podatnikOpodatkowanieDAO.findOpodatkowaniePodatnikRok(wpisView. getPodatnikObiekt(), wpisView.getRokUprzedniSt());
            if (rokuprzedni==null || rokbiezacy.getDolaczonydoroku()==null || rokbiezacy.getDolaczonydoroku().equals("")) {
                pozycjaBRView.obliczBilansOtwarciaBilansDataWybierz();
                pozycjaBRZestawienieView.obliczRZiSOtwarciaRZiSData();
                PdfBilans.drukujBilansBODataAP(pozycjaBRView.getRootBilansAktywa(),pozycjaBRView.getRootBilansPasywa(), wpisView, " do sprawozdania finansowego", pozycjaBRView.getSumabilansowapasywaBO(), pozycjaBRView.getSumabilansowaaktywa(), pozycjaBRView.getSumabilansowapasywa(), pozycjaBRView.getBilansnadzien(), pozycjaBRView.getBilansoddnia(), false);
                pozycjaBRZestawienieView.drukujRZiSBO();
            } else {
                    if (rokuprzedni!=null && rokbiezacy.getDolaczonydoroku().equals(wpisView.getRokUprzedniSt())) {
                        pozycjaBRView.setLaczlata(true);
                        pozycjaBRView.setBilansoddnia(rokuprzedni.getDatarozpoczecia());
                        pozycjaBRView.obliczBilansOtwarciaBilansDataXML();
                        pozycjaBRZestawienieView.setLaczlata(true);
                        pozycjaBRZestawienieView.setBilansoddnia(rokuprzedni.getDatarozpoczecia());
                        pozycjaBRZestawienieView.pobierzukladprzegladRZiSWybierz();
                        PdfBilans.drukujBilansBODataAP(pozycjaBRView.getRootBilansAktywa(),pozycjaBRView.getRootBilansPasywa(), wpisView, " do sprawozdania finansowego", pozycjaBRView.getSumabilansowapasywaBO(), pozycjaBRView.getSumabilansowaaktywa(), pozycjaBRView.getSumabilansowapasywa(), pozycjaBRView.getBilansnadzien(), pozycjaBRView.getBilansoddnia(), true);
                        pozycjaBRZestawienieView.drukujRZiS();
                    }
            }
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
            pdfZaksiegowaneView.drukujzaksiegowanydokument(dokfkView.getWykazZaksiegowanychDokumentow(), dokfkView.getSelectedlist(), dokfkView.getFilteredValue());
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
