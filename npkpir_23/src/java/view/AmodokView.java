/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author Osito
 */
import beansFK.DokumentFKBean;
import beansSrodkiTrwale.SrodkiTrwBean;
import dao.AmoDokDAO;
import dao.DokDAOfk;
import dao.KlienciDAO;
import dao.KontoDAOfk;
import dao.RodzajedokDAO;
import dao.STRDAO;
import dao.TabelanbpDAO;
import dao.UmorzenieNDAO;
import embeddable.Roki;
import entity.Amodok;
import entity.SrodekTrw;
import entity.UmorzenieN;
import entityfk.Dokfk;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
@Named
@ViewScoped
public class AmodokView implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Amodok> amodoklist;
    private List<Amodok> amodoklistselected;
    @Inject
    private WpisView wpisView;
    @Inject
    private AmoDokDAO amoDokDAO;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private KlienciDAO klienciDAO;
    @Inject
    private RodzajedokDAO rodzajedokDAO;
    @Inject
    private TabelanbpDAO tabelanbpDAO;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    protected STRDAO sTRDAO;
    @Inject
    private UmorzenieNDAO umorzenieDAO;

    public AmodokView() {
        this.amodoklist = Collections.synchronizedList(new ArrayList<>());
    }

    @PostConstruct
    public void init() { //E.m(this);
        nowalistadokamo();
    }

    public void generujamodokumenty() {
        try {
            generujodpisy();
            generujdokumenty();
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    public void resetujnaliczeniasrodkowwszystkich() {
        List<SrodekTrw> srodkiTrwale = sTRDAO.findAll();
        if (srodkiTrwale == null || srodkiTrwale.size() == 0) {
            init();
            Msg.msg("Pobieram środki do umorzeń");
        }
        Iterator it = srodkiTrwale.iterator();
        while (it.hasNext()) {
            SrodekTrw srodek = (SrodekTrw) it.next();
            //jak tego nie bedzie to zresetuje odpisy sprzedanych
            if (srodek.getZlikwidowany() == 0) {
                srodek.setPlanumorzen(null);
                sTRDAO.edit(srodek);
                srodek.setPlanumorzen(SrodkiTrwBean.generujumorzeniadlasrodka(srodek, wpisView));
                sTRDAO.edit(srodek);
            }
        }
        Msg.msg("Odpisy wygenerowane. Pamiętaj o wygenerowaniu dokumentów umorzeń! W tym celu wybierz w menu stronę umorzenie");
    }

    private void odpisypojedynczysrodek(SrodekTrw srodek) {
        try {
//            srodek.setPlanumorzen(SrodkiTrwBean.generujumorzeniadlasrodka(srodek, wpisView));
//            sTRDAO.edit(srodek);
            SrodkiTrwBean.usunumorzeniapozniejsze(srodek, wpisView);
            sTRDAO.edit(srodek);
            List<UmorzenieN> umorzeniawygenerowane = SrodkiTrwBean.generujumorzeniadlasrodkaAmo(srodek, wpisView);
            if (srodek.getPlanumorzen() != null) {
                srodek.getPlanumorzen().addAll(umorzeniawygenerowane);
            } else {
                srodek.setPlanumorzen(umorzeniawygenerowane);
            }
            sTRDAO.edit(srodek);
        } catch (Exception e) {
            E.e(e);
        }
    }

    //przyporzadkowuje planowane odpisy do konkretnych miesiecy
    public void generujodpisy() {
        List<SrodekTrw> srodkiTrwale = sTRDAO.findStrPod(wpisView.getPodatnikWpisu());
        if (srodkiTrwale == null || srodkiTrwale.size() == 0) {
            init();
            Msg.msg("Pobieram środki do umorzeń");
        }
        Iterator it = srodkiTrwale.iterator();
        while (it.hasNext()) {
            SrodekTrw srodek = (SrodekTrw) it.next();
            //jak tego nie bedzie to zresetuje odpisy sprzedanych
            if (srodek.getZlikwidowany() == 0) {
                odpisypojedynczysrodek(srodek);
            }
        }
        Msg.msg("Odpisy wygenerowane. Pamiętaj o wygenerowaniu dokumentów umorzeń! W tym celu wybierz w menu stronę umorzenie");
    }
    
    private void generujdokumenty() {
        List<SrodekTrw> srodkiTrwale = sTRDAO.findStrPod(wpisView.getPodatnikWpisu());
        String pod = wpisView.getPodatnikWpisu();
        Integer rokOd = wpisView.getRokWpisu();
        Integer mcOd = Integer.parseInt(wpisView.getMiesiacWpisu());
        amoDokDAO.destroy(pod, rokOd, mcOd);
        Roki roki = new Roki();
        while (roki.getRokiList().contains(rokOd)) {
            Amodok amoDok = new Amodok(mcOd, pod, rokOd);
            for (SrodekTrw srodek : srodkiTrwale) {
                List<UmorzenieN> umorzenia = umorzenieDAO.findBySrodek(srodek);
                for (UmorzenieN umAkt : umorzenia) {
                    if ((umAkt.getRokUmorzenia() == rokOd) && (umAkt.getMcUmorzenia() == mcOd)) {
                        if (umAkt.getKwota() > 0) {
                            umAkt.setRodzaj(srodek.getTyp());
                            umAkt.setAmodok(amoDok);
                            amoDok.getPlanumorzen().add(umAkt);
                            if (srodek.getKontonetto() != null) {
                                umAkt.setKontonetto(srodek.getKontonetto().getPelnynumer());
                                umAkt.setKontoumorzenie(srodek.getKontoumorzenie().getPelnynumer());
                            }
                        }
                        break;
                    }
                }
            }
            if (amoDok.getPlanumorzen().isEmpty()) {
                amoDok.setZaksiegowane(true);
            }
            amoDokDAO.edit(amoDok);
            //ZAZNACZA PUSTE JAKO TRUe a to w celu zachwoania ciaglosci a to w celu pokazania ze sa sporzadzone za zadany okres a ze nie wsyatpil blad
            if (mcOd == 12) {
                rokOd++;
                mcOd = 1;

            } else {
                mcOd++;
            }
            
        }
        //sTRDAO.editList(srodkiTrwale);
        nowalistadokamo();
        Msg.msg("i", "Dokumenty amortyzacyjne wygenerowane od miesiąca " + wpisView.getMiesiacWpisu() + " roku " + wpisView.getRokWpisuSt());
    }

    private void nowalistadokamo() {
        if (wpisView.getPodatnikWpisu() != null) {
            try {
                amodoklist = amoDokDAO.amodokKlientRok(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            } catch (Exception e) {
                E.e(e);
            }
        }
    }

    public void oznaczjakozaksiegowane() {
        for (Amodok p : amodoklistselected) {
            if (p.getZaksiegowane() == false) {
                p.setZaksiegowane(true);
            } else {
                p.setZaksiegowane(false);
            }
            amoDokDAO.edit(p);
            Msg.msg("i", "Oznaczono AMO jako zaksięgowany");
        }
    }

    public void ksiegujUmorzenieFK(List<UmorzenieN> umorzenia) {
        Dokfk znalezionyBiezacy = dokDAOfk.findDokfkLastofaTypeMc(wpisView.getPodatnikObiekt(), "AMO", wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        Dokfk dokumentAMO = DokumentFKBean.generujdokument(wpisView, klienciDAO, "AMO", "zaksięgowanie umorzenia ", rodzajedokDAO, tabelanbpDAO, kontoDAOfk, umorzenia, dokDAOfk);
        String nrdokumentu = null;
        if (znalezionyBiezacy != null) {
            nrdokumentu = znalezionyBiezacy.getNumerwlasnydokfk();
            dokumentAMO.setNumerwlasnydokfk(DokumentFKBean.zwieksznumerojeden(nrdokumentu));
            Msg.msg("w", "Dokument amortyzacyjny za mc już jest zaksięgowany. Nie jest to prawidłowe!");
        }
        try {
            dokDAOfk.create(dokumentAMO);
            for (UmorzenieN n : umorzenia) {
                n.setDokfk(dokumentAMO);
            }
            umorzenieDAO.editList(umorzenia);
            Msg.msg("Zaksięgowano dokument AMO");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd - nie zaksięgowano dokumentu AMO");
        }
    }

    public List<Amodok> getAmodoklist() {
        return amodoklist;
    }

    public void setAmodoklist(List<Amodok> amodoklist) {
        this.amodoklist = amodoklist;
    }

    public List<Amodok> getAmodoklistselected() {
        return amodoklistselected;
    }

    public void setAmodoklistselected(List<Amodok> amodoklistselected) {
        this.amodoklistselected = amodoklistselected;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

}
