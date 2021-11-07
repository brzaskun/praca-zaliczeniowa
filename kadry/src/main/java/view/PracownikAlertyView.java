/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.AngazFacade;
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
    private List<Umowa> listaumowy;
    
    @PostConstruct
    private void init() {
        if (wpisView.getFirma()!=null) {
            List<Angaz> angaze = angazFacade.findByFirma(wpisView.getFirma());
            listaumowy = new ArrayList<>();
            for (Angaz a : angaze) {
                listaumowy.addAll(a.getUmowaList().stream().filter(p->p.isAktywna()).filter(p->p.getDatado()!=null).filter(p->!p.getDatado().equals("")).collect(Collectors.toList()));
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

    public List<Umowa> getListaumowy() {
        return listaumowy;
    }

    public void setListaumowy(List<Umowa> listaumowy) {
        this.listaumowy = listaumowy;
    }
    
    
}
