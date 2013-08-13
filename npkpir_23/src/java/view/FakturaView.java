/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.FakturaDAO;
import embeddable.Pozycjenafakturzebazadanych;
import entity.Faktura;
import entity.FakturaPK;
import entity.Pozycjenafakturze;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
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
    
     
    
    public void dodaj(){
        List<Pozycjenafakturzebazadanych> pozycje = selected.getPozycjenafakturze();
        for (Pozycjenafakturzebazadanych p : pozycje){
            double ilosc = p.getIlosc();
            double cena = p.getCena();
            double wartosc = Math.round(ilosc * cena);
            p.setNetto(wartosc);
            double podatekstawka = p.getPodatek();
            double podatek = Math.round(wartosc * podatekstawka / 100);
            p.setPodatekkwota(podatek);
            double brutto = wartosc + podatek;
            p.setBrutto(brutto);
        }
        String wynik = fakturaDAO.dodaj(selected);
        if(wynik.equals("ok")){
            Msg.msg("i", "Dodano fakturę.");
            RequestContext.getCurrentInstance().update("form:akordeon:dokumentyLista");
        } else {
            Msg.msg("e", "Wystąpił błąd. Nie dodano faktury. "+wynik);
        }
        
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
