/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PitDAO;
import dao.StrataDAO;
import entity.Strata;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
 import org.primefaces.PrimeFaces;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class StrataView  implements Serializable{
    private static final long serialVersionUID = 1L;
    private List<Strata> stratypodatnika;
    @Inject
    private StrataDAO strataDAO;
    @Inject
    private PitDAO pitDAO;
    @Inject
    private Strata selected;
    @Inject
    private WpisView wpisView;

    public StrataView() {
    }
    
    @PostConstruct
    private void init() { //E.m(this);
        stratypodatnika = strataDAO.findPodatnik(wpisView.getPodatnikObiekt());
        selected.setRok(wpisView.getRokUprzedni());
    }
    
    public void usunstrate(Strata loop) {
        try {
            strataDAO.remove(loop);
            stratypodatnika.remove(loop);
            Msg.msg("i", "Usunieto stratę za rok " + loop.getRok(), "akordeon:form2:messages");
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Wystąpił błąd. Wołaj szefa " + loop, "akordeon:form2:messages");
        }
    }
    
    public void dodajstrate() {
        try {
            if (selected.getWykorzystano() > selected.getKwota()) {
                Msg.msg("e", "Kwota wykorzystana większa od straty", "akordeon:form2:messages");
                throw new Exception();
            }
            selected.obliczpolowe();
            selected.setPodatnikObj(wpisView.getPodatnikObiekt());
            if (stratypodatnika.contains(selected)) {
                Msg.msg("e", "Strata z tego roku jest już naniesiona", "akordeon:form2:messages");
                throw new Exception();
            }
            strataDAO.create(selected);
            stratypodatnika.add(selected);
            selected = new Strata();
            selected.setRok(wpisView.getRokUprzedni());
            Msg.msg("i", "Dodano stratę za rok " + selected.getRok(), "akordeon:form2:messages");
            PrimeFaces.current().ajax().update("akordeon:form1");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd poczdas dodawania straty za rok " + selected.getRok(), "akordeon:form2:messages");
        }
    }
    
    public void naniesRozliczenieStrat() {
        Msg.msg("e","Trzeba przerobic rozliczanie strat pkpir");
//        Msg.msg("i", "Rozpoczynam rozliczanie strat");
//        List<Pitpoz> pitpoz = pitDAO.findList(wpisView.getRokUprzedniSt(), "13", wpisView.getPodatnikWpisu());
//        if (pitpoz.isEmpty()) {
//            Msg.msg("e", "Nie sporządzono pitu za 13-mc poprzedniego roku. Przerywam nanoszenie strat");
//            return;
//        }
//        double strataRozliczonaWDanymRoku = 0.0;
//        double wynikzarok = 0.0;
//        for (Pitpoz p : pitpoz) {
//            strataRozliczonaWDanymRoku = Z.z(p.getStrata());
//            wynikzarok = Z.z(p.getWynik());
//        }
//        try {
//            //zerowanie strat w przypadku codniecia sie niezbedne
//            for (Strata r : stratypodatnika) {
//                Iterator<StrataWykorzystanie> it = r.getListawykorzystanie().iterator();
//                while (it.hasNext()) {
//                    StrataWykorzystanie w = it.next();
//                    if (Integer.parseInt(w.getRok()) >= wpisView.getRokUprzedni()) {
//                        it.remove();
//                        strataDAO.edit(r);
//                    }
//                }
//            }
//        } catch (Exception e) { 
//            E.e(e); 
//        }
//        //dodawanie straty jak nie bylo zysku
//        if (wynikzarok < 0) {
//            Msg.msg("i", "W roku poprzednim była strata. Dopisuję stratę do listy");
//            Iterator it = stratypodatnika.iterator();
//            while (it.hasNext()) {
//                Strata y = (Strata) it.next();
//                if (y.getRok() == wpisView.getRokUprzedni()) {
//                    it.remove();
//                }
//            }
//            Strata nowastrata = new Strata(wpisView.getPodatnikObiekt(), wpisView.getRokUprzedni(), wynikzarok, Z.z(wynikzarok/2));
//            strataDAO.usuntensamrok(nowastrata);
//            strataDAO.create(nowastrata);
//            stratypodatnika.add(nowastrata);
//            //wczesniej usunieto zapisy z roku ale nie zaktualizowano podsumowan
//            for (Strata r : stratypodatnika) {
//                double sumabiezace = 0.0;
//                for (StrataWykorzystanie s : r.getListawykorzystanie()) {
//                    sumabiezace += s.getKwotawykorzystania();
//                }
//            }
//            return;
//        } else {
//            Msg.msg("i", "Poprzedni rok zakończył się zyskiem. Nie ma czego dopisać do listy.");
//        }
//        if (strataRozliczonaWDanymRoku > 0) {
//            //nanoszenie rozliczenia strat z lat poprzednich z zysku roku ubieglego
//            for (Strata r : stratypodatnika) {
//                double zostalo = wyliczStrataZostalo(r);
//                List<StrataWykorzystanie> wykorzystanie = r.getListawykorzystanie();
//                if (zostalo <= 0) {
//                    //nic nie robi bo nie ma co rozliczac
//                } else if (wykorzystanie == null) {
//                    wykorzystanie = Collections.synchronizedList(new ArrayList<>());
//                    StrataWykorzystanie w = new StrataWykorzystanie(r, wpisView.getRokUprzedniSt(), wpisView.getMiesiacWpisu());
//                    w.setKwotawykorzystania(zostalo >= strataRozliczonaWDanymRoku ? strataRozliczonaWDanymRoku : zostalo);
//                    wykorzystanie.add(w);
//                    strataRozliczonaWDanymRoku -= w.getKwotawykorzystania();
//                    r.setListawykorzystanie(wykorzystanie);
//                } else if (wykorzystanie.isEmpty()) {
//                    wykorzystanie = Collections.synchronizedList(new ArrayList<>());
//                    StrataWykorzystanie w = new StrataWykorzystanie(r, wpisView.getRokUprzedniSt(), wpisView.getMiesiacWpisu());
//                    w.setKwotawykorzystania(zostalo >= strataRozliczonaWDanymRoku ? strataRozliczonaWDanymRoku : zostalo);
//                    wykorzystanie.add(w);
//                    strataRozliczonaWDanymRoku -= w.getKwotawykorzystania();
//                    r.setListawykorzystanie(wykorzystanie);
//                } else {
//                    List<StrataWykorzystanie> nowewykorzystania = Collections.synchronizedList(new ArrayList<>());
//                    for (Iterator<StrataWykorzystanie> it =  wykorzystanie.iterator(); it.hasNext(); ) {
//                        StrataWykorzystanie s = (StrataWykorzystanie) it.next();
//                        if (s.getRok().equals(wpisView.getRokUprzedniSt())) {
//                            s.setKwotawykorzystania(zostalo >= strataRozliczonaWDanymRoku ? strataRozliczonaWDanymRoku : zostalo);
//                            strataRozliczonaWDanymRoku -= s.getKwotawykorzystania();
//                            strataRozliczonaWDanymRoku = Math.round(strataRozliczonaWDanymRoku * 100.0) / 100.0;
//                            break;
//                        } else {
//                            StrataWykorzystanie w = new StrataWykorzystanie(r, wpisView.getRokUprzedniSt(), wpisView.getMiesiacWpisu());
//                            w.setKwotawykorzystania(zostalo >= strataRozliczonaWDanymRoku ? strataRozliczonaWDanymRoku : zostalo);
//                            strataRozliczonaWDanymRoku -= w.getKwotawykorzystania();
//                            strataRozliczonaWDanymRoku = Z.z(strataRozliczonaWDanymRoku);
//                            nowewykorzystania.add(w);
//                        }
//                    }
//                    wykorzystanie.addAll(nowewykorzystania);
//                }
//                strataDAO.edit(r);
//            }
//        }
//        stratypodatnika = strataDAO.findPodatnik(wpisView.getPodatnikObiekt());
//        for (Strata r : stratypodatnika) {
//            double sumabiezace = 0.0;
//            try {
//                for (StrataWykorzystanie s : r.getListawykorzystanie()) {
//                    sumabiezace += s.getKwotawykorzystania();
//                }
//            } catch (Exception e) { 
//                E.e(e); 
//            }
//            strataDAO.edit(r);
//        }
//        Msg.msg("i", "Ukończyłem rozliczanie strat");
    }

    //wyliczenie niezbedne przy wracaniu do historycznych pitow
    private double wyliczStrataZostalo(Strata tmp) {
        double zostalo = 0.0;
        Msg.msg("e","Trzeba przerobic rozliczanie strat pkpir");
//        double sumabiezace = 0.0;
//        try {
//            for (StrataWykorzystanie s : tmp.getListawykorzystanie()) {
//                if (Integer.parseInt(s.getRok()) < wpisView.getRokUprzedni()) {
//                    sumabiezace += s.getKwotawykorzystania();
//                    sumabiezace = Math.round(sumabiezace * 100.0) / 100.0;
//                }
//            }
//        } catch (Exception e) { E.e(e); 
//        }
//        zostalo += Z.z(tmp.getKwota() - tmp.getWykorzystano() - sumabiezace);
        return zostalo;
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    public List<Strata> getStratypodatnika() {
        return stratypodatnika;
    }
    
    public void setStratypodatnika(List<Strata> stratypodatnika) {
        this.stratypodatnika = stratypodatnika;
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }
    
    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    public Strata getSelected() {
        return selected;
    }
    
    public void setSelected(Strata selected) {
        this.selected = selected;
    }
//</editor-fold>
    
    
    
    
}
