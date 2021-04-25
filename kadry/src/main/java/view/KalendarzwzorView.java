/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.KalendarzWzorBean;
import dao.FirmaFacade;
import dao.KalendarzmiesiacFacade;
import dao.KalendarzwzorFacade;
import dao.UmowaFacade;
import embeddable.Mce;
import entity.FirmaKadry;
import entity.Kalendarzmiesiac;
import entity.Kalendarzwzor;
import entity.Umowa;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
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
public class KalendarzwzorView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Kalendarzwzor selected;
    @Inject
    private Kalendarzwzor selectedlista;
    private List<Kalendarzwzor> lista;
    private List<FirmaKadry> listafirm;
    @Inject
    private KalendarzwzorFacade kalendarzwzorFacade;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    @Inject
    private FirmaFacade firmaFacade;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    private void init() {
        selected.setFirma(wpisView.getFirma());
        selected.setRok(wpisView.getRokWpisu());
        lista  = kalendarzwzorFacade.findByFirmaRok(wpisView.getFirma(), wpisView.getRokWpisu());
        listafirm = firmaFacade.findAll();
    }
    
    public void init2() {
        lista  = kalendarzwzorFacade.findByFirmaRok(selected.getFirma(), selected.getRok());
    }

    public void create() {
      if (selected!=null) {
          try {
            kalendarzwzorFacade.create(selected);
            lista.add(selected);
            selected = new Kalendarzwzor(selected.getFirma(), selected.getRok());
            Msg.msg("Dodano nowy kalendarz");
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie dodano nowej firmy");
          }
      }
    }
    
    public void edit() {
      if (selected!=null) {
          try {
            kalendarzwzorFacade.edit(selected);
            Msg.msg("Edytowano kalendarz");
          } catch (Exception e) {
              System.out.println("");
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
            kalendarzwzorFacade.edit(selected);
            Msg.msg("Zresetowano kalendarz");
          } catch (Exception e) {
              System.out.println("");
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
                        FirmaKadry firma = selected.getFirma();
                        selected = new Kalendarzwzor(firma, rok, mc);
                        String[] popokres = data.Data.poprzedniOkres(mc, rok);
                        Kalendarzwzor poprzedni = kalendarzwzorFacade.findByFirmaRokMc(selected.getFirma(), popokres[1], popokres[0]);
                        KalendarzWzorBean.create(selected);
                        selected.zrobkolejnedni(poprzedni);
                        Msg.msg("Przygotowano kalendarz");
                    }
                } catch (Exception e){}
            }
        } else {
            Msg.msg("e", "Błąd - nie wybrano firmy dla kalendarza");
        }
    }
    
      public void globalnie() {
        if (wpisView.getFirma()!=null && wpisView.getRokWpisu()!=null) {
            FirmaKadry firmaglobalna = firmaFacade.findByNIP("8511005008");
            for (String mce: Mce.getMceListS()) {
                Kalendarzwzor kal = new Kalendarzwzor();
                kal.setRok(wpisView.getRokWpisu());
                kal.setMc(mce);
                kal.setFirma(wpisView.getFirma());
                Kalendarzwzor kalmiesiac = kalendarzwzorFacade.findByFirmaRokMc(wpisView.getFirma(), kal.getRok(), mce);
                if (kalmiesiac==null) {
                    Kalendarzwzor znaleziono = kalendarzwzorFacade.findByFirmaRokMc(firmaglobalna, kal.getRok(), mce);
                    if (znaleziono!=null) {
                        kal.generujdnizglobalnego(znaleziono);
                        kalendarzwzorFacade.create(kal);
                    } else {
                        Msg.msg("e","Brak kalendarza globalnego za "+mce);
                    }
                }
            }
             lista  = kalendarzwzorFacade.findByFirmaRok(wpisView.getFirma(), wpisView.getRokWpisu());
            Msg.msg("Pobrano dane z kalendarza globalnego z bazy danych i utworzono kalendarz wzorcowy firmy");
        } else {
            Msg.msg("e","Nie wybrano firmy");
        }
    }
     
    public void nanieszmiany() {
        if (wpisView.getFirma()!=null && wpisView.getRokWpisu()!=null) {
            FirmaKadry firma = wpisView.getFirma();
            List<Kalendarzmiesiac> kalendarzepracownikow = kalendarzmiesiacFacade.findByFirmaRokMc(firma, selected.getRok(), selected.getMc());
            for (Kalendarzmiesiac kal: kalendarzepracownikow) {
                Kalendarzwzor znaleziono = kalendarzwzorFacade.findByFirmaRokMc(firma, selected.getRok(), selected.getMc());
                kal.edytujdnizglobalnego(znaleziono);
                kalendarzmiesiacFacade.edit(kal);
            }
            Msg.msg("Naniesiono zmiany na kalendarze pracowników");
        } else {
            Msg.msg("e","Nie wybrano firmy");
        }
    }
      
    
    public void implementuj() {
        if (selected!=null) {
            List<Umowa> listaumowa = umowaFacade.findPracownikFirma(wpisView.getPracownik(), wpisView.getFirma());
            for (Umowa u : listaumowa) {
                if (u.nalezydomiesiaca(selected.getRok(), selected.getMc())) {
                    Kalendarzwzor znaleziono = kalendarzwzorFacade.findByFirmaRokMc(u.getAngaz().getFirma(), selected.getRok(), selected.getMc());
                    if (znaleziono!=null) {
                        Kalendarzmiesiac kalendarzmiesiac = new Kalendarzmiesiac();
                        kalendarzmiesiac.setRok(selected.getRok());
                        kalendarzmiesiac.setMc(selected.getMc());
                        kalendarzmiesiac.setUmowa(u);
                        kalendarzmiesiac.ganerujdnizwzrocowego(znaleziono, null);
                    } 
                }
            }
            Msg.msg("Zaimplantowano kalendarz wzorcowy u wszystkich");
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

    public List<FirmaKadry> getListafirm() {
        return listafirm;
    }

    public void setListafirm(List<FirmaKadry> listafirm) {
        this.listafirm = listafirm;
    }

   
    
    
}
