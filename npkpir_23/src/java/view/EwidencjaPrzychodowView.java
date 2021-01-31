/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansDok.EwidencjaPrzychBean;
import dao.DokDAO;
import dao.SMTPSettingsDAO;
import dao.SumypkpirDAO;
import embeddable.DokEwidPrzych;
import embeddable.Mce;
import entity.Dok;
import entity.KwotaKolumna1;
import entity.Podatnik;
import error.E;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.inject.Named;

import javax.faces.view.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import mail.MailOther;
import msg.Msg;import pdf.PdfEwidencjaPrzychodow;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class EwidencjaPrzychodowView implements Serializable {

    private List<DokEwidPrzych> lista;
    private List<DokEwidPrzych> listaFiltered;
    @Inject
    private WpisView wpisView;
    private DokEwidPrzych selDokument;
    @Inject
    private DokEwidPrzych podsumowanie;
    @Inject
    private SumypkpirDAO sumypkpirDAO;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private SMTPSettingsDAO sMTPSettingsDAO;
    private Map<String, List<DokEwidPrzych>> ksiegimiesieczne;

    public EwidencjaPrzychodowView() {
        lista = Collections.synchronizedList(new ArrayList<>());
    }

    @PostConstruct
    private void init() { //E.m(this);
        generujksiege(wpisView.getMiesiacWpisu());
       // error.E.s("d");
    }

       
     public void aktualizujTabeleTabela(AjaxBehaviorEvent e) throws IOException {
        lista.clear();
        aktualizuj();
         if (wpisView.getRodzajopodatkowania().equals("ryczałt")) {
            init();
            Msg.msg("i","Udana zamiana klienta. Aktualny klient to: " +wpisView.getPrintNazwa()+" okres rozliczeniowy: "+wpisView.getRokWpisu()+"/"+wpisView.getMiesiacWpisu());
        } else {
            Msg.msg("e","Zmiana opodatkowania. Nie można wygenerować ewidencji przychodów");
        }
    }
     
       private void aktualizuj(){
        wpisView.naniesDaneDoWpis();
    }
    
     public void aktualizujGuest(String strona) throws IOException {
        lista = Collections.synchronizedList(new ArrayList<>());
        aktualizujGuest();
        aktualizuj();
        init();
        //FacesContext.getCurrentInstance().getExternalContext().redirect(strona);
    }
       
      private void aktualizujGuest(){
        wpisView.naniesDaneDoWpis();
    }

    public void mailpkpir() {
        try {
            MailOther.pkpir(wpisView, sMTPSettingsDAO.findSprawaByDef());
        } catch (Exception e) { E.e(e); 
            
        }
    }
    
    public void drukujPKPIR() {
        try {
            PdfEwidencjaPrzychodow.drukujksiege(lista, wpisView, wpisView.getMiesiacWpisu());
        } catch (Exception e) { E.e(e); 
            
        }
    }
    
    public void drukujPKPIRrok() {
        try {
            generujksiegirok();
            if (ksiegimiesieczne.isEmpty() == false) {
                PdfEwidencjaPrzychodow.drukujksiegeRok(ksiegimiesieczne, wpisView);
            }
        } catch (Exception e) { 
            E.e(e); 
        }
    }
    
    public void generujksiegirok() {
        ksiegimiesieczne = new ConcurrentHashMap<>();
        int mcint = Integer.parseInt(wpisView.getMiesiacWpisu());
        int mcgranica = Mce.getMiesiacToNumber().get(wpisView.getOdjakiegomcdok());
        for (int i = 1; i <= mcint; i++) {
            if (i>=mcgranica) {
                lista = Collections.synchronizedList(new ArrayList<>());
                String mc = Mce.getNumberToMiesiac().get(i);
                generujksiege(mc);
                if (lista.size() > 0) {
                    ksiegimiesieczne.put(mc, lista);
                }
            }
        }
    }
    
    private void generujksiege(String mc) {
        Integer rok = wpisView.getRokWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        Podatnik pod = wpisView.getPodatnikObiekt();
        int numerkolejny = EwidencjaPrzychBean.pobierznumerrecznie(pod,rok,mc);
        List<Dok> dokumentyzaMc = EwidencjaPrzychBean.pobierzdokumentyR(dokDAO, pod, rok, mc, numerkolejny);
        podsumowanie = EwidencjaPrzychBean.ustawpodsumowanieR();
        for (Dok tmp : dokumentyzaMc) {
            DokEwidPrzych dk = new DokEwidPrzych(tmp);
            List<KwotaKolumna1> listawierszy = tmp.getListakwot1();
                for (KwotaKolumna1 tmpX : listawierszy) {
                    EwidencjaPrzychBean.rozliczkolumnyR(dk, tmpX, podsumowanie);
                }
            EwidencjaPrzychBean.rozliczkolumnysumaryczneR(dk, podsumowanie);
            lista.add(dk);
        }
        lista.add(podsumowanie);
    }
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    public List<DokEwidPrzych> getLista() {
        return lista;
    }

    public void setLista(List<DokEwidPrzych> lista) {
        this.lista = lista;
    }

    public DokEwidPrzych getSelDokument() {
        return selDokument;
    }

    public void setSelDokument(DokEwidPrzych selDokument) {
        this.selDokument = selDokument;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    //</editor-fold>

    public List<DokEwidPrzych> getListaFiltered() {
        return listaFiltered;
    }

    public void setListaFiltered(List<DokEwidPrzych> listaFiltered) {
        this.listaFiltered = listaFiltered;
    }
}
