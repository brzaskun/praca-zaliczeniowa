/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansDok.CechaBean;
import beansDok.KsiegaBean;
import dao.DokDAO;
import dao.SMTPSettingsDAO;
import dao.SumypkpirDAO;
import embeddable.DokKsiega;
import embeddable.Mce;
import entity.Dok;
import entity.KwotaKolumna1;
import entity.Podatnik;
import entity.Sumypkpir;
import entityfk.Cechazapisu;
import error.E;
import interceptor.WydrukInterceptor;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import mail.MailOther;
import msg.Msg;import pdf.PdfPkpir;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class KsiegaView implements Serializable {
private static final long serialVersionUID = 1L;
    private List<DokKsiega> lista;
    private List<DokKsiega> listaFiltered;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private DokKsiega selDokument;
    @Inject
    private DokKsiega podsumowanie;
    @Inject
    private SumypkpirDAO sumypkpirDAO;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private SMTPSettingsDAO sMTPSettingsDAO;
    private Map<String, List<DokKsiega>> ksiegimiesieczne;
    private List cechydokzlisty;
    private String wybranacechadok;

    public KsiegaView() {
        lista = Collections.synchronizedList(new ArrayList<>());
        ksiegimiesieczne = new ConcurrentHashMap<>();
    }

    @PostConstruct
    public void init() {
        generujksiege(wpisView.getMiesiacWpisu());
        zachowajsumy();
        if (wybranacechadok == null) {
            podsumowaniepopmc();
        }
       
    }
    
    public void generujksiegirok() {
        ksiegimiesieczne = new ConcurrentHashMap<>();
        int mcint = Integer.parseInt(wpisView.getMiesiacWpisu());
        for (int i = 1; i <= mcint; i++) {
            lista = Collections.synchronizedList(new ArrayList<>());
            String mc = Mce.getNumberToMiesiac().get(i);
            generujksiege(mc);
            podsumowaniepopmc();
            if (lista.size() > 3) {
                ksiegimiesieczne.put(mc, lista);
            }
        }
    }
    
    public void drukujPKPIRrok() {
        try {
            generujksiegirok();
            if (ksiegimiesieczne.isEmpty() == false) {
                PdfPkpir.drukujksiegeRok(ksiegimiesieczne, wpisView);
            }
        } catch (Exception e) { 
            E.e(e); 
        }
    }
    
    private void generujksiege(String mc) {
        lista = Collections.synchronizedList(new ArrayList<>());
        Integer rok = wpisView.getRokWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        Podatnik pod = wpisView.getPodatnikObiekt();
        int numerkolejny = KsiegaBean.pobierznumerrecznie(pod,rok,mc);
        //dlatego jest caly rok bo nadajemy numery za kazdym razem
        List<Dok> dokumentyzaMc = KsiegaBean.pobierzdokumenty(dokDAO, pod, rok, mc, numerkolejny);
        if (lista != null) {
            cechydokzlisty = CechaBean.znajdzcechy(dokumentyzaMc);
        }
        podsumowanie = KsiegaBean.ustawpodsumowanie();
        for (Dok tmp : dokumentyzaMc) {
            if (tmp.getUsunpozornie() == false) {
                DokKsiega dk = new DokKsiega(tmp);
                boolean sumowac = false;
                if (wybranacechadok == null) {
                    lista.add(dk);
                    sumowac = true;
                } else if (!tmp.getCechadokumentuLista().isEmpty() && !wybranacechadok.equals("bezcechy")) {
                    for (Cechazapisu cz : tmp.getCechadokumentuLista()) {
                        if (cz.getNazwacechy().equals(wybranacechadok)) {
                            lista.add(dk);
                            sumowac = true;
                            break;
                        }
                    }
                } else if (wybranacechadok.equals("bezcechy") && (tmp.getCechadokumentuLista() == null || tmp.getCechadokumentuLista().isEmpty())) {
                    sumowac = true;
                    lista.add(dk);
                }
                if (sumowac) {
                    List<KwotaKolumna1> listawierszy = tmp.getListakwot1();
                    for (KwotaKolumna1 tmpX : listawierszy) {
                        KsiegaBean.rozliczkolumny(dk, tmpX, podsumowanie);
                    }
                    KsiegaBean.rozliczkolumnysumaryczne(dk, podsumowanie);
                }
            }
        }
        lista.add(podsumowanie);
    }
    
        
    private void zachowajsumy() {
        Sumypkpir sumyzachowac = new Sumypkpir();
        try {
            sumyzachowac = KsiegaBean.zachowajsumypkpir(wpisView, podsumowanie);
            sumypkpirDAO.usun(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
            sumypkpirDAO.dodaj(sumyzachowac);
        } catch (Exception e) {
            sumypkpirDAO.dodaj(sumyzachowac);
        }
    }

    private void podsumowaniepopmc() {
        if (lista.get(0).getNrWpkpir() != 1) {
            System.out.println("podsumowanie");
            List<Sumypkpir> listasum = sumypkpirDAO.findS(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu().toString());
            String biezacymiesiac = wpisView.getMiesiacWpisu();
            DokKsiega sumaposrednia = KsiegaBean.ustawsumaposrednia();
            DokKsiega sumakoncowa = KsiegaBean.ustawsumakoncowa();
            KsiegaBean.rozliczpodsumowania(listasum, sumaposrednia, sumakoncowa, biezacymiesiac);
            lista.add(sumaposrednia);
            lista.add(sumakoncowa);
        }
    }
    
     public void aktualizujTabeleTabela(AjaxBehaviorEvent e) throws IOException {
        lista.clear();
        aktualizuj();
        init();
        Msg.msg("i","Udana zamiana klienta. Aktualny klient to: " +wpisView.getPodatnikWpisu()+" okres rozliczeniowy: "+wpisView.getRokWpisu()+"/"+wpisView.getMiesiacWpisu());
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
    
    @Interceptors(WydrukInterceptor.class)
    public void drukujPKPIR() {
        try {
            PdfPkpir.drukujksiege(lista, wpisView, wpisView.getMiesiacWpisu());
        } catch (Exception e) { 
            E.e(e); 
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    public List<DokKsiega> getLista() {
        return lista;
    }

    public void setLista(List<DokKsiega> lista) {
        this.lista = lista;
    }

    public DokKsiega getSelDokument() {
        return selDokument;
    }

    public void setSelDokument(DokKsiega selDokument) {
        this.selDokument = selDokument;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    public List getCechydokzlisty() {
        return cechydokzlisty;
    }

    public void setCechydokzlisty(List cechydokzlisty) {
        this.cechydokzlisty = cechydokzlisty;
    }

    public String getWybranacechadok() {
        return wybranacechadok;
    }

    //</editor-fold>
    public void setWybranacechadok(String wybranacechadok) {
        this.wybranacechadok = wybranacechadok;
    }

    public List<DokKsiega> getListaFiltered() {
        return listaFiltered;
    }

    public void setListaFiltered(List<DokKsiega> listaFiltered) {
        this.listaFiltered = listaFiltered;
    }
}
