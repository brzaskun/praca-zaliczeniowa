package view;

import dao.PodatnikDAO;
import dao.RyczDAO;
import entity.Ryczpoz;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
 import org.primefaces.PrimeFaces;
import pdf.PdfPIT28;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class RyczView implements Serializable {
    private List<Ryczpoz> lista;
    @Inject private RyczDAO ryczDAO;
    @Inject private PodatnikDAO podatnikDAO;
    @Inject
    private WpisView wpisView;
    @Inject private Ryczpoz biezacyPit;
   

    public RyczView() {
        lista = Collections.synchronizedList(new ArrayList<>());
       
    }
    
    
    
    @PostConstruct
    public void init() { //E.m(this);
        lista = ryczDAO.findRyczPod(wpisView.getRokWpisu().toString(), wpisView.getPodatnikWpisu());
       
    }
    
     public void usun() {
        int index = lista.size() - 1;
        Ryczpoz selected = lista.get(index);
        ryczDAO.remove(selected);
        lista.remove(selected);
        PrimeFaces.current().ajax().update("formpi1:tablicapit");
        Msg.msg("i", "Usunieto ostatni PIT ryczałt"+selected.getUdzialowiec()+" za m-c: "+selected.getPkpirM(),"formpi:messages");
    }
     
    public void drukujRyczalt() {
        try {
            PdfPIT28.drukuj(biezacyPit, wpisView, podatnikDAO);
        } catch (Exception e) { E.e(e); 
            
        }
    }
     
     public void inforryczalt() {
         Msg.msg("i", "Pobrano dane miesiąca");
     }

    public List<Ryczpoz> getLista() {
        return lista;
    }

    public void setLista(List<Ryczpoz> lista) {
        this.lista = lista;
    }


    public RyczDAO getRyczDAO() {
        return ryczDAO;
    }

    public void setRyczDAO(RyczDAO ryczDAO) {
        this.ryczDAO = ryczDAO;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public Ryczpoz getBiezacyPit() {
        return biezacyPit;
    }

    public void setBiezacyPit(Ryczpoz biezacyPit) {
        this.biezacyPit = biezacyPit;
    }

   
    
}

