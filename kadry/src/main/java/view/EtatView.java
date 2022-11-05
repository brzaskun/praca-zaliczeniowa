/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.DataBean;
import dao.EtatPracFacade;
import dao.KalendarzmiesiacFacade;
import data.Data;
import entity.Dzien;
import entity.EtatPrac;
import entity.Kalendarzmiesiac;
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
public class EtatView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private EtatPrac selected;
    @Inject
    private EtatPracFacade etatFacade;
    private List<EtatPrac> lista;
    private EtatPrac selectedlista;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    public void init() {
        if (wpisView.getUmowa()!=null){
            selected.setAngaz(wpisView.getAngaz());
            lista = etatFacade.findByAngaz(wpisView.getAngaz());
        }
    }
    
    public void create() {
      if (selected!=null && wpisView.getUmowa()!=null) {
          if (selected.getId()==null) {
            try {
              selected.setAngaz(wpisView.getAngaz());
              etatFacade.create(selected);
              lista.add(selected);
              edytujkalendarz(selected);
              selected = new EtatPrac();
              Msg.msg("Dodano etat");
            } catch (Exception e) {
                Msg.msg("e", "Błąd - nie dodano etatu");
            }
          } else {
              try {
                etatFacade.edit(selected);
                lista.add(selected);
                edytujkalendarz(selected);
                selected = new EtatPrac();
                Msg.msg("Dodano etat");
              } catch (Exception e) {
                  Msg.msg("e", "Błąd - nie zmieniono etatu");
              }
          }
      } else {
          Msg.msg("e","Brak wybranej umowy");
      }
    }
    
    public void usunEtat(EtatPrac zmienna) {
        if (zmienna!=null) {
            etatFacade.remove(zmienna);
            lista.remove(zmienna);
            Msg.msg("Usunięto etat");
        } else {
            Msg.msg("e","Nie wybrano etatu");
        }
    }

    private void edytujkalendarz(EtatPrac selected) {
        List<Kalendarzmiesiac> kalendarze = kalendarzmiesiacFacade.findByAngaz(selected.getAngaz());
        for (Kalendarzmiesiac k : kalendarze) {
            boolean czyjestpo = Data.czyjestpo(selected.getDataod(), k.getRok(), k.getMc());
            boolean czyjestprzed = Data.czyjestprzed(selected.getDatado(), k.getRok(), k.getMc());
            if (czyjestpo&&czyjestprzed) {
                List<Dzien> dzienList = k.getDzienList();
                for (Dzien d : dzienList) {
                    boolean czysiemiesci = DataBean.czysiemiescidzien(d.getDatastring(), selected.getDataod(), selected.getDatado());
                    if (czysiemiesci) {
                        if (selected!=null) {
                            d.setEtat1(selected.getEtat1());
                            d.setEtat2(selected.getEtat2());
                            d.setNormagodzin(Z.z(d.getNormagodzinwzorcowa()*d.getEtat1()/d.getEtat2()));
                            d.setPrzepracowano(Z.z(d.getNormagodzin()));
                        }
                    }
                }
                kalendarzmiesiacFacade.edit(k);
            } else if (czyjestprzed==false) {
                    break;
            }
        }
        Msg.msg("Naniesiono nowy etat na kalendarz");
    }
    
    public EtatPrac getSelected() {
        return selected;
    }

    public void setSelected(EtatPrac selected) {
        this.selected = selected;
    }

    public List<EtatPrac> getLista() {
        return lista;
    }

    public void setLista(List<EtatPrac> lista) {
        this.lista = lista;
    }

    public EtatPrac getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(EtatPrac selectedlista) {
        this.selectedlista = selectedlista;
    }

    
    
    
    
    
}
