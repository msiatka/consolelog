package pl.sda.consolelog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchSave {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            //Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
            Pattern.compile("[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_IPADDRESS_REGEX =

            Pattern.compile("([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])", Pattern.CASE_INSENSITIVE);

    public static final Pattern NO_MESSAGES_TRANSFERRED_TO =

            Pattern.compile("^No messages transferred to", Pattern.CASE_INSENSITIVE);

    public static final Pattern RRRR_MM_DD_HH_MM_SS =

            Pattern.compile("2[01]?[0-9]{2}-[01]?[0-9]?-(0[1-9]?|[12]?[0-9]|3[01]?) (0[1-9]?|1[0-9]?|2[0-3]?):[0-5]?[0-9]?:[0-5]?[0-9]?");

    public static final Pattern ROUTER_MESSAGES_TRANSFERRED =

            Pattern.compile("^(.*)Router: Message [0-9A-Z]{8} transferred to (.*) for (.*) via SMTP", Pattern.CASE_INSENSITIVE);

    public static final Pattern AUTHENTICATION_FAILED_FOR_USER =

            Pattern.compile("^(.*)SMTP Server: Authentication failed for user (.*) ; connecting host (.*)", Pattern.CASE_INSENSITIVE);

    public static void Regeex(List<String> input, String wzor) {

        int i = 0;

        Pattern pattern = RRRR_MM_DD_HH_MM_SS;

        // Pattern compiledPattern = Pattern.compile("Jan.*");
        //email notransfer, yestransfered, ipaddress
        switch (wzor) {
            case "email": {
                pattern = VALID_EMAIL_ADDRESS_REGEX;
                break;
            }
            case "nottransered": {
                pattern = NO_MESSAGES_TRANSFERRED_TO;
                break;
            }

            case "yestransfered": {
                pattern = ROUTER_MESSAGES_TRANSFERRED;
                break;
            }
            case "ipadres": {
                pattern = VALID_IPADDRESS_REGEX;
                break;
            }

            case "authenticationfailed": {
                pattern = AUTHENTICATION_FAILED_FOR_USER;
                break;
            }
            default:
                pattern = RRRR_MM_DD_HH_MM_SS;
        }

        System.out.println(" A Pattern mamy: " + pattern.toString());


        //tu powinnismy juz przekazac nazwe - po regexie i serwerze i dacie
        String nazwapliku = "server_" + wzor + "_";
        //String nazwapliku = "server_regex";

        boolean dopisac = true;
        boolean timestamp = true;
        BufferedWriter writer = null;

        String emp;
        String startTimeLog = "";
        String endTimeLog = "";
        boolean startTimeLogB = false;
        boolean endTimeLogB = false;
        String listString = "";
        Matcher matcher;
        Matcher matchdatetime;

        try {
            //create a temporary file
            // String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            String timeLog = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

            File logFile;
            if (timestamp) {
                logFile = new File(nazwapliku + timeLog + ".txt");
            } else logFile = new File(nazwapliku + timeLog + ".txt");

            // This will output the full path where the file will be written to...
            System.out.println(" Lokalizacja exportowanego pliku: " + logFile.getCanonicalPath());

            writer = new BufferedWriter(new FileWriter(logFile, dopisac));


            i = input.size();
            System.out.println(" rozmiar pliku, linii: " + i);


//            //Assume List<String> foo;
//            ListIterator li = input.listIterator(input.size());
//            String curr;
//            while (li.hasPrevious()) {
//
//                //curr = li.previous().toString();
//                System.out.println(i--);
//            }


            for (int j = (input.size() - 1); j >= 0; j--) {

                matchdatetime = RRRR_MM_DD_HH_MM_SS.matcher(input.get(j));

                if (!endTimeLogB) {
                    if (matchdatetime.find()) {
                        endTimeLogB = true;
                        System.out.println("Ostatni wiersz z data w odpowiednim formacie: " + j);
                        System.out.println(RRRR_MM_DD_HH_MM_SS + " pasuje do: \"" +
                                matchdatetime.group(0) +
                                "\" w wierszu: \"" + input.get(j) + "\"");
                        endTimeLog = matchdatetime.group(0);
                        break;
                    }
                }
            }


            i = 0;

            int k = 0;
            for (String x : input) {
                matchdatetime = RRRR_MM_DD_HH_MM_SS.matcher(x.toString());

                if (!startTimeLogB) {
                    if (matchdatetime.find()) {
                        startTimeLogB = true;
                        System.out.println("Pierwszy wiersz z datą w odpowiednim formacie: " + k);
                        System.out.println(RRRR_MM_DD_HH_MM_SS + " pasuje do: \"" +
                                matchdatetime.group(0) +
                                "\" w wierszu: \"" + x + "\"");
                        startTimeLog = matchdatetime.group(0);
                    }
                }

                matcher = pattern.matcher(x.toString());
                if (matcher.find()) {
                    writer.write(x);
                    writer.newLine();
                    i++;
                    // System.out.println(x);
                }

                k++;
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer regardless of what happens...
                writer.write(" ++++++++ PODSUMOWANIE ++++++++++");
                writer.newLine();
                writer.write(" podsumowanie, data pierwszego logu: " + startTimeLog);
                writer.newLine();
                writer.write(" podsumowanie, data ostatniego logu: " + endTimeLog);
                writer.newLine();
                writer.write(" ilość wystąpiń : " + i);
//                writer.write(startTimeLog);
                writer.close();
            } catch (Exception e) {
            }
        }
    }

    public static void Filter(List<String> input, String pattern) {

        int i = 0;

        //tu powinnismy juz przekazac nazwe - po regexie i serwerze i dacie
        String nazwapliku = "server_" + pattern;
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
            if (timestamp) {
                logFile = new File(nazwapliku + timeLog + ".txt");
            } else logFile = new File(nazwapliku + timeLog + ".txt");

            // This will output the full path where the file will be written to...
            System.out.println(" Lokalizacja exportowanego pliku: " + logFile.getCanonicalPath());

            writer = new BufferedWriter(new FileWriter(logFile, dopisac));


            for (String x : input) {
                if (x.contains(pattern)) {
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
    }


}
