/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import comparator.Podatnikcomparator1;
import comparator.Zusstawkinewcomparator;
import dao.PodatnikDAO;
import dao.ZUSDAO;
import dao.ZusstawkinewDAO;
import data.Data;
import embeddable.Mce;
import entity.Podatnik;
import entity.Zusstawki;
import entity.ZusstawkiPK;
import entity.Zusstawkinew;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.joda.time.DateTime;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class ZusStawkiZbiorczeView  implements Serializable{
    private static final long serialVersionUID = 1L;
    //tak sie sklada ze to jest glowna lista z podatnikami :)
    private List<Podatnik> listapodatnikow;
    @Inject
    private Zusstawkinew zusstawki;
    @Inject
    private Zusstawkinew selected;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private ZUSDAO zusDAO;
    @Inject
    private ZusstawkinewDAO zusstawkinewDAO;
    private String biezacyRok;
    @Inject
    private WpisView wpisView;
    private boolean wszystkielata;

    public ZusStawkiZbiorczeView() {
        listapodatnikow = Collections.synchronizedList(new ArrayList<>());
    }
    
    @PostConstruct
    public void init() { //E.m(this);
        listapodatnikow.addAll(podatnikDAO.findPodatnikZUS());
        Collections.sort(listapodatnikow, new Podatnikcomparator1());
        ustawRokMc();
    }
    
    private void ustawRokMc() {
        biezacyRok = String.valueOf(new DateTime().getYear());
        String biezacyMc = Mce.getNumberToMiesiac().get((new DateTime().getMonthOfYear()));
        if (selected == null) {
            selected = new Zusstawkinew();
        }
        selected.setRok(biezacyRok);
        selected.setMiesiac(biezacyMc);
    }
    
    public void dodajzusZbiorcze(Podatnik podatnik) {
        try {
            if (czywprowadzonostawki() && czywprowadzonostawkiZUS52()) {
                if (podatnik.getZusstawkinowe().contains(selected)) {
                    Msg.msg("e", "Stawki za dany okres rozliczeniowy są już wprowadzone");
                } else {
                    selected.setPodatnik(podatnik);
                    zusstawkinewDAO.create(selected);
                    podatnik.getZusstawkinowe().add(selected);
                    podatnikDAO.edit(podatnik);
                    //musi byc edit bo dodajemy nowe stawki ale do istniejacego podatnika
                    selected =  new Zusstawkinew();
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
        if (selected.getZus51ch()!=0.0) {
            return true;
        }
        if (selected.getZus51bch()!=0.0) {
            return true;
        }
        if (selected.getZus52()!=0.0) {
            return true;
        }
        if (selected.getZus52odl()!=0.0) {
            return true;
        }
        if (selected.getZus53()!=0.0) {
            return true;
        }
        if (selected.getPit4()!=0.0) {
            return true;
        }
        if (selected.getPit8()!=0.0) {
            return true;
        }
        return false;
    }
    
    private boolean czywprowadzonostawkiZUS52() {
        if (selected.getZus52()!=0.0 && selected.getZus52odl()!=0.0) {
            return true;
        } else if (selected.getZus52()==0.0 && selected.getZus52odl()==0.0) {
            return true;
        } else if (selected.getZus52()==0.0 && selected.getZus52odl()!=0.0) {
            Msg.msg("e","Nie wprowadzono dwóch stawek ZUS-52. Nie można zachować miesiąca");
            return false;
        } else if (selected.getZus52()!=0.0 && selected.getZus52odl()==0.0) {
            Msg.msg("e","Nie wprowadzono dwóch stawek ZUS-52. Nie można zachować miesiąca");
            return false;
        }
        return false;
    }
    
    public void edytujzusZbiorcze(Podatnik podatnik) {
        try {
            podatnikDAO.edit(selected);
            selected = new Zusstawkinew();
            ustawRokMc();
        } catch (Exception e) { E.e(e); 
        }
    }

    public void reset(Podatnik podatnik) {
        try {
            selected =  new Zusstawkinew();
            ustawRokMc();
            Msg.msg("Zresetowano pola, można wprowadzać nowe wartości do podatnika "+podatnik.getPrintnazwa());
        } catch (Exception e) { E.e(e); 
            Msg.msg("Niezrfesetowano pól. Nie wybrano wiersza."+podatnik.getPrintnazwa());
        }
    }
    
      public void usunzusZbiorcze(Podatnik podatnik) {
        try {
            podatnik.getZusstawkinowe().remove(selected);
            podatnikDAO.edit(selected);
            selected =  new Zusstawkinew();
            ustawRokMc();
            Msg.msg("Usunięto parametr ZUS do podatnika "+podatnik.getPrintnazwa());
        } catch (Exception e) { E.e(e); 
            Msg.msg("Nieusunięto parametr ZUS do podatnika. Nie wybrano wiersza."+podatnik.getPrintnazwa());
        }
    }
      
    public void pobierzzusZbiorcze(Podatnik podatnik) {
        List<Zusstawki> tmp = Collections.synchronizedList(new ArrayList<>());
        String data = podatnik.getDatamalyzus();
        boolean czymalyzus = false;
        if (data!=null&&!data.equals("")) {
            String rok = null;
            String mc = null;
            if (data != null) {
                rok = data.split("-")[0];
                mc = data.split("-")[1];
                czymalyzus = sprawdzmalyzus(rok,mc, selected.getRok(), selected.getMiesiac());
            }
        } else {
          Msg.msg("w","W danych podatnika brak informacji o małym ZUS");
        }
        if (czymalyzus == true) {
            tmp.addAll(zusDAO.findZUS(1));
        } else {
            tmp.addAll(zusDAO.findZUS(0));
        }
        Iterator it = tmp.iterator();
        while (it.hasNext()) {
            Zusstawki tmpX = (Zusstawki) it.next();
            int rodzajzus = 0;
            if (czymalyzus) {
                rodzajzus = 2;
            }
            if (tmpX.getRok().equals(selected.getRok())&&tmpX.getMiesiac().equals(selected.getMiesiac())&&tmpX.getRodzajzus()==rodzajzus) {
                selected.setPodatnik(podatnik);
                selected.setPit4(tmpX.getPit4());
                selected.setZus51bch(tmpX.getZus51bch());
                selected.setZus51ch(tmpX.getZus51ch());
                selected.setZus52(tmpX.getZus52());
                selected.setZus52odl(tmpX.getZus52odl());
                selected.setZus53(tmpX.getZus53());
                selected.setRodzajzus(tmpX.getRodzajzus());
                break;
            } 
        }
        Msg.msg("Pobrano parametr za wybrany okres rozliczeniowy");
    }
    
    public void uzupełnijrok(Podatnik podatnik) {
        try {
            List<Zusstawkinew> lista = podatnik.getZusstawkinowe();
            Collections.sort(lista, new Zusstawkinewcomparator());
            Zusstawkinew ostatni_zus = lista.get(lista.size() - 1);
            int nastepny_mc = Mce.getMiesiacToNumber().get(ostatni_zus.getMiesiac()) + 1;
            for (int i = nastepny_mc; i < 13; i++) {
                Zusstawkinew nowy_zus = serialclone.SerialClone.clone(ostatni_zus);
                String mc_string = Mce.getNumberToMiesiac().get(i);
                nowy_zus.setMiesiac(mc_string);
                lista.add(nowy_zus);
            }
            podatnikDAO.edit(podatnik);
            Msg.msg("Uzupełniono płatności ZUS podatnika za ostatni rok obrachunkowy");
        } catch (Exception e) { E.e(e); 
            Msg.msg("Wystąpił błąd podczas uzupełniania miesięcy");
        }
    }

    public void pobierzzusPoprzedniMiesiac(Podatnik podatnik) {
        String[] poprzedniokres = Data.poprzedniOkres(selected.getMiesiac(), selected.getRok());
        String mc = poprzedniokres[0];
        String rok = poprzedniokres[1];
        Iterator it;
        it = podatnik.getZusstawkinowe().iterator();
        while (it.hasNext()) {
            Zusstawkinew tmpX = (Zusstawkinew) it.next();
             if (tmpX.getRok().equals(rok)&&tmpX.getMiesiac().equals(mc)) {
                selected.setPodatnik(podatnik);
                selected.setPit4(tmpX.getPit4());
                selected.setZus51bch(tmpX.getZus51bch());
                selected.setZus51ch(tmpX.getZus51ch());
                selected.setZus52(tmpX.getZus52());
                selected.setZus52odl(tmpX.getZus52odl());
                selected.setZus53(tmpX.getZus53());
                break;
            }
        }
        Msg.msg("Pobrano parametr podatnika za poprzeni okres rozliczeniowy");
    }
    
    public void wybranowiadomosc(List<Zusstawkinew> zusparametr) {
        selected = serialclone.SerialClone.clone(zusstawki);
        //zonglerkaPrzyciskamiDodajEdytuj(zusparametr);
        Msg.msg("Wybrano stawki ZUS  za okres"+zusstawki.getRok()+"/"+zusstawki.getMiesiac());
    }
    
    
    
     public int sortujZUSstawki(Object obP, Object obW)  {
        try {
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
        } catch (Exception e) {
            
        }
        return 0;
    }
     
     public int sortujZUSstawkinew(Object obP, Object obW)  {
        try {
            int rokO1 = Integer.parseInt(((Zusstawkinew) obP).getRok());
            int rokO2 = Integer.parseInt(((Zusstawkinew) obW).getRok());
            int mcO1 = Integer.parseInt(((Zusstawkinew) obP).getMiesiac());
            int mcO2 = Integer.parseInt(((Zusstawkinew) obW).getMiesiac());
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
        } catch (Exception e) {
            
        }
        return 0;
    }
     
//    public void zonglerkaPrzyciskamiDodajEdytuj(List<Zusstawkinew> zusparametr){
//        try {
//            ZusstawkinowePK zusstawkiPK = selected.getZusstawkinowePK();
//            for (Zusstawkinowe p : zusparametr) {
//                if (p.equals(zusstawkiPK)) {
//                    selected = serialclone.SerialClone.clone(p);
//                    dodaj0edtuj1 = true;
//                    pokazButtonUsun = true;
//                    return;
//                }
//            }
//            dodaj0edtuj1 = false;
//            pokazButtonUsun = false;
//        } catch (Exception e) {
//            E.e(e);
//        }
//    }

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

    public Zusstawkinew getZusstawki() {
        return zusstawki;
    }

    public void setZusstawki(Zusstawkinew zusstawki) {
        this.zusstawki = zusstawki;
    }


    public Zusstawkinew getSelected() {
        return selected;
    }

    public void setSelected(Zusstawkinew selected) {
        this.selected = selected;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public boolean isWszystkielata() {
        return wszystkielata;
    }

    public void setWszystkielata(boolean wszystkielata) {
        this.wszystkielata = wszystkielata;
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
   
public void nowestawki() {
//    List<Podatnik> podatnicy = podatnikDAO.findAll();
//    List<Zusstawkinew> nowewiersze = new ArrayList<>();
//    for (Podatnik p : podatnicy) {
//        boolean dodacudzial = p.getWykazudzialow()!=null&&p.getWykazudzialow().size()==1;
//        if (p.getZusstawkinowe()!=null) {
//            for (Zusstawkinew s : p.getZusparametr()) {
//                Zusstawkinew n = new Zusstawkinew(s);
//                n.setPodatnik(p);
//                if (dodacudzial) {
//                    if (p.getId()!=5) {
//                        n.setUdzialowiec(p.getWykazudzialow().get(0));
//                    }
//                }
//                if (n!=null) {
//                    nowewiersze.add(n);
//                }
//            }
//        }
//    }
//    if (nowewiersze!=null) {
//        try {
//            zusstawkinewDAO.createList(nowewiersze);
//        } catch (Exception ew){
//            System.out.println(E.e(ew));
//        }
//    }
//    Msg.msg("Zrobiono");
//    System.out.println("koniec zus");
}    
    
}
