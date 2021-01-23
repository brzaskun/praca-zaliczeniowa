/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.KalendarzmiesiacBean;
import dao.KalendarzmiesiacFacade;
import dao.KalendarzwzorFacade;
import dao.NieobecnoscFacade;
import dao.NieobecnosckodzusFacade;
import dao.UmowaFacade;
import data.Data;
import entity.Kalendarzmiesiac;
import entity.Kalendarzwzor;
import entity.Nieobecnosc;
import entity.Nieobecnosckodzus;
import entity.Umowa;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceRef;
import msg.Msg;
import zuszla.PobierzRaporty;
import zuszla.PobierzRaportyResponse;
import zuszla.WsdlPlatnikRaportyZlaPortType;

/**
 *
 * @author Osito
 */
@Named
@RequestScoped
public class NieobecnoscView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Nieobecnosc selected;
    @Inject
    private Nieobecnosc selectedlista;
    private List<Nieobecnosc> lista;
    private List<Nieobecnosckodzus> listanieobecnosckodzus;
    private List<Umowa> listaumowa;
    @Inject
    private NieobecnoscFacade nieobecnoscFacade;
    @Inject
    private NieobecnosckodzusFacade nieobecnosckodzusFacade;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    @Inject
    private KalendarzwzorFacade kalendarzwzorFacade;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private WpisView wpisView;
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/zuszla.wsdl")
    private zuszla.WsdlPlatnikRaportyZla wsdlPlatnikRaportyZla;
    
    @PostConstruct
    private void init() {
        lista  = nieobecnoscFacade.findByUmowa(wpisView.getUmowa());
        listaumowa = umowaFacade.findPracownik(wpisView.getPracownik());
        listanieobecnosckodzus = nieobecnosckodzusFacade.findAll();
    }

    public void create() {
      if (selected!=null) {
          try {
            nieobecnoscFacade.create(selected);
            lista.add(selected);
            selected = new Nieobecnosc();
            Msg.msg("Dodano nieobecnośc");
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie dodano nowej nieobecnosci");
          }
      }
    }
    
    public void nieniesnakalendarz() {
        if (wpisView.getUmowa() != null) {
            Kalendarzmiesiac znaleziony = kalendarzmiesiacFacade.findByRokMcUmowa(wpisView.getUmowa(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
            if (znaleziony == null) {
                znaleziony = new Kalendarzmiesiac(wpisView.getUmowa(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
                Kalendarzwzor znaleziono = kalendarzwzorFacade.findByFirmaRokMc(wpisView.getFirma(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
                if (znaleziono != null) {
                    znaleziony.ganerujdnizwzrocowego(znaleziono, null);
                    Msg.msg("Pobrano dane z kalendarza wzorcowego z bazy danych");
                } else {
                    KalendarzmiesiacBean.create(znaleziony);
                    Msg.msg("Przygotowano kalendarz");
                }
            }
            for (Nieobecnosc p : lista) {
                znaleziony.naniesnieobecnosc(p);
                p.setNaniesiona(true);
                
            }
            nieobecnoscFacade.edit(selected);
            kalendarzmiesiacFacade.edit(znaleziony);
            Msg.msg("Naniesiono nieobecnosci");
        }
    }

    public void pobierzzzus() {
        try {
            PobierzRaporty parameters = new PobierzRaporty();
            parameters.setNip(wpisView.getFirma().getNip());
            parameters.setLogin("a.barczyk@taxman.biz.pl");
            parameters.setHaslo("Taxman2810*");
            String nowadata = Data.odejmijdniDzis(30);
            parameters.setDataOd(Data.dataoddo(nowadata));
            WsdlPlatnikRaportyZlaPortType port = wsdlPlatnikRaportyZla.getZusChannelPlatnikRaportyZlaWsdlPlatnikRaportyZlaPort();
            BindingProvider prov = (BindingProvider) port;
            prov.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "b2b_platnik_raporty_zla");
            prov.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "b2b_platnik_raporty_zla");
            PobierzRaportyResponse pobierzRaporty = port.pobierzRaporty(parameters);
            if (pobierzRaporty.getKod().equals("0")) {
                if (pobierzRaporty.getRaporty()==null) {
                    Msg.msg("w","Brak zwolnien w ostatnich 30 dniach");
                } else {
                    Msg.msg("Pobrano zwolnienia z ostatnich 30 dni");
                }
            } else if (pobierzRaporty.getKod().equals("200")) {
                Msg.msg("e","Serwer ZUS wyłączony");
            }
            System.out.println("");
        } catch (Exception e) {
            System.out.println("");
        }
    }
    
    public Nieobecnosc getSelected() {
        return selected;
    }

    public void setSelected(Nieobecnosc selected) {
        this.selected = selected;
    }

    public List<Nieobecnosc> getLista() {
        return lista;
    }

    public void setLista(List<Nieobecnosc> lista) {
        this.lista = lista;
    }

    public Nieobecnosc getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Nieobecnosc selectedlista) {
        this.selectedlista = selectedlista;
    }

    public List<Umowa> getListaumowa() {
        return listaumowa;
    }

    public void setListaumowa(List<Umowa> listaumowa) {
        this.listaumowa = listaumowa;
    }

    public List<Nieobecnosckodzus> getListanieobecnosckodzus() {
        return listanieobecnosckodzus;
    }

    public void setListanieobecnosckodzus(List<Nieobecnosckodzus> listanieobecnosckodzus) {
        this.listanieobecnosckodzus = listanieobecnosckodzus;
    }

   
    
    
}
