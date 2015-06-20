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
        podsumowanie.setOpis("Podsumowanie");
        podsumowanie.setKolumna7(0.0);
        podsumowanie.setKolumna8(0.0);
        podsumowanie.setKolumna9(0.0);
        podsumowanie.setKolumna10(0.0);
        podsumowanie.setKolumna11(0.0);
        podsumowanie.setKolumna12(0.0);
        podsumowanie.setKolumna13(0.0);
        podsumowanie.setKolumna14(0.0);
        podsumowanie.setKolumna15(0.0);
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
                    case "przych. sprz":
                        try {
                            dk.setKolumna7(dk.getKolumna7()+tmpX.getNetto());
                        } catch (Exception e) { E.e(e); 
                            dk.setKolumna7(tmpX.getNetto());
                        }
                        podsumowanie.setKolumna7(podsumowanie.getKolumna7() + tmpX.getNetto());
                        break;
                    case "pozost. przych.":
                        try {
                            dk.setKolumna8(dk.getKolumna8()+tmpX.getNetto());
                        } catch (Exception e) { E.e(e); 
                            dk.setKolumna8(tmpX.getNetto());
                        }
                        podsumowanie.setKolumna8(podsumowanie.getKolumna8() + tmpX.getNetto());
                        break;
                    case "zakup tow. i mat.":
                        try {
                            dk.setKolumna10(dk.getKolumna10()+tmpX.getNetto());
                        } catch (Exception e) { E.e(e); 
                            dk.setKolumna10(tmpX.getNetto());
                        }
                        podsumowanie.setKolumna10(podsumowanie.getKolumna10() + tmpX.getNetto());
                        break;
                    case "koszty ub.zak.":
                        try {
                            dk.setKolumna11(dk.getKolumna11()+tmpX.getNetto());
                        } catch (Exception e) { E.e(e); 
                            dk.setKolumna11(tmpX.getNetto());
                        }
                        podsumowanie.setKolumna11(podsumowanie.getKolumna11() + tmpX.getNetto());
                        break;
                    case "wynagrodzenia":
                        try {
                            dk.setKolumna12(dk.getKolumna12()+tmpX.getNetto());
                        } catch (Exception e) { E.e(e); 
                            dk.setKolumna12(tmpX.getNetto());
                        }
                        podsumowanie.setKolumna12(podsumowanie.getKolumna12() + tmpX.getNetto());
                        break;
                    case "poz. koszty":
                        try {
                            dk.setKolumna13(dk.getKolumna13()+tmpX.getNetto());
                        } catch (Exception e) { E.e(e); 
                            dk.setKolumna13(tmpX.getNetto());
                        }
                        podsumowanie.setKolumna13(podsumowanie.getKolumna13() + tmpX.getNetto());
                        break;
                    case "inwestycje":
                        try {
                            dk.setKolumna15(dk.getKolumna15()+tmpX.getNetto());
                        } catch (Exception e) { E.e(e); 
                            dk.setKolumna15(tmpX.getNetto());
                        }
                        podsumowanie.setKolumna15(podsumowanie.getKolumna15() + tmpX.getNetto());
                        break;
                }
            }
            if (dk.getKolumna7() != null && dk.getKolumna8() != null) {
                dk.setKolumna9(dk.getKolumna7() + dk.getKolumna8());
                podsumowanie.setKolumna9(podsumowanie.getKolumna9() + dk.getKolumna9());
            } else if (dk.getKolumna7() != null) {
                dk.setKolumna9(dk.getKolumna7());
                podsumowanie.setKolumna9(podsumowanie.getKolumna9() + dk.getKolumna9());
            } else {
                dk.setKolumna9(dk.getKolumna8());
                try {
                    podsumowanie.setKolumna9(podsumowanie.getKolumna9() + dk.getKolumna9());
                } catch (Exception e) { 
                    E.e(e); 
                }
            }
            if (dk.getKolumna12() != null && dk.getKolumna13() != null) {
                dk.setKolumna14(dk.getKolumna12() + dk.getKolumna13());
                podsumowanie.setKolumna14(podsumowanie.getKolumna14() + dk.getKolumna14());
            } else if (dk.getKolumna12() != null) {
                dk.setKolumna14(dk.getKolumna12());
                podsumowanie.setKolumna14(podsumowanie.getKolumna14() + dk.getKolumna14());
            } else {
                dk.setKolumna14(dk.getKolumna13());
                try {
                    podsumowanie.setKolumna14(podsumowanie.getKolumna14() + dk.getKolumna14());
                } catch (Exception e) { 
                    E.e(e); 
                }
            }
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
        Sumypkpir sumyzachowac = new Sumypkpir();
        SumypkpirPK sumyklucz = new SumypkpirPK();
        sumyklucz.setRok(wpisView.getRokWpisu().toString());
        sumyklucz.setMc(wpisView.getMiesiacWpisu());
        sumyklucz.setPodatnik(wpisView.getPodatnikWpisu());
        sumyzachowac.setSumypkpirPK(sumyklucz);
        //sumyzachowac.setSumy(podsumowanie);
        sumypkpirDAO.edit(sumyzachowac);
        podsumowaniepopmc();
        System.out.println("d");
    }

    private void podsumowaniepopmc() {
        try {
        if (lista.get(0).getNrWpkpir() != 1) {
            System.out.println("podsumowanie");
            DokEwidPrzych ostatni = lista.get(lista.size() - 1);
            List<Sumypkpir> listasum = sumypkpirDAO.findS(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu().toString());
            String biezacymiesiac = wpisView.getMiesiacWpisu();
            DokEwidPrzych sumaposrednia = new DokEwidPrzych();
            sumaposrednia.setOpis("z przeniesienia");
            sumaposrednia.setKolumna7(0.0);
            sumaposrednia.setKolumna8(0.0);
            sumaposrednia.setKolumna9(0.0);
            sumaposrednia.setKolumna10(0.0);
            sumaposrednia.setKolumna11(0.0);
            sumaposrednia.setKolumna12(0.0);
            sumaposrednia.setKolumna13(0.0);
            sumaposrednia.setKolumna14(0.0);
            sumaposrednia.setKolumna15(0.0);
            sumaposrednia.setIdDok(new Long(1223));
            sumaposrednia.setKontr(new Klienci());
            DokEwidPrzych sumakoncowa = new DokEwidPrzych();
            sumakoncowa.setOpis("Razem");
            sumakoncowa.setKolumna7(0.0);
            sumakoncowa.setKolumna8(0.0);
            sumakoncowa.setKolumna9(0.0);
            sumakoncowa.setKolumna10(0.0);
            sumakoncowa.setKolumna11(0.0);
            sumakoncowa.setKolumna12(0.0);
            sumakoncowa.setKolumna13(0.0);
            sumakoncowa.setKolumna14(0.0);
            sumakoncowa.setKolumna15(0.0);
            sumakoncowa.setIdDok(new Long(1224));
            sumakoncowa.setKontr(new Klienci());
            for (Sumypkpir p : listasum) {
                if (!p.getSumypkpirPK().getMc().equals(biezacymiesiac)) {
                    sumaposrednia.setKolumna7(sumaposrednia.getKolumna7() + p.getSumy().getKolumna7());
                    sumaposrednia.setKolumna8(sumaposrednia.getKolumna8() + p.getSumy().getKolumna8());
                    sumaposrednia.setKolumna9(sumaposrednia.getKolumna9() + p.getSumy().getKolumna9());
                    sumaposrednia.setKolumna10(sumaposrednia.getKolumna10() + p.getSumy().getKolumna10());
                    sumaposrednia.setKolumna11(sumaposrednia.getKolumna11() + p.getSumy().getKolumna11());
                    sumaposrednia.setKolumna12(sumaposrednia.getKolumna12() + p.getSumy().getKolumna12());
                    sumaposrednia.setKolumna13(sumaposrednia.getKolumna13() + p.getSumy().getKolumna13());
                    sumaposrednia.setKolumna14(sumaposrednia.getKolumna14() + p.getSumy().getKolumna14());
                    sumaposrednia.setKolumna15(sumaposrednia.getKolumna15() + p.getSumy().getKolumna15());
                } else {
                    sumakoncowa.setKolumna7(sumaposrednia.getKolumna7() + p.getSumy().getKolumna7());
                    sumakoncowa.setKolumna8(sumaposrednia.getKolumna8() + p.getSumy().getKolumna8());
                    sumakoncowa.setKolumna9(sumaposrednia.getKolumna9() + p.getSumy().getKolumna9());
                    sumakoncowa.setKolumna10(sumaposrednia.getKolumna10() + p.getSumy().getKolumna10());
                    sumakoncowa.setKolumna11(sumaposrednia.getKolumna11() + p.getSumy().getKolumna11());
                    sumakoncowa.setKolumna12(sumaposrednia.getKolumna12() + p.getSumy().getKolumna12());
                    sumakoncowa.setKolumna13(sumaposrednia.getKolumna13() + p.getSumy().getKolumna13());
                    sumakoncowa.setKolumna14(sumaposrednia.getKolumna14() + p.getSumy().getKolumna14());
                    sumakoncowa.setKolumna15(sumaposrednia.getKolumna15() + p.getSumy().getKolumna15());
                    break;
                }
            }
            lista.add(sumaposrednia);
            System.out.println("dodanie sumy posredniej");
            lista.add(sumakoncowa);

        } else {
        }
        } catch (Exception e) {
            E.e(e);
        }
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
        wpisView.findWpis();
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
//        try {
//            PdfPkpir.drukujksiege(lista, wpisView);
//        } catch (Exception e) { E.e(e); 
//            
//        }
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
