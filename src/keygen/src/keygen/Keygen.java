
package keygen;

import java.math.BigInteger;
import java.util.Random;


public class Keygen {
    // Αριθμός επαναλήψεων που προσδιορίζει την ακρίβεια
    private static final int K=30;
    private  static Random random = new Random();
    private static BigInteger privateKey;
    private static BigInteger publicKey;
    private static BigInteger modulus;
   
    public static void main(String[] args) {
        try {
            BigInteger p = new BigInteger(args[0]);
            BigInteger q = new BigInteger(args[1]);
            BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
            modulus=p.multiply(q);
            //publicKey  = generateKey(8);
            publicKey  = new BigInteger("11");
            privateKey=modulusInverse(publicKey, phi);
            
            System.out.println("Public key("+modulus+","+publicKey+")");
            System.out.println("Public key("+modulus+","+privateKey+")");
        } catch(NumberFormatException nfe){
            System.out.println("Wrong Format");
        }
    }
    
    
    
    public static BigInteger generateKey(int noOfBits){
        BigInteger n;
        do {
                n=new BigInteger(noOfBits, random);               
            } while (!test_prime_mr(n) || n.compareTo(new BigInteger("9"))<0);
        return n;
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
        power_mod=modulusPower(a,d, n);
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
    
    
    private static BigInteger modulusInverse(BigInteger u, BigInteger v) throws ArithmeticException {
        BigInteger x, y, q, r;
        //Arxikes times
        BigInteger u1 = BigInteger.ONE;
        BigInteger u2 = BigInteger.ZERO;
        BigInteger v1 = BigInteger.ZERO;
        BigInteger v2 = BigInteger.ONE;

        // An v=0 epistrefei 1
        if (v.equals(BigInteger.ZERO)) {
            return BigInteger.ONE;
        }
        
        while (v.compareTo(BigInteger.ZERO) == 1) {
            q = u.divide(v);
            x = v2.subtract(q.multiply(v1));
            y = u2.subtract(q.multiply(u1));
            r = u.subtract(q.multiply(v));
            u = v;
            v = r;
            v2 = v1;
            v1 = x;
            u2 = u1;
            u1 = y;
        }
        if (!u.equals(BigInteger.ONE))
            throw new ArithmeticException("Oi arithmoi den einai prwtoi metaxy tous");
        return v1.add(v2);
    }
}
