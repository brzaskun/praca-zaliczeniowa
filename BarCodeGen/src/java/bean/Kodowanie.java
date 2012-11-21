/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.xml.ws.WebServiceRef;
import net.webservicex.*;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author School
 */
@ManagedBean
@RequestScoped
public class Kodowanie implements Serializable{
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/www.webservicex.net/genericbarcode.asmx.wsdl")
    private BarCode service;

    private String dozakodowania;
    private BarCodeData parametrykodu;
    private BarCodeData parametrykodu1;
    private byte[] obrazek;
    private StreamedContent barcode; 
    

    
    @PostConstruct
    private void init(){
        parametrykodu = new BarCodeData();
        parametrykodu1 = new BarCodeData();
    }
    
    public void wygenerujKod(ActionEvent e) throws IOException{
        parametrykodu1.setHeight(200);
        parametrykodu1.setWidth(700);
        parametrykodu1.setAngle(90);
        parametrykodu1.setRatio(10);
        parametrykodu1.setModule(1);
        parametrykodu1.setLeft(10);
        parametrykodu1.setTop(10);
        parametrykodu1.setCheckSum(true);
        parametrykodu1.setBarColor("#FFFFFF");
        parametrykodu1.setBGColor("#000000");
        parametrykodu1.setFontName("Arial");
        parametrykodu1.setFontSize(new Float(10));
        parametrykodu1.setBarcodeOption(net.webservicex.BarcodeOption.CODE);
        parametrykodu1.setCheckSumMethod(CheckSumMethod.MODULO_10);
        parametrykodu1.setBarcodeType(BarcodeType.CODE_39);
        parametrykodu1.setShowTextPosition(ShowTextPosition.TOP_LEFT);
        parametrykodu1.setBarCodeImageFormat(ImageFormats.BMP);
        dozakodowania = "lolo";
        try{
        obrazek = generateBarCode(parametrykodu1, dozakodowania);
        } catch (AbortProcessingException ex){
            System.out.println(ex.getStackTrace().toString());
        }
        InputStream in = new ByteArrayInputStream(obrazek);
	BufferedImage bImageFromConvert = ImageIO.read(in);
        ImageIO.write(bImageFromConvert, "jpg", new File("c:/barcode.jpg"));
        barcode = new DefaultStreamedContent(new FileInputStream("c:/barcode.jpg"), "image/jpeg"); 
    }
    
    private static byte[] generateBarCode(net.webservicex.BarCodeData barCodeParam, java.lang.String barCodeText) {
        net.webservicex.BarCode service = new net.webservicex.BarCode();
        net.webservicex.BarCodeSoap port = service.getBarCodeSoap12();
        return port.generateBarCode(barCodeParam, barCodeText);
    }

    public String getDozakodowania() {
        return dozakodowania;
    }

    public void setDozakodowania(String dozakodowania) {
        this.dozakodowania = dozakodowania;
    }

    public BarCodeData getParametrykodu() {
        return parametrykodu;
    }

    public void setParametrykodu(BarCodeData parametrykodu) {
        this.parametrykodu = parametrykodu;
    }

    public byte[] getObrazek() {
        return obrazek;
    }

    public void setObrazek(byte[] obrazek) {
        this.obrazek = obrazek;
    }

    public StreamedContent getBarcode() {
        return barcode;
    }

    public void setBarcode(StreamedContent barcode) {
        this.barcode = barcode;
    }

  
      
}
