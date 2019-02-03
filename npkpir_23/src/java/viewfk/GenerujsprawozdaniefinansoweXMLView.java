/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import com.itextpdf.text.pdf.PdfName;
import comparator.SaldoKontocomparator;
import daoFK.SprFinKwotyInfDodDAO;
import embeddablefk.SaldoKonto;
import entityfk.PozycjaRZiSBilans;
import entityfk.SprFinKwotyInfDod;
import error.E;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import msg.Msg;
import pdf.PdfKonta;
import pdffk.PdfBilans;
import pdffk.PdfPlanKont;
import pdffk.PdfZaksiegowaneView;
import sprawozdania.rok2018.JednostkaInna;
import sprawozdania.rok2018.SprawozdanieFin2018Bean;
import sprawozdania.rok2018.SprawozdanieFin2018BilansBean;
import sprawozdania.rok2018.SprawozdanieFin2018DodInfoBean;
import sprawozdania.rok2018.SprawozdanieFin2018RZiSBean;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class GenerujsprawozdaniefinansoweXMLView  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value = "#{pozycjaBRView}")
    private PozycjaBRView pozycjaBRView;
    @ManagedProperty(value = "#{pozycjaBRZestawienieView}")
    private PozycjaBRZestawienieView pozycjaBRZestawienieView;
    @ManagedProperty(value = "#{saldoAnalitykaView}")
    private SaldoAnalitykaView saldoAnalitykaView;
    @ManagedProperty(value = "#{pdfZaksiegowaneView}")
    private PdfZaksiegowaneView pdfZaksiegowaneView;
    @ManagedProperty(value = "#{planKontView}")
    private PlanKontView planKontView;
    @Inject
    private SprFinKwotyInfDodDAO sprFinKwotyInfDodDAO;
    
    
    
    
    public void drukuj() {
        try {
            pozycjaBRView.init();
            pozycjaBRZestawienieView.init();
            saldoAnalitykaView.init();
            planKontView.init();
            Map<String, List<PozycjaRZiSBilans>> bilans = pozycjaBRView.obliczBilansOtwarciaBilansDataXML();
            List<PozycjaRZiSBilans> rzis = pozycjaBRZestawienieView.obliczRZiSOtwarciaRZiSDataXML();
            //saldoAnalitykaView.odswiezsaldoanalityczne();
            //List<SaldoKonto> saldokontolist = saldoAnalitykaView.getListaSaldoKonto();
            //Collections.sort(saldokontolist, new SaldoKontocomparator());
            generuj(bilans, rzis);
            Msg.msg("Wygenerowano sprawozdanie finansowe");
        } catch (Exception e) {
            Msg.msg("e","Wystąpił błąd podczas generowania sprawozdania finansowego "+E.e(e));
        }
    }
    
    public void generuj(Map<String, List<PozycjaRZiSBilans>> bilans, List<PozycjaRZiSBilans> rzis) {
        try {
            JednostkaInna sprawozdanie = new JednostkaInna();
            sprawozdanie.setNaglowek(SprawozdanieFin2018Bean.naglowek(data.Data.aktualnaData(), start(wpisView), stop(wpisView)));
            sprawozdanie.setWprowadzenieDoSprawozdaniaFinansowego(SprawozdanieFin2018Bean.wprowadzenieDoSprawozdaniaFinansowego(wpisView.getPodatnikObiekt(), start(wpisView), stop(wpisView)));
            sprawozdanie.setBilans(SprawozdanieFin2018BilansBean.generujbilans(bilans.get("aktywa"), bilans.get("pasywa")));
            sprawozdanie.setRZiS(SprawozdanieFin2018RZiSBean.generujrzis(rzis));
            SprFinKwotyInfDod sprFinKwotyInfDod = sprFinKwotyInfDodDAO.findsprfinkwoty(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            sprawozdanie.setDodatkoweInformacjeIObjasnieniaJednstkaInna(SprawozdanieFin2018DodInfoBean.generuj(sprFinKwotyInfDod));
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
    
    private String marszajuldoplikuxml(String nip, String mc, String rok, JednostkaInna sprawozdanie) {
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
    
    private String start(WpisView wpisView) {
        return wpisView.getRokWpisuSt()+"01-01";
    }
    
    private String stop(WpisView wpisView) {
        return wpisView.getRokWpisuSt()+"12-01";
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public PozycjaBRView getPozycjaBRView() {
        return pozycjaBRView;
    }

    public void setPozycjaBRView(PozycjaBRView pozycjaBRView) {
        this.pozycjaBRView = pozycjaBRView;
    }

    public PozycjaBRZestawienieView getPozycjaBRZestawienieView() {
        return pozycjaBRZestawienieView;
    }

    public void setPozycjaBRZestawienieView(PozycjaBRZestawienieView pozycjaBRZestawienieView) {
        this.pozycjaBRZestawienieView = pozycjaBRZestawienieView;
    }

    public SaldoAnalitykaView getSaldoAnalitykaView() {
        return saldoAnalitykaView;
    }

    public void setSaldoAnalitykaView(SaldoAnalitykaView saldoAnalitykaView) {
        this.saldoAnalitykaView = saldoAnalitykaView;
    }


    public PdfZaksiegowaneView getPdfZaksiegowaneView() {
        return pdfZaksiegowaneView;
    }

    public void setPdfZaksiegowaneView(PdfZaksiegowaneView pdfZaksiegowaneView) {
        this.pdfZaksiegowaneView = pdfZaksiegowaneView;
    }

    public PlanKontView getPlanKontView() {
        return planKontView;
    }

    public void setPlanKontView(PlanKontView planKontView) {
        this.planKontView = planKontView;
    }
    
    
    
    
}
