/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.RachunekdoumowyzleceniaFacade;
import entity.Rachunekdoumowyzlecenia;
import java.io.Serializable;
import java.util.List;
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
public class RachunkidoZlecenListaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private RachunekdoumowyzleceniaFacade rachunekdoumowyzleceniaFacade;
    @Inject
    private WpisView wpisView;
    private List<Rachunekdoumowyzlecenia> rachunekdoumowyzlecenialist;

    public void init() {
        pobierzdane();
    }

        
       
    public void pobierzdane() {
        if (wpisView.getUmowa()!=null) {
            rachunekdoumowyzlecenialist = rachunekdoumowyzleceniaFacade.findByRokUmowa(wpisView.getRokWpisu(), wpisView.getUmowa());
            Msg.msg("Pobrano dane - rachunki");
        }
    }
    
  
    public void drukuj() {
//        if (kartawynagrodzenlist!=null && kartawynagrodzenlist.size()>0) {
//            PdfKartaWynagrodzen.drukuj(kartawynagrodzenlist, wpisView.getAngaz(), wpisView.getRokWpisu());
//            Msg.msg("Wydrukowano kartę wynagrodzeń");
//        } else {
//            Msg.msg("e","Błąd drukowania. Brak karty wynagrodze");
//        }
    }

    public List<Rachunekdoumowyzlecenia> getRachunekdoumowyzlecenialist() {
        return rachunekdoumowyzlecenialist;
    }

    public void setRachunekdoumowyzlecenialist(List<Rachunekdoumowyzlecenia> rachunekdoumowyzlecenialist) {
        this.rachunekdoumowyzlecenialist = rachunekdoumowyzlecenialist;
    }


    

   
    
    
}
