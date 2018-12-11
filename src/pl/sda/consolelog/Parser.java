package pl.sda.consolelog;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Parser {

    //tu jako parametr podamy sciezke do zasoby z logami ..
    //public static Path file = Paths.get("console_lnmail2.log");
    //public static Path file = Paths.get("consoleLNmail.log");
    public static Path file = Paths.get("console.006");

    public static List<String> readFile() {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(file, StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
