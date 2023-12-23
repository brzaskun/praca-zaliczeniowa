/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.KalendarzWzorBean;
import beanstesty.KalendarzmiesiacBean;
import dao.DzienFacade;
import dao.FirmaKadryFacade;
import dao.KalendarzmiesiacFacade;
import dao.KalendarzwzorFacade;
import dao.UmowaFacade;
import data.Data;
import embeddable.Mce;
import entity.Dzien;
import entity.FirmaKadry;
import entity.Kalendarzmiesiac;
import entity.Kalendarzwzor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
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
    private DzienFacade dzienFacade;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    @Inject
    private FirmaKadryFacade firmaFacade;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private WpisView wpisView;
    private FirmaKadry firmaglobalna;
    private String rok;
    private String mc;
    
    @PostConstruct
    public void init() {
        firmaglobalna = firmaFacade.findByNIP("0000000000");
         Kalendarzwzor znaleziono = kalendarzwzorFacade.findByFirmaRokMc(firmaglobalna, wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
        if (znaleziono!=null) {
            selected = znaleziono;
            Msg.msg("Pobrano kalendarz z bazy danych");
        } else {
            selected.setFirma(firmaglobalna);
            selected.setRok(wpisView.getRokWpisu());
            selected.setMc(wpisView.getMiesiacWpisu());
        }
        lista  = kalendarzwzorFacade.findByFirmaRok(firmaglobalna, wpisView.getRokWpisu());
        rok = wpisView.getRokWpisu();
        mc = wpisView.getMiesiacWpisu();
    }
    
    public void init2() {
        firmaglobalna = firmaFacade.findByNIP("0000000000");
        lista  = kalendarzwzorFacade.findByFirmaRok(firmaglobalna, selected.getRok());
    }

    public void create() {
      if (selected!=null) {
          try {
            selected.setNorma(0.0);
            for (Dzien p : selected.getDzienList()) {
                selected.setNorma(Z.z(selected.getNorma()+p.getNormagodzin()));
            }
            kalendarzwzorFacade.create(selected);
            //on juz jest naliscie
//            if (lista==null) {
//                lista = new ArrayList<>();
//            }
//            lista.add(selected);
 //           selected = new Kalendarzwzor();
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
        if (selected != null && selected.getFirma() != null) {
            if (selected.getRok() != null && selected.getMc() != null) {
                try {
                    Kalendarzwzor znaleziono = kalendarzwzorFacade.findByFirmaRokMc(selected.getFirma(), selected.getRok(), selected.getMc());
                    if (znaleziono != null) {
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
                } catch (Exception e) {
                }
            }
        } else {
            Msg.msg("e", "Błąd - nie wybrano firmy dla kalendarza");
        }
    }
    
   
    public void zrobkalendarzumowaglobalny() {
        firmaglobalna = firmaFacade.findByNIP("0000000000");
        selected = new Kalendarzwzor();
        selected.setFirma(firmaglobalna);
        if (rok != null && mc!=null) {
            selected.setRok(rok);
            selected.setMc(mc);
            try {
                Kalendarzwzor znaleziono = kalendarzwzorFacade.findByFirmaRokMc(firmaglobalna, selected.getRok(), selected.getMc());
                if (znaleziono != null) {
                    selected = znaleziono;
                    Msg.msg("Pobrano kalendarz z bazy danych");
                } else {
                    selected = new Kalendarzwzor(firmaglobalna, rok, mc);
                    String[] popokres = data.Data.poprzedniOkres(mc, rok);
                    Kalendarzwzor poprzedni = kalendarzwzorFacade.findByFirmaRokMc(firmaglobalna, popokres[1], popokres[0]);
                    KalendarzWzorBean.dodajdnidokalendarza(selected);
                    selected.zrobkolejnedni(poprzedni);
                    if (lista==null) {
                        lista = new ArrayList<>();
                    }
                    lista.add(selected);
                    Msg.msg("Przygotowano kalendarz");
                }
            } catch (Exception e) {
            }
        } else {
            Msg.msg("e", "Błąd - nie wybrano firmy dla kalendarza");
        }
    }
    
    
     
    public void nanieszmiany() {
        if (selected!=null && selected.getId()!=null) {
            List<Kalendarzwzor> kalendarzewzorcowe = kalendarzwzorFacade.findByRokMc(selected.getRok(), selected.getMc());
            for (Kalendarzwzor kal: kalendarzewzorcowe) {
                if (!kal.getFirma().getNip().equals("0000000000")) {
                    kal.edytujdnizglobalnego(selected);
                }
            }
            kalendarzwzorFacade.editList(kalendarzewzorcowe);
            Msg.msg("Naniesiono zmiany na kalendarze wzorcowe");
        } else {
            Msg.msg("e","nie wybrnao kalendarza, kalendarz nie zachowany w bazie");
        }
    }
    
    public void nanieszmianyPracownicy() {
        if (selected!=null && selected.getId()!=null) {
            List<Kalendarzwzor> kalendarzewzorcowe = kalendarzwzorFacade.findByRokMc(selected.getRok(), selected.getMc());
            kalendarzewzorcowe.parallelStream().forEach(kal -> {
                if (!kal.getFirma().getNip().equals("0000000000")) {
                    kal.edytujdnizglobalnego(selected);
                    kal.setNorma(selected.getNorma());
                }
            });
            kalendarzwzorFacade.editList(kalendarzewzorcowe);
            List<Kalendarzmiesiac> kalendarzepracownicze = kalendarzmiesiacFacade.findByRokMc(selected.getRok(), selected.getMc());
            kalendarzepracownicze.parallelStream().forEach(kal -> {
                kal.edytujdnizglobalnego(selected);
                kal.setNorma(selected.getNorma());
            });
            kalendarzmiesiacFacade.editList(kalendarzepracownicze);
            Msg.msg("Naniesiono zmiany na kalendarze pracowników");
        } else {
            Msg.msg("e","nie wybrnao kalendarza, kalendarz nie zachowany w bazie");
        }
    }
   
       
    public void ustaw() {
        List<Dzien> dni  = dzienFacade.findAll();
        List<Dzien> sortedList = new ArrayList<>(dni);
        List<Dzien> dousuniecia = new ArrayList<>();
        List<Integer> pomin = new ArrayList<>();
        int i = 1;
        for (Iterator<Dzien> it = sortedList.iterator(); it.hasNext();) {
            Dzien dzienwybrany = it.next();
            if (!pomin.contains(dzienwybrany.getId())) {
                for (Dzien d : dni) {
                    if (d.getDatastring().equals(dzienwybrany.getDatastring())&&d.getId()!=dzienwybrany.getId()) {
                        dousuniecia.add(dzienwybrany);
                        pomin.add(d.getId());
                        break;
                    }
                }
            }
        }
        dzienFacade.removeList(dousuniecia);
        System.out.println("KONIEC");
    }
    
    public void otworzrok() {
        if (lista.isEmpty()==false) {
            List<FirmaKadry> firmy = firmaFacade.findByBezglobal();
            for (Kalendarzwzor globalny : lista) {
                for (FirmaKadry firma : firmy) {
                    globalnie(firma, globalny);
                }
            }
        }
    }
    
     public void globalnie(FirmaKadry firma, Kalendarzwzor globalny) {
        if (firma!=null && wpisView.getRokWpisu()!=null) {
                Kalendarzwzor kal = new Kalendarzwzor();
                kal.setRok(globalny.getRok());
                kal.setMc(globalny.getMc());
                kal.setFirma(wpisView.getFirma());
                Kalendarzwzor kalmiesiac = kalendarzwzorFacade.findByFirmaRokMc(firma, kal.getRok(), kal.getMc());
                if (kalmiesiac==null) {
                    if (globalny!=null) {
                        kal.generujdnizglobalnego(globalny);
                        kalendarzwzorFacade.create(kal);
                    } 
                } else if (kalmiesiac!=null && (kalmiesiac.getDzienList()==null||kalmiesiac.getDzienList().size()==0)) {
                     if (globalny!=null) {
                        kalmiesiac.generujdnizglobalnego(globalny);
                        kalendarzwzorFacade.edit(kalmiesiac);
                    }
                }
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

    public FirmaKadry getFirmaglobalna() {
        return firmaglobalna;
    }

    public void setFirmaglobalna(FirmaKadry firmaglobalna) {
        this.firmaglobalna = firmaglobalna;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
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

