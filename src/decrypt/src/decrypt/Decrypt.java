
package decrypt;

import java.math.BigInteger;


public class Decrypt {

    public static void main(String[] args) {
        try {
            BigInteger modulus = new BigInteger(args[0]);
            BigInteger privateKey = new BigInteger(args[1]);
            BigInteger encryptedMessage = new BigInteger(args[2]);
            BigInteger message=modulusPower(encryptedMessage,privateKey, modulus);
            
            System.out.println(message);
        } catch(NumberFormatException nfe){
            System.out.println("Wrong Format");
        }
    }
      
    
    public static BigInteger modulusPower(BigInteger x, BigInteger y, BigInteger q) {  
        if (y.compareTo(BigInteger.ZERO) < 0)  
            //throw new IllegalArgumentException(); 
            return new BigInteger("1").divide(x).mod(q);
        BigInteger z = x;   
        BigInteger result = BigInteger.ONE;  
        byte[] bytes = y.toByteArray();  
        for (int i = bytes.length - 1; i >= 0; i--) {  
            byte bits = bytes[i];  
            for (int j = 0; j < 8; j++) {  
                if ((bits & 1) != 0)  
                    result = result.multiply(z);    
                if ((bits >>= 1) == 0 && i == 0)  
                    return result.mod(q);  
                z = z.multiply(z);  
            }  
        }  
    return result.mod(q);  
    }  
    
}


