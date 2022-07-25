/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import beansFK.KontaFKBean;
import comparator.SaldoKontocomparator;
import dao.KlienciDAO;
import dao.RodzajedokDAO;
import dao.StronaWierszaDAO;
import dao.DokDAOfk;
import dao.KontoDAOfk;
import dao.TabelanbpDAO;
import dao.WalutyDAOfk;
import dao.WierszBODAO;
import data.Data;
import embeddable.Kwartaly;
import embeddable.Mce;
import embeddable.Parametr;
import embeddablefk.SaldoKonto;
import entity.Klienci;
import entity.Rodzajedok;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import entityfk.Wiersz;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import msg.Msg;import view.EwidencjaVatView;
import view.ParametrView;
import view.WpisView;import waluty.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class KontaVatFKView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<SaldoKonto> kontavat;
    @Inject
    private WpisView wpisView;
    @Inject
    private EwidencjaVatView ewidencjaVatView;
    @Inject
    private WierszBODAO wierszBODAO;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private KlienciDAO klienciDAO;
    @Inject
    private RodzajedokDAO rodzajedokDAO;
    @Inject
    private TabelanbpDAO tabelanbpDAO;
    @Inject
    private WalutyDAOfk walutyDAOfk;
    private boolean istniejejuzdokumentvat;
//    boolean dodajBO;

    public KontaVatFKView() {
         ////E.m(this);
    }
    
    
    
    @PostConstruct
    public void init() { //E.m(this);
       if (wpisView.getMiesiacWpisu().equals("CR")) {
           wpisView.setMiesiacWpisu(Data.aktualnyMc());
           wpisView.wpisAktualizuj();
       }
       List<Konto> kontaklienta = kontoDAOfk.findKontaVAT(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
       kontavat = przygotowanalistasald(kontaklienta);
       istniejejuzdokumentvat = false;
       Dokfk popDokfk = dokDAOfk.findDokfofaTypeKilka(wpisView.getPodatnikObiekt(), "VAT", wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        if (popDokfk != null) {
            istniejejuzdokumentvat = true;
        }
    }
    
//    public void dodajBOdoKont() {
//        //dodajBO = true;
//        init();
//        PrimeFaces.current().ajax().update("form:akorderonbis:saldokontvat");
//        Msg.msg("Dodano zapisy z BO");
//    }
    
    private List<SaldoKonto> przygotowanalistasald(List<Konto> kontaklienta) {
        List<SaldoKonto> przygotowanalista = Collections.synchronizedList(new ArrayList<>());
        int[] licznik = {0};
        String vatokres = sprawdzjakiokresvat();
        kontaklienta.stream().forEach((p) ->{
            SaldoKonto saldoKonto = new SaldoKonto();
            saldoKonto.setId(licznik[0]++);
            saldoKonto.setKonto(p);
            //naniesBOnaKonto(saldoKonto, p);
            if (p.getPelnynumer().equals("221-4")) {
                naniesZapisyNaKonto2214(saldoKonto, p);
            } else if (p.getPelnynumer().equals("221-2")){
                naniesZapisyNaKonto2214(saldoKonto, p);
            } else {
                naniesZapisyNaKonto(saldoKonto, p, vatokres);
            }
            saldoKonto.sumujBOZapisy();
            saldoKonto.wyliczSaldo();
            dodajdolisty(saldoKonto, przygotowanalista);
        });
        Collections.sort(przygotowanalista, new SaldoKontocomparator());
        return przygotowanalista;
    }

    

//    private void naniesBOnaKonto(SaldoKonto saldoKonto, Konto p) {
//        if (dodajBO) {
//            List<StronaWiersza> zapisyBO = BOFKBean.pobierzZapisyBO(p, wierszBODAO, wpisView);
//            for (StronaWiersza r : zapisyBO) {
//                if (r.getWnma().equals("Wn")) {
//                    saldoKonto.setBoWn(saldoKonto.getBoWn() + r.getKwotaPLN());
//                } else {
//                    saldoKonto.setBoMa(saldoKonto.getBoMa() + r.getKwotaPLN());
//                }
//            }
//        }
//    }

    private void naniesZapisyNaKonto(SaldoKonto saldoKonto, Konto p, String vatokres) {
        List<StronaWiersza> zapisyRok  = null;
        String okresvat = sprawdzjakiokresvat();
        zapisyRok = zmodyfikujlisteMcKw(KontaFKBean.pobierzZapisyVATRok(p, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), stronaWierszaDAO), okresvat);
        double sumawn = 0.0;
        double sumama = 0.0;
        if (zapisyRok != null) {
            for (StronaWiersza r : zapisyRok) {
                saldoKonto.getZapisy().add(r);
                if (r.getWnma().equals("Wn")) {
                    sumawn += r.getKwotaPLN();
                } else {
                    sumama += r.getKwotaPLN();
                }
            }
        }
        saldoKonto.setObrotyWn(sumawn);
        saldoKonto.setObrotyMa(sumama);
    }
    private String sprawdzjakiokresvat() {
        Integer rok = wpisView.getRokWpisu();
        Integer mc = Integer.parseInt(wpisView.getMiesiacWpisu());
        List<Parametr> parametry = wpisView.getPodatnikObiekt().getVatokres();
        return ParametrView.zwrocParametr(parametry, rok, mc);
    }
    
    
    private List<StronaWiersza> zmodyfikujlisteMcKw(List<StronaWiersza> listadokvat, String vatokres){
        try {
            switch (vatokres) {
                case "blad":
                    Msg.msg("e", "Nie ma ustawionego parametru vat za dany okres. Nie można sporządzić ewidencji VAT.");
                    throw new Exception("Nie ma ustawionego parametru vat za dany okres");
                case "miesięczne": {
                    List<StronaWiersza> listatymczasowa = Collections.synchronizedList(new ArrayList<>());
                    for (StronaWiersza p : listadokvat) {
                        if (p.getDokfk().getMiesiac().equals(wpisView.getMiesiacWpisu())) {
                            listatymczasowa.add(p);
                        }
                    }
                    return listatymczasowa;
                }
                default: {
                    List<StronaWiersza> listatymczasowa = Collections.synchronizedList(new ArrayList<>());
                    Integer kwartal = Integer.parseInt(Kwartaly.getMapanrkw().get(Integer.parseInt(wpisView.getMiesiacWpisu())));
                    List<String> miesiacewkwartale = Kwartaly.getMapakwnr().get(kwartal);
                    for (StronaWiersza p : listadokvat) {
                        String mc = p.getDokfk().getMiesiac();
                        if (mc.equals(miesiacewkwartale.get(0)) || mc.equals(miesiacewkwartale.get(1)) || mc.equals(miesiacewkwartale.get(2))) {
                                listatymczasowa.add(p);
                        }
                    }
                    return listatymczasowa;
                }
            }
        } catch (Exception e) {  E.e(e);
            Msg.msg("e", "Blada nietypowy plik KontaVarFKView zmodyfikujliste ");
            return null;
        }
    }
    
    private void naniesZapisyNaKonto2214(SaldoKonto saldoKonto, Konto p) {
        int granicaDolna = Mce.getMiesiacToNumber().get("01");
        int granicaGorna = Mce.getMiesiacToNumber().get(wpisView.getMiesiacWpisu());
        List<StronaWiersza> zapisyRok  = KontaFKBean.pobierzZapisyVATRok(p, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), stronaWierszaDAO);
        String vatokres = sprawdzjakiokresvat();
        int mcgornybo = vatokres.equals("miesięczne") ? 2 : 3;
        //nie rozumiem dlaczego usuwalem na dodatek dwie petle w jednej funkcji!
//        for (Iterator<StronaWiersza> it = zapisyRok.iterator(); it.hasNext();) {
//            int mc = Mce.getMiesiacToNumber().get(it.next().getWiersz().getDokfk().getMiesiac());
//                if (mc < mcgornybo) {
//                    it.remove();
//                }
//        }
        double sumawn = 0.0;
        double sumama = 0.0;
        if (zapisyRok != null) {
            for (StronaWiersza r : zapisyRok) {
                int mc = Mce.getMiesiacToNumber().get(r.getWiersz().getDokfk().getMiesiac());
                if (mc >= granicaDolna && mc <=granicaGorna) {
                    saldoKonto.getZapisy().add(r);
                    if (r.getWnma().equals("Wn")) {
                        sumawn += r.getKwotaPLN();
                    } else {
                        sumama += r.getKwotaPLN();
                    }
                }
            }
        }
        saldoKonto.setObrotyWn(sumawn);
        saldoKonto.setObrotyMa(sumama);
    }
    
    private void naniesZapisyNaKonto2214Rok(SaldoKonto saldoKonto, Konto p) {
        int granicaDolna = Mce.getMiesiacToNumber().get("01");
        int granicaGorna = Mce.getMiesiacToNumber().get("12");
        List<StronaWiersza> zapisyRok  = null;
//        if (kontonastepnymc(p)) {
//            String[] nowyrokmc = Mce.zmniejszmiesiac(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
//            if (nowyrokmc[0].equals(wpisView.getRokWpisuSt())) {
//                zapisyRok = KontaFKBean.pobierzZapisyVATRokMc(p, wpisView.getPodatnikObiekt(), nowyrokmc[0], nowyrokmc[1], stronaWierszaDAO);
//            }
//        } else  {
            zapisyRok = KontaFKBean.pobierzZapisyVATRok(p, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), stronaWierszaDAO);
//        }
        double sumawn = 0.0;
        double sumama = 0.0;
        if (zapisyRok != null) {
            for (StronaWiersza r : zapisyRok) {
                int mc = Mce.getMiesiacToNumber().get(r.getWiersz().getDokfk().getMiesiac());
                if (mc >= granicaDolna && mc <=granicaGorna) {
                    saldoKonto.getZapisy().add(r);
                    if (r.getWnma().equals("Wn")) {
                        sumawn += r.getKwotaPLN();
                    } else {
                        sumama += r.getKwotaPLN();
                    }
                }
            }
        }
        saldoKonto.setObrotyWn(sumawn);
        saldoKonto.setObrotyMa(sumama);
    }
    
    private boolean kontonastepnymc(Konto p) {
        if (p.getNazwapelna().contains("następny")) {
            return true;
        }
        return false;
    }

    private void dodajdolisty(SaldoKonto saldoKonto, List<SaldoKonto> przygotowanalista) {
        if (saldoKonto.getObrotyBoWn() != 0.0) {
            przygotowanalista.add(saldoKonto);
            return;
        }
        if (saldoKonto.getObrotyBoMa() != 0.0) {
            przygotowanalista.add(saldoKonto);
            return;
        }
    }

    public void generowanieDokumentuVAT(boolean zachowajstarynie0tak1) {
        double saldo2214 = 0.0;
        for (SaldoKonto p : kontavat) {
            if (p.getKonto().getPelnynumer().equals("221-4")) {
                if (p.getSaldoWn() > 0.0) {
                    saldo2214 = p.getSaldoWn();
                }
                if (p.getSaldoMa() > 0.0) {
                    saldo2214 = p.getSaldoMa();
                }
            }
        }
        double saldo2212 = 0.0;
        for (SaldoKonto p : kontavat) {
            if (p.getKonto().getPelnynumer().equals("221-2")) {
                if (p.getSaldoWn() > 0.0) {
                    saldo2212 = p.getSaldoWn();
                }
                if (p.getSaldoMa() > 0.0) {
                    saldo2212 = p.getSaldoMa();
                }
            }
        }
        double przesuniete = Z.z(ewidencjaVatView.getSumaprzesunietych());
        double przesunieteBardziej = Z.z(ewidencjaVatView.getSumaprzesunietychBardziej());
        SaldoKonto saldodlaprzesunietychBardziej = saldo2214Rok(kontoDAOfk.findKonto("221-4", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        double roznica = Z.z(saldodlaprzesunietychBardziej.getSaldoWn() - (przesuniete+przesunieteBardziej));
        double przesunieteprzychodu = Z.z(ewidencjaVatView.getSumaprzesunietychprzychody());
        double przesunieteBardziejprzychody = Z.z(ewidencjaVatView.getSumaprzesunietychBardziejPrzychody());
        SaldoKonto saldodlaprzesunietychBardziejPrzychody = saldo2212Rok(kontoDAOfk.findKonto("221-2", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        double roznicaprzychody = Z.z(saldodlaprzesunietychBardziejPrzychody.getSaldoMa() - (przesunieteprzychodu+przesunieteBardziejprzychody));
        if (Z.z(saldo2212) < Z.z(przesunieteprzychodu) || roznicaprzychody < 0) {
            Msg.msg("e", "Dokumenty z innym miesiącem VAT w ewidencji nie posiadają zapisów na koncie 221-2");
            return;
        } else {
            for (SaldoKonto p : kontavat) {
                if (p.getKonto().getPelnynumer().equals("221-2")) {
                    if (przesunieteprzychodu >= 0) {
                        p.setSaldoMa(przesunieteprzychodu);
                    }
                    if (przesunieteprzychodu < 0) {
                        p.setSaldoWn(przesunieteprzychodu);
                    }
                }
            }
        }
        if (Z.z(saldo2214) < Z.z(przesuniete) || roznica < 0) {
            Msg.msg("e", "Dokumenty z innym miesiącem VAT w ewidencji nie posiadają zapisów na koncie 221-4. Sprawdz BO");
            return;
        } else {
            for (SaldoKonto p : kontavat) {
                if (p.getKonto().getPelnynumer().equals("221-4")) {
                    if (przesuniete >= 0) {
                        p.setSaldoWn(przesuniete);
                    }
                    if (przesuniete < 0) {
                        p.setSaldoMa(przesuniete);
                    }
                }
            }
        }
        Dokfk dokumentvat = null;
        int nrkolejny = oblicznumerkolejny();
        if (nrkolejny > 0 && !zachowajstarynie0tak1) {
            usundokumentztegosamegomiesiaca();
            nrkolejny = oblicznumerkolejny();
            dokumentvat = stworznowydokument(nrkolejny);
        } else {
            Dokfk popDokfk = dokDAOfk.findDokfofaTypeKilka(wpisView.getPodatnikObiekt(), "VAT", wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
            if (popDokfk != null) {
                dokumentvat = stworznowydokumentdoksieguj(nrkolejny, popDokfk.getNumerwlasnydokfk().split("/")[0]);
            }
        }
        try {
            if (dokumentvat.getListawierszy()!=null&&dokumentvat.getListawierszy().size()>0) {
                dokDAOfk.create(dokumentvat);
                Msg.msg("Zaksięgowano dokument VAT");
            } else {
                Msg.msg("w","Nie ma sald na kontach vat. Nie generuje dokumentu");
            }
        } catch (Exception e) {  E.e(e);
            Msg.msg("e", "Wystąpił błąd - nie zaksięgowano dokumentu VAT");
        }
    }
    
     private SaldoKonto saldo2214Rok(Konto p) {
        SaldoKonto saldoKonto = new SaldoKonto();
        int licznik = 0;
        if (p.getPelnynumer().equals("221-4")) {
            saldoKonto.setId(licznik++);
            saldoKonto.setKonto(p);
            naniesZapisyNaKonto2214Rok(saldoKonto, p);
            saldoKonto.sumujBOZapisy();
            saldoKonto.wyliczSaldo();
        }
        return saldoKonto;
    }
     private SaldoKonto saldo2212Rok(Konto p) {
        SaldoKonto saldoKonto = new SaldoKonto();
        int licznik = 0;
        if (p.getPelnynumer().equals("221-2")) {
            saldoKonto.setId(licznik++);
            saldoKonto.setKonto(p);
            naniesZapisyNaKonto2214Rok(saldoKonto, p);
            saldoKonto.sumujBOZapisy();
            saldoKonto.wyliczSaldo();
        }
        return saldoKonto;
    }
    
    private int oblicznumerkolejny() {
        Dokfk poprzednidokumentvat = dokDAOfk.findDokfkLastofaType(wpisView.getPodatnikObiekt(), "VAT", wpisView.getRokWpisuSt());
        return poprzednidokumentvat == null ? 1 : poprzednidokumentvat.getNrkolejnywserii()+1;
    }

    private void usundokumentztegosamegomiesiaca() {
        List<Dokfk> popDokfk = dokDAOfk.findDokfofaTypeKilkaLista(wpisView.getPodatnikObiekt(), "VAT", wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        if (popDokfk != null) {
            for (Dokfk dok : popDokfk) {
                dokDAOfk.remove(dok);
            }
        }
    }
    
    private Dokfk stworznowydokument(int nrkolejny) {
        Dokfk nd = new Dokfk(nrkolejny, wpisView.getRokWpisuSt());
        ustawdaty(nd);
        ustawkontrahenta(nd);
        ustawnumerwlasny(nd);
        nd.setOpisdokfk("przeksięgowanie VAT za: "+wpisView.getMiesiacWpisu()+"/"+wpisView.getRokWpisuSt());
        nd.setPodatnikObj(wpisView.getPodatnikObiekt());
        ustawrodzajedok(nd);
        ustawtabelenbp(nd);
        ustawwiersze(nd);
        nd.przeliczKwotyWierszaDoSumyDokumentu();
        return nd;
    }
    
    private Dokfk stworznowydokumentdoksieguj(int nrkolejny, String numerwlasny) {
        Dokfk nd = new Dokfk(nrkolejny, wpisView.getRokWpisuSt());
        ustawdaty(nd);
        ustawkontrahenta(nd);
        ustawnumerwlasny(nd, numerwlasny);
        nd.setOpisdokfk("przeksięgowanie VAT za: "+wpisView.getMiesiacWpisu()+"/"+wpisView.getRokWpisuSt());
        nd.setPodatnikObj(wpisView.getPodatnikObiekt());
        ustawrodzajedok(nd);
        ustawtabelenbp(nd);
        ustawwiersze(nd);
        nd.przeliczKwotyWierszaDoSumyDokumentu();
        return nd;
    }

    private void ustawdaty(Dokfk nd) {
        String datadokumentu = Data.ostatniDzien(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        nd.setDatadokumentu(datadokumentu);
        nd.setDataoperacji(datadokumentu);
        nd.setDatawplywu(datadokumentu);
        nd.setDatawystawienia(datadokumentu);
        nd.setDataujecia(new Date());
        nd.setMiesiac(wpisView.getMiesiacWpisu());
        nd.setVatM(wpisView.getMiesiacWpisu());
        nd.setVatR(wpisView.getRokWpisuSt());
    }

    private void ustawkontrahenta(Dokfk nd) {
        try {
            Klienci k = klienciDAO.findKlientByNip(wpisView.getPodatnikObiekt().getNip());
            nd.setKontr(k);
        } catch (Exception e) {  E.e(e);
            
        }
    }

    private void ustawnumerwlasny(Dokfk nd) {
        String numer = "1/"+wpisView.getMiesiacWpisu()+"/"+wpisView.getRokWpisuSt()+"/VAT";
        nd.setNumerwlasnydokfk(numer);
    }
    
    private void ustawnumerwlasny(Dokfk nd, String numerwlasny) {
        int nowynumer = Integer.parseInt(numerwlasny)+1;
        String numer = nowynumer+"/"+wpisView.getMiesiacWpisu()+"/"+wpisView.getRokWpisuSt()+"/VAT";
        nd.setNumerwlasnydokfk(numer);
    }

    private void ustawrodzajedok(Dokfk nd) {
        Rodzajedok rodzajedok = rodzajedokDAO.find("VAT", wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        if (rodzajedok != null) {
            nd.setSeriadokfk(rodzajedok.getSkrot());
            nd.setRodzajedok(rodzajedok);
        } else {
            Msg.msg("e", "Brak zdefiniowanego dokumentu VAT");
        }
    }

    private void ustawtabelenbp(Dokfk nd) {
        Tabelanbp t = tabelanbpDAO.findByTabelaPLN();
        nd.setTabelanbp(t);
        Waluty w = walutyDAOfk.findWalutaBySymbolWaluty("PLN");
        nd.setWalutadokumentu(w);
    }

    private void ustawwiersze(Dokfk nd) {
        nd.setListawierszy(new ArrayList<Wiersz>());
        int idporzadkowy = 1;
        dodajzaokraglenia(kontavat);
        Konto kontoRozrachunkizUS = kontoDAOfk.findKonto("222", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        for (SaldoKonto p : kontavat) {
            if (p.getSaldoWn() != 0.0 || p.getSaldoMa() != 0.0) {
                Wiersz w = new Wiersz(idporzadkowy++, nd, 0);
                uzupelnijwiersz(w, nd);
                String opiswiersza = "przeksięg. konto: "+p.getKonto().getPelnynumer();
                w.setOpisWiersza(opiswiersza);
                if (p.getSaldoWn() != 0.0) {
                    StronaWiersza wn = new StronaWiersza(w, "Wn", p.getSaldoWn(), kontoRozrachunkizUS);
                    wn.setKwotaPLN(p.getSaldoWn());
                    StronaWiersza ma = new StronaWiersza(w, "Ma", p.getSaldoWn(), p.getKonto());
                    ma.setKwotaPLN(p.getSaldoWn());
                    w.setStronaWn(wn);
                    w.setStronaMa(ma);
                } else {
                    StronaWiersza wn = new StronaWiersza(w, "Wn", p.getSaldoMa(), p.getKonto());
                    wn.setKwotaPLN(p.getSaldoMa());
                    StronaWiersza ma = new StronaWiersza(w, "Ma", p.getSaldoMa(), kontoRozrachunkizUS);
                    ma.setKwotaPLN(p.getSaldoMa());
                    w.setStronaWn(wn);
                    w.setStronaMa(ma);
                }
                nd.getListawierszy().add(w);
            }
        }
    }

    private void uzupelnijwiersz(Wiersz w, Dokfk nd) {
        Tabelanbp t = tabelanbpDAO.findByTabelaPLN();
        w.setTabelanbp(t);
        w.setDokfk(nd);
        w.setLpmacierzystego(0);
        w.setTabelanbp(w.getTabelanbp());
        w.setDataksiegowania(nd.getDatawplywu());
    }

    private void dodajzaokraglenia(List<SaldoKonto> kontavat) {
        double nalezny = 0.0;
        double naliczony = 0.0;
        for (SaldoKonto p : kontavat) {
            if (p.getKonto().getNazwapelna().contains("należny")) {
                nalezny += p.getSaldoMa() > 0.0 ? p.getSaldoMa() : 0.0;
                nalezny -= p.getSaldoWn() > 0.0 ? p.getSaldoWn() : 0.0;
            } else {
                naliczony -= p.getSaldoMa() > 0.0 ? p.getSaldoMa() : 0.0;
                naliczony += p.getSaldoWn() > 0.0 ? p.getSaldoWn() : 0.0;
            }
        }
        double zaokraglenianalezny = Z.z(nalezny % 1);
        double zaokraglenianaliczony = Z.z(naliczony % 1);
        SaldoKonto saldokontoNalezny = zrobSaldoKontoNalezny(zaokraglenianalezny);
        if (saldokontoNalezny != null) {
            kontavat.add(saldokontoNalezny);
        }
        SaldoKonto saldokontoNaliczony = zrobSaldoKontoNaliczony(zaokraglenianaliczony);
        if (saldokontoNaliczony != null) {
            kontavat.add(saldokontoNaliczony);
        }
    }
   
    private SaldoKonto zrobSaldoKontoNalezny(double zaokraglenia) {
        SaldoKonto zwrot = null;
        Konto pozostaleprzychodyoperacyjne = kontoDAOfk.findKontoNazwaPodatnik("Pozostałe przych. operac.", wpisView);
        Konto pozostalekosztyoperacyjne = kontoDAOfk.findKontoNazwaPodatnik("Pozostałe koszty operac.", wpisView);
        if (zaokraglenia >= 0.5) {
            zwrot =  new SaldoKonto(pozostalekosztyoperacyjne, 0.0, 1-zaokraglenia);
        } else if (zaokraglenia > 0.0)  {
            return new SaldoKonto(pozostaleprzychodyoperacyjne, zaokraglenia, 0.0);
        } else if (zaokraglenia <= -0.5) {
            zwrot =  new SaldoKonto(pozostalekosztyoperacyjne, 0.0, 1+zaokraglenia);
        } else if (zaokraglenia < 0.0)  {
            return new SaldoKonto(pozostaleprzychodyoperacyjne, -zaokraglenia, 0.0);
        }
        return zwrot;
    }
    
    private SaldoKonto zrobSaldoKontoNaliczony(double zaokraglenia) {
        SaldoKonto zwrot = null;
        Konto pozostaleprzychodyoperacyjne = kontoDAOfk.findKontoNazwaPodatnik("Pozostałe przych. operac.", wpisView);
        Konto pozostalekosztyoperacyjne = kontoDAOfk.findKontoNazwaPodatnik("Pozostałe koszty operac.", wpisView);
        if (zaokraglenia >= 0.5) {
            zwrot =  new SaldoKonto(pozostaleprzychodyoperacyjne, 1- zaokraglenia, 0.0);
        } else if (zaokraglenia > 0.0) {
            zwrot =  new SaldoKonto(pozostalekosztyoperacyjne, 0.0, zaokraglenia);
        } else if (zaokraglenia <= -0.5) {
            zwrot =  new SaldoKonto(pozostaleprzychodyoperacyjne, 1+ zaokraglenia, 0.0);
        } else if (zaokraglenia < 0.0)  {
            zwrot =  new SaldoKonto(pozostalekosztyoperacyjne, 0.0, -zaokraglenia);
        }
        return zwrot;
    }
    
     public static void main(String[] args) {
        double r = 4.235646;
        double zaokr = r % 1;
        zaokr = Math.round(zaokr*100);
        zaokr /= 100;
    }
 //<editor-fold defaultstate="collapsed" desc="comment">
    public List<SaldoKonto> getKontavat() {
         return kontavat;
     }

    public void setKontavat(List<SaldoKonto> kontavat) {
        this.kontavat = kontavat;
    }

    public boolean isIstniejejuzdokumentvat() {
        return istniejejuzdokumentvat;
    }

    public void setIstniejejuzdokumentvat(boolean istniejejuzdokumentvat) {
        this.istniejejuzdokumentvat = istniejejuzdokumentvat;
    }

    public EwidencjaVatView getEwidencjaVatView() {
        return ewidencjaVatView;
    }

    public void setEwidencjaVatView(EwidencjaVatView ewidencjaVatView) {
        this.ewidencjaVatView = ewidencjaVatView;
    }
    public WpisView getWpisView() {
        return wpisView;
    }
     
    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
//</editor-fold>
   

     


}
