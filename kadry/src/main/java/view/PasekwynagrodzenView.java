/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.PasekwynagrodzenBean;
import comparator.Defnicjalistaplaccomparator;
import comparator.Kalendarzmiesiaccomparator;
import dao.DefinicjalistaplacFacade;
import dao.KalendarzmiesiacFacade;
import dao.NieobecnosckodzusFacade;
import dao.PasekwynagrodzenFacade;
import dao.PodatkiFacade;
import dao.WynagrodzeniahistoryczneFacade;
import dao.WynagrodzenieminimalneFacade;
import data.Data;
import entity.Angaz;
import entity.Definicjalistaplac;
import entity.Kalendarzmiesiac;
import entity.Pasekwynagrodzen;
import entity.Podatki;
import entity.Wynagrodzeniahistoryczne;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.primefaces.model.DualListModel;
import pdf.PdfListaPlac;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PasekwynagrodzenView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Pasekwynagrodzen selected;
    @Inject
    private Pasekwynagrodzen selectedlista;
    private Definicjalistaplac wybranalistaplac;
    private List<Pasekwynagrodzen> lista;
    private List<Definicjalistaplac> listadefinicjalistaplac;
    private org.primefaces.model.DualListModel<Kalendarzmiesiac> listakalendarzmiesiac;
    @Inject
    private DefinicjalistaplacFacade definicjalistaplacFacade;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    @Inject
    private PasekwynagrodzenFacade pasekwynagrodzenFacade;
    @Inject
    private NieobecnosckodzusFacade nieobecnosckodzusFacade;
    @Inject
    private WynagrodzeniahistoryczneFacade wynagrodzeniahistoryczneFacade;
    @Inject
    private WynagrodzenieminimalneFacade wynagrodzenieminimalneFacade;
    @Inject
    private PodatkiFacade podatkiFacade;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    public void init() {
        lista = new ArrayList<>();
        listadefinicjalistaplac = definicjalistaplacFacade.findByFirmaRok(wpisView.getFirma(), wpisView.getRokWpisu());
        Collections.sort(listadefinicjalistaplac, new Defnicjalistaplaccomparator());
        listakalendarzmiesiac = new org.primefaces.model.DualListModel<>();
    }

    public void create() {
      if (selected!=null) {
          try {
            PasekwynagrodzenBean.usunpasekjeslijest(selected, pasekwynagrodzenFacade);
            pasekwynagrodzenFacade.create(selected);
            lista.add(selected);
            selected = new Pasekwynagrodzen();
            Msg.msg("Dodano pasek wynagrodzen");
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie dodano paska wynagrodzen");
          }
      }
    }
    
    public void zapisz() {
        if (lista!=null && lista.size()>0) {
            pasekwynagrodzenFacade.editList(lista);
            Msg.msg("Zachowano listę płac");
        }
    }
    
    public void usun() {
        if (lista!=null && lista.size()>0) {
            for (Pasekwynagrodzen p : lista) {
                pasekwynagrodzenFacade.remove(p);
                lista.remove(p);
            }
            Msg.msg("Usunięto listę płac");
        }
    }
    
    public void przelicz() {
        if (wybranalistaplac!=null && !listakalendarzmiesiac.getTarget().isEmpty()) {
            int i = 1;
            List<Podatki> stawkipodatkowe = podatkiFacade.findByRokUmowa(wpisView.getRokWpisu(), "P");
            for (Kalendarzmiesiac p : listakalendarzmiesiac.getTarget()) {
                boolean czysainnekody = p.czysainnekody();
                List<Pasekwynagrodzen> paskidowyliczeniapodstawy = new ArrayList<>();
                List<Wynagrodzeniahistoryczne> historiawynagrodzen = new ArrayList<>();
                if (czysainnekody) {
                    paskidowyliczeniapodstawy = pobierzpaskidosredniej(p);
                    historiawynagrodzen = wynagrodzeniahistoryczneFacade.findByAngaz(p.getUmowa().getAngaz());
                }
                double sumapoprzednich = PasekwynagrodzenBean.sumaprzychodowpoprzednich(pasekwynagrodzenFacade, p, stawkipodatkowe.get(1).getKwotawolnaod());
                double wynagrodzenieminimalne = wynagrodzenieminimalneFacade.findByRok(wpisView.getRokWpisu()).getKwotabrutto();
                Pasekwynagrodzen pasek = PasekwynagrodzenBean.oblicz(p, wybranalistaplac, nieobecnosckodzusFacade, paskidowyliczeniapodstawy, historiawynagrodzen, stawkipodatkowe, sumapoprzednich, wynagrodzenieminimalne);
                usunpasekjakzawiera(pasek);
                lista.add(pasek);
            }
            Msg.msg("Sporządzono listę płac");
        } else {
            Msg.msg("e","Nie wybrano listy lub pracownika");
        }
    }
    
    private List<Pasekwynagrodzen> pobierzpaskidosredniej(Kalendarzmiesiac p) {
        String[] okrespoprzedni = Data.poprzedniOkres(p);
        List<Pasekwynagrodzen> paskiporzednie = pasekwynagrodzenFacade.findByRokAngaz(okrespoprzedni[1],p);
        String rokpoprzedni = String.valueOf(Integer.parseInt(okrespoprzedni[1])-1);
        paskiporzednie.addAll(pasekwynagrodzenFacade.findByRokAngaz(rokpoprzedni,p));
        return paskiporzednie;
    }

    
    private void usunpasekjakzawiera(Pasekwynagrodzen pasek) {
        for (Iterator<Pasekwynagrodzen> it = lista.iterator(); it.hasNext(); ) {
            Pasekwynagrodzen pa = it.next();
            if (pa.getKalendarzmiesiac().equals(pasek.getKalendarzmiesiac())) {
                it.remove();
            }
        }
    }
    
    public void drukuj(Pasekwynagrodzen p ) {
        if (p!=null) {
            PdfListaPlac.drukuj(p, nieobecnosckodzusFacade);
            Msg.msg("Wydrukowano pwsek wynagrodzeń");
        } else {
            Msg.msg("e","Błąd drukowania. Pasek null");
        }
    }
    public void drukujliste () {
        if (lista!=null && lista.size()>0) {
            PdfListaPlac.drukujListaPodstawowa(lista, wybranalistaplac, nieobecnosckodzusFacade);
            Msg.msg("Wydrukowano listę płac");
        } else {
            Msg.msg("e","Błąd drukowania. Brak pasków");
        }
    }
    
    public void usun(Pasekwynagrodzen p ) {
        if (p!=null) {
            if (p.getId()!=null) {
                pasekwynagrodzenFacade.remove(p);
                lista.remove(p);
            } else {
                lista.remove(p);
            }
            Msg.msg("Usunięto wiersz listy płac");
        } else {
            Msg.msg("e","Błąd usuwania. Pasek null");
        }
    }
    
    public void aktywuj(Angaz angaz) {
        if (angaz!=null) {
            wpisView.setAngaz(angaz);
            Msg.msg("Aktywowano firmę");
        }
    }
    
    public void pobierzkalendarzezamc() {
        if (wybranalistaplac!=null) {
            List<Kalendarzmiesiac> listakalendarzmiesiac = kalendarzmiesiacFacade.findByFirmaRokMc(wybranalistaplac.getFirma(), wybranalistaplac.getRok(), wybranalistaplac.getMc());
            Collections.sort(listakalendarzmiesiac, new Kalendarzmiesiaccomparator());
            if (listakalendarzmiesiac!=null) {
                this.listakalendarzmiesiac.setSource(listakalendarzmiesiac);
            }
            lista = pasekwynagrodzenFacade.findByDef(wybranalistaplac);
        }
    }
    
    public Pasekwynagrodzen getSelected() {
        return selected;
    }

    public void setSelected(Pasekwynagrodzen selected) {
        this.selected = selected;
    }

    public List<Pasekwynagrodzen> getLista() {
        return lista;
    }

    public void setLista(List<Pasekwynagrodzen> lista) {
        this.lista = lista;
    }

    public List<Definicjalistaplac> getListadefinicjalistaplac() {
        return listadefinicjalistaplac;
    }

    public void setListadefinicjalistaplac(List<Definicjalistaplac> listadefinicjalistaplac) {
        this.listadefinicjalistaplac = listadefinicjalistaplac;
    }

    public Pasekwynagrodzen getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Pasekwynagrodzen selectedlista) {
        this.selectedlista = selectedlista;
    }

    public DualListModel<Kalendarzmiesiac> getListakalendarzmiesiac() {
        return listakalendarzmiesiac;
    }

    public void setListakalendarzmiesiac(DualListModel<Kalendarzmiesiac> listakalendarzmiesiac) {
        this.listakalendarzmiesiac = listakalendarzmiesiac;
    }

   

    public Definicjalistaplac getWybranalistaplac() {
        return wybranalistaplac;
    }

    public void setWybranalistaplac(Definicjalistaplac wybranalistaplac) {
        this.wybranalistaplac = wybranalistaplac;
    }

    
    

   
    
}
