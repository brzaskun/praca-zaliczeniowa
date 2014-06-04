/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import comparator.Podatnikcomparator;
import comparator.Zusstawkicomparator;
import dao.PodatnikDAO;
import dao.ZUSDAO;
import embeddable.Mce;
import entity.Podatnik;
import entity.Zusstawki;
import entity.ZusstawkiPK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.joda.time.DateTime;
import org.primefaces.context.RequestContext;
import params.Params;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class ZUSStawkiZbiorczeView  implements Serializable{
    //tak sie sklada ze to jest glowna lista z podatnikami :)
    private static List<Podatnik> listapodatnikow;
    @Inject
    private Zusstawki zusstawki;
    @Inject
    private Zusstawki wprowadzaniezusstawki;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private ZUSDAO zusDAO;
    private String biezacyRok;
    private boolean dodaj0edtuj1;
    private boolean pokazButtonUsun;

    public ZUSStawkiZbiorczeView() {
        listapodatnikow = new ArrayList<>();
    }
    
    @PostConstruct
    public void init() {
        listapodatnikow.addAll(podatnikDAO.findAll());
        Collections.sort(listapodatnikow, new Podatnikcomparator());
        ustawRokMc();
    }
    
    private void ustawRokMc() {
        biezacyRok = String.valueOf(new DateTime().getYear());
        String biezacyMc = Mce.getNumberToMiesiac().get((new DateTime().getMonthOfYear())+1 > 12 ? 12 : (new DateTime().getMonthOfYear()));
        wprowadzaniezusstawki.getZusstawkiPK().setRok(biezacyRok);
        wprowadzaniezusstawki.getZusstawkiPK().setMiesiac(biezacyMc);
        dodaj0edtuj1 = false;
        pokazButtonUsun = false;
    }
    
    public void dodajzusZbiorcze(Podatnik selected) {
        try {
            List<Zusstawki> tmp = new ArrayList<>();
            try {
                tmp.addAll(selected.getZusparametr());
            } catch (Exception e) {
            }
            if (czywprowadzonostawki()) {
                if (tmp.contains(wprowadzaniezusstawki)) {
                    Msg.msg("e", "Stawki za dany okres rozliczeniowy są już wprowadzone");
                } else {
                    tmp.add(serialclone.SerialClone.clone(wprowadzaniezusstawki));
                    selected.setZusparametr(tmp);
                    //musi byc edit bo dodajemy nowe stawki ale do istniejacego podatnika
                    podatnikDAO.edit(selected);
                    wprowadzaniezusstawki =  new Zusstawki();
                }
                ustawRokMc();
            } else {
              Msg.msg("e","Nie wprowadzono stawek. Nie można zachować miesiąca");
            }
        } catch (Exception e) {
        }
    }
      
    private boolean czywprowadzonostawki() {
        if (wprowadzaniezusstawki.getZus51ch()!=null) {
            return true;
        }
        if (wprowadzaniezusstawki.getZus51bch()!=null) {
            return true;
        }
        if (wprowadzaniezusstawki.getZus52()!=null) {
            return true;
        }
        if (wprowadzaniezusstawki.getZus52odl()!=null) {
            return true;
        }
        if (wprowadzaniezusstawki.getZus53()!=null) {
            return true;
        }
        if (wprowadzaniezusstawki.getPit4()!=null) {
            return true;
        }
        return false;
    }
    
    public void edytujzusZbiorcze(Podatnik selected) {
        try {
            List<Zusstawki> tmp = new ArrayList<>();
            try {
                tmp.addAll(selected.getZusparametr());
            } catch (Exception e) {
            }
            Iterator it = tmp.iterator();
            while (it.hasNext()) {
                Zusstawki p = (Zusstawki) it.next();
                if (p.getZusstawkiPK().equals(wprowadzaniezusstawki.getZusstawkiPK())) {
                    it.remove();
                    tmp.add(wprowadzaniezusstawki);
                    break;
                }
            }
            selected.setZusparametr(tmp);
            podatnikDAO.edit(selected);
            wprowadzaniezusstawki = new Zusstawki();
            ustawRokMc();
        } catch (Exception e) {
        }
    }

      public void usunzusZbiorcze(Podatnik selected) {
        try {
            selected.getZusparametr().remove(zusstawki);
            podatnikDAO.edit(selected);
            wprowadzaniezusstawki =  new Zusstawki();
            ustawRokMc();
            Msg.msg("Usunięto parametr ZUS do podatnika "+selected.getNazwapelna());
        } catch (Exception e) {
            Msg.msg("Nieusunięto parametr ZUS do podatnika "+selected.getNazwapelna());
        }
    }
      
    public void pobierzzusZbiorcze() {
        String rokzus = (String) Params.paramsContains("rokzus_input");
        String mczus = (String) Params.paramsContains("miesiaczus_input");
        List<Zusstawki> tmp = new ArrayList<>();
        tmp.addAll(zusDAO.findAll());
        ZusstawkiPK key = new ZusstawkiPK();
        key.setRok(rokzus);
        key.setMiesiac(mczus);
        Iterator it;
        it = tmp.iterator();
        while (it.hasNext()) {
            Zusstawki tmpX = (Zusstawki) it.next();
            if (tmpX.getZusstawkiPK().equals(key)) {
                wprowadzaniezusstawki = serialclone.SerialClone.clone(tmpX);
                break;
            }
        }
        Msg.msg("Pobrano parametr za wybrany okres rozliczeniowy: "+rokzus+"/"+mczus);
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
        } catch (Exception e) {
            Msg.msg("Wystąpił błąd podczas uzupełniania miesięcy");
        }
    }

    public void pobierzzusPoprzedniMiesiac(Podatnik podatnik) {
        String rokzus = (String) Params.paramsContains("rokzus_input");
        String mczus = (String) Params.paramsContains("miesiaczus_input");
        int mcpoprzedni = Mce.getMiesiacToNumber().get(mczus)-1;
        String mczusnowy = Mce.getNumberToMiesiac().get(mcpoprzedni);
        if (mcpoprzedni==0) {
            mcpoprzedni = 12;
            int rokpoprzedni = Integer.parseInt(rokzus) - 1;
            rokzus = String.valueOf(rokpoprzedni);
        }
        List<Zusstawki> tmp = new ArrayList<>();
        tmp.addAll(podatnik.getZusparametr());
        ZusstawkiPK key = new ZusstawkiPK();
        key.setRok(rokzus);
        key.setMiesiac(mczusnowy);
        Iterator it;
        it = tmp.iterator();
        while (it.hasNext()) {
            Zusstawki tmpX = (Zusstawki) it.next();
            if (tmpX.getZusstawkiPK().equals(key)) {
                wprowadzaniezusstawki = serialclone.SerialClone.clone(tmpX);
                wprowadzaniezusstawki.getZusstawkiPK().setMiesiac(mczus);
                break;
            }
        }
        Msg.msg("Pobrano parametr podatnika za poprzeni okres rozliczeniowy: "+rokzus+"/"+mczus);
    }
    
    public void wybranowiadomosc(List<Zusstawki> zusparametr) {
        wprowadzaniezusstawki = serialclone.SerialClone.clone(zusstawki);
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
        ZusstawkiPK zusstawkiPK = wprowadzaniezusstawki.getZusstawkiPK();
        for (Zusstawki p : zusparametr) {
            if (p.getZusstawkiPK().equals(zusstawkiPK)) {
                dodaj0edtuj1 = true;
                return;
            }
        }
        dodaj0edtuj1 = false;
        pokazButtonUsun = false;
    }

    public List<Podatnik> getListapodatnikow() {
        return listapodatnikow;
    }

    public void setListapodatnikow(List<Podatnik> listapodatnikow) {
        ZUSStawkiZbiorczeView.listapodatnikow = listapodatnikow;
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

    public Zusstawki getWprowadzaniezusstawki() {
        return wprowadzaniezusstawki;
    }

    public void setWprowadzaniezusstawki(Zusstawki wprowadzaniezusstawki) {
        this.wprowadzaniezusstawki = wprowadzaniezusstawki;
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
    
    
    
    
    
}
