/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.RachunekdoumowyzleceniaFacade;
import entity.Pasekwynagrodzen;
import entity.Rachunekdoumowyzlecenia;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import pdf.PdfRachunekZlecenie;

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
    
    public void drukuj(Rachunekdoumowyzlecenia rach) {
        if (rach != null) {
            String nazwa = wpisView.getPracownik().getPesel()+"rachunekzlecenie.pdf";
            PdfRachunekZlecenie.drukujJeden(rach.getPasekwynagrodzen(),nazwa, rachunekdoumowyzleceniaFacade);
        } else {
            Msg.msg("e", "Błąd drukowania. Brak paska");
        }
    }
    
     public void drukuj() {
        if (rachunekdoumowyzlecenialist.isEmpty() == false) {
            String nazwa = wpisView.getPracownik().getPesel()+"rachunekizlecenie.pdf";
            List<Pasekwynagrodzen> paski = rachunekdoumowyzlecenialist.stream().map(Rachunekdoumowyzlecenia::getPasekwynagrodzen).collect(Collectors.toList());
            PdfRachunekZlecenie.drukuj(paski,nazwa, rachunekdoumowyzleceniaFacade);
        } else {
            Msg.msg("e", "Błąd drukowania. Brak pasków");
        }
    }
    
  
   

    public List<Rachunekdoumowyzlecenia> getRachunekdoumowyzlecenialist() {
        return rachunekdoumowyzlecenialist;
    }

    public void setRachunekdoumowyzlecenialist(List<Rachunekdoumowyzlecenia> rachunekdoumowyzlecenialist) {
        this.rachunekdoumowyzlecenialist = rachunekdoumowyzlecenialist;
    }


    

   
    
    
}
