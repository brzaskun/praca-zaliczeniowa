/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.AngazFacade;
import dao.KalendarzmiesiacFacade;
import dao.PasekwynagrodzenFacade;
import dao.PodatkiFacade;
import embeddable.Mce;
import embeddable.Oddelegowanie;
import embeddable.OddelegowanieTabela;
import entity.Angaz;
import entity.Kalendarzmiesiac;
import entity.Pasekwynagrodzen;
import entity.Podatki;
import entity.Pracownik;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import z.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class OddelegowanieView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    private List<Oddelegowanie> lista;
    private List<OddelegowanieTabela> tabela;
    private List<OddelegowanieTabela> tabela2001;
    private OddelegowanieTabela selected;
    private OddelegowanieTabela selected1;
    private OddelegowanieTabela selected2;
    private OddelegowanieTabela selected3;
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private PodatkiFacade podatkiFacade;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    @Inject
    private PasekwynagrodzenFacade pasekwynagrodzenFacade;
    
    
    public void init() {
        List<Angaz> angaze = angazFacade.findByFirma(wpisView.getFirma());
        lista = new ArrayList<>();
        List<String> lata = new ArrayList<>();
        String rokbiezacy = wpisView.getRokWpisu();
        String rokuprzedni = wpisView.getRokUprzedni();
        String rokuprzedniuprzedni = String.valueOf(Integer.parseInt(wpisView.getRokUprzedni())-1);
        lata.add(rokuprzedniuprzedni);
        lata.add(rokuprzedni);
        lata.add(rokbiezacy);
        for (Angaz a : angaze) {
            List<Kalendarzmiesiac> kalendarze = kalendarzmiesiacFacade.findByAngaz(a);
            for (String rok : lata) {
                if (Integer.parseInt(rok)>2019  ) {
                    List<Pasekwynagrodzen> paski = new ArrayList<>();
                    paski.addAll(pasekwynagrodzenFacade.findByRokWyplAngaz(rok, a));
                    List<Podatki> stawkipodatkowe = podatkiFacade.findByRokUmowa(rok, "P");
                    if (paski!=null&&paski.size()>0) {
                        for (String mc : Mce.getMceListS()) {
                            Kalendarzmiesiac pobranykalendarz = pobierzkalendarz(kalendarze, rok, mc);
                            if (pobranykalendarz!=null) {
                                Oddelegowanie oddelegowanie = new Oddelegowanie(pobranykalendarz, paski, a, rok, mc, stawkipodatkowe);
                                if (oddelegowanie.getLiczbadni()>0) {
                                    lista.add(oddelegowanie);
                                }
                            }
                        }
                    }
                }
            }
            
        }
        if (lista!=null) {
            tabela = new ArrayList<>();
            for (Oddelegowanie p : lista) {
                if (tabela.isEmpty()) {
                    tabela.add(new OddelegowanieTabela(p));
                } else {
                    OddelegowanieTabela doedycji = pobierzrok(tabela, p.getAngaz().getPracownik(), p.getRok());
                    if (doedycji==null) {
                        tabela.add(new OddelegowanieTabela(p));
                    } else {
                        uzupelnij(doedycji, p);
                    }
                }
            }
        }
        if (tabela != null) {
            for (OddelegowanieTabela p : tabela) {
                for (OddelegowanieTabela r : tabela) {
                    Integer prok = Integer.parseInt(p.getRok());
                    Integer rrok = Integer.parseInt(r.getRok());
                    if (p.getPracownik().equals(r.getPracownik()) && rrok < prok) {
                        if (r.getRokmcprzekroczenia() == null) {
                            double rsuma = r.getSumadni();
                            double psuma = rsuma;
                            psuma = psuma + p.getO_01().getLiczbadni();
                            if (psuma > 182) {
                                p.setRokmcprzekroczenia(p.getRok() + "/01");
                                break;
                            }
                            psuma = psuma + p.getO_02().getLiczbadni();
                            if (psuma > 182) {
                                p.setRokmcprzekroczenia(p.getRok() + "/02");
                                break;
                            }
                            psuma = psuma + p.getO_03().getLiczbadni();
                            if (psuma > 182) {
                                p.setRokmcprzekroczenia(p.getRok() + "/03");
                                break;
                            }
                            psuma = psuma + p.getO_04().getLiczbadni();
                            if (psuma > 182) {
                                p.setRokmcprzekroczenia(p.getRok() + "/04");
                                break;
                            }
                            psuma = psuma + p.getO_05().getLiczbadni();
                            if (psuma > 182) {
                                p.setRokmcprzekroczenia(p.getRok() + "/05");
                                break;
                            }
                            psuma = psuma + p.getO_06().getLiczbadni();
                            if (psuma > 182) {
                                p.setRokmcprzekroczenia(p.getRok() + "/06");
                                break;
                            }
                            psuma = psuma + p.getO_07().getLiczbadni();
                            if (psuma > 182) {
                                p.setRokmcprzekroczenia(p.getRok() + "/07");
                                break;
                            }
                            psuma = psuma + p.getO_08().getLiczbadni();
                            if (psuma > 182) {
                                p.setRokmcprzekroczenia(p.getRok() + "/08");
                                break;
                            }
                            psuma = psuma + p.getO_09().getLiczbadni();
                            if (psuma > 182) {
                                p.setRokmcprzekroczenia(p.getRok() + "/09");
                                break;
                            }
                            psuma = psuma + p.getO_10().getLiczbadni();
                            if (psuma > 182) {
                                p.setRokmcprzekroczenia(p.getRok() + "/10");
                                break;
                            }
                            psuma = psuma + p.getO_11().getLiczbadni();
                            if (psuma > 182) {
                                p.setRokmcprzekroczenia(p.getRok() + "/11");
                                break;
                            }
                            psuma = psuma + p.getO_12().getLiczbadni();
                            if (psuma > 182) {
                                p.setRokmcprzekroczenia(p.getRok() + "/12");
                                break;
                            }
                        } else {
                            p.setRokmcprzekroczenia(r.getRokmcprzekroczenia());
                        }
                    }
                }
            }
            if (tabela != null) {
                tabela2001 = new ArrayList<>();
                for (OddelegowanieTabela p : tabela) {
                    //if (p.getRok().equals(rokbiezacy)&&p.getRokmcprzekroczenia()!=null) {
                        tabela2001.add(p);
                    //}
                }
            }
        }
        System.out.println("");
    }
    
     private Kalendarzmiesiac pobierzkalendarz(List<Kalendarzmiesiac> kalendarze, String rok, String mc) {
        Kalendarzmiesiac zwrot = null;
        for (Kalendarzmiesiac k : kalendarze) {
            if (k.getRok().equals(rok)&&k.getMc().equals(mc)) {
                zwrot = k;
                break;
            }
        }
        return zwrot;
    }

    private OddelegowanieTabela pobierzrok(List<OddelegowanieTabela> tabela, Pracownik pracownik, String rok) {
        OddelegowanieTabela zwrot = null;
        for (OddelegowanieTabela t : tabela) {
            if (t.getPracownik().equals(pracownik)&&t.getRok().equals(rok)) {
                zwrot = t;
            }
        }
        return zwrot;
    }

    private void uzupelnij(OddelegowanieTabela doedycji, Oddelegowanie p) {
        switch (p.getMc()) {
            case "01":
            doedycji.setO_01(p);
            doedycji.setSumadni(doedycji.getSumadni()+p.getLiczbadni());
            break;
            case "02":
            doedycji.setO_02(p);
            doedycji.setSumadni(doedycji.getSumadni()+p.getLiczbadni());
            break;
            case "03":
            doedycji.setO_03(p);
            doedycji.setSumadni(doedycji.getSumadni()+p.getLiczbadni());
            break;
            case "04":
            doedycji.setO_04(p);
            doedycji.setSumadni(doedycji.getSumadni()+p.getLiczbadni());
            break;
            case "05":
            doedycji.setO_05(p);
            doedycji.setSumadni(doedycji.getSumadni()+p.getLiczbadni());
            break;
            case "06":
            doedycji.setO_06(p);
            doedycji.setSumadni(doedycji.getSumadni()+p.getLiczbadni());
            break;
            case "07":
            doedycji.setO_07(p);
            doedycji.setSumadni(doedycji.getSumadni()+p.getLiczbadni());
            break;
            case "08":
            doedycji.setO_08(p);
            doedycji.setSumadni(doedycji.getSumadni()+p.getLiczbadni());
            break;
            case "09":
            doedycji.setO_09(p);
            doedycji.setSumadni(doedycji.getSumadni()+p.getLiczbadni());
            break;
            case "10":
            doedycji.setO_10(p);
            doedycji.setSumadni(doedycji.getSumadni()+p.getLiczbadni());
            break;
            case "11":
            doedycji.setO_11(p);
            doedycji.setSumadni(doedycji.getSumadni()+p.getLiczbadni());
            break;
            case "12":
            doedycji.setO_12(p);
            doedycji.setSumadni(doedycji.getSumadni()+p.getLiczbadni());
            break;
        }
        doedycji.setPodatekpolski(Z.z(doedycji.getPodatekpolski()+p.getPodatekpolska()));
        doedycji.setPrzychodzagraniczny(Z.z(doedycji.getPrzychodzagraniczny()+p.getPrzychodyzagranica()));
        doedycji.setPrzychodpolski(Z.z(doedycji.getPrzychodpolski()+p.getPrzychodypolska()));
        doedycji.setPodatekzagraniczny(Z.z(doedycji.getPodatekzagraniczny()+p.getPodatekzagranica()));
        if (doedycji.getSumadni()>182 && doedycji.getRokmcprzekroczenia()==null) {
            doedycji.setRokmcprzekroczenia(p.getRok()+"/"+p.getMc());
        } else {
//            System.out.println("prac "+p.getUmowa().getPracownik().getNazwiskoImie());
//            System.out.println("rok "+p.getRok()+"/"+"mc "+p.getMc());
//            System.out.println("suma "+doedycji.getSuma());
        }
    }

    public List<OddelegowanieTabela> getTabela() {
        return tabela;
    }

    public void setTabela(List<OddelegowanieTabela> tabela) {
        this.tabela = tabela;
    }

    public OddelegowanieTabela getSelected() {
        return selected;
    }

    public void setSelected(OddelegowanieTabela selected) {
        this.selected = selected;
    }

    public List<OddelegowanieTabela> getTabela2001() {
        return tabela2001;
    }

    public void setTabela2001(List<OddelegowanieTabela> tabela2001) {
        this.tabela2001 = tabela2001;
    }

    public OddelegowanieTabela getSelected1() {
        return selected1;
    }

    public void setSelected1(OddelegowanieTabela selected1) {
        this.selected1 = selected1;
    }

    public OddelegowanieTabela getSelected2() {
        return selected2;
    }

    public void setSelected2(OddelegowanieTabela selected2) {
        this.selected2 = selected2;
    }

    public OddelegowanieTabela getSelected3() {
        return selected3;
    }

    public void setSelected3(OddelegowanieTabela selected3) {
        this.selected3 = selected3;
    }
    
    
}
