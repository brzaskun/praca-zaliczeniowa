/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gus;

import cis.bir.publ._2014._07.datacontract.ParametryWyszukiwania;
import com.sun.xml.ws.developer.WSBindingProvider;
import error.E;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceRef;
import javax.xml.ws.handler.MessageContext;
import org.tempuri.IUslugaBIRzewnPubl;
import org.tempuri.UslugaBIRzewnPubl;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class GUSView implements Serializable {

    private static final long serialVersionUID = 1L;
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/GUS/UslugaBIRzewnPubl.wsdl")
    private UslugaBIRzewnPubl service;

    public void login() {
        try {
            service = new UslugaBIRzewnPubl();
            IUslugaBIRzewnPubl e3 = service.getE3();
            String login = e3.zaloguj("e19dbcf03de941479bad");
            WSBindingProvider bp = (WSBindingProvider) e3;
            Map<String, Object> req_ctx = ((BindingProvider)e3).getRequestContext();
            Map<String, List<String>> headers = new HashMap<String, List<String>>();
            headers.put("sid", Collections.singletonList(login));
            req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
            //bp.setOutboundHeaders(Headers.create(new QName("http://tempuri.org/","sid"),login));
            String statussesji = e3.getValue("StatusSesji");
            ParametryWyszukiwania pw = new ParametryWyszukiwania();
            JAXBElement<String> jb = new JAXBElement(new QName("http://CIS/BIR/PUBL/2014/07/DataContract","Nip"), String.class, "9552158028");
            pw.setRegon(jb);
            Object res = e3.daneSzukaj(pw);
            String statuslugi = e3.getValue("StatusUslugi");
            String komunikatkod = e3.getValue("KomunikatKod");
            String komunikattresc = e3.getValue("KomunikatTresc");
            String komunikatuslugi = e3.getValue("KomunikatUslugi");
            Object res2 = e3.danePobierzPelnyRaport("810649340", "PublDaneRaportDzialalnoscFizycznejCeidg");
            //String ko1 = service.getE3().daneKomunikat();
            
            //String ko = service.getE3().getValue();
//            QName q = new QName("Regon");
            String s = service.getE3().pobierzCaptcha();
//            JAXBElement el = new JAXBElement(q, String.class, "320890902");
            
            //ko = service.getE3().daneKomunikat();
            System.out.println("d");
        } catch (Exception e) {
            E.e(e);
        }
    }

}
