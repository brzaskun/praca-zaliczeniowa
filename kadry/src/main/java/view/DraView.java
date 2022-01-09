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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
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
    private List<Definicjalistaplac> listywybrane;
    private List<Pasekwynagrodzen> paskiwynagrodzen;
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
    private String mcdra;
     
    
    public void init() {
        mcdra = wpisView.getMiesiacWpisu();
        pobierzlisty();
        Collections.sort(listadefinicjalistaplac, new Defnicjalistaplaccomparator());
    }

    public void pobierzlisty() {
        if (mcdra != null) {
            listadefinicjalistaplac = definicjalistaplacFacade.findByFirmaRokMc(wpisView.getFirma(), wpisView.getRokWpisu(), mcdra);
            for (Iterator<Definicjalistaplac> it = listadefinicjalistaplac.iterator(); it.hasNext();) {
                Definicjalistaplac d = it.next();
                if (d.getPasekwynagrodzenList() == null ||d.getPasekwynagrodzenList().isEmpty()) {
                    it.remove();
                }
            }
            Collections.sort(listadefinicjalistaplac, new Defnicjalistaplaccomparator());
            Msg.msg("Pobrnao listy");
        }
    }
    
    public void drukujliste () {
        if (paskiwynagrodzen!=null && paskiwynagrodzen.size()>0) {
            PdfDRA.drukujListaPodstawowa(paskiwynagrodzen, listywybrane, wpisView.getFirma().getNip(), mcdra);
            Msg.msg("Wydrukowano listę płac");
        } else {
            Msg.msg("e","Błąd drukowania. Brak pasków");
        }
    }
    

    public void pobierzpaski() {
        if (listywybrane!=null) {
            zus51 = 0.0;
            zus52 = 0.0;
            zus53 = 0.0;
            pit4 = 0.0;
            zus = 0.0;
            paskiwynagrodzen = new ArrayList<>();
            for (Definicjalistaplac d : listywybrane) {
                List<Pasekwynagrodzen> paski = pasekwynagrodzenFacade.findByDef(d);
                if (paski!=null) {
                    paskiwynagrodzen.addAll(paski);
                }
            }
            for (Pasekwynagrodzen p : paskiwynagrodzen) {
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

    
    public Pasekwynagrodzen getSelected() {
        return selected;
    }

    public void setSelected(Pasekwynagrodzen selected) {
        this.selected = selected;
    }

    public List<Pasekwynagrodzen> getPaskiwynagrodzen() {
        return paskiwynagrodzen;
    }

    public void setPaskiwynagrodzen(List<Pasekwynagrodzen> paskiwynagrodzen) {
        this.paskiwynagrodzen = paskiwynagrodzen;
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

    public List<Definicjalistaplac> getListywybrane() {
        return listywybrane;
    }

    public void setListywybrane(List<Definicjalistaplac> listywybrane) {
        this.listywybrane = listywybrane;
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

    public String getMcdra() {
        return mcdra;
    }

    public void setMcdra(String mcdra) {
        this.mcdra = mcdra;
    }

    

   
    
}
