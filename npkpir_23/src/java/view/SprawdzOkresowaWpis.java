/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import dao.FakturywystokresoweDAO;
import entity.Fakturywystokresowe;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class SprawdzOkresowaWpis implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private FakturywystokresoweDAO fakturywystokresoweDAO;
    
    
    @PostConstruct
    private void init() {
       
    }

    public boolean brakOkresowej() {
        boolean brakokresowej = true;
        try {
            List<Fakturywystokresowe> fakturywystokresowe = fakturywystokresoweDAO.findByKlientRok("8511005008", wpisView.getPodatnikObiekt().getNip(), wpisView.getRokWpisuSt());
            if (fakturywystokresowe!=null&&fakturywystokresowe.isEmpty()==false) {
                brakokresowej = false;
            }
        } catch (Exception e) {}
        return brakokresowej;
    }

    
    
}
