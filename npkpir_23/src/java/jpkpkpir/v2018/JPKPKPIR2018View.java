/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpkpkpir.v2018;

import embeddable.DokKsiega;
import embeddable.TKodUS;
import entity.JPKSuper;
import entity.Podatnik;
import error.E;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import msg.Msg;
import view.WpisView; import org.primefaces.PrimeFaces;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class JPKPKPIR2018View implements Serializable {

    private static final long serialVersionUID = 1L;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private TKodUS tKodUS;
    
    public JPK generuj(List<DokKsiega> wierszeksiegi) {
        byte celzlozenia = 1;
        JPK jpk = new JPK();
        if (wierszeksiegi.isEmpty()) {
            Msg.msg("e", "Księga jest pustaw w tym miesiącu, nie można wygenerować JPK_PKPIR");
        } else {
            try {
                Map<String,Object> danepkpir_info = JPKPKPIR2018Bean.zrobwierszeinfo(wierszeksiegi, wpisView);
                if (danepkpir_info.get("p1")==null) {
                    Msg.msg("e", "Brak remanentu początkowego");
                } else if (danepkpir_info.get("p2")==null) {
                    Msg.msg("e", "Brak remanentu końcowego");
                } else {
                    jpk.setNaglowek(JPKPKPIR2018Bean.naglowek(wpisView, celzlozenia, tKodUS));
                    jpk.setPodmiot1(JPKPKPIR2018Bean.podmiot(wpisView));
                    jpk.setPKPIRInfo(JPKPKPIR2018Bean.pkpirinfo(danepkpir_info));
                    Collection<? extends JPK.PKPIRWiersz> listawierszy = JPKPKPIR2018Bean.generujwiersze(wierszeksiegi);
                    jpk.getPKPIRWiersz().addAll(listawierszy);
                    jpk.setPKPIRCtrl(JPKPKPIR2018Bean.kontrola(listawierszy));
                    String sciezka = marszajuldoplikuxml(wpisView.getPodatnikObiekt(), jpk);
                    String polecenie = "wydrukXML(\""+sciezka+"\")";
                    PrimeFaces.current().executeScript(polecenie);
                    Msg.msg("Wygenerowano JPK_PKPIR");
                }
            } catch (Exception e) {
                String wiad = "Wystąpił błąd. Nie wygenerowano jpkpkpir ";
                Msg.msg("e",wiad);
                wiad = "Sprawdź czy wprowadzono remanent pocztąkowy";
                Msg.msg("e",wiad);
            }
        }
        return jpk;
    }
    
    private String marszajuldoplikuxml(Podatnik podatnik, JPKSuper jpk) {
        String sciezka = null;
        try {
            JAXBContext context = JAXBContext.newInstance(jpk.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            String mainfilename = "jpkpkpir"+podatnik.getNip()+"mcrok"+wpisView.getMiesiacWpisu()+wpisView.getRokWpisuSt()+".xml";
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/")+"resources\\xml\\";
            FileOutputStream fileStream = new FileOutputStream(new File(realPath+mainfilename));
            OutputStreamWriter writer = new OutputStreamWriter(fileStream, "UTF-8");
            marshaller.marshal(jpk, writer);
            sciezka = mainfilename;
        } catch (Exception ex) {
            E.e(ex);
        }
        return sciezka;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    
    
    
    
}
