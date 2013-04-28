/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

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
import java.sql.Timestamp;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
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
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.bind.DatatypeConverter;
import javax.xml.ws.WebServiceRef;
import msg.Msg;
import org.primefaces.context.RequestContext;
import service.GateService;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
public class beanek {
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/testdok.wsdl")
    private testservice.GateService service_1;

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/dokumenty.wsdl")
    private GateService service;
    private byte[] dok;
    private String lang;
    private String signT;
    private Holder<String> id;
    private Holder<Integer> stat;
    private Holder<String> opis;
    private Holder<String> upo;
    private String idpobierz;
    private String idMB;
    private Integer statMB;
    private String opisMB;
    private String upoMB;
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
    private void init() throws NoSuchAlgorithmException, KeyManagementException {
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
        SSLContext sc = SSLContext.getInstance("SSL");
        // Create empty HostnameVerifier
        HostnameVerifier hv = new HostnameVerifier() {
            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        };
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }

    private void requestUPO(java.lang.String refId, java.lang.String language, javax.xml.ws.Holder<java.lang.String> upo, javax.xml.ws.Holder<Integer> status, javax.xml.ws.Holder<java.lang.String> statusOpis) {
        service.GateServicePortType port = service.getGateServiceSOAP12Port();
        try {
            port.requestUPO(refId, language, upo, status, statusOpis);
        } catch (Exception e) {
            Msg.msg("e","Wystąpił błąd serwera ministerstwa. Serwer nie odpowiada","formX:msg");
        }
        
    }

    private void sendUnsignDocument(byte[] document, java.lang.String language, java.lang.String signatureType, javax.xml.ws.Holder<java.lang.String> refId, javax.xml.ws.Holder<Integer> status, javax.xml.ws.Holder<java.lang.String> statusOpis) {
        service.GateServicePortType port = service.getGateServiceSOAP12Port();
        port.sendUnsignDocument(document, language, signatureType, refId, status, statusOpis);
    }

    public void rob() throws JAXBException, FileNotFoundException, ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {
        String rok = wpisView.getRokWpisu().toString();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        Deklaracjevat temp = deklaracjevatDAO.findDeklaracjeDowyslania(podatnik);
        if(temp.getSelected().getCelzlozenia().equals("2")&&!temp.getDeklaracja().contains("Zalacznik")){
            Msg.msg("e", "Jest to deklaracja korygująca, a nie wypełniłeś załacznika z wyjaśnieniem. Deklaracja nie może być wysłąna!", "formX:msg");
            return;
        }
        String strFileContent = temp.getDeklaracja();
        System.out.println("wartosc stringu: " + strFileContent);
        String tmp = DatatypeConverter.printBase64Binary(strFileContent.getBytes("UTF-8"));
        dok = DatatypeConverter.parseBase64Binary(tmp);
        sendUnsignDocument(dok, lang, signT, id, stat, opis);
        idMB = id.value;
        idpobierz = id.value;
        statMB = stat.value;
        opisMB = opis.value;
        temp.setIdentyfikator(idMB);
        temp.setStatus(statMB.toString());
        temp.setOpis(opisMB);
        temp.setDatazlozenia(new Date());
        temp.setSporzadzil(wpisView.getWprowadzil().getImie()+" "+wpisView.getWprowadzil().getNazw());
        deklaracjevatDAO.edit(temp);
        Msg.msg("i", "Wypuszczono gołębia z deklaracja podatnika " + podatnik + " za " + rok + "-" + mc, "formX:msg");

    }

    public void pobierz() {
      requestUPO(idpobierz, lang, upo, stat, opis);
        Deklaracjevat temp = deklaracjevatDAO.findDeklaracjeDopotwierdzenia(idpobierz);
        if(temp.getStatus().equals(stat.value)){
            Msg.msg("i", "Wypatruje gołębia z potwierdzeniem deklaracji podatnika ", "formX:msg");
        } else {
            if(stat.value == 200){
                Msg.msg("i", "Gołąb wrócił  z wieścią z Warszawy. Wysyłka zakończona sukcesem. Status: "+stat.value, "formX:msg");
            } else if (stat.value.toString().startsWith("3") ){
                Msg.msg("i", "Gołąb siedzi na kawie i czeka na potwierdzenie. Status: "+stat.value, "formX:msg");
            } else {
                Msg.msg("e", "Gołąb wrócił skacowany z wieścią z Warszawy. Niepowodzenie, gdzieś jest błąd! Status: "+stat.value, "formX:msg");
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
        requestUPO(identyfikator, lang, upo, stat, opis);
        Deklaracjevat temp = deklaracjevatDAO.findDeklaracjeDopotwierdzenia(identyfikator);
        if(temp.getStatus().equals(stat.value)){
            Msg.msg("i", "Wypatruje gołębia z potwierdzeniem deklaracji podatnika ", "formX:msg");
        } else {
            if(stat.value == 200){
                Msg.msg("i", "Gołąb wrócił  z wieścią z Warszawy. Wysyłka zakończona sukcesem. Status: "+stat.value, "formX:msg");
            } else if (stat.value.toString().startsWith("3") ){
                Msg.msg("i", "Gołąb siedzi na kawie i czeka na potwierdzenie. Status: "+stat.value, "formX:msg");
            } else {
                Msg.msg("e", "Gołąb wrócił skacowany z wieścią z Warszawy. Niepowodzenie, gdzieś jest błąd! Status: "+stat.value, "formX:msg");
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
    
     public void robtest() throws JAXBException, FileNotFoundException, ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {
        String rok = wpisView.getRokWpisu().toString();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        Deklaracjevat temp = deklaracjevatDAO.findDeklaracjeDowyslania(podatnik);
        if(temp.getSelected().getCelzlozenia().equals("2")&&!temp.getDeklaracja().contains("Zalacznik")){
            Msg.msg("e", "Jest to deklaracja korygująca, a nie wypełniłeś załacznika z wyjaśnieniem. Deklaracja nie może być wysłąna!", "formX:msg");
            return;
        }
        String strFileContent = temp.getDeklaracja();
        System.out.println("wartosc stringu: " + strFileContent);
        String tmp = DatatypeConverter.printBase64Binary(strFileContent.getBytes("UTF-8"));
        dok = DatatypeConverter.parseBase64Binary(tmp);
        sendUnsignDocument_1(dok, lang, signT, id, stat, opis);
        idMB = id.value;
        idpobierz = id.value;
        statMB = stat.value;
        opisMB = opis.value;
        temp.setIdentyfikator(idMB);
        temp.setStatus(statMB.toString());
        temp.setOpis(opisMB);
        temp.setDatazlozenia(new Date());
        temp.setSporzadzil(wpisView.getWprowadzil().getImie()+" "+wpisView.getWprowadzil().getNazw());
        deklaracjevatDAO.edit(temp);
        Msg.msg("i", "Wypuszczono gołębia z deklaracja podatnika " + podatnik + " za " + rok + "-" + mc, "formX:msg");

    }

    public void pobierztest() {
        requestUPO_1(idpobierz, lang, upo, stat, opis);
        Deklaracjevat temp = deklaracjevatDAO.findDeklaracjeDopotwierdzenia(idpobierz);
        if(temp.getStatus().equals(stat.value)){
            Msg.msg("i", "Wypatruje gołębia z potwierdzeniem deklaracji podatnika ", "formX:msg");
        } else {
            if(stat.value == 200){
                Msg.msg("i", "Gołąb wrócił  z wieścią z Warszawy. Wysyłka zakończona sukcesem. Status: "+stat.value, "formX:msg");
            } else if (stat.value.toString().startsWith("3") ){
                Msg.msg("i", "Gołąb siedzi na kawie i czeka na potwierdzenie. Status: "+stat.value, "formX:msg");
            } else {
                Msg.msg("e", "Gołąb wrócił skacowany z wieścią z Warszawy. Niepowodzenie, gdzieś jest błąd! Status: "+stat.value, "formX:msg");
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
        
        
    }

    public void pobierzwyslanetest(String identyfikator) {
        requestUPO_1(identyfikator, lang, upo, stat, opis);
        Deklaracjevat temp = deklaracjevatDAO.findDeklaracjeDopotwierdzenia(identyfikator);
        if(temp.getStatus().equals(stat.value)){
            Msg.msg("i", "Wypatruje gołębia z potwierdzeniem deklaracji podatnika ", "formX:msg");
        } else {
            if(stat.value == 200){
                Msg.msg("i", "Gołąb wrócił  z wieścią z Warszawy. Wysyłka zakończona sukcesem. Status: "+stat.value, "formX:msg");
            } else if (stat.value.toString().startsWith("3") ){
                Msg.msg("i", "Gołąb siedzi na kawie i czeka na potwierdzenie. Status: "+stat.value, "formX:msg");
            } else {
                Msg.msg("e", "Gołąb wrócił skacowany z wieścią z Warszawy. Niepowodzenie, gdzieś jest błąd! Status: "+stat.value, "formX:msg");
            }
        }
        upoMB = upo.value;
        statMB = stat.value;
        opisMB = opis.value;
        temp.setUpo(upoMB);
        temp.setStatus(statMB.toString());
        temp.setOpis(opisMB);
        deklaracjevatDAO.edit(temp);
        RequestContext.getCurrentInstance().update("formX:dokumentyLista");
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

    private void requestUPO_1(java.lang.String refId, java.lang.String language, javax.xml.ws.Holder<java.lang.String> upo, javax.xml.ws.Holder<Integer> status, javax.xml.ws.Holder<java.lang.String> statusOpis) {
        testservice.GateServicePortType port = service_1.getGateServiceSOAP12Port();
        port.requestUPO(refId, language, upo, status, statusOpis);
    }

    private void sendUnsignDocument_1(byte[] document, java.lang.String language, java.lang.String signatureType, javax.xml.ws.Holder<java.lang.String> refId, javax.xml.ws.Holder<Integer> status, javax.xml.ws.Holder<java.lang.String> statusOpis) {
        testservice.GateServicePortType port = service_1.getGateServiceSOAP12Port();
        port.sendUnsignDocument(document, language, signatureType, refId, status, statusOpis);
    }
}
