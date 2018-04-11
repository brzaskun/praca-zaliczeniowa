/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import beansVAT.EDeklaracjeObslugaBledow;
import com.sun.xml.ws.client.ClientTransportException;
import dao.Deklaracjavat27DAO;
import dao.DeklaracjavatUEDAO;
import dao.DeklaracjevatDAO;
import dao.DokDAO;
import daoFK.DokDAOfk;
import entity.Deklaracjavat27;
import entity.DeklaracjavatUE;
import entity.Deklaracjevat;
import entity.Dok;
import entity.Vat27;
import entityfk.Dokfk;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringWriter;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Iterator;
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
import view.DeklaracjevatView;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class beanek  implements Serializable {
    
    private static final long serialVersionUID = 1L;

//</editor-fold>
    public static void main(String[] args) {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("c:/uslugi/Deklaracja.xml");
            StringWriter stringWriter = new StringWriter();
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(new DOMSource(doc), new StreamResult(stringWriter));
            String strFileContent = stringWriter.toString(); //This is string data of xml file
        } catch (Exception e) {
            e.getMessage();
        }
    }

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
    private String statMB;
    private String opisMB;
    private String upoMB;
    //wartosci test
    private String idMBT;
    private String idpobierzT;
    private String statMBT;
    private String opisMBT;
    private String upoMBT;
    @Inject
    DeklaracjevatDAO deklaracjevatDAO;
    @Inject
    private DeklaracjavatUEDAO deklaracjavatUEDAO;
    @Inject
    private Deklaracjavat27DAO deklaracjavat27DAO;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private DokDAO dokDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value="#{deklaracjevatView}")
    private DeklaracjevatView deklaracjevatView;

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

    private void sendUnsignDocument(byte[] document, java.lang.String language, java.lang.String signT, javax.xml.ws.Holder<java.lang.String> id, javax.xml.ws.Holder<Integer> stat, javax.xml.ws.Holder<java.lang.String> opis) {
        service.GateServicePortType port = service.getGateServiceSOAP12Port();
        port.sendUnsignDocument(document, language, signT, id, stat, opis);
    }
    
    private void sendSignDocument(byte[] dok, javax.xml.ws.Holder<java.lang.String> id, javax.xml.ws.Holder<Integer> stat, javax.xml.ws.Holder<java.lang.String> opis) {
        service.GateServicePortType port2 = service.getGateServiceSOAP12Port();
        port2.sendDocument(dok, id, stat, opis);
    }

    public void rob27(Deklaracjavat27 deklaracja, List<Dok> listadok, List<Dokfk> listadokfk) throws JAXBException, FileNotFoundException, ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {
        try {
            dok = deklaracja.getDeklaracjapodpisana();
            sendSignDocument(dok, id, stat, opis);
            idMB = id.value;
            idpobierz = id.value;
            List<String> komunikat = null;
            opisMB = opis.value;
            komunikat = EDeklaracjeObslugaBledow.odpowiedznakodserwera(stat.value);
            if (komunikat.size() > 1) {
                    Msg.msg(komunikat.get(0), komunikat.get(1));
                    opisMB = komunikat.get(1);
            }
            upoMB = upo.value;
            statMB = stat.value + " "+opis.value;
            deklaracja.setIdentyfikator(idMB);
            deklaracja.setStatus(String.valueOf(stat.value));
            deklaracja.setOpis(opisMB);
            deklaracja.setDatazlozenia(new Date());
            deklaracja.setSporzadzil(wpisView.getWprowadzil().getImie() + " " + wpisView.getWprowadzil().getNazw());
            deklaracja.setTestowa(false);
            for (Iterator<Vat27> it  = deklaracja.getPozycje().iterator(); it.hasNext();) {
                if (it.next().getKontrahent()==null) {
                    it.remove();
                }
            }
            deklaracjavat27DAO.dodaj(deklaracja);
            edytujdok(listadok, listadokfk);
            Msg.msg("i", "Wypuszczono testowego gołębia z deklaracja podatnika " + wpisView.getPodatnikWpisu() + " za " + wpisView.getRokWpisuSt() + "-" + wpisView.getMiesiacWpisu());
        } catch (ClientTransportException ex1) {
            Msg.msg("e", "Nie można nawiązać połączenia z serwerem ministerstwa podczas wysyłania deklaracji podatnika " + wpisView.getPodatnikWpisu() + " za " + wpisView.getRokWpisuSt() + "-" + wpisView.getMiesiacWpisu());
        }

    }
    
    public void robUE(DeklaracjavatUE wysylanaDeklaracja, List<Dok> listadok, List<Dokfk> listadokfk) throws JAXBException, FileNotFoundException, ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {
        try {
            dok = wysylanaDeklaracja.getDeklaracjapodpisana();
            sendSignDocument(dok, id, stat, opis);
            idMB = id.value;
            idpobierz = id.value;
            List<String> komunikat = null;
            opisMB = opis.value;
            komunikat = EDeklaracjeObslugaBledow.odpowiedznakodserwera(stat.value);
            if (komunikat.size() > 1) {
                    Msg.msg(komunikat.get(0), komunikat.get(1));
                    opisMB = komunikat.get(1);
            }
            upoMB = upo.value;
            statMB = stat.value + " "+opis.value;
            wysylanaDeklaracja.setIdentyfikator(idMB);
            wysylanaDeklaracja.setStatus(statMB.toString());
            wysylanaDeklaracja.setOpis(opisMB);
            wysylanaDeklaracja.setDatazlozenia(new Date());
            wysylanaDeklaracja.setSporzadzil(wpisView.getWprowadzil().getImie() + " " + wpisView.getWprowadzil().getNazw());
            wysylanaDeklaracja.setTestowa(false);
            deklaracjavatUEDAO.dodaj(wysylanaDeklaracja);
            edytujdok(listadok, listadokfk);
            Msg.msg("i", "Wypuszczono gołębia z deklaracja podatnika " + wpisView.getPodatnikWpisu() + " za " + wpisView.getRokWpisuSt() + "-" + wpisView.getMiesiacWpisu());
        } catch (ClientTransportException ex1) {
            Msg.msg("e", "Nie można nawiązać połączenia z serwerem ministerstwa podczas wysyłania deklaracji podatnika " + wpisView.getPodatnikWpisu() + " za " + wpisView.getRokWpisuSt() + "-" + wpisView.getMiesiacWpisu());
        }
    }
    
     private void edytujdok(List<Dok> listadok, List<Dokfk> listadokfk) {
        if(listadok!= null && listadok.size()>0) {
            if (listadok!=null && listadok.size()>0) {
                dokDAO.editList(listadok);
            } else if (listadokfk !=null && listadokfk.size()>0) {
                dokDAOfk.editList(listadokfk);
            }
        }
    }
    
    public void rob(List<Deklaracjevat> deklaracje) throws JAXBException, FileNotFoundException, ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {
        String rok = wpisView.getRokWpisu().toString();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        Deklaracjevat wysylanaDeklaracja = deklaracje.get(deklaracje.size() - 1);
        if (wysylanaDeklaracja.getSelected().getPozycjeszczegolowe().getPoleI62() > 0 && !wysylanaDeklaracja.getDeklaracja().contains("Wniosek_VAT-ZT")) {
            Msg.msg("e", "Jest to deklaracja z wnioskiem o zwrot VAT, a nie wypełniłeś załacznika VAT-ZT. Deklaracja nie może być wysłana!", "formX:msg");
            return;
        }
//        if (wysylanaDeklaracja.getSelected().getCelzlozenia().equals("2") && !wysylanaDeklaracja.getDeklaracja().contains("Zalacznik_ORD-ZU")) {
//            Msg.msg("e", "Jest to deklaracja korygująca, a nie wypełniłeś załacznika ORD-ZU z wyjaśnieniem. Deklaracja nie może być wysłana!", "formX:msg");
//            return;
//        }
        String strFileContent = wysylanaDeklaracja.getDeklaracja();
        if (strFileContent != null) {
            String tmp = DatatypeConverter.printBase64Binary(strFileContent.getBytes("UTF-8"));
            dok = DatatypeConverter.parseBase64Binary(tmp);
        } else {
            
        }
        try {
            if (wysylanaDeklaracja.isJestcertyfikat()) {
                dok = wysylanaDeklaracja.getDeklaracjapodpisana();
                sendSignDocument(dok, id, stat, opis);
            } else {
                sendUnsignDocument(dok, lang, signT, id, stat, opis);
            }
            idMB = id.value;
            idpobierz = id.value;
            List<String> komunikat = null;
            opisMB = opis.value;
            komunikat = EDeklaracjeObslugaBledow.odpowiedznakodserwera(stat.value);
            if (komunikat.size() > 1) {
                    Msg.msg(komunikat.get(0), komunikat.get(1));
                    opisMB = komunikat.get(1);
            }
            upoMB = upo.value;
            statMB = stat.value + " "+opis.value;
            wysylanaDeklaracja.setIdentyfikator(idMB);
            wysylanaDeklaracja.setStatus(statMB.toString());
            wysylanaDeklaracja.setOpis(opisMB);
            wysylanaDeklaracja.setDatazlozenia(new Date());
            wysylanaDeklaracja.setSporzadzil(wpisView.getWprowadzil().getImie() + " " + wpisView.getWprowadzil().getNazw());
            deklaracjevatDAO.edit(wysylanaDeklaracja);
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
        Deklaracjevat temp = deklaracjevatDAO.findDeklaracjeDopotwierdzenia(idMB, wpisView);
        List<String> komunikat = null;
        if (temp.getStatus().equals(stat.value)) {
            Msg.msg("i", "Wypatruje gołębia z potwierdzeniem deklaracji podatnika ", "formX:msg");
        } else {
            komunikat = EDeklaracjeObslugaBledow.odpowiedznakodserwera(stat.value);
            if (komunikat.size() > 1) {
                Msg.msg(komunikat.get(0), komunikat.get(1));
            }
        }
        upoMB = upo.value;
        statMB = stat.value + " "+opis.value;
        opisMB = komunikat.get(1);
        temp.setUpo(upoMB);
        temp.setStatus(String.valueOf(stat.value));
        temp.setOpis(opisMB);
        deklaracjevatDAO.edit(temp);
    }

    public void pobierzwyslane(Deklaracjevat sprawdzanadeklaracja) {
        String rok = wpisView.getRokWpisu().toString();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        try {
            requestUPO(sprawdzanadeklaracja.getIdentyfikator(), lang, upo, stat, opis);
        } catch (ClientTransportException ex1) {
            Msg.msg("e", "Nie można nawiązać połączenia z serwerem podczas pobierania UPO podatnika " + podatnik + " za " + rok + "-" + mc);
        }
        List<String> komunikat = null;
        if (sprawdzanadeklaracja.getStatus().equals(stat.value)) {
            Msg.msg("i", "Wypatruje gołębia z potwierdzeniem deklaracji podatnika ");
        } else {
            komunikat = EDeklaracjeObslugaBledow.odpowiedznakodserwera(stat.value);
            if (komunikat.size() > 1) {
                Msg.msg(komunikat.get(0), komunikat.get(1));
            }
        }
        upoMBT = upo.value;
        statMBT = stat.value + " "+opis.value;
        opisMBT = komunikat.get(1);
        sprawdzanadeklaracja.setUpo(upoMBT);
        sprawdzanadeklaracja.setStatus(String.valueOf(stat.value));
        sprawdzanadeklaracja.setOpis(opisMBT);
        sprawdzanadeklaracja.setDataupo(new Date());
        if (stat.value == 200) {
            deklaracjevatView.getWyslaneniepotwierdzone().remove(sprawdzanadeklaracja);
            deklaracjevatView.getWyslanenormalne().add(sprawdzanadeklaracja);
        } else if (String.valueOf(stat.value).startsWith("4")) {
            deklaracjevatView.getWyslaneniepotwierdzone().remove(sprawdzanadeklaracja);
            deklaracjevatView.getWyslanezbledem().add(sprawdzanadeklaracja);
        }
        deklaracjevatDAO.edit(sprawdzanadeklaracja);
    }
    
     public void pobierzwyslaneUE(DeklaracjavatUE sprawdzanadeklaracja) {
        String rok = wpisView.getRokWpisu().toString();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        try {
            requestUPO(sprawdzanadeklaracja.getIdentyfikator(), lang, upo, stat, opis);
        } catch (ClientTransportException ex1) {
            Msg.msg("e", "Nie można nawiązać połączenia z serwerem podczas pobierania UPO podatnika " + podatnik + " za " + rok + "-" + mc);
        }
        List<String> komunikat = null;
        if (sprawdzanadeklaracja.getStatus().equals(stat.value)) {
            Msg.msg("i", "Wypatruje gołębia z potwierdzeniem deklaracji podatnika ");
        } else {
            komunikat = EDeklaracjeObslugaBledow.odpowiedznakodserwera(stat.value);
            if (komunikat.size() > 1) {
                Msg.msg(komunikat.get(0), komunikat.get(1));
            }
        }
        upoMBT = upo.value;
        statMBT = stat.value + " "+opis.value;
        opisMBT = komunikat.get(1);
        sprawdzanadeklaracja.setUpo(upoMBT);
        sprawdzanadeklaracja.setStatus(String.valueOf(stat.value));
        sprawdzanadeklaracja.setOpis(opisMBT);
        sprawdzanadeklaracja.setDataupo(new Date());
        deklaracjavatUEDAO.edit(sprawdzanadeklaracja);
    }
     
     public void pobierzwyslane27(Deklaracjavat27 sprawdzanadeklaracja) {
        String rok = wpisView.getRokWpisu().toString();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        try {
            requestUPO(sprawdzanadeklaracja.getIdentyfikator(), lang, upo, stat, opis);
        } catch (ClientTransportException ex1) {
            Msg.msg("e", "Nie można nawiązać testowego połączenia z serwerem ministerstwa podczas pobierania UPO podatnika " + podatnik + " za " + rok + "-" + mc);
        }
        List<String> komunikat = null;
        if (sprawdzanadeklaracja.getStatus().equals(stat.value)) {
            Msg.msg("i", "Wypatruje testowego gołębia z potwierdzeniem deklaracji podatnika ");
        } else {
            komunikat = EDeklaracjeObslugaBledow.odpowiedznakodserwera(stat.value);
            if (komunikat.size() > 1) {
                Msg.msg(komunikat.get(0), komunikat.get(1));
            }
        }
        upoMBT = upo.value;
        statMBT = stat.value + " "+opis.value;
        opisMBT = komunikat.get(1);
        sprawdzanadeklaracja.setUpo(upoMBT);
        sprawdzanadeklaracja.setStatus(String.valueOf(stat.value));
        sprawdzanadeklaracja.setOpis(opisMBT);
        sprawdzanadeklaracja.setDataupo(new Date());
        deklaracjavat27DAO.edit(sprawdzanadeklaracja);
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

    private void sendSignDocument_Test(byte[] document, javax.xml.ws.Holder<java.lang.String> refId, javax.xml.ws.Holder<Integer> status, javax.xml.ws.Holder<java.lang.String> statusOpis) {
        testservice.GateServicePortType port = service_1.getGateServiceSOAP12Port();
        port.sendDocument(document, refId, status, statusOpis);
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
        if (strFileContent != null) {
            String tmp = DatatypeConverter.printBase64Binary(strFileContent.getBytes("UTF-8"));
            dok = DatatypeConverter.parseBase64Binary(tmp);
        } else {
            
        }
        try {
            if (temp.isJestcertyfikat()) {
                if (temp.getDeklaracjapodpisana() != null) {
                    dok = temp.getDeklaracjapodpisana();
                }
                sendSignDocument_Test(dok, id, stat, opis);
            } else {
                sendUnsignDocument_Test(dok, lang, signT, id, stat, opis);
            }
            idMBT = id.value;
            idpobierzT = id.value;
            List<String> komunikat = null;
            opisMBT = opis.value;
            komunikat = EDeklaracjeObslugaBledow.odpowiedznakodserwera(stat.value);
            if (komunikat.size() > 1) {
                    Msg.msg(komunikat.get(0), komunikat.get(1));
                    opisMBT = komunikat.get(1);
            }
            upoMBT = upo.value;
            statMBT = stat.value + " "+opis.value;
            temp.setIdentyfikator(idMBT);
            temp.setStatus(String.valueOf(stat.value));
            temp.setOpis(opisMBT);
            temp.setDatazlozenia(new Date());
            temp.setSporzadzil(wpisView.getWprowadzil().getImie() + " " + wpisView.getWprowadzil().getNazw());
            temp.setTestowa(true);
            deklaracjevatDAO.edit(temp);
            Msg.msg("i", "Wypuszczono testowego gołębia z deklaracja podatnika " + podatnik + " za " + rok + "-" + mc, "formX:msg");
        } catch (ClientTransportException ex1) {
            Msg.msg("e", "Nie można nawiązać połączenia z serwerem ministerstwa podczas wysyłania deklaracji podatnika " + podatnik + " za " + rok + "-" + mc, "formX:msg");
        }

    }
    
    public void robtestUE(DeklaracjavatUE deklaracja) throws JAXBException, FileNotFoundException, ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {
        try {
            dok = deklaracja.getDeklaracjapodpisana();
            sendSignDocument_Test(dok, id, stat, opis);
            idMBT = id.value;
            idpobierzT = id.value;
            List<String> komunikat = null;
            opisMBT = opis.value;
            komunikat = EDeklaracjeObslugaBledow.odpowiedznakodserwera(stat.value);
            if (komunikat.size() > 1) {
                    Msg.msg(komunikat.get(0), komunikat.get(1));
                    opisMBT = komunikat.get(1);
            }
            upoMBT = upo.value;
            statMBT = stat.value + " "+opis.value;
            deklaracja.setIdentyfikator(idMBT);
            deklaracja.setStatus(String.valueOf(stat.value));
            deklaracja.setOpis(opisMBT);
            deklaracja.setDatazlozenia(new Date());
            deklaracja.setSporzadzil(wpisView.getWprowadzil().getImie() + " " + wpisView.getWprowadzil().getNazw());
            deklaracja.setTestowa(true);
            deklaracjavatUEDAO.edit(deklaracja);
            Msg.msg("i", "Wypuszczono testowego gołębia z deklaracja podatnika " + wpisView.getPodatnikWpisu() + " za " + wpisView.getRokWpisuSt() + "-" + wpisView.getMiesiacWpisu());
        } catch (ClientTransportException ex1) {
            Msg.msg("e", "Nie można nawiązać połączenia z serwerem ministerstwa podczas wysyłania deklaracji podatnika " + wpisView.getPodatnikWpisu() + " za " + wpisView.getRokWpisuSt() + "-" + wpisView.getMiesiacWpisu());
        }

    }
    
    public void robtest27(Deklaracjavat27 deklaracja) throws JAXBException, FileNotFoundException, ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {
        try {
            dok = deklaracja.getDeklaracjapodpisana();
            sendSignDocument_Test(dok, id, stat, opis);
            idMBT = id.value;
            idpobierzT = id.value;
            List<String> komunikat = null;
            opisMBT = opis.value;
            komunikat = EDeklaracjeObslugaBledow.odpowiedznakodserwera(stat.value);
            if (komunikat.size() > 1) {
                    Msg.msg(komunikat.get(0), komunikat.get(1));
                    opisMBT = komunikat.get(1);
            }
            upoMBT = upo.value;
            statMBT = stat.value + " "+opis.value;
            deklaracja.setIdentyfikator(idMBT);
            deklaracja.setStatus(String.valueOf(stat.value));
            deklaracja.setOpis(opisMBT);
            deklaracja.setDatazlozenia(new Date());
            deklaracja.setSporzadzil(wpisView.getWprowadzil().getImie() + " " + wpisView.getWprowadzil().getNazw());
            deklaracja.setTestowa(true);
            deklaracjavat27DAO.edit(deklaracja);
            Msg.msg("i", "Wypuszczono testowego gołębia z deklaracja podatnika " + wpisView.getPodatnikWpisu() + " za " + wpisView.getRokWpisuSt() + "-" + wpisView.getMiesiacWpisu());
        } catch (ClientTransportException ex1) {
            Msg.msg("e", "Nie można nawiązać połączenia z serwerem ministerstwa podczas wysyłania deklaracji podatnika " + wpisView.getPodatnikWpisu() + " za " + wpisView.getRokWpisuSt() + "-" + wpisView.getMiesiacWpisu());
        }

    }
    
    public void wysylkaReczna(List<Deklaracjevat> deklaracje)  {
        String rok = wpisView.getRokWpisu().toString();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        Deklaracjevat wysylanaDeklaracja = deklaracje.get(deklaracje.size() - 1);
        if (wysylanaDeklaracja.getSelected().getPozycjeszczegolowe().getPoleI62() > 0 && !wysylanaDeklaracja.getDeklaracja().contains("Wniosek_VAT-ZT")) {
            Msg.msg("e", "Jest to deklaracja z wnioskiem o zwrot VAT, a nie wypełniłeś załacznika VAT-ZT. Deklaracja nie może być wysłana!", "formX:msg");
            return;
        }
//        if (wysylanaDeklaracja.getSelected().getCelzlozenia().equals("2") && !wysylanaDeklaracja.getDeklaracja().contains("Zalacznik_ORD-ZU")) {
//            Msg.msg("e", "Jest to deklaracja korygująca, a nie wypełniłeś załacznika ORD-ZU z wyjaśnieniem. Deklaracja nie może być wysłana!", "formX:msg");
//            return;
//        }
        try {
            wysylanaDeklaracja.setIdentyfikator(wpisView.getMiesiacWpisu()+wpisView.getRokWpisuSt()+wpisView.findNazwaPodatnika()+wpisView.getWprowadzil().getLogin());
            wysylanaDeklaracja.setIdentyfikator(idpobierzT);
            wysylanaDeklaracja.setStatus("200");
            statMBT = wysylanaDeklaracja.getStatus();
            wysylanaDeklaracja.setOpis("Udana rejestracja wysyłki poza systemem");
            opisMBT = wysylanaDeklaracja.getOpis();
            wysylanaDeklaracja.setUpo("Deklaracja wysłana poza systemem. Zarejestrowana elektronicznie ze względu na ciągłość");
            upoMBT = wysylanaDeklaracja.getUpo();
            wysylanaDeklaracja.setDatazlozenia(new Date());
            wysylanaDeklaracja.setSporzadzil(wpisView.getWprowadzil().getImie() + " " + wpisView.getWprowadzil().getNazw());
            wysylanaDeklaracja.setTestowa(false);
            deklaracjevatDAO.edit(wysylanaDeklaracja);
            Msg.msg("i", "Rejestracja ręczna deklaracji podatnika " + podatnik + " za " + rok + "-" + mc, "formX:msg");
        } catch (ClientTransportException ex1) {
            Msg.msg("e", "Nieudana rejestracja ręczna deklaracji podatnika " + podatnik + " za " + rok + "-" + mc, "formX:msg");
        }

    }

    public void pobierztest() {
        String rok = wpisView.getRokWpisu().toString();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        try {
            requestUPO_Test(idMBT, lang, upo, stat, opis);
        } catch (ClientTransportException ex1) {
            Msg.msg("e", "Nie można nawiązać testowego połączenia z serwerem ministerstwa podczas pobierania UPO podatnika " + podatnik + " za " + rok + "-" + mc);
        }
        Deklaracjevat temp = deklaracjevatDAO.findDeklaracjeDopotwierdzenia(idMBT, wpisView);
        List<String> komunikat = null;
//        if (temp.getStatus().equals(stat.value)) {
//            Msg.msg("i", "Wypatruje testowego gołębia z potwierdzeniem deklaracji podatnika ", "formX:msg");
//        } else {
            komunikat = EDeklaracjeObslugaBledow.odpowiedznakodserwera(stat.value);
            if (komunikat.size() > 1) {
                Msg.msg(komunikat.get(0), komunikat.get(1));
            }
//        }
        upoMBT = upo.value;
        statMBT = stat.value+ " ";
        opisMBT = komunikat.get(1);
        temp.setUpo(upoMBT);
        temp.setStatus(String.valueOf(stat.value));
        temp.setOpis(opisMBT);
        temp.setDataupo(new Date());
        deklaracjevatDAO.edit(temp);
    }
    
    

    public void pobierzwyslanetest(String identyfikator) {
        String rok = wpisView.getRokWpisu().toString();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        try {
            requestUPO_Test(identyfikator, lang, upo, stat, opis);
        } catch (ClientTransportException ex1) {
            Msg.msg("e", "Nie można nawiązać testowego połączenia z serwerem ministerstwa podczas pobierania UPO podatnika " + podatnik + " za " + rok + "-" + mc);
        }
        Deklaracjevat sprawdzanadeklaracja = deklaracjevatDAO.findDeklaracjeDopotwierdzenia(identyfikator, wpisView);
        List<String> komunikat = null;
        if (sprawdzanadeklaracja.getStatus().equals(stat.value)) {
            Msg.msg("i", "Wypatruje testowego gołębia z potwierdzeniem deklaracji podatnika ");
        } else {
            komunikat = EDeklaracjeObslugaBledow.odpowiedznakodserwera(stat.value);
            if (komunikat.size() > 1) {
                Msg.msg(komunikat.get(0), komunikat.get(1));
            }
        }
        upoMBT = upo.value;
        statMBT = stat.value + " "+opis.value;
        opisMBT = komunikat.get(1);
        sprawdzanadeklaracja.setUpo(upoMBT);
        sprawdzanadeklaracja.setStatus(String.valueOf(stat.value));
        sprawdzanadeklaracja.setOpis(opisMBT);
        deklaracjevatDAO.edit(sprawdzanadeklaracja);
        RequestContext.getCurrentInstance().update("formX:dokumentyLista");
    }
    
    public void pobierzwyslanetestUE(DeklaracjavatUE sprawdzanadeklaracja) {
        String rok = wpisView.getRokWpisu().toString();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        try {
            requestUPO_Test(sprawdzanadeklaracja.getIdentyfikator(), lang, upo, stat, opis);
        } catch (ClientTransportException ex1) {
            Msg.msg("e", "Nie można nawiązać testowego połączenia z serwerem ministerstwa podczas pobierania UPO podatnika " + podatnik + " za " + rok + "-" + mc);
        }
        List<String> komunikat = null;
        if (sprawdzanadeklaracja.getStatus().equals(stat.value)) {
            Msg.msg("i", "Wypatruje testowego gołębia z potwierdzeniem deklaracji podatnika ");
        } else {
            komunikat = EDeklaracjeObslugaBledow.odpowiedznakodserwera(stat.value);
            if (komunikat.size() > 1) {
                Msg.msg(komunikat.get(0), komunikat.get(1));
            }
        }
        upoMBT = upo.value;
        statMBT = stat.value + " "+opis.value;
        opisMBT = komunikat.get(1);
        sprawdzanadeklaracja.setUpo(upoMBT);
        sprawdzanadeklaracja.setStatus(String.valueOf(stat.value));
        sprawdzanadeklaracja.setOpis(opisMBT);
        sprawdzanadeklaracja.setDataupo(new Date());
        deklaracjavatUEDAO.edit(sprawdzanadeklaracja);
    }
    
    public void pobierzwyslanetest27(Deklaracjavat27 sprawdzanadeklaracja) {
        String rok = wpisView.getRokWpisu().toString();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        try {
            requestUPO_Test(sprawdzanadeklaracja.getIdentyfikator(), lang, upo, stat, opis);
        } catch (ClientTransportException ex1) {
            Msg.msg("e", "Nie można nawiązać testowego połączenia z serwerem ministerstwa podczas pobierania UPO podatnika " + podatnik + " za " + rok + "-" + mc);
        }
        List<String> komunikat = null;
        if (sprawdzanadeklaracja.getStatus().equals(stat.value)) {
            Msg.msg("i", "Wypatruje testowego gołębia z potwierdzeniem deklaracji podatnika ");
        } else {
            komunikat = EDeklaracjeObslugaBledow.odpowiedznakodserwera(stat.value);
            if (komunikat.size() > 1) {
                Msg.msg(komunikat.get(0), komunikat.get(1));
            }
        }
        upoMBT = upo.value;
        statMBT = stat.value + " "+opis.value;
        opisMBT = komunikat.get(1);
        sprawdzanadeklaracja.setUpo(upoMBT);
        sprawdzanadeklaracja.setStatus(stat.value.toString());
        sprawdzanadeklaracja.setOpis(opisMBT);
        sprawdzanadeklaracja.setDataupo(new Date());
        deklaracjavat27DAO.edit(sprawdzanadeklaracja);
    }

    public void przerzucdowysylki(String identyfikator) {
        Deklaracjevat deklaracjatransferowana = deklaracjevatDAO.findDeklaracjaPodatnik(identyfikator, wpisView.getPodatnikWpisu());
        deklaracjevatView.getWyslanetestowe().remove(deklaracjatransferowana);
        deklaracjatransferowana.setIdentyfikator("");
        deklaracjevatDAO.edit(deklaracjatransferowana);
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

    public String getStatMB() {
        return statMB;
    }

    public void setStatMB(String statMB) {
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

    public String getStatMBT() {
        return statMBT;
    }

    public void setStatMBT(String statMBT) {
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

    public DeklaracjevatView getDeklaracjevatView() {
        return deklaracjevatView;
    }

    public void setDeklaracjevatView(DeklaracjevatView deklaracjevatView) {
        this.deklaracjevatView = deklaracjevatView;
    }
    
    


}
