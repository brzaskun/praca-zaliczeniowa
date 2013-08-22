/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DokDAO;
import dao.EvewidencjaDAO;
import dao.FakturaDAO;
import dao.FakturywystokresoweDAO;
import embeddable.EVatwpis;
import embeddable.KwotaKolumna;
import embeddable.Pozycjenafakturzebazadanych;
import entity.Dok;
import entity.Evewidencja;
import entity.Faktura;
import entity.FakturaPK;
import entity.Fakturyokresowe;
import entity.Fakturywystokresowe;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import msg.Msg;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.primefaces.context.RequestContext;
import serialclone.SerialClone;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class FakturaView implements Serializable {

    @Inject
    protected Faktura selected;
    @Inject
    private FakturaPK fakturaPK;
    private boolean pokazfakture;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private ArrayList<Pozycjenafakturzebazadanych> pozycje = new ArrayList<>();
    @Inject
    private FakturaDAO fakturaDAO;
    @Inject
    private FakturywystokresoweDAO fakturywystokresoweDAO;
    //faktury z bazy danych
    private List<Faktura> faktury;
    //faktury okresowe z bazy danych
    private List<Fakturywystokresowe> fakturyokresowe;
    //faktury wybrane z listy
    private static List<Faktura> gosciwybral;
    //faktury okresowe wybrane z listy
    private static List<Fakturywystokresowe> gosciwybralokres;
    //do zaksiegowania faktury
    @Inject
    private DokDAO dokDAO;
    @Inject
    private EvewidencjaDAO evewidencjaDAO;

    public FakturaView() {
        faktury = new ArrayList<>();
        fakturyokresowe = new ArrayList<>();
        gosciwybral = new ArrayList<>();
        gosciwybralokres = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        faktury = fakturaDAO.findAll();
        fakturyokresowe = fakturywystokresoweDAO.findPodatnik(wpisView.getPodatnikWpisu());
    }

    public void przygotujfakture() {
        DateTime dt = new DateTime();
        LocalDate firstDate = dt.toLocalDate();
        selected.setDatawystawienia(firstDate.toString());
        selected.setDatasprzedazy(firstDate.toString());
        fakturaPK.setNumerkolejny("wpisz numer");
        fakturaPK.setWystawcanazwa(wpisView.getPodatnikWpisu());
        selected.setFakturaPK(fakturaPK);
        LocalDate terminplatnosci = firstDate.plusDays(14);
        selected.setTerminzaplaty(terminplatnosci.toString());
        try {
            String nrkonta = wpisView.getPodatnikObiekt().getNrkontabankowego();
            if(nrkonta!=null){
                selected.setNrkontabankowego(nrkonta);
            } else {
                selected.setNrkontabankowego("brak numeru konta bankowego");
            }
        } catch (Exception es){
            selected.setNrkontabankowego("brak numeru konta bankowego");
        }
        selected.setPodpis(wpisView.getPodatnikObiekt().getImie() + " " + wpisView.getPodatnikObiekt().getNazwisko());
        Pozycjenafakturzebazadanych poz = new Pozycjenafakturzebazadanych();
        pozycje.add(poz);
        selected.setPozycjenafakturze(pozycje);
        selected.setAutor(wpisView.getWprowadzil().getLogin());
        selected.setMiejscewystawienia(wpisView.getPodatnikObiekt().getMiejscowosc());
        setPokazfakture(true);
        selected.setWystawca(wpisView.getPodatnikObiekt());
        selected.setRodzajdokumentu("faktura");
        selected.setRodzajtransakcji("sprzedaż");
        Msg.msg("i", "Przygotowano fakture");
        RequestContext.getCurrentInstance().update("form:panelfaktury");
        RequestContext.getCurrentInstance().execute("aktywuj()");
    }

    public void dodaj() throws Exception {
        List<Pozycjenafakturzebazadanych> pozycje = selected.getPozycjenafakturze();
        double netto = 0.0;
        double vat = 0.0;
        double brutto = 0.0;
        ArrayList<Evewidencja> ew = new ArrayList<>();
        ew.addAll(evewidencjaDAO.znajdzpotransakcji("sprzedaz"));
        List<EVatwpis> el = new ArrayList<>();
        for (Pozycjenafakturzebazadanych p : pozycje) {
            double ilosc = p.getIlosc();
            double cena = p.getCena();
            double wartosc = ilosc * cena * 100;
            wartosc = Math.round(wartosc);
            wartosc = wartosc / 100;
            netto += wartosc;
            p.setNetto(wartosc);
            double podatekstawka = p.getPodatek();
            double podatek = wartosc * podatekstawka;
            podatek = Math.round(podatek);
            podatek = podatek / 100;
            vat += podatek;
            p.setPodatekkwota(podatek);
            double bruttop = wartosc + podatek;
            brutto += bruttop;
            p.setBrutto(bruttop);
            EVatwpis eVatwpis = new EVatwpis();
            Evewidencja ewidencja = zwrocewidencje(ew, p);
            for (EVatwpis r : el) {
                if (r.getEwidencja().equals(ewidencja)) {
                    eVatwpis = r;
                }
            }
            if (eVatwpis.getNetto() != 0) {
                eVatwpis.setNetto(eVatwpis.getNetto() + p.getNetto());
                eVatwpis.setVat(eVatwpis.getVat() + p.getPodatekkwota());
                el.add(eVatwpis);
            } else {
                eVatwpis.setEwidencja(ewidencja);
                eVatwpis.setNetto(p.getNetto());
                eVatwpis.setVat(p.getPodatekkwota());
                eVatwpis.setEstawka(String.valueOf(p.getPodatek()));
                el.add(eVatwpis);
            }
        }
        selected.setKontrahent_nip(selected.getKontrahent().getNip());
        selected.setEwidencjavat(el);
        selected.setNetto(netto);
        selected.setVat(vat);
        selected.setBrutto(brutto);
        faktury.add(selected);
        String wynik = fakturaDAO.dodaj(selected);
        if (wynik.equals("ok")) {
            Msg.msg("i", "Dodano fakturę.");
            selected = new Faktura();
            pokazfakture = false;
            RequestContext.getCurrentInstance().update("form:akordeon:nowa");
            RequestContext.getCurrentInstance().update("form:akordeon:dokumentyLista");
        } else {
            Msg.msg("e", "Wystąpił błąd. Nie dodano faktury. " + wynik);
        }

    }

    private Evewidencja zwrocewidencje(List<Evewidencja> ewidencje, Pozycjenafakturzebazadanych p) {
        for (Evewidencja r : ewidencje) {
            if (r.getNazwa().contains(String.valueOf((int) p.getPodatek()))) {
                return r;
            }
        }
        return null;
    }

    public void destroygrupa() {
        for (Faktura p : gosciwybral) {
            try {
                fakturaDAO.destroy(p);
                faktury.remove(p);
                Msg.msg("i", "Usunięto fakturę " + p.getFakturaPK().getNumerkolejny());
            } catch (Exception e) {
                Msg.msg("e", "Nie usunięto faktury " + p.getFakturaPK().getNumerkolejny());
            }
        }
        RequestContext.getCurrentInstance().update("form:akordeon:dokumentyLista");
    }

    public void dodajwiersz() {
        Pozycjenafakturzebazadanych poz = new Pozycjenafakturzebazadanych();
        pozycje.add(poz);
    }

    public void zaksieguj() throws Exception {
        for (Faktura p : gosciwybral) {
            Faktura faktura = p;
            Dok selDokument = new Dok();
            selDokument.setEwidencjaVAT(null);
            HttpServletRequest request;
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Principal principal = request.getUserPrincipal();
            selDokument.setWprowadzil(principal.getName());
            String datawystawienia = faktura.getDatawystawienia();
            String miesiac = datawystawienia.substring(5, 7);
            String rok = datawystawienia.substring(0, 4);
            selDokument.setPkpirM(miesiac);
            selDokument.setPkpirR(rok);
            selDokument.setVatM(miesiac);
            selDokument.setVatR(rok);
            selDokument.setPodatnik(wpisView.getPodatnikWpisu());
            selDokument.setStatus("bufor");
            selDokument.setUsunpozornie(false);
            selDokument.setDataWyst(faktura.getDatawystawienia());
            selDokument.setDataSprz(faktura.getDatawystawienia());
            selDokument.setKontr(faktura.getKontrahent());
            selDokument.setRodzTrans("sprzedaz");
            selDokument.setTypdokumentu("SZ");
            selDokument.setNrWlDk(faktura.getFakturaPK().getNumerkolejny());
            selDokument.setOpis(faktura.getPozycjenafakturze().get(0).getNazwa());
            List<KwotaKolumna> listaX = new ArrayList<>();
            KwotaKolumna tmpX = new KwotaKolumna();
            tmpX.setNetto(faktura.getNetto());
            tmpX.setVat(faktura.getVat());
            tmpX.setNazwakolumny("przych. sprz");
            tmpX.setBrutto(faktura.getBrutto());
            listaX.add(tmpX);
            selDokument.setListakwot(listaX);
            selDokument.setNetto(tmpX.getNetto());
            selDokument.setBrutto(tmpX.getBrutto());
            selDokument.setRozliczony(true);
            selDokument.setEwidencjaVAT(faktura.getEwidencjavat());
            try {
                sprawdzCzyNieDuplikat(selDokument);
                dokDAO.dodaj(selDokument);
                Msg.msg("i", "Zaksięgowano fakturę sprzedaży");
                faktura.setZaksiegowana(true);
                fakturaDAO.edit(faktura);
            } catch (Exception e) {
                Msg.msg("e", e.getMessage());
            }
            RequestContext.getCurrentInstance().update("form:akordeon:dokumentyLista");
        }
    }

    public void sprawdzCzyNieDuplikat(Dok selD) throws Exception {
        Dok tmp = dokDAO.znajdzDuplikat(selD);
        if (tmp != null) {
            String wiadomosc = "Dokument dla tego klienta, o takim numerze i kwocie jest juz zaksiegowany: " + tmp.getDataK();
            throw new Exception(wiadomosc);
        } else {
            System.out.println("Nie znaleziono duplikatu");
        }
    }

    public void wgenerujnumerfaktury() {
        List<Faktura> wykazfaktur = fakturaDAO.findbyKontrahent_nip(selected.getKontrahent().getNip(), wpisView.getPodatnikWpisu());
        int rozpoznaj = 0;
        try {
            if (wykazfaktur.size() > 0) {
                rozpoznaj = 1;
            }
        } catch (Exception er){}
        if (rozpoznaj == 0) {
            String numer = "1/" + wpisView.getRokWpisu().toString() + "/" + selected.getKontrahent().getNskrocona();
            selected.getFakturaPK().setNumerkolejny(numer);
            Msg.msg("i", "Generuje nową serie numerów faktury");
        } else {
            String ostatniafaktura = wykazfaktur.get(wykazfaktur.size() - 1).getFakturaPK().getNumerkolejny();
            String separator = "/";
            String[] elementy;
            elementy = ostatniafaktura.split(separator);
            int starynumer = Integer.parseInt(elementy[0]);
            starynumer++;
            String numer = String.valueOf(starynumer) + "/" + wpisView.getRokWpisu().toString() + "/" + selected.getKontrahent().getNskrocona();
            selected.getFakturaPK().setNumerkolejny(numer);
            Msg.msg("i", "Generuje kolejny numer faktury");
        }
        RequestContext.getCurrentInstance().update("form:akordeon:nrfaktury");
    }

    public void dodajfaktureokresowa() {
        for (Faktura p : gosciwybral) {
            Fakturywystokresowe fakturyokr = new Fakturywystokresowe();
            fakturyokr.setDokument(p);
            fakturyokr.setPodatnik(wpisView.getPodatnikWpisu());
            fakturyokresowe.add(fakturyokr);
            fakturywystokresoweDAO.dodaj(fakturyokr);
            Msg.msg("i", "Dodano fakturę okresową");
        }
        RequestContext.getCurrentInstance().update("form:akordeon:dokumentyLista");
        RequestContext.getCurrentInstance().update("form:akordeon:dokumentyOkresowe");
    }

    public void usunfaktureokresowa() {
        for (Fakturywystokresowe p : gosciwybralokres) {
            fakturyokresowe.remove(p);
            fakturywystokresoweDAO.destroy(p);
            Msg.msg("i", "Usunięto fakturę okresową");
        }
        RequestContext.getCurrentInstance().update("form:akordeon:dokumentyOkresowe");
    }

    public void wygenerujzokresowych() {
        for (Fakturywystokresowe p : gosciwybralokres) {
            Faktura nowa = SerialClone.clone(p.getDokument());
            nowa.setDatasprzedazy(null);
            DateTime dt = new DateTime();
            LocalDate firstDate = dt.toLocalDate();
            nowa.setDatawystawienia(firstDate.toString());
            nowa.setDatasprzedazy(firstDate.toString());
            List<Faktura> wykazfaktur = fakturaDAO.findbyKontrahent_nip(nowa.getKontrahent().getNip(), wpisView.getPodatnikWpisu());
            if (wykazfaktur.size() == 0) {
                String numer = "1/" + wpisView.getRokWpisu().toString() + "/" + nowa.getKontrahent().getNskrocona();
                nowa.getFakturaPK().setNumerkolejny(numer);
            } else {
                String ostatniafaktura = wykazfaktur.get(wykazfaktur.size() - 1).getFakturaPK().getNumerkolejny();
                String separator = "/";
                String[] elementy;
                elementy = ostatniafaktura.split(separator);
                int starynumer = Integer.parseInt(elementy[0]);
                starynumer++;
                String numer = String.valueOf(starynumer) + "/" + wpisView.getRokWpisu().toString() + "/" + nowa.getKontrahent().getNskrocona();
                nowa.getFakturaPK().setNumerkolejny(numer);
            }
            nowa.setWyslana(false);
            nowa.setZaksiegowana(false);
            nowa.setZatwierdzona(false);
            selected.setAutor(wpisView.getWprowadzil().getLogin());
            fakturaDAO.dodaj(nowa);
            faktury.add(nowa);
            Fakturywystokresowe okresowe = p;
            String datawystawienia = nowa.getDatawystawienia();
            String miesiac = datawystawienia.substring(5, 7);
            switch (miesiac) {
                case "01":
                    okresowe.setM1(okresowe.getM1() + 1);
                    break;
                case "02":
                    okresowe.setM2(okresowe.getM2() + 1);
                    break;
                case "03":
                    okresowe.setM3(okresowe.getM3() + 1);
                    break;
                case "04":
                    okresowe.setM4(okresowe.getM4() + 1);
                    break;
                case "05":
                    okresowe.setM5(okresowe.getM5() + 1);
                    break;
                case "06":
                    okresowe.setM6(okresowe.getM6() + 1);
                    break;
                case "07":
                    okresowe.setM7(okresowe.getM7() + 1);
                    break;
                case "08":
                    okresowe.setM8(okresowe.getM8() + 1);
                    break;
                case "09":
                    okresowe.setM9(okresowe.getM9() + 1);
                    break;
                case "10":
                    okresowe.setM10(okresowe.getM10() + 1);
                    break;
                case "11":
                    okresowe.setM11(okresowe.getM11() + 1);
                    break;
                case "12":
                    okresowe.setM12(okresowe.getM12() + 1);
                    break;
            }
            fakturywystokresoweDAO.edit(okresowe);
            Msg.msg("i", "Generuje bieżącą fakturę z okresowej");
        }
        RequestContext.getCurrentInstance().update("form:akordeon:dokumentyLista");
        RequestContext.getCurrentInstance().update("form:akordeon:dokumentyOkresowe");

    }
    //<editor-fold defaultstate="collapsed" desc="comment">

    public Faktura getSelected() {
        return selected;
    }

    public void setSelected(Faktura selected) {
        this.selected = selected;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public ArrayList<Pozycjenafakturzebazadanych> getPozycje() {
        return pozycje;
    }

    public void setPozycje(ArrayList<Pozycjenafakturzebazadanych> pozycje) {
        this.pozycje = pozycje;
    }

    public FakturaPK getFakturaPK() {
        return fakturaPK;
    }

    public void setFakturaPK(FakturaPK fakturaPK) {
        this.fakturaPK = fakturaPK;
    }

    public boolean isPokazfakture() {
        return pokazfakture;
    }

    public void setPokazfakture(boolean pokazfakture) {
        this.pokazfakture = pokazfakture;
    }

    public List<Faktura> getFaktury() {
        return faktury;
    }

    public void setFaktury(List<Faktura> faktury) {
        this.faktury = faktury;
    }

    public static List<Faktura> getGosciwybralS() {
        return gosciwybral;
    }

    public List<Faktura> getGosciwybral() {
        return gosciwybral;
    }

    public void setGosciwybral(List<Faktura> gosciwybral) {
        this.gosciwybral = gosciwybral;
    }

    public List<Fakturywystokresowe> getGosciwybralokres() {
        return gosciwybralokres;
    }

    public void setGosciwybralokres(List<Fakturywystokresowe> gosciwybralokres) {
        FakturaView.gosciwybralokres = gosciwybralokres;
    }

    public List<Fakturywystokresowe> getFakturyokresowe() {
        return fakturyokresowe;
    }

    public void setFakturyokresowe(List<Fakturywystokresowe> fakturyokresowe) {
        this.fakturyokresowe = fakturyokresowe;
    }
    //</editor-fold>
}
