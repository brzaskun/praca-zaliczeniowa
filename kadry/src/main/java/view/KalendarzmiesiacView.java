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
import dao.UmowaFacade;
import data.Data;
import embeddable.Mce;
import entity.Dzien;
import entity.Kalendarzmiesiac;
import entity.Kalendarzwzor;
import entity.Nieobecnosc;
import entity.Umowa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
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
    private List<Umowa> listaumowa;
    private List<Kalendarzmiesiac> listakalendarzeprac;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    @Inject
    private KalendarzwzorFacade kalendarzwzorFacade;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private NieobecnoscFacade nieobecnoscFacade;
    @Inject
    private RodzajnieobecnosciFacade rodzajnieobecnosciFacade;
    @Inject
    private WpisView wpisView;
    
    
    public void init() {
        Kalendarzmiesiac szukany = null;
        if (wpisView.getUmowa()!=null) {
             szukany  = kalendarzmiesiacFacade.findByRokMcUmowa(wpisView.getUmowa(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
        }
        if (szukany==null) {
            selected.setRok(wpisView.getRokWpisu());
            selected.setMc(wpisView.getMiesiacWpisu());
        } else {
            selected  = kalendarzmiesiacFacade.findByRokMcUmowa(wpisView.getUmowa(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
        }
        listaumowa = new ArrayList<>();
        try {
            listaumowa.add(umowaFacade.findPracownikFirmaJeden(wpisView.getPracownik(), wpisView.getFirma()));
        } catch (Exception e){}
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
            wpisView.setRokWpisu(selected.getRok());
            wpisView.setMiesiacWpisu(selected.getMc());
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
                listakalendarzeprac = kalendarzmiesiacFacade.findByRokUmowa(wpisView.getUmowa(), wpisView.getRokWpisu());
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
            Kalendarzwzor kalendarzwzor = kalendarzwzorFacade.findByFirmaRokMc(selected.getUmowa().getAngaz().getFirma(), selected.getRok(), selected.getMc());
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
        if (selected.getUmowa()==null) {
            selected.setUmowa(wpisView.getUmowa());
        }
        if (selected!=null && selected.getUmowa()!=null) {
            if (selected.getRok()!=null&&selected.getMc()!=null) {
                Kalendarzmiesiac szukany = kalendarzmiesiacFacade.findByRokMcUmowa(selected.getUmowa(), selected.getRok(), selected.getMc());
                if (szukany !=null) {
                    selected = kalendarzmiesiacFacade.findByRokMcUmowa(selected.getUmowa(), selected.getRok(), selected.getMc());
                    Msg.msg("Pobrano z bazy zachowany kalendarz");
                } else {
                    Kalendarzwzor znaleziono = kalendarzwzorFacade.findByFirmaRokMc(selected.getUmowa().getAngaz().getFirma(), selected.getRok(), selected.getMc());
                    if (znaleziono!=null) {
                        selected.ganerujdnizwzrocowego(znaleziono, null, selected.getUmowa().getEtatList());
                        Msg.msg("Pobrano dane z kalendarza wzorcowego z bazy danych");
                    } else {
                        KalendarzmiesiacBean.create(selected);
                        Msg.msg("Przygotowano kalendarz");
                    }
                }
            }
        } else {
                Msg.msg("e", "Błąd - nie wybrano firmy dla kalendarza");
        }
    }
    
    public void generujrok() {
        if (wpisView.getAngaz()!=null && wpisView.getPracownik()!=null && wpisView.getUmowa()!=null) {
            String rok = Data.getRok(wpisView.getUmowa().getDataod());
            String mcu = Data.getMc(wpisView.getUmowa().getDataod());
            Integer mcod = Integer.parseInt(Data.getMc(wpisView.getUmowa().getDataod()));
            Integer dzienod = Integer.parseInt(Data.getDzien(wpisView.getUmowa().getDataod()));
            List<Kalendarzmiesiac> kalendarze = new ArrayList<>();
            for (String mc: Mce.getMceListS()) {
                Integer kolejnymc = Integer.parseInt(mc);
                if (kolejnymc>=mcod) {
                    Kalendarzmiesiac kal = new Kalendarzmiesiac();
                    kal.setRok(wpisView.getRokWpisu());
                    kal.setMc(mc);
                    kal.setUmowa(wpisView.getUmowa());
                    Kalendarzmiesiac kalmiesiac = kalendarzmiesiacFacade.findByRokMcUmowa(wpisView.getUmowa(), wpisView.getRokWpisu(), mc);
                    if (kalmiesiac==null) {
                        Kalendarzwzor znaleziono = kalendarzwzorFacade.findByFirmaRokMc(kal.getUmowa().getAngaz().getFirma(), kal.getRok(), mc);
                        if (znaleziono!=null) {
                            kal.ganerujdnizwzrocowego(znaleziono, dzienod, selected.getUmowa().getEtatList());
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
            Kalendarzmiesiac kalendarz = kalendarze.stream().filter(p->p.getRok().equals(rok)&&p.getMc().equals(mcu)).findFirst().get();
            List<Nieobecnosc> zatrudnieniewtrakciemiesiaca = PasekwynagrodzenBean.generuj(wpisView.getUmowa(),rodzajnieobecnosciFacade, rok, mcu, kalendarz);
            if (zatrudnieniewtrakciemiesiaca!=null) {
              nieobecnoscFacade.createList(zatrudnieniewtrakciemiesiaca);
            }
            listakalendarzeprac = kalendarzmiesiacFacade.findByRokUmowa(wpisView.getUmowa(), wpisView.getRokWpisu());
            Msg.msg("Pobrano dane z kalendarza wzorcowego z bazy danych i utworzono kalendarze pracownika");
        } else {
            Msg.msg("e","Nie wybrano pracownika i umowy");
        }
    }
    
    public void pobierzkalendarzeprac() {
        if (wpisView.getAngaz()!=null && wpisView.getPracownik()!=null && wpisView.getUmowa()!=null) {
            listakalendarzeprac = kalendarzmiesiacFacade.findByRokUmowa(wpisView.getUmowa(), wpisView.getRokWpisu());
        } else {
            Msg.msg("e","Nie mozna pobrac kalendarza. Nie wybrano pracownika i umowy");
        }
    }
    
    public void pobierzkalendarz(String mc) {
        if (listakalendarzeprac!=null) {
            for (Kalendarzmiesiac p : listakalendarzeprac) {
                if (p.getMc().equals(mc)) {
                    this.selected = p;
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


    public List<Umowa> getListaumowa() {
        return listaumowa;
    }

    public void setListaumowa(List<Umowa> listaumowa) {
        this.listaumowa = listaumowa;
    }

    public List<Kalendarzmiesiac> getListakalendarzeprac() {
        return listakalendarzeprac;
    }

    public void setListakalendarzeprac(List<Kalendarzmiesiac> listakalendarzeprac) {
        this.listakalendarzeprac = listakalendarzeprac;
    }

    
   
    
    
}
