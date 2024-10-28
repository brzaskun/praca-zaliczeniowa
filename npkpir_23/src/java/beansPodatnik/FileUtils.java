/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beansPodatnik;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Osito
 */
public class FileUtils {
    public static void createDirectories(String directoryPath) {
        Path path = Paths.get(directoryPath);
        try {
            Files.createDirectories(path);
            System.out.println("Katalog i podkatalogi utworzone: " + path.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Nie udało się utworzyć katalogów: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String path = "C:/example/folder1/folder2";
        createDirectories(path);
    }
}
