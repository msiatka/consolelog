package pl.sda.consolelog;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonValidation {
    private List<Person> w;

    public PersonValidation(List<Person> w) {
        this.w = w;
    }

    public List<Person> getW() {
        return w;
    }

    public void setW(List<Person> w) {
        this.w = w;
    }

    public void adresyIP(){
        int i=0;
        int j=0;
        for (Person person : w) {
            if (validateIP(person.getIpaddress())) {
                //  System.out.println(person.toString());
                //  System.out.println(" Adres poprawny");
                i++;
            } else {

                System.out.println("Niepoprawny adres ip : " + person.getIpaddress());
                //person.toString();
                j++;
            }
        }

        System.out.println("poprawnych ip: " +i );
        System.out.println("niepoprawnych ip: " +j );

    }

    public void email(){

                  int i=0;
            int j=0;
            for (Person person : w) {
                if (validate(person.getEmail())) {
                    //  System.out.println(person.toString());
                    //  System.out.println(" Adres poprawny");
                    i++;
                } else {

                    System.out.println("Niepoprawny adres mailowy: " + person.getEmail());
                    //person.toString();
                    j++;
                }
            }

            System.out.println("poprawnych mailowych: " +i );
            System.out.println("niepoprawnych mailowych: " +j );
            System.out.println(" ----------------------- ");

        }



    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =

            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_IPADDRESS_REGEX =

            Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);

        return matcher.find();

    }

    public static boolean validateIP(String emailStr) {

        Matcher matcher = VALID_IPADDRESS_REGEX .matcher(emailStr);

        return matcher.find();

    }
}
