/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Dokcomparator;
import dao.DokDAO;
import dao.SumypkpirDAO;
import dao.WpisDAO;
import embeddable.DokEwidPrzych;
import embeddable.Mce;
import entity.Dok;
import entity.Klienci;
import entity.KwotaKolumna1;
import entity.Podatnik;
import entity.Sumypkpir;
import entity.SumypkpirPK;
import entity.Wpis;
import error.E;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
import pdf.PdfPkpir;

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

    public EwidencjaPrzychodowView() {
        lista = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        Integer rok = wpisView.getRokWpisu();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        Podatnik pod = wpisView.getPodatnikObiekt();
        int numerkolejny = 1;
        if (pod.getNumerpkpir() != null) {
            try {
                //zmienia numer gdy srodek roku
                String wartosc = ParametrView.zwrocParametr(pod.getNumerpkpir(),rok,Mce.getMapamcyCalendar().get(mc));
                numerkolejny = Integer.parseInt(wartosc);
            } catch (Exception e) { 
                E.e(e);
                System.out.println("Brak numeru pkpir wprowadzonego w trakcie roku");
            }
        }
        List<Dok> pobranedokumenty = new ArrayList<>();
        try {
            pobranedokumenty.addAll(dokDAO.zwrocBiezacegoKlientaRokPrzychody(podatnik, rok.toString()));
            //sortowanie dokumentów
            Collections.sort(pobranedokumenty, new Dokcomparator());
        } catch (Exception e) { E.e(e); 
        }
        List<Dok> obiektDOKmrjsfSel = new ArrayList<>();
        for (Dok tmpx : pobranedokumenty) {
            tmpx.setNrWpkpir(numerkolejny++);
            if (tmpx.getPkpirM().equals(mc)) {
                obiektDOKmrjsfSel.add(tmpx);
            }
        }
        podsumowanie.setKontr(null);
        podsumowanie.setKolumna5(0.0);
        podsumowanie.setKolumna6(0.0);
        podsumowanie.setKolumna7(0.0);
        podsumowanie.setKolumna8(0.0);
        podsumowanie.setKolumna9(0.0);
        podsumowanie.setKolumna10(0.0);
        podsumowanie.setKolumna11(0.0);
        for (Dok tmp : obiektDOKmrjsfSel) {
            DokEwidPrzych dk = new DokEwidPrzych();
            dk.setIdDok(tmp.getIdDok());
            dk.setTypdokumentu(tmp.getTypdokumentu());
            dk.setNrWpkpir(tmp.getNrWpkpir());
            dk.setNrWlDk(tmp.getNrWlDk());
            dk.setKontr(tmp.getKontr());
            dk.setPodatnik(tmp.getPodatnik());
            dk.setDataWyst(tmp.getDataWyst());
            dk.setOpis(tmp.getOpis());
            List<KwotaKolumna1> listawierszy = tmp.getListakwot1();
            for (KwotaKolumna1 tmpX : listawierszy) {
                switch (tmpX.getNazwakolumny()) {
                    case "20%":
                        try {
                            dk.setKolumna5(dk.getKolumna5()+tmpX.getNetto());
                        } catch (Exception e) { E.e(e); 
                            dk.setKolumna5(tmpX.getNetto());
                        }
                        podsumowanie.setKolumna5(podsumowanie.getKolumna5() + tmpX.getNetto());
                        break;
                    case "17%":
                        try {
                            dk.setKolumna6(dk.getKolumna6()+tmpX.getNetto());
                        } catch (Exception e) { E.e(e); 
                            dk.setKolumna6(tmpX.getNetto());
                        }
                        podsumowanie.setKolumna6(podsumowanie.getKolumna6() + tmpX.getNetto());
                        break;
                    case "8.5%":
                        try {
                            dk.setKolumna7(dk.getKolumna7()+tmpX.getNetto());
                        } catch (Exception e) { E.e(e); 
                            dk.setKolumna7(tmpX.getNetto());
                        }
                        podsumowanie.setKolumna7(podsumowanie.getKolumna7() + tmpX.getNetto());
                        break;
                    case "5.5%":
                        try {
                            dk.setKolumna8(dk.getKolumna8()+tmpX.getNetto());
                        } catch (Exception e) { E.e(e); 
                            dk.setKolumna8(tmpX.getNetto());
                        }
                        podsumowanie.setKolumna8(podsumowanie.getKolumna8() + tmpX.getNetto());
                        break;
                    case "3%":
                        try {
                            dk.setKolumna9(dk.getKolumna9()+tmpX.getNetto());
                        } catch (Exception e) { E.e(e); 
                            dk.setKolumna9(tmpX.getNetto());
                        }
                        podsumowanie.setKolumna9(podsumowanie.getKolumna9() + tmpX.getNetto());
                        break;
                    case "10%":
                        try {
                            dk.setKolumna10(dk.getKolumna10()+tmpX.getNetto());
                        } catch (Exception e) { E.e(e); 
                            dk.setKolumna10(tmpX.getNetto());
                        }
                        podsumowanie.setKolumna10(podsumowanie.getKolumna10() + tmpX.getNetto());
                        break;
                }
            }
                double suma = dk.getKolumna5()+dk.getKolumna6()+dk.getKolumna7()+dk.getKolumna8()+dk.getKolumna9();
                dk.setKolumna11(suma);
                podsumowanie.setKolumna11(podsumowanie.getKolumna11() + suma);
            dk.setUwagi(tmp.getUwagi());
            dk.setPkpirM(tmp.getPkpirM());
            dk.setPkpirR(tmp.getPkpirR());
            dk.setVatM(tmp.getVatM());
            dk.setVatR(tmp.getVatR());
            dk.setStatus(tmp.getStatus());
            dk.setEwidencjaVAT1(tmp.getEwidencjaVAT1());
            dk.setDokumentProsty(tmp.isDokumentProsty());
            lista.add(dk);
        }
        podsumowanie.setIdDok(new Long(1222));
        podsumowanie.setKontr(new Klienci());
        lista.add(podsumowanie);
        System.out.println("d");
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
            MailOther.pkpir(wpisView);
        } catch (Exception e) { E.e(e); 
            
        }
    }
    
    public void drukujPKPIR() {
        try {
            PdfEwidencjaPrzychodow.drukujksiege(lista, wpisView);
        } catch (Exception e) { E.e(e); 
            
        }
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
