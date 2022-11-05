/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.KalendarzWzorBean;
import beanstesty.KalendarzmiesiacBean;
import dao.FirmaKadryFacade;
import dao.KalendarzmiesiacFacade;
import dao.KalendarzwzorFacade;
import dao.UmowaFacade;
import entity.Dzien;
import entity.FirmaKadry;
import entity.Kalendarzmiesiac;
import entity.Kalendarzwzor;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import z.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class KalendarzGlobalnyView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Kalendarzwzor selected;
    @Inject
    private Kalendarzwzor selectedlista;
    private List<Kalendarzwzor> lista;
    @Inject
    private KalendarzwzorFacade kalendarzwzorFacade;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    @Inject
    private FirmaKadryFacade firmaFacade;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private WpisView wpisView;
    private FirmaKadry firmaglobalna;
    
    @PostConstruct
    public void init() {
        firmaglobalna = firmaFacade.findByNIP("0000000000");
         Kalendarzwzor znaleziono = kalendarzwzorFacade.findByFirmaRokMc(wpisView.getFirma(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
        if (znaleziono!=null) {
            selected = znaleziono;
            Msg.msg("Pobrano kalendarz z bazy danych");
        } else {
            selected.setFirma(firmaglobalna);
            selected.setRok(wpisView.getRokWpisu());
            selected.setMc(wpisView.getMiesiacWpisu());
        }
        lista  = kalendarzwzorFacade.findByFirmaRok(firmaglobalna, wpisView.getRokWpisu());
        
    }
    
    public void init2() {
        lista  = kalendarzwzorFacade.findByFirmaRok(selected.getFirma(), selected.getRok());
    }

    public void create() {
      if (selected!=null) {
          try {
            selected.setNorma(0.0);
            for (Dzien p : selected.getDzienList()) {
                selected.setNorma(Z.z(selected.getNorma()+p.getNormagodzin()));
            }
            kalendarzwzorFacade.create(selected);
            lista.add(selected);
            selected = new Kalendarzwzor(selected.getFirma(), selected.getRok());
            Msg.msg("Dodano nowy kalendarz");
          } catch (Exception e) {
              Msg.msg("e", "Błąd - nie dodano nowej firmy");
          }
      }
    }
    
    public void edit() {
      if (selected!=null) {
          try {
            selected.setNorma(0.0);
            for (Dzien p : selected.getDzienList()) {
                selected.setNorma(Z.z(selected.getNorma()+p.getNormagodzin()));
            }
            kalendarzwzorFacade.edit(selected);
            Msg.msg("Edytowano kalendarz");
          } catch (Exception e) {
              Msg.msg("e", "Błąd - nie zachowano zmian kalendarza");
          }
      }
    }
    
    public void reset() {
      if (selected!=null) {
          try {
            String[] popokres = data.Data.poprzedniOkres(selected.getMc(), selected.getRok());
            Kalendarzwzor poprzedni = kalendarzwzorFacade.findByFirmaRokMc(selected.getFirma(), popokres[1], popokres[0]);
            selected.zrobkolejnedni(poprzedni);
            selected.setNorma(0.0);
            for (Dzien p : selected.getDzienList()) {
                selected.setNorma(Z.z(selected.getNorma()+p.getNormagodzin()));
            }
            kalendarzwzorFacade.edit(selected);
            Msg.msg("Zresetowano kalendarz");
          } catch (Exception e) {
              Msg.msg("e", "Błąd - nie zachowano zmian kalendarza");
          }
      }
    }
    
    public void zrobkalendarzumowa() {
            if (selected!=null && selected.getFirma()!=null) {
            if (selected.getRok()!=null&&selected.getMc()!=null) {
                try {
                    Kalendarzwzor znaleziono = kalendarzwzorFacade.findByFirmaRokMc(selected.getFirma(), selected.getRok(), selected.getMc());
                    if (znaleziono!=null) {
                        selected = znaleziono;
                        Msg.msg("Pobrano kalendarz z bazy danych");
                    } else {
                        String mc = selected.getMc();
                        String rok = selected.getRok();
                        selected = new Kalendarzwzor(firmaglobalna, rok, mc);
                        String[] popokres = data.Data.poprzedniOkres(mc, rok);
                        Kalendarzwzor poprzedni = kalendarzwzorFacade.findByFirmaRokMc(firmaglobalna, popokres[1], popokres[0]);
                        KalendarzWzorBean.dodajdnidokalendarza(selected);
                        selected.zrobkolejnedni(poprzedni);
                        Msg.msg("Przygotowano kalendarz");
                    }
                } catch (Exception e){}
            }
        } else {
            Msg.msg("e", "Błąd - nie wybrano firmy dla kalendarza");
        }
    }
    
    
     
    public void nanieszmiany() {
        if (selected!=null && selected.getId()!=null) {
            List<Kalendarzwzor> kalendarzewzorcowe = kalendarzwzorFacade.findByRokMc(selected.getRok(), selected.getMc());
            for (Kalendarzwzor kal: kalendarzewzorcowe) {
                kal.edytujdnizglobalnego(selected);
                kalendarzwzorFacade.edit(kal);
            }
            Msg.msg("Naniesiono zmiany na kalendarze pracowników");
        } else {
            Msg.msg("e","nie wybrnao kalendarza, kalendarz nie zachowany w bazie");
        }
    }
   
    
    
    public Kalendarzwzor getSelected() {
        return selected;
    }

    public void setSelected(Kalendarzwzor selected) {
        this.selected = selected;
    }

    public List<Kalendarzwzor> getLista() {
        return lista;
    }

    public void setLista(List<Kalendarzwzor> lista) {
        this.lista = lista;
    }

    public Kalendarzwzor getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Kalendarzwzor selectedlista) {
        this.selectedlista = selectedlista;
    }

    
    public void uzupelnijkalendarze() {
        List<Kalendarzwzor> lista = kalendarzwzorFacade.findAll();
        for (Kalendarzwzor p : lista) {
            KalendarzWzorBean.dodajdnidokalendarza(p);
            kalendarzwzorFacade.edit(p);
        }
        List<Kalendarzmiesiac> lista1 = kalendarzmiesiacFacade.findAll();
        for (Kalendarzmiesiac p : lista1) {
            KalendarzmiesiacBean.reset(p);
            kalendarzwzorFacade.edit(p);
        }
    }
    
    
}

