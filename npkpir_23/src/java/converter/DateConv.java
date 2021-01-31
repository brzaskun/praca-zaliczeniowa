/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import java.io.Serializable;

/**
 *
 * @author Osito
 */
public class DateConv extends javax.faces.convert.DateTimeConverter implements Serializable  {  
  
public static final String CONVERTER_ID = "converter.DateConv";  
   
  public DateConv() {  
   // setTimeZone(TimeZone.getTimeZone("UTC+4"));  
    setPattern("yyyy-MM-dd");  
  }  
    
} 
    

