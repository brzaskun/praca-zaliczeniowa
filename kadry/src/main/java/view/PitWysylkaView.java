/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PitWysylkaView  implements Serializable {
    private static final long serialVersionUID = 1L;
//    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/testdok.wsdl")
//    private GateService service_1;

//    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/dokumenty.wsdl")
//    private GateService service;
    
//    public void robUE(DeklaracjavatUE wysylanaDeklaracja, List<Dok> listadok, List<Dokfk> listadokfk) throws JAXBException, FileNotFoundException, ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {
//        try {
//            dok = wysylanaDeklaracja.getDeklaracjapodpisana();
//            sendSignDocument(dok, id, stat, opis);
//            idMB = id.value;
//            idpobierz = id.value;
//            List<String> komunikat = null;
//            opisMB = opis.value;
//                komunikat = EDeklaracjeObslugaBledow.odpowiedznakodserwera(stat.value);
//            if (komunikat.size() > 1) {
//                    Msg.msg(komunikat.get(0), komunikat.get(1));
//                    opisMB = komunikat.get(1);
//            }
//            upoMB = upo.value;
//            statMB = stat.value + " "+opis.value;
//            wysylanaDeklaracja.setIdentyfikator(idMB);
//            wysylanaDeklaracja.setStatus(statMB.toString());
//            wysylanaDeklaracja.setOpis(opisMB);
//            wysylanaDeklaracja.setDatazlozenia(new Date());
//            wysylanaDeklaracja.setSporzadzil(wpisView.getUzer().getImie() + " " + wpisView.getUzer().getNazw());
//            wysylanaDeklaracja.setTestowa(false);
//            deklaracjavatUEDAO.create(wysylanaDeklaracja);
//            edytujdok(listadok, listadokfk);
//            Msg.msg("i", "Wypuszczono gołębia z deklaracja podatnika " + wpisView.getPodatnikWpisu() + " za " + wpisView.getRokWpisuSt() + "-" + wpisView.getMiesiacWpisu());
//        } catch (ClientTransportException ex1) {
//            Msg.msg("e", "Nie można nawiązać połączenia z serwerem ministerstwa podczas wysyłania deklaracji podatnika " + wpisView.getPodatnikWpisu() + " za " + wpisView.getRokWpisuSt() + "-" + wpisView.getMiesiacWpisu());
//        }
//    }
//     
//    private int sendSignDocument(byte[] dok, javax.xml.ws.Holder<java.lang.String> id, javax.xml.ws.Holder<Integer> stat, javax.xml.ws.Holder<java.lang.String> opis) {
//        int zwrot = 0;
//        try {
//            service.GateServicePortType port2 = service.getGateServiceSOAP12Port();
//            port2.sendDocument(dok, id, stat, opis);
//        } catch (Exception e) {
//            E.e(e);
//            zwrot = 1;
//        } finally {
//            return zwrot;
//        }
//    }
}
