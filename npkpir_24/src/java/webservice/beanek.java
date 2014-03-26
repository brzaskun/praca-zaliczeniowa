/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import com.sun.xml.ws.client.ClientTransportException;
import dao.DeklaracjevatDAO;
import entity.Deklaracjevat;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.Holder;
import javax.xml.ws.WebServiceRef;
import msg.Msg;
import org.primefaces.context.RequestContext;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import service.GateService;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class beanek {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/testdok.wsdl")
    private testservice.GateService service_1;

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/dokumenty.wsdl")
    private GateService service;
    private byte[] dok;
    private final String lang;
    private final String signT;
    private Holder<String> id;
    private Holder<Integer> stat;
    private Holder<String> opis;
    private Holder<String> upo;
    //wartosci wysylka
    private String idMB;
    private String idpobierz;
    private Integer statMB;
    private String opisMB;
    private String upoMB;
    //wartosci test
    private String idMBT;
    private String idpobierzT;
    private Integer statMBT;
    private String opisMBT;
    private String upoMBT;
    @Inject
    DeklaracjevatDAO deklaracjevatDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

    public beanek() {
        id = new Holder<>();
        stat = new Holder<>();
        opis = new Holder<>();
        upo = new Holder<>();
        lang = "pl";
        signT = "PIT";
    }

    @PostConstruct
    private void init() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    // Trust always
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    // Trust always
                }
            }
        };
        // Install the all-trusting trust manager
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(beanek.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Create empty HostnameVerifier
        HostnameVerifier hv = new HostnameVerifier() {
            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        };
        try {
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (KeyManagementException ex) {
            Logger.getLogger(beanek.class.getName()).log(Level.SEVERE, null, ex);
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }

    private void requestUPO(java.lang.String refId, java.lang.String language, javax.xml.ws.Holder<java.lang.String> upo, javax.xml.ws.Holder<Integer> status, javax.xml.ws.Holder<java.lang.String> statusOpis) {
        service.GateServicePortType port = service.getGateServiceSOAP12Port();
        try {
            port.requestUPO(refId, language, upo, status, statusOpis);
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd serwera ministerstwa. Serwer nie odpowiada", "formX:msg");
        }

    }

    private void sendUnsignDocument(byte[] document, java.lang.String language, java.lang.String signatureType, javax.xml.ws.Holder<java.lang.String> refId, javax.xml.ws.Holder<Integer> status, javax.xml.ws.Holder<java.lang.String> statusOpis) {
        service.GateServicePortType port = service.getGateServiceSOAP12Port();
        port.sendUnsignDocument(document, language, signatureType, refId, status, statusOpis);
    }

    public void rob(List<Deklaracjevat> deklaracje) throws JAXBException, FileNotFoundException, ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {
        String rok = wpisView.getRokWpisu().toString();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        Deklaracjevat temp = deklaracje.get(deklaracje.size() - 1);
        if (temp.getSelected().getPozycjeszczegolowe().getPoleI62() > 0 && !temp.getDeklaracja().contains("Wniosek_VAT-ZT")) {
            Msg.msg("e", "Jest to deklaracja z wnioskiem o zwrot VAT, a nie wypełniłeś załacznika VAT-ZT. Deklaracja nie może być wysłana!", "formX:msg");
            return;
        }
        if (temp.getSelected().getCelzlozenia().equals("2") && !temp.getDeklaracja().contains("Zalacznik_ORD-ZU")) {
            Msg.msg("e", "Jest to deklaracja korygująca, a nie wypełniłeś załacznika ORD-ZU z wyjaśnieniem. Deklaracja nie może być wysłana!", "formX:msg");
            return;
        }
        String strFileContent = temp.getDeklaracja();
        System.out.println("wartosc stringu: " + strFileContent);
        String tmp = DatatypeConverter.printBase64Binary(strFileContent.getBytes("UTF-8"));
        dok = DatatypeConverter.parseBase64Binary(tmp);
        try {
            sendUnsignDocument(dok, lang, signT, id, stat, opis);
            idMB = id.value;
            idpobierz = id.value;
            statMB = stat.value;
            opisMB = opis.value;
            temp.setIdentyfikator(idMB);
            temp.setStatus(statMB.toString());
            temp.setOpis(opisMB);
            temp.setDatazlozenia(new Date());
            temp.setSporzadzil(wpisView.getWprowadzil().getImie() + " " + wpisView.getWprowadzil().getNazw());
            deklaracjevatDAO.edit(temp);
            Msg.msg("i", "Wypuszczono gołębia z deklaracja podatnika " + podatnik + " za " + rok + "-" + mc, "formX:msg");
        } catch (ClientTransportException ex1) {
            Msg.msg("e", "Nie można nawiązać połączenia z serwerem ministerstwa podczas wysyłania deklaracji podatnika " + podatnik + " za " + rok + "-" + mc, "formX:msg");
        }

    }

    public void pobierz() {
        String rok = wpisView.getRokWpisu().toString();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        try {
            requestUPO(idMB, lang, upo, stat, opis);
        } catch (ClientTransportException ex1) {
            Msg.msg("e", "Nie można nawiązać połączenia z serwerem podczas pobierania UPO podatnika " + podatnik + " za " + rok + "-" + mc);
        }
        Deklaracjevat temp = deklaracjevatDAO.findDeklaracjeDopotwierdzenia(idMB);
        if (temp.getStatus().equals(stat.value)) {
            Msg.msg("i", "Wypatruje gołębia z potwierdzeniem deklaracji podatnika ", "formX:msg");
        } else {
            if (stat.value == 200) {
                Msg.msg("i", "Gołąb wrócił  z wieścią z Warszawy. Wysyłka zakończona sukcesem. Status: " + stat.value, "formX:msg");
            } else if (stat.value.toString().startsWith("3")) {
                Msg.msg("i", "Gołąb siedzi na kawie i czeka na potwierdzenie. Status: " + stat.value, "formX:msg");
            } else {
                Msg.msg("e", "Gołąb wrócił skacowany z wieścią z Warszawy. Niepowodzenie, gdzieś jest błąd! Status: " + stat.value, "formX:msg");
            }
        }
        upoMB = upo.value;
        statMB = stat.value;
        opisMB = opis.value;
        temp.setUpo(upoMB);
        temp.setStatus(statMB.toString());
        temp.setOpis(opisMB);
        deklaracjevatDAO.edit(temp);
    }

    public void pobierzwyslane(String identyfikator) {
        String rok = wpisView.getRokWpisu().toString();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        try {
            requestUPO(idMB, lang, upo, stat, opis);
        } catch (ClientTransportException ex1) {
            Msg.msg("e", "Nie można nawiązać połączenia z serwerem podczas pobierania UPO podatnika " + podatnik + " za " + rok + "-" + mc);
        }
        Deklaracjevat temp = deklaracjevatDAO.findDeklaracjeDopotwierdzenia(identyfikator);
        if (temp.getStatus().equals(stat.value)) {
            Msg.msg("i", "Wypatruje gołębia z potwierdzeniem deklaracji podatnika ", "formX:msg");
        } else {
            if (stat.value == 200) {
                Msg.msg("i", "Gołąb wrócił  z wieścią z Warszawy. Wysyłka zakończona sukcesem. Status: " + stat.value, "formX:msg");
            } else if (stat.value.toString().startsWith("3")) {
                Msg.msg("i", "Gołąb siedzi na kawie i czeka na potwierdzenie. Status: " + stat.value, "formX:msg");
            } else {
                Msg.msg("e", "Gołąb wrócił skacowany z wieścią z Warszawy. Niepowodzenie, gdzieś jest błąd! Status: " + stat.value, "formX:msg");
            }
        }
        upoMB = upo.value;
        statMB = stat.value;
        opisMB = opis.value;
        temp.setUpo(upoMB);
        temp.setStatus(statMB.toString());
        temp.setOpis(opisMB);
        temp.setDataupo(new Date());
        deklaracjevatDAO.edit(temp);
        RequestContext.getCurrentInstance().update("formX:dokumentyLista");
    }

    private void requestUPO_Test(java.lang.String refId, java.lang.String language, javax.xml.ws.Holder<java.lang.String> upo, javax.xml.ws.Holder<Integer> status, javax.xml.ws.Holder<java.lang.String> statusOpis) {
        testservice.GateServicePortType port = service_1.getGateServiceSOAP12Port();
        try {
            port.requestUPO(refId, language, upo, status, statusOpis);
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd serwera ministerstwa. Serwer nie odpowiada", "formX:msg");
        }
    }

    private void sendUnsignDocument_Test(byte[] document, java.lang.String language, java.lang.String signatureType, javax.xml.ws.Holder<java.lang.String> refId, javax.xml.ws.Holder<Integer> status, javax.xml.ws.Holder<java.lang.String> statusOpis) {
        testservice.GateServicePortType port = service_1.getGateServiceSOAP12Port();
        port.sendUnsignDocument(document, language, signatureType, refId, status, statusOpis);
    }

    public void robtest(List<Deklaracjevat> deklaracje) throws JAXBException, FileNotFoundException, ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {
        String rok = wpisView.getRokWpisu().toString();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        Deklaracjevat temp = deklaracje.get(deklaracje.size() - 1);
        if (temp.getSelected().getPozycjeszczegolowe().getPoleI62() > 0 && !temp.getDeklaracja().contains("Wniosek_VAT-ZT")) {
            Msg.msg("e", "Jest to deklaracja z wnioskiem o zwrot VAT, a nie wypełniłeś załacznika VAT-ZT. Deklaracja nie może być wysłana!", "formX:msg");
            return;
        }
        if (temp.getSelected().getCelzlozenia().equals("2") && !temp.getDeklaracja().contains("Zalacznik_ORD-ZU")) {
            Msg.msg("e", "Jest to deklaracja korygująca, a nie wypełniłeś załacznika ORD-ZU z wyjaśnieniem. Deklaracja nie może być wysłana!", "formX:msg");
            return;
        }
        String strFileContent = temp.getDeklaracja();
        System.out.println("wartosc stringu: " + strFileContent);
        String tmp = DatatypeConverter.printBase64Binary(strFileContent.getBytes("UTF-8"));
        dok = DatatypeConverter.parseBase64Binary(tmp);
        try {
            sendUnsignDocument_Test(dok, lang, signT, id, stat, opis);
            idMBT = id.value;
            idpobierzT = id.value;
            statMBT = stat.value;
            opisMBT = opis.value;
            temp.setIdentyfikator(idMBT);
            temp.setStatus(statMBT.toString());
            temp.setOpis(opisMBT);
            temp.setDatazlozenia(new Date());
            temp.setSporzadzil(wpisView.getWprowadzil().getImie() + " " + wpisView.getWprowadzil().getNazw());
            temp.setTestowa(true);
            deklaracjevatDAO.edit(temp);
            Msg.msg("i", "Wypuszczono gołębia z deklaracja podatnika " + podatnik + " za " + rok + "-" + mc, "formX:msg");
        } catch (ClientTransportException ex1) {
            Msg.msg("e", "Nie można nawiązać połączenia z serwerem ministerstwa podczas wysyłania deklaracji podatnika " + podatnik + " za " + rok + "-" + mc, "formX:msg");
        }

    }

    public void pobierztest() {
        String rok = wpisView.getRokWpisu().toString();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        try {
            requestUPO_Test(idMBT, lang, upo, stat, opis);
        } catch (ClientTransportException ex1) {
            Msg.msg("e", "Nie można nawiązać połączenia z serwerem ministerstwa podczas pobierania UPO podatnika " + podatnik + " za " + rok + "-" + mc);
        }
        Deklaracjevat temp = deklaracjevatDAO.findDeklaracjeDopotwierdzenia(idMBT);
        if (temp.getStatus().equals(stat.value)) {
            Msg.msg("i", "Wypatruje gołębia z potwierdzeniem deklaracji podatnika ");
        } else {
            if (stat.value == 200) {
                Msg.msg("i", "Gołąb wrócił  z wieścią z Warszawy. Wysyłka zakończona sukcesem. Status: " + stat.value);
            } else if (stat.value.toString().startsWith("3")) {
                Msg.msg("i", "Gołąb siedzi na kawie i czeka na potwierdzenie. Status: " + stat.value, "formX:msg");
            } else {
                Msg.msg("e", "Gołąb wrócił skacowany z wieścią z Warszawy. Niepowodzenie, gdzieś jest błąd! Status: " + stat.value);
            }
        }
        upoMBT = upo.value;
        statMBT = stat.value;
        opisMBT = opis.value;
        temp.setUpo(upoMBT);
        temp.setStatus(statMBT.toString());
        temp.setOpis(opisMBT);
        temp.setDataupo(new Date());
        deklaracjevatDAO.edit(temp);

    }

    public void pobierzwyslanetest(String identyfikator) {
        String rok = wpisView.getRokWpisu().toString();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        try {
            requestUPO_Test(idMBT, lang, upo, stat, opis);
        } catch (ClientTransportException ex1) {
            Msg.msg("e", "Nie można nawiązać połączenia z serwerem ministerstwa podczas pobierania UPO podatnika " + podatnik + " za " + rok + "-" + mc);
        }
        Deklaracjevat temp = deklaracjevatDAO.findDeklaracjeDopotwierdzenia(identyfikator);
        if (temp.getStatus().equals(stat.value)) {
            Msg.msg("i", "Wypatruje gołębia z potwierdzeniem deklaracji podatnika ", "formX:msg");
        } else {
            if (stat.value == 200) {
                Msg.msg("i", "Gołąb wrócił  z wieścią z Warszawy. Wysyłka zakończona sukcesem. Status: " + stat.value, "formX:msg");
            } else if (stat.value.toString().startsWith("3")) {
                Msg.msg("i", "Gołąb siedzi na kawie i czeka na potwierdzenie. Status: " + stat.value, "formX:msg");
            } else {
                Msg.msg("e", "Gołąb wrócił skacowany z wieścią z Warszawy. Niepowodzenie, gdzieś jest błąd! Status: " + stat.value, "formX:msg");
            }
        }
        upoMBT = upo.value;
        statMBT = stat.value;
        opisMBT = opis.value;
        temp.setUpo(upoMBT);
        temp.setStatus(statMBT.toString());
        temp.setOpis(opisMBT);
        deklaracjevatDAO.edit(temp);
        RequestContext.getCurrentInstance().update("formX:dokumentyLista");
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    public String getIdMBT() {
        return idMBT;
    }

    public void setIdMBT(String idMBT) {
        this.idMBT = idMBT;
    }

    public String getIdMB() {
        return idMB;
    }

    public void setIdMB(String idMB) {
        this.idMB = idMB;
    }

    public Integer getStatMB() {
        return statMB;
    }

    public void setStatMB(Integer statMB) {
        this.statMB = statMB;
    }

    public String getOpisMB() {
        return opisMB;
    }

    public void setOpisMB(String opisMB) {
        this.opisMB = opisMB;
    }

    public Holder<String> getUpo() {
        return upo;
    }

    public String getIdpobierz() {
        return idpobierz;
    }

    public void setIdpobierz(String idpobierz) {
        this.idpobierz = idpobierz;
    }

    public String getUpoMB() {
        return upoMB;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public String getIdpobierzT() {
        return idpobierzT;
    }

    public void setIdpobierzT(String idpobierzT) {
        this.idpobierzT = idpobierzT;
    }

    public Integer getStatMBT() {
        return statMBT;
    }

    public void setStatMBT(Integer statMBT) {
        this.statMBT = statMBT;
    }

    public String getOpisMBT() {
        return opisMBT;
    }

    public void setOpisMBT(String opisMBT) {
        this.opisMBT = opisMBT;
    }

    public String getUpoMBT() {
        return upoMBT;
    }

    public void setUpoMBT(String upoMBT) {
        this.upoMBT = upoMBT;
    }

//</editor-fold>
    static public void main(String[] args) {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("c:/uslugi/Deklaracja.xml");
            StringWriter stringWriter = new StringWriter();
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(doc), new StreamResult(stringWriter));
            String strFileContent = stringWriter.toString(); //This is string data of xml file
            System.out.println(strFileContent);
        } catch (Exception e) {
            e.getMessage();
        }
    }

}
