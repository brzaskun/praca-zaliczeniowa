/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deklaracje.vat272;

import deklaracje.vat272.Deklaracja.Podmiot1;
import entity.Vat27;
import entity.VatUe;
import error.E;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import view.WpisView;import waluty.Z;


/**
 *
 * @author Osito
 */
public class VAT27Bean {
    public static TNaglowek tworznaglowek(String mc, String r, String kodurzedu) {
        TNaglowek n = new TNaglowek();
        try {
            //NORMALNA
            byte p = 1;
            byte p1 = 2;
            TNaglowek.CelZlozenia cz = new TNaglowek.CelZlozenia();
            cz.setValue(p);
            cz.setPoz(cz.getPoz());
            n.setCelZlozenia(cz);
            n.setWariantFormularza(p1);
            TNaglowek.KodFormularza k = new TNaglowek.KodFormularza();
            k.setValue(TKodFormularza.VAT_27);
            k.setKodSystemowy(k.getKodSystemowy());
            k.setKodPodatku(k.getKodPodatku());
            k.setRodzajZobowiazania(k.getRodzajZobowiazania());
            k.setWersjaSchemy(k.getWersjaSchemy());
            n.setKodFormularza(k);
            n.setMiesiac((byte) Integer.parseInt(mc));
            n.setRok(rok(r));
            n.setKodUrzedu(kodurzedu);
        } catch (Exception ex) {

        }
        return n;
    }
    
    public static TNaglowek tworznaglowekkorekta(String mc, String r, String kodurzedu) {
        TNaglowek n = new TNaglowek();
        try {
            //NORMALNA
            byte p = 2;
            byte p1 = 2;
            TNaglowek.CelZlozenia cz = new TNaglowek.CelZlozenia();
            cz.setValue(p);
            cz.setPoz(cz.getPoz());
            n.setCelZlozenia(cz);
            n.setWariantFormularza(p1);
            TNaglowek.KodFormularza k = new TNaglowek.KodFormularza();
            k.setValue(TKodFormularza.VAT_27);
            k.setKodSystemowy(k.getKodSystemowy());
            k.setKodPodatku(k.getKodPodatku());
            k.setRodzajZobowiazania(k.getRodzajZobowiazania());
            k.setWersjaSchemy(k.getWersjaSchemy());
            n.setKodFormularza(k);
            n.setMiesiac((byte) Integer.parseInt(mc));
            n.setRok(rok(r));
            n.setKodUrzedu(kodurzedu);
        } catch (Exception ex) {

        }
        return n;
    }
    public static XMLGregorianCalendar rok(String rok) throws DatatypeConfigurationException {
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(String.format(rok));
    }
    
    public static Podmiot1 podmiot1(WpisView wv) {
        Podmiot1 p = new Podmiot1();
        if (wv.getFormaprawna() != null) {
            p.setRola("Podatnik");
            p.setOsobaNiefizyczna(pobierzidentyfikatorspolka(wv));
        } else {
            p.setRola("Podatnik");
            p.setOsobaFizyczna(pobierzindetyfikator(wv));
        }
        return p;
    }

//    public static void main(String[] args) {
//        try {
//            XMLGregorianCalendar s =  DatatypeFactory.newInstance().newXMLGregorianCalendar("2017");
//            error.E.s("s "+s);
//            TKodKrajuUE k = TKodKrajuUE.fromValue("DE");
//            error.E.s("k "+k);
//        } catch (Exception ex) {
//            // Logger.getLogger(VATUEM4Bean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    private static TIdentyfikatorOsobyNiefizycznej pobierzidentyfikatorspolka(WpisView w) {
        TIdentyfikatorOsobyNiefizycznej idf = new TIdentyfikatorOsobyNiefizycznej();
        idf.setNIP(w.getPodatnikObiekt().getNip());
        idf.setPelnaNazwa(w.getPodatnikObiekt().getNazwapelna());
        idf.setREGON(w.getPodatnikObiekt().getRegon());
        return idf;
    }

    private static TIdentyfikatorOsobyFizycznej2 pobierzindetyfikator(WpisView w) {
        TIdentyfikatorOsobyFizycznej2 idf = new TIdentyfikatorOsobyFizycznej2();
        try {
            idf.setNIP(w.getPodatnikObiekt().getNip());
            idf.setNazwisko(w.getPodatnikObiekt().getNazwisko());
            idf.setImiePierwsze(w.getPodatnikObiekt().getImie());
            idf.setDataUrodzenia(DatatypeFactory.newInstance().newXMLGregorianCalendar(w.getPodatnikObiekt().getDataurodzenia()));
        } catch (DatatypeConfigurationException ex) {
            // Logger.getLogger(VAT27Bean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idf;
    }

    public static PozycjeSzczegolowe pozycjeszczegolowe(List<VatUe> lista) {
        deklaracje.vat272.PozycjeSzczegolowe poz = new PozycjeSzczegolowe();
        Double sumaC = 0.0;
        Double sumaD = 0.0;
        if (lista != null) {
            for (VatUe p : lista) {
                if (p.getKontrahent() != null) {
                    switch (p.getTransakcja()) {
                        case "RVC":
                            sumaC = Z.z(sumaC +p.getNetto());
                            poz.getGrupaC().add(grupaC(p));
                            break;
                    }
                }
            }
            poz.setP10(new BigDecimal(sumaC));
            for (VatUe p : lista) {
                if (p.getKontrahent() != null) {
                    switch (p.getTransakcja()) {
                        case "RVCS":
                            sumaD = Z.z(sumaD +p.getNetto());
                            poz.getGrupaD().add(grupaD(p));
                            break;
                    }
                }
            }
            poz.setP10(Z.zBD2(sumaC));
            poz.setP11(Z.zBD2(sumaD));
        }
        return poz;
    }
    
    public static PozycjeSzczegolowe pozycjeszczegolowe27(List<Vat27> lista) {
        deklaracje.vat272.PozycjeSzczegolowe poz = new PozycjeSzczegolowe();
        Double sumaC = 0.0;
        Double sumaD = 0.0;
        if (lista != null) {
            for (Vat27 p : lista) {
                if (p.getKontrahent() != null) {
                    switch (p.getTransakcja()) {
                        case "RVC":
                            sumaC = Z.z(sumaC +p.getNetto());
                            poz.getGrupaC().add(grupaC27(p));
                            break;
                    }
                }
            }
            poz.setP10(new BigDecimal(sumaC));
            for (Vat27 p : lista) {
                if (p.getKontrahent() != null) {
                    switch (p.getTransakcja()) {
                        case "RVCS":
                            sumaD = Z.z(sumaD +p.getNetto());
                            poz.getGrupaD().add(grupaD27(p));
                            break;
                    }
                }
            }
            poz.setP10(Z.zBD2(sumaC));
            poz.setP11(Z.zBD2(sumaD));
        }
        return poz;
    }
    
    

    private static GrupaC grupaC(VatUe p) {
        GrupaC g = new GrupaC();
        g.setTyp(g.getTyp());
        if (p.isKorekta()) {
            g.setPC1(1);
        }
        g.setPC2("DOSTAWA1");
        g.setPC3(przetworznip(p.getKontrahent().getNip()));
        g.setPC4(Z.zBD2(p.getNetto()));
        return g;
    }

    private static GrupaD grupaD(VatUe p) {
        GrupaD g = new GrupaD();
        if (p.isKorekta()) {
            g.setPD1(1);
        }
        g.setTyp(g.getTyp());
        g.setPD2("USŁUGA");
        g.setPD3(przetworznip(p.getKontrahent().getNip()));
        g.setPD4(Z.zBD2(p.getNetto()));
        return g;
    }
    
     private static GrupaC grupaC27(Vat27 p) {
        GrupaC g = new GrupaC();
        g.setTyp(g.getTyp());
        if (p.isKorekta()) {
            g.setPC1(1);
        }
        g.setPC2("DOSTAWA1");
        g.setPC3(przetworznip(p.getKontrahent().getNip()));
        g.setPC4(Z.zBD2(p.getNetto()));
        return g;
    }

    private static GrupaD grupaD27(Vat27 p) {
        GrupaD g = new GrupaD();
        if (p.isKorekta()) {
            g.setPD1(1);
        }
        g.setTyp(g.getTyp());
        g.setPD2("USŁUGA");
        g.setPD3(przetworznip(p.getKontrahent().getNip()));
        g.setPD4(Z.zBD2(p.getNetto()));
        return g;
    }
    
    private static String przetworznip(String nip) {
        String dobrynip = nip;
        boolean jestprefix = sprawdznip(nip);
            if (jestprefix) {
                dobrynip = nip.substring(2);
        }
        return dobrynip;
    }
    
    private static boolean sprawdznip(String nip) {
        //jezeli false to dobrze
        String prefix = nip.substring(0, 2);
        Pattern p = Pattern.compile("[0-9]");
        boolean isnumber = p.matcher(prefix).find();
        return !isnumber;
    }

    
     public static void marszajuldoplikuxml(Deklaracja dekl, WpisView wpisView) {
        try {
            JAXBContext context = JAXBContext.newInstance(Deklaracja.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            StringWriter sw = new StringWriter();
            marshaller.marshal(dekl, new StreamResult(sw));
            Document dokmt = StringToDocument(sw.toString());
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String plik = ctx.getRealPath("/")+"resources\\xml\\testowa"+wpisView.getPodatnikObiekt().getNip()+".xml";
            FileOutputStream fileStream = new FileOutputStream(new File(plik));
            OutputStreamWriter writer = new OutputStreamWriter(fileStream, "UTF-8");
            marshaller.marshal(dekl, writer);
        } catch (Exception ex) {
            E.e(ex);
        }
    }
     
     public static String marszajuldoStringu(Deklaracja dekl, WpisView wpisView) {
        StringWriter sw = new StringWriter();
        try {
            JAXBContext context = JAXBContext.newInstance(Deklaracja.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(dekl, new StreamResult(sw));
        } catch (Exception ex) {
            E.e(ex);
        }
        return sw.toString().replaceAll("\n|\r", "").substring(sw.toString().replaceAll("\n|\r", "").indexOf(">")+1);
    }
    
     public static Document StringToDocument(String strXml) throws Exception {

        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            StringReader strReader = new StringReader(strXml);
            InputSource is = new InputSource(strReader);
            doc = (Document) builder.parse(is);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return doc;
    }
     
    public static void main(String[] args) {
        String strXml
                = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
"<Deklaracja xmlns=\"http://crd.gov.pl/wzor/2017/01/11/3846/\" xmlns:ns2=\"http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2016/01/25/eD/DefinicjeTypy/\">\n" +
"    <Naglowek>\n" +
"        <KodFormularza kodSystemowy=\"VAT-UE (4)\" wersjaSchemy=\"1-0E\">VAT-UE</KodFormularza>\n" +
"        <WariantFormularza>4</WariantFormularza>\n" +
"        <Rok>2017</Rok>\n" +
"        <Miesiac>3</Miesiac>\n" +
"        <CelZlozenia>1</CelZlozenia>\n" +
"        <KodUrzedu>3271</KodUrzedu>\n" +
"    </Naglowek>\n" +
"    <Podmiot1>\n" +
"        <ns2:OsobaNiefizyczna>\n" +
"            <ns2:NIP>8513169524</ns2:NIP>\n" +
"            <ns2:PelnaNazwa>GCO SP. Z O.O. SP. K.</ns2:PelnaNazwa>\n" +
"            <ns2:REGON>321385141</ns2:REGON>\n" +
"        </ns2:OsobaNiefizyczna>\n" +
"    </Podmiot1>\n" +
"    <PozycjeSzczegolowe>\n" +
"        <Grupa2>\n" +
"            <P_Nb>DE119831689</P_Nb>\n" +
"            <P_Nc>441</P_Nc>\n" +
"            <P_Nd>0</P_Nd>\n" +
"        </Grupa2>\n" +
"        <Grupa3>\n" +
"            <P_Ub>ATU66218014</P_Ub>\n" +
"            <P_Uc>14591</P_Uc>\n" +
"        </Grupa3>\n" +
"    </PozycjeSzczegolowe>\n" +
"    <Pouczenie>1</Pouczenie>\n" +
"</Deklaracja>";
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            builder = factory.newDocumentBuilder();
            StringReader sr = new StringReader(strXml);
            InputSource is = new InputSource(sr);
            Document doc = builder.parse(is);
            Element g = doc.getElementById("Grupa3");
            Element element = doc.getDocumentElement();
            Node node = doc.createElement("podp:DaneAutoryzujace");
            String aut = "<podp:DaneAutoryzujace xmlns:podp=\"http://e-deklaracje.mf.gov.pl/Repozytorium/Definicje/Podpis/\"><podp:NIP>8511005008"
                            +"</podp:NIP><podp:ImiePierwsze>Jan</podp:ImiePierwsze><podp:Nazwisko>Grzelczyk"
                            +"</podp:Nazwisko><podp:DataUrodzenia>25-08-1970</podp:DataUrodzenia><podp:Kwota>12552.25"
                            +"</podp:Kwota></podp:DaneAutoryzujace></Deklaracja>";
            //node.appendChild(doc.createTextNode("tresc"));
            node.setTextContent("aaa");
            Element list = doc.getElementById("Deklaracja");

            list.setAttribute("type", "formula one");
//            Attr attrType = doc.createAttribute("type");
//            attrType.setValue("formula one");
//            element.setAttributeNode(attrType);
            element.appendChild(node);
            prettyPrint(doc);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public static void prettyPrint(Document xml) throws Exception {
        Transformer tf = TransformerFactory.newInstance().newTransformer();
        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tf.setOutputProperty(OutputKeys.INDENT, "yes");
        Writer out = new StringWriter();
        tf.transform(new DOMSource(xml), new StreamResult(out));
    }


    
}
