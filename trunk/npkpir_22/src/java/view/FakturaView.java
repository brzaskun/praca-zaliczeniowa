/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.FakturaDAO;
import embeddable.Pozycjenafakturzebazadanych;
import entity.Faktura;
import entity.FakturaPK;
import java.io.Serializable;
import java.util.ArrayList;
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

           
    
    public void przygotujfakture(){
        DateTime dt = new DateTime();
        LocalDate firstDate = dt.toLocalDate();
        selected.setDatawystawienia(firstDate.toString());
        selected.setDatasprzedazy(firstDate.toString());
        fakturaPK.setNumerkolejny("wstaw numer");
        fakturaPK.setWystawcanazwa(wpisView.getPodatnikWpisu());
        selected.setFakturaPK(fakturaPK);
        LocalDate terminplatnosci = firstDate.plusDays(14);
        selected.setTerminzaplaty(terminplatnosci.toString());
        selected.setNrkontabankowego("numer wtojego konta bankowego");
        selected.setPodpis(wpisView.getPodatnikObiekt().getImie()+" "+wpisView.getPodatnikObiekt().getNazwisko());
        Pozycjenafakturzebazadanych poz = new Pozycjenafakturzebazadanych();
        pozycje.add(poz);
        selected.setAutor(wpisView.getWprowadzil().getLogin());
        selected.setMiejscewystawienia(wpisView.getPodatnikObiekt().getMiejscowosc());
        setPokazfakture(true);
        RequestContext.getCurrentInstance().update("form:panelfaktury");
    }
    
   @Inject private FakturaDAO fakturaDAO;
    
    public void dodaj(){
        if(fakturaDAO.dodaj(selected).equals("ok")){
            Msg.msg("i", "Dodano fakturę.");
        } else {
            Msg.msg("e", "Wystąpił błąd. Nie dodano faktury.");
        }
        
    }
    
    public void dodajwiersz(){
        Pozycjenafakturzebazadanych poz = new Pozycjenafakturzebazadanych();
        pozycje.add(poz);
    }

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
    
    
    
}
