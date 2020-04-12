/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sms;

import dao.PodatnikDAO;
import entity.Faktura;
import entity.Podatnik;
import error.E;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pl.smsapi.OAuthClient;
import pl.smsapi.api.SmsFactory;
import pl.smsapi.api.action.sms.SMSSend;
import pl.smsapi.api.response.MessageResponse;
import pl.smsapi.api.response.StatusResponse;
import pl.smsapi.exception.ClientException;
import pl.smsapi.exception.SmsapiException;
import pl.smsapi.proxy.ProxyNative;

/**
 *
 * @author Osito
 */
public class SmsSend {
    private final static String token = "QRC7pHWEFt72fxh6BLpMfjAgv3qfTvIwtC9sIkya";
    private final static String numer = "+48603133396";
    private final static String urlForPlSmsapi = "http://api.smsapi.pl/";
//    private final static String urlForComSmsapi = "http://api.smsapi.com/";
    
    public static void wyslijSMS(String numertel, String text) throws ClientException, SmsapiException {
        try {
            String oauthToken = token;
            OAuthClient client = new OAuthClient(oauthToken);
	    ProxyNative proxyToPlOrComSmsapi = new ProxyNative(urlForPlSmsapi);

            SmsFactory smsApi = new SmsFactory(client, proxyToPlOrComSmsapi);
            String phoneNumber = numertel;
            SMSSend action = smsApi.actionSend()
                    .setText(text)
                    .setTo(phoneNumber)
                    .setFlash(true)
                    .setNormalize(true);

            StatusResponse result = action.execute();

            for (MessageResponse status : result.getList() ) {
                System.out.println(status.getNumber() + " " + status.getStatus());
            }
        } catch (ClientException e) {
            E.e(e);
            throw new ClientException(e);
        } catch (SmsapiException e) {
            E.e(e);
            throw new SmsapiException("Błąd podczas wysyłki sms", e);
        }
    }
    
    public static Map<String, String> wyslijSMSy(Map<String, String> numery) {
        Map<String, String> zwrot = new HashMap<>();
        if (!numery.isEmpty()) {
            for (String p : numery.keySet()) {
                try {
                    wyslijSMS(p, numery.get(p));
                } catch (ClientException e) {
                    E.e(e);
                    zwrot.put(p, "Zły numer");
                } catch (SmsapiException e) {
                    E.e(e);
                    zwrot.put(p, "Błąd wysyłki");
                }
            }
        } else {
            zwrot.put("0", "Pusta lista lub brak tekstu wiadomości");
        }
        return zwrot;
    }
    
    
    public static Map<String, String> wyslijSMSyFaktura(Faktura faktura, String text, PodatnikDAO podatnikDAO) {
        Map<String, String> zwrot = new HashMap<>();
        if (faktura!=null && text!=null) {
            Map<String, String> telefony = new HashMap<>();
            if (faktura.getKontrahent().getNip()!=null) {
                Podatnik podatnik = podatnikDAO.findPodatnikByNIP(faktura.getKontrahent().getNip());
                if (podatnik!=null) {
                    if (podatnik.getTelefonkontaktowy()!=null && podatnik.getTelefonkontaktowy().length()==9) {
                        telefony.put(podatnik.getTelefonkontaktowy(), text+" "+faktura.getKontrahent().getEmail());
                    }
                }
            }
            if (!telefony.isEmpty()) {
                zwrot = wyslijSMSy(telefony);
            }
        } else {
            zwrot.put("0", "Pusta lista lub brak tekstu wiadomości");
        }
        return zwrot;
    }
    
    public static Map<String, String> wyslijSMSyFakturyLista(List<Faktura> wybrane, String text, PodatnikDAO podatnikDAO) {
        Map<String, String> zwrot = new HashMap<>();
        if (!wybrane.isEmpty() && text!=null) {
            Map<String, String> telefony = new HashMap<>();
            for (Faktura p : wybrane) {
                if (p.getKontrahent().getNip()!=null) {
                    Podatnik podatnik = podatnikDAO.findPodatnikByNIP(p.getKontrahent().getNip());
                    if (podatnik!=null) {
                        if (podatnik.getTelefonkontaktowy()!=null && podatnik.getTelefonkontaktowy().length()==9) {
                            telefony.put(podatnik.getTelefonkontaktowy(), text+" "+p.getKontrahent().getEmail());
                        }
                    }
                }
            }
            if (!telefony.isEmpty()) {
                zwrot = wyslijSMSy(telefony);
            }
        } else {
            zwrot.put("0", "Pusta lista lub brak tekstu wiadomości");
        }
        return zwrot;
    }
    
    
    public static void main(String args[]) {
        try {
            String oauthToken = token;
            OAuthClient client = new OAuthClient(oauthToken);
	    ProxyNative proxyToPlOrComSmsapi = new ProxyNative(urlForPlSmsapi);

            SmsFactory smsApi = new SmsFactory(client, proxyToPlOrComSmsapi);
            String phoneNumber = numer;
            SMSSend action = smsApi.actionSend()
                    .setText("Pierwszy sms-esałść")
                    .setTo(phoneNumber)
                    .setFlash(true)
                    .setNormalize(true);

            StatusResponse result = action.execute();

            for (MessageResponse status : result.getList() ) {
                System.out.println(status.getNumber() + " " + status.getStatus());
                System.out.println(status.getError());
                System.out.println(status.getId());
                System.out.println(status.getIdx());
                System.out.println(status.getPoints());
                System.out.println(status.getStatus());
            }
        } catch (ClientException e) {
            E.e(e);
        } catch (SmsapiException e) {
            E.e(e);
        }
    }
    
    
    
}
