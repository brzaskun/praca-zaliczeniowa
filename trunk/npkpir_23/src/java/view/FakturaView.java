/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DokDAO;
import dao.EvewidencjaDAO;
import dao.FakturaDAO;
import embeddable.EVatwpis;
import embeddable.KwotaKolumna;
import embeddable.Pozycjenafakturzebazadanych;
import entity.Dok;
import entity.Evewidencja;
import entity.Faktura;
import entity.FakturaPK;
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

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class FakturaView implements Serializable{
    @Inject protected Faktura selected;
    @Inject private FakturaPK fakturaPK;
    private boolean pokazfakture;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private ArrayList<Pozycjenafakturzebazadanych> pozycje = new ArrayList<>();
    @Inject private FakturaDAO fakturaDAO;
    //faktury z bazy danych
    private List<Faktura> faktury;
    //faktury wybrane z listy
    private static List<Faktura> gosciwybral;
    //do zaksiegowania faktury
    @Inject private DokDAO dokDAO;
    @Inject private EvewidencjaDAO evewidencjaDAO;

    public FakturaView() {
        faktury = new ArrayList<>();
        gosciwybral = new ArrayList<>();
    }
    
    
        
    
    @PostConstruct
    private void init(){
        faktury = fakturaDAO.findAll();
    }

           
    
    public void przygotujfakture(){
        DateTime dt = new DateTime();
        LocalDate firstDate = dt.toLocalDate();
        selected.setDatawystawienia(firstDate.toString());
        selected.setDatasprzedazy(firstDate.toString());
        fakturaPK.setNumerkolejny("1/2013");
        fakturaPK.setWystawcanazwa(wpisView.getPodatnikWpisu());
        selected.setFakturaPK(fakturaPK);
        LocalDate terminplatnosci = firstDate.plusDays(14);
        selected.setTerminzaplaty(terminplatnosci.toString());
        selected.setNrkontabankowego("1145 5245");
        selected.setPodpis(wpisView.getPodatnikObiekt().getImie()+" "+wpisView.getPodatnikObiekt().getNazwisko());
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
    }
    
     
    
    public void dodaj() throws Exception{
        List<Pozycjenafakturzebazadanych> pozycje = selected.getPozycjenafakturze();
        double netto = 0.0;
        double vat = 0.0;
        double brutto = 0.0;
        ArrayList<Evewidencja> ew = new ArrayList<>();
        ew.addAll(evewidencjaDAO.znajdzpotransakcji("sprzedaz"));
        List<EVatwpis> el = new ArrayList<>();
        for (Pozycjenafakturzebazadanych p : pozycje){
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
            for (EVatwpis r : el){
                if (r.getEwidencja().equals(ewidencja)){
                    eVatwpis = r;
                }
            }
            if(eVatwpis.getNetto()!=0){
                eVatwpis.setNetto(eVatwpis.getNetto()+p.getNetto());
                eVatwpis.setVat(eVatwpis.getVat()+p.getPodatekkwota());
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
        if(wynik.equals("ok")){
            Msg.msg("i", "Dodano fakturę.");
            RequestContext.getCurrentInstance().update("form:akordeon:dokumentyLista");
        } else {
            Msg.msg("e", "Wystąpił błąd. Nie dodano faktury. "+wynik);
        }
        
    }
    
    private Evewidencja zwrocewidencje(List<Evewidencja> ewidencje, Pozycjenafakturzebazadanych p){
         for (Evewidencja r : ewidencje){
                if (r.getNazwa().contains(String.valueOf( (int) p.getPodatek()))){
                    return r;
                }
            }
        return null;
    }
    
    public void destroygrupa(){
        for (Faktura p : gosciwybral){
            try {
                fakturaDAO.destroy(p);
                faktury.remove(p);
                Msg.msg("i", "Usunięto fakturę "+p.getFakturaPK().getNumerkolejny());
                RequestContext.getCurrentInstance().update("form:akordeon:dokumentyLista");
            } catch (Exception e){
                Msg.msg("e", "Nie usunięto faktury "+p.getFakturaPK().getNumerkolejny());
            }
        }
    }
       
    public void dodajwiersz(){
        Pozycjenafakturzebazadanych poz = new Pozycjenafakturzebazadanych();
        pozycje.add(poz);
    }
    
    public void zaksieguj() throws Exception{
            Faktura faktura = gosciwybral.get(0);
            Dok selDokument = new Dok();
            selDokument.setEwidencjaVAT(null);
            HttpServletRequest request;
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Principal principal = request.getUserPrincipal();
            selDokument.setWprowadzil(principal.getName());
            String datawystawienia = faktura.getDatawystawienia();
            String miesiac = datawystawienia.substring(5,7);
            String rok = datawystawienia.substring(0,4);
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
                RequestContext.getCurrentInstance().update("form:akordeon:gotowe");
            } catch (Exception e) {
                Msg.msg("e", e.getMessage());
            }
    }
    
     public void sprawdzCzyNieDuplikat(Dok selD) throws Exception{
        Dok tmp = dokDAO.znajdzDuplikat(selD);
        if (tmp != null) {
            String wiadomosc = "Dokument dla tego klienta, o takim numerze i kwocie jest juz zaksiegowany: "+ tmp.getDataK();
            throw new Exception(wiadomosc);
        } else {
            System.out.println("Nie znaleziono duplikatu");
        }
    }
     
    public void wgenerujnumerfaktury(){
       List<Faktura> wykazfaktur = fakturaDAO.findbyKontrahent_nip(selected.getKontrahent().getNip());
       if(wykazfaktur.size()==0){
           String numer = "1/"+wpisView.getRokWpisu().toString()+"/"+selected.getKontrahent().getNskrocona();
           selected.getFakturaPK().setNumerkolejny(numer);
           Msg.msg("i", "Generuje nową serie numerów faktury"); 
       } else {
         String ostatniafaktura = wykazfaktur.get(wykazfaktur.size()-1).getFakturaPK().getNumerkolejny();
         String separator = "/";
         String[] elementy;
         elementy = ostatniafaktura.split(separator);
         int starynumer = Integer.parseInt(elementy[0]);
         starynumer++;
         String numer = String.valueOf(starynumer)+"/"+wpisView.getRokWpisu().toString()+"/"+selected.getKontrahent().getNskrocona();
         selected.getFakturaPK().setNumerkolejny(numer);
         Msg.msg("i", "Generuje kolejny numer faktury"); 
       }
       RequestContext.getCurrentInstance().update("form:akordeon:nrfaktury");
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
    //</editor-fold>
    
}
