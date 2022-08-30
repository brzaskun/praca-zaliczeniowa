/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.MobiledokDAO;
import entity.Mobiledok;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Base64;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
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
    @Inject
    private MobiledokDAO mobiledokDAO;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    private void init() {
        lista = mobiledokDAO.findByNip(wpisView.getPodatnikObiekt().getNip());
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
            byte[] decode = Base64.getDecoder().decode(new String(mobiledok.getPlik()).getBytes("UTF-8"));
            //FileUtils.writeByteArrayToFile(new File("d:\\obrazek.jpg"), decode);
            DefaultStreamedContent dsc = new DefaultStreamedContent();
            dsc.setContentType("image/jpg");
            String nazwa = wpisView.getPodatnikObiekt().getNip()+"."+mobiledok.getRozszerzenie();
            dsc.setName(nazwa);
            dsc.setStream(new ByteArrayInputStream(decode));
            return dsc;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void usun(Mobiledok mobiledok) {
        if (mobiledok!=null) {
            try{
                lista.remove(mobiledok);
                mobiledokDAO.remove(mobiledok);
                Msg.dP();
            } catch (Exception e) {
                Msg.dPe();
            }
        }
    }

    public List<Mobiledok> getLista() {
        return lista;
    }

    public void setLista(List<Mobiledok> lista) {
        this.lista = lista;
    }
    
    
}
