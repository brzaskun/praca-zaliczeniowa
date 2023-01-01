/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DokumentyFacade;
import entity.Dokumenty;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class DokumentyWykazView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private DokumentyFacade dokumentyFacade;
    private List<Dokumenty> lista;
    private List<Dokumenty> listafiltered;
    @Inject
    private WpisView wpisView;
    private StreamedContent file;
    
    @PostConstruct
    private void init() {
        lista = dokumentyFacade.findByFirma(wpisView.getFirma());
    }
    
    public void fileDownloadView(Dokumenty dok) {
        InputStream  targetStream = new ByteArrayInputStream(dok.getDokument());
        String nazwa = dok.getAngaz().getPracownik().getPesel()+"aneks"+dok.getData()+".pdf";
        file = DefaultStreamedContent.builder()
                .name(nazwa)
                .contentType("application/pdf")
                .stream(() -> targetStream)
                .build();
    }
    
    public void usun(Dokumenty dok) {
        if (dok!=null) {
            try {
                dokumentyFacade.remove(dok);
                lista.remove(dok);
                Msg.msg("Usunięto dokument");
            } catch (Exception e) {
                Msg.msg("e","Błąd, nie usunięto dokumentu");
            }
        }
    }

    public StreamedContent getFile(Dokumenty dok) {
        fileDownloadView(dok);
        return file;
    }

    public List<Dokumenty> getLista() {
        return lista;
    }

    public void setLista(List<Dokumenty> lista) {
        this.lista = lista;
    }

    public List<Dokumenty> getListafiltered() {
        return listafiltered;
    }

    public void setListafiltered(List<Dokumenty> listafiltered) {
        this.listafiltered = listafiltered;
    }
    
    
    
    
    
}
