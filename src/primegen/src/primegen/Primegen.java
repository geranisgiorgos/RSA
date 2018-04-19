package primegen;

import java.math.BigInteger;
import java.util.Random;


public class Primegen {
    // Αριθμός επαναλήψεων που προσδιορίζει την ακρίβεια
    private static final int K=30;
    private  static Random random = new Random();
    public static void main(String[] args) {
        int noOfBits;
        BigInteger n;
        
        try{
            noOfBits=Integer.parseInt(args[0]);
            do {
                n=new BigInteger(noOfBits, random);               
            } while (!test_prime_mr(n));
            System.out.println(n);
        }catch(NumberFormatException nfe){
            System.out.println("Wrong Format");
        }
        
    }
    
    // Μέθοδος Miller-Rabin
    public static boolean test_prime_mr(BigInteger n) {
         for (int i = 0;i<K;i++){
            BigInteger a;
            // Παραγωγή τυχαίο αριθμού 0<a<n
            do{
                // παίρνουμε τυχαίο αριθμό a
                a = new BigInteger(n.bitLength(), random);
            }while(a.equals(BigInteger.ZERO) || a.compareTo(n)>=0);
            if (!check_mr(n,a)) 
                return false;
        }
        return true;
    }
    
    public static boolean check_mr(BigInteger n, BigInteger a){
        BigInteger m,d,power_mod;
        int s;
        // Είναι m=n-1
        m=n.subtract(new BigInteger("1"));
            
        // Παίυνουμε τη θέση του λιγότερο σημαντικού άσσου (στη δυαδική μορφή)
        // έτσι ώστε να πάρουμε την κατάλληλη δύναμη του 2
        // δηλαδή να βρούμε τον εκθέτη s
        d=m;
        s = d.getLowestSetBit();
        // μετατόπιση δεξιά ώστε να πάρουμε τον αριθμό που απομένει όταν 
        // διαιρέσουμε με το 2^s
        d=d.shiftRight(s);
        // Υπολογισμός a^d mod n
        power_mod=a.modPow(d, n);
        //power_mod=modulusPower(a,d, n);
        if (power_mod.equals(BigInteger.ONE)) 
            return true;
        for (int k = 0; k < s-1; k++) {
            if (power_mod.equals(m)) 
                return true;
            power_mod=power_mod.multiply(power_mod).mod(n);
        }
        if (power_mod.equals(m)) 
            return true;
        return false;
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
