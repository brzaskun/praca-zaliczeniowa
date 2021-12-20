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
import msg.Msg;
import waluty.Z;

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
    private List<KlientJPK> listafilter;
    private KlientJPK selected;
    private double suma;
    
    
   @PostConstruct
   private void init() {
       try {
           lista = klientJPKDAO.findbyKlientRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
           suma = 0.0;
           for (KlientJPK p : lista) {
               suma = Z.z(suma+p.getNetto());
           }
       } catch (Exception e){}
   }

   public void handleSave(KlientJPK klient) {
       klientJPKDAO.edit(klient);
       Msg.msg("Naniesiono zmiany");
   }
   
   public void usun(KlientJPK klient) {
       if (klient!=null) {
           klientJPKDAO.remove(klient);
           lista.remove(klient);
           if (listafilter!=null&&listafilter.contains(klient)) {
               listafilter.remove(klient);
           }
           Msg.msg("Usunięto wiersz");
       }
   }
   
    public List<KlientJPK> getLista() {
        return lista;
    }

    public void setLista(List<KlientJPK> lista) {
        this.lista = lista;
    }

    public double getSuma() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }

    public KlientJPK getSelected() {
        return selected;
    }

    public void setSelected(KlientJPK selected) {
        this.selected = selected;
    }

    public List<KlientJPK> getListafilter() {
        return listafilter;
    }

    public void setListafilter(List<KlientJPK> listafilter) {
        this.listafilter = listafilter;
    }
   
    
   
}
