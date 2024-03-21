/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Angazcomparator;
import comparator.Umowacomparator;
import dao.AngazFacade;
import dao.UmowaFacade;
import entity.Angaz;
import entity.Umowa;
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

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PracownikAlertyView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private UmowaFacade umowaFacade;
    private List<Umowa> listaumowy;
    private List<Angaz> listaszkoleniabhp;
    private List<Umowa> listabadanialekarskie;
    private List<Angaz> listaA1;
   
    @PostConstruct
    private void init() {
        if (wpisView.getFirma()!=null) {
            List<Angaz> angaze = angazFacade.findByFirma(wpisView.getFirma());
            listaumowy = new ArrayList<>();
            listaszkoleniabhp = new ArrayList<>();
            listaA1 = new ArrayList<>();
            listabadanialekarskie = new ArrayList<>();
            for (Iterator<Angaz> it = angaze.iterator(); it.hasNext();) {
                    Angaz angaz = it.next();
                    if (angaz.jestumowaAktywna(wpisView.getRokWpisu(), wpisView.getMiesiacWpisu())==false) {
                        it.remove();
                    } else {
                        listaA1.add(angaz);
                        listaszkoleniabhp.add(angaz);
                    }
            }
            for (Angaz a : angaze) {
                Umowa umowaAktywna = a.pobierzumowaAktywna(wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
                if (umowaAktywna!=null) {
                    listaumowy.add(umowaAktywna);
                    listabadanialekarskie.add(umowaAktywna);
                }
            }
            
            Collections.sort(listaumowy, new Umowacomparator());
            Collections.sort(listaszkoleniabhp, new Angazcomparator());
            Collections.sort(listaA1, new Angazcomparator());
            Collections.sort(listabadanialekarskie, new Umowacomparator());
//            if (listaumowy!=null) {
//                for (Umowa u : listaumowy) {
//                    if (u.getDataprzypomnienia()==null && u.getDatado()!=null && !u.getDatado().equals("")) {
//                        LocalDate today = LocalDate.parse(u.getDatado()) ;
//                        LocalDate tomorrow = today.minusDays(14) ;
//                        u.setDataprzypomnienia(tomorrow.toString());
//                    }
//                }
//            }
        }
    }
    
    public void zapiszzmianybhp() {
        umowaFacade.editList(listaszkoleniabhp);
        Msg.msg("Zachowane zmiany");
    }
    
    public void zapiszzmianya1() {
        angazFacade.editList(listaA1);
        Msg.msg("Zachowane zmiany");
    }

     public void zapiszzmianybadanialekarskie() {
        angazFacade.editList(listabadanialekarskie);
        Msg.msg("Zachowane zmiany");
    }


    public List<Umowa> getListaumowy() {
        return listaumowy;
    }

    public void setListaumowy(List<Umowa> listaumowy) {
        this.listaumowy = listaumowy;
    }

    public List<Angaz> getListaszkoleniabhp() {
        return listaszkoleniabhp;
    }

    public void setListaszkoleniabhp(List<Angaz> listaszkoleniabhp) {
        this.listaszkoleniabhp = listaszkoleniabhp;
    }



    public List<Angaz> getListaA1() {
        return listaA1;
    }

    public void setListaA1(List<Angaz> listaA1) {
        this.listaA1 = listaA1;
    }

    public List<Umowa> getListabadanialekarskie() {
        return listabadanialekarskie;
    }

    public void setListabadanialekarskie(List<Umowa> listabadanialekarskie) {
        this.listabadanialekarskie = listabadanialekarskie;
    }

   
    
}
