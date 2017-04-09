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
        File input = new File("input.html");
        Document doc = Jsoup.parse(input, "UTF-8", "http://info.cern.ch");
        Element content = doc.getElementsContainingText(searchText)
        Elements links = content.getElementsByTag("li");
        for (Element link : links) {
            String linkHref = link.attr("href");
            String linkText = link.text();
            System.out.println(linkText);
        }
    }

}
