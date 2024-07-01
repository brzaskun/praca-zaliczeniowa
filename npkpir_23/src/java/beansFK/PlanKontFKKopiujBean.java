/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import dao.KontoDAOfk;
import dao.PozycjaBilansDAO;
import dao.PozycjaRZiSDAO;
import entity.Podatnik;
import entityfk.Konto;
import entityfk.PozycjaBilans;
import entityfk.PozycjaRZiS;
import entityfk.UkladBR;
import error.E;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import msg.Msg;

/**
 *
 * @author Osito
 */
public class PlanKontFKKopiujBean {
    
    public static List<Konto> skopiujlevel0(KontoDAOfk kontoDAOfk, Podatnik podatnikDocelowy, List<Konto> wykazkont, String rokDocelowy) {
        List<Konto> macierzyste = new ArrayList<>();
        for (Iterator<Konto> it= wykazkont.iterator(); it.hasNext();) {
            Konto p = it.next();
            if (p.getLevel() == 0) {
                Konto r = serialclone.SerialClone.clone(p);
                r.setPodatnik(podatnikDocelowy);
                r.setRok(Integer.parseInt(rokDocelowy));
                r.setSprawdzono(null);
                zeruDanekontaBO(r);
                macierzyste.add(r);
                kontoDAOfk.create(r);
            }
        }
        return macierzyste;
    }
    
     public static List<Konto> skopiujlevelWzorcowy(KontoDAOfk kontoDAOfk, Podatnik docelowy, List<Konto> wykazkont, List<Konto> macierzystelista, int i, String rokdocelowy) {
        List<Konto> nowemacierzyste = Collections.synchronizedList(new ArrayList<>());
        for (Konto p : wykazkont) {
            if (p.getLevel() == i) {
                try {
                    Konto r = serialclone.SerialClone.clone(p);
                    r.setPodatnik(docelowy);
                    r.setRok(Integer.parseInt(rokdocelowy));
                    Konto macierzyste = PlanKontFKKopiujBean.wyszukajmacierzyste(r.getKontomacierzyste().getPelnynumer(), macierzystelista);
                    r.setMacierzysty(macierzyste.getId());
                    r.setKontomacierzyste(macierzyste);
                    kontoDAOfk.create(r);
                    nowemacierzyste.add(r);
                } catch (Exception e) {
                    E.e(e);

                }
            }
        }
        return nowemacierzyste;
    }
    
    private static void zeruDanekontaBO(Konto p) {
        try {
            p.setId(null);
            p.setBoWn(0);
            p.setBoMa(0);
            p.setObrotyWn(0);
            p.setObrotyMa(0);
            p.czyscPozycje();
        } catch (Exception e) {
            E.e(e);
            error.E.s("");
        }
    }

    public static List<Konto> skopiujlevel(KontoDAOfk kontoDAOfk, Podatnik podatnikzrodlowy, Podatnik podatnikDocelowy, List<Konto> wykazkont, List<Konto> macierzystelista, int biezacylevel, String rokdocelowy, boolean kopiujSlownikowe) {
        List<Konto> nowemacierzyste = new ArrayList<>();
        for (Iterator<Konto> it= wykazkont.iterator(); it.hasNext();) {
            Konto p = it.next();
            if (biezacylevel==2) {
                error.E.s("");
            }
            if (p.getLevel() == biezacylevel) {
                try {
                    if (!podatnikzrodlowy.equals(podatnikDocelowy) && p.isSlownikowe()) {
                        //??
                    } else if (p.isSlownikowe() == true && kopiujSlownikowe) {
                        Konto noweslownikowe = kopiujKonto(p, macierzystelista, podatnikDocelowy, true, rokdocelowy);
                        if (noweslownikowe!=null) {
                            nowemacierzyste.add(noweslownikowe);
                            it.remove();
                        }
                    } else if (p.isSlownikowe() == false) {
                        nowemacierzyste.add(kopiujKonto(p, macierzystelista, podatnikDocelowy, false, rokdocelowy));
                        it.remove();
                    }
                } catch (Exception e) {
                    E.e(e);
                }
            }
        }
        if (!nowemacierzyste.isEmpty()) {
            kontoDAOfk.createList(nowemacierzyste);
        }
        return nowemacierzyste;
    }
    
    private static Konto kopiujKonto(Konto p, List<Konto> macierzystelista, Podatnik podatnikDocelowy, boolean slownikowe, String rokdocelowy) {
        Konto r = serialclone.SerialClone.clone(p);
        Konto macierzyste = wyszukajmacierzyste(r.getKontomacierzyste().getPelnynumer(), macierzystelista);
        if (macierzyste.getIdslownika()!= 5 && macierzyste.getIdslownika()!= 6) {
            zeruDanekontaBO(r);
            r.setPodatnik(podatnikDocelowy);
            r.setRok(Integer.parseInt(rokdocelowy));
            r.setSlownikowe(slownikowe);
            r.setMacierzysty(macierzyste.getId());
            r.setKontomacierzyste(macierzyste);
            r.setSprawdzono(null);
        } else {
            r=null;
        }
        return r;
    }
    
    public static Konto wyszukajmacierzyste(String macierzyste, List<Konto> macierzystelista) {
        for (Konto p : macierzystelista) {
            if (p.getPelnynumer().equals(macierzyste)) {
                return p;
            }
        }
        return null;
    }
    
    public static void implementujRZiS(PozycjaRZiSDAO pozycjaRZiSDAO, UkladBR ukladBR, String podatnik, String rok, String uklad) {
        List<PozycjaRZiS> pozycje = pozycjaRZiSDAO.findRzisuklad(ukladBR);
        List<PozycjaRZiS> pozycjenowe = Collections.synchronizedList(new ArrayList<>());
        if (pozycje != null && pozycje.size() > 0) {
            List<PozycjaRZiS> macierzyste = skopiujlevel0(pozycje, podatnik, rok, uklad);
            pozycjenowe.addAll(macierzyste);
            int maxlevel = pozycjaRZiSDAO.findMaxLevelPodatnik(ukladBR);
            for (int i = 1; i <= maxlevel; i++) {
                macierzyste = skopiujlevel(pozycje, macierzyste, i, podatnik, rok, uklad);
                pozycjenowe.addAll(macierzyste);
            }
            pozycjaRZiSDAO.createList(pozycjenowe);
        } else {
            Msg.msg("e", "Brak pozycji RZiS przyporządkowanych do wybranego układu");
        }
    }
    
     private static List<PozycjaRZiS> skopiujlevel0(List<PozycjaRZiS> pozycje, String podatnik, String rok, String uklad) {
        List<PozycjaRZiS> macierzyste = Collections.synchronizedList(new ArrayList<>());
        for (PozycjaRZiS p : pozycje) {
            if (p.getLevel() == 0) {
                PozycjaRZiS r = serialclone.SerialClone.clone(p);
                r.setPodatnik(podatnik);
                r.setRok(rok);
                r.setUklad(uklad);
                r.setPrzyporzadkowanekonta(null);
                r.setPrzyporzadkowanestronywiersza(null);
                macierzyste.add(r);
            }
        }
        return macierzyste;
    }
     
      private static List<PozycjaRZiS> skopiujlevel(List<PozycjaRZiS> pozycje, List<PozycjaRZiS> macierzystelista, int i, String podatnik, String rok, String uklad) {
        List<PozycjaRZiS> nowemacierzyste = Collections.synchronizedList(new ArrayList<>());
        for (PozycjaRZiS p : pozycje) {
            if (p.getLevel() == i) {
                try {
                    PozycjaRZiS r = serialclone.SerialClone.clone(p);
                    r.setPodatnik(podatnik);
                    r.setRok(rok);
                    r.setUklad(uklad);
                    r.setPrzyporzadkowanekonta(null);
                    r.setPrzyporzadkowanestronywiersza(null);
                    PozycjaRZiS macierzyste = wyszukajmacierzyste(p, macierzystelista);
                    r.setMacierzysta(macierzyste);
                    nowemacierzyste.add(r);
                } catch (Exception e) {
                }
            }
        }
        return nowemacierzyste;
    }

    private static PozycjaRZiS wyszukajmacierzyste(PozycjaRZiS macierzyste, List<PozycjaRZiS> macierzystelista) {
        PozycjaRZiS mac = macierzyste.getMacierzysta();
        for (PozycjaRZiS p : macierzystelista) {
            if (p.getNazwa().equals(mac.getNazwa()) && p.getPozycjaString().equals(mac.getPozycjaString())) {
                return p;
            }
        }
        return null;
    }
    
    public static void implementujBilans(PozycjaBilansDAO pozycjaBilansDAO, UkladBR ukladBR, String podatnik, String rok, String uklad) {
        List<PozycjaBilans> pozycje = pozycjaBilansDAO.findBilansukladAktywa(ukladBR);
        List<PozycjaBilans> pozycjenowe = Collections.synchronizedList(new ArrayList<>());
        if (pozycje != null && pozycje.size() > 0) {
            List<PozycjaBilans> macierzyste = skopiujlevel0B(pozycje, podatnik, rok, uklad);
            pozycjenowe.addAll(macierzyste);
            int maxlevel = pozycjaBilansDAO.findMaxLevelPodatnikAktywa(ukladBR);
            for (int i = 1; i <= maxlevel; i++) {
                macierzyste = skopiujlevelB(pozycje, macierzyste, i, podatnik, rok, uklad);
                pozycjenowe.addAll(macierzyste);
            }
            pozycjaBilansDAO.createList(pozycjenowe);
        }
        pozycje = pozycjaBilansDAO.findBilansukladPasywa(ukladBR);
        pozycjenowe = Collections.synchronizedList(new ArrayList<>());
        if (pozycje != null && pozycje.size() > 0) {
            List<PozycjaBilans> macierzyste = skopiujlevel0B(pozycje, podatnik, rok, uklad);
            pozycjenowe.addAll(macierzyste);
            int maxlevel = pozycjaBilansDAO.findMaxLevelPodatnikPasywa(ukladBR);
            for (int i = 1; i <= maxlevel; i++) {
                macierzyste = skopiujlevelB(pozycje, macierzyste, i, podatnik, rok, uklad);
                pozycjenowe.addAll(macierzyste);
            }
            pozycjaBilansDAO.createList(pozycjenowe);
        } else {
            Msg.msg("e", "Brak pozycji bilansu przyporządkowanych do wybranego układu");
        }
    }
    private static List<PozycjaBilans> skopiujlevel0B(List<PozycjaBilans> pozycje, String podatnik, String rok, String uklad) {
        List<PozycjaBilans> macierzyste = Collections.synchronizedList(new ArrayList<>());
        for (PozycjaBilans p : pozycje) {
            if (p.getLevel() == 0) {
                PozycjaBilans r = serialclone.SerialClone.clone(p);
                r.setPodatnik(podatnik);
                r.setRok(rok);
                r.setUklad(uklad);
//                try {
//                    pozycjaRZiSDAO.create(r);
//                } catch (Exception e) {
//                    error.E.s("Blad " + e.getStackTrace()[0].toString());
//
//                }
                macierzyste.add(r);
            }
        }
        return macierzyste;
    }

    private static List<PozycjaBilans> skopiujlevelB(List<PozycjaBilans> pozycje, List<PozycjaBilans> macierzystelista, int i, String podatnik, String rok, String uklad) {
        List<PozycjaBilans> nowemacierzyste = Collections.synchronizedList(new ArrayList<>());
        for (PozycjaBilans p : pozycje) {
            if (p.getLevel() == i) {
                try {
                    PozycjaBilans r = serialclone.SerialClone.clone(p);
                    r.setPodatnik(podatnik);
                    r.setRok(rok);
                    r.setUklad(uklad);
                    PozycjaBilans macierzyste = wyszukajmacierzysteB(p, macierzystelista);
                    r.setMacierzysta(macierzyste);
                    nowemacierzyste.add(r);
                } catch (Exception e) {
                }
            }
        }
        return nowemacierzyste;
    }

    private static PozycjaBilans wyszukajmacierzysteB(PozycjaBilans macierzyste, List<PozycjaBilans> macierzystelista) {
        PozycjaBilans mac = macierzyste.getMacierzysta();
        for (PozycjaBilans p : macierzystelista) {
            if (p.getNazwa().equals(mac.getNazwa()) && p.getPozycjaString().equals(mac.getPozycjaString())) {
                return p;
            }
        }
        return null;
    }
}
