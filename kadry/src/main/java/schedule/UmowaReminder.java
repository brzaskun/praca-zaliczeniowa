/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedule;

import dao.AngazFacade;
import dao.UmowaFacade;
import entity.Angaz;
import entity.Umowa;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
@Stateless
public class UmowaReminder {
    @Inject
    private WpisView wpisView;
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private UmowaFacade umowaFacade;
    
    @Schedule(hour = "21", minute = "21", persistent = false)
    public void oznaczumowy() {
        List<Angaz> angaze = angazFacade.findAll();
        List<Umowa> listaumowy = new ArrayList<>();
        for (Angaz a : angaze) {
            listaumowy.addAll(a.getUmowaList().stream().filter(p->p.isAktywna()).filter(p->p.getDatado()!=null).filter(p->!p.getDatado().equals("")).collect(Collectors.toList()));
        }
        if (listaumowy!=null) {
            for (Umowa u : listaumowy) {
                if (u.getDatado()!=null&&!u.getDatado().equals("")) {
                    LocalDate today = LocalDate.parse(u.getDatado()) ;
                    LocalDate tomorrow = today.minusDays(14) ;
                    u.setDataprzypomnienia(tomorrow.toString());
                }
            }
            umowaFacade.editList(listaumowy);
        }
        List<Umowa> listaszkoleniabhp = new ArrayList<>();
        for (Angaz a : angaze) {
            listaszkoleniabhp.addAll(a.getUmowaList().stream().filter(p->p.isAktywna()).collect(Collectors.toList()));
        }
        if (listaszkoleniabhp!=null) {
            for (Umowa u : listaszkoleniabhp) {
                if (u.getDataszkolenie5lat()!=null&&!u.getDataszkolenie5lat().equals("")) {
                    LocalDate today = LocalDate.parse(u.getDataszkolenie5lat()) ;
                    LocalDate tomorrow = today.plusDays(1804) ;
                    u.setDataprzypomnieniaszkolenie(tomorrow.toString());
                } else if (u.getDataszkolenie3lata()!=null&&!u.getDataszkolenie3lata().equals("")) {
                    LocalDate today = LocalDate.parse(u.getDataszkolenie3lata()) ;
                    LocalDate tomorrow = today.plusDays(1074) ;
                    u.setDataprzypomnieniaszkolenie(tomorrow.toString());
                } else if (u.getDataszkolenierok()!=null&&!u.getDataszkolenierok().equals("")) {
                    LocalDate today = LocalDate.parse(u.getDataszkolenierok()) ;
                    LocalDate tomorrow = today.plusDays(344) ;
                    u.setDataprzypomnieniaszkolenie(tomorrow.toString());
                }
            }
            umowaFacade.editList(listaszkoleniabhp);
        }
    }
}
