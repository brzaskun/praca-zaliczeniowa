/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansDok.EwidencjaPrzychBean;
import dao.DokDAO;
import dao.SMTPSettingsDAO;
import dao.SumypkpirDAO;
import dao.WpisDAO;
import embeddable.DokEwidPrzych;
import embeddable.Mce;
import entity.Dok;
import entity.KwotaKolumna1;
import entity.Podatnik;
import entity.Wpis;
import error.E;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import mail.MailOther;
import msg.Msg;
import pdf.PdfEwidencjaPrzychodow;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class EwidencjaPrzychodowView implements Serializable {

    private ArrayList<DokEwidPrzych> lista;
    private ArrayList<DokEwidPrzych> listaFiltered;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private DokEwidPrzych selDokument;
    @Inject
    private DokEwidPrzych podsumowanie;
    @Inject
    private SumypkpirDAO sumypkpirDAO;
    @Inject
    private DokDAO dokDAO;
    @Inject private WpisDAO wpisDAO;
    @Inject
    private SMTPSettingsDAO sMTPSettingsDAO;
    private Map<String, List<DokEwidPrzych>> ksiegimiesieczne;

    public EwidencjaPrzychodowView() {
        lista = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        generujksiege(wpisView.getMiesiacWpisu());
       // System.out.println("d");
    }

       
     public void aktualizujTabeleTabela(AjaxBehaviorEvent e) throws IOException {
        lista.clear();
        aktualizuj();
        init();
        Msg.msg("i","Udana zamiana klienta. Aktualny klient to: " +wpisView.getPodatnikWpisu()+" okres rozliczeniowy: "+wpisView.getRokWpisu()+"/"+wpisView.getMiesiacWpisu(),"form:messages");
    }
     
       private void aktualizuj(){
        HttpSession sessionX = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String user = (String) sessionX.getAttribute("user");
        Wpis wpistmp = wpisDAO.find(user);
        wpistmp.setMiesiacWpisu(wpisView.getMiesiacWpisu());
        wpistmp.setRokWpisu(wpisView.getRokWpisu());
        wpistmp.setPodatnikWpisu(wpisView.getPodatnikWpisu());
        wpisDAO.edit(wpistmp);
        wpisView.naniesDaneDoWpis();
    }
    
     public void aktualizujGuest(String strona) throws IOException {
        lista = new ArrayList<>();
        aktualizujGuest();
        aktualizuj();
        init();
        //FacesContext.getCurrentInstance().getExternalContext().redirect(strona);
    }
       
      private void aktualizujGuest(){
        HttpSession sessionX = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String user = (String) sessionX.getAttribute("user");
        Wpis wpistmp = wpisDAO.find(user);
        wpistmp.setRokWpisuSt(String.valueOf(wpisView.getRokWpisu()));
        wpistmp.setMiesiacWpisu(wpisView.getMiesiacWpisu());
        wpistmp.setRokWpisu(wpisView.getRokWpisu());
        wpisDAO.edit(wpistmp);
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
        ksiegimiesieczne = new HashMap<>();
        int mcint = Integer.parseInt(wpisView.getMiesiacWpisu());
        for (int i = 1; i <= mcint; i++) {
            lista = new ArrayList<>();
            String mc = Mce.getNumberToMiesiac().get(i);
            generujksiege(mc);
            if (lista.size() > 0) {
                ksiegimiesieczne.put(mc, lista);
            }
        }
        System.out.println("wygenerowano");
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
    public ArrayList<DokEwidPrzych> getLista() {
        return lista;
    }

    public void setLista(ArrayList<DokEwidPrzych> lista) {
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

    public ArrayList<DokEwidPrzych> getListaFiltered() {
        return listaFiltered;
    }

    public void setListaFiltered(ArrayList<DokEwidPrzych> listaFiltered) {
        this.listaFiltered = listaFiltered;
    }
}
