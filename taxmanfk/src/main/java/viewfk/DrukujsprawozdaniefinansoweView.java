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
import error.E;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
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
@Named
@ViewScoped
public class DrukujsprawozdaniefinansoweView  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private WpisView wpisView;
    @Inject
    private PozycjaBRView pozycjaBRView;
    @Inject
    private PozycjaBRZestawienieView pozycjaBRZestawienieView;
    @Inject
    private SaldoAnalitykaView saldoAnalitykaView;
    @Inject
    private SaldoSyntetykaView saldoSyntetykaView;
    @Inject
    private DokfkView dokfkView;
    @Inject
    private PdfZaksiegowaneView pdfZaksiegowaneView;
    @Inject
    private PlanKontView planKontView;
    @Inject
    PodatnikOpodatkowanieDAO podatnikOpodatkowanieDAO;
    
    
    
    public void drukuj() {
        String etap = "start";
        String etap2 = "start";
        try {
            pozycjaBRView.init();
            etap = "udało się zainicjować pozycje";
            etap2 = "nie udało się zainicjować pozycje zestawienie";
            Msg.msg(etap);
            pozycjaBRZestawienieView.init();
            etap = "udało się zainicjować pozycje zestawienie";
            etap2 = "nie udało się zrobić analitykę kont";
            Msg.msg(etap);
            saldoAnalitykaView.init();
            etap = "udało się zrobić analitykę kont";
            etap2 = "nie udało się zrobić syntetykę kont";
            Msg.msg(etap);
            saldoSyntetykaView.init();
            etap = "udało się zrobić syntetykę kont";
            etap2 = "nie udało się zainicjować plan kont";
            Msg.msg(etap);
            planKontView.init();
            etap = "udało się zainicjować plan kont";
            etap2 = "nie pobrano etap rok bieżący";
            Msg.msg(etap);
            PodatnikOpodatkowanieD rokbiezacy = podatnikOpodatkowanieDAO.findOpodatkowaniePodatnikRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            etap = "pobrano opodatkowanie rok bieżący";
            etap2 = "nie pobrano opodatkowania rok uprzedni";
            Msg.msg(etap);
            PodatnikOpodatkowanieD rokuprzedni = podatnikOpodatkowanieDAO.findOpodatkowaniePodatnikRok(wpisView. getPodatnikObiekt(), wpisView.getRokUprzedniSt());
            etap = "pobrano opodatkowanie rok uprzedni";
            etap2 = "nie udane generowanie bilansu";
            Msg.msg(etap);
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
            etap = "wygenerowano bilanse";
            etap2 = "nie udane wydruki";
            Msg.msg(etap);
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
            Msg.msg("e",etap2);
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
