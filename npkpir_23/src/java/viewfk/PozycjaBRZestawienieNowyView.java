    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.PlanKontFKBean;
import beansFK.StronaWierszaBean;
import beansFK.UkladBRBean;
import comparator.PozycjaRZiSBilanscomparator;
import dao.KontoDAOfk;
import dao.KontopozycjaZapisDAO;
import dao.PodatnikOpodatkowanieDAO;
import dao.PozycjaBilansDAO;
import dao.PozycjaRZiSDAO;
import dao.StronaWierszaDAO;
import dao.UkladBRDAO;
import dao.WierszBODAO;
import data.Data;
import entityfk.Konto;
import entityfk.PozycjaBilans;
import entityfk.PozycjaRZiS;
import entityfk.PozycjaRZiSBilans;
import entityfk.StronaWiersza;
import entityfk.UkladBR;
import error.E;
import interceptor.ConstructorInterceptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import msg.Msg;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named @Interceptors(ConstructorInterceptor.class)
@ViewScoped
public class PozycjaBRZestawienieNowyView implements Serializable {
    private static final long serialVersionUID = 1L;

 
    
    @Inject
    private KontoDAOfk kontoDAO;
    @Inject
    private KontopozycjaZapisDAO kontopozycjaZapisDAO;
    @Inject 
    private StronaWierszaDAO stronaWierszaDAO;
    @Inject
    private PozycjaRZiSDAO pozycjaRZiSDAO;
    @Inject
    private PozycjaBilansDAO pozycjaBilansDAO;
    @Inject
    private PodatnikOpodatkowanieDAO podatnikOpodatkowanieDDAO;
    private UkladBR uklad;
    @Inject
    private WierszBODAO wierszBODAO;
    @Inject
    private UkladBRDAO ukladBRDAO;
    private boolean laczlata;
    private String bilansnadzien;
    private String bilansoddnia;
    private String opisdodatkowy;
    private String kontabilansowebezprzyporzadkowania;
    private List<PozycjaRZiSBilans> pozycje;
    private List<PozycjaRZiSBilans> selectedNodes;
    private List<PozycjaRZiSBilans> rootBilansAktywa;
    private List<PozycjaRZiSBilans> rootBilansPasywa;
    private double sumabilansowaaktywa;
    private double sumabilansowaaktywaBO;
    private double sumabilansowapasywa;
    private double sumabilansowapasywaBO;
    
    @Inject
    private WpisView wpisView;
    

    public PozycjaBRZestawienieNowyView() {
         ////E.m(this);
       
       
    }

    @PostConstruct
    public void init() { //E.m(this);
        try {
            uklad = ukladBRDAO.findukladBRPodatnikRokAktywny(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            bilansnadzien = Data.ostatniDzien(wpisView);
            bilansoddnia = wpisView.getRokUprzedniSt()+"-12-31";
        } catch (Exception e){}
    }
    
    public void obliczBilansOtwarciaBilansDataWybierz() {
        if (uklad == null || uklad.getUklad() == null) {
            uklad = ukladBRDAO.findukladBRPodatnikRokPodstawowy(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        }
        rootBilansAktywa = Collections.synchronizedList(new ArrayList<>());
        rootBilansPasywa = Collections.synchronizedList(new ArrayList<>());
        pobierzPozycjeAktywaPasywa(rootBilansAktywa, rootBilansPasywa);
        Collections.sort(rootBilansAktywa, new PozycjaRZiSBilanscomparator());
        Collections.sort(rootBilansPasywa, new PozycjaRZiSBilanscomparator());
        List<StronaWiersza> zapisy = StronaWierszaBean.pobraniezapisowbilansoweNowe(stronaWierszaDAO, wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt(), wpisView.getPodatnikObiekt());
        List<StronaWiersza> zapisyRokPop = StronaWierszaBean.pobraniezapisowbilansoweNowe(stronaWierszaDAO, "12", wpisView.getRokUprzedniSt(), wpisView.getPodatnikObiekt());
        List<Konto> pustekonta = kontoDAO.findByPodatnikBilansoweBezPotomkowPuste(wpisView);
        if (pustekonta.isEmpty()==false) {
            pustekonta = pustekonta.parallelStream().filter(item->item.isSaldodosprawozdania()).collect(Collectors.toList());
            kontabilansowebezprzyporzadkowania = pustekonta.stream().map(Konto::getPelnynumer).collect(Collectors.toList()).toString();
        }
        List<Konto> plankon = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        Konto kontowyniku = PlanKontFKBean.findKonto860(plankon);
        List<Konto> plankonBO = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        sumujObrotyNaKontach(zapisy, plankon);
        sumujObrotyNaKontach(zapisyRokPop, plankonBO);
        naniesKwoteWynikFinansowy(kontowyniku);
        Konto kontowynikurokpop = PlanKontFKBean.findKonto860(plankonBO);
        List<StronaWiersza> zapisyBO860 = zapisy.stream().filter(item->item.getKonto().getPelnynumer().equals("860")).collect(Collectors.toList());
        zapisyBO860 = zapisyBO860.stream().filter(item->item.getDokfk().getRodzajedok().getSkrot().equals("BO")).collect(Collectors.toList());
        sumujObrotyNaKontach(zapisyBO860, plankonBO);
        try {
           plankon.stream().forEach(new Consumer<Konto>() {
               @Override
               public void accept(Konto kontoprzetwarzane) {
                   Konto konto = znajdzkontozpozycja(kontoprzetwarzane);
                   if (konto.isJednoSaldo()){
                       boolean aktywa = konto.isAktywa();
                       if (aktywa) {
                            PozycjaRZiSBilans pozycjaznaleziona = rootBilansAktywa.parallelStream().filter(item->item.getPozycjaString().equals(konto.getPozycjaWn())).findFirst().orElse(null);
                            if (pozycjaznaleziona!=null) {
                                pozycjaznaleziona.setKwota(pozycjaznaleziona.getKwota()+kontoprzetwarzane.getSaldoWn()-kontoprzetwarzane.getSaldoMa());
                                pozycjaznaleziona.obsluzPrzyporzadkowaneKonta(kontoprzetwarzane.getSaldoWn()-kontoprzetwarzane.getSaldoMa(), kontoprzetwarzane);
                            }
                       } else {
                           PozycjaRZiSBilans pozycjaznaleziona = rootBilansPasywa.parallelStream().filter(item->item.getPozycjaString().equals(konto.getPozycjaWn())).findFirst().orElse(null);
                            if (pozycjaznaleziona!=null) {
                                pozycjaznaleziona.setKwota(pozycjaznaleziona.getKwota()-kontoprzetwarzane.getSaldoWn()+kontoprzetwarzane.getSaldoMa());
                                pozycjaznaleziona.obsluzPrzyporzadkowaneKonta(kontoprzetwarzane.getSaldoWn()+kontoprzetwarzane.getSaldoMa(), kontoprzetwarzane);
                            }
                       }
                   } else {
                       if (kontoprzetwarzane.getSaldoWn()!=0.0) {
                            PozycjaRZiSBilans pozycjaznaleziona = rootBilansAktywa.parallelStream().filter(item->item.getPozycjaString().equals(konto.getPozycjaWn())).findFirst().orElse(null);
                            if (pozycjaznaleziona!=null) {
                                pozycjaznaleziona.setKwota(pozycjaznaleziona.getKwota()+kontoprzetwarzane.getSaldoWn());
                                pozycjaznaleziona.obsluzPrzyporzadkowaneKonta(kontoprzetwarzane.getSaldoWn(), kontoprzetwarzane);
                            }
                       } else if (kontoprzetwarzane.getSaldoMa()!=0.0) {
                            PozycjaRZiSBilans pozycjaznaleziona = rootBilansPasywa.parallelStream().filter(item->item.getPozycjaString().equals(konto.getPozycjaMa())).findFirst().orElse(null);
                            if (pozycjaznaleziona!=null) {
                                pozycjaznaleziona.setKwota(pozycjaznaleziona.getKwota()+kontoprzetwarzane.getSaldoMa());
                                pozycjaznaleziona.obsluzPrzyporzadkowaneKonta(kontoprzetwarzane.getSaldoMa(), kontoprzetwarzane);
                            }
                       }
                   }
               }

               private Konto znajdzkontozpozycja(Konto konto) {
                   Konto zwrot = konto;
                   while (zwrot.getPozycjaWn()==null) {
                       Konto macierzyste = zwrot.getKontomacierzyste()!=null?zwrot.getKontomacierzyste():null;
                       if (macierzyste==null) {
                           break;
                       } else  if (macierzyste!=null&&macierzyste.getPozycjaWn()!=null) {
                           zwrot = macierzyste;
                           break;
                       } else {
                           zwrot = macierzyste;
                       }
                   }
                   return zwrot;
               }
           });
           plankonBO.stream().forEach(new Consumer<Konto>() {
               @Override
               public void accept(Konto kontoprzetwarzane) {
                   Konto konto = znajdzkontozpozycja(kontoprzetwarzane);
                   if (konto.isJednoSaldo()){
                        boolean aktywa = konto.isAktywa();
                       if (aktywa) {
                            PozycjaRZiSBilans pozycjaznaleziona = rootBilansAktywa.parallelStream().filter(item->item.getPozycjaString().equals(konto.getPozycjaWn())).findFirst().orElse(null);
                            if (pozycjaznaleziona!=null) {
                                pozycjaznaleziona.setKwotabo(pozycjaznaleziona.getKwotabo()+kontoprzetwarzane.getSaldoWn()-kontoprzetwarzane.getSaldoMa());
                                pozycjaznaleziona.obsluzPrzyporzadkowaneKonta(kontoprzetwarzane.getSaldoWn()-kontoprzetwarzane.getSaldoMa(), kontoprzetwarzane);
                            }
                       } else {
                           PozycjaRZiSBilans pozycjaznaleziona = rootBilansPasywa.parallelStream().filter(item->item.getPozycjaString().equals(konto.getPozycjaWn())).findFirst().orElse(null);
                            if (pozycjaznaleziona!=null) {
                                pozycjaznaleziona.setKwotabo(pozycjaznaleziona.getKwotabo()-kontoprzetwarzane.getSaldoWn()+kontoprzetwarzane.getSaldoMa());
                                pozycjaznaleziona.obsluzPrzyporzadkowaneKonta(kontoprzetwarzane.getSaldoWn()+kontoprzetwarzane.getSaldoMa(), kontoprzetwarzane);
                            }
                       }
                   } else {
                       if (kontoprzetwarzane.getSaldoWn()!=0.0) {
                            PozycjaRZiSBilans pozycjaznaleziona = rootBilansAktywa.parallelStream().filter(item->item.getPozycjaString().equals(konto.getPozycjaWn())).findFirst().orElse(null);
                            if (pozycjaznaleziona!=null) {
                                pozycjaznaleziona.setKwotabo(pozycjaznaleziona.getKwotabo()+kontoprzetwarzane.getSaldoWn());
                                pozycjaznaleziona.obsluzPrzyporzadkowaneKonta(kontoprzetwarzane.getSaldoWn(), kontoprzetwarzane);
                            }
                       } else if (kontoprzetwarzane.getSaldoMa()!=0.0) {
                            PozycjaRZiSBilans pozycjaznaleziona = rootBilansPasywa.parallelStream().filter(item->item.getPozycjaString().equals(konto.getPozycjaMa())).findFirst().orElse(null);
                            if (pozycjaznaleziona!=null) {
                                pozycjaznaleziona.setKwotabo(pozycjaznaleziona.getKwotabo()+kontoprzetwarzane.getSaldoMa());
                                pozycjaznaleziona.obsluzPrzyporzadkowaneKonta(kontoprzetwarzane.getSaldoMa(), kontoprzetwarzane);
                            }
                       }
                   }
               }

               private Konto znajdzkontozpozycja(Konto konto) {
                   Konto zwrot = konto;
                   while (zwrot.getPozycjaWn()==null) {
                       Konto macierzyste = zwrot.getKontomacierzyste()!=null?zwrot.getKontomacierzyste():null;
                       if (macierzyste==null) {
                           break;
                       } else  if (macierzyste!=null&&macierzyste.getPozycjaWn()!=null) {
                           zwrot = macierzyste;
                           break;
                       } else {
                           zwrot = macierzyste;
                       }
                   }
                   return zwrot;
               }
           });
           int level = 4;
           for (int l = level; l>0;l--) {
                final int currentLevel = l;
                List<PozycjaRZiSBilans> listalevelaktywa = rootBilansAktywa.stream().filter(item->item.getLevel()==currentLevel&&(item.getKwota()!=0.0||item.getKwotabo()!=0.0)).collect(Collectors.toList());
                listalevelaktywa.forEach(new Consumer<PozycjaRZiSBilans>(){
                    @Override
                    public void accept(PozycjaRZiSBilans pozycjarzisbilans) {
                        if (pozycjarzisbilans.getPozycjaString().contains(".")) {
                            String macierzysta = getStringBeforeLastSeparator(pozycjarzisbilans.getPozycjaString(), ".");
                            PozycjaRZiSBilans znaleziona = rootBilansAktywa.stream().filter(item->item.getPozycjaString().equals(macierzysta)).findFirst().orElse(null);
                            if (znaleziona!=null){
                                znaleziona.setKwota(znaleziona.getKwota()+pozycjarzisbilans.getKwota());
                                znaleziona.setKwotabo(znaleziona.getKwotabo()+pozycjarzisbilans.getKwotabo());
                            }
                        }

                    }

                });
                 List<PozycjaRZiSBilans> listalevelpasywa = rootBilansPasywa.stream().filter(item->item.getLevel()==currentLevel&&(item.getKwota()!=0.0||item.getKwotabo()!=0.0)).collect(Collectors.toList());
                listalevelpasywa.forEach(new Consumer<PozycjaRZiSBilans>(){
                    @Override
                    public void accept(PozycjaRZiSBilans pozycjarzisbilans) {
                        if (pozycjarzisbilans.getPozycjaString().contains(".")) {
                            String macierzysta = getStringBeforeLastSeparator(pozycjarzisbilans.getPozycjaString(), ".");
                            PozycjaRZiSBilans znaleziona = rootBilansPasywa.stream().filter(item->item.getPozycjaString().equals(macierzysta)).findFirst().orElse(null);
                            if (znaleziona!=null){
                                znaleziona.setKwota(znaleziona.getKwota()+pozycjarzisbilans.getKwota());
                                znaleziona.setKwotabo(znaleziona.getKwotabo()+pozycjarzisbilans.getKwotabo());
                            }
                        }

                    }

                });
           }
           rootBilansAktywa.stream().forEach(new Consumer<PozycjaRZiSBilans>(){
                @Override
                public void accept(PozycjaRZiSBilans t) {
                    if (t.getFormula()!=null) {
                        String formula = t.getFormula();
                        if (formula!=null && formula.isEmpty()==false) {
                            int formulalength = formula.length();
                            String[] formulaParse = parseall(formula);
                            double wynik = dotheMathBilans(formulaParse, formulalength, 0, rootBilansAktywa);
                            t.setKwota(wynik);
                            double wynikbo = dotheMathBilans(formulaParse, formulalength, 1, rootBilansAktywa);
                            t.setKwotabo(wynikbo);
                      
                        }
                    }
                }
           });
            rootBilansPasywa.stream().forEach(new Consumer<PozycjaRZiSBilans>(){
                @Override
                public void accept(PozycjaRZiSBilans t) {
                    if (t.getFormula()!=null) {
                        String formula = t.getFormula();
                        if (formula!=null && formula.isEmpty()==false) {
                            int formulalength = formula.length();
                            String[] formulaParse = parseall(formula);
                            double wynik = dotheMathBilans(formulaParse, formulalength, 0, rootBilansPasywa);
                            t.setKwota(wynik);
                            double wynikbo = dotheMathBilans(formulaParse, formulalength, 1, rootBilansPasywa);
                            t.setKwotabo(wynikbo);
                      
                        }
                    }
                }
           });
            sumaaktywapasywaoblicz("aktywa");
            sumaaktywapasywaoblicz("pasywa");
            Msg.msg("i", "Pobrano dane ");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", e.getLocalizedMessage());
        }
        bilansnadzien = Data.ostatniDzien(wpisView);
    }
    
    private void sumaaktywapasywaoblicz(String aktywapasywa) {
        if (aktywapasywa.equals("aktywa")) {
            List<PozycjaRZiSBilans> wezly = rootBilansAktywa.stream().filter(item->item.getLevel()==0).collect(Collectors.toList());
            double suma = 0.0;
            double sumaBO = 0.0;
            for (Iterator<PozycjaRZiSBilans> it = wezly.iterator(); it.hasNext();) {
                PozycjaRZiSBilans p = it.next();
                suma += p.getKwota();
                sumaBO += p.getKwotabo();
            }
            sumabilansowaaktywa = Z.z(suma);
            sumabilansowaaktywaBO = Z.z(sumaBO);
        } else {
            List<PozycjaRZiSBilans> wezly = rootBilansPasywa.stream().filter(item->item.getLevel()==0).collect(Collectors.toList());
            double suma = 0.0;
            double sumaBO = 0.0;
            for (Iterator<PozycjaRZiSBilans> it = wezly.iterator(); it.hasNext();) {
                 PozycjaRZiSBilans p = it.next();
                suma += p.getKwota();
                sumaBO += p.getKwotabo();
            }
            sumabilansowapasywa = Z.z(suma);
            sumabilansowapasywaBO = Z.z(sumaBO);
        }
    }
    
    private void naniesKwoteWynikFinansowy(Konto kontowyniku) {
        //usunalem 27.10.2023 bo czysci pozycje przy kazdym robieniu bo!!!
        obliczRZiSOtwarciaRZiSData();
        PozycjaRZiSBilans pozycjawynikfin = pozycje.get(pozycje.size() - 1);
        if (Z.z(pozycjawynikfin.getKwota())==0.0) {
            pozycjawynikfin = pozycje.get(pozycje.size() - 2);
        }
        double wynikfinansowy = pozycjawynikfin.getKwota();
        double wf = Z.z(Math.abs(wynikfinansowy));
        if (wynikfinansowy > 0) {//zysk
            kontowyniku.setObrotyMa(kontowyniku.getObrotyMa()+wf);
        } else {//strata
            kontowyniku.setObrotyWn(kontowyniku.getObrotyWn()+wf);
        }
        double wynikkwota = kontowyniku.getObrotyWn()-kontowyniku.getObrotyMa();
        if ( wynikkwota > 0) {
            kontowyniku.setSaldoWn(wynikkwota);
        } else {
            kontowyniku.setSaldoMa(Math.abs(wynikkwota));
        }
    }
    public static void sumujObrotyNaKontach(List<StronaWiersza> zapisy, List<Konto> plankont) {
        for (StronaWiersza p : zapisy) {
            //pobiermay dane z poszczegolnego konta
            double kwotaWn = p.getWnma().equals("Wn") ? p.getKwotaPLN() : 0.0;
            double kwotaMa = p.getWnma().equals("Ma") ? p.getKwotaPLN() : 0.0;
            try {
                Konto k = plankont.get(plankont.indexOf(p.getKonto()));
                k.setObrotyWn(k.getObrotyWn() + kwotaWn);
                k.setObrotyMa(k.getObrotyMa() + kwotaMa);
            } catch (Exception e) {
                E.e(e);
            }
        };
        //a teraz trzeba podsumowac konta bez obrotow ale z bo no i z obrotami (wyjalem to z gory)
        for (Konto r : plankont) {
            if (r.getBilansowewynikowe().equals("bilansowe")) {
                double sumaObrotyWn = r.getObrotyWn();
                double sumaObrotyMa = r.getObrotyMa();
                if (sumaObrotyWn == sumaObrotyMa) {
                    r.setSaldoWn(0.0);
                    r.setSaldoMa(0.0);
                } else {
                    if (sumaObrotyWn > sumaObrotyMa) {
                        r.setSaldoWn(Z.z(sumaObrotyWn - sumaObrotyMa));
                        r.setSaldoMa(0.0);
                    } else {
                        r.setSaldoMa(Z.z(sumaObrotyMa - sumaObrotyWn));
                        r.setSaldoWn(0.0);
                    }
                }
            }
        }
    }
    
      public void obliczRZiSOtwarciaRZiSData() {
        if (uklad == null || uklad.getUklad() == null) {
            uklad = ukladBRDAO.findukladBRPodatnikRokPodstawowy(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        }
        pozycje = Collections.synchronizedList(new ArrayList<>());
        pobierzPozycje(pozycje);
        Collections.sort(pozycje, new PozycjaRZiSBilanscomparator());
        List<StronaWiersza> zapisy = StronaWierszaBean.pobraniezapisowwynikowe(stronaWierszaDAO, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        List<StronaWiersza> zapisyRokPop = StronaWierszaBean.pobraniezapisowwynikowe(stronaWierszaDAO, wpisView.getPodatnikObiekt(), wpisView.getRokUprzedniSt(), "12");
        List<Konto> pustekonta = kontoDAO.findKontaWynikowePodatnikaBezPotomkowPuste(wpisView);
        if (pustekonta.isEmpty()==false) {
            pustekonta = pustekonta.parallelStream().filter(item->item.isSaldodosprawozdania()).collect(Collectors.toList());
            kontabilansowebezprzyporzadkowania = pustekonta.parallelStream().map(Konto::getPelnynumer).collect(Collectors.toList()).toString();
        }
        try {
           zapisy.stream().forEach(new Consumer<StronaWiersza>() {
               @Override
               public void accept(StronaWiersza stronawiersza) {
                   Konto konto = znajdzkontozpozycja(stronawiersza.getKonto());
                   if (konto.isJednoSaldo()){
                        PozycjaRZiSBilans pozycjaznaleziona = pozycje.parallelStream().filter(item->item.getPozycjaString().equals(konto.getPozycjaWn())).findFirst().orElse(null);
                        if (pozycjaznaleziona!=null) {
                            pozycjaznaleziona.setKwota(pozycjaznaleziona.getKwota()+stronawiersza.getKwotaPLN());
                        }
                   } else {
                       if (stronawiersza.getKwotaWnPLN()!=0.0) {
                            PozycjaRZiSBilans pozycjaznaleziona = pozycje.parallelStream().filter(item->item.getPozycjaString().equals(konto.getPozycjaWn())).findFirst().orElse(null);
                            if (pozycjaznaleziona!=null) {
                                pozycjaznaleziona.setKwota(pozycjaznaleziona.getKwota()+stronawiersza.getKwotaPLN());
                            }
                       } else if (stronawiersza.getKwotaMaPLN()!=0.0) {
                            PozycjaRZiSBilans pozycjaznaleziona = pozycje.parallelStream().filter(item->item.getPozycjaString().equals(konto.getPozycjaMa())).findFirst().orElse(null);
                            if (pozycjaznaleziona!=null) {
                                pozycjaznaleziona.setKwota(pozycjaznaleziona.getKwota()+stronawiersza.getKwotaPLN());
                            }
                       }
                   }
               }

               private Konto znajdzkontozpozycja(Konto konto) {
                   Konto zwrot = konto;
                   while (zwrot.getPozycjaWn()==null) {
                       Konto macierzyste = zwrot.getKontomacierzyste()!=null?zwrot.getKontomacierzyste():null;
                       if (macierzyste==null) {
                           break;
                       } else  if (macierzyste!=null&&macierzyste.getPozycjaWn()!=null) {
                           zwrot = macierzyste;
                           break;
                       } else {
                           zwrot = macierzyste;
                       }
                   }
                   return zwrot;
               }
           });
           zapisyRokPop.stream().forEach(new Consumer<StronaWiersza>() {
               @Override
               public void accept(StronaWiersza stronawiersza) {
                   Konto konto = znajdzkontozpozycja(stronawiersza.getKonto());
                   if (konto.isJednoSaldo()){
                        PozycjaRZiSBilans pozycjaznaleziona = pozycje.parallelStream().filter(item->item.getPozycjaString().equals(konto.getPozycjaWn())).findFirst().orElse(null);
                        if (pozycjaznaleziona!=null) {
                            pozycjaznaleziona.setKwotabo(pozycjaznaleziona.getKwotabo()+stronawiersza.getKwotaPLN());
                        }
                   } else {
                       if (stronawiersza.getKwotaWnPLN()!=0.0) {
                            PozycjaRZiSBilans pozycjaznaleziona = pozycje.parallelStream().filter(item->item.getPozycjaString().equals(konto.getPozycjaWn())).findFirst().orElse(null);
                            if (pozycjaznaleziona!=null) {
                                pozycjaznaleziona.setKwotabo(pozycjaznaleziona.getKwotabo()+stronawiersza.getKwotaPLN());
                            }
                       } else if (stronawiersza.getKwotaMaPLN()!=0.0) {
                            PozycjaRZiSBilans pozycjaznaleziona = pozycje.parallelStream().filter(item->item.getPozycjaString().equals(konto.getPozycjaMa())).findFirst().orElse(null);
                            if (pozycjaznaleziona!=null) {
                                pozycjaznaleziona.setKwotabo(pozycjaznaleziona.getKwotabo()+stronawiersza.getKwotaPLN());
                            }
                       }
                   }
               }

               private Konto znajdzkontozpozycja(Konto konto) {
                   Konto zwrot = konto;
                   while (zwrot.getPozycjaWn()==null) {
                       Konto macierzyste = zwrot.getKontomacierzyste()!=null?zwrot.getKontomacierzyste():null;
                       if (macierzyste==null) {
                           break;
                       } else  if (macierzyste!=null&&macierzyste.getPozycjaWn()!=null) {
                           zwrot = macierzyste;
                           break;
                       } else {
                           zwrot = macierzyste;
                       }
                   }
                   return zwrot;
               }
           });
           int level = 3;
           for (int l = level; l>0;l--) {
                final int currentLevel = l;
                List<PozycjaRZiSBilans> listalevel = pozycje.stream().filter(item->item.getLevel()==currentLevel&&(item.getKwota()!=0.0||item.getKwotabo()!=0.0)).collect(Collectors.toList());
                listalevel.forEach(new Consumer<PozycjaRZiSBilans>(){
                    @Override
                    public void accept(PozycjaRZiSBilans pozycjarzisbilans) {
                        if (pozycjarzisbilans.getPozycjaString().contains(".")) {
                            String macierzysta = getStringBeforeLastSeparator(pozycjarzisbilans.getPozycjaString(), ".");
                            PozycjaRZiSBilans znaleziona = pozycje.stream().filter(item->item.getPozycjaString().equals(macierzysta)).findFirst().orElse(null);
                            if (znaleziona!=null){
                                znaleziona.setKwota(znaleziona.getKwota()+pozycjarzisbilans.getKwota());
                                znaleziona.setKwotabo(znaleziona.getKwotabo()+pozycjarzisbilans.getKwotabo());
                            }
                        }

                    }

                });
           }
           pozycje.stream().forEach(new Consumer<PozycjaRZiSBilans>(){
                @Override
                public void accept(PozycjaRZiSBilans t) {
                    if (t.getFormula()!=null) {
                        String formula = t.getFormula();
                        if (formula!=null && formula.isEmpty()==false) {
                            int formulalength = formula.length();
                            String[] formulaParse = parseall(formula);
                            double wynik = dotheMath(formulaParse, formulalength, 0);
                            t.setKwota(wynik);
                            double wynikbo = dotheMath(formulaParse, formulalength, 1);
                            t.setKwotabo(wynikbo);
                      
                        }
                    }
                }
           });
              Msg.msg("i", "Pobrano dane ");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", e.getLocalizedMessage());
        }
        bilansnadzien = Data.ostatniDzien(wpisView);
    }
      private String[] parseall(String f) {
        String[] suma = null;
        if (f != null) {
            String[] formula = formulaparser(f);
            String[] znak = znakparser(formula, f);
            int len = formula.length+znak.length;
            suma = new String[len];
            int j = 0;
            for (int i = 0; i < formula.length; i++) {
                suma[j++] = formula[i];
                if (j < suma.length) {
                    suma[j++] = znak[i];
                }
            }
        }
        return suma;
    }
      
      private String[] formulaparser(String formula) {
        return formula != null ? formula.split("[\\+|\\-|\\<|\\>]") : null;
    }
    
    private  String[] znakparser(String[] pola, String formula) {
        String[] znaki = new String[pola.length-1];
        for (int i = 0; i < pola.length-1; i++) {
            int ileobciac = pola[i].length();
            formula = formula.substring(ileobciac);
            String znak = formula.substring(0,1);
            znaki[i] = znak;
            formula = formula.substring(1);
        }
        return znaki;
    }
      private double dotheMath(String[] formulaParse, int formulalength, int rokbieacy0bo1) {
        double wynik = 0.0; 
        PozycjaRZiSBilans znalezona = pozycje.stream().filter(item->item.getPozycjaString().equals(formulaParse[0])).findAny().orElse(null);
        if (znalezona!=null) {
            double kwota = rokbieacy0bo1==0?znalezona.getKwota():znalezona.getKwotabo();
            wynik = kwota;
        }
        for (int i = 1; i < formulalength; i++) {
            String znak = formulaParse[i++];
            if (znak.equals(">") && formulaParse.length == 3) {
                if (wynik < 0.0) {
                    wynik = 0.0;
                }
            } else if (znak.equals("<") && formulaParse.length == 3) {
                if (wynik > 0.0) {
                    wynik = 0.0;
                }
            } else {
                final int kolejnosc = i;
                PozycjaRZiSBilans drugi = pozycje.stream().filter(item->item.getPozycjaString().equals(formulaParse[kolejnosc])).findAny().orElse(null);
                if (drugi!=null) {
                    double kwota = rokbieacy0bo1==0?drugi.getKwota():drugi.getKwotabo();
                    if (znak.equals("-")) {
                        wynik -= kwota;
                    } else {
                        wynik += kwota;
                    }
                }
            }
        }
        return wynik;
    }
       private double dotheMathBilans(String[] formulaParse, int formulalength, int rokbieacy0bo1, List<PozycjaRZiSBilans> pozycje) {
        double wynik = 0.0; 
        PozycjaRZiSBilans znalezona = pozycje.stream().filter(item->item.getPozycjaString().equals(formulaParse[0])).findAny().orElse(null);
        if (znalezona!=null) {
            double kwota = rokbieacy0bo1==0?znalezona.getKwota():znalezona.getKwotabo();
            wynik = kwota;
        }
        for (int i = 1; i < formulalength; i++) {
            String znak = formulaParse[i++];
            if (znak.equals(">") && formulaParse.length == 3) {
                if (wynik < 0.0) {
                    wynik = 0.0;
                }
            } else if (znak.equals("<") && formulaParse.length == 3) {
                if (wynik > 0.0) {
                    wynik = 0.0;
                }
            } else {
                final int kolejnosc = i;
                PozycjaRZiSBilans drugi = pozycje.stream().filter(item->item.getPozycjaString().equals(formulaParse[kolejnosc])).findAny().orElse(null);
                if (drugi!=null) {
                    double kwota = rokbieacy0bo1==0?drugi.getKwota():drugi.getKwotabo();
                    if (znak.equals("-")) {
                        wynik -= kwota;
                    } else {
                        wynik += kwota;
                    }
                }
            }
        }
        return wynik;
    }
       public  String getStringBeforeLastSeparator(String input, String separator) {
        if (input == null || separator == null) {
            throw new IllegalArgumentException("Input and separator cannot be null");
        }
        
        int lastIndex = input.lastIndexOf(separator);
        if (lastIndex == -1) {
            return input; // Jeśli nie ma separatora, zwraca cały string
        }
        
        return input.substring(0, lastIndex);
    }

    private void pobierzPozycje(List<PozycjaRZiSBilans> pozycje) {
        try {
            pozycje.addAll(pozycjaRZiSDAO.findRzisuklad(uklad));
            if (pozycje.isEmpty()) {
               pozycje.add(new PozycjaRZiS(1, "A", "A", null, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
                Msg.msg("i", "Dodaje pusta pozycje");
            }
            for (Iterator<PozycjaRZiSBilans> it = pozycje.iterator(); it.hasNext();) {
                PozycjaRZiS p = (PozycjaRZiS) it.next();
                p.setPrzyporzadkowanestronywiersza(null);
                p.setKwota(0.0);
                p.setKwotabo(0.0);
            }
        } catch (Exception e) {  
            E.e(e);
        }
    }
    
    private void pobierzPozycjeAktywaPasywa(List<PozycjaRZiSBilans> pozycjeaktywa, List<PozycjaRZiSBilans> pozycjepasywa) {
        try {
            if (uklad.getUklad() == null) {
                uklad = ukladBRDAO.findukladBRPodatnikRokPodstawowy(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            }
            pozycjeaktywa.addAll(pozycjaBilansDAO.findBilansukladAktywa(uklad));
            pozycjepasywa.addAll(pozycjaBilansDAO.findBilansukladPasywa(uklad));
            if (pozycjeaktywa.isEmpty()) {
                pozycjeaktywa.add(new PozycjaBilans(1, "A", "A", null, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
                Msg.msg("i", "Dodaje pusta pozycje");
            }
            if (pozycjepasywa.isEmpty()) {
                pozycjepasywa.add(new PozycjaBilans(1, "A", "A", null, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
                Msg.msg("i", "Dodaje pusta pozycje");
            }
            UkladBRBean.czyscPozycje(pozycjeaktywa);
            UkladBRBean.czyscPozycje(pozycjepasywa);
        } catch (Exception e) {
            E.e(e);
        }
    }
    
  
    public boolean isLaczlata() {
        return laczlata;
    }

    public void setLaczlata(boolean laczlata) {
        this.laczlata = laczlata;
    }

    public String getBilansnadzien() {
        return bilansnadzien;
    }

    public void setBilansnadzien(String bilansnadzien) {
        this.bilansnadzien = bilansnadzien;
    }

    public String getBilansoddnia() {
        return bilansoddnia;
    }

    public void setBilansoddnia(String bilansoddnia) {
        this.bilansoddnia = bilansoddnia;
    }

    public List<PozycjaRZiSBilans> getPozycje() {
        return pozycje;
    }

    public void setPozycje(List<PozycjaRZiSBilans> pozycje) {
        this.pozycje = pozycje;
    }

    public UkladBR getUklad() {
        return uklad;
    }

    public void setUklad(UkladBR uklad) {
        this.uklad = uklad;
    }

    public List<PozycjaRZiSBilans> getSelectedNodes() {
        return selectedNodes;
    }

    public void setSelectedNodes(List<PozycjaRZiSBilans> selectedNodes) {
        this.selectedNodes = selectedNodes;
    }

    public String getOpisdodatkowy() {
        return opisdodatkowy;
    }

    public void setOpisdodatkowy(String opisdodatkowy) {
        this.opisdodatkowy = opisdodatkowy;
    }

    public List<PozycjaRZiSBilans> getRootBilansAktywa() {
        return rootBilansAktywa;
    }

    public void setRootBilansAktywa(List<PozycjaRZiSBilans> rootBilansAktywa) {
        this.rootBilansAktywa = rootBilansAktywa;
    }

    public List<PozycjaRZiSBilans> getRootBilansPasywa() {
        return rootBilansPasywa;
    }

    public void setRootBilansPasywa(List<PozycjaRZiSBilans> rootBilansPasywa) {
        this.rootBilansPasywa = rootBilansPasywa;
    }

    public double getSumabilansowaaktywa() {
        return sumabilansowaaktywa;
    }

    public void setSumabilansowaaktywa(double sumabilansowaaktywa) {
        this.sumabilansowaaktywa = sumabilansowaaktywa;
    }

    public double getSumabilansowaaktywaBO() {
        return sumabilansowaaktywaBO;
    }

    public void setSumabilansowaaktywaBO(double sumabilansowaaktywaBO) {
        this.sumabilansowaaktywaBO = sumabilansowaaktywaBO;
    }

    public double getSumabilansowapasywa() {
        return sumabilansowapasywa;
    }

    public void setSumabilansowapasywa(double sumabilansowapasywa) {
        this.sumabilansowapasywa = sumabilansowapasywa;
    }

    public double getSumabilansowapasywaBO() {
        return sumabilansowapasywaBO;
    }

    public void setSumabilansowapasywaBO(double sumabilansowapasywaBO) {
        this.sumabilansowapasywaBO = sumabilansowapasywaBO;
    }

    public String getKontabilansowebezprzyporzadkowania() {
        return kontabilansowebezprzyporzadkowania;
    }

    public void setKontabilansowebezprzyporzadkowania(String kontabilansowebezprzyporzadkowania) {
        this.kontabilansowebezprzyporzadkowania = kontabilansowebezprzyporzadkowania;
    }
    
    

}

