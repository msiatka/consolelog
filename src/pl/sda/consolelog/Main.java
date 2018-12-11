package pl.sda.consolelog;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            //Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
            Pattern.compile("[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_IPADDRESS_REGEX =

            Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$", Pattern.CASE_INSENSITIVE);

    public static final Pattern NO_MESSAGES_TRANSFERRED_TO =

            Pattern.compile("^No messages transferred to", Pattern.CASE_INSENSITIVE);

    public static final Pattern RRRR_MM_DD_HH_MM_SS =

            Pattern.compile("2[01]?[0-9]{2}-[01]?[0-9]?-(0[1-9]?|[12]?[0-9]|3[01]?) (0[1-9]?|1[0-9]?|2[0-3]?):[0-5]?[0-9]?:[0-5]?[0-9]?");

    public static final Pattern ROUTER_MESSAGES_TRANSFERRED =

            Pattern.compile("^(.*)Router: Message [0-9A-Z]{8} transferred to (.*) for (.*) via SMTP", Pattern.CASE_INSENSITIVE);


    public static boolean validate(String emailStr) {

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);

        return matcher.find();

    }

    public static void main(String[] args) {

     //parser2.readFile();
     //   FileWritingPerfTest.main();
      //SearchSave.Regeex(Parser.readFile(),NO_MESSAGES_TRANSFERRED_TO);


      //  SearchSave.Filter(Parser.readFile()," transferred to");
      //  SearchSave.Regeex(Parser.readFile(),ROUTER_MESSAGES_TRANSFERRED);

        //email notransfer, yestransfered, ipaddress

        SearchSave.Regeex(Parser.readFile(),"authenticationfailed");

    }
}
