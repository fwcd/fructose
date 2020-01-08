package fwcd.fructose.math;

/**
 * Converts a binary number to decimal vice versa 
 * @author Savvas Theofilou
 */
public class NumberBaseConversion {
    
    /**
     * This method converts a binary number to the corresponding decimal number
     * @param binaryNum the number to be converted
     * @return the corresponding decimal number
     */
    public static int convertBinaryToDecimal(int binaryNum){
        int decimal = 0;  
        int n = 0;  
        while(true){  
            if(binaryNum == 0){  
                break;  
            } 
            else {  
                int temp = binaryNum % 10;  
                decimal += temp*Math.pow(2, n);  
                binaryNum = binaryNum/10;  
                n++;  
            }      
        }
        
        return decimal; 
    }
    
    /**
     * This method converts a decimal number to the corresponding binary number
     * @param decimalNum the number to be converted
     * @return the corresponding binary number in a form of ArrayList
     */
    public static ArrayList<Integer> convertDecimalToBinary(int decimalNum){
        ArrayList<Integer> binary = new ArrayList<>();
        
        int temp = 1;
        while(decimalNum > 0){
            binary.add(0, decimalNum % 2);
            decimalNum /= 2;            
        }
        
        return binary;
    }
    
}
