/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DokDAO;
import dao.MobiledokDAO;
import entity.Mobiledok;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class MobiledokView implements Serializable{
    private static final long serialVersionUID = 1L;
    private List<Mobiledok> lista;
    private List<Mobiledok> lista2;
    @Inject
    private MobiledokDAO mobiledokDAO;
    @Inject
    private DokDAO  dokDAO;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    private void init() {
        lista = mobiledokDAO.findByNip(wpisView.getPodatnikObiekt().getNip());
        lista2 = dokDAO.zwrocBiezacegoKlientaRokKW(wpisView);
        System.out.println("");;
    }
    
    public StreamedContent  getPicture(Mobiledok mobiledok) {
        try {
//            DefaultStreamedContent dsc = new DefaultStreamedContent();
//            BufferedImage bufferedImg = new BufferedImage(150, 50, BufferedImage.TYPE_INT_RGB);
//            Graphics2D g2 = bufferedImg.createGraphics();
//            g2.drawString("This is a text", 0, 10);
//            ByteArrayOutputStream os = new ByteArrayOutputStream();
//            ImageIO.write(bufferedImg, "png", os);
//            dsc.setStream(new ByteArrayInputStream(os.toByteArray()));
//            return dsc;
           
            FileUtils.writeByteArrayToFile(new File("d:\\obrazek.jpg"), mobiledok.getPlik());
            DefaultStreamedContent dsc = new DefaultStreamedContent();
            dsc.setContentType("image/jpg");
            String nazwa = wpisView.getPodatnikObiekt().getNip()+"."+mobiledok.getRozszerzenie();
            dsc.setName(nazwa);
            dsc.setStream(new ByteArrayInputStream(Base64.encodeBase64(mobiledok.getPlik())));
            return dsc;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Mobiledok> getLista() {
        return lista;
    }

    public void setLista(List<Mobiledok> lista) {
        this.lista = lista;
    }
    
    
}
