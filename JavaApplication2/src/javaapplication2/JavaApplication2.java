/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Osito
 */
public class JavaApplication2 {

    public static void main(String[] args) throws Exception {
//        URL oracle = new URL("http://cumdrippingbears.tumblr.com");
//        URLConnection yc = oracle.openConnection();
//        BufferedReader in = new BufferedReader(new InputStreamReader(
//                yc.getInputStream()));
//        String inputLine;
//        while ((inputLine = in.readLine()) != null) {
//            System.out.println(inputLine);
//        }
//        in.close();
        Connection.Response res = Jsoup.connect("http://ec.europa.eu/taxation_customs/vies/vatResponse.html")
                .data("memberStateCode", "PL", "number", "8511005008", "requesterMemberStateCode", "PL", "requesterNumber", "8511005008")
                .method(Method.POST)
                .execute();

        Document doc = res.parse();
        Element table = doc.getElementById("vatResponseFormTable");
        Elements tds = table.getElementsByTag("td");
        boolean znalazl = false;
        for (Element link : tds) {
            String linkText = link.text();
            if (linkText.contains("Yes, valid VAT number") || znalazl == true) {
                znalazl = true;
                if (!linkText.equals("")) {
                    System.out.println(linkText);
                }
            } else {
                System.out.println("Nie znalazl");
                break;
            }
        }
//        System.out.println("");
        //Document doc = Jsoup.connect("http://ec.europa.eu/taxation_customs/vies/vieshome.do?locale=pl").post().
////        Element content = doc.getElementsC
//        Element link = doc.getElementById("countryCombobox");
//        link.val("PL");
//        System.out.println(""+link.html());
////        for (Element link : links) {
////            String linkHref = link.attr("href");
////            String linkText = link.text();
////            System.out.println(linkText);
////        }
    }

}
