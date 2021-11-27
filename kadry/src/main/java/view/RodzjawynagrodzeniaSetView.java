/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import DAOsuperplace.WynKodSklFacade;
import dao.RodzajwynagrodzeniaFacade;
import entity.Rodzajwynagrodzenia;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import kadryiplace.WynKodSkl;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@SessionScoped
public class RodzjawynagrodzeniaSetView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private RodzajwynagrodzeniaFacade rodzajwynagrodzeniaFacade;
    private Rodzajwynagrodzenia selectedlista;
    private List<Rodzajwynagrodzenia> lista;
    
    
    @PostConstruct
    private void init() {
        lista = rodzajwynagrodzeniaFacade.findAll();
    }

    public void zachowaj() {
        rodzajwynagrodzeniaFacade.editList(lista);
        Msg.msg("Zmiany zachowane");
    }
    
    @Inject
    private WynKodSklFacade wynKodSklFacade;
    public void generujtabele() {
        Msg.msg("Start");
        List<WynKodSkl> findAll = wynKodSklFacade.findAll();
        for (WynKodSkl p : findAll) {
            Rodzajwynagrodzenia s  = new Rodzajwynagrodzenia();
            s.setKod(p.getWksKod());
            s.setOpispelny(p.getWksOpis());
            s.setOpisskrocony(p.getWksOpisSkr());
            s.setWks_serial(p.getWksSerial());
            s.setPodatek0bezpodatek1(p.getWksPodDoch().equals('T')==false);
            s.setZus0bezzus1(p.getWksZus().equals('N')==true);
            s.setSredniaurlopowakraj(p.getWksPdstUrlop().equals('N')==false);
            s.setPodstzasilekchorobowy(p.getWksPdstZasChor().equals('T')==true);
            rodzajwynagrodzeniaFacade.create(s);
        }
        Msg.dP();
    }
    
    public List<Rodzajwynagrodzenia> getLista() {
        return lista;
    }

    public void setLista(List<Rodzajwynagrodzenia> lista) {
        this.lista = lista;
    }

 

    public Rodzajwynagrodzenia getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Rodzajwynagrodzenia selectedlista) {
        this.selectedlista = selectedlista;
    }
    
    
}
