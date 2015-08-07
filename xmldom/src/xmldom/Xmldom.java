/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmldom;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Osito
 */
public class Xmldom {

    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) {
//        try {
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            File f = new File("C://temp//text.xml");
//            Document doc = builder.parse(f);
//            Element root = doc.getDocumentElement();
//            System.out.println(root.getTagName());
//            NodeList children = root.getChildNodes();
//            for (int i = 0; i < children.getLength(); i++) {
//                Node child = children.item(i);
//                if (child instanceof Element) {
//                    Text text = (Text) ((Element) child).getFirstChild();
//                    System.out.println(child.getNodeName());
//                    System.out.println(text);
//                }
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(Xmldom.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    public static void main(String[] args) {
        try {
            Properties p = System.getProperties();
            String path = p.getProperty("user.dir");
            File f = new File(path+"\\lolo.txt");
            String text = "Hello world";
            BufferedWriter output = null;
            try {
                output = new BufferedWriter(new FileWriter(f));
                output.write(text);
            } catch ( IOException e ) {
                e.printStackTrace();
            } finally {
                if ( output != null ) output.close();
            }
           //File p = path[0];
            System.out.println("d");
        } catch (Exception ex) {
            Logger.getLogger(Xmldom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
