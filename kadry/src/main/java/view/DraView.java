/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Defnicjalistaplaccomparator;
import dao.DefinicjalistaplacFacade;
import dao.PasekwynagrodzenFacade;
import entity.Definicjalistaplac;
import entity.Pasekwynagrodzen;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import pdf.PdfDRA;
import z.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class DraView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Pasekwynagrodzen selected;
    @Inject
    private Pasekwynagrodzen selectedlista;
    private Definicjalistaplac wybranalistaplac;
    private List<Pasekwynagrodzen> lista;
    private List<Definicjalistaplac> listadefinicjalistaplac;
    @Inject
    private DefinicjalistaplacFacade definicjalistaplacFacade;
     @Inject
    private PasekwynagrodzenFacade pasekwynagrodzenFacade;
     @Inject
    private WpisView wpisView;
    private double zus51;
    private double zus52;
    private double zus53;
    private double zus;
    private double pit4;
     
    
    @PostConstruct
    public void init() {
        listadefinicjalistaplac = definicjalistaplacFacade.findByFirmaRok(wpisView.getFirma(), wpisView.getRokWpisu());
        Collections.sort(listadefinicjalistaplac, new Defnicjalistaplaccomparator());
        System.out.println("");
    }

    public void drukujliste () {
        if (lista!=null && lista.size()>0) {
            PdfDRA.drukujListaPodstawowa(lista, wybranalistaplac);
            Msg.msg("Wydrukowano listę płac");
        } else {
            Msg.msg("e","Błąd drukowania. Brak pasków");
        }
    }
    

    public void pobierzpaski() {
        if (wybranalistaplac!=null) {
            zus51 = 0.0;
            zus52 = 0.0;
            zus53 = 0.0;
            pit4 = 0.0;
            zus = 0.0;
            lista = pasekwynagrodzenFacade.findByDef(wybranalistaplac);
            for (Pasekwynagrodzen p : lista) {
                zus51 = Z.z(zus51+p.getRazemspolecznepracownik()+p.getRazemspolecznefirma());
                zus52 = Z.z(zus52+p.getPraczdrowotne());
                zus53 = Z.z(zus53+p.getRazem53());
                pit4 = Z.z(pit4+p.getPodatekdochodowy());
            }
            zus = Z.z(zus+zus51+zus52+zus+53);
            Msg.msg("Pobrano paski do DRA");
        } else {
            Msg.msg("e","Błąd pobierania pasków");
        }
    }
    
    public void zachowaj(Pasekwynagrodzen pasek) {
        if (pasek!=null) {
            pasekwynagrodzenFacade.edit(pasek);
            Msg.msg("Zachowano zmiany paska wynagrodzeń");
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

    public Definicjalistaplac getWybranalistaplac() {
        return wybranalistaplac;
    }

    public void setWybranalistaplac(Definicjalistaplac wybranalistaplac) {
        this.wybranalistaplac = wybranalistaplac;
    }

    public double getZus51() {
        return zus51;
    }

    public void setZus51(double zus51) {
        this.zus51 = zus51;
    }

    public double getZus52() {
        return zus52;
    }

    public void setZus52(double zus52) {
        this.zus52 = zus52;
    }

    public double getZus53() {
        return zus53;
    }

    public void setZus53(double zus53) {
        this.zus53 = zus53;
    }

    public double getZus() {
        return zus;
    }

    public void setZus(double zus) {
        this.zus = zus;
    }

    public double getPit4() {
        return pit4;
    }

    public void setPit4(double pit4) {
        this.pit4 = pit4;
    }

    

   
    
}
