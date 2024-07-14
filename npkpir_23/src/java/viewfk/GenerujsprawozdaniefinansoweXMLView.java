/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.PodatnikOpodatkowanieDAO;
import dao.SprFinKwotyInfDodDAO;
import entity.PodatnikOpodatkowanieD;
import entityfk.PozycjaRZiSBilans;
import entityfk.SprFinKwotyInfDod;
import error.E;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import msg.Msg;
import org.primefaces.PrimeFaces;
import pdffk.PdfZaksiegowaneView;
import sprawozdania.rok2018.JednostkaInna;
import sprawozdania.rok2018.SprawozdanieFin2018Bean;
import sprawozdania.rok2018.SprawozdanieFin2018BilansBean;
import sprawozdania.rok2018.SprawozdanieFin2018DodInfoBean;
import sprawozdania.rok2018.SprawozdanieFin2018RZiSBean;
import sprawozdania.rok2018.SprawozdanieFinOP2018Bean;
import sprawozdania.rok2018.SprawozdanieFinOP2018BilansBean;
import sprawozdania.rok2018.SprawozdanieFinOP2018DodInfoBean;
import sprawozdania.rok2018.SprawozdanieFinOP2018RZiSBean;
 import view.WpisView;
import interceptor.ConstructorInterceptor;

/**
 *
 * @author Osito
 */
@Named @Interceptors(ConstructorInterceptor.class)
@ViewScoped
public class GenerujsprawozdaniefinansoweXMLView  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private WpisView wpisView;
    @Inject
    private PozycjaBRView pozycjaBRView;
    @Inject
    private PozycjaBRZestawienieView pozycjaBRZestawienieView;
    @Inject
    private SaldoAnalitykaView saldoAnalitykaView;
    @Inject
    private PdfZaksiegowaneView pdfZaksiegowaneView;
    @Inject
    private PlanKontView planKontView;
    @Inject
    private SprFinKwotyInfDodDAO sprFinKwotyInfDodDAO;
    @Inject
    PodatnikOpodatkowanieDAO podatnikOpodatkowanieDAO;
    
    
    
    
    public void drukuj() {
        try {
            //saldoAnalitykaView.odswiezsaldoanalityczne();
            //List<SaldoKonto> saldokontolist = saldoAnalitykaView.getListaSaldoKonto();
            //Collections.sort(saldokontolist, new SaldoKontocomparator());
            SprFinKwotyInfDod sprFinKwotyInfDod = sprFinKwotyInfDodDAO.findsprfinkwoty(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            if (wpisView.getPodatnikObiekt().getNazwaRejestr()==null || wpisView.getPodatnikObiekt().getNazwaRejestr().equals("")) {
                Msg.msg("e","Brak w danych podatnika nazwy z rejestru KRS. Nie można generować sprawozdania");
            } else if (wpisView.getPodatnikObiekt().getImie() == null || wpisView.getPodatnikObiekt().getImie().length()!=10) {
                Msg.msg("e","Brak lub nieprawidłowy nr KRS. Nie można generować sprawozdania");
            } else if (wpisView.getPodatnikObiekt().getKodPKD()==null || wpisView.getPodatnikObiekt().getKodPKD().equals("")) {
                Msg.msg("e","Brak w danych podatnika kodu PKD działalności. Nie można generować sprawozdania");
            } else if (wpisView.getPodatnikObiekt().getKodPKD()!=null && wpisView.getPodatnikObiekt().getKodPKD().length()<5) {
                Msg.msg("e","Kodu PKD ma mniej niż 5 znaków. Nie można generować sprawozdania");
            } else if (sprFinKwotyInfDod==null) {
                Msg.msg("e","Brak danych dodatkowych do sprawozdania. Nie można generować sprawozdania");
            } else if (sprFinKwotyInfDod.getDatasporzadzenia()==null) {
                Msg.msg("e","Brak daty ODsporządzenia sprawozdania. Nie można generować sprawozdania");
            } else if (sprFinKwotyInfDod.getDataod()==null) {
                Msg.msg("e","Brak daty OD sprawozdania. Nie można generować sprawozdania");
            } else if (sprFinKwotyInfDod.getDatado()==null) {
                Msg.msg("e","Brak daty DO sprawozdania. Nie można generować sprawozdania");
            }else if (sprFinKwotyInfDod.getPlik()==null) {
                Msg.msg("e","Brak pliku z informacją dodatkową. Nie można generować sprawozdania");
            } else {
                pozycjaBRView.init();
                pozycjaBRZestawienieView.init();
                saldoAnalitykaView.init();
                planKontView.init();
                PodatnikOpodatkowanieD rokbiezacy = podatnikOpodatkowanieDAO.findOpodatkowaniePodatnikRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                PodatnikOpodatkowanieD rokuprzedni = podatnikOpodatkowanieDAO.findOpodatkowaniePodatnikRok(wpisView.getPodatnikObiekt(), wpisView.getRokUprzedniSt());
                if (rokuprzedni==null || rokuprzedni.getDolaczonydoroku()==null || rokuprzedni.getDolaczonydoroku().equals("")|| (rokuprzedni.getDolaczonydoroku()!=null&&!rokuprzedni.getDolaczonydoroku().equals(wpisView.getRokWpisuSt()))) {
                    Map<String, List<PozycjaRZiSBilans>> bilans = pozycjaBRView.obliczBilansOtwarciaBilansDataXML();
                    List<PozycjaRZiSBilans> rzis = pozycjaBRZestawienieView.obliczRZiSOtwarciaRZiSDataXML();
                    generuj(bilans, rzis);
                } else {
                    if (rokuprzedni!=null && rokuprzedni.getDolaczonydoroku().equals(wpisView.getRokWpisuSt())) {
                        pozycjaBRView.setLaczlata(true);
                        pozycjaBRView.setBilansoddnia(rokuprzedni.getDatarozpoczecia());
                        Map<String, List<PozycjaRZiSBilans>> bilans = pozycjaBRView.obliczBilansOtwarciaBilansDataXML();
                        pozycjaBRZestawienieView.setLaczlata(true);
                        pozycjaBRZestawienieView.setBilansoddnia(rokuprzedni.getDatarozpoczecia());
                        List<PozycjaRZiSBilans> rzis = pozycjaBRZestawienieView.pobierzukladprzegladRZiSWybierz();
                        generuj(bilans, rzis);
                    }
                }
                
            }
        } catch (Exception e) {
            Msg.msg("e","Wystąpił błąd podczas generowania sprawozdania finansowego "+E.e(e));
            Msg.msg("e","Czy jestWystąpił błąd podczas generowania sprawozdania finansowego "+E.e(e));
        }
    }
    
    public void drukujOP() {
        try {
            //saldoAnalitykaView.odswiezsaldoanalityczne();
            //List<SaldoKonto> saldokontolist = saldoAnalitykaView.getListaSaldoKonto();
            //Collections.sort(saldokontolist, new SaldoKontocomparator());
            SprFinKwotyInfDod sprFinKwotyInfDod = sprFinKwotyInfDodDAO.findsprfinkwoty(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            if (wpisView.getPodatnikObiekt().getNazwaRejestr()==null || wpisView.getPodatnikObiekt().getNazwaRejestr().equals("")) {
                Msg.msg("e","Brak w danych podatnika nazwy z rejestru KRS. Nie można generować sprawozdania");
            } else if (wpisView.getPodatnikObiekt().getNip() == null || wpisView.getPodatnikObiekt().getNip().length()!=10) {
                Msg.msg("e","Brak lub nieprawidłowy nr KRS. Nie można generować sprawozdania");
            } else if (sprFinKwotyInfDod==null) {
                Msg.msg("e","Brak danych dodatkowych do sprawozdania. Nie można generować sprawozdania");
            } else if (sprFinKwotyInfDod.getDatasporzadzenia()==null) {
                Msg.msg("e","Brak daty ODsporządzenia sprawozdania. Nie można generować sprawozdania");
            } else if (sprFinKwotyInfDod.getDataod()==null) {
                Msg.msg("e","Brak daty OD sprawozdania. Nie można generować sprawozdania");
            } else if (sprFinKwotyInfDod.getDatado()==null) {
                Msg.msg("e","Brak daty DO sprawozdania. Nie można generować sprawozdania");
            }else if (sprFinKwotyInfDod.getPlik()==null) {
                Msg.msg("e","Brak pliku z informacją dodatkową. Nie można generować sprawozdania");
            } else {
                pozycjaBRView.init();
                pozycjaBRZestawienieView.init();
                saldoAnalitykaView.init();
                planKontView.init();
                Map<String, List<PozycjaRZiSBilans>> bilans = pozycjaBRView.obliczBilansOtwarciaBilansDataXML();
                List<PozycjaRZiSBilans> rzis = pozycjaBRZestawienieView.obliczRZiSOtwarciaRZiSDataXML();
                generujOP(bilans, rzis);
            }
        } catch (Exception e) {
            Msg.msg("e","Wystąpił błąd podczas generowania sprawozdania finansowego "+E.e(e));
        }
    }
    
    public void generuj(Map<String, List<PozycjaRZiSBilans>> bilans, List<PozycjaRZiSBilans> rzis) {
        SprFinKwotyInfDod sprFinKwotyInfDod = sprFinKwotyInfDodDAO.findsprfinkwoty(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        if (sprFinKwotyInfDod.getNrschemy()==null) {
            Msg.msg("e","Brak numeru schemy, nie można wygenerować sprawozdania.");
        } else if (sprFinKwotyInfDod.getNrschemy().equals("1-0")) {
            generuj10(bilans, rzis, sprFinKwotyInfDod);
        } else if (sprFinKwotyInfDod.getNrschemy().equals("1-2")) {
            generuj12(bilans, rzis, sprFinKwotyInfDod);
            error.E.s("generuje 1-2");
        } else {
            Msg.msg("e","Nieznany numer schemy, nie można wygenerować sprawozdania.");
        }
    }
    
    
    public void generuj10(Map<String, List<PozycjaRZiSBilans>> bilans, List<PozycjaRZiSBilans> rzis, SprFinKwotyInfDod sprFinKwotyInfDod) {
        try {
            JednostkaInna sprawozdanie = new JednostkaInna();
            sprawozdanie.setNaglowek(SprawozdanieFin2018Bean.naglowek(sprFinKwotyInfDod.getDatasporzadzenia(), sprFinKwotyInfDod.getDataod(), sprFinKwotyInfDod.getDatado()));
            sprawozdanie.setWprowadzenieDoSprawozdaniaFinansowego(SprawozdanieFin2018Bean.wprowadzenieDoSprawozdaniaFinansowego(wpisView.getPodatnikObiekt(), sprFinKwotyInfDod.getDataod(), sprFinKwotyInfDod.getDatado()));
            sprawozdanie.setBilans(SprawozdanieFin2018BilansBean.generujbilans(bilans.get("aktywa"), bilans.get("pasywa")));
            sprawozdanie.setRZiS(SprawozdanieFin2018RZiSBean.generujrzis(rzis));
            sprawozdanie.setDodatkoweInformacjeIObjasnieniaJednstkaInna(SprawozdanieFin2018DodInfoBean.generuj(sprFinKwotyInfDod));
            String sciezka = marszajuldoplikuxml(wpisView.getPodatnikObiekt().getNip(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt(), sprawozdanie);
            String polecenie = "wydrukXML(\""+sciezka+"\")";
            PrimeFaces.current().executeScript(polecenie);
            Msg.msg("Wygenerowano sprawozdanie finansowe");
            //error.E.s("Wygenerowano sprawozdanie finansowe");
        } catch (Exception e) {
            Msg.msg("e","Wystąpił błąd. Nie wygenerowano sprawozdania finansowego");
            //error.E.s("Wystąpił błąd. Nie wygenerowano sprawozdania finansowego");
        }
    }
    
    public void generuj12(Map<String, List<PozycjaRZiSBilans>> bilans, List<PozycjaRZiSBilans> rzis, SprFinKwotyInfDod sprFinKwotyInfDod) {
        try {
            sprawozdania.v12.JednostkaInna sprawozdanie = new sprawozdania.v12.JednostkaInna();
            sprawozdanie.setNaglowek(sprawozdania.v12.SprawozdanieFin2018Bean.naglowek(sprFinKwotyInfDod.getDatasporzadzenia(), sprFinKwotyInfDod.getDataod(), sprFinKwotyInfDod.getDatado()));
            sprawozdanie.setWprowadzenieDoSprawozdaniaFinansowego(sprawozdania.v12.SprawozdanieFin2018Bean.wprowadzenieDoSprawozdaniaFinansowego(wpisView.getPodatnikObiekt(), sprFinKwotyInfDod.getDataod(), sprFinKwotyInfDod.getDatado()));
            sprawozdanie.setBilans(sprawozdania.v12.SprawozdanieFin2018BilansBean.generujbilans(bilans.get("aktywa"), bilans.get("pasywa")));
            sprawozdanie.setRZiS(sprawozdania.v12.SprawozdanieFin2018RZiSBean.generujrzis(rzis));
            sprawozdanie.setDodatkoweInformacjeIObjasnieniaJednostkaInna(sprawozdania.v12.SprawozdanieFin2018DodInfoBean.generuj(sprFinKwotyInfDod));
            String sciezka = marszajuldoplikuxml(wpisView.getPodatnikObiekt().getNip(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt(), sprawozdanie);
            String polecenie = "wydrukXML(\""+sciezka+"\")";
            PrimeFaces.current().executeScript(polecenie);
            Msg.msg("Wygenerowano sprawozdanie finansowe");
            //error.E.s("Wygenerowano sprawozdanie finansowe");
        } catch (Exception e) {
            Msg.msg("e","Wystąpił błąd. Nie wygenerowano sprawozdania finansowego");
            //error.E.s("Wystąpił błąd. Nie wygenerowano sprawozdania finansowego");
        }
    }
    
    public void generujOP(Map<String, List<PozycjaRZiSBilans>> bilans, List<PozycjaRZiSBilans> rzis) {
        SprFinKwotyInfDod sprFinKwotyInfDod = sprFinKwotyInfDodDAO.findsprfinkwoty(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        if (sprFinKwotyInfDod.getNrschemy()==null) {
            Msg.msg("e","Brak numeru schemy, nie można wygenerować sprawozdania.");
        } else if (sprFinKwotyInfDod.getNrschemy().equals("1-0")) {
            generujOP_10(bilans, rzis, sprFinKwotyInfDod);
        } else if (sprFinKwotyInfDod.getNrschemy().equals("1-2")) {
            generujOP_12(bilans, rzis, sprFinKwotyInfDod);
            error.E.s("generuje 1-2");
        } else {
            Msg.msg("e","Nieznany numer schemy, nie można wygenerować sprawozdania.");
        }
    }
    
    public void generujOP_10(Map<String, List<PozycjaRZiSBilans>> bilans, List<PozycjaRZiSBilans> rzis, SprFinKwotyInfDod sprFinKwotyInfDod) {
        try {
            sprawozdania.rok2018.JednostkaOp sprawozdanie = new sprawozdania.rok2018.JednostkaOp();
            sprawozdanie.setNaglowek(SprawozdanieFinOP2018Bean.naglowek(sprFinKwotyInfDod.getDatasporzadzenia(), sprFinKwotyInfDod.getDataod(), sprFinKwotyInfDod.getDatado()));
            sprawozdanie.setWprowadzenieDoSprawozdaniaFinansowegoJednostkaOp(SprawozdanieFinOP2018Bean.wprowadzenieDoSprawozdaniaFinansowego(wpisView.getPodatnikObiekt(), sprFinKwotyInfDod.getDataod(), sprFinKwotyInfDod.getDatado()));
            sprawozdanie.setBilansJednostkaOp(SprawozdanieFinOP2018BilansBean.generujbilans(bilans.get("aktywa"), bilans.get("pasywa")));
            sprawozdanie.setRZiSJednostkaOp(SprawozdanieFinOP2018RZiSBean.generujrzis(rzis));
            sprawozdanie.setDodatkoweInformacjeIObjasnieniaJednostkaInna(SprawozdanieFinOP2018DodInfoBean.generuj(sprFinKwotyInfDod));
            String sciezka = marszajuldoplikuxml(wpisView.getPodatnikObiekt().getNip(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt(), sprawozdanie);
            String polecenie = "wydrukXML(\""+sciezka+"\")";
            PrimeFaces.current().executeScript(polecenie);
            Msg.msg("Wygenerowano sprawozdanie finansowe");
            //error.E.s("Wygenerowano sprawozdanie finansowe");
        } catch (Exception e) {
            Msg.msg("e","Wystąpił błąd. Nie wygenerowano sprawozdania finansowego");
            //error.E.s("Wystąpił błąd. Nie wygenerowano sprawozdania finansowego");
        }
    }
    
    public void generujOP_12(Map<String, List<PozycjaRZiSBilans>> bilans, List<PozycjaRZiSBilans> rzis, SprFinKwotyInfDod sprFinKwotyInfDod) {
        try {
            sprawozdania.v12.JednostkaOp sprawozdanie = new sprawozdania.v12.JednostkaOp();
            sprawozdanie.setNaglowek(sprawozdania.v12.SprawozdanieFinOP2018Bean.naglowek(sprFinKwotyInfDod.getDatasporzadzenia(), sprFinKwotyInfDod.getDataod(), sprFinKwotyInfDod.getDatado()));
            sprawozdanie.setWprowadzenieDoSprawozdaniaFinansowegoJednostkaOp(sprawozdania.v12.SprawozdanieFinOP2018Bean.wprowadzenieDoSprawozdaniaFinansowego(wpisView.getPodatnikObiekt(), sprFinKwotyInfDod.getDataod(), sprFinKwotyInfDod.getDatado()));
            sprawozdanie.setBilansJednostkaOp(sprawozdania.v12.SprawozdanieFinOP2018BilansBean.generujbilans(bilans.get("aktywa"), bilans.get("pasywa")));
            sprawozdanie.setRZiSJednostkaOp(sprawozdania.v12.SprawozdanieFinOP2018RZiSBean.generujrzis(rzis));
            sprawozdanie.setDodatkoweInformacjeIObjasnieniaJednostkaInna(sprawozdania.v12.SprawozdanieFinOP2018DodInfoBean.generuj(sprFinKwotyInfDod));
            String sciezka = marszajuldoplikuxml(wpisView.getPodatnikObiekt().getNip(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt(), sprawozdanie);
            String polecenie = "wydrukXML(\""+sciezka+"\")";
            PrimeFaces.current().executeScript(polecenie);
            Msg.msg("Wygenerowano sprawozdanie finansowe");
            //error.E.s("Wygenerowano sprawozdanie finansowe");
        } catch (Exception e) {
            Msg.msg("e","Wystąpił błąd. Nie wygenerowano sprawozdania finansowego");
            //error.E.s("Wystąpił błąd. Nie wygenerowano sprawozdania finansowego");
        }
    }
    
    private String marszajuldoplikuxml(String nip, String mc, String rok, Object sprawozdanie) {
        String sciezka = null;
        try {
            JAXBContext context = JAXBContext.newInstance(sprawozdanie.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.mf.gov.pl/schematy/SF/DefinicjeTypySprawozdaniaFinansowe/2018/07/09/JednostkaInnaWZlotych http://www.mf.gov.pl/documents/764034/6464789/JednostkaInnaWZlotych(1)_v1-0.xsd");
            //String mainfilename = "sprawozdaniefinansowe"+podatnik.getNip()+"mcrok"+wpisView.getMiesiacWpisu()+wpisView.getRokWpisuSt()+".xml";
            String mainfilename = "sprawozdaniefinansowe"+nip+"mcrok"+mc+rok+".xml";
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/")+"resources\\xml\\";
            //String realPath = "D:\\";
            FileOutputStream fileStream = new FileOutputStream(new File(realPath+mainfilename));
            OutputStreamWriter writer = new OutputStreamWriter(fileStream, "UTF-8");
            marshaller.marshal(sprawozdanie, writer);
            sciezka = mainfilename;
        } catch (Exception ex) {
            E.e(ex);
        }
        return sciezka;
    }
    
//    private String start(WpisView wpisView) {
//        return wpisView.getRokWpisuSt()+"-01-01";
//    }
//    
//    private String stop(WpisView wpisView) {
//        return wpisView.getRokWpisuSt()+"-12-31";
//    }

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
