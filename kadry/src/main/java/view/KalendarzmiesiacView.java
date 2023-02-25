/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.KalendarzmiesiacBean;
import beanstesty.PasekwynagrodzenBean;
import dao.KalendarzmiesiacFacade;
import dao.KalendarzwzorFacade;
import dao.NieobecnoscFacade;
import dao.RodzajnieobecnosciFacade;
import embeddable.Mce;
import entity.Angaz;
import entity.Dzien;
import entity.Kalendarzmiesiac;
import entity.Kalendarzwzor;
import entity.Nieobecnosc;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class KalendarzmiesiacView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Kalendarzmiesiac selected;
    private List<Kalendarzmiesiac> listakalendarzeprac;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    @Inject
    private KalendarzwzorFacade kalendarzwzorFacade;
    @Inject
    private NieobecnoscFacade nieobecnoscFacade;
    @Inject
    private RodzajnieobecnosciFacade rodzajnieobecnosciFacade;
    @Inject
    private WpisView wpisView;
    private Dzien listaselected;
    
    
    public void init() {
        Kalendarzmiesiac szukany = null;
        if (wpisView.getAngaz()!=null) {
             szukany  = kalendarzmiesiacFacade.findByRokMcAngaz(wpisView.getAngaz(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
        }
        if (szukany==null) {
            selected = new Kalendarzmiesiac();
            selected.setRok(wpisView.getRokWpisu());
            selected.setMc(wpisView.getMiesiacWpisu());
        } else {
            selected  = kalendarzmiesiacFacade.findByRokMcAngaz(wpisView.getAngaz(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
            List<Dzien> dzienList = selected.getDzienList();
            for (Iterator<Dzien> it= dzienList.iterator();it.hasNext();) {
                Dzien dzien = it.next();
                if (dzien.getTypdnia()==-1) {
                    it.remove();
                }
            }
        }
        pobierzkalendarzeprac();
    }
        

    public void create() {
      if (selected!=null) {
          try {
            if (selected.getId()==null) {
                kalendarzmiesiacFacade.create(selected);
                if (!listakalendarzeprac.contains(selected)) {
                    listakalendarzeprac.add(selected);
                }
                Msg.msg("Dodano nowy kalendarz dla pracownika");
            } else {
                kalendarzmiesiacFacade.edit(selected);
                Msg.msg("Edytowano nowy kalendarz dla pracownika");
            }
          } catch (Exception e) {
              Msg.msg("e", "Błąd - nie dodano kalendarza dla pracownika");
          }
      }
    }
    
    public void usun() {
      if (selected!=null) {
          if (selected.getPasekwynagrodzenList()!=null&&selected.getPasekwynagrodzenList().size()>0) {
              Msg.msg("e","Na podstawie kalendarza sporządzono listę płac. Nie można usunąć");
          } else {
            try {
                kalendarzmiesiacFacade.remove(selected);
                selected = null;
                listakalendarzeprac = kalendarzmiesiacFacade.findByRokAngaz(wpisView.getAngaz(), wpisView.getRokWpisu());
                Msg.msg("Usunięto kalendarz");
            } catch (Exception e) {
            }
          }
      } else {
             Msg.msg("e", "Błąd - nie wybrano kalendarza dla pracownika");
      }
    }
    
    public void reset() {
      if (selected!=null) {
          try {
            Kalendarzwzor kalendarzwzor = kalendarzwzorFacade.findByFirmaRokMc(selected.getAngaz().getFirma(), selected.getRok(), selected.getMc());
            if (kalendarzwzor!=null) {
                Set<Nieobecnosc> nieobecnosci = new HashSet<>();
                for (Dzien p : selected.getDzienList()) {
                    Nieobecnosc nieobecnosc = p.getNieobecnosc();
                    if (nieobecnosc!=null) {
                        nieobecnosci.add(nieobecnosc);
                        p.setNieobecnosc(null);
                    }
                }
                for (Nieobecnosc s : nieobecnosci) {
                    s.setNaniesiona(false);
                    s.setDzienList(null);
                    nieobecnoscFacade.edit(s);
                }
                selected.nanies(kalendarzwzor);
                kalendarzmiesiacFacade.edit(selected);
                Msg.msg("Zresetowano kalendarz dla pracownika do waartości domyślnych");
            } else {
                Msg.msg("e", "Brak kalendarza wzorcowego za dany okres");
            }
          } catch (Exception e) {
              Msg.msg("e", "Błąd - nieudany reset kalendarza dla pracownika");
          }
      }
    }
    
    public void zrobkalendarzumowa() {
        if (selected.getAngaz()==null) {
            selected.setAngaz(wpisView.getAngaz());
        }
        if (selected!=null && selected.getAngaz()!=null) {
            if (selected.getRok()!=null&&selected.getMc()!=null) {
                String rok = selected.getRok();
                String mc = selected.getMc();
                Kalendarzmiesiac szukany = kalendarzmiesiacFacade.findByRokMcAngaz(selected.getAngaz(), rok, mc);
                if (szukany !=null) {
                    selected = szukany;
                    Msg.msg("Pobrano z bazy zachowany kalendarz");
                } else {
                    Kalendarzwzor znaleziono = kalendarzwzorFacade.findByFirmaRokMc(selected.getAngaz().getFirma(), rok, mc);
                    if (znaleziono!=null) {
                         Kalendarzmiesiac kal = new Kalendarzmiesiac();
                        kal.setRok(rok);
                        kal.setMc(mc);
                        kal.setAngaz(wpisView.getAngaz());
                        kal.ganerujdnizwzrocowego(znaleziono, null, selected.getAngaz().getEtatList());
                        Msg.msg("Pobrano dane z kalendarza wzorcowego z bazy danych");
                        create();
                        selected = kal;
                    } else {
                        KalendarzmiesiacBean.create(selected);
                        Msg.msg("Przygotowano kalendarz");
                    }
                    pobierzkalendarzeprac();
                }
            }
        } else {
                Msg.msg("e", "Błąd - nie wybrano firmy dla kalendarza");
        }
    }
    
    public void generujrok() {
        if (wpisView.getAngaz()!=null && wpisView.getPracownik()!=null) {
            String rok = wpisView.getRokWpisu();
            Integer rokI = Integer.parseInt(rok);
            String mcu = "01";
//            if (rokI<wpisView.getRokWpisuInt()) {
//                mcu = "01";
//            }
            Integer mcod = Integer.parseInt(mcu);
            Integer dzienod = 1;
            List<Kalendarzmiesiac> kalendarze = new ArrayList<>();
            for (String mc: Mce.getMceListS()) {
                Integer kolejnymc = Integer.parseInt(mc);
                if (kolejnymc>=mcod) {
                    Kalendarzmiesiac kal = new Kalendarzmiesiac();
                    kal.setRok(wpisView.getRokWpisu());
                    kal.setMc(mc);
                    kal.setAngaz(wpisView.getAngaz());
                    Kalendarzmiesiac kalmiesiac = kalendarzmiesiacFacade.findByRokMcAngaz(wpisView.getAngaz(), wpisView.getRokWpisu(), mc);
                    if (kalmiesiac==null) {
                        Kalendarzwzor znaleziono = kalendarzwzorFacade.findByFirmaRokMc(kal.getAngaz().getFirma(), kal.getRok(), mc);
                        if (znaleziono!=null) {
                            kal.ganerujdnizwzrocowego(znaleziono, dzienod, wpisView.getAngaz().getEtatList());
                            kalendarzmiesiacFacade.create(kal);
                            dzienod = null;
                            kalendarze.add(kal);
                        } else {
                            Msg.msg("e","Brak kalendarza wzorcowego za "+mc);
                        }
                    } else {
                        kalendarze.add(kalmiesiac);
                    }
                }
            }
            if (wpisView.getUmowa()!=null) {
                String mcu1 = wpisView.getAngaz().getMc();
                Kalendarzmiesiac kalendarz = kalendarze.stream().filter(p->p.getRok().equals(rok)&&p.getMc().equals(mcu1)).findFirst().get();
                List<Nieobecnosc> zatrudnieniewtrakciemiesiaca = PasekwynagrodzenBean.rozpoczecieumowywtrakcieMiesiaca(wpisView.getAngaz(), wpisView.getUmowa().getDataod(), wpisView.getUmowa().getDatado(),rodzajnieobecnosciFacade, rok, mcu1, kalendarz, null);
                if (zatrudnieniewtrakciemiesiaca!=null) {
                  nieobecnoscFacade.createList(zatrudnieniewtrakciemiesiaca);
                }
            }
            listakalendarzeprac = kalendarzmiesiacFacade.findByRokAngaz(wpisView.getAngaz(), wpisView.getRokWpisu());
            init();
            Msg.msg("Pobrano dane z kalendarza wzorcowego z bazy danych i utworzono kalendarze pracownika");
        } else {
            Msg.msg("e","Nie wybrano pracownika i umowy");
        }
    }
    
    
    
    public void zrobkal() {
        List<Angaz> angaze = wpisView.getFirma().getAngazList();
        for (Angaz angaz : angaze) {
            try {
                generujrok(angaz, "2023");
            } catch (Exception e){}
            //System.out.println("angaz "+angaz.getNazwiskoiImie());
        }
    }
    
    
    public void generujrok(Angaz angaz, String rok) {
        if (angaz!=null && wpisView.getPracownik()!=null) {
            Integer rokI = Integer.parseInt(rok);
            String mcu = "01";
            Integer mcod = Integer.parseInt(mcu);
            Integer dzienod = 1;
            List<Kalendarzmiesiac> kalendarze = new ArrayList<>();
            for (String mc: Mce.getMceListS()) {
                Integer kolejnymc = Integer.parseInt(mc);
                if (kolejnymc>=mcod) {
                    Kalendarzmiesiac kal = new Kalendarzmiesiac();
                    kal.setRok(rok);
                    kal.setMc(mc);
                    kal.setAngaz(angaz);
                    Kalendarzmiesiac kalmiesiac = kalendarzmiesiacFacade.findByRokMcAngaz(angaz, rok, mc);
                    if (kalmiesiac==null) {
                        Kalendarzwzor znaleziono = kalendarzwzorFacade.findByFirmaRokMc(kal.getAngaz().getFirma(), kal.getRok(), mc);
                        if (znaleziono!=null) {
                            kal.ganerujdnizwzrocowego(znaleziono, dzienod, angaz.getEtatList());
                            kalendarzmiesiacFacade.create(kal);
                            dzienod = null;
                            kalendarze.add(kal);
                        } else {
                            Msg.msg("e","Brak kalendarza wzorcowego za "+mc);
                        }
                    } else {
                        kalendarze.add(kalmiesiac);
                    }
                }
            }
//            if (wpisView.getUmowa()!=null) {
//                String mcu1 = angaz.getMc();
//                Kalendarzmiesiac kalendarz = kalendarze.stream().filter(p->p.getRok().equals(rok)&&p.getMc().equals(mcu1)).findFirst().get();
//                List<Nieobecnosc> zatrudnieniewtrakciemiesiaca = PasekwynagrodzenBean.rozpoczecieumowywtrakcieMiesiaca(angaz, wpisView.getUmowa().getDataod(), wpisView.getUmowa().getDatado(),rodzajnieobecnosciFacade, rok, mcu1, kalendarz, null);
//                if (zatrudnieniewtrakciemiesiaca!=null) {
//                  nieobecnoscFacade.createList(zatrudnieniewtrakciemiesiaca);
//                }
//            }
//            listakalendarzeprac = kalendarzmiesiacFacade.findByRokAngaz(angaz, wpisView.getRokWpisu());
//            init();
//            Msg.msg("Pobrano dane z kalendarza wzorcowego z bazy danych i utworzono kalendarze pracownika");
        } else {
            Msg.msg("e","Nie wybrano pracownika i umowy");
        }
    }
    
    public void pobierzkalendarzeprac() {
        if (wpisView.getAngaz()!=null && wpisView.getPracownik()!=null) {
            listakalendarzeprac = kalendarzmiesiacFacade.findByRokAngaz(wpisView.getAngaz(), selected.getRok());
            selected  = kalendarzmiesiacFacade.findByRokMcAngaz(wpisView.getAngaz(), selected.getRok(), selected.getMc());
            if (selected==null) {
                selected = new Kalendarzmiesiac();
                selected.setRok(wpisView.getRokWpisu());
                selected.setMc(wpisView.getMiesiacWpisu());
            } else {
                List<Dzien> dzienList = selected.getDzienList();
                for (Iterator<Dzien> it= dzienList.iterator();it.hasNext();) {
                    Dzien dzien = it.next();
                    if (dzien.getTypdnia()==-1) {
                        it.remove();
                    }
                }
            }
        } else {
            Msg.msg("e","Nie można pobrać kalendarza. Nie wybrano pracownika i umowy");
        }
    }
    
    public void pobierzkalendarz(String mc) {
        if (listakalendarzeprac!=null) {
            for (Kalendarzmiesiac p : listakalendarzeprac) {
                if (p.getMc().equals(mc)) {
                    this.selected = p;
                    List<Dzien> dzienList = selected.getDzienList();
                    for (Iterator<Dzien> it= dzienList.iterator();it.hasNext();) {
                        Dzien dzien = it.next();
                        if (dzien.getTypdnia()==-1) {
                            it.remove();
                        }
                    }
                    Msg.msg("Pobrano kalendarz");
                    break;
                }
            }
        }
    }
    
    public boolean czyjestkalendarz(String mc) {
        boolean zwrot = false;
        if (listakalendarzeprac!=null) {
            for (Kalendarzmiesiac p : listakalendarzeprac) {
                if (p.getMc().equals(mc)) {
                    zwrot = true;
                    break;
                }
            }
        }
        return zwrot;
    }
   
    public Kalendarzmiesiac getSelected() {
        return selected;
    }

    public void setSelected(Kalendarzmiesiac selected) {
        this.selected = selected;
    }


    public List<Kalendarzmiesiac> getListakalendarzeprac() {
        return listakalendarzeprac;
    }

    public void setListakalendarzeprac(List<Kalendarzmiesiac> listakalendarzeprac) {
        this.listakalendarzeprac = listakalendarzeprac;
    }

    public Dzien getListaselected() {
        return listaselected;
    }

    public void setListaselected(Dzien listaselected) {
        this.listaselected = listaselected;
    }

    
   
    
    
}
