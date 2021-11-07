/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.AngazFacade;
import dao.UmowaFacade;
import entity.Angaz;
import entity.Umowa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
    private List<Umowa> listaszkoleniabhp;
    
    @PostConstruct
    private void init() {
        if (wpisView.getFirma()!=null) {
            List<Angaz> angaze = angazFacade.findByFirma(wpisView.getFirma());
            listaumowy = new ArrayList<>();
            listaszkoleniabhp = new ArrayList<>();
            for (Angaz a : angaze) {
                listaumowy.addAll(a.getUmowaList().stream().filter(p->p.isAktywna()).filter(p->p.getDatado()!=null).filter(p->!p.getDatado().equals("")).collect(Collectors.toList()));
            }
            for (Angaz a : angaze) {
                listaszkoleniabhp.addAll(a.getUmowaList().stream().filter(p->p.isAktywna()).collect(Collectors.toList()));
            }
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

    public List<Umowa> getListaumowy() {
        return listaumowy;
    }

    public void setListaumowy(List<Umowa> listaumowy) {
        this.listaumowy = listaumowy;
    }

    public List<Umowa> getListaszkoleniabhp() {
        return listaszkoleniabhp;
    }

    public void setListaszkoleniabhp(List<Umowa> listaszkoleniabhp) {
        this.listaszkoleniabhp = listaszkoleniabhp;
    }
    
    
}
