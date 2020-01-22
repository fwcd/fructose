package fwcd.fructose.math;

/**
 * 
 * @author Savvas Theofilou
 */
public class NumberBaseConversion {
    /**
     * Parses the number expressed in "from_base" base and returns its
     * corresponsive in "to_base" base.
     * @param number number to be converted
     * @param from_base initial base
     * @param to_base destination base
     * @return the converted number in String, null for unsupported
     */
    public static String convert_base(String number,int from_base, int to_base) { 
        if (from_base != 2 && from_base != 8 && from_base != 16
                && from_base != 10 && to_base != 2 && to_base != 8
                && to_base != 16 && to_base != 10){
            System.out.println("Unsupported base/s");
            return null;
        }
        try {
            if (to_base == 16){
                return Integer.toString( Integer.parseInt(number, from_base),
                to_base).toUpperCase();
            }
            return Integer.toString( Integer.parseInt(number, from_base),
                to_base);
        }
        catch(NumberFormatException e){
            System.out.println("Number Format Exception");
            return null;
        }
    } 
}
