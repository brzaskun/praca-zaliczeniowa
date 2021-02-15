/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.KlientJPKDAO;
import entity.KlientJPK;
import java.io.Serializable;
import java.util.List;
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
public class KlientJPKView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private KlientJPKDAO klientJPKDAO;
    private List<KlientJPK> lista;
    
    
   @PostConstruct
   private void init() {
       try {
           lista = klientJPKDAO.findbyKlientRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
       } catch (Exception e){}
   }

    public List<KlientJPK> getLista() {
        return lista;
    }

    public void setLista(List<KlientJPK> lista) {
        this.lista = lista;
    }
   
   
}
