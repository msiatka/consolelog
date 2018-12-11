package pl.sda.consolelog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;




public class parser2 {

    public static Path file = Paths.get("console_lnmail2.log");


    public  static void readFile(){
        int i=0;
        List<Person> persons = new ArrayList<Person>();

        try {
            List<String> lines = Files.readAllLines(file, StandardCharsets.ISO_8859_1);


            //tu powinnismy juz przekazac nazwe - po regexie i serwerze i dacie
             String nazwapliku = "temp";
             boolean dopisac = true;
             boolean timestamp = true;
                BufferedWriter writer = null;

                String emp;
                String listString = "";

                try {
                    //create a temporary file
                    // String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                    String timeLog = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

                    File logFile;
                    if (timestamp) {  logFile = new File(nazwapliku + timeLog +".txt");}
                    else  logFile = new File(nazwapliku + timeLog +".txt");

                    // This will output the full path where the file will be written to...
                    System.out.println(" Lokalizacja exportowanego pliku: " + logFile.getCanonicalPath());

                    writer = new BufferedWriter(new FileWriter(logFile, dopisac));


                    for(String x:lines){
                        if(x.contains("No messages transferred to")){
                            writer.write(x);
                            writer.newLine();
                            i++;
                           // System.out.println(x);
                        }
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        // Close the writer regardless of what happens...
                        writer.close();
                    } catch (Exception e) {
                    }
                }


        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("No messages transferred to: " + i);
    }
}
