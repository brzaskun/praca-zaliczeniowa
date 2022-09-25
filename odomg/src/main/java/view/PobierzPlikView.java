/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PobierzPlikView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    private final String sciezka = "resources//upowaznienia//";
    private final String sciezka1 = "resources//zaswiadczenia//";
    private List<File> pliki;
    private List<File> pliki1;
    
    @PostConstruct
    private void init() {
        pliki = new ArrayList<>();
        pliki1 = new ArrayList<>();
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = ctx.getRealPath("/")+sciezka;
        try {
            File dir = new File(realPath);
            for (File file : dir.listFiles()) {
               if (file.isDirectory()) {
                   showFiles(file.listFiles()); // Calls same method again.
               } else {
                   pliki.add(file);
               }
           }
        } catch (Exception r) {}
        String realPath1 = ctx.getRealPath("/")+sciezka1;
        try {
            File dir = new File(realPath1);
            for (File file : dir.listFiles()) {
               if (file.isDirectory()) {
                   showFiles(file.listFiles()); // Calls same method again.
               } else {
                   pliki1.add(file);
               }
           }
        } catch (Exception r) {}
    }
    
    public static void main(String... args) {
        File dir = new File("D:\\dumps\\Kadry20210106");
        showFiles(dir.listFiles());
    }

    public static void showFiles(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println("Directory: " + file.getAbsolutePath());
                showFiles(file.listFiles()); // Calls same method again.
            } else {
                System.out.println("File: " + file.getAbsolutePath());
            }
        }
    }

    public List<File> getPliki() {
        return pliki;
    }

    public void setPliki(List<File> pliki) {
        this.pliki = pliki;
    }

    public List<File> getPliki1() {
        return pliki1;
    }

    public void setPliki1(List<File> pliki1) {
        this.pliki1 = pliki1;
    }
    
    
}
