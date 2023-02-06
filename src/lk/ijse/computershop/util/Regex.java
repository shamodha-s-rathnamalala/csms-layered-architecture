package lk.ijse.computershop.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    private static Regex regex;
    private final Pattern namePattern;
    private final Pattern nicPattern;
    private final Pattern telephoneNumberPattern;
    private final Pattern emailPattern;
    private final Pattern pricePattern;
    private Regex() {
        namePattern = Pattern.compile("^[a-zA-Z.+=@\\-_\\s]{3,50}$");
        nicPattern = Pattern.compile("^[0-9]{9}[vVxX]||[0-9]{12}$");
        telephoneNumberPattern = Pattern.compile("^(?:0|94|\\+94|0094)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|91)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\\d)\\d{6}$");
        emailPattern = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");;
        pricePattern = Pattern.compile("^(\\d+)||((\\d+\\.)(\\d){2})$");
    }
    public static Regex getInstance(){
        return regex==null?(regex=new Regex()):regex;
    }

    public boolean isValid(RegexType regexType,String text){
        Matcher matcher;
        switch (regexType){
            case NAME:
                matcher = namePattern.matcher(text);
                return matcher.matches();
            case NIC:
                matcher = nicPattern.matcher(text);
                return matcher.matches();
            case TELEPHONE_NUMBER:
                matcher = telephoneNumberPattern.matcher(text);
                return matcher.matches();
            case EMAIL:
                matcher = emailPattern.matcher(text);
                return matcher.matches();
            case PRICE:
                matcher = pricePattern.matcher(text);
                return matcher.matches();
            default:
                return false;
        }
    }
}

