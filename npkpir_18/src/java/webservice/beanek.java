/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import dao.DeklaracjevatDAO;
import embeddable.Vatpoz;
import entity.Deklaracjevat;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
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
import service.GateService;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
public class beanek {
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
    @Inject DeklaracjevatDAO deklaracjevatDAO;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    

    public beanek() {
        id = new Holder<>();
        stat = new Holder<>();
        opis = new Holder<>();
        upo = new Holder<>();
        lang = "pl";
        signT = "PIT";
    }
     
    private void requestUPO(java.lang.String refId, java.lang.String language, javax.xml.ws.Holder<java.lang.String> upo, javax.xml.ws.Holder<Integer> status, javax.xml.ws.Holder<java.lang.String> statusOpis) {
        service.GateServicePortType port = service.getGateServiceSOAP12Port();
        port.requestUPO(refId, language, upo, status, statusOpis);
    }

    private void sendUnsignDocument(byte[] document, java.lang.String language, java.lang.String signatureType, javax.xml.ws.Holder<java.lang.String> refId, javax.xml.ws.Holder<Integer> status, javax.xml.ws.Holder<java.lang.String> statusOpis) {
        service.GateServicePortType port = service.getGateServiceSOAP12Port();
        port.sendUnsignDocument(document, language, signatureType, refId, status, statusOpis);
    }
    
    public void rob() throws JAXBException, FileNotFoundException, ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException{
//        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder = factory.newDocumentBuilder();
//        Document doc = builder.parse("c:/uslugi/testvat1.xml");
//        StringWriter stringWriter = new StringWriter(); 
//        Transformer transformer = TransformerFactory.newInstance().newTransformer(); 
//        transformer.transform(new DOMSource(doc), new StreamResult(stringWriter)); 
//        String strFileContent = stringWriter.toString(); //This is string data of xml file
//        System.out.println(strFileContent);
        String rok = wpisView.getRokWpisu().toString();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        Deklaracjevat temp =  deklaracjevatDAO.findDeklaracjeDowyslania(podatnik);
        String strFileContent = temp.getDeklaracja();
        System.out.println("wartosc stringu: "+strFileContent);
        String tmp = DatatypeConverter.printBase64Binary(strFileContent.getBytes("UTF-8"));
        dok = DatatypeConverter.parseBase64Binary(tmp);
        sendUnsignDocument(dok, lang, signT, id, stat, opis);
        idMB = id.value;
        statMB = stat.value;
        opisMB = opis.value;
        temp.setIdentyfikator(idMB);
        deklaracjevatDAO.edit(temp);
        
    }
    
    public void pobierz(String identyfikator){
        requestUPO(identyfikator, lang, upo, stat, opis);
        upoMB = upo.value;
        statMB = stat.value;    
        opisMB = opis.value;
        Deklaracjevat temp =  deklaracjevatDAO.findDeklaracjeDopotwierdzenia(identyfikator);
        temp.setUpo(upoMB);
        deklaracjevatDAO.edit(temp);
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

    
    
    
    static public void main(String[] args){
    try{
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse("c:/uslugi/Deklaracja.xml");
        StringWriter stringWriter = new StringWriter(); 
        Transformer transformer = TransformerFactory.newInstance().newTransformer(); 
        transformer.transform(new DOMSource(doc), new StreamResult(stringWriter)); 
        String strFileContent = stringWriter.toString(); //This is string data of xml file
        System.out.println(strFileContent);
    }
    catch (Exception e){
      e.getMessage();
    }
  }

   
}
