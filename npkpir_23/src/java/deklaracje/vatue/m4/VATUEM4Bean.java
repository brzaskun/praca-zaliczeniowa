/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deklaracje.vatue.m4;

import deklaracje.vatue.m4.Deklaracja.Podmiot1;
import embeddable.VatUe;
import error.E;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.math.BigInteger;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.stream.StreamResult;
import view.WpisView;
import waluty.Z;


/**
 *
 * @author Osito
 */
public class VATUEM4Bean {
    public static TNaglowek tworznaglowek(String mc, String r, String kodurzedu) {
        TNaglowek n = new TNaglowek();
        try {
            byte p = 1;
            byte p1 = 4;
            n.setCelZlozenia(p);
            n.setWariantFormularza(p1);
            TNaglowek.KodFormularza k = new TNaglowek.KodFormularza();
            k.setValue(TKodFormularza.VAT_UE);
            k.setKodSystemowy(k.getKodSystemowy());
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
            p.setOsobaNiefizyczna(pobierzidentyfikatorspolka(wv));
        } else {
            p.setOsobaFizyczna(pobierzindetyfikator(wv));
        }
        return p;
    }

    public static void main(String[] args) {
        try {
            XMLGregorianCalendar s =  DatatypeFactory.newInstance().newXMLGregorianCalendar("2017");
            System.out.println("s "+s);
            TKodKrajuUE k = TKodKrajuUE.fromValue("DE");
            System.out.println("k "+k);
        } catch (Exception ex) {
            Logger.getLogger(VATUEM4Bean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
            Logger.getLogger(VATUEM4Bean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idf;
    }

    public static PozycjeSzczegolowe pozycjeszczegolowe(List<VatUe> lista) {
        deklaracje.vatue.m4.PozycjeSzczegolowe poz = new PozycjeSzczegolowe();
        if (lista != null) {
            for (VatUe p : lista) {
                if (p.getKontrahent() != null) {
                    switch (p.getTransakcja()) {
                        case "WDT":
                            poz.getGrupa1().add(grupa1(p));
                            break;
                        case "WNT":
                            poz.getGrupa2().add(grupa2(p));
                            break;
                        default:
                            poz.getGrupa3().add(grupa3(p));
                            break;
                    }
                }
            }
        }
        return poz;
    }

    private static Grupa1 grupa1(VatUe p) {
        Grupa1 g = new Grupa1();
        g.setPDa(kodkraju(p));
        g.setPDb(p.getKontrahent().getNip());
        g.setPDc(new BigInteger(Z.zUDI(p.getNetto()).toString()));
        g.setPDd((byte)0);
        return g;
    }

    private static Grupa2 grupa2(VatUe p) {
        Grupa2 g = new Grupa2();
        g.setPNa(kodkraju(p));
        g.setPNb(p.getKontrahent().getNip());
        g.setPNc(new BigInteger(Z.zUDI(p.getNetto()).toString()));
        g.setPNd((byte)0);
        return g;
    }

    private static Grupa3 grupa3(VatUe p) {
        Grupa3 g = new Grupa3();
        g.setPUa(kodkraju(p));
        g.setPUb(p.getKontrahent().getNip());
        g.setPUc(new BigInteger(Z.zUDI(p.getNetto()).toString()));
        return g;
    }

    private static TKodKrajuUE kodkraju(VatUe p) {
        TKodKrajuUE zwrot = null;
        if (p.getKontrahent().getKrajkod() != null) {
            try {
                String k = p.getKontrahent().getKrajkod();
                TKodKrajuUE.fromValue(k);
            } catch (Exception e) {
            }
        }
        return zwrot;
    }

     public static void marszajuldoplikuxml(Deklaracja dekl, WpisView wpisView) {
        try {
            JAXBContext context = JAXBContext.newInstance(Deklaracja.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(dekl, System.out);
            StringWriter sw = new StringWriter();
            marshaller.marshal(dekl, new StreamResult(sw));
            String plik = "C:\\Users\\Osito\\Documents\\NetBeansProjects\\npkpir_23\\build\\web\\resources\\xml\\testowa"+wpisView.getPodatnikObiekt().getNip()+".xml";
            FileOutputStream fileStream = new FileOutputStream(new File(plik));
            OutputStreamWriter writer = new OutputStreamWriter(fileStream, "UTF-8");
            marshaller.marshal(dekl, writer);
        } catch (Exception ex) {
            E.e(ex);
        }
    }
    

    
}
