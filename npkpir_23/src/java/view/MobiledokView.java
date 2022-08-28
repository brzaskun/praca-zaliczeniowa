/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.MobiledokDAO;
import entity.Mobiledok;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class MobiledokView implements Serializable{
    private static final long serialVersionUID = 1L;
    private List<Mobiledok> lista;
    @Inject
    private MobiledokDAO mobiledokDAO;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    private void init() {
        lista = mobiledokDAO.findByNip(wpisView.getPodatnikObiekt().getNip());
    }
    
    public StreamedContent getPicture(Mobiledok mobiledok) {
        try {
            DefaultStreamedContent dsc = new DefaultStreamedContent();
            dsc.setContentType("image/"+mobiledok.getRozszerzenie());
            dsc.setName(wpisView.getPodatnikObiekt().getNip()+"."+mobiledok.getRozszerzenie());
            dsc.setStream(new ByteArrayInputStream(mobiledok.getPlik()));
            return dsc;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Mobiledok> getLista() {
        return lista;
    }

    public void setLista(List<Mobiledok> lista) {
        this.lista = lista;
    }
    
    
}
