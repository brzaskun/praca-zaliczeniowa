/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import comparator.Podatnikcomparator1;
import comparator.Zusstawkicomparator;
import dao.PodatnikDAO;
import dao.ZUSDAO;
import data.Data;
import embeddable.Mce;
import entity.Podatnik;
import entity.Zusstawki;
import entity.ZusstawkiPK;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.joda.time.DateTime;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class ZUSStawkiZbiorczeView  implements Serializable{
    private static final long serialVersionUID = 1L;
    //tak sie sklada ze to jest glowna lista z podatnikami :)
    private List<Podatnik> listapodatnikow;
    @Inject
    private Zusstawki zusstawki;
    private Zusstawki obrabianeparametryzus;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private ZUSDAO zusDAO;
    private String biezacyRok;
    private boolean dodaj0edtuj1;
    private boolean pokazButtonUsun;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;

    public ZUSStawkiZbiorczeView() {
        listapodatnikow = new ArrayList<>();
    }
    
    @PostConstruct
    public void init() {
        listapodatnikow.addAll(podatnikDAO.findPodatnikZUS());
        Collections.sort(listapodatnikow, new Podatnikcomparator1());
        ustawRokMc();
    }
    
    private void ustawRokMc() {
        biezacyRok = String.valueOf(new DateTime().getYear());
        String biezacyMc = Mce.getNumberToMiesiac().get((new DateTime().getMonthOfYear())+1 > 12 ? 12 : (new DateTime().getMonthOfYear()));
        if (obrabianeparametryzus == null) {
            obrabianeparametryzus = new Zusstawki();
        }
        obrabianeparametryzus.getZusstawkiPK().setRok(biezacyRok);
        obrabianeparametryzus.getZusstawkiPK().setMiesiac(biezacyMc);
        dodaj0edtuj1 = false;
        pokazButtonUsun = false;
    }
    
    public void dodajzusZbiorcze(Podatnik selected) {
        try {
            List<Zusstawki> tmp = new ArrayList<>();
            try {
                tmp.addAll(selected.getZusparametr());
            } catch (Exception e) { E.e(e); 
            }
            if (czywprowadzonostawki() && czywprowadzonostawkiZUS52()) {
                if (tmp.contains(obrabianeparametryzus)) {
                    Msg.msg("e", "Stawki za dany okres rozliczeniowy są już wprowadzone");
                } else {
                    tmp.add(serialclone.SerialClone.clone(obrabianeparametryzus));
                    selected.setZusparametr(tmp);
                    //musi byc edit bo dodajemy nowe stawki ale do istniejacego podatnika
                    podatnikDAO.edit(selected);
                    obrabianeparametryzus =  new Zusstawki();
                }
                ustawRokMc();
            } else {
              Msg.msg("e","Nie wprowadzono stawek. Nie można zachować miesiąca");
            }
        } catch (Exception e) { E.e(e); 
            Msg.msg("e","Wystąpił nieokreślony błąd podczas dodawania stawek. "+e.getMessage());
        }
    }
      
    private boolean czywprowadzonostawki() {
        if (obrabianeparametryzus.getZus51ch()!=null) {
            return true;
        }
        if (obrabianeparametryzus.getZus51bch()!=null) {
            return true;
        }
        if (obrabianeparametryzus.getZus52()!=null) {
            return true;
        }
        if (obrabianeparametryzus.getZus52odl()!=null) {
            return true;
        }
        if (obrabianeparametryzus.getZus53()!=null) {
            return true;
        }
        if (obrabianeparametryzus.getPit4()!=null) {
            return true;
        }
        return false;
    }
    
    private boolean czywprowadzonostawkiZUS52() {
        if (obrabianeparametryzus.getZus52()!=null && obrabianeparametryzus.getZus52odl()!=null) {
            Msg.msg("e","Nie wprowadzono dwóch stawek ZUS-52. Nie można zachować miesiąca");
            return true;
        } else if (obrabianeparametryzus.getZus52()==null && obrabianeparametryzus.getZus52odl()==null) {
            return true;
        }
        return false;
    }
    
    public void edytujzusZbiorcze(Podatnik selected) {
        try {
            List<Zusstawki> tmp = new ArrayList<>();
            try {
                tmp.addAll(selected.getZusparametr());
            } catch (Exception e) { E.e(e); 
            }
            Iterator it = tmp.iterator();
            while (it.hasNext()) {
                Zusstawki p = (Zusstawki) it.next();
                if (p.getZusstawkiPK().equals(obrabianeparametryzus.getZusstawkiPK())) {
                    it.remove();
                    break;
                }
            }
            tmp.add(obrabianeparametryzus);
            selected.setZusparametr(null);
            podatnikDAO.edit(selected);
            selected.setZusparametr(tmp);
            podatnikDAO.edit(selected);
            obrabianeparametryzus = new Zusstawki();
            ustawRokMc();
        } catch (Exception e) { E.e(e); 
        }
    }

      public void usunzusZbiorcze(Podatnik selected) {
        try {
            selected.getZusparametr().remove(zusstawki);
            podatnikDAO.edit(selected);
            obrabianeparametryzus =  new Zusstawki();
            ustawRokMc();
            pokazButtonUsun = false;
            dodaj0edtuj1 = false;
            Msg.msg("Usunięto parametr ZUS do podatnika "+selected.getNazwapelna());
        } catch (Exception e) { E.e(e); 
            Msg.msg("Nieusunięto parametr ZUS do podatnika "+selected.getNazwapelna());
        }
    }
      
    public void pobierzzusZbiorcze(Podatnik selected) {
        List<Zusstawki> tmp = new ArrayList<>();
        String data = selected.getDatamalyzus();
        String rok = null;
        String mc = null;
        boolean czymalyzus = false;
        if (data != null) {
            rok = data.split("-")[0];
            mc = data.split("-")[1];
            czymalyzus = sprawdzmalyzus(rok,mc, obrabianeparametryzus.getZusstawkiPK().getRok(), obrabianeparametryzus.getZusstawkiPK().getMiesiac());
        }
        if (czymalyzus == true) {
            tmp.addAll(zusDAO.findZUS(true));
        } else {
            tmp.addAll(zusDAO.findZUS(false));
        }
        Iterator it = tmp.iterator();
        while (it.hasNext()) {
            Zusstawki tmpX = (Zusstawki) it.next();
            if (tmpX.getZusstawkiPK().equals(obrabianeparametryzus.getZusstawkiPK())) {
                obrabianeparametryzus.setPit4(tmpX.getPit4());
                obrabianeparametryzus.setZus51bch(tmpX.getZus51bch());
                obrabianeparametryzus.setZus51ch(tmpX.getZus51ch());
                obrabianeparametryzus.setZus52(tmpX.getZus52());
                obrabianeparametryzus.setZus52odl(tmpX.getZus52odl());
                obrabianeparametryzus.setZus53(tmpX.getZus53());
                break;
            }
        }
        Msg.msg("Pobrano parametr za wybrany okres rozliczeniowy");
    }
    
    public void uzupełnijrok(Podatnik podatnik) {
        try {
            List<Zusstawki> lista = podatnik.getZusparametr();
            Collections.sort(lista, new Zusstawkicomparator());
            Zusstawki ostatni_zus = lista.get(lista.size() - 1);
            int nastepny_mc = Mce.getMiesiacToNumber().get(ostatni_zus.getZusstawkiPK().getMiesiac()) + 1;
            for (int i = nastepny_mc; i < 13; i++) {
                Zusstawki nowy_zus = serialclone.SerialClone.clone(ostatni_zus);
                String mc_string = Mce.getNumberToMiesiac().get(i);
                nowy_zus.getZusstawkiPK().setMiesiac(mc_string);
                lista.add(nowy_zus);
            }
            podatnikDAO.edit(podatnik);
            Msg.msg("Uzupełniono płatności ZUS podatnika za ostatni rok obrachunkowy");
        } catch (Exception e) { E.e(e); 
            Msg.msg("Wystąpił błąd podczas uzupełniania miesięcy");
        }
    }

    public void pobierzzusPoprzedniMiesiac(Podatnik podatnik) {
        int mcpoprzedni = Mce.getMiesiacToNumber().get(obrabianeparametryzus.getZusstawkiPK().getMiesiac())-1;
        String mczusnowy = Mce.getNumberToMiesiac().get(mcpoprzedni);
        int rokpoprzedni = 0;
        if (mcpoprzedni==0) {
            mcpoprzedni = 12;
            rokpoprzedni = Integer.parseInt(obrabianeparametryzus.getZusstawkiPK().getRok()) - 1;
        } else {
            rokpoprzedni = Integer.parseInt(obrabianeparametryzus.getZusstawkiPK().getRok());
        }
        List<Zusstawki> tmp = new ArrayList<>();
        tmp.addAll(podatnik.getZusparametr());
        ZusstawkiPK key = new ZusstawkiPK();
        key.setRok(String.valueOf(rokpoprzedni));
        key.setMiesiac(mczusnowy);
        Iterator it;
        it = tmp.iterator();
        while (it.hasNext()) {
            Zusstawki tmpX = (Zusstawki) it.next();
            if (tmpX.getZusstawkiPK().equals(key)) {
                obrabianeparametryzus.setPit4(tmpX.getPit4());
                obrabianeparametryzus.setZus51bch(tmpX.getZus51bch());
                obrabianeparametryzus.setZus51ch(tmpX.getZus51ch());
                obrabianeparametryzus.setZus52(tmpX.getZus52());
                obrabianeparametryzus.setZus52odl(tmpX.getZus52odl());
                obrabianeparametryzus.setZus53(tmpX.getZus53());
                break;
            }
        }
        Msg.msg("Pobrano parametr podatnika za poprzeni okres rozliczeniowy");
    }
    
    public void wybranowiadomosc(List<Zusstawki> zusparametr) {
        obrabianeparametryzus = serialclone.SerialClone.clone(zusstawki);
        zonglerkaPrzyciskamiDodajEdytuj(zusparametr);
        pokazButtonUsun = true;
        Msg.msg("Wybrano stawki ZUS.");
    }
    
    
     public int sortujZUSstawki(Object obP, Object obW)  {
        int rokO1 = Integer.parseInt(((ZusstawkiPK) obP).getRok());
        int rokO2 = Integer.parseInt(((ZusstawkiPK) obW).getRok());
        int mcO1 = Integer.parseInt(((ZusstawkiPK) obP).getMiesiac());
        int mcO2 = Integer.parseInt(((ZusstawkiPK) obW).getMiesiac());
        if (rokO1 < rokO2) {
            return -1;
        } else if (rokO1 > rokO2) {
            return 1;
        } else if (rokO1 == rokO2) {
            if (mcO1 == mcO2) {
                return 0;
            } else if (mcO1 < mcO2) {
                return -1;
            } else {
                return 1;
            }
        }
        return 0;
    }
     
    public void zonglerkaPrzyciskamiDodajEdytuj(List<Zusstawki> zusparametr){
        try {
            ZusstawkiPK zusstawkiPK = obrabianeparametryzus.getZusstawkiPK();
            for (Zusstawki p : zusparametr) {
                if (p.getZusstawkiPK().equals(zusstawkiPK)) {
                    obrabianeparametryzus = serialclone.SerialClone.clone(p);
                    dodaj0edtuj1 = true;
                    pokazButtonUsun = true;
                    return;
                }
            }
            dodaj0edtuj1 = false;
            pokazButtonUsun = false;
        } catch (Exception e) {
            E.e(e);
        }
    }

    public List<Podatnik> getListapodatnikow() {
        return listapodatnikow;
    }

    public void setListapodatnikow(List<Podatnik> listapodatnikow) {
        this.listapodatnikow = listapodatnikow;
    }

    
    public String getBiezacyRok() {
        return biezacyRok;
    }

    public void setBiezacyRok(String biezacyRok) {
        this.biezacyRok = biezacyRok;
    }

    public Zusstawki getZusstawki() {
        return zusstawki;
    }

    public void setZusstawki(Zusstawki zusstawki) {
        this.zusstawki = zusstawki;
    }

    public Zusstawki getObrabianeparametryzus() {
        return obrabianeparametryzus;
    }

    public void setObrabianeparametryzus(Zusstawki obrabianeparametryzus) {
        this.obrabianeparametryzus = obrabianeparametryzus;
    }

    public boolean isDodaj0edtuj1() {
        return dodaj0edtuj1;
    }

    public void setDodaj0edtuj1(boolean dodaj0edtuj1) {
        this.dodaj0edtuj1 = dodaj0edtuj1;
    }

    public boolean isPokazButtonUsun() {
        return pokazButtonUsun;
    }

    public void setPokazButtonUsun(boolean pokazButtonUsun) {
        this.pokazButtonUsun = pokazButtonUsun;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    private boolean sprawdzmalyzus(String rok, String mc, String rokwprowadzany, String mcwprowadzany) {
        boolean malyzus = false;
        String[] zwiekszonemcd = Mce.zwiekszmiesiac(rok, mc, 24);
        int data = Data.compare(zwiekszonemcd[0], zwiekszonemcd[1], rokwprowadzany, mcwprowadzany);
        if (data > -1) {
            malyzus = true;
        }
        return malyzus;
    }
    
//    public static void main(String[] args) {
//        sprawdzmalyzus("2013", "09");
//    }
   
    
    
}
