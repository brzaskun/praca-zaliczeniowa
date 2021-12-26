/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.AngazFacade;
import dao.PodatkiFacade;
import embeddable.Mce;
import embeddable.Oddelegowanie;
import embeddable.OddelegowanieTabela;
import entity.Angaz;
import entity.Kalendarzmiesiac;
import entity.Podatki;
import entity.Umowa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

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
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private PodatkiFacade podatkiFacade;
    
    public void init() {
        List<Angaz> angaze = angazFacade.findByFirma(wpisView.getFirma());
        lista = new ArrayList<>();
        List<String> lata = new ArrayList<>();
        lata.add("2020");
        lata.add("2021");
        for (Angaz a : angaze) {
            List<Kalendarzmiesiac> kalendarze = a.getKalendarze();
            for (String rok : lata) {
                List<Podatki> stawkipodatkowe = podatkiFacade.findByRokUmowa(rok, "P");
                if (Integer.parseInt(rok)>2019) {
                    for (String mc : Mce.getMceListS()) {
                        Oddelegowanie oddelegowanie = new Oddelegowanie(kalendarze, a, rok, mc, stawkipodatkowe);
                        lista.add(oddelegowanie);
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
                    OddelegowanieTabela doedycji = pobierzrok(tabela, p.getUmowa(), p.getRok());
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
                            double rsuma = r.getSuma();
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
                    if (p.getRok().equals("2021")) {
                        tabela2001.add(p);
                    }
                }
            }
        }
        System.out.println("");
    }

    private OddelegowanieTabela pobierzrok(List<OddelegowanieTabela> tabela, Umowa umowa, String rok) {
        OddelegowanieTabela zwrot = null;
        for (OddelegowanieTabela t : tabela) {
            if (t.getPracownik().equals(umowa.getPracownik())&&t.getRok().equals(rok)) {
                zwrot = t;
            }
        }
        return zwrot;
    }

    private void uzupelnij(OddelegowanieTabela doedycji, Oddelegowanie p) {
        switch (p.getMc()) {
            case "01":
            doedycji.setO_01(p);
            doedycji.setSuma(doedycji.getSuma()+p.getLiczbadni());
            break;
            case "02":
            doedycji.setO_02(p);
            doedycji.setSuma(doedycji.getSuma()+p.getLiczbadni());
            break;
            case "03":
            doedycji.setO_03(p);
            doedycji.setSuma(doedycji.getSuma()+p.getLiczbadni());
            break;
            case "04":
            doedycji.setO_04(p);
            doedycji.setSuma(doedycji.getSuma()+p.getLiczbadni());
            break;
            case "05":
            doedycji.setO_05(p);
            doedycji.setSuma(doedycji.getSuma()+p.getLiczbadni());
            break;
            case "06":
            doedycji.setO_06(p);
            doedycji.setSuma(doedycji.getSuma()+p.getLiczbadni());
            break;
            case "07":
            doedycji.setO_07(p);
            doedycji.setSuma(doedycji.getSuma()+p.getLiczbadni());
            break;
            case "08":
            doedycji.setO_08(p);
            doedycji.setSuma(doedycji.getSuma()+p.getLiczbadni());
            break;
            case "09":
            doedycji.setO_09(p);
            doedycji.setSuma(doedycji.getSuma()+p.getLiczbadni());
            break;
            case "10":
            doedycji.setO_10(p);
            doedycji.setSuma(doedycji.getSuma()+p.getLiczbadni());
            break;
            case "11":
            doedycji.setO_11(p);
            doedycji.setSuma(doedycji.getSuma()+p.getLiczbadni());
            break;
            case "12":
            doedycji.setO_12(p);
            doedycji.setSuma(doedycji.getSuma()+p.getLiczbadni());
            break;
        }
        if (doedycji.getSuma()>182 && doedycji.getRokmcprzekroczenia()==null) {
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
    
    
}
