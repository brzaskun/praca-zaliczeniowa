/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import data.Data;
import generated.RaportEzla;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceRef;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.FileHeader;
import zuszla.PobierzRaporty;
import zuszla.PobierzRaportyResponse;
import zuszla.WsdlPlatnikRaportyZlaPortType;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class ZusZLAView  implements Serializable {
    private static final long serialVersionUID = 1L;
    
   @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/zuszla.wsdl")
    private zuszla.WsdlPlatnikRaportyZla wsdlPlatnikRaportyZla;
    
   public void zuszla() {
       try {
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
        new javax.net.ssl.HostnameVerifier(){

            public boolean verify(String hostname,
                    javax.net.ssl.SSLSession sslSession) {
                return hostname.equals("193.105.143.40");
            }
        });
        PobierzRaporty parameters = new PobierzRaporty();
        parameters.setNip("8511005008");
        parameters.setLogin("a.barczyk@taxman.biz.pl");
        parameters.setHaslo("Taxman2810*");
        String nowadata = Data.odejmijdniDzis(30);
        parameters.setDataOd(Data.dataoddo(nowadata));
        WsdlPlatnikRaportyZlaPortType port = wsdlPlatnikRaportyZla.getZusChannelPlatnikRaportyZlaWsdlPlatnikRaportyZlaPort();
        BindingProvider prov = (BindingProvider) port;
        prov.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "b2b_platnik_raporty_zla");
        prov.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "b2b_platnik_raporty_zla");
        PobierzRaportyResponse pobierzRaporty = port.pobierzRaporty(parameters);
        zuszla.Raporty rap = pobierzRaporty.getRaporty();
        zuszla.Raport raport = rap.getRaport().get(3);
        Base64.Decoder dec = Base64.getDecoder();
        byte[] dane = raport.getZawartosc();
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = ctx.getRealPath("/");
        Path path = Paths.get(realPath+"resources/zla/raport.zip");
        Files.write(path, dane);
        ZipFile zp = new ZipFile(realPath+"resources/zla/raport.zip","Taxman2810*".toCharArray());
        FileHeader fileHeader = zp.getFileHeader("raport.xml");
        InputStream inputStream = zp.getInputStream(fileHeader);
        //File targetFile = new File("src/main/resources/targetFile.tmp");
        RaportEzla zwrot = null;
       try {
           JAXBContext context = JAXBContext.newInstance(RaportEzla.class);
           Unmarshaller unmarshaller = context.createUnmarshaller();
           zwrot = (RaportEzla) unmarshaller.unmarshal(inputStream);
       } catch (Exception ex) {
           error.E.s("");
       }
//        File targetFile = new File(realPath+"resources/zla/raport_raport.xml");
//        FileUtils.copyInputStreamToFile(inputStream, targetFile);
       } catch (Exception e) {
       }
   }
}
