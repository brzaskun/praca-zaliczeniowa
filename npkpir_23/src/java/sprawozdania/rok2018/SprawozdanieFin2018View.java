/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprawozdania.rok2018;

import entity.Podatnik;
import error.E;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class SprawozdanieFin2018View  implements Serializable {

    private static final long serialVersionUID = 1L;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    
    public static void generuj() {
        try {
            JednostkaInna sprawozdanie = new JednostkaInna();
            sprawozdanie.naglowek = SprawozdanieFin2018Bean.naglowek("2019-01-01", "2018-01-01", "2018-12-31");
            sprawozdanie.wprowadzenieDoSprawozdaniaFinansowego = SprawozdanieFin2018Bean.wprowadzenieDoSprawozdaniaFinansowego(new Podatnik(),"2018-01-01", "2018-12-31");
            //sprawozdanie.bilans = SprawozdanieFin2018BilansBean.generujbilans();
            //sprawozdanie.rZiS = SprawozdanieFin2018RZiSBean.generujrzis();
            //sprawozdanie.dodatkoweInformacjeIObjasnieniaJednstkaInna = SprawozdanieFin2018DodInfoBean.generuj();
            String sciezka = marszajuldoplikuxml("8511005008", "01", "2019", sprawozdanie);
            //String polecenie = "wydrukXML(\""+sciezka+"\")";
            //RequestContext.getCurrentInstance().execute(polecenie);
            //Msg.msg("Wygenerowano sprawozdanie finansowe");
            System.out.println("Wygenerowano sprawozdanie finansowe");
        } catch (Exception e) {
            //Msg.msg("e","Wystąpił błąd. Nie wygenerowano sprawozdania finansowego");
            System.out.println("Wystąpił błąd. Nie wygenerowano sprawozdania finansowego");
        }
    }
    
    private static String marszajuldoplikuxml(String nip, String mc, String rok, JednostkaInna sprawozdanie) {
        String sciezka = null;
        try {
            JAXBContext context = JAXBContext.newInstance(sprawozdanie.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            //String mainfilename = "sprawozdaniefinansowe"+podatnik.getNip()+"mcrok"+wpisView.getMiesiacWpisu()+wpisView.getRokWpisuSt()+".xml";
            String mainfilename = "sprawozdaniefinansowe"+nip+"mcrok"+mc+rok+".xml";
            //ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            //String realPath = ctx.getRealPath("/")+"resources\\xml\\";
            String realPath = "D:\\";
            FileOutputStream fileStream = new FileOutputStream(new File(realPath+mainfilename));
            OutputStreamWriter writer = new OutputStreamWriter(fileStream, "UTF-8");
            marshaller.marshal(sprawozdanie, writer);
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
    
    public static void main(String[] args) {
        generuj();
    }
}
